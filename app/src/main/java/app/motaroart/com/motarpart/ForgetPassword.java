package app.motaroart.com.motarpart;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import app.motaroart.com.motarpart.services.WebServiceCall;


public class ForgetPassword extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);


    }

  public void forgetPassword(View v)
  {
    TextView user_name=(TextView)findViewById(R.id.user_name);
    TextView email=(TextView)findViewById(R.id.email);

      if(user_name.getText().length()!=0 && email.getText().length()!=0)
          new ForgetPassword1().execute(user_name.getText().toString().trim(),email.getText().toString().trim());
  }

    class ForgetPassword1 extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            return WebServiceCall.forGetPassword(strings[0],strings[1]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}
