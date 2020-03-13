package com.example.graduationproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.graphics.Color.RED;

public class Signup extends AppCompatActivity {
    int val = 0;
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button b = (Button) findViewById(R.id.b1);
        sharedPrefManager = SharedPrefManager.getInstance(Signup.this);

        final EditText fname = (EditText) findViewById(R.id.fname);
        final EditText lname = (EditText) findViewById(R.id.lname);
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText pass = (EditText) findViewById(R.id.pass);
        final EditText cpass = (EditText) findViewById(R.id.confPassword);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final EditText phone = (EditText) findViewById(R.id.phone);
        final CheckBox cb=(CheckBox)findViewById(R.id.showPass);
        cb.toggle();
        String[] genderopt = {"Male", "Female"};
        final Spinner gender = (Spinner) findViewById(R.id.gender);
        ArrayAdapter<String> objgender = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderopt);
        String[] genderoptAR = {"ذكر", "انثى"};
        ArrayAdapter<String> objgenderAR = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderoptAR);
        SharedPreferences prefs=getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language=prefs.getString("My_Lang","");
        if(language.compareTo("ar")==0)
            gender.setAdapter(objgenderAR);
        else
            gender.setAdapter(objgender);


        String[] cityopt = {"Jerusalem", "Jenin", "Tulkarm", "Nablus", "Qalqilya", "Salfit", "Jericho", "Ramallah and Al-Bireh", "Bethlehem", "Hebron"};
        final Spinner city = (Spinner) findViewById(R.id.city);
        ArrayAdapter<String> objcity = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityopt);
        String[] cityoptAR = {"القدس", "جنين", "طولكرم", "نابلس", "قلقيلية", "سلفيت", "اريحا", "رام الله والبيرة", "بيت لحم", "الخليل"};
        ArrayAdapter<String> objcityAR = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityoptAR);
        prefs=getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        language=prefs.getString("My_Lang","");
        if(language.compareTo("ar")==0)
            city.setAdapter(objcityAR);
        else
            city.setAdapter(objcity);

        alertDialogBuilder.setPositiveButton("OK", null);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // show password
                    cpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                } else {
                    // hide password
                    cpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });
//        cb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(cb.isChecked())
//                {
//                    email.setInputType(InputType.TYPE_CLASS_TEXT);
//                    pass.setInputType(InputType.TYPE_CLASS_TEXT);
//                }
//                else
//                {
//                    email.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                    pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                }
//            }
//        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val = 0;
                if (fname.getText().toString().isEmpty()) {
                    fname.setText("Enter First name");
                    fname.setTextColor(RED);
                } else {
                    if (fname.getText().toString().length() < 3) {
                        alertDialogBuilder.setTitle("Name should at least 3 characters");
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else if (!isAlpha(fname.getText().toString())) {
                        alertDialogBuilder.setTitle("Name should only has character");
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else {
                        val++;
                        //run
                    }
                }
                if (lname.getText().toString().isEmpty()) {
                    lname.setText("Enter Last name");
                    lname.setTextColor(RED);
                } else {
                    if (lname.getText().toString().length() < 3) {
                        alertDialogBuilder.setTitle("Name should at least 3 characters");
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else if (!isAlpha(lname.getText().toString())) {
                        alertDialogBuilder.setTitle("Name should only has character");
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else {
                        val++;
                        //run
                    }
                }
                if (email.getText().toString().isEmpty()) {
                    email.setText("Enter email");
                    email.setTextColor(RED);
                } else {
                    if (valEmail(email.getText().toString())) {
                        //run
                        // validate email in DB

                    } else {
                        alertDialogBuilder.setTitle("Unvalid email");
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                }
                if (pass.getText().toString().isEmpty()) {
                    pass.setText("Enter password");
                    pass.setTextColor(RED);
                } else {
                    if (!valpass(pass.getText().toString())) {
                        alertDialogBuilder.setTitle("Unvalid password");
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else {
                        if (pass.getText().toString().compareTo(cpass.getText().toString()) == 0) {
                            //run
                            val++;
                        } else {
                            alertDialogBuilder.setTitle("password not matched");
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }

                    }
                }
                if (cpass.getText().toString().isEmpty()) {
                    cpass.setText("Enter comfirm password");
                    cpass.setTextColor(RED);
                }

                if (phone.getText().toString().isEmpty()) {
                    phone.setText("Enter phone number");
                    phone.setTextColor(RED);
                } else {
                    if (!valPhone(phone.getText().toString())) {
                        alertDialogBuilder.setTitle("Wrong phone number");
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else {
                        //run
                        val++;
                    }
                }
                //Take gender
                //Take city
                if (val == 5) {
                    // save in object and database


                }

            }
        });
    }
    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }

    public boolean valEmail(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public boolean valpass(String pass) {
        String regixChar = ".*[a-zA-Z]+.*";
        String regixDig = ".*[0-9]+.*";
        String regixspecial = ".*[^a-zA-Z0-9 ]+.*";
        Pattern pattern1 = Pattern.compile(regixChar);
        Pattern pattern2 = Pattern.compile(regixDig);
        Pattern pattern3 = Pattern.compile(regixspecial);
        Matcher matcher1 = pattern1.matcher(pass);
        Matcher matcher2 = pattern2.matcher(pass);
        Matcher matcher3 = pattern3.matcher(pass);


        if (pass.length() >= 6 && matcher1.matches() && matcher2.matches() && matcher3.matches())
            return true;
        return false;

    }

    public boolean valPhone(String phone) {
        return phone.matches("\\+9705[0-9]{8}");
    }
}
