/*
   Campbest
   383-f15
a, (byte)0xbb, (byte)0xaa, (byte)0xbb, (byte)0xaa, (byte)0xbb, (byte)0xaa, (byte)0xbb, (byte)0xaa, (byte)0xbb};

   Program to decrypt message

 */


import java.io.*;
import java.nio.*;
import blowfish.Blowfish;

public class Lab1 {
	public static void main(String args[]) throws IOException {
		//get file size
		File fin = new File(args[0]);
		long size = fin.length();

		//read in file
		byte msg[] = new byte[(int)size];
		FileInputStream  fis = new FileInputStream(args[0]);
		int n = fis.read(msg);
		if (n!=size) {
			System.err.println("Error reading file fully");
			System.exit(-1);
		}

		//create key
		for(int i = 0; i<256; i++)
		{		
			//System.out.println(i);
			for(int j = 0; j<256; j++)
			{ 
				
				byte key[] = new byte[] {(byte)i, (byte)j, (byte)i, (byte)j, (byte)i, (byte)j, (byte)i, (byte)j, (byte)i, (byte)j};
	
				//create cipher
				Blowfish cipher = new Blowfish(key);
				//try to decrypt
				try{
				byte[] clear = cipher.decrypt(msg,(int)size);
		
				String finalMessage = new String(clear);
			
				if(finalMessage.contains("Dr. Campbell"))
				{		
					for(int k = 0; k<4; k++)
						System.out.print(i + "" + j);
					System.out.println();
					System.out.println(finalMessage);
				}
				}catch(Exception e){
					//System.out.println(e);
				}
			}
		}
	}
}
