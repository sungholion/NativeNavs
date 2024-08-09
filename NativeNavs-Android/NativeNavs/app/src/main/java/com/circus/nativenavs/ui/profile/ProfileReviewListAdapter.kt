package com.circus.nativenavs.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.circus.nativenavs.R
import com.circus.nativenavs.data.ProfileReviewDto
import com.circus.nativenavs.databinding.ItemProfileReviewBinding

class ProfileReviewListAdapter : ListAdapter<ProfileReviewDto, ProfileReviewListAdapter.ProfileReviewHolder>(
    ProfileReviewDiffUitl()
) {

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
        fun bindInfo(dto: ProfileReviewDto){
            itemView.apply {
                findViewById<RatingBar>(R.id.profile_review_ratingBar).rating = dto.rating.toFloat()
                findViewById<TextView>(R.id.profile_review_date).text = dto.data
                findViewById<TextView>(R.id.profile_reivew_content).text = dto.content
                findViewById<TextView>(R.id.profile_reivew_user_name).text =dto.userName
                findViewById<TextView>(R.id.profile_review_user_language).text = dto.userLanguage

                Glide.with(this)
                    .load(dto.img) // 불러올 이미지 url
                    .placeholder(R.drawable.logo_nativenavs) // 이미지 로딩 시작하기 전 표시할 이미지
                    .error(R.drawable.logo_nativenavs) // 로딩 에러 발생 시 표시할 이미지
                    .fallback(R.drawable.logo_nativenavs) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
                    .into(findViewById<ImageView>(R.id.profile_review_img))

                Glide.with(this)
                    .load(dto.userImg)
                    .placeholder(R.drawable.logo_nativenavs)
                    .error(R.drawable.logo_nativenavs)
                    .fallback(R.drawable.logo_nativenavs)
                    .into(findViewById<ImageView>(R.id.profile_review_user_img))
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