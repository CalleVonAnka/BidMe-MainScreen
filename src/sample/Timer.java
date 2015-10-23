package sample;

import java.util.Scanner;

/**
 * Created by Calle Von Anka on 2015-10-22.
 */
public class Timer  {


    public static void countdownTimer(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Timer started");
        int time = scan.nextInt() * 60; // Convert to seconds
        long delay = time * 1000;

        do
        {
            int minutes = time / 60;
            int seconds = time % 60;
            System.out.println(minutes +" minute(s), " + seconds + " second(s)");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            time = time - 1;
            delay = delay - 1000;

        }
        while (delay != 0);
        System.out.println("Time's Up!");
    }


}

