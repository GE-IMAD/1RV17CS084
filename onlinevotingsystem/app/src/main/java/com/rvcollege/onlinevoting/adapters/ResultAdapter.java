package com.rvcollege.onlinevoting.adapters;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rvcollege.loginregister.R;
import com.rvcollege.onlinevoting.model.User;

import java.util.List;



public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.UserViewHolder> {

    private List<User> listUsers;

    public ResultAdapter(List<User> listUsers) {
        this.listUsers = listUsers;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vote_recycler, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.textViewPartyName.setText(listUsers.get(position).getPartyName());
        holder.textViewConstituency.setText(listUsers.get(position).getConstituency());
        holder.textViewVotes.setText(String.valueOf(listUsers.get(position).getVotes()));
    }

    @Override
    public int getItemCount() {
        Log.v(UsersRecyclerAdapter.class.getSimpleName(),""+listUsers.size());
        return listUsers.size();
    }


    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewPartyName;
        public AppCompatTextView textViewConstituency;
        public AppCompatTextView textViewVotes;

        public UserViewHolder(View view) {
            super(view);
            textViewPartyName = (AppCompatTextView) view.findViewById(R.id.textViewPartyName);
            textViewConstituency = (AppCompatTextView) view.findViewById(R.id.textViewConstituency);
            textViewVotes = (AppCompatTextView) view.findViewById(R.id.textViewVotes);
        }
    }
}