package app.motaroart.com.motarpart;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import app.motaroart.com.motarpart.pojo.Order;
import app.motaroart.com.motarpart.pojo.User;


public class Summry extends Activity {


    SharedPreferences mPrefs;

    EditText address1,address2,state,city,pobox;
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
            TextView name,cntTotal,total;
            address1=(EditText)findViewById(R.id.address1);
            address2=(EditText)findViewById(R.id.address2);
            state=(EditText)findViewById(R.id.state);
            city=(EditText)findViewById(R.id.city);
            pobox=(EditText)findViewById(R.id.pobox);
            name=(TextView)findViewById(R.id.user_name);
            cntTotal=(TextView)findViewById(R.id.item_cnt);
            total=(TextView)findViewById(R.id.item_total);
            name.setText("Hi."+order.getOrderBy());
            cntTotal.setText(order.getProductCount()+"");
            total.setText(order.getTotalAmount()+"");
            order.getProductList().get(0);

            if(user!=null)
            {
                address1.setText(user.getShipmentAddress1());
                address2.setText(user.getShipmentAddress2());
                state.setText(user.getShipmentState());
                city.setText(user.getShipmentCity());
                pobox.setText(user.getShipmentPoBox());
            }

           else
            {
               Toast.makeText(this,"Login to Continue",Toast.LENGTH_SHORT).show();
                finish();
            }

        }

    }



}
