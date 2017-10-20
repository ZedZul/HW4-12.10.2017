package com.zedzul.github.hw4_12102017;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.zedzul.github.hw.backend.userApi.UserApi;

import java.io.IOException;


class EndpointAsyncUploadTask extends AsyncTask<Pair<Context, String>, Void, String> {

    private static final String USER_ADDED = "USER ADDED";
    private UserApi myApiService;

    @SafeVarargs
    @Override
    protected final String doInBackground(final Pair<Context, String>... params) {
        if (myApiService == null) {  // Only do this once
            final UserApi.Builder builder = new UserApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl(MainActivity.HOME_URL_WITH_API)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {

                        @Override
                        public void initialize(final AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }
        
        try {
            myApiService.insert(params[0].second,params[1].second,params[2].second).execute();
        } catch (final IOException e) {
            return e.getMessage();
        }
        return USER_ADDED;
    }
}
