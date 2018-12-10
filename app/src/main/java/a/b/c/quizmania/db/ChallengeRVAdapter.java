package a.b.c.quizmania.db;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import a.b.c.quizmania.Entities.Challenge;
import a.b.c.quizmania.R;

public class ChallengeRVAdapter extends RecyclerView.Adapter<ChallengeRVAdapter.ViewHolder> {

//    private List<UserListItem> mUsers;
    private List<Challenge> mChallenges;
    private LayoutInflater mInflater;
    private ChallengeRVAdapter.ItemClickListener mListener;

    public ChallengeRVAdapter(Context context, List<Challenge> Challenges) {
//        this.mUsers = users;
        mChallenges = Challenges;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ChallengeRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = mInflater.inflate(R.layout.user_list_layout, parent, false);
        View view = mInflater.inflate(R.layout.challenge_list_layout, parent, false);
        return new ChallengeRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChallengeRVAdapter.ViewHolder holder, int position) {
//        String challenged = mUsers.get(position).getDisplayName();
//        holder.displayNameTxtView.setText(challenged);

        String challenger = mChallenges.get(position).getChallenger().getDisplayName();
        String challengeInfo = "Category: " + mChallenges.get(position).getCategory() +
                " and Difficulty: " + mChallenges.get(position).getDifficulty();

        holder.Challenger.setText(challenger);
        holder.ChallengeInfo.setText(challengeInfo);

    }

    @Override
    public int getItemCount() {
        return mChallenges.size();
    }

    public Challenge getChallenge(int position) {
//        return mUsers.get(position);
        return mChallenges.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        public TextView displayNameTxtView;
        public TextView Challenger;
        public TextView ChallengeInfo;

        ViewHolder(View itemView) {
            super(itemView);
            Challenger = itemView.findViewById(R.id.challenger);
            ChallengeInfo = itemView.findViewById(R.id.challenge_info);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null) {
                mListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public void setClickListener(ChallengeRVAdapter.ItemClickListener itemClickListener) {
        this.mListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
