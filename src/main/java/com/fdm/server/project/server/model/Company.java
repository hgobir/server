package com.fdm.server.project.server.model;

import com.fdm.server.project.server.entity.Stock;

import java.io.Serializable;


public class Company {

    private Long companyId;
    private String name;
    private String description;
    private String sector;
    private String ceo;
    private String address;
    private Long valuation;
    private Stock stock;


    public Company(Long companyId, String name, String description, String sector, String ceo, String address, Long valuation, Stock stock) {
        this.companyId = companyId;
        this.name = name;
        this.description = description;
        this.sector = sector;
        this.ceo = ceo;
        this.address = address;
        this.valuation = valuation;
        this.stock = stock;
    }

    public Company() {
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getValuation() {
        return valuation;
    }

    public void setValuation(Long valuation) {
        this.valuation = valuation;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }


    @Override
    public String toString() {
        return "Company{" +
                "companyId=" + companyId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", sector='" + sector + '\'' +
                ", ceo='" + ceo + '\'' +
                ", address='" + address + '\'' +
                ", valuation=" + valuation +
                ", stock=" + stock +
                '}';
    }
}
