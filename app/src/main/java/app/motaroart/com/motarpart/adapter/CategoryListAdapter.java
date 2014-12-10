package app.motaroart.com.motarpart.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.motaroart.com.motarpart.R;
import app.motaroart.com.motarpart.lazyloader.ImageLoader;
import app.motaroart.com.motarpart.pojo.CategoryPojo;

/**
 * Created by Anil Ugale on 11/11/2014.
 */

public class CategoryListAdapter extends BaseAdapter {

    List<CategoryPojo> listData;
    Activity activity;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    public CategoryListAdapter(Activity activity, List<CategoryPojo> listData) {
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

        View vi = inflater.inflate(R.layout.listview_category, null);

        TextView cat_name=(TextView)vi.findViewById(R.id.cat_name);
        TextView cat_qty=(TextView)vi.findViewById(R.id.cat_qty);


        cat_name.setText(listData.get(i).getName());
        cat_qty.setText(listData.get(i).getQty()+"");

        return vi;
    }
}
