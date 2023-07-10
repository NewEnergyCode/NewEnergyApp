package app.ativity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//import app.adapters.ImageAdapter;
import app.newenergyschool.R;
import app.adapters.ImageAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private List<Uri> imageUris;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3); // Указываем сколько столбцов
        recyclerView.setLayoutManager(gridLayoutManager);

        imageUris = new ArrayList<>();
        Button buttonGallery = findViewById(R.id.buttonGallery);
        buttonGallery.setVisibility(View.GONE);
//        buttonGallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
        // Открываем Firebase Storage и получаем ссылку на корневой каталог
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        // Получаем список всех файлов в корневом каталоге
        storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                imageUris.clear();
                // Обрабатываем каждый файл в списке
                for (StorageReference fileRef : listResult.getItems()) {
                    // Получаем URL файла
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Добавляем URL в список
                            imageUris.add(uri);
                            // Обновляем адаптер RecyclerView
                            imageAdapter.notifyDataSetChanged();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Обработка ошибки при получении URL или загрузке файла
                            // TODO: Обработка ошибки
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Обработка ошибки при получении списка файлов
                // TODO: Обработка ошибки
            }
        });
        imageAdapter = new ImageAdapter(imageUris);
        recyclerView.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Uri uri) {
                Intent intent = new Intent(GalleryActivity.this, PhotoDetailActivity.class);
                intent.putExtra("imageUri", uri.toString());
                startActivity(intent);
            }
        });

//            }
//        });

    }



//    private class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
//
//        @NonNull
//        @Override
//        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false);
//            return new ImageViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
//            Uri imageUri = imageUris.get(position);
//            Picasso.get().load(imageUri).into(holder.imageView);
//        }
//
//        @Override
//        public int getItemCount() {
//            return imageUris.size();
//        }
//
//        class ImageViewHolder extends RecyclerView.ViewHolder {
//
//            ImageView imageView;
//
//            public ImageViewHolder(@NonNull View itemView) {
//                super(itemView);
//                imageView = itemView.findViewById(R.id.imageView);
//            }
//        }
//    }
}