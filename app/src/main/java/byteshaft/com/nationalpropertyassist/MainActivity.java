package byteshaft.com.nationalpropertyassist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import byteshaft.com.nationalpropertyassist.account.CodeConfirmationActivity;
import byteshaft.com.nationalpropertyassist.account.LoginActivity;
import byteshaft.com.nationalpropertyassist.activities.AssistMain;
import byteshaft.com.nationalpropertyassist.fragments.Help;
import byteshaft.com.nationalpropertyassist.fragments.JobHistory;
import byteshaft.com.nationalpropertyassist.fragments.PaymentDetails;
import byteshaft.com.nationalpropertyassist.fragments.PropertyDetails;
import byteshaft.com.nationalpropertyassist.fragments.Settings;
import byteshaft.com.nationalpropertyassist.utils.Helpers;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static MainActivity sInstance;

    public static MainActivity getInstance() {
        return sInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sInstance = this;
        if (!Helpers.isUserLoggedIn()) {
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        } else if (!Helpers.isUserActive()) {
            startActivity(new Intent(getApplicationContext(), CodeConfirmationActivity.class));
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        loadFragment(new AssistMain());
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
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_property_details) {
            loadFragment(new PropertyDetails());

        } else if (id == R.id.nav_job_history) {
            loadFragment(new JobHistory());

        } else if (id == R.id.nav_payment_details) {
            loadFragment(new PaymentDetails());

        } else if (id == R.id.nav_settings) {
            loadFragment(new Settings());

        } else if (id == R.id.nav_help) {
            loadFragment(new Help());

        } else if (id == R.id.nav_logout) {

        } else if (id == R.id.nav_np_assist) {
            loadFragment(new AssistMain());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.container, fragment);
        tx.commit();
    }
}
