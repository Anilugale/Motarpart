package app.motaroart.com.motarpart;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.motaroart.com.motarpart.services.WebServiceCall;


public class Login extends ActionBarActivity {

    EditText name, pass;
    SharedPreferences pref;
    boolean isCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.iconl);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        isCart=getIntent().getBooleanExtra("Cart",false);
        pref=getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
        String userStr=pref.getString("user","");

        if(!userStr.equals(""))
        {
            if(isCart) {
               finish();
            }
            else {
                startActivity(new Intent(this, UserActivity.class));
                finish();
            }
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



        final TextView forget_password = (TextView) findViewById(R.id.forget_password);
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, ForgetPassword.class);
                startActivity(intent);
                finish();
            }
        });

    }



    public void register(View view) {
        Intent intent = new Intent(Login.this, Registration.class);
        startActivity(intent);
        finish();

    }
    public void loginData(View view) {

        if (validateData()) {
            new Validation().execute();
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

class Validation extends AsyncTask<Void,Void,String>
{
    ProgressDialog pd;
    @Override
    protected String doInBackground(Void... voids) {
        return WebServiceCall.userLogin(name.getText().toString().trim(),pass.getText().toString().trim());
    }

    @Override
    protected void onPreExecute() {
        pd=ProgressDialog.show(Login.this,Login.this.getString(R.string.app_name),"Loading...!",true,false);
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {


        try {
            JSONObject jsonResult=new JSONObject(s);
            JSONArray jsonArrayResult=jsonResult.getJSONArray("Status");
            JSONObject status=jsonArrayResult.getJSONObject(0);
            String errorCode= status.getString("ERROR_CODE");
            String errorMsg= status.getString("ERROR_DESC");

            if(errorCode.equals("1003")||errorCode.equals("1002")||errorCode.equals("1001"))
            {
                new AlertDialog.Builder(Login.this)
                        .setTitle(Login.this.getString(R.string.app_name))
                        .setMessage(errorMsg)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }else
            if(errorCode.equals("1000"))
            {
                SharedPreferences mPrefs = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);

                mPrefs.edit().putString("user",jsonResult.getJSONArray("UserAccount").get(0).toString()).apply();
                Bundle conData = new Bundle();
                conData.putString("param_result", "Thanks Thanks");
                Intent intent = new Intent();
                intent.putExtras(conData);
                setResult(444, intent);

              
                finish();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }





        pd.dismiss();
        super.onPostExecute(s);
    }
}




}
