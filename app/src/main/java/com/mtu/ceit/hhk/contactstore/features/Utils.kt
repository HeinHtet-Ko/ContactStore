package com.mtu.ceit.hhk.contactstore.features

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

fun convertUriToBitmap(uri: Uri,context: Context):Bitmap{


    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val source =  ImageDecoder.createSource(context.contentResolver,uri)
        ImageDecoder.decodeBitmap(source)
    }else{

        MediaStore.Images.Media.getBitmap(context.contentResolver,uri)

    }


}