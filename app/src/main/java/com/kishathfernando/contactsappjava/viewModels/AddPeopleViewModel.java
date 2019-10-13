package com.kishathfernando.contactsappjava.viewModels;

import android.app.Application;

import com.kishathfernando.contactsappjava.ContactsApplication;
import com.kishathfernando.contactsappjava.domain.models.People;
import com.kishathfernando.contactsappjava.domain.repository.PeopleRepository;

import androidx.lifecycle.AndroidViewModel;

public class AddPeopleViewModel extends AndroidViewModel {

    private PeopleRepository mPeopleRepository;

    public AddPeopleViewModel(Application application) {
        super(application);
        mPeopleRepository = ((ContactsApplication)application).getPeopleRepository();
    }

    public void addPeople(People people) {
        mPeopleRepository.insertPeople(people);
    }
}
