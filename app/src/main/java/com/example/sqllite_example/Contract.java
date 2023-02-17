package com.example.sqllite_example;

import android.provider.BaseColumns;

public final class Contract {

    private Contract() {
    }

    public static class FeedEntry implements BaseColumns {

        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";

    }

}
