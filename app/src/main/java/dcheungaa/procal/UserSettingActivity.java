package dcheungaa.procal;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;


public class UserSettingActivity extends AppCompatPreferenceActivity {

    public UserSettingActivity() {}

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
                this.finish();
                return (true);
        }
        return super.onOptionsItemSelected(item);
    }

    public static class LocationFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences_main);

        }
    }
}
