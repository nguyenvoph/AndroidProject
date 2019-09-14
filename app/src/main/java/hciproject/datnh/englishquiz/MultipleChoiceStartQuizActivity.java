package hciproject.datnh.englishquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import hciproject.datnh.englishquiz.entity.MultipleChoiceQuizEntity;
import hciproject.datnh.englishquiz.model.MultipleChoiceQuizModel;
import hciproject.datnh.englishquiz.storage.ScoreStorage;

public class MultipleChoiceStartQuizActivity extends AppCompatActivity {
    public static final int SCORE_FROM_QUIZ = 0;
    public static final int SCORE_FROM_LISTEN = 1;
    private TextView txtQuestion;
    private TextView txtTimer;
    private TextView txtCurrent;
    private TextView txtTotal;
    private Button btnA;
    private Button btnB;
    private Button btnC;
    private Button btnD;
    private Button btnConfirm;
    private int mScore = 0;
    private String finalScore;
    private int indexQues = 0;
    private int currentQues = 1;
    private long time;
    private int numQues;
    //    private String diff;
    private String onChosing = null;
    private CountDownTimer timer;
    private List<MultipleChoiceQuizEntity> listQuestion;
    private int difficult = 1;
    private Handler handlerAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice_start_quiz);
        //get num of ques and difficulty
        numQues = getIntent().getExtras().getInt("numQues");//5, 10, 20, 40
//        diff = getIntent().getExtras().getString("diff");//Easy, Normal, Hard
        //get json
        String json = getIntent().getExtras().getString("model");
        MultipleChoiceQuizModel model = (new Gson()).fromJson(json, MultipleChoiceQuizModel.class);
        listQuestion = new ArrayList<>();
        listQuestion.addAll(model.getQuestions());
        if (model.getQuestions().size() > 0) {
            difficult = model.getQuestions().get(0).getDifficult();
        }
        //set total
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        txtTotal.setText("Total: " + numQues);
        //set time
        time = calculateTime(numQues);
        txtTimer = (TextView) findViewById(R.id.txtTimer);
        timer = new CountDownTimer(time, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                time = millisUntilFinished;
                //Update txtTimer
                int mins = (int) time / 60000;
                int seconds = (int) time % 60000 / 1000;
                String timeLeftText;
                timeLeftText = "" + mins;
                timeLeftText += ":";
                if (seconds < 10) timeLeftText += "0";
                timeLeftText += seconds;
                txtTimer.setText(timeLeftText);
            }

            @Override
            public void onFinish() {
                exitToResult();
            }
        }.start();
        //set textview question
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        txtQuestion.setMovementMethod(new ScrollingMovementMethod());
        txtCurrent = (TextView) findViewById(R.id.txtCurrent);
        setTextview();
        //check answer
        btnA = (Button) findViewById(R.id.btnA);
        btnB = (Button) findViewById(R.id.btnB);
        btnC = (Button) findViewById(R.id.btnC);
        btnD = (Button) findViewById(R.id.btnD);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setEnabled(false);
        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change color
                onChosing = "A";
                changeBgButton(onChosing);
                btnConfirm.setEnabled(true);
            }
        });
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change color
                onChosing = "B";
                changeBgButton(onChosing);
                btnConfirm.setEnabled(true);
            }
        });
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change color
                onChosing = "C";
                changeBgButton(onChosing);
                btnConfirm.setEnabled(true);
            }
        });
        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change color
                onChosing = "D";
                changeBgButton(onChosing);
                btnConfirm.setEnabled(true);
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQues < numQues) {
                    if (listQuestion.get(indexQues).getAnswer().equals(onChosing)) {
                        mScore++;
                    }
                    showRightAnswer();
                    handlerAnswer = new Handler();
                    handlerAnswer.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btnA.setBackgroundResource(R.drawable.button_bg_round);
                            btnB.setBackgroundResource(R.drawable.button_bg_round);
                            btnC.setBackgroundResource(R.drawable.button_bg_round);
                            btnD.setBackgroundResource(R.drawable.button_bg_round);
                            currentQues++;
                            indexQues++;
                            setTextview();
                            btnConfirm.setEnabled(false);
                        }
                    }, 1000);
                } else {
                    if (listQuestion.get(indexQues).getAnswer().equals(onChosing)) {
                        mScore++;
                    }
                    showRightAnswer();
                    handlerAnswer = new Handler();
                    handlerAnswer.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            exitToResult();
                        }
                    }, 1000);
                }
            }
        });
    }

    private void showRightAnswer(){
        if (listQuestion.get(indexQues).getAnswer().equals("A")) {
            btnA.setBackgroundResource(R.drawable.button_bg_round_correct);
        } else if (listQuestion.get(indexQues).getAnswer().equals("B")) {
            btnB.setBackgroundResource(R.drawable.button_bg_round_correct);
        } else if (listQuestion.get(indexQues).getAnswer().equals("C")) {
            btnC.setBackgroundResource(R.drawable.button_bg_round_correct);
        } else if (listQuestion.get(indexQues).getAnswer().equals("D")) {
            btnD.setBackgroundResource(R.drawable.button_bg_round_correct);
        }
    }

    private void changeBgButton(String choice) {
        if (onChosing.equals("A")) {
            btnA.setBackgroundResource(R.drawable.button_bg_round_chosen);
            btnB.setBackgroundResource(R.drawable.button_bg_round);
            btnC.setBackgroundResource(R.drawable.button_bg_round);
            btnD.setBackgroundResource(R.drawable.button_bg_round);
        } else if (onChosing.equals("B")) {
            btnA.setBackgroundResource(R.drawable.button_bg_round);
            btnB.setBackgroundResource(R.drawable.button_bg_round_chosen);
            btnC.setBackgroundResource(R.drawable.button_bg_round);
            btnD.setBackgroundResource(R.drawable.button_bg_round);
        } else if (onChosing.equals("C")) {
            btnA.setBackgroundResource(R.drawable.button_bg_round);
            btnB.setBackgroundResource(R.drawable.button_bg_round);
            btnC.setBackgroundResource(R.drawable.button_bg_round_chosen);
            btnD.setBackgroundResource(R.drawable.button_bg_round);
        } else if (onChosing.equals("D")) {
            btnA.setBackgroundResource(R.drawable.button_bg_round);
            btnB.setBackgroundResource(R.drawable.button_bg_round);
            btnC.setBackgroundResource(R.drawable.button_bg_round);
            btnD.setBackgroundResource(R.drawable.button_bg_round_chosen);
        }
    }

    private long calculateTime(int numQues) {
        return numQues * 60 * 1000;
    }

    private void setTextview() {
        //dao
        txtCurrent.setText("Current: " + currentQues);
        txtQuestion.setText(listQuestion.get(indexQues).getQuestion()
                + "\n" + listQuestion.get(indexQues).getAnswerA()
                + "\n" + listQuestion.get(indexQues).getAnswerB()
                + "\n" + listQuestion.get(indexQues).getAnswerC()
                + "\n" + listQuestion.get(indexQues).getAnswerD());
    }

    public void backToMenu(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Are you sure you want to finish the test?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exitToResult();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private void exitToResult() {
        Intent intent = new Intent(MultipleChoiceStartQuizActivity.this, ResultActivity.class);
        //Set score
        ScoreStorage scoreStorage = new ScoreStorage(this);
        scoreStorage.setValue(ScoreStorage.NAMES[0], mScore * difficult);
        finalScore = mScore * difficult + "";
        intent.putExtra("scoreFromIntent", SCORE_FROM_QUIZ);
        intent.putExtra("finalScore", finalScore);
        timer.cancel();
        startActivity(intent);
    }
}
