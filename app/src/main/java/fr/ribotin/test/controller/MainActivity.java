package fr.ribotin.test.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;

import fr.ribotin.test.R;
import fr.ribotin.test.model.User;

import static java.lang.System.out;

public class MainActivity extends AppCompatActivity {

    private TextView mWelcomeText;
    private EditText mNameInput;
    private Button mPlayButton;
    private User mUser;
    public static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    private SharedPreferences mPreferences;
    public static final String PREF_KEY_SCORE = "PREF_KEY_SCORE";
    public static final String PREF_KEY_NAMEUSER = "PREF_KEY_NAMEUSER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO Affichage dans la console
        out.println("MainActivity::onCreate()");
        //TODO Initialisation de l'attribut mPreferences
        mPreferences = getPreferences(MODE_PRIVATE);
        //TODO Initialisation de l'attribut mUser
        mUser = new User();
        //TODO Liaison graphiques variables
        mWelcomeText = (TextView) findViewById(R.id.activity_main_welcome_txt);
        mNameInput = (EditText) findViewById(R.id.activity_main_name_input);
        mPlayButton = (Button) findViewById(R.id.activity_main_play_btn);
        //TODO Desactivation du Button Play quand il n'y a pas de pseudo
        mPlayButton.setEnabled(false);
        //TODO focus sur le champ NameInput
        //mNameInput.requestFocus();
        //TODO Recup des info de score et nom d'utilisateur
        greetUser();
        //TODO Activation du Button Play quand il y a un char de rentré
        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPlayButton.setEnabled(s.toString().length() !=0); //TODO ICI
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        //TODO Detection du clique Utilisateur sur le button Play*
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Sauvegarde du pseudo entré sur l'appli dans l'attribut mUser et on utilise la methode setNameUser pour la mettre
                mUser.setNameUser(mNameInput.getText().toString());
                //
                mPreferences.edit().putString(PREF_KEY_NAMEUSER, mUser.getNameUser()).apply();
                //TODO Changemement d'activité en cliquant sur le bouton play
                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivity, GAME_ACTIVITY_REQUEST_CODE);
            }
        });
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {

        int score = data.getIntExtra(GameActivity.BUILD_EXTRA_SCORE,0);

        mPreferences.edit().putInt(PREF_KEY_SCORE, score).apply();

            greetUser();

        }

    }

    private void greetUser() {
        String firstname = mPreferences.getString(PREF_KEY_NAMEUSER, null);

        if (firstname != null) {
            int score = mPreferences.getInt(PREF_KEY_SCORE, 0);

            String fulltext = "Welcome back, " + firstname
                    + " !\nYour last score was " + score
                    + ", will you do better this time ?";
            mWelcomeText.setText(fulltext);
            mNameInput.setText(firstname);
            mNameInput.setSelection(firstname.length());
            mPlayButton.setEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        out.println("MainActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        out.println("MainActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        out.println("MainActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        out.println("MainActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        out.println("MainActivity::onDestroy()");
    }
}
