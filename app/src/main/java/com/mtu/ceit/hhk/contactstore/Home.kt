package com.mtu.ceit.hhk.contactstore

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import java.lang.Float.min

@ExperimentalMaterialApi
@RequiresApi(Build.VERSION_CODES.N)
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

    val listState = rememberLazyListState()



    val offs = min(1f,1-(listState.firstVisibleItemScrollOffset/600f + listState.firstVisibleItemIndex))

    Column(modifier = Modifier.fillMaxSize()) {

        val animateScrollState by animateDpAsState(targetValue = max(75.dp,150.dp*offs))

        Row(Modifier.fillMaxWidth()
            .background(Color(0xFF62ABEB))
            .height(animateScrollState), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Contacts", fontSize = 25.sp,
                fontFamily = FontFamily(Font(R.font.sourceserif)),
                modifier = Modifier
                    .padding(30.dp, 20.dp)
                    .clickable {
                        isSelecting = !isSelecting

                    }
            )
        }




        Log.d("offsettracking", "HomeScreen: $offs")
        LazyColumn(state = listState){
            items(clist){ contact:ContactItem ->

                ContactListItem(contact,isSelecting,selectedList)


            }
        }
    }




}

@ExperimentalMaterialApi
@RequiresApi(Build.VERSION_CODES.N)
@Composable
@Preview(showBackground = true)
fun prev() {
    HomeScreen()
}