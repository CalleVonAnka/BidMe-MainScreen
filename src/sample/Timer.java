package sample;

import java.util.Scanner;

/**
 * Created by Calle Von Anka on 2015-10-22.
 */
public class Timer  {

    //en timer som r�knar ner i konsolen fr�n x sekunder, gjorde om den till int och returnerade men fick aldrig guin till att k�ras d�
    //har en annan timer fr�n min f�rra commit som du kan testa med, dock s� r�knar den inte ner i guin. Ganska troligt att det �r thread som skapar problem

    static Thread thread = new Thread();
    public static void tjena(int x){
        for (int i = x; i>=0; i--) {
            try {
                thread.sleep(1000);
                System.out.println(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}



/*ERROR MED UPPDATERING Push failed
Failed with error: fatal: unable to access 'https://github.com/CalleVonAnka/BidMe-MainScreen.git/': Failed connect to github.com:443; No error
G�r inte att commita om, s� skapar �ndring i filen via denna kommentar, ta bort denna kommentar sen!
*/
