package org.example.threads;

import lombok.Getter;
import lombok.Setter;
import org.example.utils.Constants;
import org.example.utils.RobotParts;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Minion extends Thread{

    private String threadName;
    private Scientist linkedScientist;
    private JunkYard linkedJunkYard;
    private List<RobotParts> robotPartsList;

    public Minion(Scientist scientist, JunkYard junkYard){
        linkedScientist = scientist;
        linkedJunkYard = junkYard;
        threadName = scientist.getThreadName() + " : minion";
        robotPartsList = new ArrayList<>();
    }

    private void pickUpRobotParts(int count){
        robotPartsList.addAll(linkedJunkYard.pickUpRobotParts(count));
        System.out.println(threadName + " : " + robotPartsList);
    }
    private void putRobotParts(){
        linkedScientist.putRobotParts(robotPartsList);
        robotPartsList.clear();
    }

    @Override
    public void run() {
        System.out.println(threadName + " started.");
        linkedJunkYard.waitList();
        while (linkedJunkYard.getState() != State.TERMINATED){
            int countOfPartToTake = Constants.random.nextInt(4) + 1;
            System.out.println(threadName + " want to take: " + countOfPartToTake + " parts.");
            pickUpRobotParts(countOfPartToTake);
            putRobotParts();
            linkedJunkYard.waitList();
        }
        linkedScientist.finalCalculate();
    }
}
