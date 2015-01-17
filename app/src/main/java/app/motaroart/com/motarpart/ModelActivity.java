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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;

import app.motaroart.com.motarpart.adapter.ModelAdapter;
import app.motaroart.com.motarpart.pojo.Model;
import app.motaroart.com.motarpart.pojo.Product;
import app.motaroart.com.motarpart.services.InternetState;
import app.motaroart.com.motarpart.services.WebServiceCall;

public class ModelActivity extends Activity {
    List<Model> listData;
    EditText key_word;
    ModelAdapter adapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);
        init();
    }

    void init() {

        new DownloadData().execute();
        key_word=(EditText)findViewById(R.id.key_word);
        key_word.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                if(adapter!=null) {
                    String text = key_word.getText().toString().toLowerCase(Locale.getDefault());
                    adapter.filter(text);
                }
                else
                    Toast.makeText(ModelActivity.this, "Opps! No data found", Toast.LENGTH_LONG).show();

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

                startActivity(new Intent(ModelActivity.this, Cart.class));
                finish();
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


        if (id == R.id.action_settings) {
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


    class DownloadData extends AsyncTask<Void,Void,String>
    {
        SharedPreferences mPrefs = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        ProgressDialog process;
        @Override
        protected void onPostExecute(String jsondata) {

            try {
                Gson gson = new Gson();
                Type listOfTestObject = new TypeToken<List<Model>>() {
                }.getType();
                listData = gson.fromJson(jsondata, listOfTestObject);

                adapter = new ModelAdapter(ModelActivity.this, listData);
                ListView main_page = (ListView) findViewById(R.id.modle_list);
                main_page.setAdapter(adapter);
                main_page.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        if(InternetState.getState(ModelActivity.this)) {
                            Intent intent = new Intent(ModelActivity.this, CategoryActivity.class);
                            mPrefs.edit().putString("modelID", listData.get(i).getModelId()).apply();
                            startActivity(intent);
                        }
                            else
                            Toast.makeText(ModelActivity.this, "Opps! Connection has lost", Toast.LENGTH_LONG).show();

                    }
                });


                process.dismiss();
            }
            catch (NullPointerException ex)
            {
                process.dismiss();
                Toast.makeText(ModelActivity.this,"Opps! Connection is lost",Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(jsondata);
        }

        @Override
        protected void onPreExecute() {
            process=ProgressDialog.show(ModelActivity.this,ModelActivity.this.getResources().getString(R.string.app_name),"Loading....",true,false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {



            if(InternetState.getState(ModelActivity.this)) {

                return WebServiceCall.getModelJson(mPrefs.getString("makeID",""));

            }


            return null;
        }
    }
}
