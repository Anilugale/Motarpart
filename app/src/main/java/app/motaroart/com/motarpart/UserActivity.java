package app.motaroart.com.motarpart;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import app.motaroart.com.motarpart.pojo.User;


public class UserActivity extends Activity {
    SharedPreferences pref;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_acitivity);

        pref=getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
        String userStr=pref.getString("user","");

        if(!userStr.equals(""))
        {
            Gson gson=new Gson();
            Type type=new TypeToken<User>(){}.getType();
            user=gson.fromJson(userStr,type);
            init();
            Toast.makeText(this,user.getEmailId(),Toast.LENGTH_SHORT).show();
        }
    }

    void init()
    {
        TextView user_name=(TextView)findViewById(R.id.user_name);
        user_name.setText("Hi."+user.getName());

        TextView user_mobile=(TextView)findViewById(R.id.user_mobile);
        user_mobile.setText("Mobile."+user.getMobileNo());





    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);
       return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.logout) {
            pref.edit().remove("user").apply();
            finish();
            return true;
        }
       return super.onOptionsItemSelected(item);
    }

}
