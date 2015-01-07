package app.motaroart.com.motarpart;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
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
            Toast.makeText(this,user.getEmailId(),Toast.LENGTH_SHORT).show();
        }

    }

}
