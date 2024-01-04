package edu.sjsu.android.exercise1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve the username from the intent
        String userName = getIntent().getStringExtra("username");

        // Display the greeting message
        TextView textViewGreeting = findViewById(R.id.textView2);
        textViewGreeting.setText("Hello, " + userName + "!");
    }
}
