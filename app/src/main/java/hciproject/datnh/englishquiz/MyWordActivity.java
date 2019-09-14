package hciproject.datnh.englishquiz;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import hciproject.datnh.englishquiz.SQLite.DBManager;
import hciproject.datnh.englishquiz.communicator.ApiConnector;
import hciproject.datnh.englishquiz.entity.WordQuizEntity;
import hciproject.datnh.englishquiz.listadapter.CustomAdapter;
import hciproject.datnh.englishquiz.model.WordQuizModel;

public class MyWordActivity extends AppCompatActivity {
    private ListView lvDictionary;
    private SearchView editWord;
    private EditText editMeaning;
    private CustomAdapter customAdapter;
    private ArrayList<WordQuizEntity> arrWordAPI = new ArrayList<>();
    private ArrayList<WordQuizEntity> arrWordSQLite = new ArrayList<>();
    private ArrayList<WordQuizEntity> arrSearchWord = new ArrayList<>();
    private ArrayList<WordQuizEntity> arrSearchMeaning = new ArrayList<>();
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_word);

        dbManager = new DBManager(this);
        Runnable r = createWordRunnable();
        Thread t = new Thread(r);
        t.start();

    }

    public void showAllWord(View view){
        Button btnAllWord = (Button) findViewById(R.id.btnAllWord);
        btnAllWord.setBackgroundColor(Color.GREEN);
        Button btnFavorite = (Button) findViewById(R.id.btnFavorite);
        btnFavorite.setBackgroundColor(Color.GRAY);
        lvDictionary = (ListView) findViewById(R.id.lvDictionary);
        Collections.sort(arrWordAPI, new Comparator<WordQuizEntity>() {
            @Override
            public int compare(WordQuizEntity w1, WordQuizEntity w2) {
                return w1.getName().compareTo(w2.getName());
            }
        });

        customAdapter = new CustomAdapter(this,R.layout.list_word,arrWordAPI);
        lvDictionary.setAdapter(customAdapter);
    }

    public void showAllWordFirst(){
        Button btnAllWord = (Button) findViewById(R.id.btnAllWord);
        btnAllWord.setBackgroundColor(Color.GREEN);
        Button btnFavorite = (Button) findViewById(R.id.btnFavorite);
        btnFavorite.setBackgroundColor(Color.GRAY);

        lvDictionary = (ListView) findViewById(R.id.lvDictionary);
        Collections.sort(arrWordAPI, new Comparator<WordQuizEntity>() {
            @Override
            public int compare(WordQuizEntity w1, WordQuizEntity w2) {
                return w1.getName().compareTo(w2.getName());
            }
        });

        customAdapter = new CustomAdapter(this,R.layout.list_word,arrWordAPI);
        lvDictionary.setAdapter(customAdapter);

    }

    public void showFavoriteWord(View view){
        Button btnFavorite = (Button) findViewById(R.id.btnFavorite);
        btnFavorite.setBackgroundColor(Color.GREEN);
        Button btnAllWord = (Button) findViewById(R.id.btnAllWord);
        btnAllWord.setBackgroundColor(Color.GRAY);
        lvDictionary = (ListView) findViewById(R.id.lvDictionary);
        arrWordSQLite = dbManager.getAllWord();
        Collections.sort(arrWordSQLite, new Comparator<WordQuizEntity>() {
            @Override
            public int compare(WordQuizEntity w1, WordQuizEntity w2) {
                return w1.getName().compareTo(w2.getName());
            }
        });

        customAdapter = new CustomAdapter(this,R.layout.list_word,arrWordSQLite);
        lvDictionary.setAdapter(customAdapter);
    }

    private Runnable createWordRunnable() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //get data
                WordQuizModel model = ApiConnector.callAllWordApi();
                arrWordAPI.addAll(model.getQuestions());
                setUp();
            }
        };
        return runnable;
    }

    public void addWordToFavorite(WordQuizEntity word){
        dbManager.addword(word);
    }


    public void backToMenu(View view) {
        Intent intent = new Intent(MyWordActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void setUp() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                System.out.println(arrWordAPI.size());

                arrWordSQLite = dbManager.getAllWord();
                showAllWordFirst();


                lvDictionary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        WordQuizEntity word = (WordQuizEntity) adapterView.getItemAtPosition(i);
                        System.out.println(word.getId() + "======");

                        System.out.println( view.getId());
                    }
                });


                //search by word
                editWord = (SearchView) findViewById(R.id.editWord);
                editWord.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        customAdapter.getFilter().filter(s);
                        return false;

                    }
                });
            }
        });
    }

}
