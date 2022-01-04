package com.mtu.ceit.hhk.contactstore

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp


@Composable
fun ContactListItem(item: ContactItem,isSelecting:Boolean,selectedList:MutableList<ContactItem>,scrollOffset:Float) {

     var checked by remember {
         mutableStateOf(false)
     }



    Row (modifier = Modifier
        .fillMaxWidth()
        .clickable {

        }
        .height(100.dp)
        .padding(PaddingValues(20.dp, 10.dp)),
        verticalAlignment = Alignment.CenterVertically){
       Image(painter = painterResource(id = R.drawable.avatar) ,
           contentDescription = "avatar logo",
       modifier = Modifier
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


            if(isSelecting)
                Checkbox(checked =  if(selectedList.contains(item)) true else checked , onCheckedChange = {
                    checked = !checked
                    if (it) selectedList.add(item)
                    Log.d("selectedlist", "ContactListItem: ${selectedList.size}")
                })



    }

}

@Preview(showBackground = true)
@Composable
fun Prev() {
    ContactListItem(item = ContactItem("George","097770109404"), isSelecting = false, selectedList = mutableListOf(),scrollOffset = 0.9f )
}

