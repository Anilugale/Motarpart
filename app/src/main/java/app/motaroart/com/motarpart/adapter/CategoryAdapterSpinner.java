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
import app.motaroart.com.motarpart.pojo.CategoryPojo;

/**
 * Created by Anil Ugale on 11/11/2014.
 */

public class CategoryAdapterSpinner extends BaseAdapter {

    List<CategoryPojo> listData;
    ArrayList<CategoryPojo> listMain;
    Activity activity;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    public CategoryAdapterSpinner(Activity activity, List<CategoryPojo> listData) {

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

        View vi = inflater.inflate(R.layout.spinner_item, null);
        TextView cat_name=(TextView)vi.findViewById(R.id.model_name);
        cat_name.setText("  "+listData.get(i).getCategory().toUpperCase()); return vi;
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
