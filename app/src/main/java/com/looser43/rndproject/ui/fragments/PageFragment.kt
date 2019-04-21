package com.looser43.rndproject.ui.fragments


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.daimajia.swipe.util.Attributes
import com.looser43.rndproject.R
import com.looser43.rndproject.models.Model
import com.looser43.rndproject.ui.adapter.RecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_page.*
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 *
 */
class PageFragment : Fragment() {

    private var mPage: Int = 0
    private lateinit var mRandom: Random
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var list: ArrayList<Model>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //mPage = arguments!!.getInt(ARG_PAGE)
        // Initialize a new Random instance

        //list = Arrays.asList(*(resources.getStringArray(R.array.days_names)))

        mRandom = Random()
        // Initialize the handler instance
        mHandler = Handler()
    }

    private fun getListData(): List<Model> {
        list = ArrayList()
        for (i in 1..15) {
            list.add(Model("TextView $i"))
        }
        return list
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_page, container, false)
    }

    override fun onResume() {
        super.onResume()
        btnSelectAll?.setOnClickListener { adapter.selectAll() }
        btnClearAll?.setOnClickListener {
            adapter.clearAll()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forceRedraw()
        swipe_refresh_layout.setOnRefreshListener {
            // Initialize a new Runnable

            if (swipe_refresh_layout.isRefreshing) {
                recyclerView.adapter = null
                recyclerView.layoutManager = null
                Toast.makeText(activity?.applicationContext, "refreshing...", Toast.LENGTH_SHORT).show()
            }
            mRunnable = Runnable {
                forceRedraw()
                Toast.makeText(activity?.applicationContext, "refreshing DONE...", Toast.LENGTH_SHORT).show()
                // Hide swipe to refresh icon animation
                swipe_refresh_layout.isRefreshing = false
            }

            // Execute the task after specified time
            mHandler.postDelayed(
                mRunnable,
                (1000).toLong() // Delay 1 to 5 seconds
            )
        }
    }

    private fun forceRedraw() {
        layoutManager = LinearLayoutManager(activity?.applicationContext)
        recyclerView.layoutManager = layoutManager
        adapter = RecyclerViewAdapter(getListData() as ArrayList<Model>, activity!!)
        adapter.mode = Attributes.Mode.Multiple
        //recyclerView.itemAnimator = DefaultItemAnimator()
        //recyclerView.isNestedScrollingEnabled = true // default is true
        //recyclerView.addItemDecoration(DividerItemDecoration(activity?.applicationContext, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}
