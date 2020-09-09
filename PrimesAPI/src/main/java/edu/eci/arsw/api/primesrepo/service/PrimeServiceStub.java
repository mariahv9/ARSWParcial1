package edu.eci.arsw.api.primesrepo.service;

import edu.eci.arsw.api.primesrepo.model.FoundPrime;
import edu.eci.arsw.api.primesrepo.model.PrimeException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Santiago Carrillo
 * 2/22/18.
 */
@Service
public class PrimeServiceStub implements PrimeService {
    HashMap<String, FoundPrime> foundPrimeHashMap;

    public PrimeServiceStub (){
        foundPrimeHashMap = new HashMap<String, FoundPrime>();
    }

    @Override
    public void addFoundPrime(FoundPrime foundPrime) throws PrimeException{
        if (!foundPrimeHashMap.containsKey(foundPrime.getPrime())){
            foundPrimeHashMap.put(foundPrime.getPrime(),foundPrime);
        } else {
            throw new PrimeException("Already exists.");
        }
    }

    @Override
    public List<FoundPrime> getFoundPrimes() {
        ArrayList<FoundPrime> primes = new ArrayList<FoundPrime>();
        foundPrimeHashMap.entrySet().forEach((input) ->{
            primes.add(input.getValue());
        });
        return primes;
    }

    @Override
    public FoundPrime getPrime(String prime) throws PrimeException{
        if (!foundPrimeHashMap.containsKey(prime)){
            throw new PrimeException("Not Found");
        } else {
            return foundPrimeHashMap.get(prime);
        }
    }
}