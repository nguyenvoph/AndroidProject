package hciproject.datnh.englishquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import hciproject.datnh.englishquiz.entity.ListeningQuizEntity;
import hciproject.datnh.englishquiz.model.ListeningQuizModel;
import hciproject.datnh.englishquiz.model.SongModel;
import hciproject.datnh.englishquiz.storage.ScoreStorage;

public class ListeningActivity extends AppCompatActivity {

    private TextView txtQuestion;
    private TextView txtTotal;
    private TextView txtTimer;
    private TextView txtCurrent;
    private TextView txtScore;
    private Button btnA;
    private Button btnB;
    private Button btnC;
    private Button btnD;
    private Button btnConfirm;
    private Button btnPlay;
    private int mScore = 0;
    private String finalScore;
    private int indexQues = 0;
    private int currentQues = 1;
    private long time = 0;
    private int numQues;
    //    private String diff;
    private String onChosing;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private Handler handler;
    private Runnable runnable;
    private List<ListeningQuizEntity> listQuestion;
    private ArrayList<SongModel> listSong;
    private int position = 0;
    private CountDownTimer timer;
    private int difficult = 1;
    private Handler handlerAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening);
        //set score
        txtScore = (TextView) findViewById(R.id.txtScore);
        txtScore.setText("" + mScore);
        //get num of ques and difficulty
        numQues = getIntent().getExtras().getInt("numQues");//5, 10, 20, 40
//        diff = getIntent().getExtras().getString("diff");//Easy, Normal, Hard
        //get json
        String json = getIntent().getExtras().getString("model");
        ListeningQuizModel model = (new Gson()).fromJson(json, ListeningQuizModel.class);
        listQuestion = new ArrayList<>();
        listQuestion.addAll(model.getQuestions());
        if (model.getQuestions().size() > 0) {
            difficult = model.getQuestions().get(0).getDifficult();
        }
        System.out.println("Questions Music: " + listQuestion.size());
        //addSongList
        addSongList();
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
        //set music
        handler = new Handler();
        seekBar = (SeekBar) findViewById(R.id.seekBar);
//        seekBar.setEnabled(false);
        prepareMedia();
        mediaPlayer.start();
        playCircle();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

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
                    showRightAnswer();
                    if (listQuestion.get(indexQues).getAnswer().equals(onChosing)) {
                        mScore++;
                        txtScore.setText("" + mScore);
                    }
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
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
                            prepareMedia();
                            mediaPlayer.start();
                            playCircle();
                            btnConfirm.setEnabled(false);
                        }
                    }, 1000);
                } else {
                    if (listQuestion.get(indexQues).getAnswer().equals(onChosing)) {
                        mScore++;
                    }
                    showRightAnswer();
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }
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

    private void playCircle() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        if (mediaPlayer.isPlaying()) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    playCircle();
                }
            };
            handler.postDelayed(runnable, 1000);
        }
    }

    private void prepareMedia() {
        for (SongModel s : listSong) {
            if (s.getTitle().equals(listQuestion.get(indexQues).getFilename())) {
                position = listSong.indexOf(s);
                break;
            }
        }
        mediaPlayer = MediaPlayer.create(getApplicationContext(), listSong.get(position).getFile());
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        seekBar.setMax(mediaPlayer.getDuration());
    }

    private void showRightAnswer() {
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
        if (choice.equals("A")) {
            btnA.setBackgroundResource(R.drawable.button_bg_round_chosen);
            btnB.setBackgroundResource(R.drawable.button_bg_round);
            btnC.setBackgroundResource(R.drawable.button_bg_round);
            btnD.setBackgroundResource(R.drawable.button_bg_round);
        } else if (choice.equals("B")) {
            btnA.setBackgroundResource(R.drawable.button_bg_round);
            btnB.setBackgroundResource(R.drawable.button_bg_round_chosen);
            btnC.setBackgroundResource(R.drawable.button_bg_round);
            btnD.setBackgroundResource(R.drawable.button_bg_round);
        } else if (choice.equals("C")) {
            btnA.setBackgroundResource(R.drawable.button_bg_round);
            btnB.setBackgroundResource(R.drawable.button_bg_round);
            btnC.setBackgroundResource(R.drawable.button_bg_round_chosen);
            btnD.setBackgroundResource(R.drawable.button_bg_round);
        } else if (choice.equals("D")) {
            btnA.setBackgroundResource(R.drawable.button_bg_round);
            btnB.setBackgroundResource(R.drawable.button_bg_round);
            btnC.setBackgroundResource(R.drawable.button_bg_round);
            btnD.setBackgroundResource(R.drawable.button_bg_round_chosen);
        }
    }

    private long calculateTime(int numQues) {
        for (SongModel s : listSong) {
            for (ListeningQuizEntity l : listQuestion) {
                if (s.getTitle().equals(l.getFilename())) {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), s.getFile());
                    time += (mediaPlayer.getDuration() + 10 * 1000);
                }
            }
        }
        return time;
    }

    private void setTextview() {
        txtCurrent.setText("Current: " + currentQues);
        txtQuestion.setText(listQuestion.get(indexQues).getQuestion()
                + "\nA. " + listQuestion.get(indexQues).getAnswerA()
                + "\nB. " + listQuestion.get(indexQues).getAnswerB()
                + "\nC. " + listQuestion.get(indexQues).getAnswerC()
                + "\nD. " + listQuestion.get(indexQues).getAnswerD());
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

    public void playMedia(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
            playCircle();
        }
    }

    private void addSongList() {
        listSong = new ArrayList<>();
        listSong.add(new SongModel("test", R.raw.test));
        listSong.add(new SongModel("test1", R.raw.test1));
        listSong.add(new SongModel("test2", R.raw.test2));
        listSong.add(new SongModel("test3", R.raw.test3));
        listSong.add(new SongModel("test4", R.raw.test4));
        listSong.add(new SongModel("test5", R.raw.test5));
    }

    private void exitToResult() {
        Intent intent = new Intent(ListeningActivity.this, ResultActivity.class);
        //Set score
        ScoreStorage scoreStorage = new ScoreStorage(this);
        scoreStorage.setValue(ScoreStorage.NAMES[1], mScore * difficult);
        finalScore = mScore * difficult + "";
        intent.putExtra("finalScore", finalScore);
        mediaPlayer.release();
        handler.removeCallbacks(runnable);
        timer.cancel();
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        handler.removeCallbacks(runnable);
    }
}
