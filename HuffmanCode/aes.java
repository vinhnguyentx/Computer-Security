import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * CS361 HW4: AES implementation using CBC/ECB with
 * support of 128 or 256-bit keys.
 * 
 */
public class AES
{
	protected boolean mode;
	protected CipherKey key;
	protected State state;
	
	public static void main(String[] args)
	{
		boolean keySize = true;
		boolean mode = true;
		String keyFile = "", inputFile = "";
		//java AES option [length] [mode] keyFile inputFile
		//Length defaults to 256-bits, mode defaults to ECB.
		//Key length is actually not required unless keyFile
		//contains less bits than 128/192/256-bits.
		switch( args.length )
		{
			case 3:
			{
				keyFile = args[1];
				inputFile = args[2];
				break;
			}
			case 5: 
			{
				if( args[1].equalsIgnoreCase("-length") )
				{
					keySize = args[2].equalsIgnoreCase("128") ? false : true;
					keyFile = args[3];
					inputFile = args[4];
					break;
				}
				else if( args[1].equalsIgnoreCase("-mode") )
				{
					mode = args[2].equalsIgnoreCase("CBC") ? false : true;
					keyFile = args[3];
					inputFile = args[4];
					break;
				}
			}
			case 7:
			{
				if( args[1].equalsIgnoreCase("-length") && args[3].equalsIgnoreCase("-mode") )
				{
					keySize = args[2].equalsIgnoreCase("256") ? false : true;
					mode = args[4].equalsIgnoreCase("CBC") ? false : true;
					keyFile = args[5];
					inputFile = args[6];
					break;
				}
			}
			default:
			{
				System.err.println("Invalid params: java AES option [length] [mode] keyFile inputFile\n" +
						"[length]: Either \"-length 256\" (default) or \"-length 128\"\n" +
						"[mode]: Either \"-mode ecb\" (default) or \"-mode cbc\"");
				System.exit(-1);
			}
		}
		
		AES a = new AES(keyFile, keySize, mode);
		if( args[0].equalsIgnoreCase("e") )
		{
			long startTime = System.currentTimeMillis();
			long totalBytes = a.encrypt(inputFile);
			long endTime = System.currentTimeMillis();
			if( totalBytes != -1 )
				System.out.println("Encryption Completed. Throughput: " + 
						((double)totalBytes / (1024*1024)) /
						((double)(endTime - startTime) / 1000) + " MB/seconds.");
		}
		else if( args[0].equalsIgnoreCase("d") )
		{
			long startTime = System.currentTimeMillis();
			long totalBytes = a.decrypt(inputFile);
			long endTime = System.currentTimeMillis();
			if( totalBytes != -1 )
				System.out.println("Decryption Completed. Throughput: " + 
						((double)totalBytes / (1024*1024)) /
						((double)(endTime - startTime) / 1000) + " MB/seconds.");
		}
		else
			System.err.println("Invalid option. Must supply either \"e\" or \"d\".");
	}
	
	/**
	 * Creates AES object using key from keyFile for use on a file
	 * @param keyFile File containing single line with hex-based key
	 * @param keySize Size of key in keyFile, not really necessary
	 * @param mode Mode for use in encryption/decryption; Either ECB or CBC
	 */
	public AES(String keyFile, boolean keySize, boolean mode)
	{
		this.mode = mode;
		
		try
		{
			Scanner keyScan = new Scanner(new File(keyFile));
			if( keyScan.hasNextLine() )
			{
				int size = keySize ? 32 : 64;
				String line = keyScan.nextLine();
				for(int i = line.length(); i < size; line+="0", i++) ;
				key = new CipherKey( convertString(line.substring(0, size)) );
			}
			keyScan.close();
		}
		catch (FileNotFoundException e)
		{
			System.err.println("Key File not found");
			System.exit(-3);
		}
	}
	
