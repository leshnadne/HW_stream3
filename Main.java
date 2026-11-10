package hw3;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final AtomicInteger counterLength3 = new AtomicInteger(0);
    private static final AtomicInteger counterLength4 = new AtomicInteger(0);
    private static final AtomicInteger counterLength5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindromeThread = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text) && !isSameLetter(text)) {
                    increamentCounter(text.length());
                }
            }
        });

        Thread sameLetterThread = new Thread(() -> {
            for (String text : texts) {
                if (isAscending(text) && !isSameLetter(text)) {
                    increamentCounter(text.length());
                }
            }
        });

        Thread ascendingThread = new Thread(() -> {
            for (String text : texts) {
                if (isAscending(text) && !isSameLetter(text)) {
                    increamentCounter(text.length());
                }
            }
        });

        palindromeThread.start();
        sameLetterThread.start();
        ascendingThread.start();

        palindromeThread.join();
        sameLetterThread.join();
        ascendingThread.join();

        System.out.println("Красивых слов с длиной 3: " + counterLength3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + counterLength4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + counterLength5.get() + " шт");

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    private static void increamentCounter(int length) {
        switch (length) {
            case 3 -> counterLength3.incrementAndGet();
            case 4 -> counterLength4.incrementAndGet();
            case 5 -> counterLength5.incrementAndGet();
        }
    }

    private static boolean isPalindrome(String text) {
        int left = 0;
        int right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    private static boolean isSameLetter(String text) {
        char first = text.charAt(0);
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != first) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAscending(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) > text.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

}
