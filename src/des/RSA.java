package des;

import java.math.*;
import java.util.Date;
import java.util.Random;



public class RSA {

	BigInteger n = new BigInteger("0");
	BigInteger e = new BigInteger("0");
	BigInteger d = new BigInteger("0");
	
	void generateKey() {
		// get two prime number p and q
		Random rnd = new Random(new Date().getTime());
		BigInteger p = BigInteger.probablePrime(64, rnd);
		BigInteger q = BigInteger.probablePrime(64, rnd);
		
		// compute n and u(n)
		n = p.multiply(q);
		BigInteger u = (p.subtract(new BigInteger("1"))).multiply(q.subtract(new BigInteger("1")));
		
		// get e and d
		e = new BigInteger("65537");
		d = e.modInverse(u);
	}
	
	String encode(String ms) { // input should be a binary string; output is a binary string
		String output = "";		
		BigInteger m = new BigInteger(ms, 2);
		BigInteger c = m.modPow(e, n);
		output = c.toString(2);
		return output;
	}
	
	String decode(String cs) {// input should be a binary string; output is a binary string
		String output = "";
		BigInteger c = new BigInteger(cs, 2);
		BigInteger m = c.modPow(d, n);
		output = m.toString(2);
		return output;
	}
	
	String getn() {
		String ns = n.toString(2);
		if (ns.length() == 127)
			return "0"+ns;
		else
			return ns;
	}
	
	String gete() {
		return e.toString(2);
	}
	
	String getd() {
		return d.toString(2);
	}
	
	void setd(String newd) {
		d = new BigInteger(newd, 2);
	}
	
	void setn(String newn) {
		n = new BigInteger(newn, 2);
	}
	
	void sete(String newe) {
		e = new BigInteger(newe, 2);
	}
	
	
}
