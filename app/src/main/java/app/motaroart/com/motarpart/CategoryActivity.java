package app.motaroart.com.motarpart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.motaroart.com.motarpart.adapter.CategoryAdapter;
import app.motaroart.com.motarpart.pojo.CategoryPojo;
import app.motaroart.com.motarpart.pojo.Model;


public class CategoryActivity extends ActionBarActivity {

    List listData;
    String JsonStr="[{\"CategoryId\":2,\"Category\":\"Auto Glass\",\"Description\":\"\",\"IsActive\":true,\"CreatedOn\":\"2014-12-01T11:28:40.867\"},{\"CategoryId\":1,\"Category\":\"Auto Lamp\",\"Description\":\"Auto Lamp\",\"IsActive\":true,\"CreatedOn\":\"2014-12-01T11:28:22.86\"},{\"CategoryId\":4,\"Category\":\"Auto Service\",\"Description\":\"\",\"IsActive\":false,\"CreatedOn\":\"2014-12-01T11:30:28.623\"},{\"CategoryId\":3,\"Category\":\"Body Part\",\"Description\":\"\",\"IsActive\":true,\"CreatedOn\":\"2014-12-01T11:29:08.22\"}]";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category2);

        listData=new ArrayList<Model>();
        try {
            JSONArray ModelArray = new JSONArray(JsonStr);
            for(int i=0;i<ModelArray.length();i++) {

                JSONObject categoryJson = ModelArray.getJSONObject(i);
                CategoryPojo make=new CategoryPojo();
                make.setCategoryId(categoryJson.getInt("CategoryId"));
                make.setCategory(categoryJson.getString("Category"));
                make.setDescription(categoryJson.getString("Description"));

                listData.add(make);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        CategoryAdapter adapter=new CategoryAdapter(this,listData);

        ListView main_page=(ListView)findViewById(R.id.cat_list);
        main_page.setAdapter(adapter);

        main_page.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent =new Intent(CategoryActivity.this,Login.class);

                startActivity(intent);

            }
        });
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
