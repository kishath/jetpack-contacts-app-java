package com.kishathfernando.contactsappjava.viewModels;

import android.app.Application;

import com.kishathfernando.contactsappjava.ContactsApplication;
import com.kishathfernando.contactsappjava.domain.models.People;
import com.kishathfernando.contactsappjava.domain.repository.PeopleRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

public class PeoplesListViewModel extends AndroidViewModel {

    private PeopleRepository mPeopleRepository;

    public PeoplesListViewModel(Application application) {
        super(application);
        mAllPeople = new MediatorLiveData<>();
        mPeopleRepository = ((ContactsApplication)application).getPeopleRepository();
        loadAllPeople();
    }

    public LiveData<List<People>> getAllPeople() {
        return mAllPeople;
    }

    private MediatorLiveData<List<People>> mAllPeople;

    public void loadAllPeople() {
        mAllPeople.addSource(mPeopleRepository.getAllPeople(), new Observer<List<People>>() {
            @Override
            public void onChanged(List<People> people) {
                mAllPeople.postValue(people);
            }
        });
    }

    public void findBy(String name) {
        mAllPeople.addSource(mPeopleRepository.findBy(name), new Observer<List<People>>() {
            @Override
            public void onChanged(List<People> people) {
                mAllPeople.postValue(people);
            }
        });
    }
}
