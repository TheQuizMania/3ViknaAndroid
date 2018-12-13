package a.b.c.quizmania.utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import a.b.c.quizmania.Entities.Challenge;
import a.b.c.quizmania.R;

/**
 * An adapter for the recycle viewer to display the users in UserListActivity
 */
public class ChallengeRVAdapter extends RecyclerView.Adapter<ChallengeRVAdapter.ViewHolder> {

    // Variables
    private List<Challenge> mChallenges;
    private LayoutInflater mInflater;
    private ChallengeRVAdapter.ItemClickListener mListener;


    public ChallengeRVAdapter(Context context, List<Challenge> Challenges) {
        mChallenges = Challenges;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ChallengeRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.challenge_list_layout, parent, false);
        return new ChallengeRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChallengeRVAdapter.ViewHolder holder, int position) {
        // Writes the information to the layout
        String challenger = mChallenges.get(position).getChallenger().getDisplayName();
        String challengeInfo = "Category: " + mChallenges.get(position).getCategory() +
                "\nDifficulty: " + mChallenges.get(position).getDifficulty();

        holder.Challenger.setText(challenger);
        holder.ChallengeInfo.setText(challengeInfo);

    }

    @Override
    public int getItemCount() {
        // Returns the number of challenges
        return mChallenges.size();
    }

    public Challenge getChallenge(int position) {
        // Returns the challenge at that position
        return mChallenges.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Views
        public TextView Challenger;
        public TextView ChallengeInfo;

        ViewHolder(View itemView) {
            super(itemView);
            // Find views
            Challenger = itemView.findViewById(R.id.challenger);
            ChallengeInfo = itemView.findViewById(R.id.challenge_info);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Set onClick listener if there is none
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
