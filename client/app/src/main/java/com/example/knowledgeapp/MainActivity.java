package com.example.knowledgeapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button button;
    private Button topicButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        topicButton = (Button) findViewById(R.id.topic6);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchVideo(v);
                //openResultsActivity();
            }
        });

        topicButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//
//                topicButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//                topicButton.setTextColor(getResources().getColor(R.color.white));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    topicButton.setBackground(getResources().getDrawable(R.drawable.clickedtopics));
                    topicButton.setTextColor(Color.parseColor("#ffffffff"));
                   // topicButton.setTextColor(Color.parseColor("ffffffff"));

                }
            }
        });
    }


    public void openResultsActivity(String id) {
        Intent intent = new Intent(this, Results.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private void fetchVideo(final View view) {
        final VideoFetcher fetcher = new VideoFetcher(view.getContext());

        fetcher.dispatchRequest(new VideoFetcher.VideoResponseListener() {
            @Override
            public void onResponse(VideoFetcher.VideoResponse response) {
                if (response.isError) {
                    Toast.makeText(view.getContext(), "Error while fetching video", Toast.LENGTH_LONG);
                    return;
                }
                else
                {
                    try {
                        String id1 = response.res.getJSONArray("items").getJSONObject(0).getJSONObject("id").getString("videoId");
                        String id2 = response.res.getJSONArray("items").getJSONObject(1).getJSONObject("id").getString("videoId");
                        openResultsActivity(id1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}

