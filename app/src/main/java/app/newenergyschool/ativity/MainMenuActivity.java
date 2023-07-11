package app.newenergyschool.ativity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import app.newenergyschool.InformActivity;
import app.newenergyschool.R;


public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_menu);
    }

    public void myCabinet(View view) {
        Intent intent = new Intent(this, DirectoryRegisterActivity.class);
        startActivity(intent);
    }

    public void contacts(View view) {//TODO: make contact page
//        Intent intent = new Intent(this, InformActivity.class);
//        intent.putExtra("text", "contacts");
//        startActivity(intent);
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void mainMenu(View view) {//TODO: make contact page
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    public void aboutOurSchool(View view) {//TODO: make contact page
        Intent intent = new Intent(this, InformActivity.class);
        intent.putExtra("text", "about");
        startActivity(intent);
    }


}