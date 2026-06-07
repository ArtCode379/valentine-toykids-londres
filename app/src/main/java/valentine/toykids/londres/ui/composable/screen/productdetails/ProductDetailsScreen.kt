package valentine.toykids.londres.ui.composable.screen.productdetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay
import valentine.toykids.londres.R
import valentine.toykids.londres.data.model.Product
import valentine.toykids.londres.ui.composable.shared.GURKMContentWrapper
import valentine.toykids.londres.ui.composable.shared.GURKMEmptyView
import valentine.toykids.londres.ui.state.DataUiState
import valentine.toykids.londres.ui.theme.Background
import valentine.toykids.londres.ui.theme.ChipBackground
import valentine.toykids.londres.ui.theme.ChipContent
import valentine.toykids.londres.ui.theme.HeadingFamily
import valentine.toykids.londres.ui.theme.Muted
import valentine.toykids.londres.ui.theme.OnPrimary
import valentine.toykids.londres.ui.theme.OnSurface
import valentine.toykids.londres.ui.theme.Primary
import valentine.toykids.londres.ui.theme.Surface
import valentine.toykids.londres.ui.viewmodel.ProductDetailsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductDetailsScreen(
    productId: Int,
    modifier: Modifier = Modifier,
    viewModel: ProductDetailsViewModel = koinViewModel(),
) {
    val productState by viewModel.productDetailsState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.observeProductDetails(productId)
    }

    ProductDetailsScreenContent(
        productState = productState,
        modifier = modifier,
        onAddToCart = viewModel::addProductToCart
    )
}

@Composable
private fun ProductDetailsScreenContent(
    productState: DataUiState<Product>,
    modifier: Modifier = Modifier,
    onAddToCart: () -> Unit,
) {
    var cartAdded by remember { mutableStateOf(false) }

    LaunchedEffect(cartAdded) {
        if (cartAdded) {
            delay(2000)
            cartAdded = false
        }
    }

    Box(modifier = modifier.fillMaxSize().background(Background)) {
        GURKMContentWrapper(
            dataState = productState,
            dataPopulated = {
                val product = (productState as DataUiState.Populated).data
                val galleryUrls = listOf(
                    product.imageUrl,
                    product.imageUrl.replace("w=600", "w=601"),
                    product.imageUrl.replace("w=600", "w=602"),
                )
                val pagerState = rememberPagerState(pageCount = { galleryUrls.size })

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(bottom = 90.dp)
                ) {
                    Box {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp)
                        ) { page ->
                            AsyncImage(
                                model = galleryUrls[page],
                                contentDescription = stringResource(R.string.gurkm_product_image_description),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(320.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            repeat(galleryUrls.size) { index ->
                                Box(
                                    modifier = Modifier
                                        .size(if (index == pagerState.currentPage) 8.dp else 6.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (index == pagerState.currentPage) Primary
                                            else OnSurface.copy(alpha = 0.4f)
                                        )
                                )
                            }
                        }
                    }

                    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(ChipBackground)
                                .padding(horizontal = 12.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = stringResource(product.category.titleRes),
                                color = ChipContent,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = product.title,
                            fontFamily = HeadingFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = OnSurface,
                            lineHeight = 30.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = product.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Muted,
                            lineHeight = 22.sp
                        )
                    }
                }
            },
            dataEmpty = {
                GURKMEmptyView(
                    primaryText = stringResource(R.string.gurkm_product_details_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )

        // Floating sticky bottom bar
        if (productState is DataUiState.Populated) {
            val product = (productState as DataUiState.Populated<Product>).data
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Surface)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "£%.2f".format(product.price),
                        fontFamily = HeadingFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,
                        color = Primary
                    )
                    Button(
                        onClick = {
                            onAddToCart()
                            cartAdded = true
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Primary,
                            contentColor = OnPrimary
                        ),
                        modifier = Modifier.height(48.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.gurkm_button_add_to_cart_label),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // Add-to-cart confirmation banner (editorial: thin surface bar, Primary checkmark)
        AnimatedVisibility(
            visible = cartAdded,
            enter = slideInVertically { it },
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Surface)
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = stringResource(R.string.gurkm_added_to_cart),
                        color = OnSurface,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
