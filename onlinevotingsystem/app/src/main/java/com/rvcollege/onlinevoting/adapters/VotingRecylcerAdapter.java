package com.rvcollege.onlinevoting.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rvcollege.loginregister.R;
import com.rvcollege.onlinevoting.activities.Voted;
import com.rvcollege.onlinevoting.activities.Voting;
import com.rvcollege.onlinevoting.model.User;
import com.rvcollege.onlinevoting.sql.ConstDatabase;
import com.rvcollege.onlinevoting.sql.DatabaseHelper;

import java.util.List;

public class VotingRecylcerAdapter extends RecyclerView.Adapter<VotingRecylcerAdapter.UserViewHolder> {

    public List<User> listUsers;
    private Context context;
    public ConstDatabase databaseHelper;
    public DatabaseHelper dbhelper;
    private String email;
    // private AppCompatButton appCompatDelete;

    public VotingRecylcerAdapter(Context context,List<User> listUsers, ConstDatabase dbhelper,DatabaseHelper dhelper) {
        this.context=context;
        this.listUsers = listUsers;
        this.databaseHelper=dbhelper;
        this.dbhelper=dhelper;
        //dbhelper=new DatabaseHelper();
        SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(context);
        email=pref.getString("email","");
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_party_recycler, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder,final int position) {

        holder.textViewName.setText(listUsers.get(position).getPartyName());
       /* holder.textViewEmail.setText(listUsers.get(position).getEmail());
        holder.textViewPassword.setText(listUsers.get(position).getPassword());
        holder.textViewVoterId.setText(listUsers.get(position).getVoterId());
        holder.textViewAdhaar.setText(listUsers.get(position).getAdhaar());
        holder.textViewPhone.setText(listUsers.get(position).getPhone());
        holder.textViewConstituency.setText(listUsers.get(position).getConstituency());*/
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VoteUser(position);
                doneUser(email);
                Intent intentRegister = new Intent(context, Voted.class);
                context.startActivity(intentRegister);
                ((Voting) context).finish();

            }
        });
    }

    @Override
    public int getItemCount() {
        Log.v(UsersRecyclerAdapter.class.getSimpleName(),""+listUsers.size());
        return listUsers.size();
    }
    private void deleteUser(int position){
        databaseHelper.deleteUser(listUsers.get(position));
        listUsers.remove(position);
        Voting.notifyAdapter();
    }
    private void VoteUser(int position){
        databaseHelper.addVote(listUsers.get(position).getPartyName(),listUsers.get(position).getConstituency());

    }
private void doneUser(String email){

        dbhelper.setFlag(email);
}
    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewName;
        public AppCompatTextView textViewEmail;
        public AppCompatTextView textViewPassword;
        public AppCompatTextView textViewVoterId;
        public AppCompatTextView textViewAdhaar;
        public AppCompatTextView textViewPhone;
        public AppCompatTextView textViewConstituency;
        private AppCompatButton delete;

        public UserViewHolder(View view) {
            super(view);
            textViewName = (AppCompatTextView) view.findViewById(R.id.textViewName);
            /*textViewEmail = (AppCompatTextView) view.findViewById(R.id.textViewEmail);
            textViewPassword = (AppCompatTextView) view.findViewById(R.id.textViewPassword);
            textViewVoterId = (AppCompatTextView) view.findViewById(R.id.textViewVoterId);
            textViewAdhaar = (AppCompatTextView) view.findViewById(R.id.textViewAdhaar);
            textViewPhone = (AppCompatTextView) view.findViewById(R.id.textViewPhone);
            textViewConstituency = (AppCompatTextView) view.findViewById(R.id.textViewConstituency);*/
            delete=(AppCompatButton) view.findViewById(R.id.appCompatDelete);


        }

    }}
