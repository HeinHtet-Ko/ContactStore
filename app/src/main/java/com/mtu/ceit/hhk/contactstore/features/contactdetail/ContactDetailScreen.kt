package com.mtu.ceit.hhk.contactstore.features.contactdetail

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.mtu.ceit.hhk.contactstore.R
import com.mtu.ceit.hhk.contactstore.Screen
import com.mtu.ceit.hhk.contactstore.domain.models.LabeledValue
import com.mtu.ceit.hhk.contactstore.features.convertUriToBitmap
import com.mtu.ceit.hhk.contactstore.features.toStringValue
import com.mtu.ceit.hhk.contactstore.ui.theme.*


@ExperimentalFoundationApi
@Composable
fun ContactDetail(navController: NavController,
                  contactID:Long,
                  contactVM:ContactDetailViewModel = hiltViewModel()) {


    LaunchedEffect(Unit){
        contactVM.fetchContactDetail(contactID)
    }
    //val contactDetail by remember {
     //   mutableStateOf(contactVM.contactDetail.value)
      //  contactVM.contactDetail.value
   // }

//    val colorbyStarred = remember {
//        mutableStateOf(if(contactDetail.isStarred) Color.Yellow  else Color.Unspecified)
//    }

//    LaunchedEffect(key1 = contactVM.contactDetail.value) {
//        colorbyStarred.value =  if(contactVM.contactDetail.value!!.isStarred) Color.Yellow  else Color.Unspecified
//    }


    val onArrowPressed = remember {
        {
            navController.popBackStack()
            Unit
        }
    }

    val onEditClick = remember {
        {
            navController.navigate(Screen.ContactAddScreen.createRoute(contactID))
        }
    }

    val scst = rememberScrollState()
    val alp = maxOf(0f,(1-(scst.value)/500f))


    Box {
        ContactImage(imgByteArray = contactVM.contactDetail.value?.imgData,alpha = alp )

        Box(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scst)
            , contentAlignment = Alignment.Center ) {
            Column(modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
                .padding(7.dp, 330.dp, 7.dp, 20.dp)
                .clip(RoundedCornerShape(10.dp))
                .padding(0.dp, 20.dp, 10.dp, 120.dp)

            ) {

                Text(
                    modifier = Modifier
                        .graphicsLayer {
                            alpha = alp
                        }
                        .fillMaxWidth()
                        .padding(30.dp, 5.dp, 5.dp, 20.dp),
                    text = contactVM.contactDetail.value?.displayName ?:"",
                    color = WhiteVariant,
                    textAlign = TextAlign.Start,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif
                )


               if(!contactVM.contactDetail.value?.phones.isNullOrEmpty()) {
                   ValueCard(iconID = R.drawable.ic_call, iconTint = GreenVariant,contactVM.contactDetail.value?.phones?: emptyList())
               }


                if (!contactVM.contactDetail.value?.mails.isNullOrEmpty())
                {
                    ValueCard(iconID = R.drawable.ic_mail, iconTint = RedVariant, valueList = contactVM.contactDetail.value?.mails!!)
                }

                if(!contactVM.contactDetail.value?.note.isNullOrBlank() or !contactVM.contactDetail.value?.webAddress.isNullOrBlank())
                {
                    MoreInfoCard(
                        displayName = contactVM.contactDetail.value?.displayName ?: "",
                        webAddress = contactVM.contactDetail.value?.webAddress,
                        note = contactVM.contactDetail.value?.note)

                }

                Spacer(modifier = Modifier.height(400.dp))
            }

        }


       TopActionBar(onArrowPressed,contactVM.contactDetail.value?.displayName ?: "" , alp, onStarClick = {
            contactVM.toggleFav(contactID)
        }, onEditClick = onEditClick , starColor = if(contactVM.contactDetail.value?.isStarred == true) Color.Yellow else Color.Unspecified)


    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(30.dp),
        contentAlignment = Alignment.BottomEnd,) {

        FloatingButton(R.drawable.ic_upload, BlueVariant)
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
            ResourcesCompat.getDrawable(context.resources, R.drawable.ic_id,context.theme)!!.toBitmap()

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
                .fillMaxWidth()
                .height(400.dp)
                .clickable {
                    launcher.launch("image/*")
                }
        )
    }




}

@Composable
fun MoreInfoCard(displayName:String,webAddress:String?,note:String?) {

    Card( modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(10.dp, 5.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(3.dp)) {

        Column(
            modifier = Modifier.padding(20.dp,20.dp)
        ){
            Text(
                text = "About $displayName",
                modifier = Modifier.padding(8.dp),
                fontSize = 20.sp)
            Divider()
            Spacer(modifier = Modifier.height(10.dp))
            if(!webAddress.isNullOrBlank()) {
               MoreInfoField(title = "WebAddress", subtitle = webAddress ,
                   subTextColor = Primary, fontStyle = FontStyle.Italic )
            }
            if(!note.isNullOrEmpty()) {
                MoreInfoField(title = "Note", subtitle = note)
            }
        }
    }

}

@Composable
fun MoreInfoField(title:String,subtitle:String,subTextColor:Color?=null,fontStyle: FontStyle?=null){
    Text(
        text = title,
        modifier = Modifier.padding(7.dp),
        fontSize = 18.sp,
       )
    Text(
        text = subtitle,
        fontStyle = fontStyle ?: FontStyle.Normal,
        modifier = Modifier
            .clip(RoundedCornerShape(3.dp))
            .clickable {

            }
            .padding(7.dp),
        fontSize = 15.sp,
        color = subTextColor ?: Color.Unspecified)
}

@Composable
fun TopActionBar(
    onArrowPressed: () -> Unit,
    name: String,
    alpha: Float,
    onEditClick:()->Unit,
    onStarClick: () -> Unit,
    starColor:Color
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter ){

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
                IconButton(onClick = onEditClick
                , modifier = Modifier) {
                    Icon(painter = painterResource(id = R.drawable.ic_edit)
                        , contentDescription = null, modifier = Modifier.size(28.dp) )
                }
                IconButton(onClick = onStarClick, modifier = Modifier) {
                    Icon(painter = painterResource(id = R.drawable.ic_star_rate)
                        , contentDescription = null, modifier = Modifier.size(33.dp),
                        tint = starColor)
                }
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
fun ValueCard( iconID:Int,iconTint:Color,valueList:List<LabeledValue<String>>) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp, 5.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(3.dp)) {


            Row(
                Modifier
                    .fillMaxSize()
                    .padding(25.dp, 20.dp)) {

                Icon(
                    painter = painterResource(id = iconID),
                    contentDescription =null,
                    modifier = Modifier
                        .padding(2.dp, 9.dp)
                        .size(35.dp),
                    tint = iconTint
                )
                Spacer(modifier = Modifier.width(25.dp))
                ValueField(list = valueList)

            }


    }


}

@Composable
fun ValueField(list:List<LabeledValue<String>>) {

    val context = LocalContext.current

    Column {
        list.map {
            Column(modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .clickable {
                    val i = Intent(Intent.ACTION_CALL)
                    i.data = Uri.parse("tel:${it.value.trim()}")
                    context.startActivity(i)
                }
                .padding(7.dp)) {
                Text(text = it.value, style = MaterialTheme.typography.h6, modifier = Modifier)
                Text(text = it.lab.toStringValue(), style = MaterialTheme.typography.subtitle2)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}



