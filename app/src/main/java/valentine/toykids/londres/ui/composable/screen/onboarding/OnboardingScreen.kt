package valentine.toykids.londres.ui.composable.screen.onboarding

import androidx.annotation.StringRes
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import valentine.toykids.londres.R
import valentine.toykids.londres.ui.theme.Background
import valentine.toykids.londres.ui.theme.Border
import valentine.toykids.londres.ui.theme.HeadingFamily
import valentine.toykids.londres.ui.theme.Muted
import valentine.toykids.londres.ui.theme.OnPrimary
import valentine.toykids.londres.ui.theme.OnSurface
import valentine.toykids.londres.ui.theme.Primary
import valentine.toykids.londres.ui.theme.Surface
import valentine.toykids.londres.ui.viewmodel.GURKMOnboardingVM
import org.koin.androidx.compose.koinViewModel

data class OnboardingContent(
    @field:StringRes val titleRes: Int,
    @field:StringRes val descriptionRes: Int,
    val imageUrl: String,
)

private val onboardingPagesContent = listOf(
    OnboardingContent(
        titleRes = R.string.gurkm_page_1_title,
        descriptionRes = R.string.gurkm_page_1_description,
        imageUrl = "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=800",
    ),
    OnboardingContent(
        titleRes = R.string.gurkm_page_2_title,
        descriptionRes = R.string.gurkm_page_2_description,
        imageUrl = "https://images.unsplash.com/photo-1503454537195-1dcabb73ffb9?w=800",
    ),
    OnboardingContent(
        titleRes = R.string.gurkm_page_3_title,
        descriptionRes = R.string.gurkm_page_3_description,
        imageUrl = "https://images.unsplash.com/photo-1516981879613-9f5da904015f?w=800",
    ),
)

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    viewModel: GURKMOnboardingVM = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit,
) {
    val onboardingSetState by viewModel.onboardingSetState.collectAsState()

    LaunchedEffect(onboardingSetState) {
        if (onboardingSetState) {
            onNavigateToHomeScreen()
        }
    }

    val pagerState = rememberPagerState(pageCount = { onboardingPagesContent.size })
    val scope = rememberCoroutineScope()

    Box(modifier = modifier.fillMaxSize().background(Background)) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            OnboardingPage(content = onboardingPagesContent[page])
        }

        TextButton(
            onClick = { viewModel.setOnboarded() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 48.dp, end = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.gurkm_skip_button_title),
                color = OnSurface.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(onboardingPagesContent.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(
                                width = if (index == pagerState.currentPage) 24.dp else 8.dp,
                                height = 8.dp
                            )
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                if (index == pagerState.currentPage) Primary else Border
                            )
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    if (pagerState.currentPage < onboardingPagesContent.size - 1) {
                        scope.launch {
                            pagerState.animateScrollToPage(
                                pagerState.currentPage + 1,
                                animationSpec = tween(400)
                            )
                        }
                    } else {
                        viewModel.setOnboarded()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    contentColor = OnPrimary
                )
            ) {
                Text(
                    text = if (pagerState.currentPage < onboardingPagesContent.size - 1)
                        stringResource(R.string.gurkm_next_button_title)
                    else
                        stringResource(R.string.gurkm_start_button_title),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun OnboardingPage(content: OnboardingContent) {
    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = content.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.0f to Surface.copy(alpha = 0.95f),
                            0.65f to Surface.copy(alpha = 0.80f),
                            1.0f to Surface.copy(alpha = 0f)
                        )
                    )
                )
                .padding(horizontal = 28.dp, vertical = 80.dp)
        ) {
            Column {
                Text(
                    text = stringResource(content.titleRes),
                    fontFamily = HeadingFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    color = OnSurface,
                    lineHeight = 32.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(content.descriptionRes),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Muted,
                    fontSize = 15.sp,
                    lineHeight = 22.sp
                )
            }
        }
    }
}
