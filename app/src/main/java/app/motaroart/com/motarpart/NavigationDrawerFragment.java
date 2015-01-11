package app.motaroart.com.motarpart;


import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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


    private ActionBarDrawerToggle mDrawerToggle;

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

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.

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
        // Indicate that this fragment would like to influence the set of actions in the action bar.
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
    //    new DownloadData().execute();



        return rootView;




    }




/// init the spinner of make model and category

    class DownloadData extends AsyncTask<Void,Void,String[]>
    {

        ProgressDialog pd;
        @Override
        protected String[] doInBackground(Void... voids) {
            String[] data=new String[5];
            if(!mPrefs.getString("make","").equals(""))
                data[0]=mPrefs.getString("make","");
            else
            {
                if(InternetState.getState(rootView.getContext())) {
                  Object obj = WebServiceCall.getMakeJson();
                    if(obj instanceof Object)
                    data[0]=String.valueOf(obj);
                    mPrefs.edit().putString("make", data[0]).apply();
                }
            }

            if(!mPrefs.getString("model","[]").equals("[]") )
                data[1]=mPrefs.getString("model","");
            else
            {
                if(InternetState.getState(rootView.getContext())) {
               //     data[1]= WebServiceCall.getModelJson();
                    mPrefs.edit().putString("model", data[1]).apply();
                }
            }
            if(!mPrefs.getString("category","").equals(""))
                data[2]=mPrefs.getString("category","");
            else
            {
                if(InternetState.getState(rootView.getContext())) {
                    data[2]= WebServiceCall.getCategoryJson();
                    mPrefs.edit().putString("category", data[2]).apply();
                }
            }
            return data;
        }

        @Override
        protected void onPreExecute() {
            pd=ProgressDialog.show(rootView.getContext(),rootView.getResources().getString(R.string.app_name),"Loading..",true,false);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String[] s) {

            if(s[0]!=null&&s[1]!=null&&s[2]!=null) {
                Gson gson = new Gson();
                Type listOfTestObject = new TypeToken<List<Make>>() {
                }.getType();
                listData = gson.fromJson(s[0], listOfTestObject);
                MakeAdapterSpinner makeAdapter = new MakeAdapterSpinner(getActivity(), listData);
                sMake.setAdapter(makeAdapter);

                Type listModeltObject = new TypeToken<List<Model>>() {
                }.getType();
                listDataModel = gson.fromJson(s[1], listModeltObject);
                ModelAdapterSpinner ModelAdapter = new ModelAdapterSpinner(getActivity(), listDataModel);
                sModel.setAdapter(ModelAdapter);

                Type listCatObject = new TypeToken<List<CategoryPojo>>() {
                }.getType();
                listDataCat = gson.fromJson(s[2], listCatObject);
                CategoryAdapterSpinner catAdapter = new CategoryAdapterSpinner(getActivity(), listDataCat);
                category.setAdapter(catAdapter);
                pd.dismiss();
                super.onPostExecute(s);
            }
            else
            {
                pd.dismiss();
                new AlertDialog.Builder(getActivity())
                        .setTitle(getActivity().getResources().getString(R.string.app_name))
                        .setMessage("Sorry No offline Data available!")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
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

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return getActivity().getActionBar();
    }

    @Override
    public void onClick(View view) {

        Intent i = new Intent(getActivity(), ProductActivity.class);
        SharedPreferences.Editor edit=sp.edit();
        edit.putString("modelID", (listDataModel.get(sModel.getSelectedItemPosition()).getModelId()));
        edit.putString("catID", (listDataCat.get(category.getSelectedItemPosition()).getCategoryId()));
        edit.apply();
        startActivity(i);


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
