package edu.ktu.savedatedb;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button btn_date, btn_save_payment;
    private EditText etxt_title;
    private TextView tv_date;
    int cyear, cmonth, cday;

    Date date1 = new Date();
    String date2 = "";
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public void init() {
        btn_date = (Button) findViewById(R.id.btn_date);
        tv_date = (TextView) findViewById(R.id.textViewDate);
        btn_save_payment = (Button) findViewById(R.id.btn_save_planned_payment);
        etxt_title = (EditText) findViewById(R.id.editTextTitle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch datepicker dialog
                final Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker =
                        new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(final DatePicker view, final int year, final int month,
                                                  final int dayOfMonth) {

                                @SuppressLint("SimpleDateFormat")
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                calendar.set(year, month, dayOfMonth);
                                String dateString = sdf.format(calendar.getTime());

                                tv_date.setText(dateString); // set the date
                            }
                        }, year, month, day); // set date picker to current date
                datePicker.show();

                datePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(final DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
            };
        });

        btn_save_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateString = tv_date.getText().toString();
                try {
                     date1 = format.parse(dateString);
                     date2 = format.format(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String title = etxt_title.getText().toString();
                Database worksDB = new Database(MainActivity.this);
                WorkModel newModel = new WorkModel(-1, title, date2);
                boolean successfullInsert = worksDB.addWork(newModel);
                if(successfullInsert) {
                    Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}