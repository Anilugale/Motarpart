package app.motaroart.com.motarpart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import app.motaroart.com.motarpart.adapter.ModelAdapter;
import app.motaroart.com.motarpart.pojo.Model;

public class ModelActivity extends Activity {
    List listData;


    String JsonStr = "[{\"ModelId\":4,\"MakeName\":\"HYUNDAI\",\"MakeID\":2,\"ModelName\":\"X 50\",\"ModelDesc\":\"1234\",\"PhotoUrl\":\"electricity bill.pdf\",\"IsActive\":true,\"ModelId1\":4},{\"ModelId\":5,\"MakeName\":\"MITSUBISHI\",\"MakeID\":4,\"ModelName\":\"X 50 555555508\",\"ModelDesc\":\"123455555555\",\"PhotoUrl\":\"\",\"IsActive\":false,\"ModelId1\":5}]";

    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);
        init();
    }

    void init() {
        Type listOfTestObject = new TypeToken<List<Model>>() {
        }.getType();
        Gson gson = new Gson();
        listData = gson.fromJson(JsonStr, listOfTestObject);
        ModelAdapter adapter = new ModelAdapter(this, listData);
        ListView main_page = (ListView) findViewById(R.id.modle_list);
        main_page.setAdapter(adapter);
        main_page.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ModelActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });
    }


}
