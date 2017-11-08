package com.example.jasonzhang.cs242;

/**
 * Created by jasonzhang on 10/25/17.
 */

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

import static com.example.jasonzhang.cs242.R.id.imageView;

public class Profile extends Fragment{
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.profile, container, false);

        //set_username(name);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            set_bio(getArguments().getString("result"));
            set_username(getArguments().getString("username"));
        }





        ImageView avatar = (ImageView) rootView.findViewById(imageView);

        return rootView;
    }

    public void set_username(String username){
        TextView user = (TextView) rootView.findViewById(R.id.textView2);
        user.setText(username);
    }

    public void set_bio(String bio){
        TextView user = (TextView) rootView.findViewById(R.id.textView5);
        user.setText(bio);
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

}
