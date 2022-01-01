package com.mtu.ceit.hhk.contactstore

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet

import com.mtu.ceit.hhk.contactstore.ui.theme.ContactStoreTheme
import com.mtu.ceit.hhk.contactstore.ui.theme.Primary
import com.mtu.ceit.hhk.contactstore.ui.theme.Purple200

class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactStoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                   SplashConstraint()
                }
            }
        }
    }
}



@ExperimentalAnimationApi
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    ContactStoreTheme {
    SplashConstraint()
    }
}

@Composable
fun SplashScreen() {
    var targetDp by remember {
        mutableStateOf(0)
    }

    var targetAlpha by remember {
        mutableStateOf(0f)
    }

    val animateTargetDp by animateDpAsState(
        targetValue = targetDp.dp,
    tween(2000))

    val animateTargetFloat by animateFloatAsState(
        targetValue = targetAlpha,
    tween(3200))

    LaunchedEffect(key1 = Unit){
        targetDp = 300
        targetAlpha = 1f
    }



    Box(modifier = Modifier.fillMaxSize()
    ,
        contentAlignment = Alignment.Center
        ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
          , modifier = Modifier.padding(0.dp,0.dp,0.dp,animateTargetDp)
        ) {
            Image(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "LOGO")
            Spacer(modifier = Modifier.height(30.dp))


        }
        Text(text = "Contact Store",modifier = Modifier.alpha(animateTargetFloat))

        
    }

}

@ExperimentalAnimationApi
@Composable
fun SplashConstraint() {

    var targetAlpha by remember {
        mutableStateOf(0f)
    }
    val animateAlpha by animateFloatAsState(targetValue = targetAlpha, tween(2500))

    var targetLogo by remember {
        mutableStateOf(0.575f)
    }

    val animateLogo by animateFloatAsState(targetValue = targetLogo, tween(2500))

    var targetSub by remember {

        mutableStateOf(0.1f)

    }

    val animateSub by animateFloatAsState(targetValue = targetSub , tween(2500))




   val constraints = ConstraintSet {
       val logo = createRefFor("logo")
       val logoGuide = createGuidelineFromTop(animateLogo)
       val title = createRefFor("title")
       val titleGuide = createGuidelineFromTop(0.6f)
       val subtitle = createRefFor("subtitle")
       val subtitleGuide = createGuidelineFromBottom(0.125f)
       val subVerGuide = createGuidelineFromStart(animateSub)

       constrain(logo){

           start.linkTo(parent.start)
           end.linkTo(parent.end)
           bottom.linkTo(logoGuide)
       }
       constrain(title){
           start.linkTo(parent.start)
           end.linkTo(parent.end)
           bottom.linkTo(titleGuide)
       }
       constrain(subtitle){

         //  end.linkTo(subVerGuide)
           centerAround(subVerGuide)
           bottom.linkTo(subtitleGuide)
       }

   }

    ConstraintLayout(constraintSet = constraints,
    modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.logo)
            , contentDescription = "Contact Logo",
          modifier = Modifier
              .layoutId("logo")
              .alpha(animateAlpha),
        )

        Text(text = "Contact Store",
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .layoutId("title")
                .alpha(animateAlpha),
            fontFamily = FontFamily(Font(R.font.sourceserif)),
            fontStyle = FontStyle.Normal,
            fontSize = 30.sp)



        Text(text = "Lost Your Contact No More",
            color = Primary,
            fontFamily = FontFamily(Font(R.font.sourceserifitalic)),
            modifier = Modifier.layoutId("subtitle").alpha(animateAlpha))



    }
    LaunchedEffect(key1 = Unit){

        targetAlpha = 1f
        targetLogo = 0.5f
        targetSub = 0.5f
    }



}