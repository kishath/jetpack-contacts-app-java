package com.kishathfernando.contactsappjava.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kishathfernando.contactsappjava.R;
import com.kishathfernando.contactsappjava.adapters.PeoplesListAdapter;
import com.kishathfernando.contactsappjava.domain.models.People;
import com.kishathfernando.contactsappjava.viewModels.PeoplesListViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class PeopleListFragment extends Fragment implements PeoplesListAdapter.OnItemClickListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener{
    private PeoplesListViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private PeoplesListAdapter mPeoplesListAdapter;
    private SearchView mSearchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PeoplesListViewModel.class);
        mPeoplesListAdapter = new PeoplesListAdapter(this);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peoples_list, container, false);
        mRecyclerView = view.findViewById(R.id.peopleRecyclerView);
        FloatingActionButton addFab = view.findViewById(R.id.addFab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_peopleListFragment_to_addPeopleFragment3);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setAdapter(mPeoplesListAdapter);
        mViewModel.getAllPeople().observe(this, new Observer<List<People>>() {
            @Override
            public void onChanged(List<People> people) {
                populatePeopleList(people);
            }
        });
    }

    private void populatePeopleList(List<People> people) {
        mPeoplesListAdapter.setData(people);
    }

    @Override
    public void onItemClick(People people, View itemView) {
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.people_id), people.id);

        Navigation.findNavController(itemView).navigate(R.id.action_peopleListFragment_to_peopleDetailsFragment2, bundle);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_peoples_list, menu);

        mSearchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnCloseListener(this);
    }

    @Override
    public boolean onClose() {
        mViewModel.loadAllPeople();
        mSearchView.onActionViewCollapsed();
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String text) {
        mViewModel.findBy(text);
        return false;
    }
}
