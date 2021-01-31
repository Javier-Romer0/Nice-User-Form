package cat.itb.niceuserform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText edittext_username, edittext_password;
    TextInputLayout layout_username, layout_password;
    MaterialButton button_login, button_register, button_forgotpassword;

    private boolean dataIsFilled = false;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button_login = findViewById(R.id.login_button_login);
        button_register = findViewById(R.id.login_button_register);
        button_forgotpassword = findViewById(R.id.login_button_forgotpassword);
        edittext_username = findViewById(R.id.login_edittext_username);
        edittext_password = findViewById(R.id.login_edittext_password);
        layout_username = findViewById(R.id.login_textlayout_username);
        layout_password = findViewById(R.id.login_textlayout_password);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataIsFilled = false;
                enoughData();
                if (dataIsFilled) {
                    Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                }
            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        button_forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Unlucky", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void enoughData(){
        username = edittext_username.getText().toString().trim();
        controlDataFilled(username, layout_username);

        password = edittext_password.getText().toString().trim();
        controlPassword(password, layout_password);

        if (!username.isEmpty() && password.length()>=8){
            dataIsFilled = true;
        }
    }

    public static void controlPassword(String strPassword, TextInputLayout inputLayout){
        if (strPassword.length() < 8) inputLayout.setError("Password must have at least 8 characters");
        else inputLayout.setErrorEnabled(false);
    }

    public static void controlDataFilled(String strData, TextInputLayout inputLayout){
        if (strData.isEmpty()) inputLayout.setError("Required field");
        else inputLayout.setErrorEnabled(false);
    }
}