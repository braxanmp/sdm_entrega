package meb.sdm.entrega;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.item_info).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu){
        switch (menu.getItemId()) {
            case R.id.item_info:
                Intent intent = new Intent(this, CreditsActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    public void buttonPushed(View view) {
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
