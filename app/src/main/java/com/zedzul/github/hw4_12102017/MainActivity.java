package com.zedzul.github.hw4_12102017;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String HOME_URL_WITH_API = "https://hw4epam.appspot.com/_ah/api/";
    private static final String TAG = "MainActvity";
    private View pUploadButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        final View pDownloadButton = findViewById(R.id.download_user_info_button);
        pUploadButton = findViewById(R.id.upload_user_info_button);
        final EditText pNameEditText = (EditText) findViewById(R.id.user_name_edit_text);
        final EditText pAvatarUrlEditText = (EditText) findViewById(R.id.user_avatar_url_edit_text);
        final CalendarView pDobCalendar = (CalendarView) findViewById(R.id.user_date_of_birth_calendar_view);
        pDobCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull final CalendarView pCalendarView, final int pI, final int pI1, final int pI2) {
                try {
                    pDobCalendar.setDate(new SimpleDateFormat("dd.MM.yyyy",Locale.ENGLISH).parse(pI2 +"." + (pI1+1) +"."+ pI).getTime());
                } catch (final ParseException pE) {
                    Log.d(TAG, pE.getMessage());
                }
            }
        });

        pDownloadButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View pView) {
                final EndpointAsyncDownloadTask pEndPointAsyncTask = new EndpointAsyncDownloadTask() {

                    @Override
                    protected void onPostExecute(final String result) {
                        try {
                            final JSONObject pJson = new JSONObject(result);
                            pNameEditText.setText(pJson.getString("name"));
                            pAvatarUrlEditText.setText(pJson.getString("avatar_url"));
                            pDobCalendar.setDate(pJson.getLong("dob"));
                        } catch (final JSONException pE) {
                            pNameEditText.setText(pE.getMessage());
                        }
                    }
                };

                pEndPointAsyncTask.execute(new Pair<Context, String>(MainActivity.this, "Manfred"));

            }
        });

        pUploadButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View pView) {

                final EndpointAsyncUploadTask pEndPointUploadAsyncTask = new EndpointAsyncUploadTask() {

                    @Override
                    protected void onPostExecute(final String result) {
                        Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
                    }
                };

                try {
                    pEndPointUploadAsyncTask.execute(new Pair<Context, String>(MainActivity.this, pNameEditText.getText().toString()),
                            new Pair<Context, String>(MainActivity.this, pAvatarUrlEditText.getText().toString()),
                            new Pair<Context, String>(MainActivity.this, new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).format(new Date(pDobCalendar.getDate()))));
                }catch (final Exception pE){
                    Log.d(TAG,pE.getMessage());
                }
            }
        });

        pNameEditText.addTextChangedListener(new DownloadButtonEnabledControle());
        pAvatarUrlEditText.addTextChangedListener(new DownloadButtonEnabledControle());
    }

    private class DownloadButtonEnabledControle implements TextWatcher {

        @Override
        public void beforeTextChanged(final CharSequence pCharSequence, final int pI, final int pI1, final int pI2) {

        }

        @Override
        public void onTextChanged(final CharSequence pCharSequence, final int pI, final int pI1, final int pI2) {
            pUploadButton.setEnabled(!"".equals(pCharSequence));
        }

        @Override
        public void afterTextChanged(final Editable pEditable) {

        }
    }
}
