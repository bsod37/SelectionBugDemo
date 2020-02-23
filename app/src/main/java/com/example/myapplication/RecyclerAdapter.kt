package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.dragselectrecyclerview.DragSelectReceiver
import com.afollestad.dragselectrecyclerview.DragSelectTouchListener
import kotlinx.android.synthetic.main.recycler_item.view.*

class RecyclerAdapter(private val data: List<Item>, private val listener: SelectionChangeListener)
    : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(), DragSelectReceiver {

    private val selectedIds = HashSet<Int>()
    lateinit var touchListener: DragSelectTouchListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(data[position])
    }

    override fun setSelected(index: Int, selected: Boolean) {
        val item = data[index]
        if(selected && !item.isSelected) {
            setItemSelection(index, true)
        } else if(!selected && item.isSelected) {
            setItemSelection(index, false)
        }
    }

    override fun isSelected(index: Int): Boolean = selectedIds.contains(index)

    override fun isIndexSelectable(index: Int): Boolean = true

    fun clearSelection() {
        selectedIds.clear()
        for (item in data) {
            item.isSelected = false
        }
        listener.onSelectionChanged(0)
        notifyDataSetChanged()
    }

    private fun setItemSelection(position: Int, selected: Boolean) {
        val item = data[position]
        item.isSelected = selected
        if (selected) {
            selectedIds.add(item.id)
        } else {
            selectedIds.remove(item.id)
        }
        listener.onSelectionChanged(selectedIds.size)
        notifyItemChanged(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(item: Item) {
            itemView.title.text = item.id.toString()
            itemView.selection_icon.visibility = if (item.isSelected) View.VISIBLE else View.GONE
            itemView.setOnClickListener { setItemSelection(adapterPosition, !selectedIds.contains(item.id)) }
            itemView.setOnLongClickListener { touchListener.setIsActive(true, adapterPosition) }
        }
    }
}