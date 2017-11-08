package com.example.jasonzhang.cs242;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class User extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        TextView username = (TextView) findViewById(R.id.textView6);
        Intent intent = getIntent();
        String name = intent.getStringExtra(Follower.user_name);
        username.setText(name);
    }
}
