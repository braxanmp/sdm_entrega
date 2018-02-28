package meb.sdm.entrega;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import static java.lang.Integer.parseInt;

public class SettingsActivity extends AppCompatActivity {
    EditText name, numhelps, nameFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        name = findViewById(R.id.name);
        numhelps = findViewById(R.id.numHelps);
        nameFriend = findViewById(R.id.nameFriend);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        name.setText(sharedPref.getString("userName", ""));
        numhelps.setText("" + sharedPref.getInt("numberHelps", 0));
    }

    @Override
    protected void onPause(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();

        Toast.makeText(this, name.getText().toString(), Toast.LENGTH_LONG).show();
        editor.putString("userName", name.getText().toString());
        if(numhelps.getText().length() != 0) editor.putInt("numberHelps", parseInt(numhelps.getText().toString()));
        editor.commit();

        super.onPause();
    }
}