	/**
	 * Encrypts data line-by-line from File supplied at object creation.
	 * AES implementation, See http://csrc.nist.gov/publications/fips/fips197/fips-197.pdf
	 * CBC initialization vector based on random bytes from key seed
	 * @param file File containing input to encrypt
	 * @return number of bytes processed //True if file encrypted successfully
	 */
	public /*boolean*/long encrypt(String file)
	{
		int rounds = key.getRounds();
		key.generateRoundKeys();
		long totalBytes = 0;
		try
		{
			Scanner sc = new Scanner(new File(file));
			PrintWriter p = new PrintWriter(new File(file + ".enc"));

			byte[] initVector = new byte[16];
			new Random(key.getSeed()).nextBytes(initVector);
			State lastState = new State(initVector);
			while( sc.hasNextLine() )
			{
				String line = sc.nextLine();
				if(! line.matches("[0-9A-Fa-f]+") )
					continue;
				for(int i = line.length(); i < 32; line+="0", i++) ;
				state = new State(convertString(line));
				
				//CBC:
				if( !mode )
					state.xor(lastState);

				//Begin Encryption process
				state.addRoundKey(key, 0);
				for(int i = 1; i < rounds; i++ )
				{
					state.subBytes();
					state.shiftRows();
					state.mixCols();
					state.addRoundKey(key, i);
				}	
				state.subBytes();
				state.shiftRows();
				state.addRoundKey(key, rounds);
				
				p.println(state);
				
				//CBC:
				if( !mode )
					lastState = state;
				totalBytes+=16;
			}
			sc.close();
			p.close();
//			return true;
			return totalBytes;
		}
		catch (FileNotFoundException e)
		{
			System.err.println("Input File not found.");
//			return false;
			return -1;
		}
	}
	
	/**
	 * Decrypts data line-by-line from File supplied at object creation.
	 * AES implementation, See http://csrc.nist.gov/publications/fips/fips197/fips-197.pdf
	 * CBC initialization vector based on random bytes from key seed
	 * @param file File containing input to decrypt
	 * @return number of bytes processed//True if file decrypted successfully
	 */
	public /*boolean*/ long decrypt(String file)
	{
		int rounds = key.getRounds();
		key.generateRoundKeys();
		long totalBytes = 0;
		try
		{
			Scanner sc = new Scanner(new File(file));
			PrintWriter p = new PrintWriter(new File(file + ".dec"));
			
			byte[] initVector = new byte[16];
			new Random(key.getSeed()).nextBytes(initVector);
			State lastState = new State(initVector);
			State tempState = null;
			while( sc.hasNextLine() )
			{
				String line = sc.nextLine();
				for(int i = line.length(); i < 32; line+="0", i++) ;
				state = new State(convertString(line));
				
				//CBC:
				if( !mode )
					tempState = new State(state);

				//Begin Decryption process
				state.addRoundKey(key, rounds);
				state.invShiftRows();
				state.invSubBytes();
				
				for(int i = rounds - 1; i > 0; i-- )
				{
					state.addRoundKey(key, i);
					state.invMixCols(  );
					state.invShiftRows();
					state.invSubBytes();
				}
				state.addRoundKey(key, 0);
				
				//CBC:
				if( !mode )
				{
					state.xor(lastState);
					lastState = tempState;
				}
				p.println(state);
				totalBytes+=16;
			}
			sc.close();
			p.close();
//			return true;
			return totalBytes;
		}
		catch (FileNotFoundException e)
		{
			System.err.println("Input File not found.");
//			return false;
			return -1;
		}
	}
	
	/**
	 * Converts String of hex characters to byte representation
	 * @param data String to parse into byte array
	 * @return byte array representation of string input
	 */
	private byte[] convertString(String data)
	{
		byte[] bytes = new byte[data.length() / 2];
		for(int i = 0, j = 0; i < data.length() / 2; i++)
			bytes[i] = (byte) ((Character.digit(data.charAt(j++), 16) << 4) +
					Character.digit(data.charAt(j++), 16));
		return bytes;
	}
}