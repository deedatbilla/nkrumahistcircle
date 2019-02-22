package com.deedat.nkrumahistcircle.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.deedat.nkrumahistcircle.R;
import com.deedat.nkrumahistcircle.model.money;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import androidx.appcompat.widget.Toolbar;

public class AddActivity extends Activity {
TextView desc,amt,toggle_textview;
String toggle_value="expense";
Switch toggle;
    TextView  date;
    Button dateButton;
    TextView expennsedate;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    private static final int income = 1;
    private static final int expense = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar=findViewById(R.id.toolbar);


        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backicon();
            }
        });
       // toolbar.setElevation(10f);
        //toolbar.inflateMenu(R.menu.menu);
        FloatingActionButton fab=findViewById(R.id.floating_action_button);
        desc=findViewById(R.id.desc);
        amt=findViewById(R.id.amount);
        toggle_textview=findViewById(R.id.toggle_textview);
opt();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addexpense();
            }
        });

        dateButton = findViewById(R.id.buttonDate);

        dateButton.setText("Date");

       // date = findViewById(R.id.date);


        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                dateButton.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });


        //  myRef.setValue("Hello, World!");

    }


    public void backicon(){

        finish();
    }

    public String opt() {


        toggle= (Switch) findViewById(R.id.switch1);
        toggle_textview = (TextView) findViewById(R.id.toggle_textview);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    toggle_textview.setText("income");
                    toggle_value = "income";

                } else {
                    // The toggle is disabled
                    toggle_textview.setText("expense");
                    toggle_value = "expense";

                }
            }
        });
        return toggle_value;
    }
    public  void addexpense() {

        String expected_value = opt();
        if (expected_value == "expense") {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("finances");
            String description = desc.getText().toString().trim();
             String amount=amt.getText().toString();
            String  datestring = dateButton.getText().toString();
            if (!TextUtils.isEmpty(description)&&!TextUtils.isEmpty(amount)) {

                String id = myRef.push().getKey();
                money exp = new money(R.drawable.ic_remove_circle, description, datestring, Double.parseDouble(amount),"expense");
                myRef.child(id).setValue(exp);
                Toast.makeText(this, "Expense Successfully Added", Toast.LENGTH_SHORT).show();
                desc.setText("");
                amt.setText("");
                finish();

            } else {
                //Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                amt.setError("Enter Amount");
            }
        }

        else if(expected_value=="income"){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("finances");
            String description = desc.getText().toString().trim();
            String amount=amt.getText().toString();
            String datestring = dateButton.getText().toString();;


            if (!TextUtils.isEmpty(description)&&!TextUtils.isEmpty(amount)) {

                String id = myRef.push().getKey();
                money exp = new money(R.drawable.ic_add_circle, description, datestring, Double.parseDouble(amount),"income");
                myRef.child(id).setValue(exp);
                Toast.makeText(this, "Income Successfully Added", Toast.LENGTH_SHORT).show();
                desc.setText("");
                amt.setText("");
                finish();

            } else {
                //Toast.makeText(this, "there was a problem", Toast.LENGTH_SHORT).show();
                desc.setError("Enter description");
            }
        }

    }




}
