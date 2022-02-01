package com.mtu.ceit.hhk.contactstore.features

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import com.alexstyl.contactstore.Label
import com.mtu.ceit.hhk.contactstore.R
import com.mtu.ceit.hhk.contactstore.domain.models.ContactDetail
import com.mtu.ceit.hhk.contactstore.domain.models.LabeledPhone
import com.mtu.ceit.hhk.contactstore.ui.theme.*
import kotlin.math.exp

@ExperimentalMaterialApi
@Composable
fun ContactAdd(contactAddVM:AddContactViewModel = hiltViewModel()) {

    val contactDetail by remember {
        mutableStateOf(ContactDetail(
            displayName = "",
            firstName = "",
            lastName = "",
            phones = mutableListOf(),
            isStarred = false,
            imgData = null,

        ))
    }
    var firstName by remember {
        mutableStateOf("")
    }

    var lastName by remember {
        mutableStateOf("")
    }

//    var phoneNo by remember {
//        mutableStateOf("")
//    }

    var mail by remember {
        mutableStateOf("")
    }

    val phoneOptions:List<String> = remember {
        listOf("Mobile", "Work", "Home", "Main")
    }

    val mailOptions = remember {
        listOf("Work","Home")
    }

    var webAddress:String by remember {
        mutableStateOf("")
    }

    var note:String by remember {
        mutableStateOf("")
    }
    val scrollState = rememberScrollState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
//            Image(
//                painter = painterResource(id = R.drawable.ic_id),
//                modifier = Modifier
//                    .fillMaxHeight(0.5f)
//                    .fillMaxWidth()
//                    .clickable {
//
//                    },
//                contentDescription = null,
//                contentScale = ContentScale.Crop)

//            Column(modifier = Modifier) {
//
//                NameField(
//                    firstName = contactAddVM.firstName.value,
//                    lastName = contactAddVM.lastName.value,
//                    firstNameValueChange ={
//                        contactAddVM.onFirstNameChange(it)
//                    },
//                    lastNameValueChange = {
//                        contactAddVM.onLastNameChange(it)
//                    })
//
//                repeat(contactAddVM.phoneCount.value){
//                    CombinedField(
//                        labelText = "Phone Number",
//                        icon = Icons.Default.Phone,
//                        keyboardType = KeyboardType.Phone,
//                        options = phoneOptions,
//                        index = it,
//                        list = contactAddVM.phoneList,
//                        vm = contactAddVM
//                    )
//                }



                   item {
                                   Image(
                painter = painterResource(id = R.drawable.ic_id),
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth()
                    .clickable {

                    },
                contentDescription = null,
                contentScale = ContentScale.Crop)
                   }

                    items(contactAddVM.testCount.value){ index: Int ->

                        Log.d("testStringList", "ContactAdd: $index")
                      CompoundField {
                          Log.d("testStringList", "Contact $index")
                          contactAddVM.onTestAdd(index,it)
                      }

                    }





//                PhoneField(
//                    phoneNo = contactAddVM.phone.value,
//                    labelText = "Phone Number",
//                    icon = Icons.Default.Phone,
//                    keyboardType = KeyboardType.Phone,
//                    onPhoneValueChange = {
//                        phoneNo = it
//                    },
//                    options = phoneOptions
//                )

//                CombinedField(
//                    phoneNo = mail,
//                    labelText = "E-Mail",
//                    icon = Icons.Default.Email,
//                    keyboardType = KeyboardType.Email,
//                    onPhoneValueChange = {
//                        mail = it
//                    },
//                    options = mailOptions
//                )
//
//                MoreFieds(
//                    noteStr = note,
//                    webAddress = webAddress,
//                    onNoteValueChange = {
//                        note = it
//                    },
//                    onWebValueChange = {
//
//                    })
//
//            }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart ){

        Row(modifier = Modifier
            .height(100.dp)
            .fillMaxWidth() ,
            verticalAlignment = Alignment.CenterVertically ,
        horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = {  }, modifier = Modifier.padding(15.dp,0.dp,0.dp,0.dp)) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null ,
                    Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .clickable { })
            }

            IconButton(onClick = {  }, modifier = Modifier.padding(0.dp,0.dp,15.dp,0.dp)) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null ,
                    Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .clickable { })
            }
        }

    }



    }


