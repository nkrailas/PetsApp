package com.example.android.pets.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.net.URI;

import com.example.android.pets.data.PetContract.PetEntry;
/**
 * {@link ContentProvider} for Pets app.
 */
public class PetProvider extends ContentProvider {

    // Tag for the log messages
    public static final String LOG_TAG = PetProvider.class.getSimpleName();

    // URI matcher code for the content URI for the pets table.
    private static final int PETS = 100;

    // URI matcher code for the content URI for a single pet in the pets table.
    private static final int PET_ID = 101;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // A static initializer is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetContract.PATH_PETS, PETS);
        sUriMatcher.addURI(PetContract.CONTENT_AUTHORITY, PetContract.PATH_PETS + "/#", PET_ID);

    }

    // Database helper object
    private PetDbHelper dbHelper;

    //Initialize the provider and the database helper object.
    @Override
    public boolean onCreate() {
        // Create and initialize a PetDbHelper object to gain access to the pets database.
        dbHelper = new PetDbHelper(getContext());
        return true;
    }

    // Perform query for given URI. Use given projection, selection, selection arguments, sort order.

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        // Get readable database.
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        // This cursor will hold the result of the query.
        Cursor cursor;

        // Determine if the URI matcher can match the URI to a specific code.
        int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                // For the PETS code, query the pets table directly with given the projection, selection,
                // selection arguments, and sort order. The cursor can contain multiple rows of pets table.
                cursor = database.query(PetEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case PET_ID:
                // For the PET_ID code, extract ID from the URI.
                // For every "?" in the selection, we need to have an element in the selection arguments
                // that will fill in the "?". If we have 1 question mark, then we have 1 string.
                selection = PetEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                // Query pets table where _id equals 3 to return Cursor containing that row.
                cursor = database.query(PetEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PETS:
                return insertPet(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    // Insert a pet into the database with the given content values.
    // Return the new content URI for that specific row in the database.

    private Uri insertPet(Uri uri, ContentValues values) {

        // Get writable database.
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        // Insert the new pet with the given values.
        long id = database.insert(PetEntry.TABLE_NAME, null, values);

        // If ID is -1, then insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Once we know the ID of the new row in the table, return the new URI with the ID
        // appended to the end of it.
        return ContentUris.withAppendedId(uri, id);
    }

    // Updates data at the given selection and selection arguments, with the new ContentValues.

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        return 0;
    }

    // Delete the data at the given selection and selection arguments.

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    // Returns the MIME type of data for the content URI.

    @Override
    public String getType(Uri uri) {
        return null;
    }
}