package com.mtu.ceit.hhk.contactstore

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {

    var isSelecting by remember {
        mutableStateOf(false)
    }
    var selectedList by remember {
        mutableStateOf(mutableListOf<ContactItem>())
    }
    Log.d("selectedList", "HomeScreen: ${selectedList.size}")
    val clist = remember {
        val list:MutableList<ContactItem> = mutableListOf()
        repeat(15){
            list.add(ContactItem("Hein Htet Ko","09 770109404"))
        }
        list
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Text(text = "Contacts", fontSize = 25.sp,
            fontFamily = FontFamily(Font(R.font.sourceserif)),
            modifier = Modifier
                .padding(10.dp, 20.dp)
                .clickable {
                    isSelecting = !isSelecting
                }
        )

        LazyColumn{
            items(clist){ contact:ContactItem ->

                ContactListItem(contact,isSelecting,selectedList)


            }
        }
    }




}

@Composable
@Preview(showBackground = true)
fun prev() {
    HomeScreen()
}