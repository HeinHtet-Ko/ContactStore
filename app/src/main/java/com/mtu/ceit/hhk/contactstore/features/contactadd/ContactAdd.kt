package com.mtu.ceit.hhk.contactstore.features.contactadd

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alexstyl.contactstore.Label
import com.mtu.ceit.hhk.contactstore.R
import com.mtu.ceit.hhk.contactstore.domain.models.LabeledMail
import com.mtu.ceit.hhk.contactstore.domain.models.LabeledPhone
import com.mtu.ceit.hhk.contactstore.ui.theme.*
import kotlin.math.min

@ExperimentalMaterialApi
@Composable
fun ContactAdd(navController: NavController,contactAddVM: AddContactViewModel = hiltViewModel()) {


    val scrollState = rememberScrollState()

    val labels = stringArrayResource(id = R.array.labels)

    val maillabels = labels.copyOfRange(1,4)

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        , horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource(id = R.drawable.ic_id),
                        modifier = Modifier
                            .fillMaxWidth()
                            .graphicsLayer {
                                val scst = min(1f, 1 - ((scrollState.value + 1f) / (500f)))
                                alpha = scst
                                scaleX = scst
                                scaleY = scst
                                translationY = scrollState.value.toFloat()
                            }
                            .clickable {

                            },
                        contentDescription = null,
                        contentScale = ContentScale.Crop)



                NameField(
                    lastName = contactAddVM.lastName.value,
                    firstNameValueChange = {
                        contactAddVM.onFirstNameChange(it)
                                           },
                    lastNameValueChange = {
                        contactAddVM.onLastNameChange(it)
                    })



                repeat(contactAddVM.phoneCount.value) { index:Int ->
                    CombinedPhoneField (
                        options = labels.toList(),
                        onFieldChange = { phone,label ->
                            contactAddVM.addPhoneList(index,LabeledPhone(phone,label.toLabel()))
                        })
                    }


                repeat(contactAddVM.mailCount.value) { index:Int ->
                    CombinedMailField(
                        options = maillabels.toList()){ mail,label ->
                        contactAddVM.addMailList(index,LabeledMail(mail,label.toLabel()))
                    }
                }

                var expand by remember {
                    mutableStateOf(false)
                }

                AnimatedVisibility(expand) {
                    MoreFieds(
                        noteStr = contactAddVM.note.value,
                        onNoteValueChange = { noteStr ->
                                            contactAddVM.onNoteChange(noteStr)
                        },
                        onWebValueChange = { webString ->
                            contactAddVM.onWebAddChange(webString)
                        }
                    )
                }
                IconButton(onClick = {
                    expand = !expand
                }) {
                    Row(verticalAlignment = Alignment.CenterVertically ,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = DarkV,
                            shape = RectangleShape
                        )
                        .padding(5.dp)) {
                        Text(text =if(!expand) "More Fields" else "Less Fields")
                        Spacer(modifier = Modifier.width(5.dp))
                        Icon(modifier = Modifier.size(25.dp),
                            imageVector =if(!expand) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp ,
                            contentDescription = null)
                    }

                }
                Spacer(modifier = Modifier.height(100.dp))

    }



    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart ){

        Row(modifier = Modifier
            .height(70.dp)
            .fillMaxWidth() ,
            verticalAlignment = Alignment.CenterVertically ,
        horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = {
                                 navController.popBackStack()
            }, modifier = Modifier.padding(15.dp,0.dp,0.dp,0.dp)) {
                Icon(imageVector = Icons.Default.Close,
                    contentDescription = null ,
                    Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                       )
            }

            IconButton(onClick = {
                         contactAddVM.addContact()
                         navController.popBackStack()
            }, modifier = Modifier.padding(0.dp,0.dp,15.dp,0.dp)) {
                Icon(imageVector = Icons.Default.Check,
                    contentDescription = null ,
                    Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                       )
            }
        }

    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CombinedPhoneField(options: List<String> ,
                       onFieldChange: (String,String) -> Unit) {

    var phone by remember {
        mutableStateOf("")
    }
    var label by remember {
        mutableStateOf(options[0])
    }

    Column {
            CustomOutLineTextField(
                labelText = "Phone",
                icon = Icons.Default.Call,
                keyboardType = KeyboardType.Phone,
                onValueChange =
                {
                    phone = it
                    onFieldChange(phone,label)
                }
            )

            ExposedDropDown(options = options){

                    label = it
                    onFieldChange(phone,label)

            }

        }


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CombinedMailField(options: List<String>, onFieldChange: (String, String) -> Unit) {


    var mail by remember {
        mutableStateOf("")
    }
    var label by remember {
        mutableStateOf(options[0])
    }

    Column {
        CustomOutLineTextField(
            labelText = "Mail",
            icon = Icons.Default.Email,
            keyboardType = KeyboardType.Text,
            onValueChange = {
                mail = it
                Log.d("maillabeltracker", "CombinedMailField: $label")
                onFieldChange.invoke(mail,label)
            }
        )

        ExposedDropDown(options = options){
            label = it
            onFieldChange.invoke(mail,label)
        }

    }


}

@Composable
fun MoreFieds(noteStr:String,
              onNoteValueChange:(String)->Unit,
              onWebValueChange:(String)->Unit) {


    Column {
        CustomOutLineTextField(
            labelText = " Web Address ",
            icon = Icons.Default.Place,
            keyboardType = KeyboardType.Text,
            onValueChange = onWebValueChange)

        TextField(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding(PaddingValues(30.dp, 15.dp, 40.dp, 0.dp))
                .background(MaterialTheme.colors.onSecondary),
            value = noteStr,
            leadingIcon ={Icon(imageVector =Icons.Default.Info, contentDescription = null, tint = Primary)},
            singleLine = false,
            maxLines = 5,
            label = { Text(text = "Note")},
            onValueChange = {
                onNoteValueChange.invoke(it)
            })
    }



}

@Composable
fun NameField(lastName:String, firstNameValueChange: (String) -> Unit, lastNameValueChange:(String)->Unit) {

    CustomOutLineTextField(
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
fun ExposedDropDown(options: List<String>,onSelectionChange:(String)->Unit) {


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
            .fillMaxWidth(0.7f)
            .background(MaterialTheme.colors.background)) {

                options.forEach {
                    DropdownMenuItem(onClick = {
                        selectedOptionText = it
                        expanded = false
                        onSelectionChange.invoke(it)
                    }) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(text = it)
                            Spacer(modifier = Modifier.height(10.dp))

                        }
                    }
                }
        }
    }
}

@Composable
fun CustomOutLineTextField(labelText:String,icon:ImageVector,keyboardType: KeyboardType,onValueChange:(String)->Unit) {

    var value by remember {
        mutableStateOf("")
    }

      TextField(
          modifier = Modifier
              .padding(PaddingValues(30.dp, 15.dp, 40.dp, 0.dp))
              .background(MaterialTheme.colors.onSecondary)
              .fillMaxWidth(),
            value = value,
            onValueChange = {
                onValueChange.invoke(it)
                value = it
            },
            leadingIcon ={Icon(imageVector =icon, contentDescription = null, tint = Primary)}  ,
            label = { Text(text = labelText)},
            keyboardOptions = KeyboardOptions(keyboardType =keyboardType)
        )


}

