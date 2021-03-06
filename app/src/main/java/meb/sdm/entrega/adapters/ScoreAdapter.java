package meb.sdm.entrega.adapters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import meb.sdm.entrega.POJO.HighScore;
import meb.sdm.entrega.R;


/**
 * Created by erica on 27/02/2018.
 */

public class ScoreAdapter extends ArrayAdapter<HighScore>{

    private Context context;
    //private int resource;
    private ArrayList<HighScore> scores;

    public ScoreAdapter(@NonNull Context context, int resource, ArrayList<HighScore> scores) {
        super(context, resource, scores);
        this.context = context;
        this.scores = scores;
    }

    public View  getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.score_list_row, null);
        }

        HighScore item = scores.get(position);
        TextView usuario = (TextView) view.findViewById(R.id.label_usuario);
        usuario.setText(item.getName());
        TextView puntuacion = (TextView) view.findViewById(R.id.label_puntuacion);
        puntuacion.setText(item.getScoring());

        return view;
    }
}
