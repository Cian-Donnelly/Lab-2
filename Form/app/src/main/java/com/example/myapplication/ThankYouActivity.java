
package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ThankYouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String name = getIntent().getStringExtra("PERSON_NAME");

        if (name == null || name.trim().isEmpty()) {
            name = "User";
        }

        TextView message = new TextView(this);
        message.setText(
                "Thank you " + name
                        + ", your request is being processed."
        );
        message.setTextSize(24);
        message.setPadding(32, 32, 32, 32);

        setContentView(message);
    }
}
