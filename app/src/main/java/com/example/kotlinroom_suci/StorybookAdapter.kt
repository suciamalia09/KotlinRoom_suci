package com.example.kotlinroom_suci

import android.graphics.Movie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinroom_suci.room.Storybook
import kotlinx.android.synthetic.main.list_storybook.view.*
import java.util.*
import kotlin.collections.ArrayList

class StorybookAdapter(private val storyes: ArrayList<Storybook>, private val listener: OnAdapterListener) : RecyclerView.Adapter<StorybookAdapter.StorybookViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorybookViewHolder {
        return StorybookViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_storybook, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StorybookViewHolder, position: Int) {
        val storybook  = storyes[position]
        holder.view.text_title.text = storybook.title
        holder.view.text_title.setOnClickListener {
            listener.onClick(storybook)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(storybook)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(storybook)
        }

    }

    override fun getItemCount() = storyes.size

    class StorybookViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Storybook>){
        storyes.clear()
        storyes.addAll(list)
        notifyDataSetChanged()
    }
    interface OnAdapterListener {
        fun onClick(storybook: Storybook)
        fun onUpdate(storybook: Storybook)
        fun onDelete(storybook: Storybook)
    }
}


