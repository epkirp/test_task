package com.example.code.utils

import android.content.res.Resources
import android.util.TypedValue

object DimensionManager {


    fun convertDPToPixels(resources: Resources,value: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),resources.displayMetrics).toInt()
    }
}