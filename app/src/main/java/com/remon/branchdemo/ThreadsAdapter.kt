package com.remon.branchdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.remon.branchdemo.data.Message
import com.remon.branchdemo.databinding.ThreadsItemBinding
import java.text.SimpleDateFormat
import java.util.*


class ThreadsAdapter(val listener: MessageClickListener): RecyclerView.Adapter<ThreadsAdapter.ThreadsViewHolder>() {

    val listDiffer = AsyncListDiffer(this, object: DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    })

    class ThreadsViewHolder(val binding: ThreadsItemBinding, val clickListener: MessageClickListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(msg: Message) {
            binding.user.text = msg.userId  // agent or userid
            binding.body.text = msg.body
            binding.time.text = formatTime(msg.timestamp)

            binding.root.setOnClickListener {
                clickListener.onClick(msg)
            }
        }

        private fun formatTime(timestamp: Date): String {
            return SimpleDateFormat("MM/dd/yyyy hh:mm").format(timestamp)  // day/time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThreadsViewHolder {
        return ThreadsViewHolder(ThreadsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
    }

    override fun onBindViewHolder(holder: ThreadsViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount(): Int {
        return listDiffer.currentList.size
    }

    fun submitData(threads: List<Message>) {
        listDiffer.submitList(threads)
    }
}

interface MessageClickListener {
    fun onClick(msg: Message)
}