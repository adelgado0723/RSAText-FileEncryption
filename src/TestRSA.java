import java.math.BigInteger;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class TestRSA {

	public static void main(String[] args){
		boolean s = false;
		try{
		BigInteger p,q;
		String primes = "(Examples Of Some Large Primes)\n" + Constants.P1.toString() + " "+Constants.P2.toString()+ " "+ Constants.P3.toString() + "\n" +Constants.P4.toString() 
		+" "+ Constants.P5.toString() +" "+ Constants.P6.toString() + "\n"+ Constants.P8.toString() + " "+Constants.P9.toString() + " "+Constants.P10.toString() +"\n";
		p = BigInteger.valueOf(Long.valueOf(JOptionPane.showInputDialog("Please enter a prime number...\n\n" + primes)));
		q = BigInteger.valueOf(Long.valueOf(JOptionPane.showInputDialog("Please enter another prime number...\n\n" + primes)));
		
		RSA test = new RSA(p,q);
		
		BigInteger[] publicKey = test.getPublicKey();
		BigInteger[] privateKey = test.getPrivateKey();
		
		
		JOptionPane.showMessageDialog(null, "public key = "+publicKey[0] + ", " + publicKey[1]  
				+ ";  private key = " + privateKey[0] + ", " + privateKey[1]);
		
		String encryptedMessage = RSA.encryptMessage(JOptionPane.showInputDialog("Please enter a message to encrypt"), publicKey[0], publicKey[1]);
	
	
		JOptionPane.showMessageDialog(null, encryptedMessage);
			
		
//		JOptionPane.showMessageDialog(null, test.decryptMessage(encryptedMessage));
		}
		catch(NumberFormatException e){
			e.printStackTrace();
		}
		
	}
}
