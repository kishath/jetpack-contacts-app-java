package com.kishathfernando.contactsappjava.domain.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class People {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    public String metAt;

    public String contact;

    public String email;

    public String facebook;

    public String twitter;
}