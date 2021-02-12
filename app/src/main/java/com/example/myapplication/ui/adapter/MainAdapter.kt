package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.model.Article
import com.example.myapplication.ui.base.OnItemClick
import com.example.myapplication.utils.ImageDownloader
import kotlinx.android.synthetic.main.recycler_item.view.*

class MainAdapter(private val items: ArrayList<Article>) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    private var listener: OnItemClick<Article>? = null
    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(model: Article, listener: OnItemClick<Article>?) {
            itemView.apply {
                title.text = model.title
                summary.text = model.description
                ImageDownloader.download(imageViewAvatar, model.urlToImage)
            }
            itemView.setOnClickListener { listener?.onItemClicked(model) }
        }
    }

    fun setItemClickListener(listener:OnItemClick<Article>?){
        this.listener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(items[position],listener)
    }

    fun addItems(items: List<Article>?) {
        if (items == null) {
            this.items.clear()
            return
        }
        this.items.apply {
            addAll(items)
        }
    }


}