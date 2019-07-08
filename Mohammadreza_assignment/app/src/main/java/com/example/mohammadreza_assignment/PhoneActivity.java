package com.example.mohammadreza_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mohammadreza_assignment.model.Person;
import com.example.mohammadreza_assignment.model.Personcollection;

import java.util.Comparator;

public class PhoneActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        initializeListView();
    }

    private void initializeListView() {
        ListView listPhoneBook = findViewById(R.id.listPhoneBook);

        // create an Adapter for ListView
        ArrayAdapter<Person> listAdapter = null;

        listAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                Personcollection.getPersonList().toArray()
        );

        // assign the Adapter to the list view
        listPhoneBook.setAdapter(listAdapter);

        // sort by full name
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            listAdapter.sort(Comparator.comparing(Person::getFullName));
        }

        // create a listener
        listPhoneBook.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // get person data from AdapterView
        Person person = (Person) adapterView.getAdapter().getItem((int) l);

        // get selected index from person list
        int selectedPersonId = Personcollection.getPersonList().indexOf(person);

        // Pass the person id on to DetailActivity
        Intent intent = new Intent(PhoneActivity.this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_PERSON_ID, selectedPersonId);
        startActivity(intent);
    }
}
