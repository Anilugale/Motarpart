package app.motaroart.com.motarpart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.motaroart.com.motarpart.lazyloader.ImageLoader;
import app.motaroart.com.motarpart.pojo.Product;
import app.motaroart.com.motarpart.pojo.User;
import app.motaroart.com.motarpart.pojo.Wish;
import app.motaroart.com.motarpart.services.ImageViewer;
import app.motaroart.com.motarpart.services.WebServiceCall;


public class Detail extends ActionBarActivity {
    Product product;
    ImageLoader imageLoader ;
    List<Wish> listWish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.iconl);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        product = (Product) getIntent().getSerializableExtra("Product");

        TextView product_name = (TextView) findViewById(R.id.product_name);
        TextView product_make = (TextView) findViewById(R.id.product_make);
        TextView product_model = (TextView) findViewById(R.id.product_model);
        TextView product_mrp = (TextView) findViewById(R.id.product_mrp);
        TextView product_code = (TextView) findViewById(R.id.product_code);
        TextView product_number = (TextView) findViewById(R.id.product_number);

        TextView product_desc = (TextView) findViewById(R.id.product_desc);

        ImageView partImg= (ImageView) findViewById(R.id.part_images);
        partImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Detail.this, ImageViewer.class).putExtra("imageURL", WebServiceCall.BASE_URL + product.getProductImageUrl()));
            }
        });

        TextView stock = (TextView) findViewById(R.id.is_available);

        if(product.getIsAvailable().equals("true"))
            stock.setText("Available");
        else {
            stock.setTextColor(Color.parseColor("#CC0000"));
            stock.setText("Out of Stock");
        }

        imageLoader = new ImageLoader(getApplicationContext());
        imageLoader.DisplayImage( WebServiceCall.BASE_URL + product.getProductImageUrl(),partImg);
        product_name.setText(product.getProductName());
        product_make.setText(product.getMakeName());
        product_model.setText(product.getModelName());
        product_mrp.setText("KES " + product.getRetailerPrice());
        product_code.setText(product.getProductCode() + "");
        product_number.setText("Code." + product.getProductNumber());
        product_desc.setText(product.getProductDesc() );
        init();

    }

void init()
{
    Button add_2cart= (Button) findViewById(R.id.add_2cart);
    add_2cart.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(product.getIsAvailable().equals("true")) {
                SharedPreferences mPrefs = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
                String JsonStr = mPrefs.getString("cart", "");
                Gson gson = new Gson();
                Type listOfTestObject = new TypeToken<List<Product>>() {
                }.getType();
                List<Product> list = gson.fromJson(JsonStr, listOfTestObject);
                boolean flag = false;
                if (list != null) {
                    for (Product pro : list) {
                        if (pro.getProductId().equals(product.getProductId())) {
                            flag = true;
                            break;
                        }
                    }
                } else {
                    list = new ArrayList<Product>();
                }
                if (flag != true) {
                    list.add(product);
                    String json = gson.toJson(list, listOfTestObject);
                    mPrefs.edit().putString("cart", json).apply();
                    updateCart(list.size());
                    Toast.makeText(Detail.this, "Product added in cart", Toast.LENGTH_LONG).show();
                } else {
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
            }else
                Toast.makeText(Detail.this,"Out of Stock try after some time",Toast.LENGTH_SHORT).show();
        }
    });


    // wish btn


    final ToggleButton wish_btn=(ToggleButton)findViewById(R.id.wish_btn);
    final SharedPreferences mPrefs = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
    String wishJson=mPrefs.getString("wish","");


    final Type listOfTestObject = new TypeToken<List<Wish>>() {
    }.getType();
    final Gson gson = new Gson();


    listWish=gson.fromJson(wishJson, listOfTestObject);


    if(listWish!=null)
    {
        {
            for (Wish wish : listWish) {
                if (wish.getProductId().equals(product.getProductId())) {
                    wish_btn.setChecked(true);
                    break;
                }
            }
        }
    }
    else
        listWish =new ArrayList<>();


    wish_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(wish_btn.isChecked())
            {
                mPrefs.edit().remove("wish").apply();
                String userStr=mPrefs.getString("user","");
                if(!userStr.equals("")) {

                    Type type = new TypeToken<User>() {
                    }.getType();
                    User user = gson.fromJson(userStr, type);
                    Wish wish = new Wish();
                    wish.setAccountId(user.getAccountId());
                    wish.setProductId(product.getProductId());
                    listWish.add(wish);
                    Gson g = new Gson();
                    System.out.println(g.toJson(wish).toString());
                    mPrefs.edit().putString("wish", gson.toJson(listWish, listOfTestObject)).apply();
                }else
                {
                    new AlertDialog.Builder(Detail.this)
                            .setTitle(getString(R.string.app_name))
                            .setMessage("login for see wish list")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            }
            else
            {
                for (int i=0;i<listWish.size();i++)
                {
                    if(listWish.get(i).getProductId().equals(product.getProductId()))
                    {
                        listWish.remove(i);
                        mPrefs.edit().putString("wish",gson.toJson(listWish,listOfTestObject)).apply();

                    }
                }
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
        count.setGravity(Gravity.TOP | Gravity.CENTER);
        count.setLayoutParams(imgvwDimens);
        count.setBackgroundResource(R.drawable.cart);
        count.setPadding(5, 8, 5, 5);
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
