package learning.rafaamo.pagingquestion.domain

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface ItemDao {

  @Query("SELECT * FROM items")
  fun getItems(): PagingSource<Int, Item>

  @Insert
  fun insertAll(items: List<Item>)

}
