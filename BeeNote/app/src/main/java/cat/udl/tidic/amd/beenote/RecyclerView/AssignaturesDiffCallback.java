package cat.udl.tidic.amd.beenote.RecyclerView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import cat.udl.tidic.amd.beenote.models.Assignatures_Model;


public class AssignaturesDiffCallback extends DiffUtil.ItemCallback<Assignatures_Model> {


    @Override
    public boolean areItemsTheSame(@NonNull Assignatures_Model oldItem, @NonNull Assignatures_Model newItem) {
        return oldItem.getUserId() == newItem.getUserId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Assignatures_Model oldItem, @NonNull Assignatures_Model newItem) {
        return oldItem.equals(newItem);
    }
}
