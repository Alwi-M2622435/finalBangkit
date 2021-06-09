package com.hpdev.bangkitcapstone.ui.forum

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hpdev.bangkitcapstone.R
import com.hpdev.bangkitcapstone.data.ForumEntity

class ForumAdapter(private val forumList: List<ForumEntity>, private val ct: Context) :
    RecyclerView.Adapter<ForumAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_forum, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forum: ForumEntity = forumList[position]

        Glide.with(ct)
            .load(forum.image)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_baseline_refresh_24)
                    .error(R.drawable.ic_baseline_error_24))
            .into(holder.imageView)

        holder.tvTitle.text = forum.title
        holder.tvDescription.text = forum.description
        holder.btnLike.text = forum.likes.toString()
    }

    override fun getItemCount(): Int {
        return forumList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById<View>(R.id.img_poster) as ImageView
        val tvTitle: TextView = itemView.findViewById<View>(R.id.tv_item_title) as TextView
        val tvDescription: TextView = itemView.findViewById<View>(R.id.tv_description) as TextView
        val btnLike: Button = itemView.findViewById<View>(R.id.like_btn) as Button
    }
}