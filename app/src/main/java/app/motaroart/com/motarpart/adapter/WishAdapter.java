package app.motaroart.com.motarpart.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.motaroart.com.motarpart.Detail;
import app.motaroart.com.motarpart.R;
import app.motaroart.com.motarpart.WishActivity;
import app.motaroart.com.motarpart.lazyloader.ImageLoader;
import app.motaroart.com.motarpart.pojo.Product;
import app.motaroart.com.motarpart.pojo.Wish;

/**
 * Created by Anil Ugale on 11/11/2014.
 */

public class WishAdapter extends BaseAdapter {

    List<Product> listData;
    List<Product> listMain;
    WishActivity activity;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    public WishAdapter(WishActivity activity, List<Product> listData) {

        listMain=new ArrayList<Product>();
        this.listMain=listData;
        this.listData=listData;
        this.activity=activity;
        inflater = (LayoutInflater)activity.
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
    public View getView(int i, View view, ViewGroup viewGroup) {



           final Product product= listMain.get(i);;
            View vi = inflater.inflate(R.layout.listview_wish, null);
            TextView product_name = (TextView) vi.findViewById(R.id.product_name);
            TextView product_make = (TextView) vi.findViewById(R.id.product_make);
            TextView product_model = (TextView) vi.findViewById(R.id.product_model);
            TextView product_mrp = (TextView) vi.findViewById(R.id.product_mrp);
            TextView product_code = (TextView) vi.findViewById(R.id.product_code);
            TextView product_number = (TextView) vi.findViewById(R.id.product_number);
            TextView product_oem_no = (TextView) vi.findViewById(R.id.product_oem_no);
            product_name.setText(product.getProductName());
            product_make.setText(product.getMakeName());
            product_model.setText(product.getModelName());
            product_mrp.setText("Rs."+product.getRetailerPrice());
            product_code.setText(product.getProductCode()+"");
            product_number.setText("Code."+product.getProductNumber());
            product_oem_no.setText(product.getOME()+"");

            Button details= (Button) vi.findViewById(R.id.deatail);

            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(activity, Detail.class);
                    intent.putExtra("Product", product)   ;
                    activity.startActivity(intent);
                }
            });

            Button add_2cart= (Button) vi.findViewById(R.id.add_2cart);
            add_2cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences mPrefs = activity.getSharedPreferences(activity.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
                    String JsonStr =mPrefs.getString("cart","");
                    Gson gson = new Gson();
                    Type listOfTestObject = new TypeToken<List<Product>>() {
                    }.getType();
                    List<Product>list = gson.fromJson(JsonStr, listOfTestObject);
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
                        mPrefs.edit().putString("cart",json).commit();
                        activity.updateCart(list.size());
                        Toast.makeText(activity,"Product added in cart",Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        new AlertDialog.Builder(activity)
                                .setTitle(activity.getResources().getString(R.string.app_name))
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



        // wish btn


        final ImageButton wish_btn=(ImageButton)vi.findViewById(R.id.remove_btn);
        final SharedPreferences mPrefs = activity.getSharedPreferences(activity.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        String withJson=mPrefs.getString("wish","");


        final Type listOfTestObject = new TypeToken<List<Wish>>() {
        }.getType();
        final Gson gson = new Gson();
        final List<Wish>listWish = gson.fromJson(withJson, listOfTestObject);

        final int cnt=i;
        wish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int j=0;j<listWish.size();j++) {
                Wish wish=listWish.get(j);

                    if(wish.getProductId().equals(product.getProductId())) {
                        listWish.remove(j);
                        listMain.remove(cnt);
                        mPrefs.edit().putString("wish", gson.toJson(listWish, listOfTestObject)).apply();
                        WishAdapter.this.notifyDataSetChanged();
                    }
                }
            }
        });

         return vi;

    }




}
