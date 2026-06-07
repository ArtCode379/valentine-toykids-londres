package valentine.toykids.londres.ui.composable.screen.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import valentine.toykids.londres.R
import valentine.toykids.londres.ui.theme.Background
import valentine.toykids.londres.ui.theme.Border
import valentine.toykids.londres.ui.theme.HeadingFamily
import valentine.toykids.londres.ui.theme.Muted
import valentine.toykids.londres.ui.theme.OnPrimary
import valentine.toykids.londres.ui.theme.OnSurface
import valentine.toykids.londres.ui.theme.Primary
import valentine.toykids.londres.ui.theme.Surface

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val supportUrl = stringResource(R.string.gurkm_customer_support_link)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Text(
            text = stringResource(R.string.gurkm_top_bar_settings_title),
            fontFamily = HeadingFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            color = OnSurface
        )
        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "About",
            color = Muted,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Surface)
        ) {
            SettingsRow(
                label = stringResource(R.string.gurkm_settings_screen_company_label),
                value = stringResource(R.string.gurkm_company_name)
            )
            HorizontalDivider(color = Border, thickness = 0.5.dp)
            SettingsRow(
                label = stringResource(R.string.gurkm_settings_screen_version_label),
                value = stringResource(R.string.gurkm_app_version)
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Support",
            color = Muted,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(supportUrl))
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = OnPrimary
            )
        ) {
            Text(
                text = stringResource(R.string.gurkm_settings_screen_customer_support_label),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
private fun SettingsRow(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Text(
            text = label,
            color = Muted,
            style = MaterialTheme.typography.labelSmall
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            color = OnSurface,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}
