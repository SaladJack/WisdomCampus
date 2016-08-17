package com.kai.wisdom_scut.controller.drag;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.kai.wisdom_scut.controller.adapter.DragAdapter;


public class DragItemCallBack extends ItemTouchHelper.Callback {

    private RecycleCallBack mCallBack;

    public DragItemCallBack(RecycleCallBack callBack) {
        this.mCallBack = callBack;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        int action = ItemTouchHelper.ACTION_STATE_IDLE | ItemTouchHelper.ACTION_STATE_DRAG;
//        return makeFlag(action,dragFlags);
        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        if (viewHolder.getItemViewType() == DragAdapter.NORMAL && target.getItemViewType() == DragAdapter.NORMAL) {
            mCallBack.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }else
            return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (viewHolder instanceof DragHolderCallBack){
            DragHolderCallBack holder = (DragHolderCallBack) viewHolder;
            holder.onSelect();
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        DragHolderCallBack holder = null;
        if (viewHolder instanceof  DragHolderCallBack){
           holder = (DragHolderCallBack) viewHolder;
        }
        if (!recyclerView.isComputingLayout()) {
            if (viewHolder instanceof DragHolderCallBack)
                holder.onClear();
        }
    }
}