@Composable
fun MoreFieds(noteStr:String,webAddress:String,onNoteValueChange:(String)->Unit,onWebValueChange:(String)->Unit) {

//    CustomOutLineTextField(
//        value = note,
//        labelText = " Note ",
//        icon = Icons.Default.Info,
//        keyboardType = KeyboardType.Text,
//        onValueChange = {
//
//        })

    CustomOutLineTextField(
        value = webAddress,
        labelText = " Web Address ",
        icon = Icons.Default.Place,
        keyboardType = KeyboardType.Text,
        onValueChange = {

        })

    TextField(
        modifier = Modifier
            .height(200.dp)
            .padding(30.dp),
        value = noteStr,
        leadingIcon ={Icon(imageVector =Icons.Default.Info, contentDescription = null, tint = Primary)},
        singleLine = false,
        maxLines = 5,
        label = { Text(text = "Note")},
        onValueChange = {
           onNoteValueChange.invoke(it)
    })


}

@Composable
fun NameField(firstName:String, lastName:String, firstNameValueChange: (String) -> Unit, lastNameValueChange:(String)->Unit) {

    CustomOutLineTextField(
        value = firstName,
        icon = Icons.Default.AccountCircle,
        labelText = "First Name",
        keyboardType = KeyboardType.Text,
        onValueChange = firstNameValueChange
    )

    TextField(
        modifier = Modifier
            .padding(PaddingValues(30.dp, 0.dp, 40.dp, 0.dp))
            .background(MaterialTheme.colors.onSecondary)
            .fillMaxWidth(),
        value = lastName,
        leadingIcon = { Spacer(modifier = Modifier.width(30.dp))},
        label = { Text(text = "Last Name")},
        onValueChange = lastNameValueChange
        ,keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )

}

@ExperimentalMaterialApi
@Composable
fun CombinedField(
    phoneNo:String?=null,labelText: String,icon: ImageVector
    ,keyboardType: KeyboardType,onPhoneValueChange:((String)->Unit)?=null,
    options:List<String>,list:MutableList<LabeledPhone>?=null,index:Int = 0,vm:AddContactViewModel?=null) {



    var phone by remember {
        mutableStateOf("")
    }

    if(vm!=null){
        if(list!!.size == index+1 && phone.isNotBlank() && list.last().value != phone ){
           // vm.phoneCount.value ++
        }
    }

   CustomOutLineTextField(
       value = phoneNo ?: phone,
       labelText = labelText,
       icon = icon,
       keyboardType = keyboardType ,
       onValueChange = onPhoneValueChange ?: {
           vm!!.addPhoneList(index,LabeledPhone(phone,Label.LocationWork))
          // list!!.add(index,LabeledPhone(phone,Label.LocationWork))
           phone = it

       })

    ExposedDropDown(options)


}


@Composable
fun CompoundField(onValueChange: (String) -> Unit) {

    var value by remember {
        mutableStateOf("")
    }

    TextField(
        value = value,
        onValueChange = {
            value = it
            onValueChange.invoke(it)
        })

}

@ExperimentalMaterialApi
@Composable
fun ExposedDropDown(options: List<String>) {


    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(
        modifier =Modifier.padding(PaddingValues(30.dp, 1.dp, 40.dp, 0.dp)),
        expanded = expanded,
        onExpandedChange = {
        expanded = !expanded
    }) {
        TextField(modifier = Modifier
            .background(MaterialTheme.colors.onSecondary)
            .fillMaxWidth(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            trailingIcon = {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null )

            },
        )

        DropdownMenu(expanded = expanded, onDismissRequest = {
                expanded = false
            },modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)) {

                options.forEach {
                    DropdownMenuItem(onClick = {
                        selectedOptionText = it
                        expanded = false
                    }) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(text = it)
                            Spacer(modifier = Modifier.height(10.dp))
                            Divider(modifier = Modifier.fillMaxWidth())
                        }
                    }
                }

            }
    }

}

@Composable
fun CustomOutLineTextField(value:String,labelText:String,icon:ImageVector,keyboardType: KeyboardType,onValueChange:(String)->Unit) {


      TextField(
          modifier = Modifier
              .padding(PaddingValues(30.dp, 15.dp, 40.dp, 0.dp))
              .background(MaterialTheme.colors.onSecondary)
              .fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            leadingIcon ={Icon(imageVector =icon, contentDescription = null, tint = Primary)}  ,
            label = { Text(text = labelText)},
            keyboardOptions = KeyboardOptions(keyboardType =keyboardType)
        )


}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun ContactAddPrev() {
    ContactAdd()
}