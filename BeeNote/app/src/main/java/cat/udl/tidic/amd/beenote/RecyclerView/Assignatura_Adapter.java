package cat.udl.tidic.amd.beenote.RecyclerView;


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


public class Assignatura_Adapter extends ListAdapter<CourseModel, Assignatura_Adapter.EventHolder> {

    private OnItemClickListener eventItemListener;
    private final static String TAG = "Assignatura_Adapter";


    public Assignatura_Adapter(@NonNull DiffUtil.ItemCallback<CourseModel> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_assignatures_list, parent, false);
        return new EventHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {

        CourseModel currentEvent = getItem(position);
        holder.textViewTitle.setText(currentEvent.getTitle());

    }



    public CourseModel getEventAt(int position) {
        Log.d(TAG, "Position: "+ position);
        Log.d(TAG, "Asignatura: "+ getItem(position).getTitle());
        return getItem(position);
    }



    class EventHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        //private TextView textViewDescription;
        // private RatingBar ratingAvaluation;

        public EventHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.title);
            /*textViewDescription = itemView.findViewById(R.id.description);
            ratingAvaluation = itemView.findViewById(R.id.avaluation);*/

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
        void onItemClick(CourseModel event);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.eventItemListener = listener;
    }

}