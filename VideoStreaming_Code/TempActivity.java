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

public class TempActivity extends AppCompatActivity {
    String[] VideoNames = {"Afsha Goal","Spiderman Trailer"};
    String[] UrLNames = {"https://firebasestorage.googleapis.com/v0/b/multimedia-streaming-3ab5e.appspot.com/o/video1.mp4?alt=media&token=962b3b08-a2b3-469e-9c18-a5671b07b26a",
            "https://firebasestorage.googleapis.com/v0/b/multimedia-streaming-3ab5e.appspot.com/o/video2.mp4?alt=media&token=047430b0-1b1e-48ba-9bf9-3f398fd39fe0"};
    ListView listview;
    int[] Images={R.drawable.afsha,R.drawable.spiderman};@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        listview = (ListView)findViewById(R.id.lview);
        CustomAdapter custom = new CustomAdapter();
        listview.setAdapter(custom);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent videointent = new Intent(getApplicationContext(), VideosActivity.class);
                videointent.putExtra("data",UrLNames[position]);
                startActivity(videointent);
            }
        });
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
            imageview.setImageResource(Images[position]);
            videoname.setText(VideoNames[position]);
            return view;
        }
    }
}
