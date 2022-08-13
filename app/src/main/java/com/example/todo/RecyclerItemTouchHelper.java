package com.example.todo;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.Adaptor.TodoAdaptor;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private TodoAdaptor adaptor;

    public RecyclerItemTouchHelper(TodoAdaptor adaptor){
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adaptor= adaptor;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target){
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        if(direction==ItemTouchHelper.LEFT){
            AlertDialog.Builder builder = new AlertDialog.Builder(adaptor.getContext());
            builder.setTitle("Delete Task");
            builder.setMessage("Do you want to delete?");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            adaptor.deleteItem(position);
                        }
                    });

            builder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            adaptor.notifyItemChanged(viewHolder.getAdapterPosition());
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        }else{
            adaptor.editItem(position);
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dx, float dy, int actionState, boolean isCurrentlyActive){
        super.onChildDraw(c, recyclerView, viewHolder, dx, dy, actionState, isCurrentlyActive);

        Drawable icon;
        ColorDrawable background;

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;

        if(dx>0){
            icon = ContextCompat.getDrawable(adaptor.getContext(), R.drawable.ic_baseline_edit);
            background = new ColorDrawable(ContextCompat.getColor(adaptor.getContext(), R.color.green));
        }else{
            icon = ContextCompat.getDrawable(adaptor.getContext(), R.drawable.ic_baseline_delete);
            background = new ColorDrawable(Color.RED);
        }

        int iconMargin = (itemView.getHeight()- icon.getIntrinsicHeight())/2;
        int iconTop = itemView.getTop()+ (itemView.getHeight()- icon.getIntrinsicHeight())/2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        //swipe right
        if(dx>0){
            int iconLeft = itemView.getLeft()+iconMargin;
            int iconRight = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft()+((int)dx)+ backgroundCornerOffset, itemView.getBottom());
        }
        //swiping to left
        else if(dx<0){
            int iconLeft = itemView.getRight()-iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getRight() + ((int) dx) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else{
            background.setBounds(0,0,0,0);
        }
        background.draw(c);
        icon.draw(c);
    }


}
