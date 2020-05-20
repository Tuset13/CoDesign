package com.example.codesign;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProjectesListAdapter extends RecyclerView.Adapter<ProjectesListAdapter.ViewHolder> {

    public List<Projectes> projectesList;

    public ProjectesListAdapter(List<Projectes> projectesList){
        this.projectesList = projectesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_project_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.nameText.setText(projectesList.get(position).getProjectName());

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
