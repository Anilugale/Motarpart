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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import app.motaroart.com.motarpart.Cart;
import app.motaroart.com.motarpart.Detail;
import app.motaroart.com.motarpart.R;
import app.motaroart.com.motarpart.lazyloader.ImageLoader;
import app.motaroart.com.motarpart.pojo.Price;
import app.motaroart.com.motarpart.pojo.Product;
import app.motaroart.com.motarpart.pojo.User;
import app.motaroart.com.motarpart.services.WebServiceCall;

/**
 * Created by Anil Ugale on 11/11/2010.
 */

public class CartAdapter extends BaseAdapter {

    public List<Product> listData;
    public Map<String,String> listMain;
    public Map<String,Price> listQty;
    Activity activity;
    LayoutInflater inflater;
    ImageLoader imageLoader;

    public CartAdapter(Activity activity, List<Product> listData) {

        listMain = new TreeMap<>();
        listQty = new TreeMap<>();
        this.listData = listData;
        this.activity = activity;
        inflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext());

        for(Product pro:listData)
        {
            listQty.put(pro.getProductId(), new Price(Double.valueOf(pro.getProductPrice()),1));
        }


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
        return Long.valueOf(listData.get(i).getProductId());
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {



        final Product product = listData.get(i);
        View vi = inflater.inflate(R.layout.list_cart, null);
        TextView product_name = (TextView) vi.findViewById(R.id.product_name);
        final EditText product_qty = (EditText) vi.findViewById(R.id.product_qty);
        TextView product_mrp = (TextView) vi.findViewById(R.id.product_mrp);
        TextView product_code1 = (TextView) vi.findViewById(R.id.product_code1);
        product_code1.setText(product.getProductCode());
        final TextView product_qty_total = (TextView) vi.findViewById(R.id.product_qty_total);
        TextView product_code = (TextView) vi.findViewById(R.id.product_code);
        ImageButton remove_btn=(ImageButton)vi.findViewById(R.id.remove_btn);





        remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String old =product_qty_total.getText().toString().substring(4,product_qty_total.getText().toString().length());
                listData.remove(i);
             listQty.remove(product.getProductId());
                SharedPreferences mPrefs = activity.getSharedPreferences(activity.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
                Gson gson = new Gson();
                Type listOfTestObject = new TypeToken<List<Product>>() {
                }.getType();
                String json=gson.toJson(listData,listOfTestObject);
                mPrefs.edit().putString("cart",json).apply();
                ((Cart)activity).updateGrandPriceMinuse(Double.valueOf(old.trim()),listData.size());

                CartAdapter.this.notifyDataSetChanged();

            }
        });

        ImageView part_images= (ImageView) vi.findViewById(R.id.part_images);
        imageLoader.DisplayImage(WebServiceCall.BASE_URL+product.getProductImageUrl(),part_images);
        part_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(activity, Detail.class);
                intent.putExtra("Product", product)   ;
                activity.startActivity(intent);
            }
        });
        product_name.setText(product.getProductName());

        SharedPreferences pref=activity.getSharedPreferences(activity.getString(R.string.app_name),Activity.MODE_PRIVATE);
        Gson gson=new Gson();
        Type type=new TypeToken<User>(){}.getType();
        final User user=gson.fromJson(pref.getString("user",""),type);





        if(user!=null) {

            if(user.getAccountType().equals("C")) {
                product_mrp.setText("KES " + product.getProductPrice());
                product_qty_total.setText("KES " + product.getProductPrice());
            }
            else  if(user.getAccountType().equals("W")) {
                product_mrp.setText("" + product.getWholesalerPrice());
                product_qty_total.setText("" + product.getWholesalerPrice());
            }else  if(user.getAccountType().equals("R")) {
                product_mrp.setText("KES " + product.getRetailerPrice());
                product_qty_total.setText("KES " + product.getRetailerPrice());
            }
            else
            {
                product_mrp.setText("KES " + product.getProductPrice());
                product_qty_total.setText("KES " + product.getProductPrice());
            }
        }
        else
        {
            product_mrp.setText("KES " + product.getProductPrice());
            product_mrp.setText("KES " + product.getProductPrice());
            product_qty_total.setText("KES " + product.getProductPrice());
        }

        product_code.setText(product.getProductNumber() + "");
        if(listMain.get(product.getProductId())!=null) {
            product_qty.setText(listMain.get(product.getProductId()));
            int price_count = 0;

            listMain.put(product.getProductId(),listMain.get(product.getProductId()));

            if (listMain.get(product.getProductId()).length() != 0) {

                price_count = Integer.valueOf(listMain.get(product.getProductId()));
            }
            String old =product_qty_total.getText().toString().substring(4,product_qty_total.getText().toString().length());

            double value;
            if(user!=null) {

                if(user.getAccountType().equals("C")) {
                    value=   Double.valueOf(product.getProductPrice());
                }
                else  if(user.getAccountType().equals("W")) {
                    value=   Double.valueOf(product.getWholesalerPrice());
                }else  if(user.getAccountType().equals("R")) {
                    value=   Double.valueOf(product.getRetailerPrice());
                }
                else
                {
                    value=   Double.valueOf(product.getProductPrice());
                }
            }
            else
            {
                value=   Double.valueOf(product.getProductPrice());
            }


            double price = value * (price_count);
            product_qty_total.setText("KES " + price);

            if(listQty.get(product.getProductId())!=null)
                listQty.put(product.getProductId(), new Price(Double.valueOf(product.getProductPrice()),listQty.get(product.getProductId()).qty));
            else
                listQty.put(product.getProductId(), new Price(Double.valueOf(product.getProductPrice()),1));

            ((Cart)activity).updateGrandPrice(Double.valueOf(old.trim()),(price),listData.size()+"");
        }
        else {
            product_qty.setText("1");
            listQty.put(product.getProductId(), new Price(Double.valueOf(product.getProductPrice()),1));
            listMain.put(product.getProductId(),"1");
        }



        product_qty.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {
                int price_count = 0;
                double price=0.0;
                if (s.length() != 0) {
                    price_count = Integer.valueOf(s.toString());





                    listQty.put(product.getProductId(), new Price(Double.valueOf(product.getProductPrice()),Integer.valueOf(s.toString())));
                    if (user != null) {

                        if (user.getAccountType().equals("C")) {
                            listMain.put(product.getProductId(), s.toString());


                            price = Double.valueOf(product.getProductPrice()) * (price_count);
                        } else if (user.getAccountType().equals("W")) {
                            listMain.put(product.getWholesalerPrice(), s.toString());
                            price = Double.valueOf(product.getWholesalerPrice()) * (price_count);
                        } else if (user.getAccountType().equals("R")) {
                            listMain.put(product.getRetailerPrice(), s.toString());
                            price = Double.valueOf(product.getRetailerPrice()) * (price_count);
                        } else {
                            listMain.put(product.getProductId(), s.toString());
                            price = Double.valueOf(product.getProductPrice()) * (price_count);
                        }
                    } else {
                        listMain.put(product.getProductId(), s.toString());
                        price = Double.valueOf(product.getProductPrice()) * (price_count);
                    }


                    String old = product_qty_total.getText().toString().substring(4, product_qty_total.getText().toString().length());

                    product_qty_total.setText("KES " + price + "0");

                    ((Cart) activity).updateGrandPrice(Double.valueOf(old.trim()), (price), listData.size() + "");
                }
            }

        });





        return vi;

    }




}
