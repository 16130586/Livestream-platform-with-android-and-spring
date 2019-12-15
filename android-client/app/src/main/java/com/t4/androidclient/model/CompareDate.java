package com.t4.androidclient.model;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CompareDate {
        public CompareDate(){

        }
        public long compareDateToCurrent(Date date) {
            DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

            Date currentDate = new Date();
            simpleDateFormat.format(currentDate);
            simpleDateFormat.format(date);

            try {

                long getDiff = (currentDate.getTime()) - date.getTime()*1000;
                long getDaysDiff = getDiff / (24 * 60 * 60 * 1000);
                return getDaysDiff;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
    }
}
