package org.example.threads;

import lombok.Getter;
import org.example.Storage;


@Getter
public class Scientist extends Thread {
    private final Storage linkedStorage;

    private final String threadName;

    public Scientist(String storageName, Storage storage) {
        threadName = storageName + " - Scientist";
        linkedStorage = storage;
    }

    @Override
    public void run() {
        setName(threadName);
        System.out.println(threadName + " started.");
        while (linkedStorage.isActive()) {
            try {
                linkedStorage.sortRobotParts();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(threadName + " stopped.");

    }

}
