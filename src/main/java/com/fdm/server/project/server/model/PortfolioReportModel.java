package com.fdm.server.project.server.model;

public class PortfolioReportModel {

    private String Symbol;
    private String Name;
    private double Invested;
    private double Units;
    private String Time;
    private double Price;

    public PortfolioReportModel(String symbol, String name, double invested, double units, String time, double price) {
        Symbol = symbol;
        Name = name;
        Invested = invested;
        Units = units;
        Time = time;
        Price = price;
    }

//    public PortfolioReportModel(String symbol, String name, Double totalInvested, String toString, Double lastPerShareTradedPrice) {
//    }

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String symbol) {
        Symbol = symbol;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getInvested() {
        return Invested;
    }

    public void setInvested(double invested) {
        Invested = invested;
    }

    public double getUnits() {
        return Units;
    }

    public void setUnits(double units) {
        Units = units;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    @Override
    public String toString() {
        return "PortfolioReportModel{" +
                "Symbol='" + Symbol + '\'' +
                ", Name='" + Name + '\'' +
                ", Invested=" + Invested +
                ", Units=" + Units +
                ", Time='" + Time + '\'' +
                ", Price=" + Price +
                '}';
    }
}
