package org.example;

import lombok.Getter;
import org.example.utils.Constants;
import org.example.utils.Factory;
import org.example.utils.RobotParts;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class JunkYard {

    private final List<Storage> storageList = new ArrayList<>();
    private final Factory factory = new Factory("Factory", this);

    private final List<RobotParts> robotPartsList = new ArrayList<>();

    public List<RobotParts> pickUpRobotParts(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> provideRobotPart())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }

    public void addRobotParts(List<RobotParts> list) {
        synchronized (robotPartsList) {
            robotPartsList.addAll(list);
            System.out.println("Robot parts on junk yard: " + robotPartsList);
        }
    }

    private RobotParts provideRobotPart() {
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

    public synchronized void waitList() {
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void notifyList() {
        notifyAll();
    }

    public void run() {
        storageList.forEach(Storage::activate);

        factory.start();
        try {
            factory.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        storageList.forEach(Storage::deactivate);
        notifyList();

        System.out.println();
        storageList.forEach(Storage::calculateFinalResult);
    }

    public void addStorages(List<Storage> st) {
        storageList.addAll(st);
    }
}
