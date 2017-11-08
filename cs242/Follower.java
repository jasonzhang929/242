package com.example.jasonzhang.cs242;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jasonzhang on 10/25/17.
 */

public class Follower extends Fragment {
    public static String user_name = "com.example.jasonzhang.cs242.MESSAGE";

    ListView l;
    public ArrayList<String> followers = new ArrayList<String>(Arrays.asList("xyz", "abc"));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.follower, container, false);
        followers = new ArrayList<String>(Arrays.asList("xyz", "abc"));
        l = (ListView) rootView.findViewById(R.id.followerlistView);
        l.setAdapter(new Follower.followAdapter(getActivity(), R.layout.follow_item, followers));

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
            Follower.ViewHolder mainViewholder = null;
            if (convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.avatar = (ImageView) convertView.findViewById(R.id.follower_avatar);
                viewHolder.user = (TextView) convertView.findViewById(R.id.follower_user);
                viewHolder.user.setText(followers.get(position));
                viewHolder.topage = (Button) convertView.findViewById(R.id.follower_topage);
                viewHolder.topage.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Button was clicked for listitem " + position, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), User.class);
                        intent.putExtra(user_name, followers.get(position));
                        startActivity(intent);

                    }
                });
                convertView.setTag(viewHolder);
            }
            else{
                mainViewholder = (Follower.ViewHolder) convertView.getTag();
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

    public static Drawable LoadImage(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}
