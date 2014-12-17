package app.motaroart.com.motarpart.adapter;



import android.app.Activity;
import android.content.Context;
import android.test.UiThreadTest;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.motaroart.com.motarpart.R;
import app.motaroart.com.motarpart.lazyloader.ImageLoader;
import app.motaroart.com.motarpart.pojo.CategoryPojo;
import app.motaroart.com.motarpart.pojo.Make;
import app.motaroart.com.motarpart.pojo.Model;

/**
 * Created by Anil Ugale on 11/11/2014.
 */

public class ModelAdapter extends BaseAdapter {

    List<Model> listData;
    List<Model> listMain;
    Activity activity;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    public ModelAdapter(Activity activity, List<Model> listData) {

        listMain=new ArrayList<Model>();
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

        View vi = inflater.inflate(R.layout.list_view_main, null);
        TextView cat_name=(TextView)vi.findViewById(R.id.model_name);
        cat_name.setText("  "+listData.get(i).getModelName()); return vi;
    }




}
