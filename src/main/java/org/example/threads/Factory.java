package org.example.threads;

import org.example.JunkYard;
import org.example.utils.Constants;
import org.example.utils.Rnd;
import org.example.utils.RobotParts;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Factory extends Thread {
    private final JunkYard linkedJunkYard;
    private int countOfNight = 0;

    public Factory(String thName, JunkYard junkYard) {
        setName(thName);
        linkedJunkYard = junkYard;
    }

    public List<RobotParts> dropSomeParts(int count) {
        System.out.printf("\nFactory drop: %d\n", count);
        return IntStream.range(0, count).mapToObj(i ->
                RobotParts.values()[Constants.RANDOM.nextInt(RobotParts.values().length)])
                .collect(Collectors.toList());
    }

    @Override
    public void run() {
        linkedJunkYard.addRobotParts(dropSomeParts(Constants.START_COUNT_OF_ROBOT_PARTS));
        while (countOfNight != Constants.COUNT_OF_NIGHT) {
            countOfNight++;
            int countOfPart = Rnd.getRandValue(Constants.MAX_COUNT_OF_PARTS_PER_NIGHT, Constants.MIN_COUNT_OF_PARTS_PER_NIGHT);
            linkedJunkYard.addRobotParts(dropSomeParts(countOfPart));
            linkedJunkYard.notifyList();
            waitNextNight();
        }
        System.out.println("\nFactory stopped.");
    }

    private void waitNextNight() {
        try {
            sleep(Constants.TIME_DAY_SWAP);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
