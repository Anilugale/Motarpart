package app.motaroart.com.motarpart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;

import app.motaroart.com.motarpart.adapter.MakeAdapter;
import app.motaroart.com.motarpart.pojo.Make;
import app.motaroart.com.motarpart.pojo.Product;


public class MakeActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    String JsonStr = "[{\"MakeId\":1,\"MakeName\":\"AUDI\",\"Description\":\"\",\"IsActive\":true,\"CreatedOn\":\"2014-11-23T16:20:45.353\"},{\"MakeId\":7,\"MakeName\":\"HONDA\",\"Description\":null,\"IsActive\":true,\"CreatedOn\":\"2014-11-23T16:21:23.07\"},{\"MakeId\":2,\"MakeName\":\"HYUNDAI\",\"Description\":null,\"IsActive\":true,\"CreatedOn\":\"2014-11-23T16:20:47.713\"},{\"MakeId\":3,\"MakeName\":\"KIA\",\"Description\":null,\"IsActive\":true,\"CreatedOn\":\"2014-11-23T16:20:55.903\"},{\"MakeId\":10,\"MakeName\":\"MAZDA\",\"Description\":null,\"IsActive\":true,\"CreatedOn\":\"2014-11-23T16:21:32.173\"},{\"MakeId\":4,\"MakeName\":\"MITSUBISHI\",\"Description\":null,\"IsActive\":false,\"CreatedOn\":\"2014-11-23T16:21:00.257\"},{\"MakeId\":11,\"MakeName\":\"New Make\",\"Description\":\"1\",\"IsActive\":true,\"CreatedOn\":\"2014-12-01T16:27:38.233\"},{\"MakeId\":8,\"MakeName\":\"NISSAN\",\"Description\":null,\"IsActive\":true,\"CreatedOn\":\"2014-11-23T16:21:27.847\"},{\"MakeId\":6,\"MakeName\":\"SUBARU\",\"Description\":null,\"IsActive\":true,\"CreatedOn\":\"2014-11-23T16:21:14.32\"},{\"MakeId\":9,\"MakeName\":\"SUZUKI\",\"Description\":null,\"IsActive\":true,\"CreatedOn\":\"2014-11-23T16:21:31.647\"},{\"MakeId\":5,\"MakeName\":\"TOYOTA\",\"Description\":null,\"IsActive\":true,\"CreatedOn\":\"2014-11-23T16:21:13.787\"}]";
    private NavigationDrawerFragment mNavigationDrawerFragment;

    MakeAdapter adapter;
    private CharSequence mTitle;
    List<Make> listData;
    EditText key_word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        Gson gson = new Gson();
        Type listOfTestObject = new TypeToken<List<Make>>() {
        }.getType();
        listData = gson.fromJson(JsonStr, listOfTestObject);

        adapter = new MakeAdapter(this, listData);
        ListView main_page = (ListView) findViewById(R.id.main_page_list);
        main_page.setAdapter(adapter);
        main_page.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MakeActivity.this, ModelActivity.class);
                intent.putExtra("MakeID", listData.get(i).getMakeId());
                startActivity(intent);
            }
        });
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

                startActivity(new Intent(MakeActivity.this, Cart.class));
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


}
