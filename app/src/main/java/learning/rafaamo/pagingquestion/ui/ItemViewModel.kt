package learning.rafaamo.pagingquestion.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.TerminalSeparatorType
import androidx.paging.cachedIn
import androidx.paging.insertHeaderItem
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import learning.rafaamo.pagingquestion.domain.ItemRepository
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(itemRepository: ItemRepository): ViewModel() {

  var pagingDataFlow: Flow<PagingData<UiItem>> = itemRepository.getItems().map { pagingData ->
    (pagingData.map { println(it); UiItem.Item(it) } as PagingData<UiItem>)
    .insertHeaderItem(TerminalSeparatorType.SOURCE_COMPLETE, UiItem.Header(UUID.randomUUID().toString()))
  }
  .cachedIn(viewModelScope)

}

sealed class UiItem {
  class Header(val id: String): UiItem()
  class Item(val item: learning.rafaamo.pagingquestion.domain.Item): UiItem()
}