package com.mtu.ceit.hhk.contactstore.features

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mtu.ceit.hhk.contactstore.R
import com.mtu.ceit.hhk.contactstore.ui.theme.GreenVariant
import com.mtu.ceit.hhk.contactstore.ui.theme.Primary
import com.mtu.ceit.hhk.contactstore.ui.theme.WhiteGray
import kotlin.math.exp

@ExperimentalMaterialApi
@Composable
fun ContactAdd() {

    var firstName by remember {
        mutableStateOf("")
    }

    var lastName by remember {
        mutableStateOf("")
    }

    var phone by remember {
        mutableStateOf("")
    }

    var mail by remember {
        mutableStateOf("")
    }

    var count by remember {
        mutableStateOf(1)
    }


    Column(modifier = Modifier.fillMaxSize()
    , horizontalAlignment = Alignment.Start
    ) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
            ) {

            Image(painter = painterResource(id = R.drawable.ic_id),
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {

                    },
                contentDescription = null, contentScale = ContentScale.Crop)

        }

        CustomOutLineTextField(
            value = firstName,
            icon = Icons.Default.Face,
            labelText = "First Name",
            keyboardType = KeyboardType.Text
        ) {
            firstName = it
        }
        CustomOutLineTextField(
            value = lastName,
            labelText = "Last Name",
            keyboardType = KeyboardType.Text
        ) {
            lastName = it
        }
        CustomOutLineTextField(
            value = phone,
            icon = Icons.Default.Phone,
            labelText = "Phone Number",
            keyboardType = KeyboardType.Phone
        ){
            phone = it
        }

        ExposedDropDown()
        CustomOutLineTextField(
            value = mail,
            icon = Icons.Default.Email,
            labelText = " Email ",
            keyboardType = KeyboardType.Email
        ){
            mail = it
        }

    }


    }


@ExperimentalMaterialApi
@Composable
fun ExposedDropDown() {



//
    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {
        expanded = !expanded
    }) {
        TextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = { Text("Label") },
            trailingIcon = {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null )

            }


//            trailingIcon = {
//                ExposedDropdownMenuDefaults.TrailingIcon(
//                    expanded = expanded
//                )
//            },
          //  colors = ExposedDropdownMenuDefaults.textFieldColors()
        )


            DropdownMenu(expanded = expanded, onDismissRequest = {
                expanded = false
            },modifier = Modifier.fillMaxWidth(0.7f)) {
                DropdownMenuItem(onClick = {
                    expanded = false
                },) {
                    Text(text = "good")
                }
                DropdownMenuItem(onClick = {
                    expanded = false
                }) {
                    Text(text = "bad")
                }

            }



    }

//
//
//    ExposedDropdownMenuBox(
//        expanded = expanded,
//        onExpandedChange = {
//            expanded = !expanded
//        }
//    ) {
//
//        ExposedDropdownMenu(
//            expanded = expanded,
//            onDismissRequest = {
//                expanded = false
//            }
//        ) {
//            options.forEach { selectionOption ->
//                DropdownMenuItem(
//                    onClick = {
//                        selectedOptionText = selectionOption
//                        expanded = false
//                    }
//                ) {
//                    Text(text = selectionOption)
//                }
//            }
//        }
//    }
//
}

@Composable
fun CustomOutLineTextField(value:String,labelText:String,icon:ImageVector?=null,keyboardType: KeyboardType,onValueChange:(String)->Unit) {




       // Icon(imageVector = icon, contentDescription = null , modifier = Modifier.size(35.dp))
      //  Spacer(modifier = Modifier.width(10.dp))
        TextField(
            modifier = Modifier
                .padding(25.dp, 5.dp, 35.dp, 5.dp)
                .fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            leadingIcon = { if(icon!=null)Icon(imageVector =icon, contentDescription = null) else Spacer(
                modifier = Modifier.width(20.dp)
            )},
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