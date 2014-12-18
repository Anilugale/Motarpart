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

import java.lang.reflect.Type;
import java.util.List;

import app.motaroart.com.motarpart.adapter.ProductAdapter;
import app.motaroart.com.motarpart.pojo.Product;


public class ProductActivity extends Activity {


    List listData;
    String JsonStr="\n" +
            "[{\"MakeName\":\"HYUNDAI\",\"ModelName\":\"X 50\",\"Category\":\"Auto Lamp\",\"ProductId\":1,\"ProductNumber\":\"1000001\",\"ProductCode\":\"1001\",\"OME\":\"10021445\",\"ProductName\":\"abcd\",\"MakeId\":2,\"ModelId\":4,\"CategoryId\":1,\"IsAvailable\":true,\"IsActive\":true,\"ProductImageUrl\":null,\"ProductDesc\":null,\"ProductPrice\":5000.00,\"RetailerPrice\":3000.00,\"WholesalerPrice\":2000.00},{\"MakeName\":\"HYUNDAI\",\"ModelName\":\"X 50\",\"Category\":\"Auto Lamp\",\"ProductId\":2,\"ProductNumber\":\"1000001\",\"ProductCode\":\"1002\",\"OME\":\"10021445\",\"ProductName\":\"abcd\",\"MakeId\":2,\"ModelId\":4,\"CategoryId\":1,\"IsAvailable\":true,\"IsActive\":true,\"ProductImageUrl\":null,\"ProductDesc\":null,\"ProductPrice\":5000.00,\"RetailerPrice\":3000.00,\"WholesalerPrice\":2000.00},{\"MakeName\":\"HYUNDAI\",\"ModelName\":\"X 50\",\"Category\":\"Auto Lamp\",\"ProductId\":3,\"ProductNumber\":\"1000001\",\"ProductCode\":\"1003\",\"OME\":\"10021445\",\"ProductName\":\"abcd\",\"MakeId\":2,\"ModelId\":4,\"CategoryId\":1,\"IsAvailable\":true,\"IsActive\":true,\"ProductImageUrl\":null,\"ProductDesc\":null,\"ProductPrice\":5000.00,\"RetailerPrice\":3000.00,\"WholesalerPrice\":2000.00},{\"MakeName\":\"HYUNDAI\",\"ModelName\":\"X 50\",\"Category\":\"Auto Lamp\",\"ProductId\":4,\"ProductNumber\":\"1000001\",\"ProductCode\":\"1004\",\"OME\":\"10021445\",\"ProductName\":\"abcd\",\"MakeId\":2,\"ModelId\":4,\"CategoryId\":1,\"IsAvailable\":true,\"IsActive\":true,\"ProductImageUrl\":null,\"ProductDesc\":null,\"ProductPrice\":5000.00,\"RetailerPrice\":3000.00,\"WholesalerPrice\":2000.00},{\"MakeName\":\"HYUNDAI\",\"ModelName\":\"X 50\",\"Category\":\"Auto Lamp\",\"ProductId\":5,\"ProductNumber\":\"1000001\",\"ProductCode\":\"1005\",\"OME\":\"10021445\",\"ProductName\":\"abcd\",\"MakeId\":2,\"ModelId\":4,\"CategoryId\":1,\"IsAvailable\":true,\"IsActive\":true,\"ProductImageUrl\":null,\"ProductDesc\":null,\"ProductPrice\":5000.00,\"RetailerPrice\":3000.00,\"WholesalerPrice\":2000.00}]\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        Type listOfTestObject = new TypeToken<List<Product>>() {
        }.getType();
        Gson gson = new Gson();
        listData = gson.fromJson(JsonStr, listOfTestObject);



        ProductAdapter adapter=new ProductAdapter(this,listData);

        ListView main_page=(ListView)findViewById(R.id.product_list);
        main_page.setAdapter(adapter);

        main_page.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent =new Intent(ProductActivity.this,Login.class);

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

                startActivity(new Intent(ProductActivity.this, Cart.class));
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


    public void updateCart(int cnt)
    {
        if (count != null)
            count.setText(cnt + "  ");
    }


}
