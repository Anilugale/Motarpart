package app.motaroart.com.motarpart;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Login extends Activity {

    EditText name, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);


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

        } else {
        }


    }

    private boolean validateData() {
        if (name.getText().toString().trim().length()==0) {
            Toast.makeText(this,"Enter the user name!",Toast.LENGTH_LONG).show();
            return false;
        }
        else if (pass.getText().toString().trim().length()==0) {
            Toast.makeText(this,"Enter the password!",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }






   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
