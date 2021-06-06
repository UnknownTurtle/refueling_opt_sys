package ru.spbu.mas;
import java.util.Date;

public class Request {
    Date date = new Date(); //дата заявки
    int port = 0; // номер порта
    int pier = 0; // номер причала
    String type; // тип бенкеруемого вещества
    public void printdata()
    {
        String str = String.format("date is %tc", date);
        System.out.println(date.toString());
    }
}
