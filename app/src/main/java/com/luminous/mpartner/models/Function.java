package com.luminous.mpartner.models;

public class Function {

    public int Id;

    public Function(int id, String name) {
        Id = id;
        Name = name;
    }

    public String Name;

    @Override
    public String toString() {
        return "" + Name;
    }
}
