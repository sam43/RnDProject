package com.looser43.rndproject


import android.graphics.Canvas
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.looser43.rndproject.swipeController.SwipeController
import com.looser43.rndproject.swipeController.SwipeControllerActions
import kotlinx.android.synthetic.main.fragment_page.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 *
 */
class PageFragment : Fragment() {
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

    private var swipeController: SwipeController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //mPage = arguments!!.getInt(ARG_PAGE)
        // Initialize a new Random instance
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
        layoutManager = LinearLayoutManager(activity?.applicationContext)
        recyclerView.layoutManager = layoutManager
        adapter = RecyclerViewAdapter(resources.getStringArray(R.array.days_names))
        recyclerView.adapter = adapter
        setupRecyclerView()

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
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        adapter.notifyDataSetChanged()
    }


    /*private fun initSwipe() {
        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

                override fun onMove(
                    recyclerView: androidx.recyclerview.widget.RecyclerView,
                    viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
                    target: androidx.recyclerview.widget.RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(
                    viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
                    direction: Int
                ) {
                    if (direction == ItemTouchHelper.RIGHT) {
                        // remove the item from recycler view
                        Toast.makeText(activity?.applicationContext, "swiping L2R", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity?.applicationContext, "swiping R2L", Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: androidx.recyclerview.widget.RecyclerView,
                    viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    val corners = 16f
                    val itemView = viewHolder.itemView
                    val p = Paint()

                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                        if (dX > 0) {
                            val background = RectF(
                                itemView.left.toFloat(),
                                itemView.top.toFloat(),
                                itemView.left.toFloat() + dX,
                                itemView.bottom.toFloat()
                            )
                            p.color = Color.parseColor("#0E73A9")
                            c.drawRect(background, p)
                            c.drawRoundRect(background, corners, corners, p)
                            drawText("Next", c, background, p)

                        } else if (dX < 0) {
                            val background = RectF(
                                itemView.right.toFloat(),
                                itemView.top.toFloat(),
                                itemView.right.toFloat() + dX,
                                itemView.bottom.toFloat()
                            )
                            // p.color = Color.parseColor("#EF5350")
                            p.color = Color.parseColor("#EF5350")
                            //swipeToRestore = "Reject"
                            c.drawRect(background, p)
                            c.drawRoundRect(background, corners, corners, p)
                            drawText("Reject", c, background, p)
                        }
                    }
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }

                private fun drawText(text: String, c: Canvas, button: RectF, p: Paint) {
                    val textSize = 50F
                    p.color = Color.WHITE
                    p.isAntiAlias = true
                    p.textSize = textSize

                    val textWidth: Float = p.measureText(text)
                    c.drawText(text, button.centerX() - (textWidth + 10), button.centerY() + (textSize / 2), p)
                }

            }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(days_list_2)
    }*/
    /*private fun initSwipe() {
        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

                override fun onMove(
                    recyclerView: androidx.recyclerview.widget.RecyclerView,
                    viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
                    target: androidx.recyclerview.widget.RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(
                    viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
                    direction: Int
                ) {
                    if (direction == ItemTouchHelper.RIGHT) {
                        // remove the item from recycler view
                        Toast.makeText(activity?.applicationContext, "swiping L2R", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity?.applicationContext, "swiping R2L", Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: androidx.recyclerview.widget.RecyclerView,
                    viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    val corners = 16f
                    val itemView = viewHolder.itemView
                    val paint = Paint()

                    try {

                        val icon: Bitmap
                        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                            val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                            val width = height / 5
                            viewHolder.itemView.translationX = dX / 5

                            paint.color = Color.parseColor("#D32F2F")
                            val background = RectF(
                                itemView.right.toFloat() + dX / 5,
                                itemView.top.toFloat(),
                                itemView.right.toFloat(),
                                itemView.bottom.toFloat()
                            )
                            c.drawRect(background, paint)
                            icon = BitmapFactory.decodeResource(resources, R.drawable.ic_delete)
                            val icon_dest = RectF(
                                itemView.right + dX / 7,
                                itemView.top.toFloat() + width,
                                itemView.right.toFloat() + dX / 20,
                                itemView.bottom.toFloat() - width
                            )
                            c.drawBitmap(icon, null, icon_dest, paint)
                        } else {
                            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }

                private fun drawText(text: String, c: Canvas, button: RectF, p: Paint) {
                    val textSize = 50F
                    p.color = Color.WHITE
                    p.isAntiAlias = true
                    p.textSize = textSize

                    val textWidth: Float = p.measureText(text)
                    c.drawText(text, button.centerX() - (textWidth + 10), button.centerY() + (textSize / 2), p)
                }

            }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(days_list_2)
    }*/

    private fun setupRecyclerView() {

        swipeController = SwipeController(object : SwipeControllerActions() {
            override fun onRightClicked(position: Int) {
                adapter.remove(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, adapter.itemCount)
            }
        }, activity?.applicationContext)

        val itemTouchhelper = ItemTouchHelper(swipeController!!)
        itemTouchhelper.attachToRecyclerView(recyclerView)

        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                swipeController?.onDraw(c)
            }
        })
    }

}
