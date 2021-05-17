import Utils.Time;

import java.text.ParseException;

public class tryout {

    public static void main(String[] args) throws ParseException {
        String[] dates = {"2020:08:06", "2020:08:07", "2020:08:12","2020:09:01"};
        int[] datesInt = Time.parseDates(dates);
        for (int i = 0; i < datesInt.length; i++) {
            System.out.println(datesInt[i]);
        }
    }
}