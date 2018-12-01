package com.looser43.rndproject.swipeUtils

import android.graphics.Canvas
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.looser43.rndproject.callbacks.RecyclerItemTouchHelperListener

class RecyclerItemTouchHelper(dragDirs: Int, swipeDirs: Int, private val listener: RecyclerItemTouchHelperListener) :
    ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    private var swiping = false

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (viewHolder != null) {
            //val foregroundView = (viewHolder as RecyclerViewAdapter.ViewHolder).viewForeground

            //getDefaultUIUtil().onSelected(foregroundView)
        }
    }

    override fun onChildDrawOver(
        c: Canvas, recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
        actionState: Int, isCurrentlyActive: Boolean
    ) {
        val newDx = dX / 3
        if (newDx <= 0 && newDx > 0) {
            /*val foregroundView = (viewHolder as RecyclerViewAdapter.ViewHolder).viewForeground
            getDefaultUIUtil().onDrawOver(
                c, recyclerView, foregroundView, newDx, dY,
                actionState, isCurrentlyActive
            )*/
            Log.d("swipe1", "swiping left to right dx = $newDx and $swiping")
        } else {
            /*val foregroundView = (viewHolder as RecyclerViewAdapter.ViewHolder).viewForeground
            getDefaultUIUtil().onDraw(
                c, recyclerView, foregroundView, newDx, dY,
                actionState, isCurrentlyActive
            )*/
            Log.d("swipe1", "swiping right to left dx = $newDx and $swiping")
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        /*val foregroundView = (viewHolder as RecyclerViewAdapter.ViewHolder).viewForeground
        getDefaultUIUtil().clearView(foregroundView)*/
    }

    override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
        actionState: Int, isCurrentlyActive: Boolean
    ) {
        val newDx = dX / 3

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            viewHolder.itemView.translationX = dX / 40
            swiping = isCurrentlyActive
            if (newDx <= 0 && newDx > 0) {
                /*val foregroundView = (viewHolder as RecyclerViewAdapter.ViewHolder).viewForeground
                getDefaultUIUtil().onDraw(
                    c, recyclerView, foregroundView, newDx, dY,
                    actionState, isCurrentlyActive
                )*/
                Log.d("swipe", "swiping left to right dx = $newDx and $swiping")

            } else {
                /*val foregroundView = (viewHolder as RecyclerViewAdapter.ViewHolder).viewForeground
                getDefaultUIUtil().onDraw(
                    c, recyclerView, foregroundView, newDx, dY,
                    actionState, isCurrentlyActive
                )*/
                Log.d("swipe", "swiping right to left dx = $newDx and $swiping")
            }
        }
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onSwiped(viewHolder, direction, viewHolder.adapterPosition)
    }
}
