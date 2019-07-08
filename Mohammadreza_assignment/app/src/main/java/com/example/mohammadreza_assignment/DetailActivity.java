package com.example.mohammadreza_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohammadreza_assignment.model.Person;
import com.example.mohammadreza_assignment.model.Personcollection;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_PERSON_ID = "personId";

    TextView textViewName, textViewPhone, textViewEmail, textViewAddress;
    ImageView imageViewPhoto;

    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initialize();
        getPersonId();
        populateData();
    }

    private void initialize() {
        textViewName = findViewById(R.id.textViewName);
        textViewPhone = findViewById(R.id.textViewPhone);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewAddress = findViewById(R.id.textViewAddress);

        imageViewPhoto = findViewById(R.id.imageViewPhoto);

        findViewById(R.id.imageButtonPhoneCall).setOnClickListener(this);
        findViewById(R.id.imageButtonPhoneMessage).setOnClickListener(this);
    }

    private void getPersonId() {
        int personId = (int) getIntent().getExtras().get(EXTRA_PERSON_ID);
        // get person from list
        person = Personcollection.getPersonList().get(personId);
    }

    private void populateData() {
        // populate person data
        textViewName.setText(person.getFullName());
        textViewPhone.setText(person.getPhone());
        textViewEmail.setText(person.getEmail());
        textViewAddress.setText(person.getAddress());

        // populate image
        imageViewPhoto.setImageResource(person.getImageResourceId());
        imageViewPhoto.setContentDescription(person.getFullName());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButtonPhoneCall:
                makePhoneCall();
                break;
            case R.id.imageButtonPhoneMessage:
                sendMessage();
                break;
        }
    }

    private void makePhoneCall() {
        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setData(Uri.parse("tel:" + person.getPhone()));
        startActivity(intent);
    }

    private void sendMessage() {
        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setData(Uri.parse("sms:" + person.getPhone()));
        intent.putExtra("sms_body", "Sample SMSMessage");

        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
