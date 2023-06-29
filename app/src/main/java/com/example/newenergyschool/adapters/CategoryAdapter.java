package com.example.newenergyschool.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newenergyschool.MainActivity;
import com.example.newenergyschool.R;
import com.example.newenergyschool.model.Catagory;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    List<Catagory> ageCategory;

    public CategoryAdapter(Context context, List<Catagory> catagories) {
        this.context = context;
        this.ageCategory = catagories;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View categoryItems = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(categoryItems);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.categoryTitle.setText(ageCategory.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.showDirectionByAge(ageCategory.get(position).getId());
//                Intent intent=new Intent(context, DirectoryInfo.class);
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ageCategory.size();
    }

    public static final class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTitle;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.CategoryTitle);
        }
    }

}
