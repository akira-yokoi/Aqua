package org.amd.aqua;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.amd.aqua.util.ViewUtil;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
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

        try {
            AsyncHttpRequest request = new AsyncHttpRequest(this);
            request.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public class AsyncHttpRequest extends AsyncTask<Uri.Builder, Void, String> {

        private Activity mainActivity;

        public AsyncHttpRequest(Activity activity) {

            // 呼び出し元のアクティビティ
            this.mainActivity = activity;
        }

        // このメソッドは必ずオーバーライドする必要があるよ
        // ここが非同期で処理される部分みたいたぶん。
        @Override
        protected String doInBackground(Uri.Builder... builder) {
            Request request = new Request.Builder()
//                    .url("http://www.asahi.com")
                    .url("http://hackathon.support-cloud-projects.com/LaundromatWebApi/api/memberinfo?ANKOWNERID=46228846")
                    .addHeader("Authorization", "Bearer Ad6xhPJRT4ZZwuQBIDGLsgnL8tI3ivPIQdp3Dfj_R3BFyppz8D9dUTDXScKTFLR0Bvt8hRPTSOtflPMowamQEgb1JjM14XhlJJnyIpTOqpsbP7-IjjYrutsGXnnoAA7ry_W95-Ior06hGKG1d2pvrpQrkU-1MVloxC5zXlHNEI_oyytddCZ3GUOw1eOwJnWi2tP_fi0JkQ7yfC0o9t9Xh5YXAlNnVvmeX1mbFJmOnw-D2sYM0TQz2lBR8rnrUOhYNcHlEgkA2kMYBg87MzRhCCvNtHKHP2j20j2gZJWAA9pdIYmmEmULe9oF1Y5zZHiHY_6O_hPjhM_xiWKoxCQu990ZuW0Il4SMsmxPlIRMhkiAhn3kdUELKdmHx2cY3a52Aftv4BGI6omzI9RBoJiQ9SPpEApRTFw5xnKiSINwVQfPVHUZItr2YZ4Ckg3p1dJpq_Au8Uq4P_9lUpfF6atTFFUlrKQuWHGpWMflefSoroxkHGYSgkY4ptBqzPBnEvA4")
                    .get()
                    .build();

            OkHttpClient client = new OkHttpClient();

            try {
                Response response = client.newCall(request).execute();
                ResponseBody body = response.body();
                String strBody = body.string();
                System.err.println( strBody);
                return strBody;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }


        // このメソッドは非同期処理の終わった後に呼び出されます
        @Override
        protected void onPostExecute(String result) {
            System.err.println();
        }
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
                    Intent mapsIntent = new Intent(MainActivity.this, MapsActivity.class);
                    startActivity(mapsIntent);
                    return true;
                case R.id.navigation_notifications:
                    ViewUtil.showShortToast(MainActivity.this, "Notifications");
                    return true;
            }
            return false;
        }
    };
}
