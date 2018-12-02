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
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.cart_list_item.view.*
import kotlinx.android.synthetic.main.tes_layout.view.*

class RecyclerViewAdapter(
    val data: List<String>,
    val context: Context?
) : RecyclerSwipeAdapter<RecyclerViewAdapter.ViewHolder>() {

    private var check: Boolean = false
    private var itemManager: SwipeItemRecyclerMangerImpl = SwipeItemRecyclerMangerImpl(this)

    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.swipe
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tv: TextView = v.planet_name
        val swipeLayout: SwipeLayout? = v.swipe
        val fab: FloatingActionButton? = v.fab
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


        holder.swipeLayout?.addSwipeListener(object : SwipeLayout.SwipeListener {
            override fun onOpen(layout: SwipeLayout?) {
                Log.d("swipeLayout", "onOpen")
                check = true
            }

            override fun onUpdate(layout: SwipeLayout?, leftOffset: Int, topOffset: Int) {
                Log.d("swipeLayout", "onUpdate")
            }

            override fun onStartOpen(layout: SwipeLayout?) {
                Log.d("swipeLayout", "onStartOpen")
            }

            override fun onStartClose(layout: SwipeLayout?) {
                Log.d("swipeLayout", "onStartClose")
            }

            override fun onHandRelease(layout: SwipeLayout?, xvel: Float, yvel: Float) {
                Log.d("swipeLayout", "onHandRelease")
            }

            override fun onClose(layout: SwipeLayout?) {
                Log.d("swipeLayout", "onClose")
                check = false
            }

        })

        // Drag From Left
        holder.swipeLayout?.addDrag(SwipeLayout.DragEdge.Left, holder.swipeLayout.findViewById(R.id.bottom_wrapper1))

        // Drag From Right
        holder.swipeLayout?.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.bottom_wrapper))


        holder.fab?.setOnClickListener {
            Log.d("swipeLayoutFab", "working... on click")
            holder.swipeLayout?.close()
        }

        itemManager.bindView(holder.itemView, position)

    }

}