package app.motaroart.com.motarpart.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;


import app.motaroart.com.motarpart.Detail;
import app.motaroart.com.motarpart.R;
import app.motaroart.com.motarpart.lazyloader.ImageLoader;
import app.motaroart.com.motarpart.pojo.CategoryPojo;
import app.motaroart.com.motarpart.pojo.Part;

/**
 * Created by Anil Ugale on 11/11/2014.
 */

public class PartAdapter extends BaseAdapter {

    List<Part> listData;
    Activity activity;
    LayoutInflater inflater;
    ImageLoader imageLoader;

    public PartAdapter(Activity activity, List<Part> listData) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View vi = inflater.inflate(R.layout.listview_row, null);
        TextView name= (TextView) vi.findViewById(R.id.part_name);
        TextView make= (TextView) vi.findViewById(R.id.part_make);
        TextView model= (TextView) vi.findViewById(R.id.part_model);
        TextView price= (TextView) vi.findViewById(R.id.part_mrp);
        TextView part_number= (TextView) vi.findViewById(R.id.part_number);
        TextView oem_no= (TextView) vi.findViewById(R.id.part_oem_no);
        TextView itm_code= (TextView) vi.findViewById(R.id.item_code);

        final int cnt=i;
        name.setText(listData.get(i).getName());
        make.setText(listData.get(i).getMake());
        model.setText(listData.get(i).getModel());
        price.setText("MRP:"+listData.get(i).getMrp()+"");
        part_number.setText(listData.get(i).getNumber());
        oem_no.setText(listData.get(i).getOem_no());
        itm_code.setText(listData.get(i).getItem_no());
        ImageView img= (ImageView) vi.findViewById(R.id.part_images);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, Detail.class));
            }
        });
        ToggleButton wish_btn=(ToggleButton)vi.findViewById(R.id.wish_btn);
        wish_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              if(b)
                  System.out.println("checked"+listData.get(cnt).getName());
                else
                  System.out.println("unchecked"+listData.get(cnt).getName());
            }
        });

        return vi;
    }
}
