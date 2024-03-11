package org.example.threads;

import lombok.Getter;
import org.example.utils.Constants;
import org.example.utils.Factory;
import org.example.utils.RobotParts;

import java.util.ArrayList;
import java.util.Arrays;
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
        while (countOfNight != 100){
            countOfNight++;
            int countOfPart = Constants.random.nextInt(4) + 1;
            factory.dropSomeParts(countOfPart);
            notifyList();
            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
