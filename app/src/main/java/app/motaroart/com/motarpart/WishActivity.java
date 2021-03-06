package app.motaroart.com.motarpart;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
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

import app.motaroart.com.motarpart.adapter.WishAdapter;
import app.motaroart.com.motarpart.pojo.Product;
import app.motaroart.com.motarpart.pojo.User;
import app.motaroart.com.motarpart.pojo.Wish;
import app.motaroart.com.motarpart.services.InternetState;
import app.motaroart.com.motarpart.services.WebServiceCall;


public class WishActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    SharedPreferences mPrefs;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish);
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

                    if(InternetState.getState(WishActivity.this)) {
                        Intent i = new Intent(WishActivity.this, ProductActivity.class);
                        SharedPreferences.Editor edit = mPrefs.edit();
                        edit.putString("searchKey", keySearch.getText().toString());
                        edit.apply();
                        startActivity(i);
                    }
                    else
                        Toast.makeText(WishActivity.this, "Connection has lost", Toast.LENGTH_LONG).show();

                }
            }
        });

        mPrefs = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        new GetWish().execute();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }


    class GetWish extends AsyncTask<Void,Void,String>
    {
        ProgressDialog pd;
        @Override
        protected void onPostExecute(String s) {


           if(s!=null) {
                if(!s.equals("")) {

                    if (s.equals("NotLogin")) {
                        new AlertDialog.Builder(WishActivity.this)
                                .setTitle(WishActivity.this.getString(R.string.app_name))
                                .setMessage("Login to see your wish List!")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })

                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setCancelable(false)
                                .show();

                    }
                    else {

                        Type listOfTestObject = new TypeToken<List<Product>>() {
                        }.getType();
                        Gson gson = new Gson();
                        List<Product> listData = gson.fromJson(s, listOfTestObject);
                        ListView main_page = (ListView) findViewById(R.id.product_list);
                        if(listData.size()!=0) {


                            WishAdapter adapter = new WishAdapter(WishActivity.this, listData);


                            main_page.setAdapter(adapter);

                            main_page.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                                    Intent intent = new Intent(WishActivity.this, Login.class);

                                    startActivity(intent);

                                }
                            });
                        }
                        else
                        {
                            main_page.setVisibility(View.GONE);
                            TextView error=(TextView) findViewById(R.id.error);
                            error.setVisibility(View.VISIBLE);
                        }
                    }

                }
            }
            pd.dismiss();
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            pd=ProgressDialog.show(WishActivity.this, WishActivity.this.getResources().getString(R.string.app_name),"Loading..",true,false);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                Gson gson = new Gson();
                Type listOfTestWish = new TypeToken<List<Wish>>() {
                }.getType();

                List<Wish> listWish = gson.fromJson( mPrefs.getString("wish",""), listOfTestWish);
                String userStr=mPrefs.getString("user","");

                    if(!userStr.equals("")) {

                        Type type=new TypeToken<User>(){}.getType();
                        User user=gson.fromJson(userStr,type);
                        JSONObject wishList = new JSONObject();
                        wishList.put("AccountId", user.getAccountId());//TODO
                        wishList.put("WishList", gson.toJson(listWish));
                        String str = "{'AccountWishList':{'WishList':" + gson.toJson(listWish) + ",'AccountId':'"+user.getAccountId()+"'}}";
                        String result = WebServiceCall.SetWishList(str);
                        if(Boolean.valueOf(result))
                        {
                            mPrefs.edit().remove("wish").apply();
                        }

                        return   WebServiceCall.getWishListProduct(user.getAccountId());
                    }
                    else
                    {
                        return "NotLogin";
                    }

             } catch (JSONException e) {
                e.printStackTrace();
                return null;

            }


        }
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
                startActivity(new Intent(WishActivity.this, Cart.class));
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

        if (id == R.id.action_user) {
            startActivity(new Intent(this, Login.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    public void updateCart(int cnt)
    {
        if (count != null)
            count.setText(cnt + "  ");
    }

}
