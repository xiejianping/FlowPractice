package com.example.flowpractice.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.flowpractice.databinding.FragmentRoomBinding
import com.example.flowpractice.databinding.ItemDbUserBinding
import com.example.flowpractice.db.DbViewModel
import com.example.flowpractice.db.User
import com.example.flowpractice.extension.fragmentBind

class RoomFragment : Fragment() {
    private val mBinding by fragmentBind(FragmentRoomBinding::inflate)

    private val dbViewModel by viewModels<DbViewModel>()

    private val roomUserAdapter by lazy { RoomUserAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.apply {
            btAdd.setOnClickListener {
                val uid = etId.text.toString()
                val firstName = etFirstName.text.toString()
                val lastName = etLastName.text.toString()
                dbViewModel.insert(User(uid, firstName, lastName))
            }

            rvAllUser.adapter = roomUserAdapter
        }
        lifecycleScope.launchWhenCreated {
            dbViewModel.findAll().collect {
                roomUserAdapter.setData(it)
            }

        }
    }
}

class RoomUserAdapter : RecyclerView.Adapter<UserViewHolder>() {

    private var data: List<User>? = null

    fun setData(data: List<User>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemDbUserBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.setData(data?.getOrNull(position) ?: return)
    }

    override fun getItemCount() = data?.size ?: 0

}

class UserViewHolder(private val viewBinding: ItemDbUserBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun setData(user: User) {
        viewBinding.tvUser.text = "${user.uid}  ${user.firstName} · ${user.lastName}"
    }
}