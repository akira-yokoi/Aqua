package org.amd.aqua;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.amd.aqua.fragment.FragmentActionListener;
import org.amd.aqua.fragment.HomeFragment;
import org.amd.aqua.fragment.SupportListDetailFragment;
import org.amd.aqua.fragment.SupportListFragment;
import org.amd.aqua.model.SupportItem;
import org.amd.aqua.util.ViewUtil;

import static org.amd.aqua.R.id.navigation;
import static org.amd.aqua.model.SupportContentManager.MODE_ALL;
import static org.amd.aqua.model.SupportContentManager.MODE_REQUEST;
import static org.amd.aqua.model.SupportContentManager.MODE_SUPPORT;
import static org.amd.aqua.model.SupportContentManager.STATUS_ALL;
import static org.amd.aqua.model.SupportContentManager.STATUS_SUPPORTING;
import static org.amd.aqua.model.SupportContentManager.STATUS_LOOKING;
import static org.amd.aqua.model.SupportContentManager.STATUS_COMPLETE;

public class MenuActivity extends AppCompatActivity implements FragmentActionListener, SupportListDetailFragment.Listener {

    private BottomNavigationView bottomNavigationView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        goHome();

        // Floatingボタン
        FloatingActionButton newSupportRequestButton = (FloatingActionButton) findViewById(R.id.newSupportRequest);
        newSupportRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newSupportItemIntent = new Intent(MenuActivity.this, SupportItemEditAcitivity.class);
                startActivity(newSupportItemIntent);
            }
        });

        // ButtomNavigation
        bottomNavigationView = (BottomNavigationView) findViewById(navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SupportListFragment fragment = (SupportListFragment) fragmentManager.getFragments().get(0);

        int id = item.getItemId();
        switch (id) {
            case R.id.filter_all:
                fragment.setMode(MODE_ALL);
                break;
            case R.id.filter_support:
                fragment.setMode(MODE_SUPPORT);
                break;
            case R.id.filter_request:
                fragment.setMode(MODE_REQUEST);
                break;
        }
        fragment.filter();
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    goHome();
                    return true;
                case R.id.navigation_list:
                    goList(MODE_ALL, STATUS_ALL);
//                    Intent mapsIntent = new Intent(MenuActivity.this, MapsActivity.class);
//                    startActivity(mapsIntent);
                    return true;
                case R.id.navigation_notifications:
                    ViewUtil.showShortToast(MenuActivity.this, "Notifications");
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onAction(Fragment fragment, int actionId) {
        if (fragment instanceof HomeFragment) {
            if (actionId == R.id.supporterlookingButton) {
                goList(MODE_SUPPORT, STATUS_LOOKING);
            }
            if (actionId == R.id.supporterSupportingButton) {
                goList(MODE_SUPPORT, STATUS_SUPPORTING);
            }
            if (actionId == R.id.supporterCompletedButton) {
                goList(MODE_SUPPORT, STATUS_COMPLETE);
            }

            if (actionId == R.id.requestantLookingButton) {
                goList(MODE_REQUEST, STATUS_LOOKING);
            }
            if (actionId == R.id.requestantSupportingButton) {
                goList(MODE_REQUEST, STATUS_SUPPORTING);
            }
            if (actionId == R.id.requestantCompletedButton) {
                goList(MODE_REQUEST, STATUS_COMPLETE);
            }
        }
    }

    @Override
    public void onSelect(Fragment fragment, Object item) {
        if (fragment instanceof SupportListFragment) {
            final SupportListDetailFragment myBottomSheet = SupportListDetailFragment.newInstance((SupportItem) item);
            myBottomSheet.show(getSupportFragmentManager(), myBottomSheet.getTag());
        }
    }

    private void setTitleBar(int titleResourceId) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(titleResourceId);
    }

    private void goHome() {
        setTitleBar(R.string.title_home);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HomeFragment fragemnt = HomeFragment.newInstance("first", "second");
        fragmentTransaction.replace(R.id.content, fragemnt);
        fragmentTransaction.commit();
    }

    private void goList(int mode, int status) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        SupportListFragment fragemnt = SupportListFragment.newInstance(mode, status);
        fragmentTransaction.replace(R.id.content, fragemnt);
        fragmentTransaction.commit();

        // イベントを発生させずに選択状態にする
        bottomNavigationView.getMenu().findItem(R.id.navigation_list).setChecked(true);
    }

    @Override
    public void doSupport() {

    }
}
