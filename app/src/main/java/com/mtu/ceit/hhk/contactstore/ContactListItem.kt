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
import com.mtu.ceit.hhk.contactstore.domain.Contact
import com.mtu.ceit.hhk.contactstore.ui.theme.w
import kotlin.math.roundToInt


@ExperimentalMaterialApi
@Composable
fun ContactListItem(item: Contact) {

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
                , contentDescription = "call icon")
        }

    

        Row(
            Modifier
                .align(Alignment.CenterStart)
                .fillMaxWidth()
                .fillMaxHeight()
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

            Text(text = item.name ?: " ", fontSize = 18.sp
                , fontFamily = FontFamily(Font(R.font.mukutamedium)))

        }


    }

}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun Prev() {
   // ContactListItem(item = ContactItem("George","097770109404"), isSelecting = false, selectedList = mutableListOf() )
}

