package com.example.android.habbittracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.android.habbittracker.data.HabbitContract.HabbitEntry;
import com.example.android.habbittracker.data.HabbitDbHelper;

public class MainActivity extends AppCompatActivity {

    private HabbitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        mDbHelper = new HabbitDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseInfo() {

        Cursor cursor = queryAllHabbits();

        TextView displayView = (TextView) findViewById(R.id.text_view_habbit);

        try {

            displayView.setText("The habbits table contains " + cursor.getCount() + " habbits.\n\n");
            displayView.append(HabbitEntry._ID + " - " +
                    HabbitEntry.COLUMN_HABBIT + " - " +
                    HabbitEntry.COLUMN_TIME + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabbitEntry._ID);
            int habbitColumnIndex = cursor.getColumnIndex(HabbitEntry.COLUMN_HABBIT);
            int timeColumnIndex = cursor.getColumnIndex(HabbitEntry.COLUMN_TIME);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentHabbit = cursor.getString(habbitColumnIndex);
                int currentTime = cursor.getInt(timeColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentHabbit + " - " +
                        currentTime));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    public Cursor queryAllHabbits() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                HabbitEntry._ID,
                HabbitEntry.COLUMN_HABBIT,
                HabbitEntry.COLUMN_TIME};


        Cursor cursor = db.query(
                HabbitEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order
        return cursor;
    }


}