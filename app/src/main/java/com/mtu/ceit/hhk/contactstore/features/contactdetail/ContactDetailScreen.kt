package com.mtu.ceit.hhk.contactstore

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alexstyl.contactstore.Label
import com.mtu.ceit.hhk.contactstore.domain.models.ContactDetail
import com.mtu.ceit.hhk.contactstore.features.contactdetail.ContactDetailViewModel
import com.mtu.ceit.hhk.contactstore.features.contactlist.LocalContactListViewModel
import com.mtu.ceit.hhk.contactstore.features.convertUriToBitmap
import com.mtu.ceit.hhk.contactstore.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.math.max
import kotlin.math.min

@ExperimentalFoundationApi
@Composable
fun ContactDetail(navController: NavController,contactID:Long,contactVM:ContactDetailViewModel = hiltViewModel()) {



//    LaunchedEffect(key1 = Unit){
//        contactVM.fetchContactDetail(contactID)
//    }

    val contactDetail = remember {
        contactVM.fetchContactDetail(contactID)
        contactVM.contactDetail.value!!
    }
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



    val alp = maxOf(0f,(1-(scst.value)/500f))


    Box {
        ContactImage(imgByteArray = contactVM.contactDetail.value!!.imgData,alpha = alp )

        Box(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scst)
            , contentAlignment = Alignment.Center ) {
            Column(modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
                .padding(7.dp, 330.dp, 7.dp, 20.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(DarkVariantOne)
                .padding(0.dp, 20.dp, 10.dp, 120.dp)

            ) {

                Text(
                    modifier = Modifier
                        .graphicsLayer {
                            alpha = alp
                        }
                        .fillMaxWidth()
                        .padding(30.dp, 5.dp, 5.dp, 20.dp),
                    text = contactDetail.displayName ?: " ",
                    color = WhiteVariant,
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif
                )

                contactDetail.phones.map {
                    PhoneCard(R.drawable.ic_call,it.value,it.label)
                }
                contactDetail.mails?.map {
                    PhoneCard(R.drawable.ic_mail,it.value,it.label)
                }
                Spacer(modifier = Modifier.height(500.dp))
            }

        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter ){

            TopActionBar(onArrowPressed,contactDetail.displayName ?: "" , alp)

        }


    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(30.dp),
        contentAlignment = Alignment.BottomEnd,) {

        var animaSt by remember {
            mutableStateOf(true)
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
               // FloatingButton(R.drawable.ic_delete, RedVariant)
            }
        }
        

        
    }
}


@Composable
fun ContactImage(imgByteArray: ByteArray?,alpha:Float) {

    val context = LocalContext.current

    var imageUri:Uri? by remember {
        mutableStateOf(null)
    }

    var imageBitmap:Bitmap by remember {
        val bm = if(imgByteArray == null){
            ResourcesCompat.getDrawable(context.resources,R.drawable.ic_id,context.theme)!!.toBitmap()

        } else
        {

            BitmapFactory.decodeByteArray(imgByteArray,0,imgByteArray.size)
        }

        mutableStateOf(bm)
    }
    
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){ uri:Uri? ->

        imageUri = uri

    }
    
    SideEffect {
        imageUri?.let {
            imageBitmap = convertUriToBitmap(it,context)
        }

    }

    

    if(imgByteArray != null){
        Image(

            bitmap = imageBitmap.asImageBitmap(),
            contentScale = ContentScale.Crop
            ,contentDescription = "logo",
            modifier = Modifier
                .graphicsLayer {
                    this.alpha = alpha
                }
                .background(Primary)
                .fillMaxWidth()
                .height(400.dp)
                .clickable {
                    launcher.launch("image/*")
                }
        )
    }else{
        Image(

            //  bitmap = imageBitmap.asImageBitmap(),

            painter = painterResource(id = R.drawable.ic_id),
            contentScale = ContentScale.Crop
            ,contentDescription = "logo",
            modifier = Modifier
                .graphicsLayer {
                    this.alpha = alpha
                }
                .background(Primary)
                .fillMaxWidth()
                .height(400.dp)
                .clickable {
                    launcher.launch("image/*")
                }
        )
    }




}

@Composable
fun TopActionBar(onArrowPressed:()->Unit,name:String,alpha: Float) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .padding(0.dp, 0.dp, 15.dp, 10.dp),
        contentAlignment = Alignment.TopEnd){

        Box(modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                this.alpha = 1f - alpha
            }
            .background(DarkVariantOne),
            contentAlignment = Alignment.Center) {
            Text(
                text = name,
                fontSize = 20.sp ,
                modifier = Modifier.padding(10.dp),
                fontFamily = FontFamily.Serif)
        }

        Box(modifier = Modifier
            .alpha(alpha)
            .fillMaxSize(),
            contentAlignment = Alignment.TopStart) {
            IconButton(onClick = {
                onArrowPressed.invoke()
            }, modifier = Modifier
            ) {
                Icon(contentDescription = null,
                    modifier = Modifier.size(30.dp), imageVector = Icons.Filled.ArrowBack )
            }
        }
        Row(modifier = Modifier.alpha(alpha))
        {
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
                Text(text = rawValue, style = MaterialTheme.typography.h6)
                Text(text = labelStr, style = MaterialTheme.typography.subtitle2)
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