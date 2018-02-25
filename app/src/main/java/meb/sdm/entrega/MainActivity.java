package meb.sdm.entrega;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonPushed(View view) {
        Log.d("push", "some button pushed");
        if(view.equals(findViewById(R.id.image_gamepad)) || view.equals(findViewById(R.id.label_gamepad)) ){
            Intent intent = new Intent(this, PlayActivity.class);
            startActivity(intent);

        }else if(view.equals(findViewById(R.id.image_score)) || view.equals(findViewById(R.id.label_score))){
            Intent intent = new Intent(this, ScoreActivity.class);
            startActivity(intent);
        }
        else if(view.equals(findViewById(R.id.image_settings)) || view.equals(findViewById(R.id.label_settings))){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

    }


}
