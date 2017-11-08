package com.example.jasonzhang.cs242;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jasonzhang on 10/25/17.
 */

public class Repo extends Fragment {
    ListView l;
    public ArrayList<String> repos = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.repo, container, false);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            repos = getArguments().getStringArrayList("repos");
        }
        l = (ListView) rootView.findViewById(R.id.repolistView);
        l.setAdapter(new MyListAdapter(getActivity(), R.layout.repo_item, repos));


        return rootView;
    }

    private class MyListAdapter extends ArrayAdapter<String> {
        private int layout;
        public MyListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if (convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.file = (TextView) convertView.findViewById(R.id.textView7);
                viewHolder.file.setText(repos.get(position));

                convertView.setTag(viewHolder);
            }
            else{
                mainViewholder = (ViewHolder) convertView.getTag();
                mainViewholder.file.setText(getItem(position));
            }
            return convertView;
        }
    }
    public class ViewHolder {

        TextView file;

    }
}
