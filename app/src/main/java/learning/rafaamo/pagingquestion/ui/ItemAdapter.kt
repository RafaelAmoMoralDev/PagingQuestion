package learning.rafaamo.pagingquestion.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import learning.rafaamo.pagingquestion.R
import learning.rafaamo.pagingquestion.databinding.HeaderBinding
import learning.rafaamo.pagingquestion.databinding.ItemBinding

class ItemAdapter(diffCallback: DiffUtil.ItemCallback<UiItem>): PagingDataAdapter<UiItem, RecyclerView.ViewHolder>(diffCallback) {

  override fun getItemViewType(position: Int): Int {
    val uiItem: UiItem = peek(position)!!
    return if (uiItem is UiItem.Header) {
      R.layout.header
    } else {
      R.layout.item
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return if (viewType == R.layout.header) {
      HeaderViewHolder(HeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    } else {
      ItemViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val uiItem: UiItem = getItem(position)!!

    if (holder is HeaderViewHolder) {
      holder.bind(uiItem as UiItem.Header)
    } else if (holder is ItemViewHolder) {
      holder.bind(uiItem as UiItem.Item)
    }
  }

  object ItemComparator : DiffUtil.ItemCallback<UiItem>() {
    override fun areItemsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
      val isSameHeader = oldItem is UiItem.Header
              && newItem is UiItem.Header &&
              newItem.id == oldItem.id

      val isSamePost = oldItem is UiItem.Item
              && newItem is UiItem.Item
              && oldItem.item.id == newItem.item.id

      return isSameHeader || isSamePost
    }

    override fun areContentsTheSame(oldItem: UiItem, newItem: UiItem): Boolean {
      return oldItem == newItem
    }
  }

}

class ItemViewHolder(private val view: ItemBinding): RecyclerView.ViewHolder(view.root) {

  fun bind(postItem: UiItem.Item) {
    view.apply {
      name.text = postItem.item.name
      code.text = postItem.item.code
    }
  }

}

class HeaderViewHolder(private val view: HeaderBinding): RecyclerView.ViewHolder(view.root) {

  fun bind(headerItem: UiItem.Header) {
    view.apply {
      id.text = headerItem.id
    }
  }

}