package com.mtu.ceit.hhk.contactstore

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import com.mtu.ceit.hhk.contactstore.ui.theme.Primary

@ExperimentalAnimationApi
@Composable
fun SplashScreen(navController: NavController) {

    var targetAlpha by remember {
        mutableStateOf(0f)
    }
    val animateAlpha by animateFloatAsState(targetValue = targetAlpha, tween(2000))

    var targetLogo by remember {
        mutableStateOf(0.575f)
    }

    val animateLogo by animateFloatAsState(targetValue = targetLogo, tween(2000),
    finishedListener = {
        navController.popBackStack()
        navController.navigate(Screen.Home.route)
    })

    var targetSub by remember {

        mutableStateOf(0.1f)

    }

    val animateSub by animateFloatAsState(targetValue = targetSub, tween(2500))


    val constraints = ConstraintSet {
        val logo = createRefFor("logo")
        val logoGuide = createGuidelineFromTop(animateLogo)
        val title = createRefFor("title")
        val titleGuide = createGuidelineFromTop(0.6f)
        val subtitle = createRefFor("subtitle")
        val subtitleGuide = createGuidelineFromBottom(0.125f)
        val subVerGuide = createGuidelineFromStart(animateSub)

        constrain(logo) {

            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(logoGuide)
        }
        constrain(title) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(titleGuide)
        }
        constrain(subtitle) {

            centerAround(subVerGuide)
            bottom.linkTo(subtitleGuide)
        }

    }

    ConstraintLayout(
        constraintSet = constraints,
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Contact Logo",
            modifier = Modifier
                .layoutId("logo")
                .alpha(animateAlpha).size(75.dp),
        )

        Text(
            text = "Contact Store",
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .layoutId("title")
                .alpha(animateAlpha),
            fontFamily = FontFamily(Font(R.font.sourceserif)),
            fontStyle = FontStyle.Normal,
            fontSize = 30.sp
        )



        Text(
            text = "Lost Your Contact No More",
            color = Primary,
            fontFamily = FontFamily(Font(R.font.sourceserifitalic)),
            modifier = Modifier.layoutId("subtitle").alpha(animateAlpha)
        )


    }
    LaunchedEffect(key1 = Unit) {

        targetAlpha = 1f
        targetLogo = 0.5f
        targetSub = 0.5f
    }
}

