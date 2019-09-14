package hciproject.datnh.englishquiz.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hciproject.datnh.englishquiz.entity.WordQuizEntity;


public class DBManager extends SQLiteOpenHelper{
    public static final String DATABASE_NAME ="dictionary_list";
    private static final String TABLE_NAME ="dictionary";
    private static final String ID ="id";
    private static final String NAME ="name";
    private static final String MEANING ="mean";
    private static final String TYPE ="type";
    private Context context;

    public DBManager(Context context) {
        super(context, DATABASE_NAME,null, 1);
        Log.d("DBManager", "DBManager: ");
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE "+TABLE_NAME +" (" +
                ID +"  primary key, "+
                NAME + " TEXT, "+
                MEANING  +" TEXT, "+
                TYPE +" TEXT)";
        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    //Add new a 
    public void addword(WordQuizEntity word){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, word.getId());
        values.put(NAME, word.getName());
        values.put(MEANING, word.getMeaning());
        values.put(TYPE, word.getType());

        //Neu de null thi khi value bang null thi loi

        db.insert(TABLE_NAME,null,values);

        db.close();
    }

    public void deleteWord(WordQuizEntity word) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?",
                new String[] { String.valueOf(word.getId()) });
        db.close();
    }

    public void deleteWordById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public ArrayList<WordQuizEntity> getAllWord() {
        ArrayList<WordQuizEntity> listWord = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                WordQuizEntity word = new WordQuizEntity();
                word.setId(cursor.getInt(0));
                word.setName(cursor.getString(1));
                word.setMeaning(cursor.getString(2));
                word.setType(cursor.getString(3));
                listWord.add(word);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listWord;
    }

    public boolean checkWord(int id) {
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { ID,
                        NAME, MEANING ,TYPE }, ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
            check = false;
        } else {
            check = true;
            cursor.moveToFirst();
        }

        System.out.println("check = " + check);

        cursor.close();
        db.close();
        return check;
    }

}
