package meb.sdm.entrega;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.play_menu, menu);
        menu.findItem(R.id.item_fifty).setVisible(true);
        menu.findItem(R.id.item_phone).setVisible(true);
        menu.findItem(R.id.item_public).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu){
        switch (menu.getItemId()) {
            case R.id.item_fifty:
                Toast.makeText(this, "Fifty selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_phone:
                Toast.makeText(this, "Phone selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_public:
                Toast.makeText(this, "Public selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }
}
