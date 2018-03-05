package meb.sdm.entrega;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import static java.lang.Integer.parseInt;

public class SettingsActivity extends AppCompatActivity {
    EditText name, nameFriend;
    Spinner numhelps;
    private static final String NUMBER_HELPS = "numberHelps";
    private static final String USER_NAME = "userName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        name = findViewById(R.id.name_text);
        numhelps = findViewById(R.id.numHelps);
        nameFriend = findViewById(R.id.nameFriend);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        name.setText(sharedPref.getString(USER_NAME, ""));
        numhelps.setSelection(sharedPref.getInt(NUMBER_HELPS, 0));
    }

    @Override
    protected void onPause(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        Toast.makeText(this, name.getText().toString(), Toast.LENGTH_LONG).show();
        editor.putString(USER_NAME, name.getText().toString());
        if(numhelps.getSelectedItem() != null) editor.putInt(NUMBER_HELPS, parseInt(numhelps.getSelectedItem().toString()));
        editor.commit();
        super.onPause();
    }
}
