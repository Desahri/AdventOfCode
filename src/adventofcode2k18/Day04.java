package adventofcode2k18;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Day04 {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        ArrayList<String> in = GetInput.get("04.txt");
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
    }

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

        static void fillStates() {
            for (String dateID : dates.keySet()) {
                GuardDate gd = dates.get(dateID);
                GuardState[] states = gd.states;
                GuardState currentState = GuardState.AWAKE;
                for (int i = 0; i < states.length; i++) {
                    if (states[i] == GuardState.UNKNOWN) {
                        states[i] = currentState;
                        continue;
                    }
                    currentState = states[i];
                }
            }
        }

        int getSleepTime() {
            int total = 0;
            for (GuardState gs : states) {
                if (gs == GuardState.ASLEEP) {
                    total++;
                }
            }
            return total;
        }

        enum GuardState {
            UNKNOWN,
            AWAKE,
            ASLEEP
        }
    }
}