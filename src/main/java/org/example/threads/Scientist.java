package org.example.threads;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.example.utils.RobotParts;

import java.util.*;


@Getter
public class Scientist extends Thread{

    private String threadName;
    private volatile Map<RobotParts, Integer> listOfParts;
    private volatile List<RobotParts> unsortedRobotParts;
    private Minion linkedMinion;

    public Scientist(String threadName, JunkYard junkYard){
        this.threadName = threadName;
        this.listOfParts = new HashMap<>();
        this.unsortedRobotParts = new ArrayList<>();
        Arrays.stream(RobotParts.values()).forEach(part-> listOfParts.put(part, 0));
        this.linkedMinion = new Minion(this, junkYard);
    }

    public synchronized void putRobotParts(List<RobotParts> list){
        unsortedRobotParts.addAll(list);
        notifyAll();
    }

    private synchronized void sortRobotParts() throws InterruptedException {
        unsortedRobotParts.forEach(part-> listOfParts.put(part, listOfParts.get(part) + 1));
        wait();
    }

    public void run() {
        linkedMinion.start();
        while (linkedMinion.getState() != State.TERMINATED){
            try {
                sortRobotParts();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(this.threadName + " : " + listOfParts);
        //TODO RESULT CALCULATING AND OUTPUT;
    }


}
