package com.mtu.ceit.hhk.contactstore

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.mtu.ceit.hhk.contactstore.ui.theme.w
import kotlin.math.roundToInt


@ExperimentalMaterialApi
@Composable
fun ContactListItem(item: ContactItem,isSelecting:Boolean,selectedList:MutableList<ContactItem>) {

     var checked by remember {
         mutableStateOf(false)
     }


    val swSt = rememberSwipeableState(initialValue = 0)
    val siz = with(LocalDensity.current){
        100.dp.toPx()
    }
    Box (modifier = Modifier

        .fillMaxWidth()
        .swipeable(
            state = swSt,
            orientation = Orientation.Horizontal,
            thresholds = { _, _ ->
                FractionalThreshold(0.3f)
            }, anchors = mapOf(0f to 0, siz to 1, -siz to 2)
        )
        .clickable {

        }
        .height(100.dp)
        .padding(20.dp, 5.dp)
        .background(w)
        ,
        contentAlignment = Alignment.CenterStart
        ){

        Row {
            
            Spacer(modifier = Modifier.width(30.dp))
            
            Image(

                painter = painterResource(id = R.drawable.call)
                , modifier = Modifier

                    .size(50.dp)
                    .clickable {

                    }
                , contentDescription = "onswipe")
        }

    

        Row(
            Modifier
                .align(Alignment.CenterStart)
                .fillMaxWidth()
                .offset { IntOffset(swSt.offset.value.roundToInt(), 0) }
                .background(MaterialTheme.colors.background)
        , verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = R.drawable.avatar) ,
                contentDescription = "avatar logo",
                modifier = Modifier
                    .padding(20.dp, 0.dp)
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(PaddingValues(30.dp, 3.dp)),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = item.name, fontSize = 18.sp
                    , fontFamily = FontFamily(Font(R.font.mukutamedium)))
                Text(text = item.ph_no,
                    fontSize = 13.sp)
            }
        }





            if(isSelecting)
                Checkbox(checked =  if(selectedList.contains(item)) true else checked , onCheckedChange = {
                    checked = !checked
                    if (it) selectedList.add(item)
                    Log.d("selectedlist", "ContactListItem: ${selectedList.size}")
                })



    }

}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun Prev() {
    ContactListItem(item = ContactItem("George","097770109404"), isSelecting = false, selectedList = mutableListOf() )
}

