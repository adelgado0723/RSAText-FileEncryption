import javax.swing.JFileChooser;
import javax.swing.JTextArea;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class BasicFile 
{															//BasicFile.java is a class that assists with opening
	File f, s;												//and closing of files as well as with reading files
															//and in the case of its static method, "saveAs(JTextArea)",
	public final static int BYTES_PER_KILO = 1024;			//it writes to files as well.

	public BasicFile() 
	{
		JFileChooser choose = new JFileChooser(".");
		int status = choose.showOpenDialog(null);

		try 
		{
			if (status != JFileChooser.APPROVE_OPTION)
				throw new IOException();
			f = choose.getSelectedFile();
			if (!f.exists())
				throw new FileNotFoundException();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();		
		}
	}

	void display(String msg, String s) 
	{
		JOptionPane.showMessageDialog(null, msg, s, JOptionPane.ERROR_MESSAGE);
	}
	public String getContent() 
	{															//Reads the content of text files and places them
		String contents = "";									//them in an output string.
		try{
		BufferedReader br = new BufferedReader(new FileReader(f.getPath()));
		
			while(br.ready())
				contents += br.readLine() + "\n";
		
			br.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return contents;

	}
	public File getContents() 
	{
		return f;
	}

	public File getSaveFile()
	{
		return s;
	}

	public long getFileSize()
	{
		return f.length();
	}

	public String getName() 
	{
		return f.getName();
	}

	public String getPath() 
	{
		return f.getAbsolutePath();
	}

	public String getAttributes() 								//Returns an output string with information about
	{															//directories and other files found in the same
		File directory = new File(f.getParent());				//directory as the file that called this method.
		String contents[] = directory.list();
		String status = "";
		status += String.format("%-12s %s\n\n", "Directory:", directory.getAbsolutePath());
 
		for (String i : contents) 
		{
			File f2 = new File(f.getParent() + "/" + i);
			
			if (f2.isDirectory())
			{
				status += String.format("Directory %s\n\n", i);
			} 
			else 
			{
				status += String.format("%-16s %s\n", "File Name: ", i);
				status += String.format("%-20s %s\n", "Path: ", f2.getAbsolutePath());
				status += String.format("%-20s %.2f Kilobytes\n", "Size: ", (double) f2.length() / BYTES_PER_KILO);
				status += String.format("%-19s %d\n\n", "Lines:", textLines(f2));
			}
		}
		return status;

	}

	public int textLines(File countMe)
	{															//Text line counter method.
		int lines = 0;
		try
		{
		BufferedReader reader = new BufferedReader(new FileReader(countMe));
	
		while (reader.readLine() != null)
			lines++;
		
		reader.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return lines;
	}

	public void saveLocationChooser() throws IOException {		//Lets you choose where to save a
		JFileChooser choose = new JFileChooser(".");		    //file using JFileChooser.
		s = new File("");
		int status = choose.showSaveDialog(null);

		if (status != JFileChooser.APPROVE_OPTION)
			throw new IOException();
		s = choose.getSelectedFile();

	}
	
	public boolean findWord(StringTokenizer t, String key) 
	{ 															//Recursively searches for a key word.
		if (!t.hasMoreTokens())
			return false;
		else if (t.nextToken().equals(key))
			return true;
		else
			return findWord(t, key);
	}

	public String searchFor(String key) throws IOException 
	{																		//Returns string containing all occurrences 
																			//of the key word and their line numbers.
		LineNumberReader lnr = new LineNumberReader(new FileReader(f));
		String results = "", toTokenizer = "";

		while ((toTokenizer = lnr.readLine()) != null) 
		{

			StringTokenizer t = new StringTokenizer(toTokenizer);

			if (findWord(t, key))
				results += String.format("%-4d: %s\n", (lnr.getLineNumber()), toTokenizer);

		}
		if (results.equals(""))
			results = String.format("\"%s\" not found in %s\n", key, f.getName());

		lnr.close();
		return results;

	}
	
	public static void saveAs(JTextArea text)								//Static method that takes a JTextArea
	{																		//and facilitates saving it to the 
	    JFileChooser fc = new JFileChooser();								//desired location.
		
	    int returnVal = fc.showSaveDialog(text); 							//Setting parent component to JFileChooser.
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {						//OK button pressed by user
		        File file = fc.getSelectedFile(); 							//get File selected by user
		        try
		        {
		        FileWriter fw = new FileWriter(file);
		        text.write(fw);
		        }
		        catch(IOException exception)
		        {
		        	exception.printStackTrace();
		        }
		       
		}
	}
}