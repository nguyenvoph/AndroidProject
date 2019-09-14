package hciproject.datnh.englishquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.gson.Gson;

import hciproject.datnh.englishquiz.communicator.ApiConnector;
import hciproject.datnh.englishquiz.entity.WordQuizEntity;
import hciproject.datnh.englishquiz.model.MultipleChoiceQuizModel;

public class MainActivity extends AppCompatActivity {
    //test commit
    public static final int FROMQUIZ = 0;
    public static final int FROMLISTEN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void closeApplication(View view) {
        this.finishAffinity();
    }

    public void goToMultipleChoice(View view) {
        Intent intent = new Intent(MainActivity.this, MultipleChoiceActivity.class);
        intent.putExtra("fromActivity", FROMQUIZ);
        startActivity(intent);
    }

    public void goToVocabulary(View view) {
        Intent intent = new Intent(MainActivity.this, VocabularyActivity.class);
        startActivity(intent);
    }

    public void goToMyWord(View view) {
        Intent intent = new Intent(MainActivity.this, MyWordActivity.class);
        startActivity(intent);
    }

    public void goToListening(View view) {
        Intent intent = new Intent(MainActivity.this, MultipleChoiceActivity.class);
        intent.putExtra("fromActivity", FROMLISTEN);
        startActivity(intent);
    }

    public void goToScore(View view) {
        Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
        startActivity(intent);
    }


    public void goToAbout(View view) {
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        startActivity(intent);
    }
}
