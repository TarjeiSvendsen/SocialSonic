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


    /**
     * Uses the quicksort algorithm to sort an integer list, not sure where it could be used in this application,
     * but useful training nonetheless
     * @param unsortedArray the unsorted array to be sorted
     */
    public static void quickSort(int[] unsortedArray){
        quickSort(unsortedArray,0,unsortedArray.length-1);
    }
    private static void quickSort(int[] unsortedArray, int from, int to){
        if (to - from <= 0) return;
        int k = partitionByValue(unsortedArray,from,to);

        quickSort(unsortedArray,from,k-1);
        quickSort(unsortedArray,k+1,to);
    }
    private static int partitionByValue(int[] unsortedArray, int from, int to){
        int left = from;
        int right = to -1;
        int pivot = unsortedArray[to];
        while (true){
            while (left <= right && unsortedArray[left] < pivot){
                left++;
            }
            while(left <= right && unsortedArray[right] >= pivot){
                right--;
            }
            if (left > right) break;
            switchValues(unsortedArray,left++,right--);
        }
        switchValues(unsortedArray,left,to);
        return left;
    }

    public static void switchValues(int[] array, int fromIndex, int toIndex){
        int tmp = array[fromIndex];
        array[fromIndex] = array[toIndex];
        array[toIndex] = tmp;
    }
}
