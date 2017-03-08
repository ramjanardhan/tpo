/*
 * This class is used for password Encryption
 */
package com.mss.tpo.util;
/**
 *
 * @author Narendar
 */
public class PasswordUtil {

    /**
     * Creates a new instance of EncriptDecriptPwd
     */
    public PasswordUtil() {
    }

    //START: Password Encryption method 
    public static String encryptPwd(String srcPwd) {
        System.out.println("srcPwd --> " + srcPwd);
        // Converting String to array
        char asciiArr[] = srcPwd.toCharArray();
        // Finding length and converting it into ascii
        int encryASCIIArr[] = new int[srcPwd.length()];
        for (int i = 0; i < encryASCIIArr.length; i++) {
            int asciiChar = (int) asciiArr[i] + 3;
            int asciiCharMulBy3 = asciiChar * 3;
            encryASCIIArr[i] = asciiCharMulBy3;
        }
        String encryPwd = "";
        for (int j = 0; j < encryASCIIArr.length; j++) {
            encryPwd = encryPwd + "#" + encryASCIIArr[j];
        }
        System.out.println("encryPwd --> " + encryPwd);
        return encryPwd;
    }//END: Password Encryption method

    //START: Password Decryption method
    public static String decryptPwd(String encryPwd) {
        String encryPwdArr[] = encryPwd.split("#");
        String decryptedPwd = "";
        for (int lk = 0; lk < encryPwdArr.length; lk++) {
            if (!encryPwdArr[lk].equalsIgnoreCase("")) {
                int asciiChar = Integer.parseInt(encryPwdArr[lk]);
                int asciiCharDivBy3 = asciiChar / 3;
                int asciiCharSubBy3 = asciiCharDivBy3 - 3;
                decryptedPwd = decryptedPwd + (char) asciiCharSubBy3;
            }
        }
        return decryptedPwd;
    }//END: Password Decryption method

    public static void main(String args[]) {
        System.out.println("Encrypt password-->" + encryptPwd("1234"));
        System.out.println(decryptPwd("#336#333#237#180#174#207#366"));
    }
}
