package com.example.mohammadreza_assignment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mohammadreza_assignment.model.Person;
import com.example.mohammadreza_assignment.model.Personcollection;

import java.util.Optional;

public class PersonActivity extends AppCompatActivity  implements  View.OnClickListener{

    EditText editTextLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        initialize();
    }

    private void initialize() {
        // add click listener for buttons
        findViewById(R.id.buttonAddPerson).setOnClickListener(this);
        findViewById(R.id.buttonRemovePerson).setOnClickListener(this);
        findViewById(R.id.buttonFindPerson).setOnClickListener(this);

        // if last name is not empty, remove button and find button are enabled.
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editTextLastName.getText().length() > 0) {
                    findViewById(R.id.buttonRemovePerson).setEnabled(true);
                    findViewById(R.id.buttonFindPerson).setEnabled(true);
                } else {
                    findViewById(R.id.buttonRemovePerson).setEnabled(false);
                    findViewById(R.id.buttonFindPerson).setEnabled(false);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAddPerson:
                addNewPerson();
                break;

            case R.id.buttonRemovePerson:
                if (editTextLastName.getText().length() > 0) {
                    Person foundPerson = findPersonByLastName(editTextLastName.getText().toString());

                    if (foundPerson != null) {
                        removePerson(foundPerson);
                    }
                }
                break;

            case R.id.buttonFindPerson:
                if (editTextLastName.getText().length() > 0) {
                    Person foundPerson = findPersonByLastName(editTextLastName.getText().toString());

                    if (foundPerson != null) {
                        findPerson(foundPerson);
                    }
                }
                break;
        }
    }

    private void addNewPerson() {
        Intent intent = new Intent(PersonActivity.this, NewActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Person findPersonByLastName(String lastName) {
        Optional<Person> foundPerson = Personcollection.getPersonList().stream()
                .filter(person -> person.getLastName().equals(lastName))
                .findFirst();

        if (foundPerson.isPresent()) {
            return foundPerson.get();
        }
        // not found person
        Toast.makeText(PersonActivity.this, "No user found!", Toast.LENGTH_LONG).show();
        return null;
    }

    private void removePerson(Person person) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Remove a person")
                .setMessage("Do you want to delete this person ?\n\n" + person)
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_delete)

                .setPositiveButton("Yes", (dialog, i) -> {
                    Personcollection.getPersonList().remove(person);
                    Toast.makeText(PersonActivity.this, "Deleted successfully!", Toast.LENGTH_LONG).show();
                })
                .setNegativeButton("No", null);

        builder.show();
    }

    private void findPerson(Person person) {
        // get selected index from person list
        int selectedPersonId = Personcollection.getPersonList().indexOf(person);

        Toast.makeText(PersonActivity.this, "Success to find a person!", Toast.LENGTH_LONG).show();

        // Pass the person id on to DetailActivity
        Intent intent = new Intent(PersonActivity.this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_PERSON_ID, selectedPersonId);
        startActivity(intent);
    }
}
