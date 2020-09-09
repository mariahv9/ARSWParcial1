package edu.eci.arsw.api.primesrepo;

import edu.eci.arsw.api.primesrepo.model.FoundPrime;
import edu.eci.arsw.api.primesrepo.service.PrimeService;
import edu.eci.arsw.api.primesrepo.service.PrimeServiceStub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Santiago Carrillo
 * 2/22/18.
 */
@RestController
@RequestMapping ("/primes")
public class PrimesController {

    @Autowired
    PrimeService primeService;

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<?> getPrimes() {
        List<FoundPrime> foundPrimes = null;
        try {
            foundPrimes = primeService.getFoundPrimes();
            return new ResponseEntity<>(foundPrimes, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>("ERROR 500", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping (method = RequestMethod.POST)
    public ResponseEntity<?> addPrimes (@RequestBody FoundPrime number) {
        try {
            primeService.addFoundPrime(number);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping (path = "/{primenumber}", method = RequestMethod.GET)
    public ResponseEntity<?> getNumberPrime (@PathVariable String primenumber) {
        try {
            return new ResponseEntity<>(primeService.getPrime(primenumber), HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}