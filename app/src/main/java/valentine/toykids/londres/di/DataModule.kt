package valentine.toykids.londres.di

import valentine.toykids.londres.data.repository.CartRepository
import valentine.toykids.londres.data.repository.GURKMOnboardingRepo
import valentine.toykids.londres.data.repository.OrderRepository
import valentine.toykids.londres.data.repository.ProductRepository

import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    includes(databaseModule, dataStoreModule)

    single {
        GURKMOnboardingRepo(
            gurkmOnboardingStoreManager = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single { ProductRepository() }

    single {
        CartRepository(
            cartItemDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single {
        OrderRepository(
            orderDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }
}