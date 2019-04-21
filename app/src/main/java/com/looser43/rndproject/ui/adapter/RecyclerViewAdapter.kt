package com.looser43.rndproject.ui.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl
import com.looser43.rndproject.R
import com.looser43.rndproject.callbacks.AdapterCallBacks
import com.looser43.rndproject.models.Model
import kotlinx.android.synthetic.main.cart_list_item.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.okButton
import org.jetbrains.anko.toast
import kotlin.random.Random


class RecyclerViewAdapter(
    dataL: ArrayList<Model>,
    val context: Context
) : RecyclerSwipeAdapter<RecyclerViewAdapter.ViewHolder>() {

    /*companion object {
    }
*/
    private var globalPos: Int = -1
    private val selectedItems = SparseBooleanArray()
    private lateinit var h: ViewHolder
    private var data: ArrayList<Model>? = ArrayList()
    private var items: ArrayList<Model>? = ArrayList()
    private var call: AdapterCallBacks? = null
    private var check: Boolean = false
    private var itemManager: SwipeItemRecyclerMangerImpl = SwipeItemRecyclerMangerImpl(this)

    init {
        data = dataL
        call = context as AdapterCallBacks
    }

    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.swipe
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tv: TextView = v.planet_name
        val item: RelativeLayout = v.view_foreground
        val swipeLayout: SwipeLayout? = v.swipe
        //val fab: FloatingActionButton? = v.fab
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return if (data == null) 0 else data!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = data?.get(position)
        setHolder(holder)
        holder.tv.text = model?.text
        holder.swipeLayout?.showMode = SwipeLayout.ShowMode.PullOut

        holder.item.setBackgroundColor(if (model?.isSelected as Boolean) Color.LTGRAY else Color.WHITE)

        holder.swipeLayout?.addSwipeListener(object : SwipeLayout.SwipeListener {
            override fun onOpen(layout: SwipeLayout?) {
                Log.d("swipeLayout", "onOpen and val : $globalPos")
                globalPos = Random.nextInt(0, 10)
                if (globalPos == 3) {
                    showDialog()
                }
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
        holder.swipeLayout?.addDrag(
            SwipeLayout.DragEdge.Left,
            holder.swipeLayout.findViewById(R.id.bottom_wrapper1)
        )

        // Drag From Right
        holder.swipeLayout?.addDrag(
            SwipeLayout.DragEdge.Right,
            holder.swipeLayout.findViewById(R.id.bottom_wrapper)
        )

/*
        if (position == 3) {
            holder.swipeLayout?.isSwipeEnabled = false
        }

        if (position == 1) {
            // Drag From Left
            holder.swipeLayout?.addDrag(
                SwipeLayout.DragEdge.Left,
                holder.swipeLayout?.findViewById(R.id.bottom_wrapper1)
            )

            // Drag From Right
            holder.swipeLayout?.addDrag(SwipeLayout.DragEdge.Right, null)
        } else {

            // Drag From Left
            holder.swipeLayout?.addDrag(
                SwipeLayout.DragEdge.Left,
                holder.swipeLayout?.findViewById(R.id.bottom_wrapper1)
            )

            // Drag From Right
            holder.swipeLayout?.addDrag(
                SwipeLayout.DragEdge.Right,
                holder.swipeLayout?.findViewById(R.id.bottom_wrapper)
            )

        }*/

        holder.itemView.setOnLongClickListener {
            if (!check) {
                if (!model.isSelected) {
                    items?.add(model)
                } else {
                    items?.remove(model)
                }
                call?.onLongClick(holder.item, position)
                model.isSelected = !model.isSelected
                holder.item.setBackgroundColor(if (model.isSelected) Color.LTGRAY else Color.WHITE)

                Log.d("Items", "getting items: ${items?.size}")
            }
            true
        }

        /*// Drag From Up
        holder.swipeLayout?.addDrag(SwipeLayout.DragEdge.Bottom, holder.swipeLayout.findViewById(R.id.bottom_wrapper3))*/


        /*holder.fab?.setOnClickListener {
            Log.d("swipeLayoutFab", "working... on click")
            holder.swipeLayout?.close()
        }*/

        itemManager.bindView(holder.itemView, position)

    }

    private fun showDialog() {
        context.alert("body", "title") {
            okButton {
                context.toast("Ok button pressed.")
            }
            cancelButton {
                it.dismiss()
            }
            /*also {
                                ctx.setTheme(R.style.CustomDialog)
                            }*/
        }.show()
    }

    private fun setHolder(holder: ViewHolder) {
        this.h = holder
    }

    private fun getHolder(): ViewHolder {
        return this.h
    }

    fun add(item: Model) {
        if (data != null) {
            data?.add(item)
            notifyItemInserted(data!!.size - 1)
        } else {
            Log.d("nullCheck", "getting null $item")
        }
    }

    fun selectAll() {
        items?.clear()
        /*data?.let {
            items?.addAll(it)
        }*/
        data?.forEach {
            //call?.onLongClick(getHolder().item, position)
            items?.add(it)
            if (it.isSelected) {
                it.isSelected = false
            }
            it.isSelected = !it.isSelected
            getHolder().item.setBackgroundColor(Color.LTGRAY)
            //getHolder().item.setBackgroundColor(if (it.isSelected) Color.LTGRAY else Color.WHITE)
        }
        Log.d("Items", "getting items: ${items?.size}")
        notifyDataSetChanged()
    }

    fun clearAll() {
        if (!data.isNullOrEmpty()) {
            items?.clear()
            data?.forEach {
                it.isSelected = false
                getHolder().item.setBackgroundColor(Color.WHITE)
            }
            Log.d("Items", "getting items: ${items?.size}")
            notifyDataSetChanged()
        }
    }

}