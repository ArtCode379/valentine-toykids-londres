package valentine.toykids.londres.ui.composable.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import valentine.toykids.londres.R
import valentine.toykids.londres.data.model.Product
import valentine.toykids.londres.data.model.ProductCategory
import valentine.toykids.londres.ui.composable.shared.GURKMContentWrapper
import valentine.toykids.londres.ui.composable.shared.GURKMEmptyView
import valentine.toykids.londres.ui.state.DataUiState
import valentine.toykids.londres.ui.theme.Background
import valentine.toykids.londres.ui.theme.Border
import valentine.toykids.londres.ui.theme.ChipBackground
import valentine.toykids.londres.ui.theme.ChipContent
import valentine.toykids.londres.ui.theme.HeadingFamily
import valentine.toykids.londres.ui.theme.Muted
import valentine.toykids.londres.ui.theme.OnSurface
import valentine.toykids.londres.ui.theme.Primary
import valentine.toykids.londres.ui.theme.Surface
import valentine.toykids.londres.ui.viewmodel.ProductViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = koinViewModel(),
    onNavigateToProductDetails: (productId: Int) -> Unit,
) {
    val productsState by viewModel.productsState.collectAsState()
    var showSearch by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize().background(Background)) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.gurkm_top_bar_home_title),
                    fontFamily = HeadingFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = OnSurface
                )
            },
            actions = {
                IconButton(onClick = { showSearch = !showSearch }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = OnSurface
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Surface)
        )

        HomeContent(
            productsState = productsState,
            onNavigateToProductDetails = onNavigateToProductDetails,
            onAddProductToCart = viewModel::addToCart,
        )
    }
}

@Composable
private fun HomeContent(
    productsState: DataUiState<List<Product>>,
    onNavigateToProductDetails: (productId: Int) -> Unit,
    onAddProductToCart: (productId: Int) -> Unit,
) {
    GURKMContentWrapper(
        dataState = productsState,
        dataPopulated = {
            val products = (productsState as DataUiState.Populated).data
            var selectedCategory by remember { mutableStateOf<ProductCategory?>(null) }
            val filtered = if (selectedCategory == null) products
            else products.filter { it.category == selectedCategory }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                if (products.isNotEmpty()) {
                    HeroProductCard(
                        product = products.first(),
                        onClick = { onNavigateToProductDetails(products.first().id) }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Collections",
                    fontFamily = HeadingFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = OnSurface,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        CategoryChip(
                            label = "All",
                            selected = selectedCategory == null,
                            onClick = { selectedCategory = null }
                        )
                    }
                    items(ProductCategory.entries) { cat ->
                        CategoryChip(
                            label = stringResource(cat.titleRes),
                            selected = selectedCategory == cat,
                            onClick = { selectedCategory = if (selectedCategory == cat) null else cat }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(R.string.gurkm_all_products),
                    fontFamily = HeadingFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = OnSurface,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                val productsToShow = filtered.drop(1)
                val rows = productsToShow.chunked(2)
                rows.forEachIndexed { rowIndex, rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowItems.forEachIndexed { colIndex, product ->
                            val cardHeight: Dp = if ((rowIndex + colIndex) % 2 == 1) 240.dp else 180.dp
                            ProductGridCard(
                                product = product,
                                imageHeight = cardHeight,
                                modifier = Modifier.weight(1f),
                                onClick = { onNavigateToProductDetails(product.id) }
                            )
                        }
                        if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        },
        dataEmpty = {
            GURKMEmptyView(
                primaryText = stringResource(R.string.gurkm_products_state_empty_primary_text),
                modifier = Modifier.fillMaxSize(),
            )
        },
    )
}

@Composable
private fun HeroProductCard(product: Product, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.0f to Surface.copy(alpha = 0f),
                            0.55f to Surface.copy(alpha = 0.5f),
                            1.0f to Surface.copy(alpha = 0.92f)
                        )
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = "Featured",
                color = Primary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                letterSpacing = 1.sp
            )
            Text(
                text = product.title,
                fontFamily = HeadingFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = OnSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "£%.2f".format(product.price),
                color = Primary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun CategoryChip(label: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .then(
                if (selected) Modifier.background(Primary)
                else Modifier.background(ChipBackground).border(1.dp, Border, RoundedCornerShape(50))
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            color = if (selected) OnSurface else ChipContent,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            fontSize = 13.sp
        )
    }
}

@Composable
private fun ProductGridCard(
    product: Product,
    imageHeight: Dp,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Surface)
            .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight)
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
        )
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = stringResource(product.category.titleRes),
                color = Muted,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = product.title,
                color = OnSurface,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 18.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "£%.2f".format(product.price),
                color = Primary,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}
