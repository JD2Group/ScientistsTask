package org.example.threads;

import lombok.Getter;
import org.example.Storage;


@Getter
public class Scientist extends Thread{

    private String threadName;
    private Storage linkedStorage;
    private int result;

    public Scientist(String storageName, Storage storage){
        threadName = storageName + " - Scientist";
        linkedStorage = storage;
    }

    @Override
    public void run() {
        setName(threadName);
        System.out.println(threadName + " started.");
        while (linkedStorage.isActive()){
            try {
                linkedStorage.sortRobotParts();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
