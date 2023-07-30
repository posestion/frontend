package com.example.posestion

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class GridAdapter(val layout: View) : RecyclerView.ViewHolder(layout) {
    fun setImage(imageResource: Int) {
        if (layout is ImageView) {
            layout.setImageResource(imageResource)
        }
    }
}