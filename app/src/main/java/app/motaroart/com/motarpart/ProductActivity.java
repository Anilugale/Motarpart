package app.motaroart.com.motarpart;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.motaroart.com.motarpart.adapter.CategoryAdapter;
import app.motaroart.com.motarpart.adapter.ProductAdapter;
import app.motaroart.com.motarpart.pojo.CategoryPojo;
import app.motaroart.com.motarpart.pojo.Model;
import app.motaroart.com.motarpart.pojo.Product;


public class ProductActivity extends Activity {


    List listData;
    String JsonStr="\n" +
            "[{\"MakeName\":\"HYUNDAI\",\"ModelName\":\"X 50\",\"Category\":\"Auto Lamp\",\"ProductId\":1,\"ProductNumber\":\"1000001\",\"ProductCode\":\"1001\",\"OME\":\"10021445\",\"ProductName\":\"abcd\",\"MakeId\":2,\"ModelId\":4,\"CategoryId\":1,\"IsAvailable\":true,\"IsActive\":true,\"ProductImageUrl\":null,\"ProductDesc\":null,\"ProductPrice\":5000.00,\"RetailerPrice\":3000.00,\"WholesalerPrice\":2000.00},{\"MakeName\":\"HYUNDAI\",\"ModelName\":\"X 50\",\"Category\":\"Auto Lamp\",\"ProductId\":2,\"ProductNumber\":\"1000001\",\"ProductCode\":\"1002\",\"OME\":\"10021445\",\"ProductName\":\"abcd\",\"MakeId\":2,\"ModelId\":4,\"CategoryId\":1,\"IsAvailable\":true,\"IsActive\":true,\"ProductImageUrl\":null,\"ProductDesc\":null,\"ProductPrice\":5000.00,\"RetailerPrice\":3000.00,\"WholesalerPrice\":2000.00},{\"MakeName\":\"HYUNDAI\",\"ModelName\":\"X 50\",\"Category\":\"Auto Lamp\",\"ProductId\":3,\"ProductNumber\":\"1000001\",\"ProductCode\":\"1003\",\"OME\":\"10021445\",\"ProductName\":\"abcd\",\"MakeId\":2,\"ModelId\":4,\"CategoryId\":1,\"IsAvailable\":true,\"IsActive\":true,\"ProductImageUrl\":null,\"ProductDesc\":null,\"ProductPrice\":5000.00,\"RetailerPrice\":3000.00,\"WholesalerPrice\":2000.00},{\"MakeName\":\"HYUNDAI\",\"ModelName\":\"X 50\",\"Category\":\"Auto Lamp\",\"ProductId\":4,\"ProductNumber\":\"1000001\",\"ProductCode\":\"1004\",\"OME\":\"10021445\",\"ProductName\":\"abcd\",\"MakeId\":2,\"ModelId\":4,\"CategoryId\":1,\"IsAvailable\":true,\"IsActive\":true,\"ProductImageUrl\":null,\"ProductDesc\":null,\"ProductPrice\":5000.00,\"RetailerPrice\":3000.00,\"WholesalerPrice\":2000.00},{\"MakeName\":\"HYUNDAI\",\"ModelName\":\"X 50\",\"Category\":\"Auto Lamp\",\"ProductId\":5,\"ProductNumber\":\"1000001\",\"ProductCode\":\"1005\",\"OME\":\"10021445\",\"ProductName\":\"abcd\",\"MakeId\":2,\"ModelId\":4,\"CategoryId\":1,\"IsAvailable\":true,\"IsActive\":true,\"ProductImageUrl\":null,\"ProductDesc\":null,\"ProductPrice\":5000.00,\"RetailerPrice\":3000.00,\"WholesalerPrice\":2000.00}]\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);



        listData=new ArrayList<Product>();
        try {
            JSONArray ProductArray = new JSONArray(JsonStr);
            for(int i=0;i<ProductArray.length();i++) {

                JSONObject productJson = ProductArray.getJSONObject(i);
                Product product=new Product();

                product.setMakeName(productJson.getString("MakeName"));
                product.setModelName(productJson.getString("ModelName"));
                product.setCategory(productJson.getString("Category"));
                product.setProductId(productJson.getInt("ProductId"));
                product.setProductNumber(productJson.getLong("ProductNumber"));
                product.setProductCode(productJson.getInt("ProductCode"));
                product.setOME(productJson.getLong("OME"));
                product.setProductName(productJson.getString("ProductName"));
                product.setMakeId(productJson.getInt("MakeId"));
                product.setModelId(productJson.getInt("ModelId"));
                product.setCategoryId(productJson.getInt("CategoryId"));
                product.setAvailable(productJson.getBoolean("IsAvailable"));
                product.setProductImageUrl(productJson.getString("ProductImageUrl"));
                product.setProductDesc(productJson.getString("ProductDesc"));
                product.setProductPrice(productJson.getDouble("ProductPrice"));
                product.setRetailerPrice(productJson.getDouble("RetailerPrice"));
                product.setWholesalerPrice(productJson.getDouble("WholesalerPrice"));

                listData.add(product);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


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






/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
