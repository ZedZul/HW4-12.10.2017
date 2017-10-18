package com.zedzul.github.hw4_12102017;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private View pDownloadButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        pDownloadButton = findViewById(R.id.download_user_info_button);
        final View pUploadButton = findViewById(R.id.upload_user_info_button);
        final EditText pNameEditText = (EditText) findViewById(R.id.user_name_edit_text);
        final EditText pAvatarUrlEditText = (EditText) findViewById(R.id.user_avatar_url_edit_text);
        final CalendarView pDobCalendar = (CalendarView) findViewById(R.id.user_date_of_birth_calendar_view);

        pDownloadButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View pView) {
                //TODO download user info
            }
        });

        pUploadButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View pView) {
                //TODO upload user info
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
            pDownloadButton.setEnabled(!"".equals(pCharSequence));
        }

        @Override
        public void afterTextChanged(final Editable pEditable) {

        }
    }
}
