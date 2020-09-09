package edu.eci.arsw.api.primesrepo.service;

import edu.eci.arsw.api.primesrepo.model.FoundPrime;
import edu.eci.arsw.api.primesrepo.model.PrimeException;

import java.util.List;

/**
 * @author Santiago Carrillo
 * 2/22/18.
 */
public interface PrimeService {
    void addFoundPrime( FoundPrime foundPrime ) throws PrimeException;
    List<FoundPrime> getFoundPrimes();
    FoundPrime getPrime( String prime ) throws PrimeException;
}