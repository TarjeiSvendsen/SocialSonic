package tari.socialsonic.filesystem;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tari.socialsonic.utils.filesystem.Scanner;


public class FileSystemTests {
    @Nested
    class FileMetadataTests{

        @Test
        void checkFileHeader(){
            Scanner scanner = new Scanner();
            scanner.scanRoot();
        }
    }
}
