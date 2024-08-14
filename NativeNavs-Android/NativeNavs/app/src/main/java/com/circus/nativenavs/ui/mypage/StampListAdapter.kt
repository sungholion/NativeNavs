import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.circus.nativenavs.data.StampDto
import com.circus.nativenavs.databinding.ItemStampBinding

class StampListAdapter : ListAdapter<StampDto, StampListAdapter.StampViewHolder>(StampDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StampViewHolder {
        val binding = ItemStampBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StampViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StampViewHolder, position: Int) {
        val stampDto = getItem(position)
        holder.bind(stampDto)
    }

    class StampViewHolder(private val binding: ItemStampBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(stampDto: StampDto) {
            binding.stampTv.text = stampDto.name
             Glide.with(binding.stampIv.context)
                 .load(stampDto.image)
                 .into(binding.stampIv)
        }
    }

    class StampDiffCallback : DiffUtil.ItemCallback<StampDto>() {
        override fun areItemsTheSame(oldItem: StampDto, newItem: StampDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StampDto, newItem: StampDto): Boolean {
            return oldItem == newItem
        }
    }
}
