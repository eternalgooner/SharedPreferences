package david.com.savedpreferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
    private GridLayout gl;
    TextView column_1;
    TextView column_2;
    TextView column_3;
    TextView column_4;
    TextView column_5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gl = (GridLayout) findViewById(R.id.gl);
        column_1 = (TextView) findViewById(R.id.column_1);
        column_2 = (TextView) findViewById(R.id.column_2);
        column_3 = (TextView) findViewById(R.id.column_3);
        column_4 = (TextView) findViewById(R.id.column_4);
        column_5 = (TextView) findViewById(R.id.column_5);
        setupSharedPreferences();
    }

    private void setupSharedPreferences() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String storeColour = sp.getString(getString(R.string.pref_colour_key), "#FFFFFF");
        boolean pref_working = sp.getBoolean("pref_working_key", true);

        gl.setBackgroundColor(Color.parseColor(storeColour));

        if(pref_working){
            column_1.setText(pref_working + "");
        }else{
            column_1.setText(!pref_working + "");
        }

        sp.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_preferences, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItem = item.getItemId();
        if(menuItem == R.id.menu_pref){
            showPreferencesScreen();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPreferencesScreen() {
        Context context = MainActivity.this;
        Intent intent = new Intent(context, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(getString(R.string.pref_colour_key))){
            String storeColour = sharedPreferences.getString(key, "#FFFFFF");
            gl.setBackgroundColor(Color.parseColor(storeColour));
        }else if(key.equals(R.string.pref_working_key)){
            column_1.setText(sharedPreferences.getBoolean(key, true) +"");
        }
    }
}
