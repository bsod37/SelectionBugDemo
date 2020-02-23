package com.example.myapplication

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.afollestad.dragselectrecyclerview.DragSelectReceiver
import com.afollestad.dragselectrecyclerview.DragSelectTouchListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SelectionChangeListener {

    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecycler()
        title = "Selected 0"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clear) {
            adapter.clearSelection()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSelectionChanged(selectedItemsCount: Int) {
        title = "Selected $selectedItemsCount"
    }

    private fun setupRecycler() {
        val range = (1..1000)
        val data = range.map { Item(it, false) }

        adapter = RecyclerAdapter(data, this)

        recycler.layoutManager = GridLayoutManager(this, SPAN_COUNT)
        recycler.adapter = adapter
        recycler.addItemDecoration(RecyclerItemDecorator(6.toPx, SPAN_COUNT))

        val touchListener = DragSelectTouchListener.create(this, adapter)
        recycler.addOnItemTouchListener(touchListener)
        adapter.touchListener = touchListener
    }

    private val Int.toPx: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    private companion object {

        private const val SPAN_COUNT = 3
    }
}
