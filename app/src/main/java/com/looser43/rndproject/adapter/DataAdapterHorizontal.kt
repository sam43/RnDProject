package com.looser43.rndproject.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.looser43.rndproject.R
import com.looser43.rndproject.models.DataModelHorizontal
import java.util.*


class DataAdapterHorizontal(
    private val items: ArrayList<DataModelHorizontal>,
    val context: Context,
    b: Boolean,
    nl: Boolean
) :
    androidx.recyclerview.widget.RecyclerView.Adapter<DataAdapterHorizontal.MyViewHolderHorizontal>() {

    private var selectedPosition = -1
    private var previousPosition = 0
    //private var call: JobDetailCallback? = null
    private var _clicked = false
    private var _nl = false
    private var applicantList: MutableList<DataModelHorizontal>? = null

    init {
        //call = context as JobDetailCallback
        applicantList = items
        _clicked = b
        _nl = nl
    }

    override fun onBindViewHolder(holder: MyViewHolderHorizontal, @SuppressLint("RecyclerView") position: Int) {

        val dModel = items[position]
        holder.number_Value.text = dModel.application
        holder.string_Value.text = dModel.value

        when (position) {
            items.size - 1 -> {
                holder.ivFastFor.visibility = View.GONE
                holder.itemView.visibility = View.INVISIBLE
            }
            items.size - 2 -> {
                holder.ivFastFor.visibility = View.GONE
                holder.itemView.visibility = View.VISIBLE
            }
            else -> {
                holder.ivFastFor.visibility = View.VISIBLE
                holder.itemView.visibility = View.VISIBLE
            }
        }

        if (position == previousPosition) {
            //_clicked = false
            holder.ll_details.setBackgroundResource(R.drawable.circle1)
            holder.string_Value.setTextColor(Color.parseColor("#FFFFFF"))
            holder.number_Value.setTextColor(Color.parseColor("#B32D7D"))
        } else {
            holder.ll_details.setBackgroundResource(R.drawable.circle)
            holder.string_Value.setTextColor(Color.parseColor("#E0ABCB"))
            holder.number_Value.setTextColor(Color.parseColor("#B32D7D"))
        }

        when {
            (position == 0) && _clicked -> {
                holder.ll_details.setBackgroundResource(R.drawable.circle1)
                holder.string_Value.setTextColor(Color.parseColor("#FFFFFF"))
                holder.number_Value.setTextColor(Color.parseColor("#B32D7D"))
                _clicked = false
                Log.d("vval", "1")
            }
            position == selectedPosition -> {
                holder.ll_details.setBackgroundResource(R.drawable.circle1)
                holder.string_Value.setTextColor(Color.parseColor("#FFFFFF"))
                holder.number_Value.setTextColor(Color.parseColor("#B32D7D"))
                Log.d("vval", "2")
            }
            _clicked -> {
                Log.d("rvHoriCe", "value: $_clicked")
                holder.ll_details.setBackgroundResource(R.drawable.circle1)
                holder.string_Value.setTextColor(Color.parseColor("#FFFFFF"))
                holder.number_Value.setTextColor(Color.parseColor("#B32D7D"))
                Log.d("vval", "3")
            }
            _nl -> {
                Log.d("rvHoriCe", "value: $_clicked")
                holder.ll_details.setBackgroundResource(R.drawable.circle)
                holder.string_Value.setTextColor(Color.parseColor("#E0ABCB"))
                holder.number_Value.setTextColor(Color.parseColor("#B32D7D"))
                Log.d("vval", "3")
            }
            position == previousPosition -> {
                holder.ll_details.setBackgroundResource(R.drawable.circle)
                holder.string_Value.setTextColor(Color.parseColor("#E0ABCB"))
                holder.number_Value.setTextColor(Color.parseColor("#B32D7D"))
                Log.d("vval", "4")
            }
            else -> {
                holder.ll_details.setBackgroundResource(R.drawable.circle)
                holder.string_Value.setTextColor(Color.parseColor("#E0ABCB"))
                holder.number_Value.setTextColor(Color.parseColor("#B32D7D"))
                Log.d("vval", "5")
            }
        }
        holder.itemView.setOnClickListener {
            if (dModel.application != "0") {
                //call?.onClickBackgroundChange(position)
                selectedPosition = position
                notifyItemChanged(selectedPosition)
                deselectPrevious(position)
                _clicked = true
            }
        }
    }

    private fun deselectPrevious(position: Int) {
        val prevPos = previousPosition
        previousPosition = position
        notifyItemChanged(prevPos)
        notifyItemChanged(previousPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderHorizontal {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.custom_hori_data, parent, false)
        return DataAdapterHorizontal.MyViewHolderHorizontal(itemView)
    }

    override fun getItemCount(): Int {
        return applicantList?.size as Int
    }

    fun add(pos: Int, r: DataModelHorizontal) {
        if (applicantList?.size!! <= 7) {
            applicantList?.add(pos, r)
            //applicantList!!.reverse()
            notifyItemInserted(pos)
        } else {
            Toast.makeText(context, "can't add", Toast.LENGTH_SHORT).show()
        }
    }

    class MyViewHolderHorizontal(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        var number_Value: TextView = itemView.findViewById(R.id.tv_university)
        var string_Value: TextView = itemView.findViewById(R.id.value_TV)
        var ivFastFor: ImageView = itemView.findViewById(R.id.iv_fast_for)
        var ll_details: RelativeLayout = itemView.findViewById(R.id.ll_item_details)

    }
}