package app.newenergyschool.ativity;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import app.newenergyschool.R;
import app.newenergyschool.model.Client;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashMap;

public class UsersCabinetActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference databaseReference;
    HashMap<String, Client> clientArrayList = new HashMap();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.users_cabinet);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        addAllClientsIntoList();

    }

    public void addAllClientsIntoList() {
        databaseReference.child("Clients").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot clientSnapshot : snapshot.getChildren()) {
                    Client value = clientSnapshot.getValue(Client.class);
                    if (value != null) {
                        clientArrayList.put(value.getTelephoneNumber(), value);
                    }
                }
                updateTextView();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UsersCabinetActivity.this, "Ошибка чтения из базы данных!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateTextView() {
        TextView clientName = findViewById(R.id.name_users_cabinet);
        String tel = getIntent().getStringExtra("tel");
        String firstName = clientArrayList.get(tel).getFirstName();
        String childName = clientArrayList.get(tel).getChildName();
        String childAge = clientArrayList.get(tel).getChildAge();
        String secondName = clientArrayList.get(tel).getSecondName();
        clientName.setText(firstName + " " + secondName);
        TextView clientsChildrenName = findViewById(R.id.child_name_users_cabinet);
        clientsChildrenName.setText(childName);
        TextView clientsChildrenAge = findViewById(R.id.child_age_users_cabinet);
        clientsChildrenAge.setText(childAge);
        TextView clientsTel = findViewById(R.id.tel_number_users_cabinet);
        clientsTel.setText(tel);
    }


}