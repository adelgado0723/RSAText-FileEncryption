import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Stack;

public class RSA {

	BigInteger p, q, n, e, d, phi;

	// given large prime numbers p and q
	// we create an object that creates the
	// encryption and decryption keys.
	public RSA(BigInteger p, BigInteger q) {
		this.p = p;
		this.q = q;
		generateKeys();
	}

	public BigInteger[] getPublicKey() {
		BigInteger[] publicKey = { e, n };
		return (publicKey);
	}

	public BigInteger[] getPrivateKey() {
		BigInteger[] privateKey = { d, n };
		return (privateKey);
	}

	private void generateKeys() {
		n = p.multiply(q);
		// phi = (p-1)(q-1)
		phi = p.subtract(BigInteger.valueOf(1));
		phi = phi.multiply(q.subtract(BigInteger.valueOf(1)));
		e = chooseE();
		d = chooseD();
	}

	private BigInteger chooseE() {

		for (int i = n.intValue()-1; i > 0; i--) {
			BigInteger temp1 = euclid(BigInteger.valueOf(i), phi);

			if (temp1.intValue() == 1)
				return BigInteger.valueOf(i);
		}
		return BigInteger.valueOf(-1);
	}

	private BigInteger chooseD() {
		// this can be done using the extended Euclidean Algorithm
		BigInteger[] findD = extendedEuclid(e, phi);

		while (findD[1].compareTo(BigInteger.valueOf(0)) == -1 || findD[1].compareTo(e) == 0) {
			findD[1] = findD[1].add(phi);
		}

		return findD[1];
	}

	public static String encryptMessage(String message, BigInteger e, BigInteger n) {

		String longValues = "";

		for (int i = 0; i < message.length(); i++) {
			// turning the characters to numbers
			BigInteger encoded = BigInteger.valueOf((long) message.charAt(i));
			// Using algorithm 5 from section 3.6 of the book
			// for fast modular exponentiation
			encoded = modExponentiate(encoded, e, n);

			longValues += encoded.toString() + " ";
			System.out.println("Long Values-- " + message.charAt(i) + " = " + encoded + "  encoded = " + encoded);

		}
		return longValues;
	}

	public static String decryptMessage(String message, BigInteger d, BigInteger n) {

		String decrypted = "";
		BigInteger decoded;
		//getting rid of spaces and putting integers into an array
		String temp = "";
		ArrayList<BigInteger> encryptedMessage = new ArrayList<BigInteger>();
		
		for(int i = 0; i < message.length(); i++){
			
			if(message.charAt(i) != ' '){
				temp += message.charAt(i);
			}
			else{
				
				long temp2 = Long.parseLong(temp);
				encryptedMessage.add(BigInteger.valueOf(temp2));
				temp = "";
			}
		}
		
		
		for (int i = 0; i < encryptedMessage.size(); i++) {
			// Using algorithm 5 from section 3.6 of the book
			// for fast modular exponentiation
			decoded = modExponentiate(encryptedMessage.get(i), d, n);

			System.out.print("Decoded Values-- " + encryptedMessage.get(i) + "  decoded = " + decoded + " \n");

			decrypted += (char) decoded.intValue();

		}

		return decrypted;
	}

	private static BigInteger modExponentiate(BigInteger base, BigInteger exponent, BigInteger mod) {
		BigInteger x = new BigInteger("1");
		BigInteger y = base;

		while (exponent.intValue() > 0) {
			if ((exponent.mod(BigInteger.valueOf(2))).equals(BigInteger.valueOf(1))) {
				x = (x.multiply(y)).mod(mod);
			}
			y = (y.multiply(y)).mod(mod);
			exponent = exponent.divide(BigInteger.valueOf(2));

		}

		return x.mod(mod);
	}

	private static BigInteger[] extendedEuclid(BigInteger a, BigInteger b)

	{
		BigInteger[] ans = new BigInteger[3];
		BigInteger q;

		if (b.equals(
				BigInteger.valueOf(0))) { /* If b = 0, then we're done... */
			ans[0] = a;
			ans[1] = BigInteger.valueOf(1);
			ans[2] = BigInteger.valueOf(0);
		} else { /* Otherwise, make a recursive function call */
			q = a.divide(b);
			ans = extendedEuclid(b, a.mod(b));
			BigInteger temp = ans[2].multiply(q);
			temp = ans[1].subtract(temp);

			ans[1] = ans[2];
			ans[2] = temp;
		}

		return ans;
	}

	private static BigInteger euclid(BigInteger a, BigInteger b) {
		BigInteger d;

		if (b.equals(
				BigInteger.valueOf(0))) { /* If b = 0, then we're done... */
			d = a;
		} else { /* Otherwise, make a recursive function call */
			d = euclid(b, a.mod(b));
		}

		return d;
	}


}
