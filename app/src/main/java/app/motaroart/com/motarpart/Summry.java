package app.motaroart.com.motarpart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import app.motaroart.com.motarpart.pojo.Order;
import app.motaroart.com.motarpart.pojo.Product;
import app.motaroart.com.motarpart.pojo.User;
import app.motaroart.com.motarpart.services.InternetState;


public class Summry extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    SharedPreferences mPrefs;
    Order order;
    EditText add1,add2,state,city,pobox;
    String currency="KES ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summry);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.iconl);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        mPrefs = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        ImageButton searchKey=(ImageButton)findViewById(R.id.searchKey);
        searchKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText keySearch=(EditText)findViewById(R.id.keySearch);
                if(keySearch.getText().toString().trim().length()>0) {

                    if(InternetState.getState(Summry.this)) {
                        Intent i = new Intent(Summry.this, ProductActivity.class);
                        SharedPreferences.Editor edit = mPrefs.edit();
                        edit.putString("searchKey", keySearch.getText().toString());
                        edit.apply();
                        startActivity(i);
                    }
                    else
                        Toast.makeText(Summry.this, "Connection has lost", Toast.LENGTH_LONG).show();

                }
            }
        });


        order= (Order) getIntent().getSerializableExtra("Order");
        mPrefs=getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
        String userStr=  mPrefs.getString("user","");
        Gson gson=new Gson();
        Type type=new TypeToken<User>(){}.getType();
        User user=gson.fromJson(userStr,type);
        if(order!=null)
        {

            TextView cntTotal,total,item_total,vat_per,vat_price;


            cntTotal=(TextView)findViewById(R.id.item_cnt);
            total=(TextView)findViewById(R.id.grand_total);
            item_total=(TextView)findViewById(R.id.item_total);
            vat_per=(TextView)findViewById(R.id.vat_per);
            vat_price=(TextView)findViewById(R.id.vat_price);


            cntTotal.setText(order.getProductCount()+"");
            total.setText(currency+order.getTotalAmount()+"");
            item_total.setText(currency+order.getOrderAmount()+"");
            vat_per.setText("VAT ("+order.getVATPercent()+"%)");
            vat_price.setText(currency+order.getVATAmount()+"");


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
        }if(order.getProductList().size()==0)
        {
            Toast.makeText(this,"Cart is empty!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    TextView count;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        count = new TextView(this);
        count.setTextColor(Color.BLUE);
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Summry.this, Cart.class));
            }
        });
        LinearLayout.LayoutParams imgvwDimens =
                new LinearLayout.LayoutParams(100, 100);
        count.setGravity(Gravity.TOP | Gravity.CENTER);
        count.setLayoutParams(imgvwDimens);
        count.setBackgroundResource(R.drawable.cart);
        count.setPadding(5, 8, 5, 5);
        count.setTypeface(null, Typeface.BOLD);
        SharedPreferences mPrefs = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        String JsonStr = mPrefs.getString("cart", "");
        Gson gson = new Gson();
        Type listOfTestObject = new TypeToken<List<Product>>() {
        }.getType();
        List<Product> list = gson.fromJson(JsonStr, listOfTestObject);
        if (list!=null) {
            count.setText(list.size() + "  ");
        } else {
            count.setText(0 + "  ");
        }

        count.setTextSize(15);
        menu.add(0, 0, 1, "count").setActionView(count).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences mPrefs = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        String JsonStr = mPrefs.getString("cart", "");
        Gson gson = new Gson();
        Type listOfTestObject = new TypeToken<List<Product>>() {
        }.getType();
        List<Product> list = gson.fromJson(JsonStr, listOfTestObject);
        if (count != null&&list!=null)
            count.setText(list.size() + "  ");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (mNavigationDrawerFragment.mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if(id ==android.R.id.home)
        {
            Intent homeIntent = new Intent(this, MakeActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }

        if (id == R.id.action_user) {
            startActivity(new Intent(this, Login.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }
}
