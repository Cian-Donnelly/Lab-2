
package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText nameInput, surnameInput, emailInput;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.nameInput);
        surnameInput = findViewById(R.id.surnameInput);
        emailInput = findViewById(R.id.emailInput);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(v -> {
            String firstName =
                    nameInput.getText().toString().trim();
            String surname =
                    surnameInput.getText().toString().trim();
            String email =
                    emailInput.getText().toString().trim();

            if (firstName.isEmpty()) {
                nameInput.setError("Enter your first name");
                return;
            }

            if (surname.isEmpty()) {
                surnameInput.setError("Enter your surname");
                return;
            }

            if (email.isEmpty()) {
                emailInput.setError("Enter your email address");
                return;
            }

            String fullName = firstName + " " + surname;

            Intent intent = new Intent(
                    MainActivity.this,
                    ThankYouActivity.class
            );

            intent.putExtra("PERSON_NAME", fullName);
            startActivity(intent);
        });
    }
}
