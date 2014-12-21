package in.crebit.bitblue.app.WebView.bitblue.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CrebitDB.db";
    private static final String MOBILE_PASS_TABLE = "mobilepasstable ";
    private static final String MOBILE_COLUMN = "mobile ";
    private static final String PASSWORD_COLUMN = "password ";
    private static final String[] COLUMNS = {MOBILE_COLUMN, PASSWORD_COLUMN};
    private String CREATE_MOBILE_PASS_TABLE;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        CREATE_MOBILE_PASS_TABLE = "CREATE TABLE " + MOBILE_PASS_TABLE + " ( " +
                MOBILE_COLUMN + "TEXT, " +
                PASSWORD_COLUMN + "TEXT)";
        sqLiteDatabase.execSQL(CREATE_MOBILE_PASS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MOBILE_PASS_TABLE);
        this.onCreate(sqLiteDatabase);
    }

    public boolean isNumberExists(String number) {
        boolean isfound;
        String query =
                "Select * FROM " + MOBILE_PASS_TABLE +
                        " WHERE " + MOBILE_COLUMN + " =  \"" + number + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
            isfound = true;
        else
            isfound = false;
        return isfound;
    }

    public boolean isPasswordCorrect(String mobile) {
        boolean isCorrect;
        String query =
                "Select " + PASSWORD_COLUMN + " FROM " + MOBILE_PASS_TABLE +
                        " WHERE " + MOBILE_COLUMN + " =  \"" + mobile + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst())
            isCorrect = true;
        else
            isCorrect = false;
        return isCorrect;
    }

    public void add_credentials(String number, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MOBILE_COLUMN, number);
        values.put(PASSWORD_COLUMN, password);
        db.insert(MOBILE_PASS_TABLE, null, values);
        db.close();
    }
}
