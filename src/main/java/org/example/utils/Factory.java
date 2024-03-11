package org.example.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Factory {

    private List<RobotParts> robotPartsList = new ArrayList<>();

    public void dropSomeParts(int count) {
        synchronized (robotPartsList) {
            System.out.println("Factory drop: " + count);
            IntStream.range(0, count).forEach(i ->
                    robotPartsList.add(RobotParts.values()[Constants.random.nextInt(RobotParts.values().length)]));
            System.out.println("Robot parts on junk yard: " + robotPartsList);
        }
    }

    public RobotParts provideRobotPart(){
        synchronized (robotPartsList) {
            if (robotPartsList.isEmpty()) {
                return null;
            }
            int index = Constants.random.nextInt(robotPartsList.size());
            RobotParts part = robotPartsList.get(index);
            robotPartsList.remove(index);
            return part;
        }
    }

}
