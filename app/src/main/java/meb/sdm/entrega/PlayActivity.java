package meb.sdm.entrega;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import meb.sdm.entrega.POJO.Question;

import static java.lang.Integer.parseInt;

public class PlayActivity extends AppCompatActivity {

    private Question question;
    private List<Question> questionList = generateQuestionList();
    private int questionIndex;
    private Button answer1, answer2, answer3, answer4;
    private TextView questionText, playingFor, playingQuestion;
    private boolean rigth = false;
    private Map<Integer, Button> mapButton = new HashMap<Integer, Button>();
    private static final String ANSWER_PUSHED = "answerPushed";
    private static final String QUESTION_INDEX = "questionIndex";
    private static final String USED_PUBLIC = "usedPublic";
    private static final String USED_FIFTY = "usedFifty";
    private static final String USED_PHONE = "usedPhone";
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private final int[] accumulated = {0, 100, 200, 300,
            500, 1000, 2000, 4000, 8000, 16000, 32000,
            64000, 125000, 250000, 500000, 1000000};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        answer1 = findViewById(R.id.button_option_1);
        answer1.setEnabled(false);
        answer2 = findViewById(R.id.button_option_2);
        answer2.setEnabled(false);
        answer3 = findViewById(R.id.button_option_3);
        answer3.setEnabled(false);
        answer4 = findViewById(R.id.button_option_4);
        answer4.setEnabled(false);
        mapButton.put(1,answer1);
        mapButton.put(2,answer2);
        mapButton.put(3,answer3);
        mapButton.put(4,answer4);
        questionText = findViewById(R.id.play_label_question);
        playingFor = findViewById(R.id.play_label_money);
        playingQuestion = findViewById(R.id.play_label_play_questionNumber);
        rigth = false;
        questionIndex = 0;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPref.edit();
       // descomentar esto para ir a una pregunta concreta
        /*editor.putInt(QUESTION_INDEX, 14);
        editor.commit();*/
        /*editor.remove(USED_PHONE);
        editor.remove(USED_PUBLIC);
        editor.remove(USED_FIFTY);
        editor.commit();*/
        showQuestion(sharedPref.getInt(QUESTION_INDEX, 0));


    }


    public void showQuestion(int index){
        question = questionList.get(index);
        playingFor.setText(""+(accumulated[index]));
        questionText.setText(question.getText());
        playingQuestion.setText(question.getNumber());
        for(Map.Entry<Integer, Button> m : mapButton.entrySet()) {
            m.getValue().setBackgroundTintList(this.getResources().getColorStateList(R.color.colorNotAnswered));
            m.getValue().setEnabled(true);
            m.getValue().setVisibility(View.VISIBLE);
        }
        answer1.setText(question.getAnswer1());
        answer2.setText(question.getAnswer2());
        answer3.setText(question.getAnswer3());
        answer4.setText(question.getAnswer4());
        restoreHelps();

        /**
         * Buscamos el botón pulsado para vovlerlo a marcar.
         */
        int but = sharedPref.getInt(ANSWER_PUSHED, 0);
        if ( but != 0 ) answerPushed(mapButton.get(but));
    }


    public void answerPushed(final View view){
        if(mapButton.get(parseInt(question.getRight())).equals((Button)view)) {
            rigth = true;
            int n = parseInt(question.getNumber());
            if (n == 15){
                correctFinal(view);
            } else {
                if (n == 5 || n == 10) {
                    autoSave(view, n);
                } else {
                    correctAnswer(view);
                }
            }
        } else {
            wrongAnswer((Button) view);
        }
        editor.remove(USED_PHONE);
        editor.remove(USED_PUBLIC);
        editor.remove(USED_FIFTY);
        editor.commit();
    }

    private void correctFinal(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.play_win);
        builder.setCancelable(false);
        builder.setMessage("Congratulations, you win.\nYour score will be saved");
        builder.setNeutralButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor.remove(ANSWER_PUSHED);
                editor.remove(QUESTION_INDEX);
                editor.commit();
                //------------save BD
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        builder.create().show();
    }

    private void wrongAnswer(Button view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.play_failed_question);
        builder.setCancelable(false);
        builder.setMessage(String.format(getResources().getString(R.string.play_failed_question_text), view.getText()));
        builder.setNeutralButton("Accept", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            editor.remove(ANSWER_PUSHED);
            editor.remove(QUESTION_INDEX);
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        });
        builder.create().show();

        for (int i = 1; i < 5; i++) {
            mapButton.get(i).setEnabled(false);
            if(mapButton.get(i).equals(view)) {
                editor.putInt(ANSWER_PUSHED, i);
                editor.commit();
            }
        }

        view.setBackgroundTintList(this.getResources().getColorStateList(R.color.worgAnswer));

    }

    private void correctAnswer(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.play_rigth_question);
        builder.setCancelable(false);
        builder.setMessage(String.format(getResources().getString(R.string.play_rigth_question_text), "" + accumulated[sharedPref.getInt(QUESTION_INDEX, 0)]));
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nextQuestion(view);
            }
        });
        builder.setNegativeButton("Stop", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor.remove(ANSWER_PUSHED);
                editor.remove(QUESTION_INDEX);
                editor.commit();
                AlertDialog.Builder builder2 = new AlertDialog.Builder(view.getContext());
                builder2.setMessage("Your scoure has been saved");
                builder2.setCancelable(false);
                builder2.setNeutralButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //--------------------------------Save here score in BD
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
                builder2.create().show();
            }
        });
        builder.create().show();

            mapButton.get(parseInt(question.getRight())).setBackgroundTintList(
                    this.getResources().getColorStateList(R.color.colorPrimary));
        editor.putInt(ANSWER_PUSHED, parseInt(question.getRight()));
        editor.commit();
    }

    private void autoSave(final View view, int n) {
        AlertDialog.Builder saver = new AlertDialog.Builder(view.getContext());
        saver.setCancelable(false);
        saver.setMessage(String.format("Congratulations, you passed the question number %1s, your progress is now saved", n));
        saver.setNeutralButton("Accept",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //--------------------------------Save here score in BD
                nextQuestion(view);
            }
        });
        saver.create().show();
    }

    public void nextQuestion(View view){

        int index = sharedPref.getInt(QUESTION_INDEX, 0);
        if(index < 14)   {
            editor.putInt(QUESTION_INDEX, sharedPref.getInt(QUESTION_INDEX, 0) + 1);
            editor.remove(ANSWER_PUSHED);
            editor.commit();
            showQuestion(sharedPref.getInt(QUESTION_INDEX, 0));
        } else {
            Toast.makeText(this, "finished", Toast.LENGTH_SHORT).show();
            editor.putInt(QUESTION_INDEX, 1);
            editor.commit();
        }
    }

    private void checkAnswer(Button answer, int buttonNumber){
        if(parseInt(question.getRight()) == buttonNumber) rigth = true;
        else {
            answer.setClickable(false);
            Toast.makeText(this, R.string.play_failed_question, Toast.LENGTH_SHORT).show();
        }
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
                mapButton.get(parseInt(question.getFifty1())).setVisibility(View.GONE);
                mapButton.get(parseInt(question.getFifty2())).setVisibility(View.GONE);
                editor.putBoolean(USED_FIFTY, true);
                editor.commit();
                Toast.makeText(this, "50% aplied", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_phone:
                mapButton.get(parseInt(question.getPhone())).setBackgroundTintList(this.getResources().getColorStateList(R.color.phoneAnswer));
                editor.putBoolean(USED_PHONE, true);
                editor.commit();
                Toast.makeText(this, "Phone selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_public:
                mapButton.get(parseInt(question.getAudience())).setBackgroundTintList(this.getResources().getColorStateList(R.color.publicAnswer));
                editor.putBoolean(USED_PUBLIC, true);
                editor.commit();
                Toast.makeText(this, "Public selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    private void restoreHelps(){
        if(sharedPref.getBoolean(USED_FIFTY, false)){
            mapButton.get(parseInt(question.getFifty1())).setVisibility(View.GONE);
            mapButton.get(parseInt(question.getFifty2())).setVisibility(View.GONE);
        }

        if(sharedPref.getBoolean(USED_PUBLIC, false)){
            mapButton.get(parseInt(question.getAudience())).setBackgroundTintList(this.getResources().getColorStateList(R.color.publicAnswer));
        }
        if(sharedPref.getBoolean(USED_PHONE, false)){
            mapButton.get(parseInt(question.getPhone())).setBackgroundTintList(this.getResources().getColorStateList(R.color.phoneAnswer));
        }
    }
    
    public List<Question> generateQuestionList() {
        List<Question> list = new ArrayList<Question>();
        Question q = null;

        q = new Question("1","Which is the Sunshine State of the US?","North Carolina","Florida",
                "Texas","Arizona","2","2","2","1",
                "4"
        );
        list.add(q);

        q = new Question(
                "2",
                "Which of these is not a U.S. state?",
                "New Hampshire",
                "Washington",
                "Wyoming",
                "Manitoba",
                "4",
                "4",
                "4",
                "2",
                "3"
        );
        list.add(q);

        q = new Question(
                "3",
                "What is Book 3 in the Pokemon book series?",
                "Charizard",
                "Island of the Giant Pokemon",
                "Attack of the Prehistoric Pokemon",
                "I Choose You!",
                "3",
                "2",
                "3",
                "1",
                "4"
        );
        list.add(q);

        q = new Question(
                "4",
                "Who was forced to sign the Magna Carta?",
                "King John",
                "King Henry VIII",
                "King Richard the Lion-Hearted",
                "King George III",
                "1",
                "3",
                "1",
                "2",
                "3"
        );
        list.add(q);

        q = new Question(
                "5",
                "Which ship was sunk in 1912 on its first voyage, although people said it would never sink?",
                "Monitor",
                "Royal Caribean",
                "Queen Elizabeth",
                "Titanic",
                "4",
                "4",
                "4",
                "1",
                "2"
        );
        list.add(q);

        q = new Question(
                "6",
                "Who was the third James Bond actor in the MGM films? (Do not include &apos;Casino Royale&apos;.)",
                "Roger Moore",
                "Pierce Brosnan",
                "Timothy Dalton",
                "Sean Connery",
                "1",
                "3",
                "3",
                "2",
                "3"
        );
        list.add(q);

        q = new Question(
                "7",
                "Which is the largest toothed whale?",
                "Humpback Whale",
                "Blue Whale",
                "Killer Whale",
                "Sperm Whale",
                "4",
                "2",
                "2",
                "2",
                "3"
        );
        list.add(q);

        q = new Question(
                "8",
                "In what year was George Washington born?",
                "1728",
                "1732",
                "1713",
                "1776",
                "2",
                "2",
                "2",
                "1",
                "4"
        );
        list.add(q);

        q = new Question(
                "9",
                "Which of these rooms is in the second floor of the White House?",
                "Red Room",
                "China Room",
                "State Dining Room",
                "East Room",
                "2",
                "2",
                "2",
                "3",
                "4"
        );
        list.add(q);

        q = new Question(
                "10",
                "Which Pope began his reign in 963?",
                "Innocent III",
                "Leo VIII",
                "Gregory VII",
                "Gregory I",
                "2",
                "1",
                "2",
                "3",
                "4"
        );
        list.add(q);

        q = new Question(
                "11",
                "What is the second longest river in South America?",
                "Parana River",
                "Xingu River",
                "Amazon River",
                "Rio Orinoco",
                "1",
                "1",
                "1",
                "2",
                "3"
        );
        list.add(q);

        q = new Question(
                "12",
                "What Ford replaced the Model T?",
                "Model U",
                "Model A",
                "Edsel",
                "Mustang",
                "2",
                "4",
                "4",
                "1",
                "3"
        );
        list.add(q);

        q = new Question(
                "13",
                "When was the first picture taken?",
                "1860",
                "1793",
                "1912",
                "1826",
                "4",
                "4",
                "4",
                "1",
                "3"
        );
        list.add(q);

        q = new Question(
                "14",
                "Where were the first Winter Olympics held?",
                "St. Moritz, Switzerland",
                "Stockholm, Sweden",
                "Oslo, Norway",
                "Chamonix, France",
                "4",
                "1",
                "4",
                "2",
                "3"
        );
        list.add(q);

        q = new Question(
                "15",
                "Which of these is not the name of a New York tunnel?",
                "Brooklyn-Battery",
                "Lincoln",
                "Queens Midtown",
                "Manhattan",
                "4",
                "4",
                "4",
                "1",
                "3"
        );
        list.add(q);

        return list;
    }
}
