package com.looser43.rndproject

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import kotlinx.android.synthetic.main.cart_list_item.view.*

class RecyclerViewAdapter(
    val data: List<String>,
    val context: Context?
) : RecyclerSwipeAdapter<RecyclerViewAdapter.ViewHolder>() {
    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.swipe
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tv: TextView = v.planet_name
        val swipeLayout: SwipeLayout? = v.swipe
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_list_item, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        holder.tv.text = data[position]
        holder.swipeLayout?.showMode = SwipeLayout.ShowMode.PullOut

        // Drag From Left
        holder.swipeLayout?.addDrag(SwipeLayout.DragEdge.Left, holder.swipeLayout.findViewById(R.id.bottom_wrapper1))

        // Drag From Right
        holder.swipeLayout?.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.bottom_wrapper))

        holder.swipeLayout?.addSwipeListener(object : SwipeLayout.SwipeListener {
            override fun onOpen(layout: SwipeLayout?) {
                Log.d("swipeLayout", "")
            }

            override fun onUpdate(layout: SwipeLayout?, leftOffset: Int, topOffset: Int) {
                Log.d("swipeLayout", "")
            }

            override fun onStartOpen(layout: SwipeLayout?) {
                Log.d("swipeLayout", "")
            }

            override fun onStartClose(layout: SwipeLayout?) {
                Log.d("swipeLayout", "")
            }

            override fun onHandRelease(layout: SwipeLayout?, xvel: Float, yvel: Float) {
                Log.d("swipeLayout", "")
            }

            override fun onClose(layout: SwipeLayout?) {
                Log.d("swipeLayout", "")
            }

        })

    }

}