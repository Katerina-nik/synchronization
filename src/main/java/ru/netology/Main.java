package ru.netology;

import java.util.*;


public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    public static final int LENGTH_ROUTE = 100;
    public static final int THREADS = 1000;
    public static final String LETTERS = "RLRFR";

    public static void main(String[] args) throws InterruptedException {

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREADS; i++) {
            Thread textThread = new Thread(() -> {
                String texts = generateRoute(LETTERS, LENGTH_ROUTE);
                int amountR = (int) texts.chars().filter(x -> x == 'R').count();

                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(amountR)) {
                        sizeToFreq.put(amountR, sizeToFreq.get(amountR) + 1);

                    } else {
                        sizeToFreq.put(amountR, 1);
                    }
                }
            });

            textThread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

        int maxCount = sizeToFreq.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).get().getKey();
        System.out.printf("Самое частое количество повторений: %d встретился %d раз \n", maxCount, sizeToFreq.get(maxCount));

        System.out.println("Другие размеры:");
        for (Integer key : sizeToFreq.keySet()) {
            int value = sizeToFreq.get(key);
            System.out.println("- " + key + " (" + value + " раз)");
        }
    }

    public static String generateRoute(String letters, int length) {

        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();

    }
}




