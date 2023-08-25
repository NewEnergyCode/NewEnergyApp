package app.newenergyschool.ativity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import app.newenergyschool.R;
import app.newenergyschool.adapters.ClientsListAdapter;
import app.newenergyschool.model.Client;

public class ClientListActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    EditText searchWindow;
    ImageView searchIcon;
    RecyclerView clientListView;
    ArrayList<Client> clientArrayList = new ArrayList<>();
    ArrayList<Client> clientArrayListAfterSearch = new ArrayList<>();
    ClientsListAdapter clientsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);
        searchIcon = findViewById(R.id.seach_icon);
        clientListView = findViewById(R.id.client_list);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        addAllClientsIntoList();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        clientListView.setLayoutManager(layoutManager);

        clientsListAdapter = new ClientsListAdapter(this, clientArrayList);
        clientListView.setAdapter(clientsListAdapter);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchClientByInputText();
            }
        });


    }


    public void addAllClientsIntoList() {
        databaseReference.child("Clients").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot clientSnapshot : snapshot.getChildren()) {
                    Client value = clientSnapshot.getValue(Client.class);
                    if (value != null) {
                        clientArrayList.add(value);
                    }
                }
                clientsListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ClientListActivity.this, "Ошибка чтения из базы данных!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchClientByInputText() {
        searchWindow = findViewById(R.id.find_client_window);
        String textForSearching = searchWindow.getText().toString().toLowerCase();
        clientArrayListAfterSearch.clear();
        ClientsListAdapter newAdapter = new ClientsListAdapter(ClientListActivity.this, new ArrayList<>());
        clientListView.setAdapter(newAdapter);
        if (!textForSearching.equals("")) {
            searchByText(textForSearching);
            if (!clientArrayListAfterSearch.isEmpty()) {
                newAdapter = new ClientsListAdapter(ClientListActivity.this, clientArrayListAfterSearch);
                clientListView.setAdapter(newAdapter);
            } else {
                Toast.makeText(ClientListActivity.this, "Клиент отсутствует в списке!", Toast.LENGTH_SHORT).show();
                clientListView.setAdapter(clientsListAdapter);
            }
        } else if (textForSearching.equals("")) {
            Toast.makeText(ClientListActivity.this, "Введите текст для поиска!", Toast.LENGTH_SHORT).show();
            clientListView.setAdapter(clientsListAdapter);
        }

    }

    public void searchByText(String text) {
        for (Client s : clientArrayList) {
            String tel = s.getTelephoneNumber();
            String name = s.getFirstName().toLowerCase();
            String secondName = s.getSecondName().toLowerCase();
            if (text.equals(name)) {
                clientArrayListAfterSearch.add(s);
            }
            if (text.equals(secondName)) {
                clientArrayListAfterSearch.add(s);
            }
            if (text.equals(tel)) {
                clientArrayListAfterSearch.add(s);
            }
        }
    }

}