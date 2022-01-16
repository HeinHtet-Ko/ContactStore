package com.mtu.ceit.hhk.contactstore
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.alexstyl.contactstore.ContactColumn
import com.alexstyl.contactstore.ContactPredicate
import com.alexstyl.contactstore.ContactStore
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.mtu.ceit.hhk.contactstore.domain.Contact
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlin.math.min


@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun HomeScreen(contactVM:LocalContactListViewModel = hiltViewModel()) {

    val perState = rememberMultiplePermissionsState(permissions = listOf(
        android.Manifest.permission.WRITE_CONTACTS,
        android.Manifest.permission.READ_CONTACTS,
        android.Manifest.permission.CALL_PHONE
    ))

    var isSelecting by remember {
        mutableStateOf(false)
    }

    val listC = contactVM._contactList.collectAsState(initial = emptyList())



    val context = LocalContext.current
    val i = Intent(Intent.ACTION_CALL)
    i.data = Uri.parse("tel:09770109404")

    LaunchedEffect(Unit) {

        perState.launchMultiplePermissionRequest()
       if( perState.allPermissionsGranted) {

           contactVM.getContacts()

       }
    }

    val listState = rememberLazyListState()



    val offs = min(1f,1-(listState.firstVisibleItemScrollOffset/500f + listState.firstVisibleItemIndex))

    Column(modifier = Modifier.fillMaxSize()) {

        val animateScrollState by animateDpAsState(targetValue = max(75.dp,150.dp*offs))
        val animateFloat by animateFloatAsState(targetValue = kotlin.math.max(1f,10*offs))

        Row(

            Modifier
                .clickable {
                    // context.startActivity(i)
                    //  LocalContext.current.startActivity(i)
                }
                .fillMaxWidth()
                ,verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Contacts", fontSize = 25.sp,
                color = Color.Yellow
                ,
                fontFamily = FontFamily(Font(R.font.sourceserif)),
                modifier = Modifier

                    .clickable {
                        isSelecting = !isSelecting

                    }
            )
        }




        Log.d("offsettracking", "HomeScreen: ${offs}")
        LazyColumn(state = listState){


            items(items = listC.value , key = {
                it.id
            }){ contact:Contact ->
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