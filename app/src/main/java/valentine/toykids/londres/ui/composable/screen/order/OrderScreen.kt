package valentine.toykids.londres.ui.composable.screen.order

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import valentine.toykids.londres.R
import valentine.toykids.londres.data.entity.OrderEntity
import valentine.toykids.londres.ui.composable.shared.GURKMContentWrapper
import valentine.toykids.londres.ui.composable.shared.GURKMEmptyView
import valentine.toykids.londres.ui.state.DataUiState
import valentine.toykids.londres.ui.theme.Background
import valentine.toykids.londres.ui.theme.HeadingFamily
import valentine.toykids.londres.ui.theme.Muted
import valentine.toykids.londres.ui.theme.OnSurface
import valentine.toykids.londres.ui.theme.Primary
import valentine.toykids.londres.ui.theme.Success
import valentine.toykids.londres.ui.theme.Surface
import valentine.toykids.londres.ui.viewmodel.OrderViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.format.DateTimeFormatter

@Composable
fun OrdersScreen(
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = koinViewModel(),
) {
    val ordersState by viewModel.ordersState.collectAsState()

    OrdersContent(
        ordersState = ordersState,
        modifier = modifier,
    )
}

@Composable
private fun OrdersContent(
    ordersState: DataUiState<List<OrderEntity>>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize().background(Background)) {
        GURKMContentWrapper(
            dataState = ordersState,
            dataPopulated = {
                val orders = (ordersState as DataUiState.Populated).data
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(orders, key = { it.orderNumber }) { order ->
                        OrderCard(order = order)
                    }
                }
            },
            dataEmpty = {
                GURKMEmptyView(
                    primaryText = stringResource(R.string.gurkm_orders_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}

@Composable
private fun OrderCard(order: OrderEntity) {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Surface)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.gurkm_order_number, order.orderNumber),
                fontFamily = HeadingFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = OnSurface
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Success.copy(alpha = 0.15f))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = stringResource(R.string.gurkm_order_completed),
                    color = Success,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = order.timestamp.format(formatter),
            color = Muted,
            style = MaterialTheme.typography.labelSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.gurkm_order_customer, order.customerFirstName, order.customerLastName),
            color = OnSurface,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = order.description,
            color = Muted,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 3
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "£%.2f".format(order.price),
                color = Primary,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}
