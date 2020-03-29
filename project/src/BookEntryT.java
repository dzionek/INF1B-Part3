import java.lang.reflect.Array;
import java.util.Arrays;

public class BookEntryT {
    public static void main(String[] args) {
        int success = 0;
        final int NUM_TESTS = 7;
        // Test 1,2,3,4 - null objects
        try {
            BookEntry a = new BookEntry(
                null, new String[] {"John C.", "Elizabeth K."},
                (float) 3.6, "ABC", 250);
        } catch (NullPointerException e) {
            System.out.println("Test 1 (empty title) passed");
            success++;
        }

        try {
            BookEntry a = new BookEntry(
                "Alice", null,
                3.6f, "ABC", 250);
        } catch (NullPointerException e) {
            System.out.println("Test 2 (empty authors array) passed");
            success++;
        }

        try {
            BookEntry a = new BookEntry(
                "Alice", new String[] {"abc", "agd", null},
                3.6f, "ABC", 250);
        } catch (NullPointerException e) {
            System.out.println("Test 3 (empty entries in authors array) passed");
            success++;
        }

        try {
            BookEntry a = new BookEntry(
                "Alice", new String[] {"abc", "agd", "ghi"},
                (float) 3.6, null, 250);
        } catch (NullPointerException e) {
            System.out.println("Test 4 (empty ISBN) passed");
            success++;
        }

        // Test 5,6 - rating out of bounds
        try {
            BookEntry a = new BookEntry(
                "Alice", new String[] {"abc", "agd", "ghi"},
                5.0001f, "abc", 250);
        } catch (IllegalArgumentException e) {
            System.out.println("Test 5 (rating >5) passed");
            success++;
        }

        try {
            BookEntry a = new BookEntry(
                "Alice", new String[] {"abc", "agd", "ghi"},
                -2f, "abc", 250);
        } catch (IllegalArgumentException e) {
            System.out.println("Test 6 (negative rating) passed");
            success++;
        }

        // Test 7 - negative pages
        try {
            BookEntry a = new BookEntry(
                "Alice", new String[] {"abc", "agd", "ghi"},
                -2f, "abc", -3);
        } catch (IllegalArgumentException e) {
            System.out.println("Test 7 (negative pages) passed");
            success++;
        }

        System.out.println(success + "/" + NUM_TESTS + " passed.");

        // ToString

        BookEntry book = new BookEntry(
                "Alice in Wonderland", new String[] {"John C.", "Elizabeth K."},
                3.6f, "ABC", 250
        );

        System.out.println(book);
    }
}
