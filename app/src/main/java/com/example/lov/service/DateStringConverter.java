package com.example.lov.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateStringConverter {

    public Date getDate(String strin)throws ParseException{

        Date date = new SimpleDateFormat("dd-MM-yyyy").parse(strin);
        return date;
    }

    public String getString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = dateFormat.format(date);
        return strDate;
    }

}
