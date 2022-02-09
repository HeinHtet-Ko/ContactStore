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
import com.alexstyl.contactstore.Contact
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.mtu.ceit.hhk.contactstore.R
import com.mtu.ceit.hhk.contactstore.Screen
import com.mtu.ceit.hhk.contactstore.ui.theme.GreenVariant
import com.mtu.ceit.hhk.contactstore.ui.theme.RedVariant


@SuppressLint("MutableCollectionMutableState")
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ContactList(contactVM: LocalContactListViewModel = hiltViewModel(),navController: NavController) {



    val list = contactVM.contactList.value


    var selectedList by remember {
        mutableStateOf(mutableListOf<Contact>())
    }
    var isSelecting by remember {
        mutableStateOf(false)
    }

    val openSelect = remember {
        {
            isSelecting = true
        }

    }

    val closeSelect = remember {

       {
            isSelecting = false
            selectedList.clear()
        }

    }

     val clickItem:(Contact)->Unit = remember {
         {
             contact: Contact ->
             navController.navigate(Screen.ContactDetailScreen.createRoute(contact.contactId))
         }
    }



   val addAll = remember {
       {
           val tempList = mutableListOf<Contact>()
           if(selectedList.size == list.size) {
               tempList.clear()
           }else{
               tempList.addAll(list)
           }
           selectedList = tempList
       }
   }

   val toggleList = { contact: Contact ->

        val tempList = mutableListOf<Contact>()
        tempList.addAll(selectedList)

        if(!tempList.contains(contact)){
            tempList.add(contact)
        }

        else{
            tempList.remove(contact)
    }

        selectedList = tempList


    }



    val listState = rememberLazyListState()


    var isShowing by remember {
        mutableStateOf(true)
    }



    var query by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.fillMaxSize()) {

//        TextField(value = query, onValueChange = {
//            query = it
//            contactVM.onQuChange(it)
//        } )


        AnimatedVisibility(visible = isSelecting) {
            SelectContactHeader(selectedList,closeSelect,addAll,isSelecting)
        }
        AnimatedVisibility(visible = !isSelecting) {
            ContactHeader(title = "Contact List",isSelecting,openSelect,selectedList)
        }

        Box {

            LazyColumn(modifier = if(isSelecting)Modifier.padding(0.dp,0.dp,0.dp,80.dp)else Modifier){


                if(query.isBlank())
                {
                    items(items =  list,
                        key = {
                            it.contactId
                        }){ contact: Contact ->
                        ContactListItem(contact,isSelecting,selectedList,toggleList){
                            clickItem.invoke(contact)
                        }
                    }
                }else{
                    items(items = contactVM.searchResults.value,
                        ){ contact: Contact ->
                        ContactListItem(contact,isSelecting,selectedList,toggleList){
                            clickItem.invoke(contact)
                        }
                    }
                }

            }

         
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

                    Column {
                        AnimatedVisibility(visible = isSelecting) {
                            BottomActionBar(onDeleteItems = {
                                Log.d("selectedtrack", "ContactList: ${selectedList[0].contactId}")
                                contactVM.deleteContacts(selectedList.map { it.contactId })
                                closeSelect()
                            }, onUploadItems = {

                            })
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
                        FloatingActionButton(onClick = {
                                                       navController.navigate(Screen.ContactAddScreen.route)
                        }, backgroundColor = GreenVariant) {

                            Icon(painter = painterResource(id = R.drawable.ic_addcontact), contentDescription = null)

                        }
                    }
                }




            }

        }





    }

}

@Composable
fun BottomActionBar(onDeleteItems:()->(Unit),onUploadItems:()->(Unit) ) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .background(MaterialTheme.colors.surface)
        .padding(35.dp, 7.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        Column (horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable {
                onDeleteItems.invoke()
            }){
            Icon(painter = painterResource(id = R.drawable.ic_delete)
                ,contentDescription = "delete contacts"
                ,tint = RedVariant)
            Text(text = "Delete", fontSize = 12.sp)
        }
       
        Spacer(modifier = Modifier.width(70.dp))

            Column (horizontalAlignment = Alignment.CenterHorizontally) {


                        Icon(painter = painterResource(id = R.drawable.ic_upload),
                            modifier = Modifier.clickable {  },
                            contentDescription = "upload contacts",
                            tint = Color.Green)
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

            IconButton(onClick = {
                    closeSelect.invoke()
            }) {
                Icon(painter = painterResource(id = R.drawable.ic_close)
                    , contentDescription = null
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

}