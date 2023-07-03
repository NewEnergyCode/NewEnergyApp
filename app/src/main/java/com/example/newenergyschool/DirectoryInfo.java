package com.example.newenergyschool;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class DirectoryInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory_inform);
        ImageView directionImage = findViewById(R.id.direction_rev_image);
        ImageView directionLogo = findViewById(R.id.direction_rev_logo);
        TextView directionTitle = findViewById(R.id.direction_rev_title);
        TextView textRev = findViewById(R.id.direction_rev_min_tv);
        directionImage.setImageResource(getIntent().getIntExtra("directionImage", 0));
        directionLogo.setImageResource(getIntent().getIntExtra("directionLogo", 0));
        directionTitle.setText(getIntent().getStringExtra("directionTitle"));
        textRev.setText(getIntent().getStringExtra("textRev"));
    }

    public void openWritePage(View view) {

        Intent intent = new Intent(this, WritePageActivity.class);
        startActivity(intent);

    }
}