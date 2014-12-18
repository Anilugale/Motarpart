package app.motaroart.com.motarpart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.motaroart.com.motarpart.adapter.CategoryAdapter;
import app.motaroart.com.motarpart.pojo.CategoryPojo;
import app.motaroart.com.motarpart.pojo.Model;
import app.motaroart.com.motarpart.pojo.Product;


public class CategoryActivity extends Activity {

    List listData;
    String JsonStr="[{\"CategoryId\":2,\"Category\":\"Auto Glass\",\"Description\":\"\",\"IsActive\":true,\"CreatedOn\":\"2014-12-01T11:28:40.867\"},{\"CategoryId\":1,\"Category\":\"Auto Lamp\",\"Description\":\"Auto Lamp\",\"IsActive\":true,\"CreatedOn\":\"2014-12-01T11:28:22.86\"},{\"CategoryId\":4,\"Category\":\"Auto Service\",\"Description\":\"\",\"IsActive\":false,\"CreatedOn\":\"2014-12-01T11:30:28.623\"},{\"CategoryId\":3,\"Category\":\"Body Part\",\"Description\":\"\",\"IsActive\":true,\"CreatedOn\":\"2014-12-01T11:29:08.22\"}]";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category2);

        listData=new ArrayList<Model>();
        try {
            JSONArray ModelArray = new JSONArray(JsonStr);
            for(int i=0;i<ModelArray.length();i++) {

                JSONObject categoryJson = ModelArray.getJSONObject(i);
                CategoryPojo make=new CategoryPojo();
                make.setCategoryId(categoryJson.getInt("CategoryId"));
                make.setCategory(categoryJson.getString("Category"));
                make.setDescription(categoryJson.getString("Description"));

                listData.add(make);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        CategoryAdapter adapter=new CategoryAdapter(this,listData);

        ListView main_page=(ListView)findViewById(R.id.cat_list);
        main_page.setAdapter(adapter);

        main_page.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent =new Intent(CategoryActivity.this,ProductActivity.class);

                startActivity(intent);

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

                startActivity(new Intent(CategoryActivity.this, Cart.class));
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


}
