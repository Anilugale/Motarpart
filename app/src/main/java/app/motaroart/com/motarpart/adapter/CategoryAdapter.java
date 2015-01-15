package app.motaroart.com.motarpart.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import app.motaroart.com.motarpart.R;
import app.motaroart.com.motarpart.lazyloader.ImageLoader;
import app.motaroart.com.motarpart.pojo.CategoryPojo;
import app.motaroart.com.motarpart.services.WebServiceCall;

/**
 * Created by Anil Ugale on 11/11/2014.
 */

public class CategoryAdapter extends BaseAdapter {

    List<CategoryPojo> listData;
    ArrayList<CategoryPojo> listMain;
    Activity activity;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    public CategoryAdapter(Activity activity, List<CategoryPojo> listData) {

        listMain=new ArrayList<CategoryPojo>();
        this.listMain.addAll(listData);
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
        ViewHolder holder;
        if(view==null) {
            view = inflater.inflate(R.layout.list_view_cat, viewGroup,false);
            holder = new ViewHolder();
            holder.part_images= (ImageView)view.findViewById(R.id.cat_img);
            holder.cat_name = (TextView) view.findViewById(R.id.model_name);
            view.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)view.getTag();
        }
        holder.cat_name.setText("  " + listData.get(i).getCategory().toUpperCase());
        holder.cat_name.setText("  " + listData.get(i).getCategory().toUpperCase());
        imageLoader.DisplayImage(WebServiceCall.BASE_URL+listData.get(i).getImageUrl(), holder.part_images);
         return view;
    }
    static class ViewHolder {
        TextView cat_name;
        ImageView part_images;

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
            for (CategoryPojo wp : listMain)
            {
                if (wp.getCategory().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    listData.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


}
