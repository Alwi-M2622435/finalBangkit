package com.hpdev.bangkitcapstone.ui.professional

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hpdev.bangkitcapstone.R
import com.hpdev.bangkitcapstone.data.ProfessionalEntity

class ProfessionalAdapter(private val professionalList: List<ProfessionalEntity>, private val ct: Context) :
    RecyclerView.Adapter<ProfessionalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_professional, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val professional: ProfessionalEntity = professionalList[position]

        Glide.with(ct)
            .load(professional.image)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_baseline_refresh_24)
                    .error(R.drawable.ic_baseline_error_24))
            .into(holder.imageView)

        holder.tvTitle.text = professional.title
        holder.tvDescription.text = professional.description
        holder.mRating.rating = professional.rate
    }

    override fun getItemCount(): Int {
        return professionalList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById<View>(R.id.img_poster) as ImageView
        val tvTitle: TextView = itemView.findViewById<View>(R.id.tv_item_title) as TextView
        val tvDescription: TextView = itemView.findViewById<View>(R.id.tv_description) as TextView
        val mRating: RatingBar = itemView.findViewById<View>(R.id.rating) as RatingBar
    }
}