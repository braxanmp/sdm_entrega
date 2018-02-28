package meb.sdm.entrega;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    private Map<Integer, Button> buttonMap = new HashMap<Integer, Button>();
    /*private int[] acumulated = new int[]{0, 100, 200, 300,
            500, 1000, 2000, 4000, 8000, 16000, 32000,
            64000, 125000, 250000, 500000, 1000000};*/
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        answer1 = findViewById(R.id.button_option_1);
        answer1.setClickable(false);
        answer2 = findViewById(R.id.button_option_2);
        answer2.setClickable(false);
        answer3 = findViewById(R.id.button_option_3);
        answer3.setClickable(false);
        answer4 = findViewById(R.id.button_option_4);
        answer4.setClickable(false);
        buttonMap.put(1,answer1);
        buttonMap.put(2,answer2);
        buttonMap.put(3,answer3);
        buttonMap.put(4,answer4);
        questionText = findViewById(R.id.play_label_question);
        playingFor = findViewById(R.id.play_label_money);
        playingQuestion = findViewById(R.id.play_label_play_questionNumber);
        rigth = false;
        questionIndex = 0;
        showQuestion(questionIndex);

    }

    public void showQuestion(int index){
        question = questionList.get(index);
        //playingFor.setText(parseInt(question.getNumber()));
        questionText.setText(question.getText());
        playingQuestion.setText(question.getNumber());
        answer1.setText(question.getAnswer1());
        answer1.setClickable(true);
        answer2.setText(question.getAnswer2());
        answer2.setClickable(true);
        answer3.setText(question.getAnswer3());
        answer3.setClickable(true);
        answer4.setText(question.getAnswer4());
        answer4.setClickable(true);
    }

    @SuppressLint({"ResourceAsColor", "NewApi"})
    public void answerPushed(View view){

        if(buttonMap.get(parseInt(question.getRight())).equals((Button)view)) {
            rigth = true;
            Toast.makeText(this, R.string.play_succesfull_question, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.play_failed_question, Toast.LENGTH_SHORT).show();
        }
        answer1.setBackgroundTintList(this.getResources().getColorStateList(R.color.worgAnswer));
        answer1.setEnabled(false);
        answer2.setBackgroundTintList(this.getResources().getColorStateList(R.color.worgAnswer));
        answer2.setEnabled(false);
        answer3.setBackgroundTintList(this.getResources().getColorStateList(R.color.worgAnswer));
        answer3.setEnabled(false);
        answer4.setBackgroundTintList(this.getResources().getColorStateList(R.color.worgAnswer));
        answer4.setEnabled(false);
        buttonMap.get(parseInt(question.getRight())).setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));

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
