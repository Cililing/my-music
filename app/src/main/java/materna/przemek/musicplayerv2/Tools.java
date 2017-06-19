package materna.przemek.musicplayerv2;

public class Tools {

    private static final long MILLSEC_IN_HOUR = 3600000;
    private static final long MILLSEC_IN_MIN = 60000;
    private static final long MILLSEC_IN_SEC = 1000;

    public static String millisecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";
        String minString = "";

        int hours = (int) (milliseconds / (MILLSEC_IN_HOUR));
        int min = (int) ((milliseconds % MILLSEC_IN_HOUR)/ MILLSEC_IN_MIN );
        int seconds = (int) (((milliseconds % MILLSEC_IN_HOUR) % MILLSEC_IN_MIN) / MILLSEC_IN_SEC);

        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        if (min < 10) {
            minString = "0" + String.valueOf(min);
        }
        else {
            minString = String.valueOf(min);
        }

        if (seconds < 10) {
            secondsString += "0" + String.valueOf(seconds);
        }
        else {
            secondsString = String.valueOf(seconds);
        }



        finalTimerString = finalTimerString + minString + ":" + secondsString;
        return finalTimerString;
    }

    public static int getDurationPercent(long current, long total) {
        Double pct = (double) 0;

        pct = (double) ((current/total) * 100);
        return pct.intValue();

    }

    public static int progressToTimer(int progress, int totalDuration) {
        int current = 0;
        totalDuration = (int) (totalDuration / MILLSEC_IN_SEC);
        current = (int) ((((double) progress) /100) * totalDuration);
        return (int) (current * MILLSEC_IN_SEC);
    }

}
