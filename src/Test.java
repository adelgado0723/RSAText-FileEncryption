import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
public class Test {

	public static void main(String[] arg) {

		boolean done = false;
		BasicFile f;
		RSA test;
		String menu = "Enter Option\n\n1. Provide p and q.\nProgram generates keys.\n\n"
				+ "		2. Provide message and encryption key.\nReceive encrypted file.\n\n"
				+ "		3. Provide encrypted message and decryption key.\nReceive decrypted file.\n\n"
				+ "		4. Quit\n";

		do {
			String s = JOptionPane.showInputDialog(menu);
			try{
				int input = Integer.parseInt(s);

			switch (input) {

			case 1:
				//Provide P and Q, 
				//Program generates keys
				BigInteger p,q;
				String primes = "(Examples Of Some Large Primes)\n" + Constants.P1.toString() + " "+Constants.P2.toString()+ " "+ Constants.P3.toString() + "\n" +Constants.P4.toString() 
				+" "+ Constants.P5.toString() +" "+ Constants.P6.toString() + "\n"+ Constants.P8.toString() + " "+Constants.P9.toString() + " "+Constants.P10.toString() +"\n";
				p = BigInteger.valueOf(Long.valueOf(JOptionPane.showInputDialog("Please enter a prime number...\n\n" + primes)));
				q = BigInteger.valueOf(Long.valueOf(JOptionPane.showInputDialog("Please enter another prime number...\n\n" + primes)));
				
				test = new RSA(p,q);
				
				BigInteger[] publicKey = test.getPublicKey();
				BigInteger[] privateKey = test.getPrivateKey();
				
				
				JOptionPane.showMessageDialog(null, "public key = [(encryption exponent = "+publicKey[0] + "), (n = " + publicKey[1]  
						+ ")];\n  private key = [(decryption exponent = " + privateKey[0] + "), (n = " + privateKey[1] + ")]");
				
				break;
			case 2:
				//provide encryption key and message
				//receive encrypted message
				BigInteger e,n;
				String message;
				e = BigInteger.valueOf(Long.valueOf(JOptionPane.showInputDialog("Please enter the encryption exponent, e...\n\n" )));
				n = BigInteger.valueOf(Long.valueOf(JOptionPane.showInputDialog("Please enter n...\n\n" )));
				JOptionPane.showMessageDialog(null, "Please select the file that contains\nthe message to be encrypted...");
				f = new BasicFile();
				JTextArea textArea = new JTextArea();
				BufferedReader br = new BufferedReader(new FileReader(f.getPath()));
				textArea.read(br, true);

				JScrollPane scrollPane = new JScrollPane(textArea);
				textArea.setWrapStyleWord(true);
				scrollPane.setPreferredSize(new Dimension(900, 650));

				JOptionPane.showMessageDialog(null, scrollPane, f.getName(), JOptionPane.INFORMATION_MESSAGE);
				br.close();
				
				message = f.getContent();
				JOptionPane.showMessageDialog(null, "Click Ok to begin encoding...");
				
				String encrypted = RSA.encryptMessage(message, e, n);
				textArea = new JTextArea(encrypted);
				scrollPane = new JScrollPane(textArea);
				textArea.setWrapStyleWord(true);
				scrollPane.setPreferredSize(new Dimension(850, 350));

				JOptionPane.showMessageDialog(null, scrollPane, f.getName(), JOptionPane.INFORMATION_MESSAGE);
				JOptionPane.showMessageDialog(null, "All done!\nPlease choose a save location");
				BasicFile.saveAs(textArea);

				break;
			case 3:
				//provide encryption key and message
				//receive encrypted message
				BigInteger d;
				message = "";
				d = BigInteger.valueOf(Long.valueOf(JOptionPane.showInputDialog("Please enter the decryption exponent, d...\n\n" )));
				n = BigInteger.valueOf(Long.valueOf(JOptionPane.showInputDialog("Please enter n...\n\n" )));
				JOptionPane.showMessageDialog(null, "Please select the file that contains\nthe message to be decrypted...");
				f = new BasicFile();
				textArea = new JTextArea();
				br = new BufferedReader(new FileReader(f.getPath()));
				textArea.read(br, true);

				scrollPane = new JScrollPane(textArea);
				textArea.setWrapStyleWord(true);
				scrollPane.setPreferredSize(new Dimension(850, 350));

				JOptionPane.showMessageDialog(null, scrollPane, f.getName(), JOptionPane.INFORMATION_MESSAGE);
				br.close();
				
				message = f.getContent();
				JOptionPane.showMessageDialog(null, "Click Ok to begin decoding...");
				
				String decrypted = RSA.decryptMessage(message, d, n);
				textArea = new JTextArea(decrypted);
				scrollPane = new JScrollPane(textArea);
				textArea.setWrapStyleWord(true);
				scrollPane.setPreferredSize(new Dimension(900, 650));

				JOptionPane.showMessageDialog(null, scrollPane, f.getName(), JOptionPane.INFORMATION_MESSAGE);
				JOptionPane.showMessageDialog(null, "All done!\nPlease choose a save location");
				BasicFile.saveAs(textArea);
				
				break;
			case 4:
				done = true;
				//quit
				
				break;
			}
			} catch (NumberFormatException | NullPointerException | IOException e) {
				display(e.toString(), "Error");
			}
		} while (!done);
	}
	static void display(String s, String err) {
		JOptionPane.showMessageDialog(null, s, err, JOptionPane.ERROR_MESSAGE);
	}

	static void display(String s) {
		JOptionPane.showMessageDialog(null, s, "Content", JOptionPane.ERROR_MESSAGE);
	}
}
