package com.kishathfernando.contactsappjava.domain.repository;

import android.app.Application;

import com.kishathfernando.contactsappjava.domain.dao.PeopleDao;
import com.kishathfernando.contactsappjava.domain.db.PeopleDatabase;
import com.kishathfernando.contactsappjava.domain.models.People;

import java.util.List;

import androidx.lifecycle.LiveData;

public class PeopleRepository {
    private PeopleDao mPeopleDao;

    public PeopleRepository(Application application) {
        PeopleDatabase db = PeopleDatabase.getDatabase(application);
        mPeopleDao = db.peopleDao();
    }

    public LiveData<List<People>> getAllPeople() {
        return mPeopleDao.getAll();
    }

    public void insertPeople(People people) {
        mPeopleDao.insert(people);
    }

    public LiveData<People> find(int id) {
        return mPeopleDao.find(id);
    }

    public LiveData<List<People>> findBy(String name) {
        return mPeopleDao.findBy(name);
    }
}
