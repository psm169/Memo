package com.bignerdranch.android.memo.Data;

/**
 * Created by realbyte on 2017. 7. 13..
 */

public class CategoriData {
    private String categoriTitle;
    private long id;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {

        return id;
    }

    public String getCategoriTitle()
    {
        return categoriTitle;
    }

    public void setCategoriTitle(String categoriTitle)
    {
        this.categoriTitle = categoriTitle;
    }
}
