package app.motaroart.com.motarpart.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.motaroart.com.motarpart.Cart;
import app.motaroart.com.motarpart.Detail;
import app.motaroart.com.motarpart.R;
import app.motaroart.com.motarpart.lazyloader.ImageLoader;
import app.motaroart.com.motarpart.pojo.Product;

/**
 * Created by Anil Ugale on 11/11/2014.
 */

public class CartAdapter extends BaseAdapter {

    List<Product> listData;
    List<Product> listMain;
    Activity activity;
    LayoutInflater inflater;
    ImageLoader imageLoader;

    public CartAdapter(Activity activity, List<Product> listData) {

        listMain = new ArrayList<Product>();
        this.listMain = listData;
        this.listData = listData;
        this.activity = activity;
        inflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext());
    }

    @Override

    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {



            final Product product = listMain.get(i);
            View vi = inflater.inflate(R.layout.list_cart, null);
            TextView product_name = (TextView) vi.findViewById(R.id.product_name);
            final EditText product_qty = (EditText) vi.findViewById(R.id.product_qty);
            TextView product_mrp = (TextView) vi.findViewById(R.id.product_mrp);
            final TextView product_qty_total = (TextView) vi.findViewById(R.id.product_qty_total);
            TextView product_code = (TextView) vi.findViewById(R.id.product_code);
            ImageButton remove_btn=(ImageButton)vi.findViewById(R.id.remove_btn);
            remove_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String old =product_qty_total.getText().toString().substring(3,product_qty_total.getText().toString().length());
                    listMain.remove(i);
                    SharedPreferences mPrefs = activity.getSharedPreferences(activity.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
                    Gson gson = new Gson();
                    Type listOfTestObject = new TypeToken<List<Product>>() {
                    }.getType();
                    String json=gson.toJson(listMain,listOfTestObject);
                    mPrefs.edit().putString("cart",json).commit();
                    ((Cart)activity).updateGrandPrice(Double.valueOf(old.trim()),0.0,listData.size()+"");

                }
            });
            product_name.setText(product.getProductName());
            product_mrp.setText("Rs." + product.getProductPrice());
            product_qty_total.setText("Rs." + product.getProductPrice());
            product_code.setText(product.getProductNumber() + "");
            product_qty.setText("1");
            product_qty.addTextChangedListener(new TextWatcher() {

                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {

                }

                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {

                }

                public void afterTextChanged(Editable s) {
                    int price_count = 0;

                    if (s.length() != 0) {

                        price_count = Integer.valueOf(s.toString());
                    }
                    String old =product_qty_total.getText().toString().substring(3,product_qty_total.getText().toString().length());

                    double price = Double.valueOf(product.getProductPrice()) * (price_count);
                    product_qty_total.setText("Rs." + price);


                    ((Cart)activity).updateGrandPrice(Double.valueOf(old.trim()),Double.valueOf(price),listData.size()+"");


                }
            });


            Button details = (Button) vi.findViewById(R.id.deatail);

            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, Detail.class);
                    intent.putExtra("Product", product);
                    activity.startActivity(intent);
                }
            });
            return vi;

    }


    void chnageLsit()
    {
        notifyDataSetChanged();
    }

}
