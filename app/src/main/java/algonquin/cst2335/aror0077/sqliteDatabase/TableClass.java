package algonquin.cst2335.aror0077.sqliteDatabase;

import android.provider.BaseColumns;

public final class TableClass {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private TableClass() {
    }

    /* Inner class that defines the table contents */
    public static class PexelDataBase implements BaseColumns {
        public static final String TABLE_NAME = "PexelTable";
        public static final String COLUMN_NAME_Image = "Image";
        public static final String COLUMN_NAME_LargeImage = "LargeImage";
        public static final String COLUMN_NAME_Url = "Url";


        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + PexelDataBase.TABLE_NAME + " (" +
                        PexelDataBase._ID + " INTEGER PRIMARY KEY," +
                        PexelDataBase.COLUMN_NAME_Image + " TEXT," +
                        PexelDataBase.COLUMN_NAME_LargeImage + " TEXT," +
                        PexelDataBase.COLUMN_NAME_Url + " TEXT)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + PexelDataBase.TABLE_NAME;

    }

}

