package app.motaroart.com.motarpart.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import app.motaroart.com.motarpart.R;
import app.motaroart.com.motarpart.lazyloader.ImageLoader;
import app.motaroart.com.motarpart.pojo.Make;
import app.motaroart.com.motarpart.pojo.OrderDProduct;

/**
 * Created by Anil Ugale on 11/11/2014.
 */

public class ProductHistoryAdapter extends BaseAdapter {


    ArrayList<OrderDProduct> listMain;
    Activity activity;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    public ProductHistoryAdapter(Activity activity, List<OrderDProduct> listData) {
        this.listMain=new ArrayList<OrderDProduct>();
        this.listMain.addAll(listData);
        this.activity=activity;
        inflater = (LayoutInflater)activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity);
    }

    @Override

    public int getCount() {
        return listMain.size();
    }

    @Override
    public Object getItem(int i) {
        return listMain.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null) {
            view = inflater.inflate(R.layout.list_product_order_history, viewGroup,false);
            holder = new ViewHolder();
            holder.p_name = (TextView) view.findViewById(R.id.p_name);
            holder.p_qty = (TextView) view.findViewById(R.id.p_qty);
            holder.p_rate = (TextView) view.findViewById(R.id.p_rate);
            holder.p_total = (TextView) view.findViewById(R.id.p_total);
            holder.p_number = (TextView) view.findViewById(R.id.p_number);
            view.setTag(holder);
        }
        else
        {
           holder=(ViewHolder)view.getTag();
        }


        holder.p_name.setText("  " + listMain.get(i).getProductName().toUpperCase());
        holder.p_number.setText("  " + listMain.get(i).getProductNumber().toUpperCase());
        holder.p_qty.setText("  " + listMain.get(i).getQuantity().toUpperCase());
        holder.p_total.setText("  " + listMain.get(i).getTotalAmount().toUpperCase());
        holder.p_rate.setText("  " + listMain.get(i).getProductPrice().toUpperCase());


        return view;
    }


    static class ViewHolder {
        TextView p_name,p_qty,p_rate,p_total,p_number;

    }

}
