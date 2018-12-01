package com.looser43.rndproject


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.util.Attributes
import com.looser43.rndproject.callbacks.RecyclerItemTouchHelperListener
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
        adapter = RecyclerViewAdapter(list, activity?.applicationContext)
        adapter.mode = Attributes.Mode.Multiple
        //recyclerView.itemAnimator = DefaultItemAnimator()
        //recyclerView.isNestedScrollingEnabled = true // default is true
        //recyclerView.addItemDecoration(DividerItemDecoration(activity?.applicationContext, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}
