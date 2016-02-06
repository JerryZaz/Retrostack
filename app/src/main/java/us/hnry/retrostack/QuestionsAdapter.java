package us.hnry.retrostack;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import us.hnry.retrostack.model.SOQuestion;

/**
 * Created by Henry on 2/5/2016.
 *
 */
public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder> {

    private List<SOQuestion.SOItem> items;

    public QuestionsAdapter(List<SOQuestion.SOItem> items) {
        this.items = items;
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent, false);
        return new QuestionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        SOQuestion.SOItem question = items.get(position);
        holder.titleTextView.setText(question.getTitle());
        holder.linkTextView.setText(question.getLink());
        holder.itemView.setTag(question);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void swapList(List<SOQuestion.SOItem> items) {
        if(this.items != null) {
            this.items.clear();
            this.items.addAll(items);
        } else {
            this.items = items;
        }
        notifyDataSetChanged();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView linkTextView;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.single_row_title);
            linkTextView = (TextView) itemView.findViewById(R.id.single_row_link);
        }
    }
}
