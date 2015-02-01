package app.motaroart.com.motarpart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import app.motaroart.com.motarpart.adapter.ProductHistoryAdapter;
import app.motaroart.com.motarpart.pojo.OrderDetails;
import app.motaroart.com.motarpart.pojo.Product;
import app.motaroart.com.motarpart.services.InternetState;
import app.motaroart.com.motarpart.services.WebServiceCall;


public class OrderDetail extends ActionBarActivity {

    OrderDetails orderDetails;
    String currency="KES ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        String  orderNumber=getIntent().getStringExtra("orderNumber");
       /* getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(true);*/
        if(InternetState.getState(this))
        {
            new DataDownload().execute(orderNumber);
        }
    }

    class DataDownload extends AsyncTask<String,Void,String>
    {
        ProgressDialog pd;
        @Override
        protected String doInBackground(String... voids) {
            return WebServiceCall.getOrderDetail(voids[0]);
        }
        @Override
        protected void onPreExecute() {
            pd=ProgressDialog.show(OrderDetail.this,getString(R.string.app_name),"Loading..",true,false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {


            if(s!=null)
            {
                Gson gson=new Gson();
                Type type=new TypeToken<OrderDetails>(){}.getType();
                orderDetails=gson.fromJson(s,type);
                init();
            }
            pd.dismiss();
            super.onPostExecute(s);
        }
    }

    private void init() {
        TextView order_number=(TextView)findViewById(R.id.order_number);
        order_number.setText(orderDetails.getOrder().get(0).getOrderNumber().replace(".0",""));

        TextView no_of_item=(TextView)findViewById(R.id.no_of_item);

        SimpleDateFormat  format =new SimpleDateFormat("dd/mm/yyyy HH:MM:ss");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        try {
             date=format1.parse(orderDetails.getOrder().get(0).getOrderDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        no_of_item.setText(format.format(date))  ;

        TextView subtotal=(TextView)findViewById(R.id.subtotal);
        subtotal.setText(currency+orderDetails.getOrder().get(0).getOrderAmount());

        TextView vat_price=(TextView)findViewById(R.id.vat_price);
        vat_price.setText(currency+orderDetails.getOrder().get(0).getVATAmount());


        TextView grand_total=(TextView)findViewById(R.id.grand_total);
        grand_total.setText(currency+ orderDetails.getOrder().get(0).getTotalAmount());

        TextView vat_pre=(TextView)findViewById(R.id.vat_per1);
        vat_pre.setText("VAT ("+orderDetails.getOrder().get(0).getVATPercent()+")");

        ListView orderHistory=(ListView)findViewById(R.id.order_history);
        ProductHistoryAdapter adapter=new ProductHistoryAdapter(this,orderDetails.getOrderDetails());
        orderHistory.setAdapter(adapter);
    }


    //

  //  Action menu
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

                startActivity(new Intent(OrderDetail.this, Cart.class));
                finish();
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
    protected void onRestart() {
        super.onRestart();
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
        if(id==R.id.action_wish){
            startActivity(new Intent(this, WishActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
