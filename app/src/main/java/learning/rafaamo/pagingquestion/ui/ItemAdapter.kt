package learning.rafaamo.pagingquestion.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import learning.rafaamo.pagingquestion.databinding.ItemBinding
import learning.rafaamo.pagingquestion.domain.Item

class ItemAdapter(diffCallback: DiffUtil.ItemCallback<Item>): PagingDataAdapter<Item, ItemViewHolder>(diffCallback) {

  object ItemComparator : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
      return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
      return oldItem == newItem
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    return ItemViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
  }

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    val item = getItem(position)
    holder.bind(item)
  }

}

class ItemViewHolder(private val view: ItemBinding): RecyclerView.ViewHolder(view.root) {

  fun bind(postItem: Item?) {
    if (postItem != null) {
      view.apply {
        name.text = postItem.name
        code.text = postItem.code
      }
    }
  }

}