package com.example.m4hdyar.gym;

import android.content.Context;
import android.os.Build;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;

public class SerialRequestQueue {
    private static final int MAX_CACHE_SIZE = 2097152; //2 MB
    private static final int MAX_SERIAL_THREAD_POOL_SIZE = 1;
    private static final int MAX_MULTI_THREAD_POOL_SIZE = 4;
    private static RequestQueue serialRequestQueue;

    /**
     * Use to fetch the serial request queue
     */
    public static RequestQueue getSerialRequestQueue(Context context) {
        if (serialRequestQueue == null) {
            serialRequestQueue = prepareSerialRequestQueue(context);
            serialRequestQueue.start();
        }
        return serialRequestQueue;
    }

    private static RequestQueue prepareSerialRequestQueue(Context context) {
        Cache cache = new DiskBasedCache(context.getCacheDir(), MAX_CACHE_SIZE);
        Network network = new BasicNetwork(new HurlStack());
        return new RequestQueue(cache, network, MAX_SERIAL_THREAD_POOL_SIZE);
    }

}
