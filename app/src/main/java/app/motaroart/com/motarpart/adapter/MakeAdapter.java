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

/**
 * Created by Anil Ugale on 11/11/2014.
 */

public class MakeAdapter extends BaseAdapter {

    List<Make> listData;
    ArrayList<Make> listMain;
    Activity activity;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    public MakeAdapter(Activity activity, List<Make> listData) {

        listMain=new ArrayList<Make>();
        this.listMain=new ArrayList<Make>();
        this.listMain.addAll(listData);
        this.listData=listData;
        this.activity=activity;
        inflater = (LayoutInflater)activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity);
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
        ViewHolder holder;
        if(view==null) {
            view = inflater.inflate(R.layout.list_view_main, viewGroup,false);
            holder = new ViewHolder();

            view.setTag(holder);
        }
        else
        {
           holder=(ViewHolder)view.getTag();
        }

        holder.cat_name = (TextView) view.findViewById(R.id.model_name);
        holder.cat_name.setText("  " + listData.get(i).getMakeName().toUpperCase());

        return view;
    }


    static class ViewHolder {
        TextView cat_name;

    }
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        listData.clear();
        if (charText.length() == 0) {
            listData.addAll(listMain);
        }
        else
        {
            for (Make wp : listMain)
            {
                if (wp.getMakeName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    listData.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
