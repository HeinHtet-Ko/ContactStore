package com.mtu.ceit.hhk.contactstore
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.mtu.ceit.hhk.contactstore.domain.Contact
import com.mtu.ceit.hhk.contactstore.features.contactlist.ContactListItem
import com.mtu.ceit.hhk.contactstore.features.contactlist.LocalContactListViewModel


@SuppressLint("MutableCollectionMutableState")
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ContactList(contactVM: LocalContactListViewModel = hiltViewModel()) {

    val perState = rememberMultiplePermissionsState(permissions = listOf(
        android.Manifest.permission.WRITE_CONTACTS,
        android.Manifest.permission.READ_CONTACTS,
        android.Manifest.permission.CALL_PHONE
    ))


    var selectedList by remember {
        mutableStateOf(mutableListOf<Contact>())
    }
    var isSelecting by remember {
        mutableStateOf(false)
    }

    val openSelect = {
        isSelecting = true
    }

    val closeSelect:()->Unit = {
        isSelecting = false
        selectedList.clear()
    }



    val listC = contactVM.contactList.collectAsState(initial = emptyList())



   // val count = listC.value.find { it.name == "HHK" }
    Log.d("contactheader", "ContactList: ${selectedList} ")

    val toggleList = { contact:Contact ->

        val tempList = mutableListOf<Contact>()
        tempList.addAll(selectedList)
       // val index = listC.value.indexOf(contact)
       // val updated = contact.copy(name = "HHK")
       // listC.value.toMutableList().add(index,updated)
        Log.d("contactheader", "ContactList: invoked")
        if(!tempList.contains(contact)){
            tempList.add(contact)
        }

        else{
            tempList.remove(contact)
    }

        selectedList = tempList
      //  selectedList = listC.value

       // listC.value = listC.value.toMutableList()[index] = updated

    }

//    val i = Intent(Intent.ACTION_CALL)
//    i.data = Uri.parse("tel:09770109404")



    LaunchedEffect(Unit) {
        perState.launchMultiplePermissionRequest()
        if( perState.allPermissionsGranted) {
            contactVM.getContacts()
        }
    }

   // val listState = rememberLazyListState()



   // val offs = min(1f,1-(listState.firstVisibleItemScrollOffset/500f + listState.firstVisibleItemIndex))

    Column(modifier = Modifier.fillMaxSize()) {

//        val animateScrollState by animateDpAsState(targetValue = max(75.dp,150.dp*offs))
//        val animateFloat by animateFloatAsState(targetValue = kotlin.math.max(1f,10*offs))

       // Log.d("contactheader", "Contact: $isSelecting")
        ContactHeader(title = "Contact List",isSelecting,openSelect,selectedList)
        SelectContactHeader(closeSelect)
        LazyColumn(){


            items(items = listC.value , key = {
                it.id
            }){ contact:Contact ->
                ContactListItem(contact,isSelecting,selectedList,toggleList)
            }
        }
    }

}

@Composable
fun SelectContactHeader(closeSelect:()->Unit) {
    Row(Modifier.fillMaxWidth()
                .height(100.dp)
            ,verticalAlignment = Alignment.CenterVertically
            ) {
            // Spacer(modifier = Modifier.width(30.dp))

            IconButton(onClick = {
                    closeSelect.invoke()
            }) {
                Icon(painter = painterResource(id = R.drawable.ic_close)
                    , contentDescription = null
                    , tint = Color.Green
                    ,modifier = Modifier.size(33.dp))
            }

        Text(text = "Item Selected", fontSize = 20.sp,
            color = Color.Yellow,
            fontFamily = FontFamily(Font(R.font.sourceserif)),
        )
        IconButton(onClick = {

        }) {
            Icon(painter = painterResource(id = R.drawable.ic_selectall)
                , contentDescription = null
                , tint = Color.Green
                ,modifier = Modifier.size(33.dp))
        }
        }


        }

    
@Composable
fun ContactHeader(title:String,isSelecting:Boolean,toggleSe:()->Unit,list:List<Contact>) {

    var count = list.size
   // Log.d("contactheader", "ContactHeader: ${count.size}")

    var text =
        if(isSelecting) " ${count} items " else " Contact List "

    Row(

        Modifier
            .fillMaxWidth()
            .height(100.dp)
        ,verticalAlignment = Alignment.CenterVertically
        , horizontalArrangement = Arrangement.SpaceAround) {
       // Spacer(modifier = Modifier.width(30.dp))
        Text(text = text, fontSize = 25.sp,
            color = Color.Yellow
            ,
            fontFamily = FontFamily(Font(R.font.sourceserif)),
            )
        IconButton(onClick = {
            toggleSe.invoke()
        }) {
            Icon(painter = painterResource(id = R.drawable.ic_select)
                , contentDescription = null
                , tint = Color.Green
                ,modifier = Modifier.size(33.dp))
        }
    }

}


@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@RequiresApi(Build.VERSION_CODES.N)
@Composable
@Preview(showBackground = true)
fun prev() {
    ContactList()
}