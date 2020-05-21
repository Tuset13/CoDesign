package com.example.codesign;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.codesign.Classes.Projectes;
import com.example.codesign.Projecte.ProjectActivity;

import java.util.List;

public class ProjectesListAdapter extends RecyclerView.Adapter<ProjectesListAdapter.ViewHolder> {

    public List<Projectes> projectesList;
    public Context context;

    public ProjectesListAdapter(Context context, List<Projectes> projectesList){
        this.projectesList = projectesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.nameText.setText(projectesList.get(position).getProjectName());

        final String project_id = projectesList.get(position).projecteId;

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ProjectActivity.class);
                i.putExtra(context.getString(R.string.id_key), project_id);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public TextView nameText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            nameText = mView.findViewById(R.id.gamedata1);
        }
    }
}
