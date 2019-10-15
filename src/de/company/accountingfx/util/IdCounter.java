package de.company.accountingfx.util;

public class IdCounter {

        private static Integer counter = 0;

        public IdCounter() {
            counter++;
        }

        public Integer getCounter() {
            return counter;
        }
    }