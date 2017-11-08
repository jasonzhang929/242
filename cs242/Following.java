package com.example.jasonzhang.cs242;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jasonzhang on 10/25/17.
 */

public class Following extends Fragment {
    ListView l;
    public ArrayList<String> followings = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.following, container, false);
        followings.add("Jason");
        followings.add("Kelly");
        l = (ListView) rootView.findViewById(R.id.followinglistView);
        l.setAdapter(new Following.followAdapter(getActivity(), R.layout.follow_item, followings));
        return rootView;
    }

    private class followAdapter extends ArrayAdapter<String> {
        private int layout;
        public followAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }
        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            Following.ViewHolder mainViewholder = null;
            if (convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.avatar = (ImageView) convertView.findViewById(R.id.follower_avatar);
                viewHolder.user = (TextView) convertView.findViewById(R.id.follower_user);
                viewHolder.topage = (Button) convertView.findViewById(R.id.follower_topage);
                viewHolder.topage.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Button was clicked for listitem " + position, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), User.class);
                            startActivity(intent);

                    }
                });
                convertView.setTag(viewHolder);
            }
            else{
                mainViewholder = (Following.ViewHolder) convertView.getTag();
                mainViewholder.user.setText(getItem(position));
            }
            return convertView;
        }
    }
    public class ViewHolder {
        ImageView avatar;
        TextView user;
        Button topage;
    }
}
