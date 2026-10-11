
package com.example.q2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText emailInput, codeInput;
    Button sendButton, validateButton;
    TextView pinDisplay;

    int validationCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 60, 40, 40);

        TextView heading = new TextView(this);
        heading.setText("Email Validation");
        heading.setTextSize(26);
        layout.addView(heading);

        emailInput = new EditText(this);
        emailInput.setHint("Enter your email address");
        emailInput.setInputType(33);
        layout.addView(emailInput);

        sendButton = new Button(this);
        sendButton.setText("Send Validation Code");
        layout.addView(sendButton);

        pinDisplay = new TextView(this);
        pinDisplay.setTextSize(18);
        layout.addView(pinDisplay);

        codeInput = new EditText(this);
        codeInput.setHint("Enter validation code");
        codeInput.setInputType(2);
        layout.addView(codeInput);

        validateButton = new Button(this);
        validateButton.setText("Validate Account");
        layout.addView(validateButton);

        setContentView(layout);

        sendButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();

            if (email.isEmpty()
                    || !android.util.Patterns.EMAIL_ADDRESS
                    .matcher(email).matches()) {
                emailInput.setError("Enter a valid email address");
                return;
            }

            validationCode = 100000 + new Random().nextInt(900000);

            Toast.makeText(
                    this,
                    "Validation code generated",
                    Toast.LENGTH_SHORT
            ).show();


            String subject = Uri.encode("Your Validation Code");
            String body = Uri.encode(
                    "Your validation code is: " + validationCode
            );

            Uri emailUri = Uri.parse(
                    "mailto:" + Uri.encode(email)
                            + "?subject=" + subject
                            + "&body=" + body
            );

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(emailUri);

            startActivity(emailIntent);

            try {
                startActivity(Intent.createChooser(
                        emailIntent,
                        "Send validation email"
                ));
            } catch (android.content.ActivityNotFoundException e) {
                // Fallback to another implicit email intent.
                Intent fallbackIntent = new Intent(Intent.ACTION_SEND);
                fallbackIntent.setType("message/rfc822");
                fallbackIntent.putExtra(
                        Intent.EXTRA_EMAIL,
                        new String[]{email}
                );
                fallbackIntent.putExtra(
                        Intent.EXTRA_SUBJECT,
                        "Your Validation Code"
                );
                fallbackIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        "Your validation code is: " + validationCode
                );

                try {
                    startActivity(Intent.createChooser(
                            fallbackIntent,
                            "Send validation email"
                    ));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(
                            this,
                            "No email app is available.",
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });

        validateButton.setOnClickListener(v -> {
            String enteredCode =
                    codeInput.getText().toString().trim();

            if (enteredCode.isEmpty()) {
                codeInput.setError("Enter the validation code");
                return;
            }

            if (validationCode == 0) {
                Toast.makeText(
                        this,
                        "Please request a code first.",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            if (enteredCode.equals(String.valueOf(validationCode))) {
                Toast.makeText(
                        this,
                        "Account validated successfully!",
                        Toast.LENGTH_LONG
                ).show();
            } else {
                Toast.makeText(
                        this,
                        "Incorrect code. Please try again.",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}
