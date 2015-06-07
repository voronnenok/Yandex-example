package com.example.yandexmap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.yandexmap.entity.Task;

public class MainActivity extends ActionBarActivity implements TaskClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openMapScreen();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    }


    @Override
    public void onTaskClick(Task task) {
        openInfoScreen(task);
    }

    private void openMapScreen() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(MapFragment.TAG);
        if(fragment == null) {
            fragment = MapFragment.newInstance();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment, MapFragment.TAG)
                    .commit();
        }
    }

    private void openInfoScreen(Task task) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(InfoFragment.TAG);
        if(fragment == null) {
            fragment = InfoFragment.newInstance(task);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment, InfoFragment.TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }

}
