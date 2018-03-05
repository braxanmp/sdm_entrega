package meb.sdm.entrega.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import meb.sdm.entrega.POJO.HighScore;

/**
 * Created by bmeci on 05/03/2018.
 */

public class DataBaseScore extends SQLiteOpenHelper {
    private static DataBaseScore databasescore;

    private DataBaseScore(Context context){
        super(context, "database", null, 1);
    }

    public static synchronized DataBaseScore getSingletonDataBaseScore(Context context){
        if(databasescore == null){
            databasescore = new DataBaseScore(context);
        }
        return databasescore;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE score_table (id INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, name TEXT NOT NULL, score TEXT NOT NULL);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<HighScore> getListHigscores(){
        ArrayList<HighScore> result = new ArrayList<>();
        HighScore item;

        // Get access to the database in read mode
        SQLiteDatabase database = getReadableDatabase();
        // Query the table to get the text and author for all existing entries
        Cursor cursor = database.query(
                "score_table", new String[]{"name", "score"}, null, null, null, null, null);
        // Go through the resulting cursor
        while (cursor.moveToNext()) {
            // Create a Quotation object for the given entry in the database
            item = new HighScore();
            item.setName(cursor.getString(0));
            item.setScoring(cursor.getString(1));
            // Add the object to the result list
            result.add(item);
        }
        // Close cursor and database helper
        cursor.close();
        database.close();

        return result;
    }

    public boolean haveHighscore(String name) {

        // Get access to the database in read mode
        SQLiteDatabase database = getReadableDatabase();
        // Query the table to get the id of a given quotation (if any)
        Cursor cursor = database.query("score_table", new String[]{"name"}, "name=?", new String[]{name}, null, null, null, null);
        // If no rows are returned then the quotation is not in the database
        final boolean result = (cursor.getCount() > 0);
        // Close cursor and database helper
        cursor.close();
        database.close();

        return result;
    }

    public void addHighscore(String name, String score) {
        if(haveHighscore(name)){
            deleteScore(name);
        }
            // Get access to the database in write mode
            SQLiteDatabase database = getWritableDatabase();
            // Insert the new quotation into the table (autoincremental id)
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("score", score);
            database.insert("score_table", null, values);
            // Close the database helper
            database.close();

    }

    /*
        Remove all entries from the database
    */
    public void clearAllQuotations() {

        // Get access to the database in write mode
        SQLiteDatabase database = getWritableDatabase();
        // Delete all entries from the table
        database.delete("score_table", null, null);
        // Close the database helper
        database.close();
    }

    /*
        Delete a given quotation from the database
    */
    public void deleteScore(String name) {

        // Get access to the database in write mode
        SQLiteDatabase database = getWritableDatabase();
        // Delete from the table any entry with the given text
        database.delete("score_table", "name=?", new String[]{name});
        // Close the database helper
        database.close();
    }

}
