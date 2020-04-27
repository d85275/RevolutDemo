package com.e.revolutdemo.currency_data;

public class Currency {
    private String name;
    private int desId;
    private int flag;

    public Currency(String name, int desId, int flag) {
        this.name = name;
        this.desId = desId;
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDes() {
        return desId;
    }

    public void setDes(int desId) {
        this.desId = desId;
    }
}
