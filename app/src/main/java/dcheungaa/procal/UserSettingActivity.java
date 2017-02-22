package dcheungaa.procal;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;


public class UserSettingActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new LocationFragment()).commit();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ((DrawerLayout) MainActivity.views.get("drawer")).closeDrawer(Gravity.LEFT);
                this.finish();
                return (true);
        }
        return super.onOptionsItemSelected(item);
    }
    public static class LocationFragment extends PreferenceFragment {

        private LayoutInflater inflater;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //addPreferencesFromResource(R.xml.preferences_main);
        }

    }
}