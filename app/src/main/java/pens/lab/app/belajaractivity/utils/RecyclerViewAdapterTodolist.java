package pens.lab.app.belajaractivity.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pens.lab.app.belajaractivity.R;
import pens.lab.app.belajaractivity.model.Task;

public class RecyclerViewAdapterTodolist extends RecyclerView.Adapter<RecyclerViewAdapterTodolist.MyViewHolder> {
    private static ArrayList<Task> mDataset;
    private static RecyclerViewAdapterTodolist.MyClickListener myClickListener;
    private static RecyclerViewAdapterTodolist.MyLongClickListener myLongClickListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tvTitle;
        TextView tvDescription;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTodolistTitle);
            tvDescription = (TextView) itemView.findViewById(R.id.tvTodolistDescription);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position =getAdapterPosition();
            myClickListener.onItemClick(position, view);
        }

        @Override
        public boolean onLongClick(View view) {
            int position =getAdapterPosition();
            myLongClickListener.onItemLongClick(position, view);
            return true;
        }
    }

    public RecyclerViewAdapterTodolist(ArrayList<Task> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public RecyclerViewAdapterTodolist.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item_todolist, parent, false);
        RecyclerViewAdapterTodolist.MyViewHolder myViewHolder = new RecyclerViewAdapterTodolist.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterTodolist.MyViewHolder holder, int position) {
        holder.tvTitle.setText(mDataset.get(position).getTitle());
        holder.tvDescription.setText(mDataset.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setOnItemClickListener(RecyclerViewAdapterTodolist.MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
    public void setOnItemLongClickListener(RecyclerViewAdapterTodolist.MyLongClickListener myLongClickListener) {
        this.myLongClickListener = myLongClickListener;
    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
    public interface MyLongClickListener {
        public void onItemLongClick(int position, View v);
    }
}
