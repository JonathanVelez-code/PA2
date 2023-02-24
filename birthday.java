import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class birthday {

    private static final Lock lock = new ReentrantLock();
    private static final Condition allEntered = lock.newCondition();
    private static final int guestList = 100;
    private static int counter = 0;
    private static boolean cupcakes = true;

    public static void main(String[] args) throws InterruptedException {

        boolean[] enteredList = new boolean[guestList];

        List<Thread> threads = new ArrayList<>();
        Random rand = new Random(System.currentTimeMillis());

        while(!allGuestsEntered(enteredList)){
            final int currentGuest = rand.nextInt(guestList);
             Thread thread = new Thread(() -> {
                 try {
                     enter(currentGuest,enteredList);
                 } catch (InterruptedException e) {
                     throw new RuntimeException(e);
                 }


            });
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads){
            thread.join();
        }

        for (int i=0; i<guestList; i++){
            System.out.println("Guest: "+ i+ " = "+ enteredList[i]);
        }
    }

    private static boolean allGuestsEntered(boolean[] enteredList) {

        lock.lock();
        try {
            for (boolean entered : enteredList) {
                if (!entered) {
                    return false;
                }
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    private static void enter(int currentGuest, boolean[] enteredList) throws InterruptedException {
        Random rand = new Random(System.currentTimeMillis());
        int randSleep = rand.nextInt(guestList);
        lock.lock();
        if (counter < guestList){
            if(!enteredList[currentGuest]) {
                System.out.println("Guest "+ currentGuest + " tell Minotaur it his first time entering the Minotaur’s labyrinth.");
                enteredList[currentGuest] = true;
                Thread.sleep(randSleep);
                leaveFirstTime(currentGuest);
            }
            else{
                System.out.println("Guest "+ currentGuest + " tell Minotaur he already entered the Minotaur’s labyrinth before.");
                Thread.sleep(randSleep);
                leaveSetup(currentGuest);
            }
        }
        lock.unlock();

    }
    private static void leaveFirstTime(int currentGuest){
        lock.lock();
        if (!cupcakes) {
            cupcakes = true;
            System.out.println("Cupcake was requested");

        }
        cupcakes=false;
        System.out.println("Cupcake gone");
        counter++;
        System.out.printf("Guest %d has left the labyrinth and ate a cupcake. Guests left: %d\n", currentGuest, guestList - counter);
        if (counter == guestList){
            allEntered.signalAll();
        }
        lock.unlock();
    }
    private static void leaveSetup(int currentGuest) {
        lock.lock();
        if(!cupcakes) {
            cupcakes=true; //request cupcake but doesn't eat it
            System.out.println("Cupcake was requested");
        }
        System.out.printf("Guest %d has left the labyrinth. Guests left: %d\n", currentGuest, guestList - counter);
        if (counter == guestList){
            allEntered.signalAll();
        }
        lock.unlock();
    }

}