package org.example.threads;

import lombok.Getter;
import org.example.Storage;
import org.example.utils.Constants;
import org.example.utils.RobotParts;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Minion extends Thread{

    private String threadName;

    private Scientist linkedScientist;
    private JunkYard linkedJunkYard;
    private Storage linkedStorage;

    private List<RobotParts> backpack;

    public Minion(Scientist scientist, JunkYard junkYard, Storage storage){
        linkedScientist = scientist;
        linkedJunkYard = junkYard;
        linkedStorage = storage;
        threadName = scientist.getThreadName() + " : minion";
        backpack = new ArrayList<>();
    }

    private void pickUpRobotParts(int count){
        backpack.addAll(linkedJunkYard.pickUpRobotParts(count));
        System.out.println(threadName + " : " + backpack);
    }
    private void putRobotParts(){
        linkedStorage.putRobotParts(backpack);
        backpack.clear();
    }

    @Override
    public void run() {
        setName(threadName);
        System.out.println(threadName + " started.");
        linkedJunkYard.waitList();
        while (linkedJunkYard.getState() != State.TERMINATED){
            int countOfPartToTake = Constants.random.nextInt(4) + 1;
            System.out.println(threadName + " want to take: " + countOfPartToTake + " parts.");
            pickUpRobotParts(countOfPartToTake);
            putRobotParts();
            linkedJunkYard.waitList();
        }
        linkedStorage.finalCalculate();
    }
}
