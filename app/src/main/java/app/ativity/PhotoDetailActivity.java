package app.ativity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import app.newenergyschool.R;
import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_item_open_one_photo);
        ImageView imageView = findViewById(R.id.gallery_open_photo_item);
        Intent intent = getIntent();
        if (intent != null) {
            String imageUri = intent.getStringExtra("imageUri");
            if (imageUri != null) {
                Uri uri = Uri.parse(imageUri);
                Picasso.get().load(uri).into(imageView);
            }
        }
    }
}
