package valentine.toykids.londres.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import valentine.toykids.londres.data.dao.CartItemDao
import valentine.toykids.londres.data.dao.OrderDao
import valentine.toykids.londres.data.database.converter.Converters
import valentine.toykids.londres.data.entity.CartItemEntity
import valentine.toykids.londres.data.entity.OrderEntity

@Database(
    entities = [CartItemEntity::class, OrderEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class GURKMDatabase : RoomDatabase() {

    abstract fun cartItemDao(): CartItemDao

    abstract fun orderDao(): OrderDao
}