package RecyclerView_Tasks;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import cat.udl.tidic.amd.beenote.R;
import cat.udl.tidic.amd.beenote.models.CourseModel;
import cat.udl.tidic.amd.beenote.models.TasksModel;


public class Tasks_Adapter extends ListAdapter<TasksModel, Tasks_Adapter.EventHolder> {

    private OnItemClickListener eventItemListener;
    private final static String TAG = "Tasks_Adapter";


    public Tasks_Adapter(@NonNull DiffUtil.ItemCallback<TasksModel> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tasks_list, parent, false);
        return new EventHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {

        TasksModel currentEvent = getItem(position);
        holder.textViewTitle.setText(currentEvent.getTitle());
        holder.textViewDetails.setText(currentEvent.getDetails());
        holder.textViewTime.setText(currentEvent.getDeadline());
    }



    public TasksModel getEventAt(int position) {
        Log.d(TAG, "Position: "+ position);
        Log.d(TAG, "Tarea: "+ getItem(position).getTitle());
        return getItem(position);
    }



    class EventHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDetails;
        private TextView textViewTime;

        public EventHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.titol_tasks);
            textViewDetails = itemView.findViewById(R.id.detalls);
            textViewTime = itemView.findViewById(R.id.data);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (eventItemListener != null && position != RecyclerView.NO_POSITION) {
                        eventItemListener.onItemClick(getItem(position));
                    }
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(TasksModel event);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.eventItemListener = listener;
    }

}