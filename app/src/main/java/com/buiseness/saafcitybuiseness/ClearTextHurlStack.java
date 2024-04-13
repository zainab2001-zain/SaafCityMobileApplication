package com.buiseness.saafcitybuiseness;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.android.volley.toolbox.HurlStack;

import javax.net.ssl.HttpsURLConnection;

public class ClearTextHurlStack extends HurlStack {
    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (connection instanceof HttpsURLConnection) {
            ((HttpsURLConnection) connection).setHostnameVerifier((hostname, session) -> true);
        }
        return connection;
    }
}

