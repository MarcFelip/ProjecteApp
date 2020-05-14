package RecyclerView_Tasks;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import cat.udl.tidic.amd.beenote.models.TasksModel;


public class TasksDiffCallback extends DiffUtil.ItemCallback<TasksModel> {


    @Override
    public boolean areItemsTheSame(@NonNull TasksModel oldItem, @NonNull TasksModel newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull TasksModel oldItem, @NonNull TasksModel newItem) {
        return oldItem.equals(newItem);
    }
}
