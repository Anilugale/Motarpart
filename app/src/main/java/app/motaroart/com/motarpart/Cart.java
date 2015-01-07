package app.motaroart.com.motarpart;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

    CartAdapter adapter;
    TextView product_grand_price;
    ListView main_page;
    TextView cart_cnt;
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
        Context mContext = getApplicationContext();
        SharedPreferences mPrefs = mContext.getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        String JsonStr =mPrefs.getString("cart","");
        Gson gson = new Gson();
        listData = gson.fromJson(JsonStr, listOfTestObject);
        double grand=0.0;
        cart_cnt = (TextView) findViewById(R.id.cart_cnt);
        if(listData!=null) {
            for (Product product : listData) {
                grand += Double.valueOf(product.getProductPrice().trim());
            }
            product_grand_price.setText("Rs." + grand);
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
        else
        cart_cnt.setText("My Cart (" +0 + ")");


    }

    public void updateGrandPrice(double oldPrice,double newPrice,String productID)
    {
        cart_cnt.setText("My Cart (" + productID + ")");
        if (adapter.getCount()==0 )
            {
                new AlertDialog.Builder(this)
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage("No products in cart.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }




            String old=product_grand_price.getText().toString().substring(3,product_grand_price.getText().length());
            double newPriceGrand = (Double.valueOf(old) - oldPrice) + newPrice;
            product_grand_price.setText("Rs." + newPriceGrand);


    }
    public void updateGrandPriceMinuse(double oldPrice,int productID)
    {
        String old=product_grand_price.getText().toString().substring(3,product_grand_price.getText().length());
        double newPriceGrand = (Double.valueOf(old) - oldPrice) ;
        product_grand_price.setText("Rs." + newPriceGrand);
        cart_cnt.setText("My Cart (" + productID + ")");
    }


}

