package org.goriachev.homework.contracts;

import android.content.ContentUris;
import android.net.Uri;

public class PeriodicalsContract {

    public static final String TABLE_NAME = "editions";

    public static final String CONTENT_AUTHORITY = "org.goriachev.homework";

    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + TABLE_NAME;


    public static class Columns {

        public static final String _ID = "_id";

        public static final String INDEX = "publication_index";

        public static final String PUBLICATION_TYPE = "publication_type";

        public static final String NAME = "name";

        public static final String PRICE = "price";

        public static final String SUBSCRIBE_DATE = "subscribe_date";

        public static final String DURATION = "duration";
    }

    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);


    // создание uri с id
    public static Uri buildEditionUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    // получение id из uri
    public static long getEditionId(Uri uri) {
        return ContentUris.parseId(uri);
    }
}
