package hciproject.datnh.englishquiz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import hciproject.datnh.englishquiz.storage.ScoreStorage;

public class ScoreActivity extends AppCompatActivity {
    private TextView txtScoreTitle;
    private TextView txtScore;
    private String[] scoreName = {"txtQuizScore", "txtListenScore", "txtVocaScore"};
    private ImageView quizImgView;
    private ImageView listeningImgView;
    private ImageView vocabularyImgView;
    private ScoreStorage scoreStorage = new ScoreStorage(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        txtScoreTitle = (TextView) findViewById(R.id.txtScoreTitle);
        String scoreTitle = "Highest Score\n" +
                "Last Score\n" +
                "Total Score\n";

        txtScoreTitle.setText(scoreTitle);
        changePictureAndScore();
        quizImgView = (ImageView) findViewById(R.id.txtQuizScore);
        listeningImgView = (ImageView) findViewById(R.id.txtListenScore);
        vocabularyImgView = (ImageView) findViewById(R.id.txtVocaScore);

        listeningImgView.performClick();
    }

    public void backToMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void changePictureAndScore(){
        txtScore = (TextView) findViewById(R.id.txtScore);
        String score = "60\n" +
                "50\n" +
                "50\n";
        txtScore.setText(score);
    }

    public void clickQuizScore(View view) {
        quizImgView.setBackgroundColor(Color.BLUE);
        listeningImgView.setBackgroundColor(Color.TRANSPARENT);
        vocabularyImgView.setBackgroundColor(Color.TRANSPARENT);
        TextView txtScoreName = (TextView) findViewById(R.id.txtScoreName);
        txtScoreName.setText("GRAMMAR");
        txtScore = (TextView) findViewById(R.id.txtScore);
        String score = scoreStorage.getValue(ScoreStorage.NAMES[0], scoreStorage.SCORES[0]) + "\n" +
                scoreStorage.getValue(ScoreStorage.NAMES[0], scoreStorage.SCORES[1]) + "\n" +
                scoreStorage.getValue(ScoreStorage.NAMES[0], scoreStorage.SCORES[2]) + "\n";
        txtScore.setText(score);
    }

    public void clickListening(View view) {
        quizImgView.setBackgroundColor(Color.TRANSPARENT);
        listeningImgView.setBackgroundColor(Color.BLUE);
        vocabularyImgView.setBackgroundColor(Color.TRANSPARENT);
        TextView txtScoreName = (TextView) findViewById(R.id.txtScoreName);
        txtScoreName.setText("LISTEN");
        txtScore = (TextView) findViewById(R.id.txtScore);
        String score = scoreStorage.getValue(ScoreStorage.NAMES[1], scoreStorage.SCORES[0]) + "\n" +
                scoreStorage.getValue(ScoreStorage.NAMES[1], scoreStorage.SCORES[1]) + "\n" +
                scoreStorage.getValue(ScoreStorage.NAMES[1], scoreStorage.SCORES[2]) + "\n";
        txtScore.setText(score);
    }

    public void clickVocabulary(View view) {
        quizImgView.setBackgroundColor(Color.TRANSPARENT);
        listeningImgView.setBackgroundColor(Color.TRANSPARENT);
        vocabularyImgView.setBackgroundColor(Color.BLUE);
        TextView txtScoreName = (TextView) findViewById(R.id.txtScoreName);
        txtScoreName.setText("WORD GAME");
        txtScore = (TextView) findViewById(R.id.txtScore);
        String score = scoreStorage.getValue(ScoreStorage.NAMES[2], scoreStorage.SCORES[0]) + "\n" +
                scoreStorage.getValue(ScoreStorage.NAMES[2], scoreStorage.SCORES[1]) + "\n" +
                scoreStorage.getValue(ScoreStorage.NAMES[2], scoreStorage.SCORES[2]) + "\n";
        txtScore.setText(score);
    }
}
