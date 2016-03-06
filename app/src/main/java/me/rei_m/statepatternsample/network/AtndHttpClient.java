package me.rei_m.statepatternsample.network;

import android.accounts.NetworkErrorException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import me.rei_m.statepatternsample.entity.AtndEventEntity;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;

public class AtndHttpClient {

    public Observable<List<AtndEventEntity>> fetchEvent() {

        // リクエストのURLを作成.
        final HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api.atnd.org")
                .addPathSegment("events")
                .addQueryParameter("keyword_or", "google,cloud")
                .addQueryParameter("format", "json")
                .build();

        final Request request = new Request.Builder()
                .url(url)
                .cacheControl(CacheControl.FORCE_NETWORK)
                .build();

        return Observable.create(subscriber -> {
            try {
                Response response = HttpClient.INSTANCE.get().newCall(request).execute();
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    JSONObject responseJson;
                    try {
                        responseJson = new JSONObject(response.body().string());
                        int eventCount = responseJson.getInt("results_returned");
                        if (0 < eventCount) {
                            JSONArray events = responseJson.getJSONArray("events");
                            List<AtndEventEntity> eventEntityList = new ArrayList<AtndEventEntity>();
                            for (int i = 0; i < eventCount - 1; i++) {
                                JSONObject event = events.getJSONObject(i).getJSONObject("event");
                                eventEntityList.add(new AtndEventEntity(event.getInt("event_id"), event.getString("title")));
                            }
                            subscriber.onNext(eventEntityList);
                        }
                    } catch (JSONException e) {
                        subscriber.onError(e);
                    }
                } else {
                    // エラーは適当
                    subscriber.onError(new NetworkErrorException());
                }
            } catch (IOException e) {
                subscriber.onError(e);
            }
            subscriber.onCompleted();
        });
    }
}
