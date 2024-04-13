package com.buiseness.saafcitybuiseness;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.buiseness.saafcitybuiseness.Model.DataPart;
import com.android.volley.toolbox.HttpHeaderParser;
import java.util.Map;

public class Multipartrequest extends Request<NetworkResponse> {
    private final Response.Listener<NetworkResponse> mListener;
    private final Map<String, String> mParams;
    private final Map<String, DataPart> mByteData;

    public Multipartrequest(String url, Map<String, String> params, Map<String, DataPart> byteData,
                            Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.mListener = listener;
        this.mParams = params;
        this.mByteData = byteData;
    }

    @Override
    public Map<String, String> getParams() {
        return mParams;
    }


    public Map<String, DataPart> getByteData() {
        return mByteData;
    }

    @Override
    public String getBodyContentType() {
        return "multipart/form-data";
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {
        mListener.onResponse(response);
    }
}
