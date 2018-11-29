package com.looser43.rndproject

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cart_list_item.view.*

class RecyclerViewAdapter(val data: List<String>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    /*var call: Callbacks? = null*/
    init {
        //call = context as Callbacks
    }


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tv: TextView = v.planet_name
        val viewForeground: RelativeLayout = v.view_foreground
        val viewBackground: RelativeLayout = v.view_background
        val viewBackground1: RelativeLayout = v.view_background1
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

        holder.viewBackground1.setOnClickListener {
            Log.d("swipeClick", "clicked")
        }
        holder.viewBackground.setOnClickListener {
            Log.d("swipeClick", "clicked")
        }
    }

}