package com.example.mohammadreza_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mohammadreza_assignment.model.Person;
import com.example.mohammadreza_assignment.model.Personcollection;

public class NewActivity extends AppCompatActivity {

    EditText editTextFirstName, editTextLastName, editTextPhone, editTextEmail, editTextAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        initialize();
    }

    private void initialize() {
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAddress = findViewById(R.id.editTextAddress);

        // set click listener
        findViewById(R.id.buttonAddPerson).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewActivity.this.addNewPerson(view);
            }
        });
    }

    private void addNewPerson(View view) {
        if (editTextLastName.getText().length() == 0 && editTextFirstName.getText().length() == 0) {
            // not found person
            Toast.makeText(NewActivity.this,
                    "Name can't be empty!\nPlease enter first name and(or) last name", Toast.LENGTH_LONG).show();
            return;
        }

        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String email = editTextEmail.getText().toString();
        String address = editTextAddress.getText().toString();

        Personcollection.getPersonList().add(new Person(firstName, lastName, phone, email, address));

        Toast.makeText(NewActivity.this,
                "Success to add a new person!", Toast.LENGTH_LONG).show();

        // Clear EditText box
        clearInput();
    }

    private void clearInput() {
        editTextFirstName.setText("");
        editTextLastName.setText("");
        editTextPhone.setText("");
        editTextEmail.setText("");
        editTextAddress.setText("");
    }
}
