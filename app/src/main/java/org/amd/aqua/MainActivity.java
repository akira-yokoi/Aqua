package org.amd.aqua;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import org.amd.aqua.rest.WeatherApi;
import org.amd.aqua.rest.WeatherResponse;
import org.amd.aqua.util.ViewUtil;

import java.util.Date;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.converter.GsonConverter;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static org.amd.aqua.R.id.navigation;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Floatingボタン
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);fab.setBackgroundColor( getResources().getColor( R.color.colorPrimary));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                // アイコンのカスタマイズ
//                Menu menu = navigationView.getMenu();
//                MenuItem navCamera = (MenuItem) menu.findItem(R.id.nav_camera);
//                Context context = MainActivity.this;
//                FontDrawable drawable = new FontDrawable.Builder(context, (char) 0xF286, FontUtil.getTypefaceFromAssets(context, FontUtil.FONT_AWESOME)).build();
//                navCamera.setIcon(drawable);
//            }
//        });
        navigationView.setNavigationItemSelectedListener(this);

        // ButtomNavigation
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void callRest(View view) {
        ViewUtil.showShortToast(this, "callRest");

        // JSONのパーサー
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        // RestAdapterの生成
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("http://api.openweathermap.org")
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog("=NETWORK="))
                .build();

        // 非同期処理の実行
        adapter.create(WeatherApi.class).get("weather", "Tokyo,jp")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MainActivity", "onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("MainActivity", "Error : " + e.toString());
                    }

                    @Override
                    public void onNext(WeatherResponse response) {
                        Log.d("MainActivity", "onNext()");
                        if (response != null) {
                            ((TextView) findViewById(R.id.text)).setText(response.weather.get(0).main);
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.filter_all) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            ViewUtil.showLongToast(this, "nav_camera");
        } else if (id == R.id.nav_gallery) {
            ViewUtil.showLongToast(this, "nav_gallery");
        } else if (id == R.id.nav_slideshow) {
            ViewUtil.showLongToast(this, "nav_slideshow");
        } else if (id == R.id.nav_manage) {
            ViewUtil.showLongToast(this, "nav_manage");
        } else if (id == R.id.nav_share) {
            ViewUtil.showLongToast(this, "nav_share");
        } else if (id == R.id.nav_send) {
            ViewUtil.showLongToast(this, "nav_send");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ViewUtil.showShortToast(MainActivity.this, "Home");
                    return true;
                case R.id.navigation_list:
                    ViewUtil.showShortToast(MainActivity.this, "Dashboard");
                    Intent mapsIntent = new Intent( MainActivity.this, MapsActivity.class);
                    startActivity( mapsIntent);
                    return true;
                case R.id.navigation_notifications:
                    ViewUtil.showShortToast(MainActivity.this, "Notifications");
                    return true;
            }
            return false;
        }
    };
}
