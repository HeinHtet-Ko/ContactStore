package com.mtu.ceit.hhk.contactstore

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.alexstyl.contactstore.ContactColumn
import com.alexstyl.contactstore.ContactStore
import com.google.accompanist.permissions.ExperimentalPermissionsApi

import com.mtu.ceit.hhk.contactstore.ui.theme.ContactStoreTheme
import com.mtu.ceit.hhk.contactstore.ui.theme.Primary
import com.mtu.ceit.hhk.contactstore.ui.theme.Purple200

class MainActivity : ComponentActivity() {
    @ExperimentalPermissionsApi
    @ExperimentalMaterialApi
    @RequiresApi(Build.VERSION_CODES.N)
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contact_store = ContactStore.newInstance(this)

        contact_store.fetchContacts(

        )


        val list:MutableList<ContactItem> = mutableListOf()
        repeat(15){
            list.add(ContactItem("Hein Htet Ko","09 770109404"))
        }

        setContent {
            ContactStoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                   MyContactApp(store = contact_store)
                }
            }
        }
    }
}
