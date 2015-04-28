package huji.ac.il.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Yuli on 25/04/2015.
 */
public class TodoDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TODOs_db";
    private static final String TODOS_TABLE_NAME = "TODOs";
    private static final String TODOS_TABLE_CREATE =
            "CREATE TABLE " + TODOS_TABLE_NAME + " (" +
                    "_id integer primary key autoincrement, " +
                    "title" + " TEXT, " +
                    "due" + " LONG);";

    TodoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TODOS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return;
    }
}