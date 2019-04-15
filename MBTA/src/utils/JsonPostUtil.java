package utils;

import main.Application;
import com.google.gson.Gson;
import java.io.IOException;
import json.NearbyStopBean;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author Eddy
 */
public class JsonPostUtil {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    
    //HTTP Request to Google API by posting device's MAC address
    public static String MACpostJson(String api, Object RequestJsonbean) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(RequestJsonbean);
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(api).post(body).build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    //Http Request to MBTA API for nearby stops info
    public static NearbyStopBean NearbyStopOKHttp(String lat, String lng) throws IOException {
        System.out.println(Application.nearby_start + lat + "&filter[longitude]=" + lng + Application.nearby_end);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Application.nearby_start + lat + "&filter[longitude]=" + lng + Application.nearby_end).build();
        okhttp3.Response response = client.newCall(request).execute();
        
        
        
        Gson gson = new Gson();
        NearbyStopBean nearbyStopItem = gson.fromJson(response.body().string().trim(), NearbyStopBean.class);
        
        return nearbyStopItem;
    }
}
