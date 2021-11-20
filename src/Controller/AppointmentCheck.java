package Controller;

import java.time.LocalDateTime;

public class AppointmentCheck {

    public static Boolean startWindowCheck (LocalDateTime newMeetingStart, LocalDateTime newMeetingEnd,
                                            LocalDateTime checkMeetingStart, LocalDateTime checkMeetingEnd)
    {
        Boolean check = true;
           if((newMeetingStart.isAfter(checkMeetingStart)||newMeetingStart.isEqual(checkMeetingStart))
               &&
                   (newMeetingStart.isBefore(checkMeetingEnd))
           ){
               check = false;
               return false;
           }
           else {
               return true;
           }
    }
    public static Boolean endWindowCheck (LocalDateTime newMeetingStart, LocalDateTime newMeetingEnd,
                                            LocalDateTime checkMeetingStart, LocalDateTime checkMeetingEnd)
    {
        if((newMeetingEnd.isAfter(checkMeetingStart)) && (newMeetingEnd.isBefore(checkMeetingEnd) ||
                newMeetingEnd.isEqual(checkMeetingEnd)) )
        {
            return false;
        }

        return true;
    }

    public static Boolean outsideWindowCheck (LocalDateTime newMeetingStart, LocalDateTime newMeetingEnd,
                                          LocalDateTime checkMeetingStart, LocalDateTime checkMeetingEnd)
    {
        if((newMeetingStart.isBefore(checkMeetingStart)||newMeetingStart.isEqual(checkMeetingStart))
                &&
                (newMeetingEnd.isAfter(checkMeetingEnd) || newMeetingEnd.isEqual(checkMeetingEnd)))
        {
            return false;
        }

        return true;
    }

    /**
     * Lambda expression used
     * @param timeNow
     * @param checkMeetingStart
     * @return
     */
    public static Boolean loginMeetingCheck (LocalDateTime timeNow, LocalDateTime checkMeetingStart)
    {
        GeneralInterface test = ldtTime -> ldtTime.plusMinutes(15);
       // System.out.println(test.addTimeMinutes(LocalDateTime.now()));
        LocalDateTime ldtWindow = test.addTimeMinutes(timeNow);
        if((checkMeetingStart.isEqual(timeNow) || (checkMeetingStart.isAfter(timeNow) &&
                (checkMeetingStart.isBefore(ldtWindow)||checkMeetingStart.isEqual(ldtWindow))
        )))
        {
            return true;
        }
        return false;


    }


}
