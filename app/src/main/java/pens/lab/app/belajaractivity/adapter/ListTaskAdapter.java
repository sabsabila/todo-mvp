package pens.lab.app.belajaractivity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pens.lab.app.belajaractivity.R;
import pens.lab.app.belajaractivity.model.Task;

public class ListTaskAdapter extends RecyclerView.Adapter<ListTaskAdapter.MyViewHolder> {
    private static List<Task> mDataset;
    private static ListTaskAdapter.MyClickListener myClickListener;
    private static ListTaskAdapter.MyLongClickListener myLongClickListener;
    private static ListTaskAdapter.MyOnCheckedListener myOnCheckedListener;
    private final String tag;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, CompoundButton.OnCheckedChangeListener {
        TextView tvTitle;
        TextView tvDescription;
        CheckBox cbTask;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTodolistTitle);
            tvDescription = (TextView) itemView.findViewById(R.id.tvTodolistDescription);
            cbTask = (CheckBox) itemView.findViewById(R.id.cbTask);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            cbTask.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            myClickListener.onItemClick(position, view, tag);
        }

        @Override
        public boolean onLongClick(View view) {
            int position =  getAdapterPosition();
            myLongClickListener.onItemLongClick(position, view, tag);
            return true;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int idx = getAdapterPosition();
            myOnCheckedListener.onItemChecked(idx, buttonView, isChecked, tag);
        }
    }

    public ListTaskAdapter(List<Task> myDataset, ListTaskAdapter.MyClickListener onClickListener,
                           ListTaskAdapter.MyLongClickListener longClickListener,
                           ListTaskAdapter.MyOnCheckedListener checkedListener, String tag) {
        mDataset = myDataset;
        myClickListener = onClickListener;
        myLongClickListener = longClickListener;
        myOnCheckedListener = checkedListener;
        this.tag = tag;
    }

    @Override
    public ListTaskAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item_todolist, parent, false);
        ListTaskAdapter.MyViewHolder myViewHolder = new ListTaskAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ListTaskAdapter.MyViewHolder holder, int position) {
        holder.tvTitle.setText(mDataset.get(position).getTitle());
        holder.tvDescription.setText(mDataset.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setOnItemClickListener(ListTaskAdapter.MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
    public void setOnItemLongClickListener(ListTaskAdapter.MyLongClickListener myLongClickListener) {
        this.myLongClickListener = myLongClickListener;
    }
    public void setOnCheckedChangeListener(ListTaskAdapter.MyOnCheckedListener myCheckChangedListener) {
        this.myOnCheckedListener = myCheckChangedListener;
    }
    public interface MyClickListener {
        void onItemClick(int position, View v, String tag);
    }
    public interface MyLongClickListener {
        void onItemLongClick(int position, View v, String tag);
    }

    public interface MyOnCheckedListener{
        void onItemChecked(int id, CompoundButton buttonView, boolean isChecked, String tag);
    }
}
