package valentine.toykids.londres.di

import androidx.room.Room
import valentine.toykids.londres.data.database.GURKMDatabase
import org.koin.dsl.module

private const val DB_NAME = "gurkm_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = GURKMDatabase::class.java,
            name = DB_NAME
        ).build()
    }

    single { get<GURKMDatabase>().cartItemDao() }

    single { get<GURKMDatabase>().orderDao() }
}