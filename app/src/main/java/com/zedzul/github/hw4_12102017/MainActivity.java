package com.zedzul.github.hw4_12102017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final ICalculator mCalculator = new Calculator();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        /*final View pSolveButton = findViewById(R.id.solve_button);
        final EditText pInputEditText = (EditText) findViewById(R.id.input_edit_text);
        final TextView pResultTextView = (TextView) findViewById(R.id.result_text_view);


        pSolveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View pView) {
                pResultTextView.setText(mCalculator.solve(pInputEditText.getText().toString()));
            }
        });

        pInputEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(final CharSequence pCharSequence, final int pI, final int pI1, final int pI2) {

            }

            @Override
            public void onTextChanged(final CharSequence pCharSequence, final int pI, final int pI1, final int pI2) {
                pSolveButton.setEnabled(!"".equals(pCharSequence));
            }

            @Override
            public void afterTextChanged(final Editable pEditable) {

            }
        });*/
    }
}
