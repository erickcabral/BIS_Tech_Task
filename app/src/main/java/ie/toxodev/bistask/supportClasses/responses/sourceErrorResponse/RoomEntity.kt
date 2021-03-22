package ie.toxodev.bistask.supportClasses.responses.sourceErrorResponse

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "source_errors_json")
class RoomEntity(
    @ColumnInfo(name = "source_list")
    val jsonString: String,

    @ColumnInfo(name = "time_stamp")
    val timeStamp: String
) {
    @PrimaryKey
    var id = 0
}