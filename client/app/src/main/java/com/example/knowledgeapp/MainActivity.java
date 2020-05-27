package com.example.knowledgeapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchVideo(v);
                //openResultsActivity();
            }
        });
    }

    public void openResultsActivity() {
        Intent intent = new Intent(this, Results.class);
        startActivity(intent);
    }

    private void fetchVideo(final View view) {
        final VideoFetcher fetcher = new VideoFetcher(view.getContext());

        fetcher.dispatchRequest(new VideoFetcher.VideoResponseListener() {
            @Override
            public void onResponse(VideoFetcher.VideoResponse response) {
                if (response.isError) {
                    Toast.makeText(view.getContext(), "Error while fetching stock price", Toast.LENGTH_LONG);
                    return;
                }
            }
        });
    }
}

