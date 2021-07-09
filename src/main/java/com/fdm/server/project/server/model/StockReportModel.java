package com.fdm.server.project.server.model;

public class StockReportModel {

    private String Symbol;
    private String Name;
    private double Value;
    private long Volume;
    private double Gains;

    public StockReportModel(String symbol, String name, double value, long volume, double gains) {
        Symbol = symbol;
        Name = name;
        Value = value;
        Volume = volume;
        Gains = gains;
    }

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

    public double getValue() {
        return Value;
    }

    public void setValue(double value) {
        Value = value;
    }

    public long getVolume() {
        return Volume;
    }

    public void setVolume(long volume) {
        Volume = volume;
    }

    public double getGains() {
        return Gains;
    }

    public void setGains(double gains) {
        Gains = gains;
    }

    @Override
    public String toString() {
        return "StockReportModel{" +
                "Symbol='" + Symbol + '\'' +
                ", Name='" + Name + '\'' +
                ", Value=" + Value +
                ", Volume=" + Volume +
                ", Gains=" + Gains +
                '}';
    }
}
