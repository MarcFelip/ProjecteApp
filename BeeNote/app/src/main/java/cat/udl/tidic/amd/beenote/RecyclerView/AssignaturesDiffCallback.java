package cat.udl.tidic.amd.beenote.RecyclerView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import cat.udl.tidic.amd.beenote.models.CourseModel;


public class AssignaturesDiffCallback extends DiffUtil.ItemCallback<CourseModel> {


    @Override
    public boolean areItemsTheSame(@NonNull CourseModel oldItem, @NonNull CourseModel newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull CourseModel oldItem, @NonNull CourseModel newItem) {
        return oldItem.equals(newItem);
    }
}
