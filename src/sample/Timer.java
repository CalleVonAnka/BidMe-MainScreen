package sample;

import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil;

import java.util.Scanner;

/**
 * Created by Calle Von Anka on 2015-10-22.
 */
public class Timer  {

    //en timer som räknar ner i konsolen från x sekunder, gjorde om den till int och returnerade men fick aldrig guin till att köras då
    //har en annan timer från min förra commit som du kan testa med, dock så räknar den inte ner i guin. Ganska troligt att det är thread som skapar problem
    static Integer time;
    //static Thread thread = new Thread();
    public static void tjena(int x){
        for (int i = x; i>=0; i--) {
            System.out.println(i);
            time = i;
        }
    }
}



/*ERROR MED UPPDATERING Push failed
Failed with error: fatal: unable to access 'https://github.com/CalleVonAnka/BidMe-MainScreen.git/': Failed connect to github.com:443; No error
Går inte att commita om, så skapar ändring i filen via denna kommentar, ta bort denna kommentar sen!
*/
