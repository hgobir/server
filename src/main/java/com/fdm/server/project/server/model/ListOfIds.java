package com.fdm.server.project.server.model;

import java.util.List;

public class ListOfIds {

    private List<Long> longList;

    public ListOfIds() {
    }

    public ListOfIds(List<Long> longList) {
        this.longList = longList;
    }

    public List<Long> getLongList() {
        return longList;
    }

    public void setLongList(List<Long> longList) {
        this.longList = longList;
    }
}
