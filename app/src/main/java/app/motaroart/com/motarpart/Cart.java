package app.motaroart.com.motarpart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import app.motaroart.com.motarpart.adapter.CartAdapter;
import app.motaroart.com.motarpart.pojo.Product;


public class Cart extends Activity {
    List<Product> listData;
    String JsonStr = "\n" +
            "[{\"MakeName\":\"HYUNDAI\",\"ModelName\":\"X 50\",\"Category\":\"Auto Lamp\",\"ProductId\":1,\"ProductNumber\":\"1000001\",\"ProductCode\":\"1001\",\"OME\":\"10021445\",\"ProductName\":\"abcd\",\"MakeId\":2,\"ModelId\":4,\"CategoryId\":1,\"IsAvailable\":true,\"IsActive\":true,\"ProductImageUrl\":null,\"ProductDesc\":null,\"ProductPrice\":5000.00,\"RetailerPrice\":3000.00,\"WholesalerPrice\":2000.00},{\"MakeName\":\"HYUNDAI\",\"ModelName\":\"X 50\",\"Category\":\"Auto Lamp\",\"ProductId\":2,\"ProductNumber\":\"1000001\",\"ProductCode\":\"1002\",\"OME\":\"10021445\",\"ProductName\":\"abcd\",\"MakeId\":2,\"ModelId\":4,\"CategoryId\":1,\"IsAvailable\":true,\"IsActive\":true,\"ProductImageUrl\":null,\"ProductDesc\":null,\"ProductPrice\":5000.00,\"RetailerPrice\":3000.00,\"WholesalerPrice\":2000.00},{\"MakeName\":\"HYUNDAI\",\"ModelName\":\"X 50\",\"Category\":\"Auto Lamp\",\"ProductId\":3,\"ProductNumber\":\"1000001\",\"ProductCode\":\"1003\",\"OME\":\"10021445\",\"ProductName\":\"abcd\",\"MakeId\":2,\"ModelId\":4,\"CategoryId\":1,\"IsAvailable\":true,\"IsActive\":true,\"ProductImageUrl\":null,\"ProductDesc\":null,\"ProductPrice\":5000.00,\"RetailerPrice\":3000.00,\"WholesalerPrice\":2000.00},{\"MakeName\":\"HYUNDAI\",\"ModelName\":\"X 50\",\"Category\":\"Auto Lamp\",\"ProductId\":4,\"ProductNumber\":\"1000001\",\"ProductCode\":\"1004\",\"OME\":\"10021445\",\"ProductName\":\"abcd\",\"MakeId\":2,\"ModelId\":4,\"CategoryId\":1,\"IsAvailable\":true,\"IsActive\":true,\"ProductImageUrl\":null,\"ProductDesc\":null,\"ProductPrice\":5000.00,\"RetailerPrice\":3000.00,\"WholesalerPrice\":2000.00},{\"MakeName\":\"HYUNDAI\",\"ModelName\":\"X 50\",\"Category\":\"Auto Lamp\",\"ProductId\":5,\"ProductNumber\":\"1000001\",\"ProductCode\":\"1005\",\"OME\":\"10021445\",\"ProductName\":\"abcd\",\"MakeId\":2,\"ModelId\":4,\"CategoryId\":1,\"IsAvailable\":true,\"IsActive\":true,\"ProductImageUrl\":null,\"ProductDesc\":null,\"ProductPrice\":5000.00,\"RetailerPrice\":3000.00,\"WholesalerPrice\":2000.00}]\n";

    CartAdapter adapter;
    TextView product_grand_price;
    ListView main_page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();

    }

    private void init() {

        product_grand_price=(TextView)findViewById(R.id.product_grand_price);


        Type listOfTestObject = new TypeToken<List<Product>>() {
        }.getType();
        Gson gson = new Gson();
        listData = gson.fromJson(JsonStr, listOfTestObject);

        double grand=0.0;
        for (Product product:listData)
        {
            grand+=Double.valueOf(product.getProductPrice().trim());
        }
        product_grand_price.setText("Rs."+grand);
        TextView cart_cnt = (TextView) findViewById(R.id.cart_cnt);
        cart_cnt.setText("My Cart (" + listData.size() + ")");

        adapter = new CartAdapter(this, listData);
        main_page = (ListView) findViewById(R.id.mycart_list);
        main_page.setAdapter(adapter);
        main_page.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Cart.this, Login.class);
                startActivity(intent);
            }
        });
    }

    public void updateGrandPrice(double oldPrice,double newPrice)
    {

        String old=product_grand_price.getText().toString().substring(3,product_grand_price.getText().length());
        double newPriceGrand=(Double.valueOf(old)-oldPrice)+newPrice;
        product_grand_price.setText("Rs."+newPriceGrand);
    }
}

