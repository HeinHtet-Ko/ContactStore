package com.mtu.ceit.hhk.contactstore.features.contactlist

import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mtu.ceit.hhk.contactstore.ui.theme.DarkVariant
import com.mtu.ceit.hhk.contactstore.ui.theme.Primary

@Composable
fun ContactSearchField(height:Int, query:String, labelStr:String, onQueryChange:(String)->Unit) {

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(height.dp)
        .padding(20.dp,3.dp), contentAlignment = Alignment.TopCenter) {
        TextField(
            value = query,
            label = {Text(text = labelStr)},
            onValueChange = onQueryChange,
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(DarkVariant)
                .fillMaxWidth(0.8f),
            trailingIcon = { Icon(tint = Primary, imageVector = Icons.Default.Search, contentDescription = null ,
                modifier = Modifier.padding(10.dp)) }
        )

    }

}

@Preview(uiMode = UI_MODE_NIGHT_MASK, showBackground = true)
@Composable
fun searchPrev(){
   // ContactSearchField(query = "", labelStr = " ", onQueryChange = {})
}