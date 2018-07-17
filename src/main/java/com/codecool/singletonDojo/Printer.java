package com.codecool.singletonDojo;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Printer {

    private static final List<Printer> INSTANCES = new ArrayList<>();

    private int id; // ID of the printer. Unique.
    private LocalTime busyEndTime;

    static Printer getInstance() {
        Random random = new Random();
        if (INSTANCES.isEmpty()) {
            int nextId = 1;
            for (int i = 0; i < 10; i++) {
                INSTANCES.add(new Printer(nextId));
                nextId++;
            }
        }
        if (allIsBusy()) {
            int randomPrinter = random.nextInt(10);
            return INSTANCES.get(randomPrinter);
        }
        while (true) {
            for (Printer printer : INSTANCES) {
                if (printer.isAvailable()) {
                    int chance = random.nextInt(5);
                        if (chance == 3) {
                            return printer;
                        }
                }
            }
        }
    }

    private Printer(int id) {
        this.id = id;
        busyEndTime = LocalTime.now();
    }

    // Prints out the given String
    void print(String toPrint) {
        // Its not needed to actually print with a printer in this exercise
        System.out.println("Printer " + id + " is printing.");
        System.out.println(toPrint);
        busyEndTime = LocalTime.now().plusSeconds(5);
    }

    // Returns true if the printer is ready to print now.
    boolean isAvailable() {
        return LocalTime.now().isAfter(busyEndTime);
    }

    private static boolean allIsBusy() {
        boolean allIsBusy = true;
        for (Printer printer : INSTANCES) {
            if (printer.isAvailable()) {
                allIsBusy = false;
            }
        }
        return allIsBusy;
    }
}
