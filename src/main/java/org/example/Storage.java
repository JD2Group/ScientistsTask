package org.example;

import org.example.threads.Minion;
import org.example.utils.RobotParts;

import java.util.*;

public class Storage {
    private Map<RobotParts, Integer> sortedRobotParts;
    private List<RobotParts> unsortedRobotParts;
    private boolean stop = false;
    //private int result;



    public Storage(){
        this.sortedRobotParts = new HashMap<>();
        this.unsortedRobotParts = new ArrayList<>();
        Arrays.stream(RobotParts.values()).forEach(part-> sortedRobotParts.put(part, 0));
    }

    public synchronized void putRobotParts(List<RobotParts> list){
        unsortedRobotParts.addAll(list);
        notifyAll();
    }

    public synchronized void sortRobotParts() throws InterruptedException {
        if (!unsortedRobotParts.isEmpty()){
            unsortedRobotParts.forEach(part->
                    sortedRobotParts.put(part, sortedRobotParts.get(part) + 1));
            unsortedRobotParts.clear();
        }
        wait();
    }

    public synchronized void finalCalculate(){
        stop = true;
        notifyAll();
    }

    public void setSortedRobotParts(Map<RobotParts, Integer> sortedRobotParts) {
        this.sortedRobotParts = sortedRobotParts;
    }

    public List<RobotParts> getUnsortedRobotParts() {
        return unsortedRobotParts;
    }

    public void setUnsortedRobotParts(List<RobotParts> unsortedRobotParts) {
        this.unsortedRobotParts = unsortedRobotParts;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public Map<RobotParts, Integer> getSortedRobotParts(){
        return sortedRobotParts;
    }


}
