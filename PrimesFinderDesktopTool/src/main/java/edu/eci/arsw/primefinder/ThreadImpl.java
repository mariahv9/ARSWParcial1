package edu.eci.arsw.primefinder;

import edu.eci.arsw.math.MathUtilities;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.util.List;

public class ThreadImpl extends Thread {
    List<BigInteger> numbers;
    PrimesResultSet prs;

    public ThreadImpl (List<BigInteger> numbers, PrimesResultSet prs) {
        this.numbers = numbers;
        this.prs = prs;
    }

    @Override
    public void run (){
        MathUtilities cal = new MathUtilities();
        for (BigInteger number : numbers) {
            synchronized (PrimesFinderTool.monitor) {
                if (PrimesFinderTool.pause) {
                    try {
                        PrimesFinderTool.monitor.wait();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            if (cal.isPrime(number)){
                prs.addPrime(number);
            }
        }
        PrimesFinderTool.finish.incrementAndGet();
    }
}