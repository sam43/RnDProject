package com.looser43.rndproject


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.looser43.rndproject.callbacks.RecyclerItemTouchHelperListener
import com.looser43.rndproject.swipeUtils.RecyclerItemTouchHelper
import kotlinx.android.synthetic.main.fragment_page.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 *
 */
class PageFragment : Fragment(), RecyclerItemTouchHelperListener {
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {

    }

    companion object {
        val ARG_PAGE = "ARG_PAGE"

        fun newInstance(page: Int): PageFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            val fragment = PageFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var mPage: Int = 0
    private lateinit var mRandom: Random
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var list: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //mPage = arguments!!.getInt(ARG_PAGE)
        // Initialize a new Random instance

        list = Arrays.asList(*(resources.getStringArray(R.array.days_names)))

        mRandom = Random()
        // Initialize the handler instance
        mHandler = Handler()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        forceRedraw()
        val itemTouchHelperCallback = RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT, this)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)

        val itemTouchHelperCallback1 = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Row is swiped from recycler view
                // remove it from adapter
                if (direction == ItemTouchHelper.RIGHT) {
                    // remove the item from recycler view
                    Toast.makeText(activity?.applicationContext, "swiping L2R", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity?.applicationContext, "swiping R2L", Toast.LENGTH_SHORT).show()
                }

            }

        }

        // attaching the touch helper to recycler view
        ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(recyclerView)

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
        adapter = RecyclerViewAdapter(list)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.isNestedScrollingEnabled = true // default is true
        //recyclerView.addItemDecoration(DividerItemDecoration(activity?.applicationContext, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}
