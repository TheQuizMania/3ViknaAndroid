package a.b.c.quizmania.db;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import a.b.c.quizmania.Entities.UserListItem;
import a.b.c.quizmania.R;

public class UsersRVAdapter extends RecyclerView.Adapter<UsersRVAdapter.ViewHolder> {

    // Variables
    private List<UserListItem> mUsers;
    private LayoutInflater mInflater;
    private ItemClickListener mListener;

    public UsersRVAdapter(Context context, List<UserListItem> users) {
        this.mUsers = users;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public UsersRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.user_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UsersRVAdapter.ViewHolder holder, int position) {
        String challenged = mUsers.get(position).getDisplayName();
        holder.displayNameTxtView.setText(challenged);
    }

    public String getDisplayName(int position) {
        // Returns the display name of the user
        return mUsers.get(position).getDisplayName();
    }

    @Override
    public int getItemCount() {
        // Returns the number of users
        return mUsers.size();
    }

    public UserListItem getUser(int position) {
        // Returns the user
        return mUsers.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Views
        public TextView displayNameTxtView;

        ViewHolder(View itemView) {
            super(itemView);
            // Finding views
            displayNameTxtView = itemView.findViewById(R.id.username_user_list_rv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Sets on click listener if there is none
            if(mListener != null) {
                mListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
