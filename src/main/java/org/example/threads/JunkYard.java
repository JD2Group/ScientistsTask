package org.example.threads;

import org.example.utils.Constants;
import org.example.utils.Factory;
import org.example.utils.RobotParts;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JunkYard extends Thread implements Factory{

    private List<Scientist> scientistList;
    private List<Minion> minionList;
    private int countOfNight = 0;

    public List<RobotParts> pickUpRobotParts(int count){
        return IntStream.range(0, count)
                .mapToObj(i -> provideRobotPart())
                .collect(Collectors.toList());
    }

    @Override
    public void run() {
        while (countOfNight != 100){
            countOfNight++;
            int countOfPart = Constants.random.nextInt(4) + 1;
            dropSomeParts(countOfPart);

        }
    }
}
