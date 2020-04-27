package com.e.revolutdemo.models;
import com.e.revolutdemo.currency_data.Rates;

import java.util.HashMap;

public class DataModel {
    String base;
    String date;
    Rates rates;

    public DataModel(String base, String date, Rates rates) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Rates getRates() {
        return rates;
    }

    public void setRates(Rates rates) {
        this.rates = rates;
    }

    public HashMap<String, Double> getMap() {
        HashMap<String, Double> map = new HashMap<>();
        map.put("EUR", 1.0); // EUR is the default base
        map.put("AUD", rates.getAUD());
        map.put("BGN", rates.getBGN());
        map.put("BRL", rates.getBRL());
        map.put("CAD", rates.getCAD());
        map.put("CHF", rates.getCHF());
        map.put("CNY", rates.getCNY());
        map.put("CZK", rates.getCZK());
        map.put("DKK", rates.getDKK());
        map.put("GBP", rates.getGBP());
        map.put("HKD", rates.getHKD());
        map.put("HRK", rates.getHRK());
        map.put("HUF", rates.getHUF());
        map.put("IDR", rates.getIDR());
        map.put("ILS", rates.getILS());
        map.put("INR", rates.getINR());
        map.put("ISK", rates.getISK());
        map.put("JPY", rates.getJPY());
        map.put("KRW", rates.getKRW());
        map.put("MXN", rates.getMXN());
        map.put("MYR", rates.getMYR());
        map.put("NOK", rates.getNOK());
        map.put("NZD", rates.getNZD());
        map.put("PHP", rates.getPHP());
        map.put("PLN", rates.getPLN());
        map.put("RON", rates.getRON());
        map.put("RUB", rates.getRUB());
        map.put("SEK", rates.getSEK());
        map.put("SGD", rates.getSGD());
        map.put("THB", rates.getTHB());
        map.put("TRY", rates.getTRY());
        map.put("USD", rates.getUSD());
        map.put("ZAR", rates.getZAR());
        return map;
    }
}