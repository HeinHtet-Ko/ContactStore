package com.mtu.ceit.hhk.contactstore
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.alexstyl.contactstore.ContactColumn
import com.alexstyl.contactstore.ContactPredicate
import com.alexstyl.contactstore.ContactStore
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first


@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun HomeScreen() {

   val contactStore = ContactStore.newInstance(LocalContext.current)




    val perState = rememberMultiplePermissionsState(permissions = listOf(
        android.Manifest.permission.WRITE_CONTACTS,
        android.Manifest.permission.READ_CONTACTS,
        android.Manifest.permission.CALL_PHONE
    ))

    var isSelecting by remember {
        mutableStateOf(false)
    }
    var selectedList by remember {
        mutableStateOf(mutableListOf<ContactItem>())
    }
    Log.d("selectedList", "HomeScreen: ${selectedList.size}")


    val context = LocalContext.current
    val i = Intent(Intent.ACTION_CALL)
    i.data = Uri.parse("tel:09770109404")

    LaunchedEffect(Unit) {




        perState.launchMultiplePermissionRequest()
       if( perState.allPermissionsGranted) {
           val cs = mutableListOf<ContactItem>()
           val cst = contactStore.fetchContacts(
               columnsToFetch = listOf(
                   ContactColumn.Names,
                   ContactColumn.Phones
               )).first()

           Log.d("contactire", "HomeScreen: ${cst.size}")
           cst.forEach {
              val phone =  it.phones.map { contact ->
                  if(contact.value.raw.isNotBlank())
                      contact.value.raw
                  else
                      ""
              }
               cs.add(ContactItem(it.displayName!!,phone.toString()))
           }
           selectedList = cs
       }
    }

    val listState = rememberLazyListState()



   // val offs = min(1f,1-(listState.firstVisibleItemScrollOffset/500f + listState.firstVisibleItemIndex))

    Column(modifier = Modifier.fillMaxSize()) {

       // val animateScrollState by animateDpAsState(targetValue = max(75.dp,150.dp*offs))

        Row(

            Modifier
                .clickable {
                    context.startActivity(i)
                    //  LocalContext.current.startActivity(i)
                }
                .fillMaxWidth()
                .background(Color(0xFF62ABEB))
                .height(50.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Contacts", fontSize = 25.sp,
                fontFamily = FontFamily(Font(R.font.sourceserif)),
                modifier = Modifier
                    .padding(30.dp, 20.dp)
                    .clickable {
                        isSelecting = !isSelecting

                    }
            )
        }




        Log.d("offsettracking", "HomeScreen: ${selectedList.size}")
        LazyColumn(state = listState){



            items(selectedList){ contact:ContactItem ->

                ContactListItem(contact,isSelecting, mutableListOf())


            }
        }
    }




}

@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@RequiresApi(Build.VERSION_CODES.N)
@Composable
@Preview(showBackground = true)
fun prev() {
    HomeScreen()
}