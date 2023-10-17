package com.example.quizapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.R;
import com.example.quizapp.data.QuizAttempt;

import java.util.List;

import static com.example.quizapp.utils.DateUtils.formatDate;

public class QuizHistoryAdapter extends RecyclerView.Adapter<QuizHistoryAdapter.QuizAttemptViewHolder> {

    private final List<QuizAttempt> quizAttempts;

    public QuizHistoryAdapter(List<QuizAttempt> quizAttempts) {
        this.quizAttempts = quizAttempts;
    }

    @NonNull
    @Override
    public QuizAttemptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_quiz_history, parent, false);
        return new QuizHistoryAdapter.QuizAttemptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizAttemptViewHolder holder, int position) {

        QuizAttempt item = quizAttempts.get(position);

        holder.tvCategory.setText(String.valueOf(item.getCategory()));
        holder.tvScore.setText(String.valueOf(item.getScore()));
        holder.tvDate.setText(formatDate(item.getAttemptTime()));

        holder.cvParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO NOTHING
            }
        });

    }

    @Override
    public int getItemCount() {
        return quizAttempts.size();
    }

    public static class QuizAttemptViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCategory, tvScore, tvDate;
        public CardView cvParent;

        public QuizAttemptViewHolder(View v) {
            super(v);
            tvCategory = v.findViewById(R.id.textViewCategory);
            tvScore = v.findViewById(R.id.textViewScore);
            tvDate = v.findViewById(R.id.textViewDate);
            cvParent = v.findViewById(R.id.cvQuizHistoryItem);

        }
    }

}
