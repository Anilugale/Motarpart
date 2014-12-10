package app.motaroart.com.motarpart;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.motaroart.com.motarpart.pojo.CategoryPojo;


public class Category extends Activity  implements NavigationDrawerFragment.NavigationDrawerCallbacks , SearchView.OnQueryTextListener{
    List<CategoryPojo> listData;
    private SearchView mSearchView;
    private TextView mStatusView;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        //   mStatusView = (TextView) findViewById(R.id.status_text);
        // Set up the drawer.
      mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
     /*    FragmentManager fragmentManager = getFragmentManager();
         fragmentManager.beginTransaction()
                .replace(R.id.container,new MainActivity.PlaceholderFragment())
                .commit();*/

        final List<CategoryPojo> listData=new ArrayList<CategoryPojo>();

       /* CategoryPojo t1=new CategoryPojo();
        t1.setName("ENGINE");
        t1.setQty(120);
        listData.add(t1);

        CategoryPojo t2=new CategoryPojo();
        t2.setName("BRAKES");
        t2.setQty(140);
        listData.add(t2);

        CategoryPojo t3=new CategoryPojo();
        t3.setName("SUSPENSION & STEERING");
        t3.setQty(140);
        listData.add(t3);

        CategoryPojo t4=new CategoryPojo();
        t4.setName("CLUTCH & TRANSMISSION");
        t4.setQty(140);
        listData.add(t4);

        CategoryPojo t5=new CategoryPojo();
        t5.setName("COOLING & HEATING");
        t5.setQty(140);
        listData.add(t5);

        CategoryPojo t6=new CategoryPojo();
        t6.setName("ELECTRICAL & LIGHTING");
        t6.setQty(140);
        listData.add(t6);

        CategoryPojo t7=new CategoryPojo();
        t7.setName("BODY & EXHAUST PARTS");
        t7.setQty(140);
        listData.add(t7);

        CategoryPojo t8=new CategoryPojo();
        t8.setName("LUBRICANTS");
        t8.setQty(140);
        listData.add(t8);


        CategoryListAdapter adapter=new CategoryListAdapter(this,listData);
        ListView  main_page=(ListView)findViewById(R.id.category_list);
        main_page.setAdapter(adapter);

        main_page.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(Category.this,MakeActivity.class);
                intent.putExtra("category",listData.get(i).getName());
                startActivity(intent);
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        if (id == R.id.action_user) {
            startActivity(new Intent(this,Login.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
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







    private void setupSearchView(MenuItem searchItem) {

        if (isAlwaysExpanded()) {
            mSearchView.setIconifiedByDefault(false);
        } else {
            searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
                    | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        }

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();

            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
            for (SearchableInfo inf : searchables) {
                if (inf.getSuggestAuthority() != null
                        && inf.getSuggestAuthority().startsWith("applications")) {
                    info = inf;
                }
            }
            mSearchView.setSearchableInfo(info);
        }

        mSearchView.setOnQueryTextListener(this);
    }

    public boolean onQueryTextChange(String newText) {
        System.out.println(newText);
      /*  mStatusView.setText("Query = " + newText);*/
        return false;
    }

    public boolean onQueryTextSubmit(String query) {
      /*  mStatusView.setText("Query = " + query + " : submitted");*/
        System.out.println(query
        );
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
