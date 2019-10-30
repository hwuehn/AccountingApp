package de.company.accountingfx.model.util;

public class CounterId {
    private static Integer counter = 0;

    public CounterId() {
        counter++;
    }

    public Integer getCounter() {
        return counter;
    }

}
