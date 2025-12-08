package tari.socialsonic.utils.filesystem;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Scanner {
    public static int scanRoot(){
        Map<String,String> tags = new HashMap<>();
        try {
            InputStream inputStream = new FileInputStream(""); // Removed for privacy momentarily
            int zeroOccurrence = 0;
            byte[] nextBytes = inputStream.readNBytes(180);
            char[] nextChars = new char[180];
            for (int i = 0; i < nextBytes.length; i++){
                if (nextBytes[i] != 0D){
                    nextChars[i] = (char) nextBytes[i];
                }

            }
            System.out.println(nextChars);
        }
        catch (Exception e){
            System.out.println(e);
        }
        return 0;
    };

    private static String charToString(char[] chars){
        StringBuilder stringBuilder = new StringBuilder();
        for (char c: chars){
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }
}
