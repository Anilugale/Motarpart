package app.motaroart.com.motarpart.adapter;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import app.motaroart.com.motarpart.pojo.User;
import app.motaroart.com.motarpart.pojo.Wish;
import app.motaroart.com.motarpart.services.WebServiceCall;

/**
 * Created by Anil Ugale on 11/11/2014.
 */

public class WishAdapter extends BaseAdapter {

    List<Product> listData;
    List<Product> listMain;
    WishActivity activity;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    User user;
    public WishAdapter(WishActivity activity, List<Product> listData) {

        SharedPreferences pref=activity.getSharedPreferences(activity.getString(R.string.app_name),Context.MODE_PRIVATE);
        String userStr=pref.getString("user","");

        if(!userStr.equals("")) {
            Gson gson = new Gson();
            Type type = new TypeToken<User>() {
            }.getType();
            user = gson.fromJson(userStr, type);
        }
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



        final Product product= listMain.get(i);
        View vi = inflater.inflate(R.layout.listview_wish, null);
        TextView product_name = (TextView) vi.findViewById(R.id.product_name);
        TextView product_make = (TextView) vi.findViewById(R.id.product_make);
        TextView product_model = (TextView) vi.findViewById(R.id.product_model);
        TextView product_mrp = (TextView) vi.findViewById(R.id.product_mrp);
        TextView product_code = (TextView) vi.findViewById(R.id.product_code);
        TextView product_number = (TextView) vi.findViewById(R.id.product_number);
        TextView stock = (TextView) vi.findViewById(R.id.is_available);

        if(product.getIsAvailable().equals("true"))
            stock.setText("In Stock");
        else {
            stock.setTextColor(Color.parseColor("#CC0000"));
            stock.setText("Out of Stock");
        }
        ImageView part_images= (ImageView) vi.findViewById(R.id.part_images);

        imageLoader.DisplayImage(WebServiceCall.BASE_URL+product.getProductImageUrl(),part_images);

        part_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  activity.startActivity(new Intent(activity, ImageViewer.class).putExtra("imageURL",WebServiceCall.BASE_URL+product.getProductImageUrl()));
                Intent intent =new Intent(activity, Detail.class);
                intent.putExtra("Product", product)   ;
                activity.startActivity(intent);
            }
        });
        product_name.setText(product.getProductName());
        product_make.setText(product.getMakeName());
        product_model.setText(product.getModelName());
        product_mrp.setText("KES."+product.getRetailerPrice());
        product_code.setText(product.getProductCode()+"");
        product_number.setText("Code."+product.getProductNumber());



        Button add_2cart= (Button) vi.findViewById(R.id.add_2cart);
        add_2cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (product.getIsAvailable().equals("true")) {
                    SharedPreferences mPrefs = activity.getSharedPreferences(activity.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
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
                    if (!flag) {
                        list.add(product);
                        String json = gson.toJson(list, listOfTestObject);
                        mPrefs.edit().putString("cart", json).apply();
                        activity.updateCart(list.size());
                        Toast.makeText(activity, "Product added in cart", Toast.LENGTH_LONG).show();

                    } else {
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
                }else
                    Toast.makeText(activity,"Out of Stock try after some time.",Toast.LENGTH_SHORT).show();

            }
        });



        // wish btn


        final ImageButton wish_btn=(ImageButton)vi.findViewById(R.id.remove_btn);

        wish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



             new RemoveWish().execute(product.getProductId());
                for(int cnt=0;cnt<listMain.size();cnt++)
                {
                    Product p=listMain.get(cnt);
                    if(p.getProductId()==product.getProductId())
                    {
                        Type listOfTestWish = new TypeToken<List<Wish>>() {
                        }.getType();
                        SharedPreferences mPrefs = activity.getSharedPreferences(activity.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
                        Gson gson = new Gson();
                        List<Wish> listWish = gson.fromJson( mPrefs.getString("wish",""), listOfTestWish);
                        if(listWish!=null) {
                            if(listWish.size()>0) {
                                for (int cv = 0; cv < listWish.size(); cv++) {
                                    Wish w = listWish.get(cv);
                                    if (w.getProductId() == product.getProductId()) {
                                        listWish.remove(cv);
                                        System.out.println(listWish.size());
                                        mPrefs.edit().putString("wish", gson.toJson(listWish)).apply();
                                        break;
                                    }
                                }
                            }
                        }
                        listMain.remove(cnt);
                        WishAdapter.this.notifyDataSetChanged();
                       new RemoveWish().execute(product.getProductId());
                        break;
                    }
                }


            }
        });

        return vi;

    }


    class  RemoveWish extends AsyncTask<String,Void,Void>
    { ProgressDialog pd;
        @Override
        protected void onPreExecute() {
             pd=ProgressDialog.show(activity,activity.getString(R.string.app_name),"Removing..!",true,false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pd.dismiss();
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(String... str) {
            System.out.println(WebServiceCall.removeWishListItem(user.getAccountId(), str[0]) + str[0]);// TODO

            return null;
        }
    }



}
