package com.kishathfernando.contactsappjava.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kishathfernando.contactsappjava.R;
import com.kishathfernando.contactsappjava.databinding.FragmentPeopleDetailsBinding;
import com.kishathfernando.contactsappjava.domain.models.People;
import com.kishathfernando.contactsappjava.viewModels.PeoplesDetailViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class PeopleDetailsFragment extends Fragment {

    private FragmentPeopleDetailsBinding mBinding;
    private PeoplesDetailViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PeoplesDetailViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_people_details, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            int peopleId = getArguments().getInt(getString(R.string.people_id));
            mViewModel.getPeople(peopleId).observe(this, new Observer<People>() {
                @Override
                public void onChanged(People people) {
                    mBinding.setPeople(people);
                }
            });
        }
    }
}
