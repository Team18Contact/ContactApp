package com.example.contactapp.contact

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.contactapp.R
import java.io.IOException

object Constants{
    const val ITEM_OBJECT = "item_object"

    fun convertToUri (drawableImg: Int): Uri = Uri.parse("android.resource://" + R::class.java.`package`?.name + "/" + drawableImg)

    fun  convertToBitmap(context: Context, uri: Uri): Bitmap? {
        try {
            var inputStream = context.contentResolver.openInputStream(uri)
            return BitmapFactory.decodeStream(inputStream)
        } catch(e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}