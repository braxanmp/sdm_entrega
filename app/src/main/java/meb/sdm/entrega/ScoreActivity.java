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

import meb.sdm.entrega.POJO.HighScore;
import meb.sdm.entrega.adapters.ScoreAdapter;
import meb.sdm.entrega.databases.DataBaseScore;

public class ScoreActivity extends AppCompatActivity {
    private ListView lv_local;
    private ListView lv_friends;
    private ScoreAdapter adapter, adapter2;
    private static TabHost scoresHost;
    private boolean localtab = true;
    DataBaseScore db;
    private static final String TAB_LOCAL_SCORES = "tabLocalScores";
    private static final String TAB_FRIENDS_SCORES = "tabFriendsScores";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        db = DataBaseScore.getSingletonDataBaseScore(this);

        scoresHost = (TabHost) findViewById(R.id.my_tab_host);
        scoresHost.setup();


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
                builder.setTitle(R.string.delete);
                builder.setMessage(R.string.delete_score);
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteScore(adapter.getItem(i).getName());
                        adapter.remove(adapter.getItem(i));
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), R.string.deleted_score, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton(android.R.string.no, null);
                builder.create().show();

                return true;
            }
        });


        adapter2 = new ScoreAdapter(
                ScoreActivity.this, R.layout.score_list_row, getFriendScores());
        lv_friends = (ListView) findViewById(R.id.list_view_friends);
        lv_friends.setAdapter(adapter2);
        makeFriendsScoreTab();
        lv_friends.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int i = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
                builder.setIcon(android.R.drawable.stat_sys_warning);
                builder.setTitle(R.string.delete);
                builder.setMessage(R.string.delete_score);
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteScore(adapter2.getItem(i).getName());
                        adapter2.remove(adapter2.getItem(i));
                        adapter2.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), R.string.deleted_score, Toast.LENGTH_SHORT).show();
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
                localtab = !localtab;
                supportInvalidateOptionsMenu();
            }
        });
    }
    public ArrayList<HighScore> getScores(){
        return db.getListHigscores();
    }

    public ArrayList<HighScore> getFriendScores(){
        ArrayList<HighScore> res = new ArrayList<HighScore>();
        res.add(new HighScore("pepe", "800", "9", "0"));
        res.add(new HighScore("Pedro", "900", "9", "0"));
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
                AlertDialog.Builder builder = new AlertDialog.Builder(ScoreActivity.this);
                builder.setIcon(android.R.drawable.stat_sys_warning);
                builder.setTitle(R.string.delete);
                builder.setMessage(R.string.delete_scores);
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(localtab) {
                            adapter.clear();
                            adapter.notifyDataSetChanged();
                            db.clearAllQuotations();
                        } else {
                            adapter2.clear();
                            adapter2.notifyDataSetChanged();
                        }
                        Toast.makeText(getApplicationContext(), R.string.deleted_scores, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton(android.R.string.no, null);
                builder.create().show();
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
