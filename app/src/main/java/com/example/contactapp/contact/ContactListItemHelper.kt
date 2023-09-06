package com.example.contactapp.contact

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

//class ContactListItemHelper(private val context: Context): ItemTouchHelper.Callback() {
class ContactListItemHelper(
    dragDirs: Int,
    swipeDirs: Int,
    private val onSwiped: (Int) -> Unit
) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

    override fun onChildDraw(
        canvas: Canvas, recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val view = (viewHolder as ContactRecyclerViewAdapter.Holder).linearLayout
            getDefaultUIUtil().onDraw(canvas, recyclerView, view, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun clearView(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ) {
        super.clearView(recyclerView, viewHolder)
        getDefaultUIUtil().clearView((viewHolder as ContactRecyclerViewAdapter.Holder).linearLayout)
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.RIGHT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onSwiped(viewHolder.adapterPosition)
    }

//    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//        val contactViewHolder = viewHolder as ContactRecyclerViewAdapter.Holder
//        val phoneNumber = contactViewHolder.contactNumber
//
//        if(direction == ItemTouchHelper.RIGHT) {
//            val callUriSwipedPerson = Uri.parse("tel:${phoneNumber}")
//            context.startActivity(Intent(Intent.ACTION_CALL, callUriSwipedPerson))
//        }
//    }
}