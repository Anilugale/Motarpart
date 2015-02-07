package app.motaroart.com.motarpart;

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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.motaroart.com.motarpart.adapter.CartAdapter;
import app.motaroart.com.motarpart.pojo.Order;
import app.motaroart.com.motarpart.pojo.OrderProduct;
import app.motaroart.com.motarpart.pojo.Price;
import app.motaroart.com.motarpart.pojo.Product;
import app.motaroart.com.motarpart.pojo.Setting;
import app.motaroart.com.motarpart.pojo.User;
import app.motaroart.com.motarpart.services.InternetState;


public class Cart extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks{
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    List<Product> listData;
    List<Setting> settings;
    CartAdapter adapter;
    TextView product_grand_price;
    ListView main_page;
    TextView cart_cnt,vat_per,vat_price,total_item;
    SharedPreferences mPrefs;
    double vatRate;
    User user;
    String curruncy="KES ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.iconl);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        ImageButton searchKey=(ImageButton)findViewById(R.id.searchKey);
        searchKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText keySearch=(EditText)findViewById(R.id.keySearch);
                if(keySearch.getText().toString().trim().length()>0) {

                    if(InternetState.getState(Cart.this)) {
                        Intent i = new Intent(Cart.this, ProductActivity.class);
                        SharedPreferences.Editor edit = mPrefs.edit();
                        edit.putString("searchKey", keySearch.getText().toString());
                        edit.apply();
                        startActivity(i);
                    }
                    else
                        Toast.makeText(Cart.this, "Connection has lost", Toast.LENGTH_LONG).show();

                }
            }
        });


        mPrefs = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        if(!mPrefs.getString("Setting","").equals("")) {

            new DownLoadSetting().execute();
            String userStr = mPrefs.getString("user", "");
            Gson gson = new Gson();
            Type type = new TypeToken<User>() {
            }.getType();
            user = gson.fromJson(userStr, type);
        }
        else
        {
            Toast.makeText(this,"Connection has lost",Toast.LENGTH_SHORT).show();
        }

    }

    private void init() {
        vat_per=(TextView)findViewById(R.id.vat_per);
        total_item=(TextView)findViewById(R.id.item_total);
        vat_price=(TextView)findViewById(R.id.vat_price);


        vat_per.setText("VAT ("+Integer.toString((int)temp)+"%)");
        product_grand_price=(TextView)findViewById(R.id.grand_total);
        Type listOfTestObject = new TypeToken<List<Product>>() {
        }.getType();
        String JsonStr =mPrefs.getString("cart","");
        Gson gson = new Gson();
        listData = gson.fromJson(JsonStr, listOfTestObject);
        double grand=0.0;
        cart_cnt = (TextView) findViewById(R.id.cart_cnt);
        if(listData!=null) {
            if (listData.size() != 0) {
                for (Product product : listData) {
                    if (user != null) {

                        if (user.getAccountType().equals("C")) {
                            grand += Double.valueOf(product.getProductPrice().trim());
                        } else if (user.getAccountType().equals("W")) {
                            grand += Double.valueOf(product.getWholesalerPrice().trim());
                        } else if (user.getAccountType().equals("R")) {
                            grand += Double.valueOf(product.getRetailerPrice().trim());
                        } else {
                            grand += Double.valueOf(product.getProductPrice().trim());
                        }
                    } else {
                        grand += Double.valueOf(product.getProductPrice().trim());
                    }


                }

                double vatPrice = (grand * vatRate);
                this.vatPrice = vatPrice;

                totalPrice = Math.floor(grand);
                total_item.setText(curruncy + totalPrice + "0");
                product_grand_price.setText(curruncy + Math.floor(totalPrice + vatPrice) + "0");
                vat_price.setText(curruncy + Math.floor(vatPrice) + "0");
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
            } else {
                cart_cnt.setText("My Cart (" + 0 + ")");
                main_page = (ListView) findViewById(R.id.mycart_list);
                main_page.setVisibility(View.GONE);
                TextView error = (TextView) findViewById(R.id.error);
                error.setVisibility(View.VISIBLE);
                Button con = (Button) findViewById(R.id.continueChekOut);
                con.setEnabled(false);
                LinearLayout layout=(LinearLayout) findViewById(R.id.totalprice);
                layout.setVisibility(View.GONE);
            }
        }
        else {
            cart_cnt.setText("My Cart (" + 0 + ")");
            main_page = (ListView) findViewById(R.id.mycart_list);
            main_page.setVisibility(View.GONE);
            TextView error=(TextView)findViewById(R.id.error);
            error.setVisibility(View.VISIBLE);
            Button con=(Button) findViewById(R.id.continueChekOut);
            con.setEnabled(false);
            LinearLayout layout=(LinearLayout) findViewById(R.id.totalprice);
            layout.setVisibility(View.GONE);
        }
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


        double newPricedata=0.0;

        for (Map.Entry<String, Price> entry : adapter.listQty.entrySet())
        {


            newPricedata+=entry.getValue().price*entry.getValue().qty;


        }

        totalPrice=newPricedata;//(totalPrice - oldPrice) + newPrice;
        total_item.setText(curruncy+totalPrice+"0");
        double vatPrice=totalPrice*vatRate;
        this.vatPrice=vatPrice;
        product_grand_price.setText(curruncy+Math.floor(totalPrice+vatPrice)+"0");
        vat_price.setText(curruncy+Math.floor(vatPrice)+"0");
    }
    public void updateGrandPriceMinuse(double oldPrice,int productID)
    {

        int cartcnt=(int)productID;
        if (count!=null)
            count.setText(cartcnt + "  ");
        double newPricedata=0.0;
        for (Map.Entry<String, Price> entry : adapter.listQty.entrySet())
        {


            newPricedata+=entry.getValue().price*entry.getValue().qty;


        }

        if(cartcnt==0)
        {

            main_page.setVisibility(View.GONE);
            TextView error=(TextView)findViewById(R.id.error);
            error.setVisibility(View.VISIBLE);
            Button con=(Button) findViewById(R.id.continueChekOut);
            con.setEnabled(false);
            LinearLayout layout=(LinearLayout) findViewById(R.id.totalprice);
            layout.setVisibility(View.GONE);
        }

        totalPrice=newPricedata;//(totalPrice- oldPrice) ;
        total_item.setText(curruncy+totalPrice+"0");
        this.vatPrice=totalPrice*vatRate;
        cart_cnt.setText("My Cart (" + cartcnt + ")");
        product_grand_price.setText(curruncy+ Math.floor(totalPrice+vatPrice)+"0");
        vat_price.setText(curruncy+Math.floor(vatPrice)+"0");
    }

    double totalPrice;
    double vatPrice;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==444) {
            String userStr = mPrefs.getString("user", "");
            Gson gson = new Gson();
            Type type = new TypeToken<User>() {
            }.getType();
            user = gson.fromJson(userStr, type);
        }

    }

    public void continueChekOut(View view) {


        if(adapter!=null)
        {

            if(user==null)
            {
                this.startActivityForResult(new Intent(this, Login.class).putExtra("Cart", true),555);

            }
            else {

                /// parameter setting
                Order order = new Order();
                order.setAccountId(user.getAccountId());
                order.setOrderBy(user.getLoginId());
                order.setProductCount(String.valueOf(adapter.listData.size()));
                order.setOrderSource("MAPP");
                List<OrderProduct> productList = new ArrayList<>();

                for (Product pro : adapter.listData) {
                    OrderProduct op = new OrderProduct();
                    op.setCategoryId(pro.getCategoryId());
                    op.setCategoryName(pro.getCategory());
                    op.setMakeId(pro.getMakeId());
                    op.setMakeName(pro.getMakeName());
                    op.setProductId(pro.getProductId());
                    op.setProductCode(pro.getProductCode());
                    op.setProductName(pro.getProductName());
                    op.setProductNumber(pro.getProductNumber());
                    op.setProductPrice(pro.getProductPrice());
                    op.setModelId(pro.getModelId());
                    op.setModelName(pro.getModelName());
                    op.setUrl(pro.getProductImageUrl());
                    op.setQuantity(adapter.listQty.get(pro.getProductId()).qty+"");
                    productList.add(op);
                }
                order.setProductList(productList);
                order.setOrderAmount(String.valueOf(totalPrice));
                order.setVATAmount(String.valueOf(vatPrice));
                order.setVATPercent(Integer.toString((int)temp)+"");
                order.setTotalAmount(String.valueOf(totalPrice + vatPrice));

                if(InternetState.getState(this)) {
                    if (order.getProductList().size() != 0 ) {
                        if( !order.getTotalAmount().equals("0.0")) {
                            Intent intent = new Intent(this, Summry.class);
                            intent.putExtra("Order", order);
                            startActivity(intent);
                            finish();
                        }
                        else
                            Toast.makeText(this, "Quantity is 0.", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(this, "card is empty.", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(this, "Connection has lost.", Toast.LENGTH_SHORT).show();
            }
        }

    }
    double temp = 0;

    public void shopMore(View view) {

        Intent homeIntent = new Intent(this, MakeActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }

    class DownLoadSetting extends AsyncTask<Void,Void,String>
    {
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            pd=ProgressDialog.show(Cart.this,getString(R.string.app_name),"Loading",true,false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String aVoid) {

            if(aVoid!=null) {
                Gson gson = new Gson();

                Type type = new TypeToken<List<Setting>>() {
                }.getType();
                settings= gson.fromJson(aVoid,type);

                for(Setting s:settings)
                {
                    if(s.getKeyName().equals("VAT"))
                    {
                        temp=Integer.valueOf(s.getKeyValue());
                        break;
                    }
                }

                vatRate=temp /100.0;
                init();
            }
            else
            {
                Toast.makeText(Cart.this,"Connection has loast",Toast.LENGTH_SHORT).show();
            }

            pd.dismiss();
            super.onPostExecute(aVoid);
        }

        @Override
        protected String doInBackground(Void... voids) {
            return mPrefs.getString("Setting","");

        }
    }



    /// Cart menu and data
    TextView count;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        count = new TextView(this);

        count.setTextColor(Color.BLUE);


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
    protected void onRestart() {
        super.onRestart();
        String userStr = mPrefs.getString("user", "");
        Gson gson = new Gson();
        Type type = new TypeToken<User>() {
        }.getType();
        user = gson.fromJson(userStr, type);
        SharedPreferences mPrefs = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        String JsonStr = mPrefs.getString("cart", "");

        Type listOfTestObject = new TypeToken<List<Product>>() {
        }.getType();
        List<Product> list = gson.fromJson(JsonStr, listOfTestObject);
        if (count != null&&list!=null)
            count.setText(list.size() + "  ");
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (mNavigationDrawerFragment.mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if(id ==android.R.id.home)
        {
            Intent homeIntent = new Intent(this, MakeActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }

        if (id == R.id.action_user) {
            startActivity(new Intent(this, Login.class));
            return true;
        }
        if(id==R.id.action_wish){
            if(InternetState.getState(this)) {
                startActivity(new Intent(this, WishActivity.class));
            }
            Toast.makeText(this, "Connection has lost", Toast.LENGTH_LONG).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

