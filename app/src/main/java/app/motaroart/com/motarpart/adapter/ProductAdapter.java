package app.motaroart.com.motarpart.adapter;



import android.app.Activity;
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
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.motaroart.com.motarpart.Detail;
import app.motaroart.com.motarpart.ProductActivity;
import app.motaroart.com.motarpart.R;
import app.motaroart.com.motarpart.lazyloader.ImageLoader;
import app.motaroart.com.motarpart.pojo.Product;

/**
 * Created by Anil Ugale on 11/11/2014.
 */

public class ProductAdapter extends BaseAdapter {

    List<Product> listData;
    List<Product> listMain;
    Activity activity;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    public ProductAdapter(Activity activity, List<Product> listData) {

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


        if(view==null) {
           final Product product= listMain.get(i);;
            View vi = inflater.inflate(R.layout.listview_product, null);
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
                    for (Product pro:list)
                    {
                        if(pro.getProductId()==product.getProductId())
                        {
                            flag=true;
                            break;
                        }
                    }
                    if(!flag)
                    {
                        list.add(product);
                        String json=gson.toJson(list,listOfTestObject);
                        mPrefs.edit().putString("cart",json).commit();
                        ((ProductActivity)activity).updateCart(list.size());

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

            return vi;
        }
        else
            return view;
    }




}
