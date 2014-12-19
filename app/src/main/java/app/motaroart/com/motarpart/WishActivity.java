package app.motaroart.com.motarpart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;


public class WishActivity extends Activity {
    SharedPreferences mPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish);
         mPrefs = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);

        new GetWish().execute();
    }




   class GetWish extends AsyncTask<Void,Void,String>
    {
        ProgressDialog pd;
        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
             pd=ProgressDialog.show(WishActivity.this, WishActivity.this.getResources().getString(R.string.app_name),"Loading..");

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {


            return  mPrefs.getString("wish","");
        }
    }


}
