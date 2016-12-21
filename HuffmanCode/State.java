
/**
 * A "State" object consistent with the AES terminology.
 * Represents a 16-byte, 4-by-4 matrix of the internal
 * state of a cipher undergoing AES.
 *
 */
public class State
{
	private final int stateSize = 16;
	protected byte[] state;
	
	public State(byte[] bytes)
	{
		state = new byte[stateSize];
		for(int i = 0; i < stateSize; i++)
			state[i] = bytes[i];
	}
	public State(State s)
	{
		state = new byte[stateSize];
		for(int i = 0; i < stateSize; i++)
			state[i] = s.state[i];
	}
	
	/**
	 * XORs state with Round Key of the specific round
	 * Accomplishes confusion
	 * @param key Key Object containing array of round keys
	 * @param num The round key number to XOR with
	 */
	public void addRoundKey(CipherKey key, int num)
	{
		byte[] roundKey = key.getRoundKey(num);
		for( int i = 0; i < stateSize; i++ )
			state[i] ^= roundKey[i];
	}
	
	/**
	 * Substitutes all bytes with corresponding S-box bytes
	 * Accomplishes confusion
	 */
	public void subBytes()
	{
		for(int i = 0; i < stateSize; i++)
			state[i] = (byte) lookupTables.sbox[ (int)(state[i] & 0xFF) ];
	}
	
	/**
	 * Shifts bytes in each row by 0,1,2, and 3 respectively
	 * Accomplished diffusion
	 */
	public void shiftRows()
	{
		byte[] newData = new byte[16];
		for(int i = 0; i < stateSize; i++)
			newData[i] = state[ lookupTables.smat[i] ];
		state = newData;
	}
	
	/**
	 * Most complicated step, multiplies state by matrix in GF.
	 * Accomplishes diffusion
	 */
	public void mixCols()
	{
		byte[] newData = new byte[stateSize];
		for(int i = 0; i < 4; i++)
		{
			int a0 = i * 4;
			int a1 = a0 + 1;
			int a2 = a1 + 1;
			int a3 = a2 + 1;
			
			newData[a0] = (byte) ( lookupTables.mult2[ (int) (state[a0] & 0xFF) ] ^
					lookupTables.mult3[ (int) (state[a1] & 0xFF) ] ^ state[a2] ^ state[a3]);
			newData[a1] = (byte) ( state[a0] ^ lookupTables. mult2[ (int) (state[a1] & 0xFF) ] ^
					lookupTables.mult3[ (int) (state[a2] & 0xFF) ] ^ state[a3]);
			newData[a2] = (byte) ( state[a0] ^ state[a1] ^ lookupTables.mult2[ (int) (state[a2] & 0xFF) ] ^
					lookupTables.mult3[ (int) (state[a3] & 0xFF) ]);
			newData[a3] = (byte) ( lookupTables.mult3[ (int) (state[a0] & 0xFF) ] ^ state[a1] ^ state[a2] ^
					lookupTables.mult2[ (int) (state[a3] & 0xFF) ]);
		}
		state = newData;
	}
	
	/**
	 * Inverse step of subBytes
	 */
	public void invSubBytes()
	{
		for(int i = 0; i < stateSize; i++)
			state[i] = (byte) lookupTables.invSbox[ (int)(state[i] & 0xFF) ];
	}
	
	/**
	 * Inverse step of shiftRows
	 */
	public void invShiftRows()
	{
		byte[] newData = new byte[stateSize];
		for(int i = 0; i < 16; i++)
			newData[ lookupTables.smat[i] ] = state[i];
		state = newData;
	}
	
	/**
	 * Inverse step of mixCols
	 */
	public void invMixCols()
	{
		byte[] newData = new byte[stateSize];
		for(int i = 0; i < 4; i++)
		{
			int a0 = i * 4;
			int a1 = a0 + 1;
			int a2 = a1 + 1;
			int a3 = a2 + 1;
			
			newData[a0] = (byte) ( lookupTables.mult14[ (int) (state[a0] & 0xFF) ] ^
					lookupTables.mult11[ (int) (state[a1] & 0xFF) ] ^
					lookupTables.mult13[ (int) (state[a2] & 0xFF) ] ^
					lookupTables.mult9[ (int) (state[a3] & 0xFF) ]);
			newData[a1] = (byte) ( lookupTables.mult9[ (int) (state[a0] & 0xFF) ] ^
					lookupTables.mult14[ (int) (state[a1] & 0xFF) ] ^
					lookupTables.mult11[ (int) (state[a2] & 0xFF) ] ^
					lookupTables.mult13[ (int) (state[a3] & 0xFF) ]);
			newData[a2] = (byte) ( lookupTables.mult13[ (int) (state[a0] & 0xFF) ] ^
					lookupTables.mult9[ (int) (state[a1] & 0xFF) ] ^
					lookupTables.mult14[ (int) (state[a2] & 0xFF) ] ^
					lookupTables.mult11[ (int) (state[a3] & 0xFF) ]);
			newData[a3] = (byte) ( lookupTables.mult11[ (int) (state[a0] & 0xFF) ] ^
					lookupTables.mult13[ (int) (state[a1] & 0xFF) ] ^
					lookupTables.mult9[ (int) (state[a2] & 0xFF) ] ^
					lookupTables.mult14[ (int) (state[a3] & 0xFF) ]);
		}
		state = newData;
	}
	
	/**
	 * XORs this state with another state
	 * @param s Other state to XOR with
	 */
	public void xor(State s)
	{
		for(int i = 0; i < stateSize; i++)
			state[i]^=s.state[i];
	}
	
	/**
	 * Converts State to String representation of hex characters
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for( byte b : state)
			sb.append( Integer.toHexString((b & 0xF0) >> 4 ).toUpperCase() +
					Integer.toHexString(b & 0x0F).toUpperCase());
		return sb.toString();
	}
}