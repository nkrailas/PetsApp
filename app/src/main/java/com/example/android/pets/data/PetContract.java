package com.example.android.pets.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

// API Contract

public final class PetContract {

    // Use an empty constructor to prevent accidentally instantiating the contract class.
    private PetContract() {
    }

    // Content Authority is the name for the entire content provider.
    public static final String CONTENT_AUTHORITY = "com.example.android.pets";

    // Use Content Authority to create base of all URI's which apps use to contact the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible path appended to base content URI for possible URI's.
    public static final String PATH_PETS = "pets";

    // Inner class that defines constant values for pets database table. Each entry is a single pet.
    public static final class PetEntry implements BaseColumns {

        // The content URI to access the pet data in the provider.
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);

        // MIME type for a list of pets.
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_PETS;

        // MIME type for a single pet.
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                CONTENT_AUTHORITY + "/" + PATH_PETS;

        // Name of database table for pets.
        public final static String TABLE_NAME = "pets";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PET_NAME = "name";
        public static final String COLUMN_PET_BREED = "breed";
        public static final String COLUMN_PET_GENDER = "gender";
        public static final String COLUMN_PET_WEIGHT = "weight";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

        // Returns whether or not the given gender is GENDER_UNKNOWN, GENDER_MALE, GENDER_FEMALE.
        public static boolean isValidGender(int gender) {
            if (gender == GENDER_UNKNOWN || gender == GENDER_MALE || gender == GENDER_FEMALE) {
                return true;
            }

            return false;
        }
    }
}
