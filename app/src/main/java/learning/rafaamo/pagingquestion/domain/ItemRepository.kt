package learning.rafaamo.pagingquestion.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(private val itemDao: ItemDao) {

  fun getItems(): Flow<PagingData<Item>> {
    return Pager(
      config = PagingConfig(
        pageSize = 20,
        enablePlaceholders = true
      ),
      pagingSourceFactory = {
        itemDao.getItems()
      }
    ).flow
  }

}
