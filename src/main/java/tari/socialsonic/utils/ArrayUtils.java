package tari.socialsonic.utils;

public class ArrayUtils {

    /**
     * Merges two arrays, places the first array first, and the second array last
     * @param one first array to copy
     * @param two second array to copy
     * @return an array containing both the first and second array in chronological order
     */
    public static byte[] mergeByteArrays(byte[] one,byte[]two){
        byte[] combined = new byte[one.length + two.length];
        System.arraycopy(one,0,combined,0,one.length);
        System.arraycopy(two,0,combined,one.length,two.length);
        return combined;
    }
}
