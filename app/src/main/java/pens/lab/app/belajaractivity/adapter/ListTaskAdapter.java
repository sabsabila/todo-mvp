package pens.lab.app.belajaractivity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;

import java.util.List;

import pens.lab.app.belajaractivity.R;
import pens.lab.app.belajaractivity.model.Task;

public class ListTaskAdapter extends RecyclerView.Adapter<ListTaskAdapter.MyViewHolder> {
    private static List<Task> mDataset;
    private static ListTaskAdapter.MyClickListener myClickListener;
    private static ListTaskAdapter.MyOnCheckedListener myOnCheckedListener;
    private final String tag;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{
        TextView tvTitle;
        TextView tvDescription;
        CheckBox cbTask;
        ImageButton editBtn, deleteBtn, shareBtn;
        SwipeLayout swipeLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            initElement();
            initActionListener();
        }

        private void initActionListener() {
            cbTask.setOnCheckedChangeListener(this);
            editBtn.setOnClickListener(this);
            deleteBtn.setOnClickListener(this);
            shareBtn.setOnClickListener(this);
        }

        private void initElement(){
            tvTitle = (TextView) itemView.findViewById(R.id.tvTodolistTitle);
            tvDescription = (TextView) itemView.findViewById(R.id.tvTodolistDescription);
            cbTask = (CheckBox) itemView.findViewById(R.id.cbTask);
            editBtn = (ImageButton) itemView.findViewById(R.id.edit_btn);
            deleteBtn = (ImageButton) itemView.findViewById(R.id.delete_btn);
            shareBtn = (ImageButton) itemView.findViewById(R.id.share_btn);
            swipeLayout =  (SwipeLayout) itemView.findViewById(R.id.swipeLayout);
            swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        }
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if(view.getId() == editBtn.getId())
                myClickListener.onEditClick(position, view, tag);
            if(view.getId() == deleteBtn.getId())
                myClickListener.onDeleteClick(position, view, tag);
            if(view.getId() == shareBtn.getId())
                myClickListener.onShareClick(position, view, tag);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int idx = getAdapterPosition();
            myOnCheckedListener.onItemChecked(idx, buttonView, isChecked, tag);
        }
    }

    public ListTaskAdapter(List<Task> myDataset, ListTaskAdapter.MyClickListener onClickListener,
                           ListTaskAdapter.MyOnCheckedListener checkedListener, String tag) {
        mDataset = myDataset;
        myClickListener = onClickListener;
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

    public void setOnCheckedChangeListener(ListTaskAdapter.MyOnCheckedListener myCheckChangedListener) {
        this.myOnCheckedListener = myCheckChangedListener;
    }

    public interface MyClickListener {
        void onEditClick(int position, View v, String tag);
        void onDeleteClick(int position, View v, String tag);
        void onShareClick(int position, View v, String tag);
    }

    public interface MyOnCheckedListener{
        void onItemChecked(int id, CompoundButton buttonView, boolean isChecked, String tag);
    }
}
