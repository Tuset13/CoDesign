package com.example.codesign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

public class InvitacionsListFragment extends Fragment {

    public InvitacionsListFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_project_list, container, false);

        String[] exemples = {"Projecte Alpha", "Projecte Beta", "Projecte Gamma"};

        ListView listView = view.findViewById(R.id.idListView);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, exemples);

        listView.setAdapter(listViewAdapter);

        return view;
    }
}
