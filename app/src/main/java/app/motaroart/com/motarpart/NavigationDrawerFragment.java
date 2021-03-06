package app.motaroart.com.motarpart;



import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.motaroart.com.motarpart.adapter.CategoryAdapterSpinner;
import app.motaroart.com.motarpart.adapter.MakeAdapterSpinner;
import app.motaroart.com.motarpart.adapter.ModelAdapterSpinner;
import app.motaroart.com.motarpart.pojo.CategoryPojo;
import app.motaroart.com.motarpart.pojo.Make;
import app.motaroart.com.motarpart.pojo.Model;
import app.motaroart.com.motarpart.services.InternetState;
import app.motaroart.com.motarpart.services.WebServiceCall;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment implements View.OnClickListener {

    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    private NavigationDrawerCallbacks mCallbacks;


    public ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    SharedPreferences sp;
    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
    }

    Spinner sMake, sModel, category;
    View rootView;
    SharedPreferences mPrefs ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_drawer, container, false);
        mPrefs = rootView.getContext().getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        sMake = (Spinner) rootView.findViewById(R.id.s_make);
        sModel = (Spinner) rootView.findViewById(R.id.s_model);
        category = (Spinner) rootView.findViewById(R.id.s_sub);
        Button b = (Button) rootView.findViewById(R.id.main_seach_drawer);
        b.setOnClickListener(this);
        if(InternetState.getState(getActivity()))
        new DownloadData().execute();
        else
        Toast.makeText(getActivity(),"Connection has lost",Toast.LENGTH_LONG).show();

        sMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(listData.get(i).getMakeId()!=-1)
                {
                    if(InternetState.getState(getActivity())) {
                        new DownloadDataCar().execute();
                    }else
                        Toast.makeText(getActivity(), "Connection has lost", Toast.LENGTH_LONG).show();


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return rootView;




    }



    class DownloadData extends AsyncTask<Void,Void,String>
    {

        ProgressDialog pd;
        @Override
        protected String doInBackground(Void... voids) {
            mPrefs.edit().putString("Setting", WebServiceCall.getSetting()).apply();

            return WebServiceCall.getMakeJson();
        }

        @Override
        protected void onPreExecute() {
            pd=ProgressDialog.show(rootView.getContext(),rootView.getResources().getString(R.string.app_name),"Loading..",true,false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            if(s!=null) {
                Gson gson = new Gson();
                Type listOfTestObject = new TypeToken<List<Make>>() {
                }.getType();
                Make make=new Make();
                make.setMakeId(-1);
                make.setMakeName("Make");
                listData = gson.fromJson(s, listOfTestObject);
                listData.add(0,make);
                MakeAdapterSpinner makeAdapter = new MakeAdapterSpinner(getActivity(), listData);
                sMake.setAdapter(makeAdapter);

                Model model=new Model();
                model.setModelName("Model");
                model.setModelId("-1");
                listDataModel = new ArrayList<>();
                listDataModel.add(0,model);
                ModelAdapterSpinner ModelAdapter = new ModelAdapterSpinner(getActivity(), listDataModel);
                sModel.setAdapter(ModelAdapter);
                ModelAdapter.notifyDataSetChanged();

                CategoryPojo cat=new CategoryPojo();
                cat.setCategory("Category");
                cat.setCategoryId("-1");
                listDataCat =new ArrayList<>();
                listDataCat.add(0,cat);
                CategoryAdapterSpinner catAdapter = new CategoryAdapterSpinner(getActivity(), listDataCat);

                category.setAdapter(catAdapter);
                catAdapter.notifyDataSetChanged();
                pd.dismiss();
                super.onPostExecute(s);
            }

                pd.dismiss();


        }
    }

    class DownloadDataCar extends AsyncTask<Void,Void,List<String>>
    {
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
           pd=ProgressDialog.show(getActivity(),getString(R.string.app_name),"Loading...",true,false);
            super.onPreExecute();
        }

        @Override
        protected List<String> doInBackground(Void... voids) {

          List<String> str=new ArrayList<>();
            str.add(WebServiceCall.getModelJson(listData.get(sMake.getSelectedItemPosition()).getMakeId()+""));
            str.add(WebServiceCall.getCategoryJson());
            return  str;
        }

        @Override
        protected void onPostExecute(List<String> strings) {

            if(strings!=null) {
                Gson gson = new Gson();

                Type listModeltObject = new TypeToken<List<Model>>() {
                }.getType();
                Model model=new Model();
                        model.setModelName("Model");
                model.setModelId("-1");
                listDataModel = gson.fromJson(strings.get(0), listModeltObject);
                listDataModel.add(0,model);
                ModelAdapterSpinner ModelAdapter = new ModelAdapterSpinner(getActivity(), listDataModel);
                sModel.setAdapter(ModelAdapter);

                Type listCatObject = new TypeToken<List<CategoryPojo>>() {
                }.getType();

                CategoryPojo cat=new CategoryPojo();
                cat.setCategory("Category");
                cat.setCategoryId("-1");
                listDataCat = gson.fromJson(strings.get(1), listCatObject);
                listDataCat.add(0,cat);
                CategoryAdapterSpinner catAdapter = new CategoryAdapterSpinner(getActivity(), listDataCat);
                category.setAdapter(catAdapter);
            }else
            {
                Toast.makeText(getActivity(),"Connection Problem.",Toast.LENGTH_LONG).show();
            }

           pd.dismiss();
            super.onPostExecute(strings);
        }
    }
    List<CategoryPojo>listDataCat;
    List<Model> listDataModel;
    List<Make>listData;


    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar =  ((ActionBarActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
             //   R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity)getActivity()).getSupportActionBar();
    }

    @Override
    public void onClick(View view) {

        if(listDataModel!=null && listDataCat!=null) {
            if (!listDataModel.get(sModel.getSelectedItemPosition()).getModelId().equals("-1") && !listDataCat.get(category.getSelectedItemPosition()).getCategoryId().equals("-1")) {
                Intent i = new Intent(getActivity(), ProductActivity.class);
                SharedPreferences.Editor edit = mPrefs.edit();
                edit.putString("modelID", (listDataModel.get(sModel.getSelectedItemPosition()).getModelId()));
                edit.putString("catID", (listDataCat.get(category.getSelectedItemPosition()).getCategoryId()));
                if (edit.commit())
                    startActivity(i);
            }
        }



    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }
}
