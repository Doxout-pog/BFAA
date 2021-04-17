package com.Naufaldo.Submisssion1

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.Naufaldo.Submisssion1.databinding.ItemCardviewUserBinding

class Adapter(private val listUser: ArrayList<User>): RecyclerView.Adapter<Adapter.ListViewHolder>() {
    private lateinit var mContex: Context

    companion object {
        private lateinit var onItemClickCallback: OnItemClickCallback
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        Companion.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    inner class ListViewHolder(private val itemBinding: ItemCardviewUserBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(user: User) {
            with(itemBinding) {
                mContex = itemView.context
                itemBinding.tvName.text = user.name
                itemBinding.tvUserName.text = "Username: @${user.userName}"
                Glide.with(mContex)
                        .load(user.avatar)
                        .into(itemBinding.cvPhoto)
            }
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemBinding = ItemCardviewUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)
        holder.itemView.setOnClickListener {
            val intent = Intent(mContex, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USER, listUser[position])
            mContex.startActivity(intent)
        }
    }
}