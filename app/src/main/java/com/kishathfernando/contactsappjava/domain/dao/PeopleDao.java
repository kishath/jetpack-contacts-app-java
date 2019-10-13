package com.kishathfernando.contactsappjava.domain.dao;

import com.kishathfernando.contactsappjava.domain.models.People;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PeopleDao {

    @Query("SELECT * FROM People ORDER BY id DESC")
    LiveData<List<People>> getAll();

    @Insert
    void insert(People people);

    @Query("DELETE FROM People")
    void deleteAll();

    @Query("SELECT * FROM People WHERE id = :id")
    LiveData<People> find(int id);

    @Query("SELECT * FROM People WHERE name LIKE '%' || :name || '%'")
    LiveData<List<People>> findBy(String name);
}
