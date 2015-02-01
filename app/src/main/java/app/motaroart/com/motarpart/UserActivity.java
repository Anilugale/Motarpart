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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import app.motaroart.com.motarpart.adapter.OrderHistoryAdapter;
import app.motaroart.com.motarpart.pojo.OrderHistory;
import app.motaroart.com.motarpart.pojo.Product;
import app.motaroart.com.motarpart.pojo.User;
import app.motaroart.com.motarpart.services.InternetState;
import app.motaroart.com.motarpart.services.WebServiceCall;


public class UserActivity extends ActionBarActivity {
    SharedPreferences pref;
    User user;
    ListView orderHistory;
    List<OrderHistory> orderHistoryList;
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
        if(InternetState.getState(this))
            new DownloadData().execute();
        else
        Toast.makeText(this, " Connection has lost", Toast.LENGTH_LONG).show();
    }

    void init()
    {
        orderHistory=(ListView)findViewById(R.id.order_history);


        orderHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(InternetState.getState(UserActivity.this))
                {
                    Intent intent=new Intent(UserActivity.this,OrderDetail.class);
                    intent.putExtra("orderNumber",orderHistoryList.get(i).getOrderNumber());
                    startActivity(intent);
               }
                else
                    Toast.makeText(UserActivity.this, " Connection has lost", Toast.LENGTH_LONG).show();

            }
        });

    }






   class DownloadData extends AsyncTask<Void,Void,String>
   {
       ProgressDialog pd;
       @Override
       protected String doInBackground(Void... voids) {

           return WebServiceCall.getOrderList(user.getAccountId());

       }

       @Override
       protected void onPreExecute() {
           pd=ProgressDialog.show(UserActivity.this,getString(R.string.app_name),"Loading...",true,false);
           super.onPreExecute();
       }

       @Override
       protected void onPostExecute(String s) {

           if(s!=null)
           {
               try {
                   JSONObject json=new JSONObject(s);
                   Gson gson=new Gson();
                   Type type=new TypeToken<List<OrderHistory>>(){}.getType();
                   orderHistoryList=gson.fromJson(json.getString("Order"),type);
                   OrderHistoryAdapter adapter=new OrderHistoryAdapter(UserActivity.this,orderHistoryList);
                   orderHistory.setAdapter(adapter);

               } catch (JSONException e) {
                   e.printStackTrace();
               }


           }
           else
           {
               Toast.makeText(UserActivity.this,"Opps!,Connection has lost.",Toast.LENGTH_LONG).show();
           }
           pd.dismiss();
           super.onPostExecute(s);
       }
   }


/// Action menu

    TextView count;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);

        count = new TextView(this);

        count.setTextColor(Color.BLUE);

        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(UserActivity.this, Cart.class));
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
        if (id == R.id.logout) {
            pref.edit().remove("user").apply();
            finish();
            return true;
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
