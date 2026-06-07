package valentine.toykids.londres.ui.composable.screen.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import valentine.toykids.londres.R
import valentine.toykids.londres.ui.composable.shared.GURKMContentWrapper
import valentine.toykids.londres.ui.composable.shared.GURKMEmptyView
import valentine.toykids.londres.ui.state.CartItemUiState
import valentine.toykids.londres.ui.state.DataUiState
import valentine.toykids.londres.ui.theme.Background
import valentine.toykids.londres.ui.theme.Border
import valentine.toykids.londres.ui.theme.Muted
import valentine.toykids.londres.ui.theme.OnPrimary
import valentine.toykids.londres.ui.theme.OnSurface
import valentine.toykids.londres.ui.theme.Primary
import valentine.toykids.londres.ui.theme.Surface
import valentine.toykids.londres.ui.viewmodel.CartViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = koinViewModel(),
    onNavigateToCheckoutScreen: () -> Unit,
) {
    val cartItemsState by viewModel.cartItemsState.collectAsStateWithLifecycle()
    val totalPrice by viewModel.totalPrice.collectAsStateWithLifecycle()

    CartScreenContent(
        cartItemsState = cartItemsState,
        modifier = modifier,
        totalPrice = totalPrice,
        onPlusItemClick = viewModel::incrementProductInCart,
        onMinusItemClick = viewModel::decrementItemInCart,
        onCompleteOrderButtonClick = onNavigateToCheckoutScreen,
    )
}

@Composable
private fun CartScreenContent(
    cartItemsState: DataUiState<List<CartItemUiState>>,
    modifier: Modifier = Modifier,
    totalPrice: Double,
    onPlusItemClick: (Int) -> Unit,
    onMinusItemClick: (Int) -> Unit,
    onCompleteOrderButtonClick: () -> Unit,
) {
    Column(modifier = modifier.fillMaxSize().background(Background)) {
        GURKMContentWrapper(
            dataState = cartItemsState,
            dataPopulated = {
                val items = (cartItemsState as DataUiState.Populated).data
                Column(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(items, key = { it.productId }) { item ->
                            CartItemRow(
                                item = item,
                                onPlus = { onPlusItemClick(item.productId) },
                                onMinus = { onMinusItemClick(item.productId) },
                            )
                            HorizontalDivider(color = Border, thickness = 0.5.dp)
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Surface)
                            .padding(horizontal = 20.dp, vertical = 20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(R.string.gurkm_cart_subtotal),
                                color = Muted,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "£%.2f".format(totalPrice),
                                color = OnSurface,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider(color = Border, thickness = 0.5.dp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(R.string.gurkm_cart_total),
                                color = OnSurface,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Text(
                                text = "£%.2f".format(totalPrice),
                                color = Primary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onCompleteOrderButtonClick,
                            modifier = Modifier.fillMaxWidth().height(52.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Primary,
                                contentColor = OnPrimary
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.gurkm_button_proceed_checkout),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            },
            dataEmpty = {
                GURKMEmptyView(
                    primaryText = stringResource(R.string.gurkm_cart_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}

@Composable
private fun CartItemRow(
    item: CartItemUiState,
    onPlus: () -> Unit,
    onMinus: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Surface)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.productImageUrl,
            contentDescription = item.productTitle,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Background)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.productTitle,
                color = OnSurface,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "£%.2f".format(item.productPrice),
                color = Primary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            IconButton(onClick = onMinus, modifier = Modifier.size(32.dp)) {
                Icon(
                    imageVector = if (item.quantity <= 1) Icons.Default.Delete else Icons.Default.Remove,
                    contentDescription = if (item.quantity <= 1)
                        stringResource(R.string.gurkm_delete_item_icon_description)
                    else
                        stringResource(R.string.gurkm_decrease_quantity_icon_description),
                    tint = if (item.quantity <= 1) Muted else OnSurface,
                    modifier = Modifier.size(18.dp)
                )
            }
            Text(
                text = item.quantity.toString(),
                color = OnSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            IconButton(onClick = onPlus, modifier = Modifier.size(32.dp)) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.gurkm_increase_quantity_icon_description),
                    tint = Primary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
