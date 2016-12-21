
/**
 * A "CipherKey" object consistent with AES terminology.
 * Represents a 128/192/256-bit key and the subsequent
 * 10/12/14 roundkeys, respectively.
 *
 */
public class CipherKey
{
	protected byte[] key;
	protected byte[][] roundKeys;
	protected int keyCols;
	protected int rounds;
	
	public CipherKey(byte[] bytes)
	{
		key = bytes;
		keyCols = key.length / 4;
		rounds = keyCols + 4 + 2;
		roundKeys = null;
		if( key.length != 32 && key.length != 24 && key.length != 16 )
		{
			System.err.println("Invalid key length.");
			System.exit(-2);
		}
	}
	
	/**
	 * Generate Round Keys from key
	 */
	public void generateRoundKeys()
	{
		roundKeys = new byte[(rounds + 1) * 4][4]; //Cols, Rows? hmmmm
		for(int i = 0; i < keyCols; i++)
			for(int j = 0; j < 4; j++ )
				roundKeys[i][j] = key[4 * i + j];

		byte[] col = new byte[4];
		for( int i = 0; i < 4; i++)
			col[i] = key[4 * keyCols + (i-4)];

		for(int i = keyCols; i < (rounds + 1) * 4; )
		{
			byte temp = col[0];
			col[0] = col[1];
			col[1] = col[2];
			col[2] = col[3];
			col[3] = temp;
			
			col = subBytes(col);
			
			for(int j = 0; j < 4; j++)	
				roundKeys[i][j] = col[j] = (byte) (roundKeys[i-keyCols][j] ^ col[j] ^
						(byte) (j == 0 ? lookupTables.Rcon[i / keyCols] : 0));
			i++;
			for(int j = 0; j < 3; j++, i++ )
				for(int k = 0; k < 4; k++)
					roundKeys[i][k] = col[k] = (byte) (roundKeys[i-keyCols][k] ^ col[k]);
			
			//ONLY for 256-bit keys:
			if( key.length == 32 && i < 60 )
			{
				col = subBytes(col);
				for(int j = 0; j < 4; j++, i++ )
					for(int k = 0; k < 4; k++)
						roundKeys[i][k] = col[k] = (byte) (roundKeys[i-keyCols][k] ^ col[k]);
			}
		}
	}
	
	/**
	 * Substitutes all bytes with corresponding S-box bytes
	 * @param bytes Byte array to substitute
	 * @return Corresponding substituted byte array
	 */
	private byte[] subBytes(byte[] bytes)
	{
		for(int i = 0; i < bytes.length; i++)
			bytes[i] = (byte) lookupTables.sbox[ (int)(bytes[i] & 0xFF) ];
		return bytes;
	}
	
	/**
	 * Return 16-bit round key for the specified round
	 * @param num Specified round
	 * @return 16-bit array representing the num round key
	 */
	public byte[] getRoundKey(int num)
	{
		byte[] roundKey = new byte[16];
		for(int i = num * 4, k = 0; i < (num + 1) * 4; i++ )
			for(int j = 0; j < 4; j++ )
				roundKey[k++] = roundKeys[i][j];
		return roundKey;
	}
	
	/**
	 * Returns long representative of the key
	 * @return 64-bit long representative of the key
	 */
	public long getSeed()
	{

		long retVal = 0;
		for( int i = 0; i < 8; i++)
			retVal = (retVal << 8) + (key[i] & 0xFF);
		return retVal;
	}
	
	/**
	 * Returns total number of rounds
	 * @return Number of rounds
	 */
	public int getRounds()
	{
		return rounds;
	}
}