package hciproject.datnh.englishquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import hciproject.datnh.englishquiz.common.Constant;
import hciproject.datnh.englishquiz.communicator.ApiConnector;
import hciproject.datnh.englishquiz.model.ListeningQuizModel;
import hciproject.datnh.englishquiz.model.MultipleChoiceQuizModel;

public class MultipleChoiceActivity extends AppCompatActivity {

    private Spinner spinQues = null;
    private Spinner spinDiff = null;
    private int fromScreen;
    private TextView txtScreenTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        spinQues = (Spinner)findViewById(R.id.spinnerQues);
        spinDiff = (Spinner)findViewById(R.id.spinnerDiff);
        Bundle bd = new Bundle();
        bd = getIntent().getExtras();
        fromScreen = bd.getInt("fromActivity");//0,1
        if(fromScreen == 0){
            txtScreenTitle = (TextView) findViewById(R.id.txtTitle);
            String title = "GRAMMAR";
            txtScreenTitle.setText(title);
        } else if(fromScreen == 1){
            txtScreenTitle = (TextView) findViewById(R.id.txtTitle);
            String title = "LISTEN";
            txtScreenTitle.setText(title);
        }
    }

    public void backToMenu(View view) {
        Intent intent = new Intent(MultipleChoiceActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void doQuiz(View view) {
        Runnable r = createRunnable();
        Thread t = new Thread(r);
        t.start();
    }

    private Runnable createRunnable() {
        final int numQues = Integer.parseInt(spinQues.getSelectedItem().toString());
        final String diff = spinDiff.getSelectedItem().toString();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (fromScreen == 0) {
                    final Intent intentMul = new Intent(MultipleChoiceActivity.this, MultipleChoiceStartQuizActivity.class);
                    intentMul.putExtra("numQues", numQues);
//                intent.putExtra("diff", diff);
                    //get data
                    //first param is diff, 2nd is quantity
                    MultipleChoiceQuizModel model = ApiConnector.callMultipleChoiceApi(changeDiffToDiffInt(diff), numQues);
                    String json = (new Gson()).toJson(model);
                    intentMul.putExtra("model", json);
                    startActivity(intentMul);
                } else if (fromScreen == 1){
                    final Intent intentLis = new Intent(MultipleChoiceActivity.this, ListeningActivity.class);
                    intentLis.putExtra("numQues", numQues);
                    ListeningQuizModel model = ApiConnector.callListeningApi(changeDiffToDiffInt(diff), numQues);
                    String json = (new Gson()).toJson(model);
                    intentLis.putExtra("model", json);
//                    intentLis.putExtra("diff", diff);
                    startActivity(intentLis);
                }
            }
        };

        return runnable;
    }

    private int changeDiffToDiffInt(String diff) {
        if (diff.equals("Easy")) {
            return 1;
        } else if (diff.equals("Normal")) {
            return 2;
        } else {
            return 3;
        }
    }
}
