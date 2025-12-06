package tari.socialsonic;


import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tari.socialsonic.utils.ArrayUtils;

import static org.junit.jupiter.api.Assertions.*;

@Tag("UnitTests")
public class ArrayUtilTests {

    @Nested
    class MergeByteArrayTests{
        byte[] test1 = "Hello".getBytes();
        byte[] test2 = "World".getBytes();
        byte[] actual = "HelloWorld".getBytes();

        @Test
        void inputOutputMismatch(){
            byte[] combined = ArrayUtils.mergeByteArrays(test1,test2);
            assertArrayEquals(actual, combined,"Input does not match output");
        }
        @Test
        void emptyArray(){
            byte[] test1 = {};
            byte[] test2 = {};
            byte[] combined = ArrayUtils.mergeByteArrays(test1,test2);
            assertArrayEquals(new byte[] {}, combined,"Input does not match output");

        }
    }
}
