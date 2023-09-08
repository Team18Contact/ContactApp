package com.example.contactapp.contact

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.net.Uri
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ContactListItemHelper(private val context: Context, private val fragment: ContactListFragment): ItemTouchHelper.Callback() {
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
            val view = (viewHolder as ContactRecyclerViewAdapter.RecyclerHolder).linearLayout
            getDefaultUIUtil().onDraw(canvas, recyclerView, view, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun clearView(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ) {
        super.clearView(recyclerView, viewHolder)
        getDefaultUIUtil().clearView((viewHolder as ContactRecyclerViewAdapter.RecyclerHolder).linearLayout)
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
        val contactViewHolder = viewHolder as ContactRecyclerViewAdapter.RecyclerHolder
        val phoneNumber = contactViewHolder.contactNumber

        if(direction == ItemTouchHelper.RIGHT) {
            val callUriSwipedPerson = Uri.parse("tel:${phoneNumber}")
            context.startActivity(Intent(Intent.ACTION_CALL, callUriSwipedPerson))
            fragment.updateSwipeItem(contactViewHolder)
        }
    }
}