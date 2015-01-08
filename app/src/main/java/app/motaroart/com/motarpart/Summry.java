package app.motaroart.com.motarpart;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import app.motaroart.com.motarpart.pojo.Order;
import app.motaroart.com.motarpart.pojo.User;


public class Summry extends Activity {


    SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summry);
        Order order= (Order) getIntent().getSerializableExtra("Order");
        mPrefs=getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
        String userStr=  mPrefs.getString("user","");
        Gson gson=new Gson();
        Type type=new TypeToken<User>(){}.getType();
        User user=gson.fromJson(userStr,type);
        if(order!=null)
        {

            TextView name,cntTotal,total,addresss,poNo;

            name=(TextView)findViewById(R.id.user_name);
            cntTotal=(TextView)findViewById(R.id.item_cnt);
            total=(TextView)findViewById(R.id.item_total);
            addresss=(TextView)findViewById(R.id.address);

            name.setText("Hi."+order.getOrderBy());
            cntTotal.setText(order.getProductCount()+"");
            total.setText(order.getTotalAmount()+"");

            order.getProductList().get(0);

        }

    }



}
