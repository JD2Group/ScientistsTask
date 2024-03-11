package org.example.threads;

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
public class JunkYard extends Thread{

    private List<Scientist> scientistList = new ArrayList<>();
    private List<Minion> minionList = new ArrayList<>();
    private int countOfNight = 0;
    private Factory factory = new Factory();

    public List<RobotParts> pickUpRobotParts(int count){
        return IntStream.range(0, count)
                .mapToObj(i -> factory.provideRobotPart())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public synchronized void waitList(){
        try {
            wait();
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
    public synchronized void notifyList(){
        notifyAll();
    }

    public void addScientists(List<Scientist> scientists){
        scientistList.addAll(scientists);
    }

    @Override
    public void run() {

        scientistList.forEach(Scientist::start);
        factory.dropSomeParts(Constants.START_COUNT_OF_ROBOT_PARTS);
        while (countOfNight != Constants.COUNT_OF_NIGHT){
            countOfNight++;
            int countOfPart = Constants.random.nextInt(Constants.MAX_COUNT_OF_PARTS_PER_NIGHT
                    -Constants.MIN_COUNT_OF_PARTS_PER_NIGHT + 1)
                    + Constants.MIN_COUNT_OF_PARTS_PER_NIGHT;
            factory.dropSomeParts(countOfPart);
            notifyList();
            try {
                sleep(Constants.TIME_DAY_SWAP);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
