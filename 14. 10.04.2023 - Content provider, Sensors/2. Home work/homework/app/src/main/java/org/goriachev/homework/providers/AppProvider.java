package org.goriachev.homework.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.goriachev.homework.contracts.PeriodicalsContract;
import org.goriachev.homework.databaseHelpers.PeriodicalsDatabaseHelper;

public class AppProvider extends ContentProvider {

    private PeriodicalsDatabaseHelper databaseHelper;

    private static final UriMatcher uriMatcher = buildUriMatcher();

    public static final int EDITIONS = 100;

    public static final int EDITIONS_ID = 101;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        // все издания
        matcher.addURI(PeriodicalsContract.CONTENT_AUTHORITY, PeriodicalsContract.TABLE_NAME, EDITIONS);

        // издание по id
        matcher.addURI(PeriodicalsContract.CONTENT_AUTHORITY, PeriodicalsContract.TABLE_NAME + "/#", EDITIONS_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new PeriodicalsDatabaseHelper(getContext());
        databaseHelper.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final int match = uriMatcher.match(uri);

        var builder = new SQLiteQueryBuilder();

        switch (match) {
            case EDITIONS:
                builder.setTables(PeriodicalsContract.TABLE_NAME);
                break;
            case EDITIONS_ID:
                builder.setTables(PeriodicalsContract.TABLE_NAME);
                var id = PeriodicalsContract.getEditionId(uri);
                builder.appendWhere(PeriodicalsContract.Columns._ID + " = " + id);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        var db = databaseHelper.open();

        return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = uriMatcher.match(uri);

        switch (match) {
            case EDITIONS:
                return PeriodicalsContract.CONTENT_TYPE;
            case EDITIONS_ID:
                return PeriodicalsContract.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = uriMatcher.match(uri);

        if (match == EDITIONS) {
            var db = databaseHelper.open();
            var id = db.insert(PeriodicalsContract.TABLE_NAME, null, contentValues);

            if (id > 0) {
                return PeriodicalsContract.buildEditionUri(id);
            } else {
                throw new SQLException("Failed to insert: " + uri.toString());
            }
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        final int match = uriMatcher.match(uri);

        if (match != EDITIONS && match != EDITIONS_ID)
            throw new IllegalArgumentException("Unknown URI: " + uri);

        var selectionCriteria = selection;
        var db = databaseHelper.open();

        if (match == EDITIONS_ID) {
            var id = PeriodicalsContract.getEditionId(uri);
            selectionCriteria = PeriodicalsContract.Columns._ID + " = " + id;

            if ((selection != null) && !selection.isEmpty())
                selectionCriteria += "and (" + selection + ")";
        }

        return db.update(PeriodicalsContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = uriMatcher.match(uri);

        if (match != EDITIONS && match != EDITIONS_ID)
            throw new IllegalArgumentException("Unknown URI: " + uri);

        var selectionCriteria = selection;
        var db = databaseHelper.open();

        if (match == EDITIONS_ID) {
            var id = PeriodicalsContract.getEditionId(uri);
            selectionCriteria = PeriodicalsContract.Columns._ID + " = " + id;

            if ((selection != null) && !selection.isEmpty())
                selectionCriteria += "and (" + selection + ")";
        }

        return db.delete(PeriodicalsContract.TABLE_NAME, selectionCriteria, selectionArgs);
    }
}
