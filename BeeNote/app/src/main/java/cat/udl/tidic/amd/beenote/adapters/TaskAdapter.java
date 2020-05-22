package cat.udl.tidic.amd.beenote.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import cat.udl.tidic.amd.beenote.R;
import cat.udl.tidic.amd.beenote.models.CourseModel;
import cat.udl.tidic.amd.beenote.models.Task;
import cat.udl.tidic.amd.beenote.models.TaskStatusEnum;
import cat.udl.tidic.amd.beenote.utils.Utils;


public class TaskAdapter extends ListAdapter<Task, TaskAdapter.TaskHolder> {

    private OnItemClickListener eventItemListener;
    private final static String TAG = "Assignatura_Adapter";
    private String CourseID;

    public TaskAdapter(@NonNull DiffUtil.ItemCallback<Task> diffCallback) {
        super(diffCallback);
    }


    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_task_item_list, parent, false);
        return new TaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {

        Task currentTask = getItem(position);
        holder.task_deadline_day.setText(String.valueOf(Utils.get_day(currentTask.getDeadline())));
        holder.task_deadline_month.setText(String.valueOf(Utils.get_month(currentTask.getDeadline())));

        holder.task_name.setText(currentTask.getTittle());
        holder.task_details.setText(currentTask.getDetails());
        Float mark = currentTask.getMark();
        holder.task_percentage.setText("Completat:" +"0%");

        holder.task_mark.setText("Nota: " + String.valueOf(currentTask.getMark()));
        if(mark > 0){
            holder.task_percentage.setText("Completat: " +"100%");
        }else{
            holder.task_percentage.setText("Completat:" +"0%");
        }
        holder.task_total_points.setText(String.valueOf(currentTask.getPoints())+"("+ currentTask.getPercent()+"%)");


        holder.task_status.setText(currentTask.getStatus().getName());
        holder.task_status.setTextColor(ContextCompat.getColor(holder.task_status.getContext(),
                TaskStatusEnum.getColourResource(currentTask.getStatus())));

        holder.taskColour.setBackgroundColor(ContextCompat.getColor(holder.task_status.getContext(),
                TaskStatusEnum.getColourResource(currentTask.getStatus())));

    }

    public String getCourseID()
    {
        return CourseID;
    }



    class TaskHolder extends RecyclerView.ViewHolder {
        private TextView task_deadline_day;
        private TextView task_deadline_month;
        private TextView task_status;
        private TextView task_name;
        private TextView task_details;
        private TextView task_mark;
        private TextView task_total_points;
        private TextView task_percentage;
        private TextView taskColour;


        public TaskHolder(View itemView) {
            super(itemView);
            task_deadline_day = itemView.findViewById(R.id.task_deadline_day);
            task_deadline_month = itemView.findViewById(R.id.task_deadline_month);
            task_status = itemView.findViewById(R.id.task_status);
            task_name = itemView.findViewById(R.id.task_name);
            task_details = itemView.findViewById(R.id.task_details);
            task_mark = itemView.findViewById(R.id.task_mark);
            task_total_points = itemView.findViewById(R.id.task_total_points);
            task_percentage = itemView.findViewById(R.id.task_percentage);
            taskColour = itemView.findViewById(R.id.task_colour);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (eventItemListener != null && position != RecyclerView.NO_POSITION) {

                    }
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(CourseModel event);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.eventItemListener = listener;
    }

}