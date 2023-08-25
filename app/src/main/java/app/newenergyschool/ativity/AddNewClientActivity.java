package app.newenergyschool.ativity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import app.newenergyschool.R;
import app.newenergyschool.model.Client;

public class AddNewClientActivity extends AppCompatActivity {
    EditText telephoneNumber;
    EditText firstName;
    EditText secondName;
    EditText childName;
    EditText childAge;
    Button button;
    DatabaseReference databaseReference;

    List<String> valuesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_client);
        button = findViewById(R.id.add_new_client_button);
        telephoneNumber = findViewById(R.id.client_phone_number);
        firstName = findViewById(R.id.client_first_name);
        secondName = findViewById(R.id.client_second_name);
        childName = findViewById(R.id.client_child_name);
        childAge = findViewById(R.id.client_child_age);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        addAllClientsIntoList();
        buttonClickHandling();

    }

    public void addNewClient() {
        String phone = telephoneNumber.getText().toString();
        Client client = new Client(telephoneNumber, firstName, secondName, childName, childAge);
        databaseReference.child("Clients").child(phone).setValue(client);
    }


    public void addAllClientsIntoList() {

        databaseReference.child("Clients").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot clientSnapshot : dataSnapshot.getChildren()) {
                    String value = clientSnapshot.child("telephoneNumber").getValue(String.class);
                    if (value != null) {
                        valuesList.add(value);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddNewClientActivity.this, "Ошибка чтения из базы данных!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buttonClickHandling() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (telephoneNumber.getText().toString().equals("")) {
                    Toast.makeText(AddNewClientActivity.this, "Введите номер телефона.", Toast.LENGTH_SHORT).show();
                } else if (telephoneNumber.getText().toString().toCharArray().length != 10) {
                    Toast.makeText(AddNewClientActivity.this, "Введенный номер телефона не верен.", Toast.LENGTH_SHORT).show();
                } else if (valuesList.contains(telephoneNumber.getText().toString())) {
                    Toast.makeText(AddNewClientActivity.this, "Клиент с таким номером уже добавлен!", Toast.LENGTH_SHORT).show();
                } else if (firstName.getText().toString().equals("")) {
                    Toast.makeText(AddNewClientActivity.this, "Введите имя.", Toast.LENGTH_SHORT).show();
                } else if (secondName.getText().toString().equals("")) {
                    Toast.makeText(AddNewClientActivity.this, "Введите фамилию.", Toast.LENGTH_SHORT).show();
                } else if (childName.getText().toString().equals("")) {
                    Toast.makeText(AddNewClientActivity.this, "Введите имя ребенка.", Toast.LENGTH_SHORT).show();
                } else if (childAge.getText().toString().equals("")) {
                    Toast.makeText(AddNewClientActivity.this, "Введите возраст ребенка.", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(childAge.getText().toString()) < 1 || Integer.parseInt(childAge.getText().toString()) > 16) {
                    Toast.makeText(AddNewClientActivity.this, "Перевірте правильність введення віку дитини.", Toast.LENGTH_SHORT).show();
                } else {
                    addNewClient();
                    Toast.makeText(AddNewClientActivity.this, "Успешно добавлено!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddNewClientActivity.this, AdminPageActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }


}