/**
 * Created by thanhnguyencs and vinhnguyentx on 6/29/15.
 */
public class DataCrypto {

    // variable used after indicating the mode
    public static int Nk = 8;
    public static int Nr = 14;
    public static int totalWords = 60;

    private byte[][] state;
    private KeyExpansion keyObj;

    public DataCrypto(String key) {
        keyObj = new KeyExpansion(key);
        state = new byte[4][4];
        // initial state
        for (int i = 0; i < state[0].length; ++i) {
            for (int j = 0;j < state.length; ++j) {
                state[i][j] = 0;
            }
        }
    }

    // input the line containing state into state 2d array
    private void setState(String line) {
        int num = 0;
        // TODO: handle the case when the line is longer or shorter truncate or pad to right with 0
        boolean flag = true;
        for (int i = 0; i < state[0].length && flag; ++i) {
            for (int j = 0;j < state.length && flag; ++j) {
                String twoChars = line.substring(num, num += 2);
                state[j][i] =(byte) ((Character.digit(twoChars.charAt(0), 16) << 4)
                        + Character.digit(twoChars.charAt(1), 16));

                if (line.length() == num) {
                    flag = false;
                }
                if (line.length() == num + 1) {
                    line = line.concat("0");
                }
            }
        }
    }

    public String encrypt(String line) {
        setState(line);
        AESEncrypt e = new AESEncrypt(state, keyObj);
        if (AES.DEBUG)
            e.encryptDB();
        else
            e.encrypt();
        return e.getState();
    }

    public String decrypt(String line) {
        setState(line);
        AESDecrypt d = new AESDecrypt(state, keyObj);
        if (AES.DEBUG)
            d.encryptDB();
        else
            d.encrypt();
        return d.getState();
    }
}
