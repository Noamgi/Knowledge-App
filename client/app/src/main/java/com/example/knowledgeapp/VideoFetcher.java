package com.example.knowledgeapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

public class VideoFetcher {

    private RequestQueue _queue;
    private static final String TAG = "VideoFetcher";
    private final static String REQUEST_URL =  "https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCC552Sd-3nyi_tk2BudLUzA&maxResults=2&order=rating&type=video&videoCategoryId=28&videoDuration=short&videoEmbeddable=true&key=AIzaSyAUjJAuuv8AS_F9vrG_OmoOK7KzdPUf99M";

    public VideoFetcher(Context context) {
        _queue = Volley.newRequestQueue(context);
    }

    public class VideoResponse {
        public boolean isError;
        public JSONObject res = null;

        public VideoResponse(boolean isError) {
            this.isError = isError;
        }
        public VideoResponse(boolean isError, JSONObject response) {
            this.isError = isError;
            this.res = response;
        }
    }

    public interface VideoResponseListener {
        void onResponse(VideoResponse response);
    }

    private VideoResponse createErrorResponse() {
        return new VideoResponse(true);
    }

    private VideoResponse createResponse(JSONObject response) {
        return new VideoResponse(false, response);
    }

    public void dispatchRequest(final VideoResponseListener listener) {

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, REQUEST_URL , null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "GET Request sent successfully");
                        Log.i(TAG, response.toString());
                        listener.onResponse(createResponse(response));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Failed to sent GET Request - " + error);
            }
        });
        req.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        _queue.add(req);
    }
}
