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
        threadName = scientist.getThreadName();
        robotPartsList = new ArrayList<>();
    }

    private void pickUpRobotPart(){
        robotPartsList.add(linkedJunkYard.provideRobotPart());
    }
    private void putRobotPart(){
        linkedScientist.putRobotParts(robotPartsList);
    }

    public void run() {
        while (linkedJunkYard.getState() != State.TERMINATED){

            int countOfPartToTake = Constants.random.nextInt(4);
            System.out.println(threadName + " want to take: " + countOfPartToTake + " parts.");




        }
    }
}
