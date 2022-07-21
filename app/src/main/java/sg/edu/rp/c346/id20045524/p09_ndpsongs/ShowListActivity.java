package sg.edu.rp.c346.id20045524.p09_ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowListActivity extends AppCompatActivity {
    Spinner spinner;
    Button btnShowAll5Stars;
    ArrayList<Song> alSong;
    ListView lv;
    //ArrayAdapter<Song> aaSong;
    boolean toggle5Stars = true;
    CustomAdapter caSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        spinner = findViewById(R.id.spinner);
        btnShowAll5Stars = findViewById(R.id.btnShowAll5Stars);
        lv = findViewById(R.id.lv);

        loadSpinnerData();


        DBHelper dbh = new DBHelper(this);
        alSong = dbh.getAllSongs();
//        aaSong = new ArrayAdapter<Song>(this,
//                android.R.layout.simple_list_item_1, alSong);
//        lv.setAdapter(aaSong);
        caSong = new CustomAdapter(this, R.layout.row, alSong);

        lv.setAdapter(caSong);


        btnShowAll5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alSong.clear();
                if (toggle5Stars){ //Extra filtering
                    alSong.addAll(dbh.getAllSongs(5));
                    toggle5Stars = false;
                    btnShowAll5Stars.setText("Show All Songs");
                } else{
                    alSong.addAll(dbh.getAllSongs());
                    toggle5Stars = true;
                    btnShowAll5Stars.setText("Show All Songs With 5 Stars");
                }

                caSong.notifyDataSetChanged();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {

                Song data = alSong.get(position); //user can select song to edit
                Intent i = new Intent(ShowListActivity.this,
                        EditActivity.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String yearLabel = adapterView.getItemAtPosition(position).toString();
                alSong.clear();
                if(!yearLabel.equals("--")){
                    alSong.addAll(dbh.getAllSongsByYear(Integer.parseInt(yearLabel)));
                    Toast.makeText(ShowListActivity.this, yearLabel,
                            Toast.LENGTH_SHORT).show();
                } else {
                    alSong.addAll(dbh.getAllSongs());
                }

                caSong.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    //app will autoload savedData
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("OnResume", "Data changed");
        DBHelper dbh = new DBHelper(this);
        alSong.clear();
        alSong.addAll(dbh.getAllSongs());
        caSong.notifyDataSetChanged();

    }

    private void loadSpinnerData() {
        DBHelper dbh = new DBHelper(this);
        ArrayList<String> yearFilter = dbh.getYearLabels();
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, yearFilter);

        // attaching data adapter to spinner
        spinner.setAdapter(yearAdapter);
    }


}