package com.example.android.habbittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.habbittracker.data.HabbitContract.HabbitEntry;
import com.example.android.habbittracker.data.HabbitDbHelper;

public class EditorActivity extends AppCompatActivity {

    private EditText mActivityEditText;
    private EditText mTimeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mActivityEditText = (EditText) findViewById(R.id.edit_activity);
        mTimeEditText = (EditText) findViewById(R.id.edit_time);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                insertActivity();
                // Exit activity
                finish();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertActivity() {

        String ActivityString = mActivityEditText.getText().toString().trim();
        String TimeString = mTimeEditText.getText().toString().trim();
        int time = Integer.parseInt(TimeString);


        HabbitDbHelper mDbHelper = new HabbitDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(HabbitEntry.COLUMN_HABBIT, ActivityString);
        values.put(HabbitEntry.COLUMN_TIME, time);

        // Insert a new row for pet in the database, returning the ID of that new row.
        long newRowId = db.insert(HabbitEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving activity", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Habbit saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

}

