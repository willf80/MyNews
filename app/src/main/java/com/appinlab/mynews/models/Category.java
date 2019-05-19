package com.appinlab.mynews.models;

import java.io.Serializable;

public class Category implements Serializable {
    private String mLibelle;
    private boolean mChecked;

    public Category() {
    }

    public Category(String libelle) {
        mLibelle = libelle;
    }

    public String getLibelle() {
        return mLibelle;
    }

    public void setLibelle(String libelle) {
        mLibelle = libelle;
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        mChecked = checked;
    }
}
