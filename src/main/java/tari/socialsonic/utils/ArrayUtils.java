package tari.socialsonic.utils;

public class ArrayUtils {

    public static byte[] mergeByteArrays(byte[] one,byte[]two){
        byte[] combined = new byte[one.length + two.length];
        System.arraycopy(one,0,combined,0,one.length);
        System.arraycopy(two,0,combined,one.length,two.length);
        return combined;
    }
}
