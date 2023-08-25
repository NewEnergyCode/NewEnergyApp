package app.newenergyschool.ativity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import app.newenergyschool.R;

public class AdminPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
    }

    public void addNewClient(View view) {
        Intent intent = new Intent(this, AddNewClientActivity.class);
        startActivity(intent);
    }
    public void showClientsList(View view) {
        Intent intent = new Intent(this, ClientListActivity.class);
        startActivity(intent);
    }
}