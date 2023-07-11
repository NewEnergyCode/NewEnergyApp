package app.newenergyschool.ativity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import app.newenergyschool.R;
import app.newenergyschool.ativity.WritePageActivity;

public class DirectoryInfoActivity extends AppCompatActivity {

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