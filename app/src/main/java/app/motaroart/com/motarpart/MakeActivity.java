package app.motaroart.com.motarpart;

import android.app.Activity;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.motaroart.com.motarpart.adapter.MakeAdapter;
import app.motaroart.com.motarpart.adapter.PartAdapter;
import app.motaroart.com.motarpart.pojo.Make;
import app.motaroart.com.motarpart.pojo.Part;


public class MakeActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks , SearchView.OnQueryTextListener{
    private SearchView mSearchView;
    private TextView mStatusView;
   String JsonStr="[{\"MakeId\":1,\"MakeName\":\"AUDI\",\"Description\":\"\",\"IsActive\":true,\"CreatedOn\":\"2014-11-23T16:20:45.353\"},{\"MakeId\":7,\"MakeName\":\"HONDA\",\"Description\":null,\"IsActive\":true,\"CreatedOn\":\"2014-11-23T16:21:23.07\"},{\"MakeId\":2,\"MakeName\":\"HYUNDAI\",\"Description\":null,\"IsActive\":true,\"CreatedOn\":\"2014-11-23T16:20:47.713\"},{\"MakeId\":3,\"MakeName\":\"KIA\",\"Description\":null,\"IsActive\":true,\"CreatedOn\":\"2014-11-23T16:20:55.903\"},{\"MakeId\":10,\"MakeName\":\"MAZDA\",\"Description\":null,\"IsActive\":true,\"CreatedOn\":\"2014-11-23T16:21:32.173\"},{\"MakeId\":4,\"MakeName\":\"MITSUBISHI\",\"Description\":null,\"IsActive\":false,\"CreatedOn\":\"2014-11-23T16:21:00.257\"},{\"MakeId\":11,\"MakeName\":\"New Make\",\"Description\":\"1\",\"IsActive\":true,\"CreatedOn\":\"2014-12-01T16:27:38.233\"},{\"MakeId\":8,\"MakeName\":\"NISSAN\",\"Description\":null,\"IsActive\":true,\"CreatedOn\":\"2014-11-23T16:21:27.847\"},{\"MakeId\":6,\"MakeName\":\"SUBARU\",\"Description\":null,\"IsActive\":true,\"CreatedOn\":\"2014-11-23T16:21:14.32\"},{\"MakeId\":9,\"MakeName\":\"SUZUKI\",\"Description\":null,\"IsActive\":true,\"CreatedOn\":\"2014-11-23T16:21:31.647\"},{\"MakeId\":5,\"MakeName\":\"TOYOTA\",\"Description\":null,\"IsActive\":true,\"CreatedOn\":\"2014-11-23T16:21:13.787\"}]";
    private NavigationDrawerFragment mNavigationDrawerFragment;

    MakeAdapter adapter;
    private CharSequence mTitle;
    List<Make> listData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        //   mStatusView = (TextView) findViewById(R.id.status_text);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));



       listData=new ArrayList<Make>();
        try {
            JSONArray makeArray = new JSONArray(JsonStr);
            for(int i=0;i<makeArray.length();i++) {

                JSONObject makeJson = makeArray.getJSONObject(i);
                Make make=new Make();
                make.setMakeId(makeJson.getInt("MakeId"));
                make.setMakeName(makeJson.getString("MakeName"));
                make.setDescription(makeJson.getString("Description"));

                listData.add(make);




            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        adapter=new MakeAdapter(this,listData);

        ListView main_page=(ListView)findViewById(R.id.main_page_list);
        main_page.setAdapter(adapter);

        main_page.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent =new Intent(MakeActivity.this,ModelActivity.class);
                intent.putExtra("MakeID",listData.get(i).getMakeId());
                startActivity(intent);

            }
        });
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

    }



    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView(searchItem);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





    private void setupSearchView(MenuItem searchItem) {


        mSearchView.setOnQueryTextListener(this);
    }

    public boolean onQueryTextChange(String newText) {

      /*  adapter.filterState(newText);
        System.out.println(newText);*/

        return false;
    }

    public boolean onQueryTextSubmit(String query) {
           return false;
    }

    public boolean onClose() {
        mStatusView.setText("Closed!");
        return false;
    }

    protected boolean isAlwaysExpanded() {
        return false;
    }















}
