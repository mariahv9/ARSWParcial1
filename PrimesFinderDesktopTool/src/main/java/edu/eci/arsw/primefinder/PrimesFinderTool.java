package edu.eci.arsw.primefinder;

import edu.eci.arsw.mouseutils.MouseMovementMonitor;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class PrimesFinderTool {
    public static AtomicInteger finish;
    public static Object monitor = new Object();
    public static boolean pause = false;

    public static ArrayList<ArrayList> divideThreads(BigInteger a, BigInteger b, int threadsCant){
        int cont = 0;
        ArrayList<ArrayList> divideThreads = new ArrayList<>();
        for (int i = 0; i < threadsCant; i++){
            divideThreads.add(new ArrayList<BigInteger>());
        }
        BigInteger i = a;
        while (i.compareTo(b) <= 0){
            divideThreads.get(cont).add(i);
            cont ++;
            if (cont == threadsCant){
                cont = 0;
            }
            i = i.add(BigInteger.ONE);
        }
        return divideThreads;
    }

    public static void pause (){
        if (pause) {
            pause = false;
            synchronized (monitor){
                monitor.notifyAll();
            }
        } else {
            pause = true;
        }
    }

	public static void main(String[] args) {
        finish = new AtomicInteger();
        finish.set(0);
        int maxPrim = 1000;
        int threads = 4;
        ArrayList<ArrayList> numbers = divideThreads (new BigInteger("1"), new BigInteger("10000"), threads);
        PrimesResultSet prs = new PrimesResultSet("john");
        ThreadImpl [] threadsList = new ThreadImpl[threads];
        for (int i = 0; i < threads; i ++){
            threadsList[i] = new ThreadImpl(numbers.get(i), prs);
            threadsList[i].start();
            System.out.println("Hilo" + i);
        }
        while(finish.get() != threads){
            try {
                //check every 10ms if the idle status (10 seconds without mouse
                //activity) was reached.
                Thread.sleep(10);
                if (MouseMovementMonitor.getInstance().getTimeSinceLastMouseMovement() > 10000){
                    if (!pause){

                        pause();
                    }
                    System.out.println("Idle CPU ");

                }else{
                    if (pause) {
                        pause();
                    }
                    System.out.println("User working again!");
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(PrimesFinderTool.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < threads; i ++){
            try {
                threadsList[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Prime numbers found:");
        System.out.println(prs.getPrimes());
	}
}