package com.example.android.pets.data;

import android.net.Uri;
import android.provider.BaseColumns;

// API Contract

public final class PetContract {

    // Content Authority is the name for the entire content provider.
    public static final String CONTENT_AUTHORITY = "com.example.android.pets";
    // Use Content Authority to create base of all URI's which apps use to contact the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    // Possible path appended to base content URI for possible URI's.
    public static final String PATH_PETS = "pets";

    // Use an empty constructor to prevent accidentally instantiating the contract class.
    private PetContract() {}

    // Inner class that defines constant values for pets database table. Each entry is a single pet.
    public static final class PetEntry implements BaseColumns {

        // The content URI to access the pet data in the provider.
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);

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

        // Returns whether or not the given gender GENDER_UNKNOWN, GENDER_MALE, GENDER_FEMALE.
        public static boolean isValidGender(int gender) {
            if (gender == GENDER_UNKNOWN || gender == GENDER_MALE || gender == GENDER_MALE) {
                return true;
            }

            return false;
        }


    }
}
