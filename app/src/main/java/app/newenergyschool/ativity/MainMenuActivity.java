package app.newenergyschool.ativity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import app.newenergyschool.InformActivity;
import app.newenergyschool.R;


public class MainMenuActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_menu);
        TextView adminPage = findViewById(R.id.admin_page);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            if (currentUser.getPhoneNumber().equals("+380931386462")) {
                adminPage.setVisibility(View.VISIBLE);
            }
        }

    }

    public void myCabinet(View view) {
        Intent intent = new Intent(this, DirectoryRegisterActivity.class);
        startActivity(intent);
    }

    public void contacts(View view) {//TODO: make contact page
        Intent intent = new Intent(this, InformActivity.class);
        intent.putExtra("text", "contacts");
        startActivity(intent);
    }

    public void mainMenu(View view) {//TODO: make contact page
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    public void gameMenu(View view) {//TODO: make contact page
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void aboutOurSchool(View view) {//TODO: make contact page
        Intent intent = new Intent(this, InformActivity.class);
        intent.putExtra("text", "about");
        startActivity(intent);
    }

    public void adminPage(View view) {//TODO: make contact page
        Intent intent = new Intent(this, AdminPageActivity.class);
        startActivity(intent);
    }


}