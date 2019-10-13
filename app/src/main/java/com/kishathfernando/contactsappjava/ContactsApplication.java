package com.kishathfernando.contactsappjava;

import android.app.Application;

import com.kishathfernando.contactsappjava.domain.repository.PeopleRepository;

public class ContactsApplication extends Application {
    private PeopleRepository mPeopleRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        mPeopleRepository = new PeopleRepository(this);
    }

    public PeopleRepository getPeopleRepository() {
        return mPeopleRepository;
    }
}
