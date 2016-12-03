package co.edu.eafit.parkingmobile;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.edu.eafit.parkingmobile.models.ModelManager;
import co.edu.eafit.parkingmobile.models.Parking;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private Adapter adapter = new Adapter(getSupportFragmentManager());
    private DrawerLayout drawerLayout;
    private ListFragment listFragment = new ListFragment();
    private MapFragment mapFragment = new MapFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter.addFragment(listFragment, "Lista");
        adapter.addFragment(mapFragment, "Mapa");

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        }  );
    }

    @Override
    protected void onResume() {
        super.onResume();
        getParkings();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    public static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title){
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }

    public void getParkings(){
        JsonArrayRequest request = new JsonArrayRequest("https://baas.kinvey.com/appdata/kid_SyrrJECzx/carpark",
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        ModelManager.getInstance().parkings.clear();

                        for (int i=0; i < response.length(); i++){
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Parking parkingTmp = new Parking();
                                parkingTmp.setId(obj.getString("_id"));
                                parkingTmp.setName(obj.getString("name"));
                                parkingTmp.setDetails(obj.getString("details"));
                                parkingTmp.setFieldsAvailable(obj.getString("fields_available"));
                                parkingTmp.setPrice(obj.getString("price"));
                                parkingTmp.setLatitude(obj.getDouble("latitude"));
                                parkingTmp.setLongitude(obj.getDouble("longitude"));
                                parkingTmp.setPicture(obj.getString("picture"));
                                ModelManager.getInstance().parkings.add(parkingTmp);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        listFragment.adapter.notifyDataSetChanged();
                    }
                },
        new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Basic a2lkX1N5cnJKRUN6eDplNWFmOWVhYTgwNDQ0YWFjODhkNGJlN2U1NzA4MGE0Mw==");
                headers.put("Content-Type", "multipart/form-data");
                headers.put("X-Kinvey-API-Version", "3");
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(request, "tag_json_arry");
    }
}
