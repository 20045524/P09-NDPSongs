package sg.edu.rp.c346.id20045524.p09_ndpsongs;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Song> {

    Context parent_context;
    int layout_id;
    ArrayList<Song> alSong;

    public CustomAdapter(Context context, int resource,  ArrayList<Song> objects){
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        alSong = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object - Get
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row - Read
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding - Find and Bind
        TextView tvTitle = rowView.findViewById(R.id.tvTitle);
        TextView tvYear = rowView.findViewById(R.id.tvYear);
        TextView tvStar = rowView.findViewById(R.id.tvStars);
        TextView tvSingers = rowView.findViewById(R.id.tvSingers);

        // Obtain the Android Version information based on the position - Find
        Song currentSong = alSong.get(position);

        // Set values to the TextView to display the corresponding information - Populate
        tvTitle.setText(currentSong.getTitle());
        tvYear.setText(currentSong.getYear()+"");

        tvSingers.setText(currentSong.getSingers());
        if (currentSong.getStars() == 5){
            tvStar.setText(currentSong.starDisplay());
            tvStar.setTextColor(parent_context.getResources().getColor(R.color.rose));
        } else if (currentSong.getStars() == 4){
            tvStar.setText(currentSong.starDisplay());
            tvStar.setTextColor(parent_context.getResources().getColor(R.color.orange));
        } else if (currentSong.getStars() == 3){
            tvStar.setText(currentSong.starDisplay());
            tvStar.setTextColor(parent_context.getResources().getColor(R.color.yellow));
        } else if (currentSong.getStars() == 2){
            tvStar.setText(currentSong.starDisplay());
            tvStar.setTextColor(parent_context.getResources().getColor(R.color.grass));
        } else {
            tvStar.setText(currentSong.starDisplay());
            tvStar.setTextColor(parent_context.getResources().getColor(R.color.black));
        }


        return rowView;
    }



}
