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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;

import app.motaroart.com.motarpart.adapter.MakeAdapter;
import app.motaroart.com.motarpart.adapter.ModelAdapter;
import app.motaroart.com.motarpart.pojo.Make;
import app.motaroart.com.motarpart.pojo.Model;
import app.motaroart.com.motarpart.pojo.Product;
import app.motaroart.com.motarpart.services.InternetState;
import app.motaroart.com.motarpart.services.WebServiceCall;

public class ModelActivity extends Activity {
    List<Model> listData;
    EditText key_word;
    ModelAdapter adapter;
    String JsonStr = "[{\"ModelId\":4,\"MakeName\":\"HYUNDAI\",\"MakeID\":2,\"ModelName\":\"X 50\",\"ModelDesc\":\"1234\",\"PhotoUrl\":\"electricity bill.pdf\",\"IsActive\":true,\"ModelId1\":4},{\"ModelId\":5,\"MakeName\":\"MITSUBISHI\",\"MakeID\":4,\"ModelName\":\"X 50 555555508\",\"ModelDesc\":\"123455555555\",\"PhotoUrl\":\"\",\"IsActive\":false,\"ModelId1\":5}]";

    @Override
    @SuppressWarnings("deprecation")
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
                // TODO Auto-generated method stub
                String text = key_word.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
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
                if (jsondata == null) {

                    jsondata = mPrefs.getString("model", "");
                    if(jsondata.equals(""))
                        throw new NullPointerException("Some required files are missing");

                }
                Gson gson = new Gson();
                Type listOfTestObject = new TypeToken<List<Model>>() {
                }.getType();
                listData = gson.fromJson(jsondata, listOfTestObject);

                adapter = new ModelAdapter(ModelActivity.this, listData);
                ListView main_page = (ListView) findViewById(R.id.main_page_list);
                main_page.setAdapter(adapter);
                main_page.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(ModelActivity.this, CategoryActivity.class);
                        intent.putExtra("MakeID", listData.get(i).getModelId());
                        startActivity(intent);
                    }
                });


                process.dismiss();
            }
            catch (NullPointerException ex)
            {
                process.dismiss();
                new AlertDialog.Builder(ModelActivity.this)
                        .setTitle(ModelActivity.this.getResources().getString(R.string.app_name))
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
            process=ProgressDialog.show(ModelActivity.this,ModelActivity.this.getResources().getString(R.string.app_name),"Loading....",true,false);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String jsondata=null;
            if(InternetState.getState(ModelActivity.this)) {
                jsondata = WebServiceCall.getModelJson();
                mPrefs.edit().putString("model",jsondata).apply();
            }


            return jsondata;
        }
    }
}
