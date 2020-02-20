package fr.ribotin.test.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import fr.ribotin.test.R;
import fr.ribotin.test.model.Question;
import fr.ribotin.test.model.QuestionBank;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mQuestion;
    private Button mAnswer1;
    private Button mAnswer2;
    private Button mAnswer3;
    private Button mAnswer4;

    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;

    private int mNumberOfQuestions;
    private int mScore;

    public static final String BUILD_EXTRA_SCORE = "SCORE_EXTRA_BUILD";
    public static final String BUNDLE_STATE_SCORE = "currentScore";
    public static final String BUNDLE_STATE_QUESTION = "currentQuestion";

    private boolean mEnableTouchEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //TODO Affichage dans la console
        System.out.println("GameActivity::onCreate()");
        //TODO Liaison graphiques variables
        mQuestion = (TextView) findViewById(R.id.activity_game_question_text);
        mAnswer1 = (Button) findViewById(R.id.activity_game_answer1_btn);
        mAnswer2 = (Button) findViewById(R.id.activity_game_answer2_btn);
        mAnswer3 = (Button) findViewById(R.id.activity_game_answer3_btn);
        mAnswer4 = (Button) findViewById(R.id.activity_game_answer4_btn);
        //TODO La méthode onClick() sera désormais appelée à chaque fois que l'utilisateur cliquera sur un des quatre boutons de réponse.
        mAnswer1.setOnClickListener(this);
        mAnswer2.setOnClickListener(this);
        mAnswer3.setOnClickListener(this);
        mAnswer4.setOnClickListener(this);
        //TODO distinguer le bouton sur lequel le joueur a appuyé. Pour ce faire, nous allons utiliser la méthode setTag(), et assigner un identifiant différent pour chaque bouton :
        mAnswer1.setTag(0);
        mAnswer2.setTag(1);
        mAnswer3.setTag(2);
        mAnswer4.setTag(3);
        //TODO Attribut du type QuestionBank, avec une méthode permettant de renvoyer un objet de ce type
        mQuestionBank = this.generateQuestions();
        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        } else {
            mScore = 0;
            mNumberOfQuestions = 4;
        }

        mEnableTouchEvents = true;

    }

    private QuestionBank generateQuestions() {

        Question question1 = new Question("Quel est le nom de l'actuel président français?",
                Arrays.asList("François Hollande", "Emmanuel Macron", "Jacques Chirac", "François Mitterand"),
                1);

        Question question2 = new Question("Combien de pays y a-t-il dans l'Union européenne?",
                Arrays.asList("15", "24", "28", "32"),
                2);

        Question question3 = new Question("Qui est le créateur du système d'exploitation Android?",
                Arrays.asList("Andy Rubin", "Steve Wozniak", "Jake Wharton", "Paul Smith"),
                0);

        Question question4 = new Question("Quand le premier homme at-il atterri sur la lune?",
                Arrays.asList("1958", "1962", "1967", "1969"),
                3);

        Question question5 = new Question("Quelle est la capitale de la Roumanie?",
                Arrays.asList("Bucarest", "Warsaw", "Budapest", "Berlin"),
                0);

        Question question6 = new Question("Qui a peint la Joconde?",
                Arrays.asList("Michelangelo", "Leonardo Da Vinci", "Raphael", "Carravagio"),
                1);

        Question question7 = new Question("Dans quelle ville le compositeur Frédéric Chopin est-il enterré?",
                Arrays.asList("Strasbourg", "Warsaw", "Paris", "Moscow"),
                2);

        Question question8 = new Question("Quel est le domaine national de premier niveau de la Belgique?",
                Arrays.asList(".bg", ".bm", ".bl", ".be"),
                3);

        Question question9 = new Question("Quel est le numéro de maison des Simpsons?",
                Arrays.asList("42", "101", "666", "742"),
                3);

        return new QuestionBank(Arrays.asList(question1,
                question2,
                question3,
                question4,
                question5,
                question6,
                question7,
                question8,
                question9));
    }

    //TODO Affichage des questions
    private void displayQuestion(final Question question) {
        // Set the text for the question text view and the four buttons
        mQuestion.setText(question.getQuestion());
        mAnswer1.setText(question.getChoiceList().get(0));
        mAnswer2.setText(question.getChoiceList().get(1));
        mAnswer3.setText(question.getChoiceList().get(2));
        mAnswer4.setText(question.getChoiceList().get(3));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mNumberOfQuestions);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        //TODO La méthode getTag() renvoie un type Object par défaut. Or, nous avions initialisé la valeur avec un entier. Il faut donc effectuer un cast afin de "forcer" la valeur à être un entier, comme ceci :
        int responseIndex = (int) v.getTag();
        //TODO Bonne réponse
        if (responseIndex == mCurrentQuestion.getAnswerIndex()){
            //TODO Une fois l'objet Toast construit, il suffit d'appeler la méthode show() sur l'instance pour l'afficher. Vu que vous êtes aussi flemmards que moi, vous pouvez chaîner les appels pour gagner du temps :
            Toast.makeText(this, "Correct!",Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
        //TODO Mauvaise Reponse
        Toast.makeText(this, "Mauvaise Reponse!",Toast.LENGTH_SHORT).show();
    }
        //TODO Si le nombre de Questions est = 0 donc plus de Questions Alors il ouvre une boite de dialogue avec le score du joueur et termine GameActivity

        mEnableTouchEvents = false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents = true;

                // If this is the last question, ends the game.
                // Else, display the next question.
                if (--mNumberOfQuestions == 0) {
                    // End the game
                    endGame();
                } else {
                    mCurrentQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mCurrentQuestion);
                }
            }
        }, 2300); // LENGTH_SHORT is usually 2 second long
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    private void endGame() {
        AlertDialog.Builder scoreEndGame = new AlertDialog.Builder(this);
        scoreEndGame.setTitle("Bien Joué !")
                .setMessage("Ton score est " + mScore + "/4")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(BUILD_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        System.out.println("GameActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("GameActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        System.out.println("GameActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        System.out.println("GameActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        System.out.println("GameActivity::onDestroy()");
    }
}
