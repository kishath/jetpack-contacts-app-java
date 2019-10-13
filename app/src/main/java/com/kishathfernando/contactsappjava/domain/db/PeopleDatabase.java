package com.kishathfernando.contactsappjava.domain.db;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.kishathfernando.contactsappjava.domain.dao.PeopleDao;
import com.kishathfernando.contactsappjava.domain.models.People;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {People.class}, version = 1)
public abstract class PeopleDatabase extends RoomDatabase {

    public abstract PeopleDao peopleDao();

    private static volatile PeopleDatabase INSTANCE;

    private static Context sContext;

    public static PeopleDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PeopleDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PeopleDatabase.class, "people_db")
                            .allowMainThreadQueries()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                    sContext = context;
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final PeopleDao mDao;

        PopulateDbAsync(PeopleDatabase db) {
            mDao = db.peopleDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            Cursor cursor = sContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);
            while (cursor != null && cursor.moveToNext()) {
                People people = new People();
                people.name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                people.contact = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                people.email = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));

                mDao.insert(people);
            }

            if (cursor != null)
            cursor.close();
            return null;
        }
    }
}
