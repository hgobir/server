package com.fdm.server.project.server.model;

import java.io.Serializable;
import java.util.List;

public class CompanyList implements Serializable {

    private List<Company> companyList;

    public CompanyList() {
    }

    public CompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

    @Override
    public String toString() {
        return "CompanyList{" +
                "companyList=" + companyList +
                '}';
    }
}
