package sg.edu.rp.c346.id20045524.p09_ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tvTitle, tvSingers, tvYear, tvStars;
    EditText etTitle, etSingers, etYear;
    RadioGroup rgStars;
    Button btnInsert, btnShowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.tvTitle);
        tvSingers = findViewById(R.id.tvSingers);
        tvYear = findViewById(R.id.tvYear);
        tvStars = findViewById(R.id.tvStars);
        etTitle = findViewById(R.id.etTitle);
        etSingers = findViewById(R.id.etSingers);
        etYear = findViewById(R.id.etYear);
        rgStars = findViewById(R.id.rgStars);
        btnInsert = findViewById(R.id.btnInsert);
        btnShowList = findViewById(R.id.btnShowList);

        btnInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                String title = etTitle.getText().toString();
                String singers = etSingers.getText().toString();
                String yearString = etYear.getText().toString();

                boolean digitsOnly = TextUtils.isDigitsOnly(etYear.getText());

                if (title.trim().length() == 0 || singers.trim().length() == 0
                        || yearString.trim().length() == 0){
                    Toast.makeText(MainActivity.this, "Blank field detected!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (digitsOnly == true){
                        int getYearNum = Integer.parseInt(yearString);

                        if (rgStars.getCheckedRadioButtonId() != -1){ //check if a radiobutton have been selected
                            long inserted_id = dbh.insertSong(title, singers, getYearNum, getStarRating());
                            if (inserted_id != -1){
                                Toast.makeText(MainActivity.this, "Insert successful",
                                        Toast.LENGTH_SHORT).show();
                                clear();
                            }
                        } else { // returns -1 if no radiobutton is checked.
                            Toast.makeText(MainActivity.this, "Check a Star Please!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Input Numbers only!",
                                Toast.LENGTH_SHORT).show();
                    }
                }


            }

            private void clear() {
                etTitle.setText("");
                etSingers.setText("");
                etYear.setText("");
                rgStars.clearCheck();
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //startActivity
                Intent i = new Intent(MainActivity.this, ShowListActivity.class);
                startActivity(i);
            }
        });

    }

    public int getStarRating() {
        int checkedRadioId = rgStars.getCheckedRadioButtonId();
        RadioButton checkedStarRating = findViewById(checkedRadioId);
        int starRating = Integer.parseInt(checkedStarRating.getText().toString());

        Log.d("Star Rating!!!! ", String.valueOf(starRating));

        return starRating;
    }

}
