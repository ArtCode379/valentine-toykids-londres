package valentine.toykids.londres.di

import valentine.toykids.londres.ui.viewmodel.AppViewModel
import valentine.toykids.londres.ui.viewmodel.CartViewModel
import valentine.toykids.londres.ui.viewmodel.CheckoutViewModel
import valentine.toykids.londres.ui.viewmodel.GURKMOnboardingVM
import valentine.toykids.londres.ui.viewmodel.OrderViewModel
import valentine.toykids.londres.ui.viewmodel.ProductDetailsViewModel
import valentine.toykids.londres.ui.viewmodel.ProductViewModel
import valentine.toykids.londres.ui.viewmodel.GURKMSplashVM
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        AppViewModel(
            cartRepository = get()
        )
    }

    viewModel {
        GURKMSplashVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        GURKMOnboardingVM(
            onboardingRepository = get()
        )
    }

    viewModel {
        ProductViewModel(
            productRepository = get(),
            cartRepository = get(),
        )
    }

    viewModel {
        ProductDetailsViewModel(
            productRepository = get(),
            cartRepository = get(),
        )
    }

    viewModel {
        CheckoutViewModel(
            cartRepository = get(),
            productRepository = get(),
            orderRepository = get(),
        )
    }

    viewModel {
        CartViewModel(
            cartRepository = get(),
            productRepository = get(),
        )
    }

    viewModel {
        OrderViewModel(
            orderRepository = get(),
        )
    }
}