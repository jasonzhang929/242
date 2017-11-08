package com.example.jasonzhang.cs242;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    public static String username;
    public static String password;
    public int repocount, followercount, followingcount;
    public ListView l;
    private Bundle bundle = new Bundle();
    public static String answer;
    public String repourl;
    public ArrayList<String> repos;




    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        username = intent.getStringExtra(Start.Login_ID);
        password = intent.getStringExtra(Start.Password);
        bundle.putString("username", username);
        String FILENAME = "hello_file";
        String[] myTaskParams = {"https://api.github.com/users/jman", password, "GET", "1"};


        RetrieveFeedTask ret = new RetrieveFeedTask();

            try {
                answer = ret.execute(myTaskParams).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        JSONObject obj = null;
        try {
            obj = new JSONObject(answer);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        repourl = "https://api.github.com/users/jman/repos";
        myTaskParams[0] = repourl;
        RetrieveFeedTask rep = new RetrieveFeedTask();
        try {
            answer = rep.execute(myTaskParams).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        JSONArray array = null;
        try {
            array = new JSONArray(answer);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 5; i++)
        {
            try {
                repos.add(array.getJSONObject(0).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        bundle.putStringArrayList("repos", repos);



        bundle.putString("result", answer);


        repocount = 0;
        followingcount = 0;
        followercount = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



    }

    public void search(){
        TextView input = (TextView) findViewById(R.id.editText6);
        String inputs = input.getText().toString();

    }

    static class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        private Exception exception;
        private static String feedback = "";


        @Override
        protected String doInBackground(String... urls) {



            URL obj = null;
            try {
                obj = new URL(urls[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection con = null;
            try {
                con = (HttpURLConnection) obj.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // optional default is GET
            try {
                con.setRequestMethod(urls[2]);
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

            //con.setRequestProperty("client_id", "jasonzhang929");

            int responseCode = 0;
            try {
                responseCode = con.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStream inputStream = null;
            try {
                inputStream = con.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(inputStream != null)

                try {
                    feedback = convertInputStreamToString(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            else
                feedback = "Did not work!";
            return feedback;
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }



    public ArrayAdapter get_adapter (){
        String[] codes = {"Python", "Java"};
        return new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,codes);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void parseapi(){

    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public Profile profile;
        public Repo repo;
        public Following following;
        public Follower follower;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            profile = new Profile();
            profile.setArguments(bundle);
            repo = new Repo();
            repo.setArguments(bundle);
            following = new Following();
            follower = new Follower();
            follower.setArguments(bundle);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return profile;
                case 1:
                    return repo;
                case 2:
                    return following;
                case 3:
                    return follower;
                default:
                    return  null;
            }
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Profile";
                case 1:
                    return "Repo(" + Integer.toString(repocount) + ")";
                case 2:
                    return "Following " + Integer.toString(followingcount);
                case 3:
                    return Integer.toString(followercount) + " Followers";
            }
            return null;
        }
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
