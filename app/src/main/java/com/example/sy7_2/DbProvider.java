package com.example.sy7_2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DbProvider  extends ContentProvider {

    public static final int PERSON_DIR = 0;
    public static final int PERSON_ITEM = 1;
    public static final String AUTHORITY = "com.example.sy7_2.provider";
    private static UriMatcher uriMatcher;
    private DbHelper dbHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "person", PERSON_DIR);
        uriMatcher.addURI(AUTHORITY, "person/#", PERSON_ITEM);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext(), "Persons.db", null, 2);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case PERSON_DIR:
                cursor = db.query("Person", projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case PERSON_ITEM:
                String personId = uri.getPathSegments().get(1);
                cursor = db.query("Person", projection, "id = ?",
                        new String[] { personId }, null, null, sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)) {
            case PERSON_DIR:
            case PERSON_ITEM:
                long newBookId = db.insert("Person", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/person/" + newBookId);
                break;
            default:
                break;
        }
        return uriReturn;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}

