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
            holder.count = (TextView) view.findViewById(R.id.count);
            holder.item_total = (TextView) view.findViewById(R.id.item_total);
            holder.source = (TextView) view.findViewById(R.id.payment_type);
            holder.mode = (TextView) view.findViewById(R.id.mode);

            holder.vat_per = (TextView) view.findViewById(R.id.vat_per);
            holder.vat_amt = (TextView) view.findViewById(R.id.vat_total);
            holder.total = (TextView) view.findViewById(R.id.grand_total);
            holder.state = (TextView) view.findViewById(R.id.state);

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

        holder.count.setText("" + listData.get(i).getProductCount());
        holder.item_total.setText("" + Double.valueOf(listData.get(i).getOrderAmount()));
        holder.source.setText("" + listData.get(i).getOrderSource());
        holder.mode.setText("" + listData.get(i).getTransactionMode());
        holder.vat_per.setText( " VAT ( "+listData.get(i).getVATPercent()+" %)");
        holder.vat_amt.setText("" +Double.valueOf( listData.get(i).getVATAmount()));
        holder.total.setText("" + Double.valueOf(listData.get(i).getTotalAmount()));

        if(listData.get(i).getOrderStatus().equals("O"))
            holder.state.setText("Pending");
        else  if(listData.get(i).getOrderStatus().equals("D"))
            holder.state.setText("Delivered");
         else  if(listData.get(i).getOrderStatus().equals("I"))
            holder.state.setText("In Transit");


        return view;


    }


    static class ViewHolder
    {
        TextView order_no,date,count,item_total,source,mode,vat_per,vat_amt,total,state;
    }



}
