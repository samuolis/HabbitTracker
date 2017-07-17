package com.example.android.habbittracker.data;

import android.provider.BaseColumns;

/**
 * Created by Lukas on 2017-07-17.
 */

public class HabbitContract {

    private HabbitContract() {
    }


    public static final class HabbitEntry implements BaseColumns {

        public final static String TABLE_NAME = "habbits";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_HABBIT = "habbit";
        public final static String COLUMN_TIME = "time";


    }
}
