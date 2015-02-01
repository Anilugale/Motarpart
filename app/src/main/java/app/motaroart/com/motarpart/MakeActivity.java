package app.motaroart.com.motarpart;

import android.app.Activity;
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
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.Locale;

import app.motaroart.com.motarpart.adapter.MakeAdapter;
import app.motaroart.com.motarpart.pojo.Make;
import app.motaroart.com.motarpart.pojo.Product;
import app.motaroart.com.motarpart.services.InternetState;
import app.motaroart.com.motarpart.services.WebServiceCall;


public class MakeActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private NavigationDrawerFragment mNavigationDrawerFragment;

    MakeAdapter adapter;
    private CharSequence mTitle;
    List<Make> listData;

    SharedPreferences mPrefs ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       init();
        mPrefs = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        final EditText key_word;
        key_word=(EditText)findViewById(R.id.key_word);
            key_word.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable arg0) {
                    if(adapter!=null) {
                        String text = key_word.getText().toString().toLowerCase(Locale.getDefault());
                        adapter.filter(text);
                    }
                    else
                        Toast.makeText(MakeActivity.this, "Data not found", Toast.LENGTH_LONG).show();
                }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

            }
        });

        ImageButton searchKey=(ImageButton)findViewById(R.id.searchKey);
        searchKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText keySearch=(EditText)findViewById(R.id.keySearch);
                if(keySearch.getText().toString().trim().length()>0) {

                    if(InternetState.getState(MakeActivity.this)) {
                        Intent i = new Intent(MakeActivity.this, ProductActivity.class);
                        SharedPreferences.Editor edit = mPrefs.edit();
                        edit.putString("searchKey", keySearch.getText().toString());
                        edit.apply();
                        startActivity(i);
                    }
                    else
                        Toast.makeText(MakeActivity.this, "Connection has lost", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    private void init() {
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        if(InternetState.getState(this))
            new DownloadData().execute();
        else
            Toast.makeText(this, "Connection has lost", Toast.LENGTH_LONG).show();

    }



    @Override
    public void onNavigationDrawerItemSelected(int position) {

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
                if(InternetState.getState(MakeActivity.this)) {
                    startActivity(new Intent(MakeActivity.this, Cart.class));
                }
                else
                  Toast.makeText(MakeActivity.this, "Connection has lost", Toast.LENGTH_LONG).show();
            }
        });
        LinearLayout.LayoutParams imgvwDimens =
                new LinearLayout.LayoutParams(100, 100);
        count.setGravity(Gravity.TOP | Gravity.RIGHT);
        count.setLayoutParams(imgvwDimens);
        count.setBackgroundResource(R.drawable.cart);
        count.setPadding(5, 5, 5, 5);
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
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


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


        if (id == R.id.action_user) {
            startActivity(new Intent(this, Login.class));
            return true;
        }
        if(id==R.id.action_wish){
            if(InternetState.getState(MakeActivity.this)) {
                startActivity(new Intent(this, WishActivity.class));
            }
            Toast.makeText(this, "Connection has lost", Toast.LENGTH_LONG).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //  backgroud service


    class DownloadData extends AsyncTask<Void,Void,String>
    {
        SharedPreferences mPrefs = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        ProgressDialog process;
        @Override
        protected void onPostExecute(String jsondata) {

            try {
                if (jsondata == null) {

                    jsondata = mPrefs.getString("make", "");
                    if(jsondata.equals(""))
                        throw new NullPointerException("Some required files are missing");

                }
                Gson gson = new Gson();
                Type listOfTestObject = new TypeToken<List<Make>>() {
                }.getType();
                listData = gson.fromJson(jsondata, listOfTestObject);

                adapter = new MakeAdapter(MakeActivity.this, listData);
                ListView main_page = (ListView) findViewById(R.id.main_page_list);
                main_page.setAdapter(adapter);
                main_page.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        if(InternetState.getState(MakeActivity.this)) {
                            Intent intent = new Intent(MakeActivity.this, ModelActivity.class);
                            mPrefs.edit().putString("makeID", String.valueOf(listData.get(i).getMakeId())).apply();
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(MakeActivity.this, "Connection has lost", Toast.LENGTH_LONG).show();

                    }
                });


                process.dismiss();
            }
            catch (NullPointerException ex)
            {
                process.dismiss();
                new AlertDialog.Builder(MakeActivity.this)
                        .setTitle(MakeActivity.this.getResources().getString(R.string.app_name))
                        .setMessage("Sorry No offline Data available!")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            super.onPostExecute(jsondata);
        }

        @Override
        protected void onPreExecute() {
            process=ProgressDialog.show(MakeActivity.this,MakeActivity.this.getResources().getString(R.string.app_name),"Loading....",true,false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String jsondata=null;
            String str= mPrefs.getString("make","");
            if(!str.equals(""))
                return str;

            if(InternetState.getState(MakeActivity.this)) {
                jsondata = WebServiceCall.getMakeJson();
                mPrefs.edit().putString("make",jsondata).apply();
            }


            return jsondata;
        }
    }


}
