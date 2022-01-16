package com.mtu.ceit.hhk.contactstore

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mtu.ceit.hhk.contactstore.ui.theme.CardNight


@ExperimentalFoundationApi
@Composable
fun MainScreen() {

    val titleList = remember {
        listOf("View Online Store","View Local Store","Download ","Upload")
    }
    
    Scaffold(
        topBar = { 
            CustomTopAppBar()
        },
        
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            LazyVerticalGrid(cells = GridCells.Fixed(2)
                , contentPadding = PaddingValues(10.dp,20.dp)) {


                items(4){ it ->
                    MainActionCard(title = titleList[it] )
                }

            }

        }
    }

  


}

@Composable
fun CustomTopAppBar() {

    TopAppBar(
        title = {
        Text(text = "Contact Store", fontSize = 25.sp)},
    navigationIcon = {
         IconButton(onClick = { }) {
             Icon(painter = painterResource(id = R.drawable.ic_id),
                 contentDescription = "haha" , modifier = Modifier.size(70.dp))
         }
    },
    backgroundColor = CardNight,
    contentColor = Color.Green,
    elevation = 4.dp)

}

@Composable
fun MainActionCard(title:String){

    Card(
        modifier = Modifier
            .padding(12.dp)
            .clickable {

            }
            .size(120.dp)
            .clip(RoundedCornerShape(7.dp)),
        elevation = 7.dp
    ) {
        Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()

            ) {

            Icon(painter = painterResource(id = R.drawable.ic_cloud),
                contentDescription = null,
            modifier = Modifier.padding(10.dp))
            Text(text = title,modifier = Modifier.padding(10.dp))

        }
    }


}


@ExperimentalFoundationApi
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PrevMain(){

    MainScreen()

}