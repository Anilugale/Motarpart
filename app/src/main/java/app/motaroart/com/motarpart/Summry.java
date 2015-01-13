package app.motaroart.com.motarpart;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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
    Order order;
    EditText add1,add2,state,city,pobox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summry);
        order= (Order) getIntent().getSerializableExtra("Order");
        mPrefs=getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
        String userStr=  mPrefs.getString("user","");
        Gson gson=new Gson();
        Type type=new TypeToken<User>(){}.getType();
        User user=gson.fromJson(userStr,type);
        if(order!=null)
        {

            TextView name,cntTotal,total;

            name=(TextView)findViewById(R.id.user_name);
            cntTotal=(TextView)findViewById(R.id.item_cnt);
            total=(TextView)findViewById(R.id.item_total);

            name.setText("Hi."+order.getOrderBy());
            cntTotal.setText(order.getProductCount()+"");
            total.setText(order.getTotalAmount()+"");


            add1=(EditText)findViewById(R.id.address1);
            add2=(EditText)findViewById(R.id.address2);
            state=(EditText)findViewById(R.id.state);
            city=(EditText)findViewById(R.id.city);
            pobox=(EditText)findViewById(R.id.pobox);

            add1.setText(user.getShipmentAddress1());
            add2.setText(user.getShipmentAddress2());
            state.setText(user.getShipmentState());
            city.setText(user.getShipmentCity());
            pobox.setText(user.getShipmentPoBox());



        }

    }


    public  void paymentOrder(View v)
    {
        order.setShipmentAddress1(add1.getText().toString().trim());
        order.setShipmentAddress2(add2.getText().toString().trim());
        order.setShipmentState(state.getText().toString().trim());
        order.setShipmentCity(city.getText().toString().trim());
        order.setShipmentPoBox(pobox.getText().toString().trim());
        order.setVoucherCode("");
        System.out.println(new Gson().toJson(order));

        if( validate()) {
            startActivity(new Intent(this, Payment.class).putExtra("order", order));
        finish();
        }


    }

    boolean validate()
    {


        if(add1.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the Address1!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        if(add2.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the Address2!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(state.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the State!",Toast.LENGTH_SHORT).show();
            return false;
        } else if(city.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the City!",Toast.LENGTH_SHORT).show();
            return false;
        }else if(pobox.getText().toString().trim().length()==0)
        {
            Toast.makeText(this,"Enter the Po Box!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}
