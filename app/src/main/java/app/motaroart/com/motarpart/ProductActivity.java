package app.motaroart.com.motarpart;

import android.app.ProgressDialog;
import android.content.Context;
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

import java.lang.reflect.Type;
import java.util.List;

import app.motaroart.com.motarpart.adapter.ProductAdapter;
import app.motaroart.com.motarpart.pojo.Product;
import app.motaroart.com.motarpart.services.InternetState;
import app.motaroart.com.motarpart.services.WebServiceCall;


public class ProductActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    SharedPreferences mPrefs;
    List listData;

    Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

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

                    if(InternetState.getState(ProductActivity.this)) {
                        Intent i = new Intent(ProductActivity.this, ProductActivity.class);
                        SharedPreferences.Editor edit = mPrefs.edit();
                        edit.putString("searchKey", keySearch.getText().toString());
                        edit.apply();
                        startActivity(i);
                    }
                    else
                        Toast.makeText(ProductActivity.this, "Connection has lost", Toast.LENGTH_LONG).show();

                }
            }
        });
        new GetProduct().execute();
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

                startActivity(new Intent(ProductActivity.this, Cart.class));
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
        if(id==R.id.action_wish){
            startActivity(new Intent(this, WishActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void updateCart(int cnt)
    {
        if (count != null)
            count.setText(cnt + "  ");
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }

    class GetProduct extends AsyncTask<Void,Void,String>
    {
        ProgressDialog pd;
        @Override
        protected String doInBackground(Void... voids) {
            String str=mPrefs.getString("searchKey","");
            mPrefs.edit().remove("searchKey").apply();
            if(str.equals("")) {

                String result=WebServiceCall.getProduct(mPrefs.getString("modelID", ""), mPrefs.getString("catID", ""));
                SharedPreferences.Editor editor=mPrefs.edit();
                editor.remove("modelID");
                editor.remove("catID");
                editor.apply();
                return result;
            }
            else
                return WebServiceCall.SearchProduct(str);
        }
        @Override
        protected void onPreExecute() {
           pd=ProgressDialog.show(ProductActivity.this,getString(R.string.app_name),"Loading...!",true,false);
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String s) {
            if (s!=null) {
                if(s.equals("[]"))
                {
                    ListView main_page=(ListView)findViewById(R.id.product_list);
                    TextView error=(TextView)findViewById(R.id.error);
                    error.setVisibility(View.VISIBLE);
                    main_page.setVisibility(View.GONE);
                }
                else {
                    Type listOfTestObject = new TypeToken<List<Product>>() {
                    }.getType();
                    listData = gson.fromJson(s, listOfTestObject);
                    ProductAdapter adapter = new ProductAdapter(ProductActivity.this, listData);
                    ListView main_page = (ListView) findViewById(R.id.product_list);
                    main_page.setAdapter(adapter);
                    main_page.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                            Intent intent = new Intent(ProductActivity.this, Login.class);

                            startActivity(intent);

                        }
                    });
                }
            }
            else
            {
                ListView main_page=(ListView)findViewById(R.id.product_list);
                TextView error=(TextView)findViewById(R.id.error);
                error.setVisibility(View.VISIBLE);
                main_page.setVisibility(View.GONE);
            }

           pd.dismiss();
            super.onPostExecute(s);
        }
    }

}
