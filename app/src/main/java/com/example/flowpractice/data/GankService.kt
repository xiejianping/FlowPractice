package com.example.flowpractice.data

import android.util.Log
import com.example.flowpractice.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.Charset

object GankService {

    private const val TAG = "GankService"
    private const val BASE_URL = "https://gank.io/api/v2"

    private val okhttp by lazy { OkHttpClient.Builder().addInterceptor(logInterceptor).build() }

    private val logInterceptor = Interceptor { chain ->
        if (BuildConfig.DEBUG.not()) {
            return@Interceptor chain.proceed(chain.request())
        }
        val request = chain.request()

        val defaultCharset = Charset.forName("utf-8")
        val logRequest = request.newBuilder().build()
        val url = logRequest.url().toString()
        val requestBody = logRequest.body()
        var body = ""
        if (requestBody != null) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            var charset: Charset? = null
            val contentType = requestBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(defaultCharset)
            }
            body = buffer.readString(charset ?: defaultCharset)
        }
        Log.v(
            TAG, "\n请求服务器 : $url " +
                    "\nheaders : ${logRequest.headers()}" +
                    "\nbody : $body"
        )

        val response = chain.proceed(request)

        val responseBody = response.body()
        val rBody: String
        val source = responseBody?.source()
        source?.request(Long.MAX_VALUE)
        val buffer = source?.buffer

        var charset = defaultCharset
        val contentType = responseBody?.contentType()
        if (contentType != null) {
            try {
                charset = contentType.charset(defaultCharset);
            } catch (e: Exception) {
                e.printStackTrace();
            }
        }
        rBody = buffer?.clone()?.readString(charset) ?: ""

        Log.v(
            TAG,
            "\n服务器响应: code:" + response.code()
                    + "\n请求url：" + response.request().url()
                    + "\n请求body：" + body
                    + "\nResponse: " + rBody
        )

        response
    }

    val gankApi by lazy { createRetrofit(BASE_URL).create(GankApi::class.java) }

    private fun createRetrofit(baseURL: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .client(okhttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}