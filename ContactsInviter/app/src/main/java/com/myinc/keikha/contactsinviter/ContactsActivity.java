package com.myinc.keikha.contactsinviter;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;


public class ContactsActivity extends ActionBarActivity {

    private SimpleCursorAdapter adapterContacts;
    // Defines the id of the loader for later reference
    public static final int CONTACT_LOADER_ID = 78;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        setupCursorAdapter();
        getSupportLoaderManager().initLoader(CONTACT_LOADER_ID,
                new Bundle(), contactsLoader);
    }


//    bind our listView directly to a cursor data set
    // cursor data set == provider or a raw sqlliste database
// Create simple cursor adapter to connect the cursor dataset we load with a listview
    private void setupCursorAdapter() {
    // Column data from cursor to bind views from
    String[] uiBindFrom = { ContactsContract.Contacts.DISPLAY_NAME };
    // View IDs which will have the respective column data inserted
    int[] uiBindTo = { R.id.tvName };
    // Create the simple cursor adapter to use for our list
    // specifying the template to inflate (item_contact),
    // Fields to bind from and to and mark the adapter as observing for changes
       adapterContacts = new SimpleCursorAdapter(
            this, R.layout.item_contact,
            null, uiBindFrom, uiBindTo,
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        ListView lvContacts = (ListView) findViewById(R.id.lvContacts);
        lvContacts.setAdapter(adapterContacts);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private LoaderManager.LoaderCallbacks<Cursor> contactsLoader =
            new LoaderManager.LoaderCallbacks<Cursor>() {
                // Create and return the actual cursor loader for the contacts data
                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    // Define the columns to retrieve
                    String[] projectionFields =  new String[] { ContactsContract.Contacts._ID,
                            ContactsContract.Contacts.DISPLAY_NAME };
                    // Construct the loader
                    CursorLoader cursorLoader = new CursorLoader(ContactsActivity.this,
                            ContactsContract.Contacts.CONTENT_URI, // URI
                            projectionFields,  // projection fields
                            null, // the selection criteria
                            null, // the selection args
                            null // the sort order
                    );
                    // Return the loader for use
                    return cursorLoader;
                }

                // When the system finishes retrieving the Cursor,
                // a call to the onLoadFinished() method takes place.
                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                    // The swapCursor() method assigns the new Cursor to the adapter
                    adapterContacts.swapCursor(cursor);
                }

                // This method is triggered when the loader is being reset
                // and the loader data is no longer available.
                @Override
                public void onLoaderReset(Loader<Cursor> loader) {
                    // Clear the Cursor we were using with another call to the swapCursor()
                    adapterContacts.swapCursor(null);
                }
            };

}
