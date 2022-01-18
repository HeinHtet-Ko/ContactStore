package com.mtu.ceit.hhk.contactstore

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.provider.ContactsContract
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp
import com.mtu.ceit.hhk.contactstore.ui.theme.*
import kotlin.math.min

@ExperimentalFoundationApi
@Composable
fun ContactDetail() {

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

            }

            Box(modifier = Modifier.
            fillMaxSize()
            ,
            contentAlignment = Alignment.BottomStart){
                Text(text = "Hein Htet Ko", fontSize = 20.sp , color = WhiteGray ,
                    modifier = Modifier.padding(10.dp),
                    fontFamily = FontFamily.Serif
                )
            }
            
        }

     Column {
         PhoneCard()
         val list = listOf("Delete","Favourite","Edit","Upload")
         LazyVerticalGrid(cells = GridCells.Fixed(2),
             modifier = Modifier
                 .fillMaxWidth()
                 .height(300.dp),
             contentPadding = PaddingValues(15.dp),

             ){
             items(list.size){
                 CustCard()
             }
         }



         FloatingButton()

     }


    }
}

@Composable
fun FloatingButton() {
    var isFavourite by remember {
        mutableStateOf(false)
    }

    Card (elevation = 10.dp) {
        FloatingActionButton(onClick = {
            isFavourite = !isFavourite
        },
            modifier = Modifier.size(40.dp),
            backgroundColor = MaterialTheme.colors.onBackground) {

            Icon(painter = painterResource(id = R.drawable.ic_delete)
                , contentDescription = null,
                tint =  if (isFavourite) Color.Red else Primary)

        }
    }


}

@Composable
fun PhoneCard() {



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

            Icon(painter = painterResource(id = R.drawable.ic_call)
                , contentDescription = "call icon",
                tint = Primary,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column (horizontalAlignment = Alignment.Start){
                Text(text = "09 770108404")
                Text(text = "Mobile")
            }


        }
    }


}

@Composable
fun CustCard() {




    Card(modifier = Modifier
        .padding(10.dp)
        .clickable {

        }
        .height(80.dp), elevation = 15.dp
       ,
       backgroundColor = MaterialTheme.colors.surface

       ) {
               Column(
                   horizontalAlignment = Alignment.CenterHorizontally
               ){
                   Icon(painter = painterResource(id = R.drawable.ic_delete)
                       , contentDescription = "delete icon",
                   tint = Primary )

                   Text(text = "Delete", color = MaterialTheme.colors.onSurface)

               }
   }

}

@ExperimentalFoundationApi
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PrevDetail(){
    ContactDetail()

}