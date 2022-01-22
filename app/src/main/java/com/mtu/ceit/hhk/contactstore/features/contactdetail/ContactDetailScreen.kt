package com.mtu.ceit.hhk.contactstore

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.annotation.DrawableRes
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alexstyl.contactstore.Label
import com.mtu.ceit.hhk.contactstore.domain.models.ContactDetail
import com.mtu.ceit.hhk.contactstore.features.contactlist.LocalContactListViewModel
import com.mtu.ceit.hhk.contactstore.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.math.min

@ExperimentalFoundationApi
@Composable
fun ContactDetail(navController: NavController,contactID:Long,contactVM:LocalContactListViewModel = hiltViewModel()) {



    contactVM.getContactDetail(contactID)

    val contactDetail = contactVM.contactDetail.value!!
    val isStarred = remember {
        mutableStateOf(contactDetail.isStarred)
    }
    val colorbyStarred = if(isStarred.value) Color.Yellow  else Color.Unspecified

    val onArrowPressed = remember {
        {
            navController.popBackStack()
            Unit
        }
    }

    val scst = rememberScrollState()
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scst)) {


        Box(modifier = Modifier

            .fillMaxWidth()
            .height(400.dp)
           ){

            Image(
                painter = painterResource(id = R.drawable.ic_id),
                contentScale = ContentScale.Crop
                ,contentDescription = "logo",
                modifier = Modifier
                    .background(Primary)
                    .fillMaxSize()
                   )
            TopActionBar(onArrowPressed)
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            DarkGray
                        ), startY = 650f
                    )
                ),
            contentAlignment = Alignment.BottomStart) {
                Text(text = "${contactDetail.name}", fontSize = 20.sp , color = WhiteGray ,
                    modifier = Modifier.padding(30.dp,15.dp,15.dp,15.dp),
                    fontFamily = FontFamily.Serif
                )
            }
        }

     Column {

         contactDetail.phones.map {
             PhoneCard(R.drawable.ic_call,it.value,it.label)
         }
         contactDetail.mails?.map {
             PhoneCard(R.drawable.ic_mail,it.value,it.label)
         }


     }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(30.dp),
        contentAlignment = Alignment.BottomEnd,) {

        var animaSt by remember {
            mutableStateOf(true)
        }

        LaunchedEffect(key1 = Unit){
//            while (true)
//            {
//                delay(3000)
//                animaSt = !animaSt
//            }
        }

        AnimatedVisibility(visible =animaSt,
        enter = fadeIn()+ slideIn(
            initialOffset = {IntOffset(it.height,1200)},
         animationSpec = (tween(500))
        ),
            exit = fadeOut() +slideOut(
                targetOffset = {IntOffset(it.height,1200)},
                animationSpec = (tween(500))
            )
        ) {
            Row {
                FloatingButton(R.drawable.ic_upload, GreenVariant)
                Spacer(modifier = Modifier.width(10.dp))
                FloatingButton(R.drawable.ic_delete, RedVariant)
            }
        }
        

        
    }
}

@Composable
fun TopActionBar(onArrowPressed:()->Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(70.dp)
        .padding(0.dp, 10.dp, 15.dp, 10.dp),
        contentAlignment = Alignment.TopEnd){
        Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopStart) {
            IconButton(onClick = {
                onArrowPressed.invoke()
            }, modifier = Modifier
            ) {
                Icon(contentDescription = null,
                    modifier = Modifier.size(30.dp), imageVector = Icons.Filled.ArrowBack )
            }
        }
        Row(modifier = Modifier

        ) {

            IconButton(onClick = {
                //  isStarred.value = !isStarred.value
            }, modifier = Modifier) {
                Icon(painter = painterResource(id = R.drawable.ic_edit)
                    , contentDescription = null, modifier = Modifier.size(28.dp) )
            }
            IconButton(onClick = {
               // isStarred.value = !isStarred.value
            }, modifier = Modifier) {
                Icon(painter = painterResource(id = R.drawable.ic_star_rate)
                    , contentDescription = null, modifier = Modifier.size(33.dp) )
            }
        }



    }

}

@Composable
fun FloatingButton(drawableID: Int,color: Color) {

    FloatingActionButton(onClick = {},
        backgroundColor = CardNightVariant ) {

        Icon(painter = painterResource(id = drawableID),
            contentDescription = null, tint = color )

    }



}

@Composable
fun PhoneCard( drawableID:Int, rawValue:String, label:Label) {



    val labelStr:String = remember {
        var name = label::class.simpleName ?: ""
        if(name.contains("Location"))
            name = name.substringAfter("Location")
        else if(name.contains("PhoneNumber"))
            name = name.substringAfter("PhoneNumber")
        else
            name

        name
    }



    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(20.dp, 10.dp)
        ,
        elevation = 10.dp,
        shape = RoundedCornerShape(3.dp),


        ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(25.dp, 5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Icon(painter = painterResource(id = drawableID)
                , contentDescription = "call icon",
                tint = Primary,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column (horizontalAlignment = Alignment.Start){
                Text(text = rawValue)
                Text(text = labelStr)
            }


        }
    }


}




@ExperimentalFoundationApi
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PrevDetail(){
   // ContactDetail()

}