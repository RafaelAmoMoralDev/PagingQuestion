package learning.rafaamo.pagingquestion.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import learning.rafaamo.pagingquestion.domain.Item
import learning.rafaamo.pagingquestion.domain.ItemRepository
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(itemRepository: ItemRepository): ViewModel() {

  var pagingDataFlow: Flow<PagingData<Item>> = itemRepository.getItems().cachedIn(viewModelScope)

}