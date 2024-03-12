package org.example;

import lombok.Getter;
import org.example.threads.Minion;
import org.example.threads.Scientist;
import org.example.utils.RobotParts;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Storage {
    private final Minion linkedMinion;
    private final Scientist linkedScientists;
    private final Map<RobotParts, Integer> sortedRobotParts;
    private final List<RobotParts> unsortedRobotParts;
    private final String name;
    private boolean active;
    private int result;

    public Storage(String name, JunkYard junkYard) {
        this.name = name;
        sortedRobotParts = new HashMap<>();
        unsortedRobotParts = new ArrayList<>();
        linkedScientists = new Scientist(this.name, this);
        linkedMinion = new Minion(this.name, junkYard, this);
        Arrays.stream(RobotParts.values()).forEach(part -> sortedRobotParts.put(part, 0));
    }

    public void activate() {
        active = true;
        linkedMinion.start();
        linkedScientists.start();
    }

    public synchronized void deactivate() {
        active = false;
        notifyAll();
    }

    public synchronized void putRobotParts(List<RobotParts> list) {
        System.out.printf("%s - Loading... \n", name);
        unsortedRobotParts.addAll(list);
        notifyAll();
    }

    public synchronized void sortRobotParts() throws InterruptedException {
        System.out.printf("%s - Sorting...\n", name);
        if (!unsortedRobotParts.isEmpty()) {
            unsortedRobotParts.forEach(part ->
                    sortedRobotParts.put(part, sortedRobotParts.get(part) + 1));
            unsortedRobotParts.clear();
        }
        wait();
    }

    public void calculateFinalResult() {
        System.out.printf("%s : %s\n", name, sortedRobotParts);
        result = sortedRobotParts.values().stream()
                .sorted()
                .collect(Collectors.toList()).get(0);
        System.out.printf("%s result = %s\n", name, result);
    }

}
