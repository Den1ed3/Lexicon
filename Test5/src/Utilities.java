import java.util.ArrayList;
import java.util.Collections;

public class Utilities {

    /**
     * Checks if the two specified words have the same length and also differ
     * in exactly one letter.
     *
     * @param w1 the first word.
     * @param w2 the second word.
     * @return true or false, whether the two words different by one letter
     * exactly or not.
     */
    public static boolean diffByOneLetter(String w1, String w2) {
        if (w1.length() != w2.length()) return false;

        int diffCount = 0;
        for (int i = 0; i < w1.length(); i++)
            if (w1.charAt(i) != w2.charAt(i)) diffCount++;
        return diffCount == 1;
    }

    /**
     * Sorts the specified list using the bubble-sort algorithm.
     *
     * @param list the list to be sorted.
     * @param <T> the type of the data.
     */
    public static <T extends Comparable<T>> void bubbleSort(ArrayList<T> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (list.get(j).compareTo(list.get(j + 1)) > 0)
                    Collections.swap(list, j, j + 1);
    }

    /**
     * Sorts the specified list using the merge-sort algorithm.
     *
     * @param list the list to be sorted.
     * @param <T> the type of the data.
     */
    public static <T extends Comparable<T>> void mergeSort(ArrayList<T> list) {
        ArrayList<T> left = new ArrayList<>();
        ArrayList<T> right = new ArrayList<>();
        int center;

        if (list.size() > 1) {
            center = list.size() / 2;

            for (int i = 0; i < center; i++)
                left.add(list.get(i));
            for (int i = center; i < list.size(); i++)
                right.add(list.get(i));

            mergeSort(left);
            mergeSort(right);
            merge(left, right, list);
        }
    }

    /**
     * Performs the merge function of the merge-sort algorithm.
     *
     * @param left the left sublist.
     * @param right the right sublist.
     * @param whole the entire list.
     * @param <T> the type of the data.
     */
    private static <T extends Comparable<T>> void merge(ArrayList<T> left, ArrayList<T> right, ArrayList<T> whole) {
        int leftIndex = 0;
        int rightIndex = 0;
        int wholeIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            if ((left.get(leftIndex).compareTo(right.get(rightIndex))) < 0) {
                whole.set(wholeIndex, left.get(leftIndex));
                leftIndex++;
            } else {
                whole.set(wholeIndex, right.get(rightIndex));
                rightIndex++;
            }
            wholeIndex++;
        }

        ArrayList<T> rest;
        int restIndex;
        if (leftIndex >= left.size()) {
            rest = right;
            restIndex = rightIndex;
        } else {
            rest = left;
            restIndex = leftIndex;
        }

        for (int i = restIndex; i < rest.size(); i++) {
            whole.set(wholeIndex, rest.get(i));
            wholeIndex++;
        }
    }
}
