package app.motaroart.com.motarpart.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import app.motaroart.com.motarpart.R;
import app.motaroart.com.motarpart.lazyloader.ImageLoader;
import app.motaroart.com.motarpart.pojo.OrderHistory;

/**
 * Created by Anil Ugale on 11/11/2014.
 */

public class OrderHistoryAdapter extends BaseAdapter {

   public List<OrderHistory> listData;

    Activity activity;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    String currency="KES ";

    public OrderHistoryAdapter(Activity activity, List<OrderHistory> listData) {
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

        ViewHolder holder;
        if(view==null) {
            view = inflater.inflate(R.layout.list_order_history, viewGroup,false);
            holder = new ViewHolder();
            holder.order_no = (TextView) view.findViewById(R.id.od_number);
            holder.date = (TextView) view.findViewById(R.id.date);
            holder.total = (TextView) view.findViewById(R.id.grand_total);


            view.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)view.getTag();
        }


        holder.order_no.setText(listData.get(i).getOrderNumber());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm");
        try {
            Date past = format.parse(listData.get(i).getOrderDate());
            holder.date.setText(""+ sdf.format(past));
        } catch (ParseException e) {
            e.printStackTrace();
        }

       holder.total.setText(currency + Double.valueOf(listData.get(i).getTotalAmount())+"0");




        return view;


    }


    static class ViewHolder
    {
        TextView order_no,date,count,item_total,source,mode,vat_per,vat_amt,total,state;

    }



}
