package com.zedzul.github.hw4_12102017;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.zedzul.github.hw.backend.userApi.UserApi;
import com.zedzul.github.hw.backend.userApi.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

class EndpointAsyncDownloadTask extends AsyncTask<Pair<Context, String>, Void, String> {

    private static final String NO_DATA = "No data";
    private UserApi myApiService = null;

    @SafeVarargs
    @Override
    protected final String doInBackground(final Pair<Context, String>... params) {
        if (myApiService == null) {  // Only do this once
            final UserApi.Builder builder = new UserApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("https://hw4epam.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {

                        @Override
                        public void initialize(final AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        String result = "";
        try {
            final List<User> users = myApiService.list().execute().getItems();
            if (users == null || users.isEmpty()) {
                result = NO_DATA;
            }

            try {
                result = new JSONObject()
                        .put("name", users.get(users.size() - 1).getName())
                        .put("avatar_url", users.get(users.size() - 1).getAvatarUrl())
                        .put("dob", users.get(users.size() - 1).getDateOfBirth().getValue())
                        .toString();
            } catch (final JSONException pE) {
                result = pE.getMessage();
            }
            return result;
        } catch (final IOException e) {
            return e.getMessage();
        }
    }
}
