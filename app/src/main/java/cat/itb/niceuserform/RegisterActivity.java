package cat.itb.niceuserform;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private MaterialButton button_register, button_login;
    private TextInputLayout layout_username, layout_password, layout_repeatpassword, layout_email, layout_name, layout_surnames, layout_birthdate, layout_genderpronoun;
    private TextInputEditText edittext_username, edittext_password, edittext_repeatpassword, edittext_email, edittext_name, edittext_surnames, edittext_birthdate;
    private AutoCompleteTextView genderpronoun_options;
    private MaterialCheckBox termsCheckbox;
    private DatePickerDialog birthdate_picker;
    private DatePickerDialog.OnDateSetListener dateListener;

    private String username, password, repeatedpassword, email, name, surnames, birthdate, genderpronoun;
    private boolean dataIsFilled = false, correctPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        button_register = findViewById(R.id.register_button_register);
        button_login = findViewById(R.id.register_button_login);

        layout_username = findViewById(R.id.register_textlayout_username);
        layout_password = findViewById(R.id.register_textlayout_password);
        layout_repeatpassword = findViewById(R.id.register_textlayout_repeatpassword);
        layout_email = findViewById(R.id.register_textlayout_email);
        layout_name = findViewById(R.id.register_textlayout_name);
        layout_surnames = findViewById(R.id.register_textlayout_surnames);
        layout_birthdate = findViewById(R.id.register_textlayout_birthdate);
        layout_genderpronoun = findViewById(R.id.register_textlayout_genderpronoun);

        edittext_username = findViewById(R.id.register_edittext_username);
        edittext_password = findViewById(R.id.register_edittext_password);
        edittext_repeatpassword = findViewById(R.id.register_edittext_repeatpassword);
        edittext_email = findViewById(R.id.register_edittext_email);
        edittext_name = findViewById(R.id.register_edittext_name);
        edittext_surnames = findViewById(R.id.register_edittext_surnames);
        edittext_birthdate = findViewById(R.id.register_edittext_birthdate);
        genderpronoun_options = findViewById(R.id.register_genderpronoun_options);

        termsCheckbox = findViewById(R.id.register_checkbox_termsandconditions);


        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.gender_options, R.layout.list_item);
        genderpronoun_options.setAdapter(adapter);


        edittext_birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                birthdate_picker = new DatePickerDialog(RegisterActivity.this, dateListener ,year, month, day) {};
                birthdate_picker.show();
            }
        });

        dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String birthdate = dayOfMonth + "/" + month + "/" + year;
                edittext_birthdate.setText(birthdate);
            }
        };

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctPassword = false;
                dataIsFilled = false;
                enoughData();
                comparePassword(password, repeatedpassword, layout_repeatpassword);
                if (dataIsFilled && correctPassword) {
                    Intent intent = new Intent(RegisterActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                }
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });



    }

    public void enoughData(){
        username = edittext_username.getText().toString().trim();
        LoginActivity.controlDataFilled(username, layout_username);

        password = edittext_password.getText().toString().trim();
        LoginActivity.controlPassword(password, layout_password);

        repeatedpassword = edittext_repeatpassword.getText().toString().trim();
        LoginActivity.controlPassword(repeatedpassword, layout_repeatpassword);

        email = edittext_email.getText().toString().trim();
        LoginActivity.controlDataFilled(email, layout_email);

        name = edittext_name.getText().toString().trim();
        LoginActivity.controlDataFilled(name, layout_name);

        surnames = edittext_surnames.getText().toString().trim();
        LoginActivity.controlDataFilled(surnames, layout_surnames);

        birthdate = edittext_birthdate.getText().toString().trim();
        LoginActivity.controlDataFilled(birthdate, layout_birthdate);

        genderpronoun = genderpronoun_options.getText().toString().trim();
        LoginActivity.controlDataFilled(genderpronoun, layout_genderpronoun);

        selectCheckbox();
        if (!username.isEmpty() && password.length()>=8 && repeatedpassword.length()>=8 && !email.isEmpty() && !name.isEmpty() && !surnames.isEmpty() && !birthdate.isEmpty() && !genderpronoun.isEmpty() && termsCheckbox.isChecked()){
            dataIsFilled = true;
        }
    }

    public void comparePassword(String pass1, String pass2, TextInputLayout inputLayout){
        if (!pass1.equals(pass2)){
            correctPassword = false;
            inputLayout.setErrorEnabled(true);
            inputLayout.setError("The password is different");
        }else correctPassword = true;
    }

    public void selectCheckbox(){
        if (!termsCheckbox.isChecked()) termsCheckbox.setTextColor(getResources().getColor(R.color.colorRed));
        else termsCheckbox.setTextColor(getResources().getColor(R.color.colorBlack));
    }
}