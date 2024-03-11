package org.example.threads;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.example.utils.RobotParts;

import java.util.*;
import java.util.stream.Collectors;


@Getter
public class Scientist extends Thread implements Comparable<Scientist>{

    private String threadName;
    private volatile Map<RobotParts, Integer> listOfParts;
    private volatile List<RobotParts> unsortedRobotParts;
    private Minion linkedMinion;
    private boolean stop = false;
    private int result;

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
        if (!unsortedRobotParts.isEmpty()){
            unsortedRobotParts.forEach(part->
                    listOfParts.put(part, listOfParts.get(part) + 1));
            unsortedRobotParts.clear();
        }
        wait();
    }
    public synchronized void finalCalculate(){
        stop = true;
        notifyAll();
    }

    @Override
    public void run() {
        System.out.println(threadName + " started.");
        linkedMinion.start();
        while (!stop){
            try {
                sortRobotParts();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(this.threadName + " : " + listOfParts);
        result = listOfParts.values().stream().sorted().collect(Collectors.toList()).get(0);
        System.out.println(this.threadName + " result = " + result);
    }


    @Override
    public int compareTo(Scientist o) {
        return Integer.compare(o.result, result);
    }
}
