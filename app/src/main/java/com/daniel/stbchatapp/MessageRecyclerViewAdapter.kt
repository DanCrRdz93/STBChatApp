package com.daniel.stbchatapp

import android.view.Gravity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import com.daniel.stbchatapp.databinding.FragmentMessageBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MessageRecyclerViewAdapter(
    private val user : String,
    private val values: MutableList<MessageFragment.FastChat>
) : RecyclerView.Adapter<MessageRecyclerViewAdapter.ViewHolder>() {

    fun updateList(newList: List<MessageFragment.FastChat>) {
        values.clear()
        values.addAll(newList)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idMessage.text = item.message
        holder.idUserName.text = item.user

        if (item.user == user) {
            holder.layout.setBackgroundColor(
                getColor(
                    holder.itemView.context,
                    R.color.lightBlue
                )
            )
            holder.mainLayout.gravity = Gravity.END


        } else {
            holder.layout.setBackgroundColor(
                getColor(
                    holder.itemView.context,
                    R.color.lightGreen
                )
            )
            holder.mainLayout.gravity = Gravity.START
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val mainLayout = binding.mainLayout
        val layout = binding.layout
        val idMessage: TextView = binding.tvMessage
        val idUserName: TextView = binding.tvUser
        //val contentView: TextView = binding.content

//        override fun toString(): String {
//            return super.toString() + " '" + contentView.text + "'"
//        }
    }

}