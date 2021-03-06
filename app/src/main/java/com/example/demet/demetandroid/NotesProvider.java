package com.example.demet.demetandroid;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by demet on 26.01.2018.
 */

public class NotesProvider extends ContentProvider {
    private static final String AUTHORITY = "dd";

    private static final String BASE_PATH = "notes";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );



    private static final int NOTES = 1;

    private static final int NOTES_ID = 2;

    //URIMatcher; NO_MATCH = -1 (constant value)
    private static final UriMatcher uriMatcher = new UriMatcher((UriMatcher.NO_MATCH));

    public static final String CONTENT_ITEM_TYPE = "NOTE";

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, NOTES);

        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", NOTES_ID);
    }

    private SQLiteDatabase database;
    @Override
    public boolean onCreate() {
        DBOpenHelper helper=new DBOpenHelper(getContext());
         database=helper.getWritableDatabase();
         return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return database.query(DBOpenHelper.TABLE_NOTES,DBOpenHelper.ALL_Columns,s,null,null,null,DBOpenHelper.NOTE_CREATED + " DESC");


    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long id=database.insert(DBOpenHelper.TABLE_NOTES,null,contentValues);
        return Uri.parse(BASE_PATH+"/" +id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return database.delete(DBOpenHelper.TABLE_NOTES,s,strings);

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {

       return database.update(DBOpenHelper.TABLE_NOTES,contentValues,s,strings);
    }
}
