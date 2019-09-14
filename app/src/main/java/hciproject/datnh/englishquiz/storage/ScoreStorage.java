package hciproject.datnh.englishquiz.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class ScoreStorage{
    public static final String FILE_NAME = "score";
    public static final String[] SCORES = {"HighestScore", "LastScore", "TotalScore"};
    public static final String[] NAMES = {"quiz", "listening", "word"};
    public Context context;

    public ScoreStorage(Context context) {
        this.context = context;
    }

    public int getValue(String name, String score) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(name + score, 0);
    }

    public boolean setValue(String name, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        int crrHighScore = sharedPreferences.getInt(name + SCORES[0], 0);
        int crrTotalScore = sharedPreferences.getInt(name + SCORES[2], 0);

        editor.putInt(name + SCORES[0], value > crrHighScore ? value : crrHighScore);
        editor.putInt(name + SCORES[1], value);
        editor.putInt(name + SCORES[2], value + crrTotalScore);

        return editor.commit();
    }
}
