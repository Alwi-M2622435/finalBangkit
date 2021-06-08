package com.hpdev.bangkitcapstone.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hpdev.bangkitcapstone.R
import com.hpdev.bangkitcapstone.data.ImageEntity

class MenuAdapter(val imageList: List<ImageEntity>, val ct: Context) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.dashboard_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image: ImageEntity = imageList[position]

        Glide.with(ct)
            .load(image.image)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_baseline_refresh_24)
                    .error(R.drawable.ic_baseline_error_24))
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById<View>(R.id.myimage) as ImageView
    }

}