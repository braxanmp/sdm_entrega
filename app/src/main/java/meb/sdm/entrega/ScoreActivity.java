package meb.sdm.entrega;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        TabHost host = (TabHost) findViewById(R.id.my_tab_host);
        host.setup();
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
    }
    public ArrayList<HighScore> getMockQuotations(){
        ArrayList<HighScore> res = new ArrayList<>();
        res.add(new HighScore("maria1", "1002", "latitud", "longitud"));
        res.add(new HighScore("juan2", "3006", "latitud", "longitud"));
        res.add(new HighScore("juana", "500", "latitud", "longitud"));
        res.add(new HighScore("mario45", "4700", "latitud", "longitud"));
        res.add(new HighScore("blanca8", "5000", "latitud", "longitud"));
        res.add(new HighScore("carmen11", "4999", "latitud", "longitud"));

        return res;
    }
}
