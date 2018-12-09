package com.looser43.rndproject

import android.animation.ObjectAnimator
import android.util.Log
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.looser43.rndproject.adapter.RecyclerViewAdapter


class SimpleItemAnimator : DefaultItemAnimator() {

    override fun animateAppearance(
        viewHolder: RecyclerView.ViewHolder,
        preLayoutInfo: ItemHolderInfo?,
        postLayoutInfo: ItemHolderInfo
    ): Boolean {
        Log.d("AnimationTag", "animateApperance")
        val holder = viewHolder as RecyclerViewAdapter.ViewHolder
        val animator = ObjectAnimator.ofFloat(holder.tv, "translationX", 0F, holder.tv.width / 2.toFloat())
        animator.duration = 2000
        animator.start()
        return super.animateAdd(viewHolder)
    }

}