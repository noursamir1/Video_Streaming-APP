package com.example.videostreamingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ImageView videoimage;
    TextView  videoname;
    String[] VideoNames = {"Afsha Goal","Spiderman Trailer"};
    ListView listview;
    int[] Images={R.drawable.afsha,R.drawable.spiderman};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //listview = (ListView)findViewById(R.id.listView);
        //CustomAdapter custom = new CustomAdapter();
        //listview.setAdapter(custom);
        /*listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent videointent = new Intent(getApplicationContext(), VideosActivity.class);
                startActivity(videointent);
            }
        });*/
    }
    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return VideoNames.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.listviewlayout, null);
            ImageView imageview = (ImageView) view.findViewById(R.id.imageView);
            TextView videoname = (TextView) view.findViewById(R.id.videoname);
            //imageview.setImageResource(Images[position]);
            videoname.setText(VideoNames[position]);
            return view;
        }
    }
}
