package com.tino.vprv.model;

public class DataModel {
    public int id;
    public String name;

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof DataModel)) return false;
        if (obj == this) return true;
        DataModel other = (DataModel) obj;
        return id == other.id && name.equals(other.name);
    }
}