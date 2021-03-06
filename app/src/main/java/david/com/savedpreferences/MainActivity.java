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
    //TextView column_4;
    //TextView column_5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gl = (GridLayout) findViewById(R.id.gl);
        column_1 = (TextView) findViewById(R.id.column_1);
        column_2 = (TextView) findViewById(R.id.column_2);
        column_3 = (TextView) findViewById(R.id.column_3);
        //column_4 = (TextView) findViewById(R.id.column_4);
        //column_5 = (TextView) findViewById(R.id.column_5);
        setupSharedPreferences();
    }

    private void setupSharedPreferences() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String storeColour = sp.getString(getString(R.string.pref_colour_key), "#FFFFFF");
        boolean pref_working = sp.getBoolean("pref_working_key", false);
        boolean autoSaveOn = sp.getBoolean("auto_save_key", false);

        gl.setBackgroundColor(Color.parseColor(storeColour));

        if(pref_working){
            column_1.setText(R.string.pref_on);
        }else{
            column_1.setText(R.string.pref_off);
        }

        if(autoSaveOn){
            column_2.setText("Enabled");
        }else{
            column_2.setText("Disabled");
        }

        column_3.setText(storeColour);

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
            column_3.setText(storeColour);
        }else if(key.equals("pref_working_key")){
            boolean prefIsWorking = sharedPreferences.getBoolean(key, false);
            if(prefIsWorking){
                column_1.setText(R.string.pref_on);
            }else{
                column_1.setText(R.string.pref_off);
            }
        }else if(key.equals("auto_save_key")) {
            boolean autoSaveOn = sharedPreferences.getBoolean(key, false);
            if (autoSaveOn) {
                column_2.setText(R.string.auto_save_on);
            } else {
                column_2.setText(R.string.auto_save_off);
            }
        }
    }
}
