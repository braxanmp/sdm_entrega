package meb.sdm.entrega;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;

import meb.sdm.entrega.adapters.ScoreAdapter;

public class ScoreActivity extends AppCompatActivity {
    private ListView lv_local;
    private ListView lv_friends;
    private ScoreAdapter adapter;
    private static TabHost scoresHost;
    private static final String TAB_LOCAL_SCORES = "tabLocalScores";
    private static final String TAB_FRIENDS_SCORES = "tabFriendsScores";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        scoresHost = (TabHost) findViewById(R.id.my_tab_host);
        scoresHost.setup();
        /*TabHost.TabSpec spec = host.newTabSpec("TAB_1");
// Tab Indicator specified as Label and Icon
        spec.setIndicator("Label1",
                getResources().getDrawable(R.drawable.));
        spec.setContent(R.id.my_scroll_view1);
        host.addTab(spec);
        spec = host.newTabSpec("TAB2");
// Tab Indicator specified as View
        spec.setIndicator(
                getLayoutInflater().inflate(R.layout.tab2, null));
        spec.setContent(R.id.my_scroll_view2);
        host.addTab(spec);
*/


        adapter = new ScoreAdapter(
                ScoreActivity.this, R.layout.score_list_row, getScores());
        lv_local = (ListView) findViewById(R.id.list_view_local);
        lv_local.setAdapter(adapter);
        makeLocalScoresTab();

        lv_local.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int i = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                builder.setIcon(android.R.drawable.stat_sys_warning);
                builder.setTitle("Delete");
                builder.setMessage(R.string.delete_score);
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.remove(adapter.getItem(i));
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), R.string.delete_score, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton(android.R.string.no, null);
                builder.create().show();

                return true;
            }
        });


        adapter = new ScoreAdapter(
                ScoreActivity.this, R.layout.score_list_row, getScores());
        lv_friends = (ListView) findViewById(R.id.list_view_friends);
        lv_friends.setAdapter(adapter);
        makeFriendsScoreTab();
        lv_friends.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int i = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                builder.setIcon(android.R.drawable.stat_sys_warning);
                builder.setTitle("Delete");
                builder.setMessage(R.string.delete_score);
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.remove(adapter.getItem(i));
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), R.string.delete_score, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton(android.R.string.no, null);
                builder.create().show();

                return true;
            }
        });

        scoresHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                supportInvalidateOptionsMenu();
            }
        });
    }
    public ArrayList<HighScore> getScores(){
        ArrayList<HighScore> res = new ArrayList<>();
        res.add(new HighScore("maria1", "1002", "latitud", "longitud"));
        res.add(new HighScore("juan2", "3006", "latitud", "longitud"));
        res.add(new HighScore("juana", "500", "latitud", "longitud"));
        res.add(new HighScore("mario45", "4700", "latitud", "longitud"));
        res.add(new HighScore("blanca8", "5000", "latitud", "longitud"));
        res.add(new HighScore("carmen11", "4999", "latitud", "longitud"));

        return res;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.score_menu, menu);
        menu.findItem(R.id.item_del).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu){
        switch (menu.getItemId()) {
            case R.id.item_del:
                Intent intent = new Intent(this, CreditsActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    //MARIANO
    private void makeLocalScoresTab(){
        TabHost.TabSpec spec = scoresHost.newTabSpec("TAB_LOCAL_SCORES");
        // Tab Indicator specified as Label and Icon
        spec.setIndicator(getResources().getString(R.string.label_local_scores));
        spec.setContent(R.id.list_view_local);
        scoresHost.addTab(spec);
    }

    private void makeFriendsScoreTab(){
        TabHost.TabSpec spec = scoresHost.newTabSpec("TAB_FRIENDS_SCORES");
        // Tab Indicator specified as Label and Icon
        spec.setIndicator(getResources().getString(R.string.label_friends_scores));
        spec.setContent(R.id.list_view_friends);
        scoresHost.addTab(spec);


    }
}
