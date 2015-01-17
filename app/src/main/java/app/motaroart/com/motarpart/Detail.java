package app.motaroart.com.motarpart;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.motaroart.com.motarpart.lazyloader.ImageLoader;
import app.motaroart.com.motarpart.pojo.Product;
import app.motaroart.com.motarpart.services.ImageViewer;
import app.motaroart.com.motarpart.services.WebServiceCall;


public class Detail extends Activity {
    Product product;
    ImageLoader imageLoader ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        product = (Product) getIntent().getSerializableExtra("Product");

        TextView product_name = (TextView) findViewById(R.id.product_name);
        TextView product_make = (TextView) findViewById(R.id.product_make);
        TextView product_model = (TextView) findViewById(R.id.product_model);
        TextView product_mrp = (TextView) findViewById(R.id.product_mrp);
        TextView product_code = (TextView) findViewById(R.id.product_code);
        TextView product_number = (TextView) findViewById(R.id.product_number);
        TextView product_oem_no = (TextView) findViewById(R.id.product_oem_no);
        TextView product_desc = (TextView) findViewById(R.id.product_desc);

        ImageView partImg= (ImageView) findViewById(R.id.part_images);
        partImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Detail.this, ImageViewer.class).putExtra("imageURL", WebServiceCall.BASE_URL + product.getProductImageUrl()));
            }
        });

        imageLoader = new ImageLoader(getApplicationContext());
        imageLoader.DisplayImage( WebServiceCall.BASE_URL + product.getProductImageUrl(),partImg);
        product_name.setText(product.getProductName());
        product_make.setText(product.getMakeName());
        product_model.setText(product.getModelName());
        product_mrp.setText("KES " + product.getRetailerPrice());
        product_code.setText(product.getProductCode() + "");
        product_number.setText("Code." + product.getProductNumber());
        product_oem_no.setText(product.getOME() + "");
        product_desc.setText(product.getProductDesc() + "");
        init();

    }

void init()
{
    Button add_2cart= (Button) findViewById(R.id.add_2cart);
    add_2cart.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            SharedPreferences mPrefs = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
            String JsonStr =mPrefs.getString("cart","");
            Gson gson = new Gson();
            Type listOfTestObject = new TypeToken<List<Product>>() {
            }.getType();
            List<Product> list = gson.fromJson(JsonStr, listOfTestObject);
            boolean flag=false;
            if (list!=null) {
                for (Product pro:list)
                {
                    if(pro.getProductId().equals(product.getProductId()))
                    {
                        flag=true;
                        break;
                    }
                }
            }
            else
            {
                list=new ArrayList<Product>();
            }
            if(flag!=true)
            {
                list.add(product);
                String json=gson.toJson(list,listOfTestObject);
                mPrefs.edit().putString("cart",json).apply();
               updateCart(list.size());
                Toast.makeText(Detail.this, "Product added in cart", Toast.LENGTH_LONG).show();
            }
            else
            {
                new AlertDialog.Builder(Detail.this)
                        .setTitle(Detail.this.getResources().getString(R.string.app_name))
                        .setMessage("This Product is already in Cart")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
    });
}


    // action bar menu and count

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

                startActivity(new Intent(Detail.this, Cart.class));
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
