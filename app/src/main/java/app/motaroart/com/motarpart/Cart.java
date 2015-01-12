package app.motaroart.com.motarpart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.List;

import app.motaroart.com.motarpart.adapter.CartAdapter;
import app.motaroart.com.motarpart.pojo.Order;
import app.motaroart.com.motarpart.pojo.Product;
import app.motaroart.com.motarpart.pojo.Setting;
import app.motaroart.com.motarpart.pojo.User;
import app.motaroart.com.motarpart.services.InternetState;
import app.motaroart.com.motarpart.services.WebServiceCall;


public class Cart extends Activity {
    List<Product> listData;
    List<Setting> settings;
    CartAdapter adapter;
    TextView product_grand_price;
    ListView main_page;
    TextView cart_cnt,vat_per,vat_price;
    SharedPreferences mPrefs;
    double vatRate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        if(InternetState.getState(this)) {
            new DownLoadSetting().execute();

        }
    }

    private void init() {
        vat_per=(TextView)findViewById(R.id.vat_per);
        vat_price=(TextView)findViewById(R.id.vat_price);
        vat_per.setText(settings.get(5).getKeyValue()+"%");
        product_grand_price=(TextView)findViewById(R.id.product_grand_price);
        Type listOfTestObject = new TypeToken<List<Product>>() {
        }.getType();
        Context mContext = getApplicationContext();
        mPrefs = mContext.getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        String JsonStr =mPrefs.getString("cart","");
        Gson gson = new Gson();
        listData = gson.fromJson(JsonStr, listOfTestObject);
        double grand=0.0;
        cart_cnt = (TextView) findViewById(R.id.cart_cnt);
        if(listData!=null) {
            for (Product product : listData) {
                grand += Double.valueOf(product.getProductPrice().trim());
            }

            double vatPrice=(grand*vatRate);
            product_grand_price.setText("Rs." +Math.floor(grand+vatPrice));
            vat_price.setText(Math.floor(vatPrice)+"");
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
        cart_cnt.setText("My Cart (" + productID + ")"+settings.get(5).getKeyValue());
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
        double vatPrice=newPriceGrand*vatRate;
        vat_price.setText(Math.floor(vatPrice)+"");
        product_grand_price.setText("Rs." +Math.floor(newPriceGrand+vatPrice));
    }
    public void updateGrandPriceMinuse(double oldPrice,int productID)
    {
        String old=product_grand_price.getText().toString().substring(3,product_grand_price.getText().length());
        double newPriceGrand = (Double.valueOf(old) - oldPrice) ;

        product_grand_price.setText("Rs." + newPriceGrand);

        cart_cnt.setText("My Cart (" + productID + ")");
    }


    public void continueChekOut(View view) {

        String userStr=  mPrefs.getString("user","");
        if(userStr.trim().length()==0)
        {
            startActivity(new Intent(this,Login.class).putExtra("Cart",true));
        }
        else
        {
        /*
            "AccountId":"1", *
                "ProductCount":"5",*
                "OrderAmount":"56000.00",
                "VATPercent":"16.50",
                "VATAmount":"9240.00",
                "TotalAmount":"65240.00",
                "TransactionNumber":"",
                "TransactionMode":"MPESA/CARD",-
                "Remark":"MPESA TEXT DATA",-
                "OrderSource":"MAPP", *
                "VoucherCode":"",
                "OrderBy":"Jai",*
                */

            Gson gson=new Gson();
            Type type=new TypeToken<User>(){}.getType();
            User user=gson.fromJson(userStr,type);
            /// parameter setting
            Order order=new Order();
            order.setAccountId(user.getAccountId());
            order.setOrderBy(user.getLoginId());
            order.setProductCount(String.valueOf(adapter.listData.size()));
            order.setOrderSource("MAPP");
            order.setProductList(adapter.listData);


            //TODO
           /* order.setOrderAmount();
            order.setVATAmount();
            order.setVATPercent();
            order.setTotalAmount();*/

            Intent intent=new Intent(this,Summry.class);
            intent.putExtra("Order",order);
            startActivity(intent);


        }

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
                 vatRate=((Double.valueOf(settings.get(5).getKeyValue()) /100.0));
                init();
            }

           pd.dismiss();
            super.onPostExecute(aVoid);
        }

        @Override
        protected String doInBackground(Void... voids) {
            return WebServiceCall.getSetting(mPrefs);

        }
    }
    double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }
}

