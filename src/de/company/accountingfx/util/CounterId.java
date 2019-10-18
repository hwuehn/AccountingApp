package de.company.accountingfx.util;

public class CounterId {
    private static Integer counter = 0;

    public CounterId() {
        counter++;
    }

    public Integer getCounter() {
        return counter;
    }

}
