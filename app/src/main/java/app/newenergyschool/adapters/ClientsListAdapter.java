package app.newenergyschool.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.newenergyschool.R;
import app.newenergyschool.ativity.ClientListActivity;
import app.newenergyschool.ativity.DirectoryInfoActivity;
import app.newenergyschool.ativity.GalleryActivity;
import app.newenergyschool.ativity.UsersCabinetActivity;
import app.newenergyschool.model.Client;

public class ClientsListAdapter extends RecyclerView.Adapter<ClientsListAdapter.ClientListViewHolder> {
    Context context;
    ArrayList<Client> clientArrayList;

    public ClientsListAdapter(ClientListActivity clientListActivity, ArrayList<Client> clientArrayList) {
        this.context = clientListActivity;
        this.clientArrayList = clientArrayList;
    }


    @NonNull
    @Override
    public ClientsListAdapter.ClientListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View clientListView = LayoutInflater.from(context).inflate(R.layout.client_info_item, parent, false);
        return new ClientListViewHolder(clientListView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientsListAdapter.ClientListViewHolder holder, int position) {
        holder.firstName.setText(clientArrayList.get(position).getFirstName());
        holder.secondName.setText(clientArrayList.get(position).getSecondName());
        holder.telephoneNumber.setText(clientArrayList.get(position).getTelephoneNumber());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UsersCabinetActivity.class);
                intent.putExtra("tel", holder.telephoneNumber.getText());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return clientArrayList.size();
    }

    public static final class ClientListViewHolder extends RecyclerView.ViewHolder {
        TextView firstName;
        TextView secondName;
        TextView telephoneNumber;

        public ClientListViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.client_info_name);
            secondName = itemView.findViewById(R.id.client_info_second_name);
            telephoneNumber = itemView.findViewById(R.id.tel_number_client_info);
        }
    }
}

