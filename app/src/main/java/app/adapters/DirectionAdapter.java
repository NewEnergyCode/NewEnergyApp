package app.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import app.ativity.DirectoryInfoActivity;
import app.newenergyschool.R;
import app.ativity.WritePageActivity;
import app.model.Direction;

import java.util.List;

public class DirectionAdapter extends RecyclerView.Adapter<DirectionAdapter.DirectionViewHolder> {

    Context context;
    List<Direction> directionList;

    public DirectionAdapter(Context context, List<Direction> directionList) {
        this.context = context;
        this.directionList = directionList;
    }


    @NonNull
    @Override
    public DirectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View directionItems = LayoutInflater.from(context).inflate(R.layout.direction_item_courses, parent, false);
        return new DirectionViewHolder(directionItems);

    }

    @Override
    public void onBindViewHolder(@NonNull DirectionViewHolder holder, int position) {
//        holder.directionCourses.setCardBackgroundColor(Color.parseColor(directionList.get(position).getColor())); //get color for our image fone
        int imageId = context.getResources().getIdentifier("ic_" + directionList.get(position).getImg(), "drawable", context.getPackageName());
        int imageLogo = context.getResources().getIdentifier("ic_" + directionList.get(position).getLogoDirection(), "drawable", context.getPackageName());
        holder.directionImage.setImageResource(imageId);
        holder.directionLogo.setImageResource(imageLogo);
        holder.directionTitle.setText(directionList.get(position).getTitle());
        holder.directionAge.setText(directionList.get(position).getAge());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DirectoryInfoActivity.class);

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                        new Pair<View, String>(holder.directionCourses, "cardView"));
                intent.putExtra("directionCourses", directionList.get(position).getImg());
                intent.putExtra("directionImage", imageId);
                intent.putExtra("directionLogo", imageLogo);
                intent.putExtra("directionTitle", directionList.get(position).getTitle());
                intent.putExtra("textRev", directionList.get(position).getText());
                context.startActivity(intent);

            }
        });
        holder.buttonDirectionRev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DirectoryInfoActivity.class);

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                        new Pair<View, String>(holder.directionCourses, "cardView"));
                intent.putExtra("directionCourses", directionList.get(position).getImg());
                intent.putExtra("directionImage", imageId);
                intent.putExtra("directionLogo", imageLogo);
                intent.putExtra("directionTitle", directionList.get(position).getTitle());
                intent.putExtra("textRev", directionList.get(position).getText());
                context.startActivity(intent);
            }
        });

        holder.buttonDirectionWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WritePageActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return directionList.size();
    }

    public static final class DirectionViewHolder extends RecyclerView.ViewHolder {

        CardView directionCourses;
        ImageView directionImage;
        ImageView directionLogo;
        TextView directionTitle;
        TextView directionAge;
        TextView textRev;
        Button buttonDirectionWrite;
        Button buttonDirectionRev;


        public DirectionViewHolder(@NonNull View itemView) {
            super(itemView);
            directionCourses = itemView.findViewById(R.id.cardView);
            directionImage = itemView.findViewById(R.id.category_image);
            directionLogo = itemView.findViewById(R.id.direction_logo);
            directionTitle = itemView.findViewById(R.id.direction_title);
            directionAge = itemView.findViewById(R.id.direction_age);
            textRev = itemView.findViewById(R.id.direction_rev_min_tv);
            buttonDirectionWrite = itemView.findViewById(R.id.buttonWrite);
            buttonDirectionRev = itemView.findViewById(R.id.buttonRev);

        }
    }


}
