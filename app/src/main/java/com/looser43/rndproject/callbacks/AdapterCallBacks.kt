package com.looser43.rndproject.callbacks

import android.view.View

interface AdapterCallBacks {
    fun onLongClick(v: View, position: Int)
    fun onSingleClick(position: Int)
}