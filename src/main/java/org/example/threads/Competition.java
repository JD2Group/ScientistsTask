package org.example.threads;

import org.example.utils.Constants;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Competition extends Thread{

    private int countOfScientists;

    public Competition(String thName, int countOfScientists){
        setName(thName);
        this.countOfScientists = countOfScientists;
    }
    public Competition(){
        setName(Constants.DEFAULT_COMPETITION_NAME);
        this.countOfScientists = Constants.DEFAULT_COUNT_OF_SCIENTISTS_PER_COMP;
    }

    @Override
    public void run() {
        JunkYard junkYard = new JunkYard();

        List<Scientist> scientistList = IntStream.range(0, countOfScientists)
                .mapToObj(i -> new Scientist( getName() + " - Sc" + (i+1), junkYard))
                .collect(Collectors.toList());
        junkYard.addScientists(scientistList);

        junkYard.start();

        try {
            junkYard.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //TODO Убрать это, но мой ноут сел.
        junkYard.getScientistList().forEach(i->{
            try {
                i.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


        scientistList.sort(Comparator.comparingInt(Scientist::getResult));
        Scientist winner = scientistList.get(countOfScientists-1);
        System.out.println("Winner: " + winner.getThreadName() + ", score = " + winner.getResult());
    }
}
