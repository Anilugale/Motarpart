package app.motaroart.com.motarpart;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.motaroart.com.motarpart.services.WebServiceCall;


public class Login extends Activity {

    EditText name, pass;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref=getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
        String userStr=pref.getString("user","");
        if(!userStr.equals(""))
        {
          startActivity(new Intent(this,UserActivity.class));
            finish();
        }


        pass = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.username);

        CheckBox repeatChkBx = (CheckBox) findViewById(R.id.show_password);
        repeatChkBx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    pass.setInputType(129);
                }

            }
        });


        final TextView temp = (TextView) findViewById(R.id.singup);
        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);

            }
        });
        final TextView forget_password = (TextView) findViewById(R.id.forget_password);
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, ForgetPassword.class);
                startActivity(intent);

            }
        });

    }

    public void loginData(View view) {

        if (validateData()) {

            String josn=WebServiceCall.userLogin(name.getText().toString().trim(),pass.getText().toString().trim());

            finish();
        }


    }

    private boolean validateData() {
        if (name.getText().toString().trim().length()==0) {
            Toast.makeText(this,"Enter the user name!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (pass.getText().toString().trim().length()==0) {
            Toast.makeText(this,"Enter the password!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }






}
