package com.example.mohammadreza_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohammadreza_assignment.model.Person;
import com.example.mohammadreza_assignment.model.Personcollection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CODE = 1;
    TextView textViewLastName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            checkPermissions();
    }
}
    private void bind() {
        textViewLastName = findViewById(R.id.textViewLastName);

        findViewById(R.id.btnList).setOnClickListener(this);
        findViewById(R.id.btnPersonActivity).setOnClickListener(this);
        initializeDummyData();
    }

    private void initializeDummyData() {
        Personcollection.getPersonList().add(
                new Person("Robert", "Paul", R.drawable.roberto,
                        "5141112222", "Robert@gmail.com", "Montreal")
        );
        Personcollection.getPersonList().add(
                new Person("Joe", "Dao", R.drawable.joe,
                        "5142223333", "Joe@gmail.com", "Montreal")
        );
        Personcollection.getPersonList().add(
                new Person("Smith", "Brandson", R.drawable.smith,
                        "4383334444", "Smith@gmail.net", "Montreal")
        );
        Personcollection.getPersonList().add(
                new Person("Simon", "Doh", R.drawable.doh,
                        "5144445555", "simon@gmail.com", "Montreal")
        );
        Personcollection.getPersonList().add(
                new Person("Lionel", "karo", R.drawable.karo,
                        "5146667777", "Lionel@gmail.com", "Montreal")
        );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnList:
                showPhoneBookList();
                break;
            case R.id.btnPersonActivity:
                openPersonActivity();
                break;
        }
    }

    private void showPhoneBookList() {
        Intent intent = new Intent(MainActivity.this, PhoneActivity.class);
        startActivity(intent);
    }

    private void openPersonActivity() {
        Intent intent = new Intent(MainActivity.this, PersonActivity.class);
        startActivity(intent);
    }

    private void checkPermissions() {
        if (!Permission()) {
            String[] perms = new String[]{
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.RECEIVE_SMS};
            ActivityCompat.requestPermissions(this, perms, CODE);
        }
    }

    private boolean Permission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED &&

                ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                        == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}