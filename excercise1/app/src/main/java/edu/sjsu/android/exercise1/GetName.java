package edu.sjsu.android.exercise1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GetName extends Activity implements View.OnClickListener {

    private EditText editTextName;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_name);

        editTextName = findViewById(R.id.editText);
        buttonSubmit = findViewById(R.id.button);
        buttonSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonSubmit) {
            // Retrieve the user-entered name
            String userName = editTextName.getText().toString();

            // Start the greeting activity
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("username", userName);
            startActivity(intent);
        }
    }
}
