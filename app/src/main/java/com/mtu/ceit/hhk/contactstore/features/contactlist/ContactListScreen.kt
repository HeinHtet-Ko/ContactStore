package com.mtu.ceit.hhk.contactstore.features.contactlist
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.mtu.ceit.hhk.contactstore.FloatingButton
import com.mtu.ceit.hhk.contactstore.R
import com.mtu.ceit.hhk.contactstore.Screen
import com.mtu.ceit.hhk.contactstore.domain.models.Contact
import com.mtu.ceit.hhk.contactstore.ui.theme.GreenVariant
import com.mtu.ceit.hhk.contactstore.ui.theme.RedVariant


@SuppressLint("MutableCollectionMutableState")
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ContactList(contactVM: LocalContactListViewModel = hiltViewModel(),navController: NavController) {




    val perState = rememberMultiplePermissionsState(permissions = listOf(
        android.Manifest.permission.WRITE_CONTACTS,
        android.Manifest.permission.READ_CONTACTS,
        android.Manifest.permission.CALL_PHONE
    ))
    val listC = contactVM.contactList.collectAsState(initial = emptyList())

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

     val clickItem:(Contact)->Unit = remember {
         {
             contact: Contact ->
             contactVM.getContactDetail(contact.id)
             Log.d("contactidnavigate", "ContactList: ${contact.id} is")
             navController.navigate(Screen.ContactDetailScreen.createRoute(contact.id))
         }
    }



   val addAll = {
       val tempList = mutableListOf<Contact>()
       tempList.addAll(listC.value)
       selectedList = tempList
   }



   // val count = listC.value.find { it.name == "HHK" }
    Log.d("contactheader", "ContactList: ${selectedList} ")

    val toggleList = { contact: Contact ->

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

    val listState = rememberLazyListState()

    var isScrolling = !listState.isScrollInProgress

    var isShowing by remember {
        mutableStateOf(true)
    }


    val tempIndex = listState.firstVisibleItemIndex - listState.firstVisibleItemIndex
    var pair = mutableListOf(0,listState.firstVisibleItemIndex)
    isShowing = pair[0] >= pair[1]
    pair[0] = pair[1]
  //  pair[1] = listState.firstVisibleItemIndex
//    if(tempIndex>listState.firstVisibleItemIndex)
//    val isHideFloating = if()

    Log.d("liststatetracker", "ContactList: ${pair}")
   // val offs = min(1f,1-(listState.firstVisibleItemScrollOffset/500f + listState.firstVisibleItemIndex))

    Column(modifier = Modifier.fillMaxSize()) {

//        val animateScrollState by animateDpAsState(targetValue = max(75.dp,150.dp*offs))
//        val animateFloat by animateFloatAsState(targetValue = kotlin.math.max(1f,10*offs))

       // Log.d("contactheader", "Contact: $isSelecting")

        AnimatedVisibility(visible = isSelecting) {
            SelectContactHeader(selectedList,closeSelect,addAll,isSelecting)
        }
        AnimatedVisibility(visible = !isSelecting) {
            ContactHeader(title = "Contact List",isSelecting,openSelect,selectedList)
        }

        Box() {
            LazyColumn(modifier = if(isSelecting)Modifier.padding(0.dp,0.dp,0.dp,80.dp)else Modifier
            , state = listState){
                items(items = listC.value , key = {
                    it.id
                }){ contact: Contact ->
                    ContactListItem(contact,isSelecting,selectedList,toggleList){
                        clickItem.invoke(contact)
                    }
                }
            }

         
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

                    Column {
                        AnimatedVisibility(visible = isSelecting) {
                            BottomActionBar()
                        }
                    }




            }
            Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier
                .fillMaxSize()
                .padding(25.dp)) {

                Column {
                    AnimatedVisibility(visible = isShowing && !isSelecting,
                        enter = fadeIn() + slideIn(
                            initialOffset = { IntOffset(it.height,1200) },
                            animationSpec = (tween(500))
                        ),
                        exit = fadeOut() + slideOut(
                            targetOffset = { IntOffset(it.height,1200) },
                            animationSpec = (tween(500))
                        )
                    ) {
                        FloatingActionButton(onClick = {  }, backgroundColor = GreenVariant) {

                            Icon(painter = painterResource(id = R.drawable.ic_addcontact), contentDescription = null)

                        }
                    }
                }




            }

        }





    }

}

@Composable
fun BottomActionBar() {

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .background(MaterialTheme.colors.surface)
        .padding(35.dp, 7.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable {

        }){
            Icon(painter = painterResource(id = R.drawable.ic_delete), contentDescription = "delete contacts"
            , tint = RedVariant)
            Text(text = "Delete", fontSize = 12.sp)
        }
       
        Spacer(modifier = Modifier.width(70.dp))

            Column (horizontalAlignment = Alignment.CenterHorizontally) {

                  //  IconButton(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource(id = R.drawable.ic_upload), modifier = Modifier.clickable {  }, contentDescription = "upload contacts",
                            tint = Color.Green)
                   // }


                Text(text = "Upload", fontSize = 12.sp)
            }


       
    }

}

@Composable
fun SelectContactHeader(selectedList:List<Contact>, closeSelect:()->Unit, addAll:()->Unit, isSelecting: Boolean) {
    Row(
        Modifier
            .fillMaxWidth()
            .alpha(if (isSelecting) 1f else 0f)
            .background(MaterialTheme.colors.surface)
            .height(90.dp)
            ,verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
            ) {
            // Spacer(modifier = Modifier.width(30.dp))

            IconButton(onClick = {
                    closeSelect.invoke()
            }) {
                Icon(painter = painterResource(id = R.drawable.ic_close)
                    , contentDescription = null
                    , tint = RedVariant
                    ,modifier = Modifier.size(33.dp))
            }

        Text(text = "${selectedList.size} Item Selected", fontSize = 20.sp,
            color = MaterialTheme.colors.primaryVariant,
            fontFamily = FontFamily(Font(R.font.sourceserif)),
        )
        IconButton(onClick = {
            addAll.invoke()
        }) {
            Icon(painter = painterResource(id = R.drawable.ic_selectall)
                , contentDescription = null
                , tint = GreenVariant
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
            .alpha(if (isSelecting) 0f else 1f)
            .background(MaterialTheme.colors.surface)
            .height(100.dp)
        ,verticalAlignment = Alignment.CenterVertically
        , horizontalArrangement = Arrangement.SpaceAround) {
       // Spacer(modifier = Modifier.width(30.dp))
        Text(text = text, fontSize = 25.sp,
            color = MaterialTheme.colors.primaryVariant
            ,
            fontFamily = FontFamily(Font(R.font.sourceserif)),
            )
        IconButton(onClick = {
            toggleSe.invoke()
        }) {
            Icon(painter = painterResource(id = R.drawable.ic_select)
                , contentDescription = null
                , tint = GreenVariant
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
    //ContactList()
}