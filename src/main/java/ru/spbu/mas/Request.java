package ru.spbu.mas;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Request {
    Date date = new Date(); //дата заявки
    SimpleDateFormat dateFormat = new SimpleDateFormat("d.MM.yyyy HH:mm");
    int port = 0; // номер порта
    int pier = 0; // номер причала
    double m = 0.0;
    boolean status = true;
    String type; // тип бункеруемого вещества

    public Request(String date, int port, int pier, double m){
        this.date = transform(date);
        this.port = port;
        this.pier = pier;
        this.m = m;
        show();
    }

    public void show(){
        System.out.println("Получена заявка: "+dateFormat.format(this.date)+" "+this.port+" "+this.pier+" "+this.m);
    }

    public static Date transform(String a)
    {
        SimpleDateFormat myDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String str = a.length() == 0 ? "2000-01-01 10:00" : a;
        Date parsingDate;
        try {
            parsingDate = myDate.parse(str);
            return parsingDate;
        }
        catch(ParseException e){
            System.out.println("Error parsing");
            return null;
        }

    }
}
