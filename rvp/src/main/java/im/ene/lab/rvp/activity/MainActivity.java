package im.ene.lab.rvp.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import im.ene.lab.rvp.R;
import im.ene.lab.rvp.fragment.CheeseListFragment;
import im.ene.lab.rvp.fragment.SimpleListFragment;

public class MainActivity extends AppCompatActivity {

  Fragment mFragment;

  private int mCounter = 0; // keep track of current fragment

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mFragment = getSupportFragmentManager().findFragmentById(R.id.container);
    if (mFragment == null) {
      setupFragment();
    }
  }

  private void setupFragment() {
    if (mCounter % 2 == 0) {
      mFragment = SimpleListFragment.newInstance();
    } else {
      mFragment = CheeseListFragment.newInstance();
    }
    mCounter++;
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.container, mFragment)
        .commit();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_global, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Switch between Lists type:
    // SimpleListFragment with a simple list of Strings,
    // CheeseListFragment with a more complicated list;
    switch (item.getItemId()) {
      case R.id.action_settings:
        setupFragment();
        return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
