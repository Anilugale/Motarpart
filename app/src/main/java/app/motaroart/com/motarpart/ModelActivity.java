package app.motaroart.com.motarpart;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.motaroart.com.motarpart.pojo.Model;
import app.motaroart.com.motarpart.adapter.ModelAdapter;


public class ModelActivity extends Activity {
    List listData;


    String JsonStr="[{\"ModelId\":4,\"MakeName\":\"HYUNDAI\",\"MakeID\":2,\"ModelName\":\"X 50\",\"ModelDesc\":\"1234\",\"PhotoUrl\":\"electricity bill.pdf\",\"IsActive\":true,\"ModelId1\":4},{\"ModelId\":5,\"MakeName\":\"MITSUBISHI\",\"MakeID\":4,\"ModelName\":\"X 50 555555508\",\"ModelDesc\":\"123455555555\",\"PhotoUrl\":\"\",\"IsActive\":false,\"ModelId1\":5}]";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);
         listData=new ArrayList<Model>();
        try {
            JSONArray ModelArray = new JSONArray(JsonStr);
            for(int i=0;i<ModelArray.length();i++) {

                JSONObject ModelJson = ModelArray.getJSONObject(i);
                Model make=new Model();

                make.setModelId(ModelJson.getInt("ModelId"));
                make.setMakeID(ModelJson.getInt("MakeID"));
                make.setModelName(ModelJson.getString("ModelName"));
                make.setMakeName(ModelJson.getString("MakeName"));
                make.setModelDesc(ModelJson.getString("ModelDesc"));
                make.setPhotoUrl(ModelJson.getString("PhotoUrl"));

                listData.add(make);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        ModelAdapter adapter=new ModelAdapter(this,listData);

        ListView main_page=(ListView)findViewById(R.id.modle_list);
        main_page.setAdapter(adapter);

        main_page.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent =new Intent(ModelActivity.this,ModelActivity.class);

                startActivity(intent);

            }
        });
    }



}
