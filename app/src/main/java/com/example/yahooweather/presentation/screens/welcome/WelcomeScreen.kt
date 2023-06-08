import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.renderscript.Allocation
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.yahooweather.R
import com.example.yahooweather.ui.theme.Typography
import com.example.yahooweather.utils.Constants
import com.example.yahooweather.utils.OnBoardingPage
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class, ExperimentalPermissionsApi::class)
@Composable
fun WelcomeScreen(navController: NavController) {
    val pagerState = rememberPagerState()
    val pagerScreens = arrayOf(
        OnBoardingPage.FirstPage(),
        OnBoardingPage.SecondPage()
    )
    var goToWeatherScreen = 0

    val permissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission())
        { wasGranted ->
            goToWeatherScreen = 1

        }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

        HorizontalPager(
            state = pagerState,
            pageCount = pagerScreens.size,
            modifier = Modifier
                .fillMaxSize()

        ) { page: Int ->
            PagerScreen(onBoardPage = pagerScreens[page], pagerState = pagerState)

            if (pagerState.currentPage == 1) {
                LaunchedEffect(key1 = true) {
                    permissionState.launchPermissionRequest()
                }
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            FinishButton(
                pagerState = pagerState,
                onClick = {
                    if (permissionState.status.shouldShowRationale) {
                        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        if (goToWeatherScreen == 1) {
                            navController.popBackStack()
                            navController.navigate(Constants.Screens.MAIN_WEATHER_SCREEN)
                        }
                    } else
                        navController.popBackStack()
                    navController.navigate(Constants.Screens.MAIN_WEATHER_SCREEN)

                },
                modifier = Modifier.padding(12.dp)
            )

            HorizontalPagerIndicator(
                pagerState = pagerState, pageCount = pagerScreens.size, modifier = Modifier
                    .align(
                        Alignment.CenterHorizontally
                    )
                    .padding(bottom = 20.dp)
            )

        }


    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerScreen(onBoardPage: OnBoardingPage, pagerState: PagerState) {
//    val bitmap =
//        BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.cloud_blue_sky_)
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//        if (pagerState.currentPage == 1 && pagerState.currentPageOffsetFraction >= -0.3f) {
//            LegacyBlurImage(bitmap = bitmap, blurRatio = 25f, modifier = Modifier.fillMaxSize())
//        } else {
        Image(
            painter = painterResource(id = onBoardPage.image),
            contentDescription = "Page background",
            contentScale = ContentScale.Crop.also { ContentScale.FillHeight }
        )
    }
    Text(text = onBoardPage.title, style = Typography.bodyLarge)

//    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FinishButton(
    pagerState: PagerState,
    onClick: () -> Unit, modifier: Modifier
) {

    val offset = remember {
        derivedStateOf {
            pagerState.currentPageOffsetFraction
        }
    }

    Row(
        modifier = modifier
            .padding(40.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedVisibility(
            visible = pagerState.currentPage == 1 && offset.value > -0.2,
            enter = fadeIn(tween(1000)) + expandVertically(
                expandFrom = Alignment.Top,
                animationSpec = tween(
                    1500
                )
            ),
            exit = fadeOut(tween(1000)) + shrinkVertically(
                shrinkTowards = Alignment.Bottom,
                animationSpec = tween(
                    1500,
                    easing = EaseOut
                )
            )
        )
        {
            Button(onClick = { onClick() }, modifier = modifier.fillMaxWidth()) {
                Text(text = "Finish", style = Typography.bodyLarge)

            }
        }


    }

}

//@Composable
//fun LegacyBlurImage(
//    bitmap: Bitmap,
//    blurRatio: Float,
//    modifier: Modifier
//) {
//    val renderScript = RenderScript.create(LocalContext.current)
//    val bitmapAlloc = Allocation.createFromBitmap(renderScript, bitmap)
//    ScriptIntrinsicBlur.create(renderScript, bitmapAlloc.element).apply {
//        setRadius(blurRatio)
//        setInput(bitmapAlloc)
//        forEach(bitmapAlloc)
//    }
//    var bluredBitmap=
//    bitmapAlloc.copyTo(bitmap)
//    renderScript.destroy()
//
//     BlurImage(bitmap, modifier)
//
//}
//
//@Composable
//fun BlurImage(bitmap: Bitmap, modifier: Modifier) {
//    Image(
//        bitmap = bitmap.asImageBitmap(),
//        contentDescription = null,
//        contentScale = ContentScale.Crop,
//        modifier = modifier
//    )
//}

