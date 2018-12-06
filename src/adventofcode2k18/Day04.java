package adventofcode2k18;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Day04 {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        ArrayList<String> in = GetInput.get("04.txt");
        //solution part 1
        /*
        //create guard dates
        for (String data : in) {
            GuardDate.addDate(data);
        }
        GuardDate.fillStates();

        //mapping from a guard to its total sleeptime
        HashMap<String, Integer> totalSleep = new HashMap<>();

        //calculate total times for each guard
        for (String key : GuardDate.dates.keySet()) {
            GuardDate gd = GuardDate.dates.get(key);
            if (!totalSleep.containsKey(gd.idGuard)) {
                totalSleep.put(gd.idGuard, gd.getSleepTime());
                continue;
            }
            totalSleep.replace(gd.idGuard, gd.getSleepTime() + totalSleep.get(gd.idGuard));
        }

        //find guard with most sleep
        String highestID = null;
        int sleepTime = 0;
        for (String key : totalSleep.keySet()) {
            int sleep = totalSleep.get(key);
            if (sleep > sleepTime) {
                sleepTime = sleep;
                highestID = key;
            }
        }
        System.out.println(highestID);

        //find minute with most sleeping
        int[] sleepDays = new int[60];
        for (String idDate : GuardDate.dates.keySet()) {
            GuardDate gd = GuardDate.dates.get(idDate);
            if (gd.idGuard.equals(highestID)) {
                for (int i = 0; i < gd.states.length; i++) {
                    if (gd.states[i] == GuardDate.GuardState.ASLEEP) {
                        sleepDays[i]++;
                    }
                }
            }
        }
        int max = 0;
        int index = 0;
        for (int i = 0; i < 60; i++) {
            if (sleepDays[i] > max) {
                max = sleepDays[i];
                index = i;
            }
        }
        System.out.println(index);
        System.out.println("answer: " + (index * Integer.parseInt(highestID.substring(1))));
        */

        //solution part 2
        //create guard dates
        for (String data : in) {
            GuardDate.addDate(data);
        }
        GuardDate.fillStates();

        //find best guard and matching highest minute with most sleeping
        HashMap<String, int[]> totalSleeps = new HashMap<>(); //maps a guard to an array of sleeping amounts per minute
        for (String idDate : GuardDate.dates.keySet()) {
            GuardDate gd = GuardDate.dates.get(idDate);
            String guardID = gd.idGuard;
            int[] sleepDays = (totalSleeps.containsKey(guardID) ? totalSleeps.get(guardID) : new int[60]);
            for (int i = 0; i < gd.states.length; i++) {
                if (gd.states[i] == GuardDate.GuardState.ASLEEP) {
                    sleepDays[i]++;
                }
            }
            totalSleeps.put(guardID, sleepDays);
        }
        String bestGuard = " ";
        int bestSleep = 0;
        int bestMinute = 0;
        for (String guard : totalSleeps.keySet()) {
            int[] sleepDays = totalSleeps.get(guard);
            for (int i = 0; i < sleepDays.length; i++) {
                if (sleepDays[i] > bestSleep) {
                    bestSleep = sleepDays[i];
                    bestMinute = i;
                    bestGuard = guard;
                }
            }
        }
        System.out.println(bestGuard);
        System.out.println(bestMinute);
        System.out.println("answer: " + (bestMinute * Integer.parseInt(bestGuard.substring(1))));
    }

    /**
     * represents a date in the shape of month and day
     * can change its own day to the next day (used for when a guard shift start before 00:00)
     */
    static class Date {
        static ArrayList<Integer> SMALL_MONTHS = new ArrayList<>(Arrays.asList(2, 4, 6, 9, 11));
        int month;
        int day;

        Date(int m, int d) {
            month = m;
            day = d;
        }

        void nextDay() {
            if (day == 31 || (day == 30 && SMALL_MONTHS.contains(month))) {
                day = 1;
                if (month != 12) {
                    month++;
                } else {
                    month = 1;
                }
            } else {
                day++;
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (month < 10) {
                sb.append(0);
            }
            sb.append(month);
            if (day < 10) {
                sb.append(0);
            }
            sb.append(day);
            return sb.toString();
        }
    }

    /**
     * represents a specific day. Contains the guard working that day and the state of the guard at each minute
     * between 00:00 and 00:59
     * contains a static hashmap that maps a date string (mmdd) to a guard date
     */
    static class GuardDate {
        static HashMap<String, GuardDate> dates = new HashMap<>();

        String idGuard;
        String date;
        GuardState[] states = new GuardState[60];

        private GuardDate() {
            states[0] = GuardState.AWAKE;
            states[59] = GuardState.AWAKE;
            for (int i = 1; i < states.length; i++) {
                states[i] = GuardState.UNKNOWN;
            }
        }

        /**
         * used to possibly create a new date
         * if the day of the record string already exists as a guard date
         * add a new guard state depending on the state represented in the string(minute + AWAKE or ASLEEP)
         * or add the guard id if the record is a guard starts shift record
         */
        static void addDate(String record) {
            Date date = new Date(Integer.parseInt(record.substring(6, 8)), Integer.parseInt(record.substring(9, 11)));
            if (!record.substring(12, 14).equals("00")) {
                date.nextDay();
            }
            String idDate = date.toString();
            GuardDate gd = (dates.containsKey(idDate) ? dates.get(idDate) : new GuardDate());
            gd.date = date.toString();
            int minute = Integer.parseInt(record.substring(15, 17));
            switch (record.charAt(19)) {
                case 'f':
                    gd.states[minute] = GuardState.ASLEEP;
                    break;
                case 'w':
                    gd.states[minute] = GuardState.AWAKE;
                    break;
                case 'G':
                    StringBuilder sb = new StringBuilder();
                    int i = 25;
                    while (record.charAt(i) != ' ') {
                        sb.append(record.charAt(i));
                        i++;
                    }
                    gd.idGuard = sb.toString();
                    break;
            }
            dates.put(idDate, gd);
        }

        /**
         * for each guard date fills up the state
         * the state per minute is the state of the previous minute if its state is unknown
         */
        static void fillStates() {
            for (String dateID : dates.keySet()) {
                GuardDate gd = dates.get(dateID);
                GuardState[] states = gd.states;
                for (int i = 0; i < states.length; i++) {
                    if (states[i] == GuardState.UNKNOWN) {
                        states[i] = states[i - 1];
                        continue;
                    }
                }
            }
        }

        /**
         * USED FOR PART 1
         * <p>
         * return the number of minutes the guard has been sleeping
         */
        int getSleepTime() {
            int total = 0;
            for (GuardState gs : states) {
                if (gs == GuardState.ASLEEP) {
                    total++;
                }
            }
            return total;
        }

        /**
         * the three states a guard can be.
         * note that a guard state can never be unknown after fill states is called
         */
        enum GuardState {
            UNKNOWN,
            AWAKE,
            ASLEEP
        }
    }
}