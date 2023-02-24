import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class vase {
    private static final Lock lock = new ReentrantLock();
    private static final int guestVisit = 400;
    private static final int guestList = 100;
    private static boolean busy = false;


    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[guestVisit];
        boolean[] enteredList = new boolean[guestList];
        Random rand = new Random(System.currentTimeMillis());

        for (int i = 0; i < guestVisit; i++) {
            final int guestId = rand.nextInt(guestList);
            threads[i] = new Thread(() -> {

                try {
                    enterRoom(guestId, enteredList);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

    }

    private static void enterRoom(int guestId, boolean[] enteredList) throws InterruptedException {
        Random rand = new Random(System.currentTimeMillis());
        int randSleep = rand.nextInt(guestList);
        lock.lock();
        if (!busy) {
            busy = true;
            System.out.println("view the vase by guest: " + guestId );
            Thread.sleep(randSleep);
            enteredList[guestId] = true;
            System.out.println("guest: " + guestId + " is about to step out of the room.");
            busy = false;

        }
        lock.unlock();
    }

}