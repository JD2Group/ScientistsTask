package org.example.threads;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.example.Storage;
import org.example.utils.RobotParts;

import java.util.*;
import java.util.stream.Collectors;


@Getter
public class Scientist extends Thread{

    private String threadName;
    private Minion linkedMinion;
    private Storage linkedStorage;
    private int result;

    public Scientist(String threadName, JunkYard junkYard){
        this.threadName = threadName;
        linkedStorage = new Storage();
        linkedMinion = new Minion(this, junkYard, linkedStorage);
    }

    @Override
    public void run() {
        setName(threadName);
        System.out.println(threadName + " started.");
        linkedMinion.start();
        while (!linkedStorage.isStop()){
            try {
                linkedStorage.sortRobotParts();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Map<RobotParts, Integer> listOfParts = linkedStorage.getSortedRobotParts();
        System.out.println(this.threadName + " : " + listOfParts);
        result = listOfParts.values().stream().sorted().collect(Collectors.toList()).get(0);
        System.out.println(this.threadName + " result = " + result);
    }

}
