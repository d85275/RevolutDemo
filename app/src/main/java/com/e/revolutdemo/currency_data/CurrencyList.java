package com.e.revolutdemo.currency_data;

import com.blongho.country_data.World;
import com.e.revolutdemo.R;

import java.util.ArrayList;


public class CurrencyList {

    public ArrayList<Currency> getList() {
        ArrayList<Currency> arrayList = new ArrayList<>();
        World.getFlagOf(749);
        arrayList.add(new Currency("EUR", R.string.EUR_des, R.drawable.euro));
        arrayList.add(new Currency("AUD", R.string.AUD_des, World.getFlagOf("AS")));
        arrayList.add(new Currency("BGN", R.string.BGN_des, World.getFlagOf("BE")));
        arrayList.add(new Currency("BRL", R.string.BRL_des, World.getFlagOf("BR")));
        arrayList.add(new Currency("CAD", R.string.CAD_des, World.getFlagOf("CA")));
        arrayList.add(new Currency("CHF", R.string.CHF_des, World.getFlagOf("Switzerland")));
        arrayList.add(new Currency("CNY", R.string.CNY_des, World.getFlagOf("China")));
        arrayList.add(new Currency("CZK", R.string.CZK_des, World.getFlagOf("cz")));
        arrayList.add(new Currency("DKK", R.string.DKK_des, World.getFlagOf("Denmark")));
        arrayList.add(new Currency("GBP", R.string.GBP_des, World.getFlagOf("united kingdom")));
        arrayList.add(new Currency("HKD", R.string.HKD_des, World.getFlagOf("hk")));
        arrayList.add(new Currency("HRK", R.string.HRK_des, World.getFlagOf("hr")));
        arrayList.add(new Currency("HUF", R.string.HUF_des, World.getFlagOf("HU")));
        arrayList.add(new Currency("IDR", R.string.IDR_des, World.getFlagOf("id")));
        arrayList.add(new Currency("ILS", R.string.ILS_des, World.getFlagOf("IS")));
        arrayList.add(new Currency("INR", R.string.INR_des, World.getFlagOf("IN")));
        arrayList.add(new Currency("ISK", R.string.ISK_des, World.getFlagOf("iceland")));
        arrayList.add(new Currency("JPY", R.string.JPY_des, World.getFlagOf("jp")));
        arrayList.add(new Currency("KRW", R.string.KRW_des, World.getFlagOf("South Korea")));
        arrayList.add(new Currency("MXN", R.string.MXN_des, World.getFlagOf("MX")));
        arrayList.add(new Currency("MYR", R.string.MYR_des, World.getFlagOf("MY")));
        arrayList.add(new Currency("NOK", R.string.NOK_des, World.getFlagOf("NO")));
        arrayList.add(new Currency("NZD", R.string.NZD_des, World.getFlagOf("NZ")));
        arrayList.add(new Currency("PHP", R.string.PHP_des, World.getFlagOf("ph")));
        arrayList.add(new Currency("PLN", R.string.PLN_des, World.getFlagOf("PL")));
        arrayList.add(new Currency("RON", R.string.RON_des, World.getFlagOf("RO")));
        arrayList.add(new Currency("RUB", R.string.RUB_des, World.getFlagOf("RS")));
        arrayList.add(new Currency("SEK", R.string.SEK_des, World.getFlagOf("SWe")));
        arrayList.add(new Currency("SGD", R.string.SGD_des, World.getFlagOf("SN")));
        arrayList.add(new Currency("THB", R.string.THB_des, World.getFlagOf("TH")));
        arrayList.add(new Currency("TRY", R.string.TRY_des, World.getFlagOf("TUr")));
        arrayList.add(new Currency("USD", R.string.USD_des, World.getFlagOf("USA")));
        arrayList.add(new Currency("ZAR", R.string.ZAR_des, World.getFlagOf("south africa")));
        return arrayList;
    }

}
