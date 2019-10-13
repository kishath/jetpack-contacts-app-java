package com.kishathfernando.contactsappjava.viewModels;

import android.app.Application;

import com.kishathfernando.contactsappjava.ContactsApplication;
import com.kishathfernando.contactsappjava.domain.models.People;
import com.kishathfernando.contactsappjava.domain.repository.PeopleRepository;

import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class PeoplesDetailViewModel extends AndroidViewModel {
    private PeopleRepository mPeopleRepository;
    private MutableLiveData<Integer> mPeopleId;

    public PeoplesDetailViewModel(Application application) {
        super(application);
        mPeopleRepository = ((ContactsApplication)application).getPeopleRepository();
        mPeopleId = new MutableLiveData<>();
    }

    public LiveData<People> getPeople(int id) {
        mPeopleId.setValue(id);
        LiveData<People> peopleData = Transformations.switchMap(mPeopleId,
                new Function<Integer, LiveData<People>>() {
                    @Override
                    public LiveData<People> apply(Integer input) {
                        return mPeopleRepository.find(input);
                    }
                });
        return peopleData;
    }
}
