package com.example.myapplication

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemDecorator(private val margin: Int,
                            private val spanCount: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {

        val position = parent.getChildLayoutPosition(view)
        outRect.right = margin
        outRect.bottom = margin
        if (position % spanCount == 0) {
            outRect.left = margin
        }
        if (position < spanCount) {
            outRect.top = margin
        }
    }
}