package com.mtu.ceit.hhk.contactstore.features

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import com.mtu.ceit.hhk.contactstore.R
import com.mtu.ceit.hhk.contactstore.ui.theme.*
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

    var phoneNo by remember {
        mutableStateOf("")
    }

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

    var targetFloat = ((9+(-scrollState.value * 0.01f))/9 )*0.4




    val frf by animateFloatAsState(targetValue = targetFloat.toFloat())

   // targetFloat = (-scrollState.value * 0.1f)


    val constraints = ConstraintSet {
        val imageContainer = createRefFor("img")
        val column = createRefFor("column")
        val imgGuide = createGuidelineFromTop(frf)



        constrain(imageContainer) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(parent.end)

            bottom.linkTo(column.top)

        }
        constrain(column) {
            start.linkTo(parent.start)
            top.linkTo(imageContainer.bottom)
           // end.linkTo()

        }

    }


    Log.d("scrtracklist", "ContactAdd: ${(-scrollState.value/4)}")

    ConstraintLayout(constraintSet = constraints,
            modifier = Modifier.fillMaxSize()) {



            Image(
                painter = painterResource(id = R.drawable.lback),
                modifier = Modifier
                  //  .absoluteOffset(y = (-(scrollState.value)).dp)
                    .layoutId("img")
                    // .fillMaxHeight(0.5f)
                    .fillMaxWidth()
                    .scale(((9+(-scrollState.value * 0.01f))/9 ))
                    .alpha(((9 + (-scrollState.value * 0.01f)) / 9))


                    .background(CardNightVariant)
                    .clickable {

                    },
                contentDescription = null,
                contentScale = ContentScale.Crop)



//
//           Box(modifier = Modifier
//               .fillMaxSize()
//               .layoutId("column"),
//           contentAlignment = Alignment.BottomStart){


               Column(modifier = Modifier
                   .layoutId("column")
                   .fillMaxHeight()
                   // .fillMaxSize()
                   // .scrollable(rememberScrollState(),Orientation.Vertical)
                   .verticalScroll(scrollState)
                        ) {
//                   Spacer(modifier = Modifier.height(100.dp).fillMaxWidth(0.5f)
//                       .background(Color.Red))
                   NameField(
                       firstName = firstName,
                       lastName = lastName,
                       firstNameValueChange = {
                           firstName = it
                       },
                       lastNameValueChange = {
                           lastName = it
                       } )

                   PhoneField(
                       phoneNo = phoneNo,
                       labelText = "Phone Number",
                       icon = Icons.Default.Phone,
                       keyboardType = KeyboardType.Phone,
                       onPhoneValueChange = {
                           phoneNo = it
                       },
                       options = phoneOptions
                   )

                   PhoneField(
                       phoneNo = mail,
                       labelText = "E-Mail",
                       icon = Icons.Default.Email,
                       keyboardType = KeyboardType.Email,
                       onPhoneValueChange = {
                           mail = it
                       },
                       options = mailOptions
                   )

                   MoreFieds(
                       note = note,
                       webAddress = webAddress,
                       onNoteValueChange = {

                       },
                       onWebValueChange = {

                       })
                   MoreFieds(
                       note = note,
                       webAddress = webAddress,
                       onNoteValueChange = {

                       },
                       onWebValueChange = {

                       })
                   MoreFieds(
                       note = note,
                       webAddress = webAddress,
                       onNoteValueChange = {

                       },
                       onWebValueChange = {

                       })
                   Spacer(modifier = Modifier.height(200.dp))
               }

        }





}

@Composable
fun MoreFieds(note:String,webAddress:String,onNoteValueChange:(String)->Unit,onWebValueChange:(String)->Unit) {

    CustomOutLineTextField(
        value = note,
        labelText = " Note ",
        icon = Icons.Default.Info,
        keyboardType = KeyboardType.Text,
        onValueChange = {

        })

    CustomOutLineTextField(
        value = webAddress,
        labelText = " Web Address ",
        icon = Icons.Default.Place,
        keyboardType = KeyboardType.Text,
        onValueChange = {

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
        onValueChange = lastNameValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )

}

@ExperimentalMaterialApi
@Composable
fun PhoneField(phoneNo:String,labelText: String,icon: ImageVector,keyboardType: KeyboardType,onPhoneValueChange:(String)->Unit,
        options:List<String>) {

   CustomOutLineTextField(
       value = phoneNo,
       labelText = labelText,
       icon = icon,
       keyboardType = keyboardType ,
       onValueChange = onPhoneValueChange)

    ExposedDropDown(options)


}


@ExperimentalMaterialApi
@Composable
fun ExposedDropDown(options: List<String>) {


    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(
        modifier =Modifier.padding(PaddingValues(30.dp, 0.dp, 40.dp, 0.dp)),
        expanded = expanded,
        onExpandedChange = {
        expanded = !expanded
    }) {
        TextField(modifier = Modifier
            .background(MaterialTheme.colors.onSecondary)
            .fillMaxWidth(0.7f),
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
            .fillMaxWidth(0.5f)
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