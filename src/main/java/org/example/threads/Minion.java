package org.example.threads;

import lombok.Getter;
import org.example.JunkYard;
import org.example.Storage;
import org.example.utils.Constants;
import org.example.utils.Rnd;
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
        threadName = String.format("%s - Minion", storageName);
        backpack = new ArrayList<>();
    }

    private void pickUpRobotParts(int count) {
        backpack.addAll(linkedJunkYard.pickUpRobotParts(count));
        System.out.printf("%s collected: %s\n", threadName, backpack);
    }

    private void putRobotParts() {
        linkedStorage.putRobotParts(backpack);
        backpack.clear();
    }

    @Override
    public void run() {
        setName(threadName);
        System.out.printf("%s started.\n", threadName);
        linkedJunkYard.waitList();
        while (linkedStorage.isActive()) {
            int countOfPartToTake = Rnd.getRandValue(Constants.MAX_COUNT_OF_PARTS_TO_TAKE, Constants.MIN_COUNT_OF_PARTS_TO_TAKE + 1);
            System.out.printf("%s want to take: %d parts.\n", threadName, countOfPartToTake);
            pickUpRobotParts(countOfPartToTake);
            putRobotParts();
            linkedJunkYard.waitList();
        }
        System.out.printf("%s stopped.\n", threadName);
    }

}
