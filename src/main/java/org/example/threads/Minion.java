package org.example.threads;

import lombok.Getter;
import org.example.JunkYard;
import org.example.Storage;
import org.example.utils.Constants;
import org.example.utils.RobotParts;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Minion extends Thread {

    private final JunkYard linkedJunkYard;
    private final Storage linkedStorage;

    private final String threadName;
    private final List<RobotParts> backpack;

    public Minion(String storageName, JunkYard junkYard, Storage storage) {
        linkedJunkYard = junkYard;
        linkedStorage = storage;
        threadName = storageName + " - Minion";
        backpack = new ArrayList<>();
    }

    private void pickUpRobotParts(int count) {
        backpack.addAll(linkedJunkYard.pickUpRobotParts(count));
        System.out.println(threadName + " collected: " + backpack);
    }

    private void putRobotParts() {
        linkedStorage.putRobotParts(backpack);
        backpack.clear();
    }

    @Override
    public void run() {
        setName(threadName);
        System.out.println(threadName + " started.");
        linkedJunkYard.waitList();
        while (linkedStorage.isActive()) {
            int countOfPartToTake = Constants.random.nextInt(Constants.MAX_COUNT_OF_PARTS_TO_TAKE
                    - Constants.MIN_COUNT_OF_PARTS_TO_TAKE + 1)
                    + Constants.MIN_COUNT_OF_PARTS_TO_TAKE;
            System.out.println(threadName + " want to take: " + countOfPartToTake + " parts.");
            pickUpRobotParts(countOfPartToTake);
            putRobotParts();
            linkedJunkYard.waitList();
        }
        System.out.println(threadName + " stopped.");
    }
}
