package valentine.toykids.londres

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import valentine.toykids.londres.ui.composable.approot.AppRoot
import valentine.toykids.londres.ui.theme.ProductAppGURKMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductAppGURKMTheme {
                AppRoot()
            }
        }
    }
}