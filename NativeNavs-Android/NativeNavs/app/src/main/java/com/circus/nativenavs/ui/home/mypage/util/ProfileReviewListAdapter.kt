package com.circus.nativenavs.ui.home.mypage.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.circus.nativenavs.R
import com.circus.nativenavs.data.mypage.ProfileReviewDto

class ProfileReviewListAdapter : ListAdapter<ProfileReviewDto,ProfileReviewListAdapter.ProfileReviewHolder>(ProfileReviewDiffUitl()) {

    companion object {
        class ProfileReviewDiffUitl : DiffUtil.ItemCallback<ProfileReviewDto>() {
            override fun areItemsTheSame(
                oldItem: ProfileReviewDto, newItem: ProfileReviewDto
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ProfileReviewDto, newItem: ProfileReviewDto
            ): Boolean {
                return oldItem == newItem
            }

        }
    }



    inner class ProfileReviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindInfo(dto:ProfileReviewDto){
            itemView.apply {
                findViewById<RatingBar>(R.id.profile_review_ratingBar).rating = dto.rating.toFloat()
                findViewById<TextView>(R.id.profile_review_date).text = dto.data
                findViewById<ImageView>(R.id.profile_review_user_img).setImageURI(dto.img.toUri())
                findViewById<TextView>(R.id.profile_reivew_content).text = dto.content
                findViewById<TextView>(R.id.profile_reivew_user_name).text =dto.userName
                findViewById<TextView>(R.id.profile_review_user_language).text = dto.userLanguage
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileReviewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_profile_review, parent,false)
        return ProfileReviewHolder(view)

    }

    override fun onBindViewHolder(holder: ProfileReviewHolder, position: Int) {
        val item = getItem(position)
        holder.bindInfo(item)

    }

}