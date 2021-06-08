package ru.spbu.mas;

public class refuelingStation {
    private static final int time = 3600000; // 1 hour in ms
    private static final double count = 5000.0; // 1 ton
    private double speed = count/time; // speed in ton/ms
    public int addRefuelingTime(int myTime, double m){
        return (int)(speed*m+myTime);
    }
}
