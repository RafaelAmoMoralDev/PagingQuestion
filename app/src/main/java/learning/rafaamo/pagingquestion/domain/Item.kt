package learning.rafaamo.pagingquestion.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "items")
data class Item(
  @PrimaryKey(autoGenerate = true)
  val id: Long,
  val name: String,
  val code: String
) {

  companion object {
    fun getData(): List<Item> {
      return mutableListOf<Item>().apply {
        for (i in 1..101) {
          add(Item(i.toLong(), "Item $i", UUID.randomUUID().toString()))
        }
      }
    }
  }

}
