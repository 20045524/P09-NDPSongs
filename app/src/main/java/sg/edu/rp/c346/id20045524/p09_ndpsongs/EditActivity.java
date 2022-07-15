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

public class EditActivity extends AppCompatActivity {
    TextView tvTitle, tvSingers, tvYear, tvStars;
    EditText etTitle, etSingers, etYear, etID;
    RadioGroup rgStars;
    Button btnUpdate, btnDelete, btnCancel;
    Song data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        tvTitle = findViewById(R.id.tvTitle);
        tvSingers = findViewById(R.id.tvSingers);
        tvYear = findViewById(R.id.tvYear);
        tvStars = findViewById(R.id.tvStars);
        etTitle = findViewById(R.id.etTitle);
        etSingers = findViewById(R.id.etSingers);
        etYear = findViewById(R.id.etYear);
        rgStars = findViewById(R.id.rgStars);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);
        etID = findViewById(R.id.etID);
        etID.setEnabled(false);

        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");

        etID.setText(data.getId()+""); //Change int to string when setting text
        etTitle.setText(data.getTitle());
        etSingers.setText(data.getSingers());
        etYear.setText(data.getYear()+""); //Change int to string when setting text
        rgStars.check(data.getStars()); //check the radiobutton that correspond to the value

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                String title = etTitle.getText().toString();
                String singers = etSingers.getText().toString();
                String yearString = etYear.getText().toString();

                boolean digitsOnly = TextUtils.isDigitsOnly(etYear.getText());

                if (title.trim().length() == 0 || singers.trim().length() == 0
                        || yearString.trim().length() == 0){
                    Toast.makeText(EditActivity.this, "Blank field detected!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (digitsOnly == true){
                        int getYearNum = Integer.parseInt(yearString);

                        if (rgStars.getCheckedRadioButtonId() != -1){ //check if a radiobutton have been selected
                            data.setTitle(title);
                            data.setSingers(singers);
                            data.setYear(getYearNum);
                            data.setStars(getStarRating());
                            long updateded_id = dbh.updateSong(data);
                            if (updateded_id != -1){
                                Toast.makeText(EditActivity.this, "Update successful",
                                        Toast.LENGTH_SHORT).show();
                                dbh.close();
                                finish();
                            }
                        } else { // returns -1 if no radiobutton is checked.
                            Toast.makeText(EditActivity.this, "Check a Star Please!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(EditActivity.this, "Input Numbers only!",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                dbh.deleteSong(data.getId());
                finish();
                Log.d("Finish", "Go Back");

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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