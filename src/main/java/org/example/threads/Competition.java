package org.example.threads;

import org.example.Storage;
import org.example.utils.Constants;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Competition extends Thread{

    private int countOfStorages;

    public Competition(String thName, int countOfStorages){
        setName(thName);
        this.countOfStorages = countOfStorages;
    }
    public Competition(){
        setName(Constants.DEFAULT_COMPETITION_NAME);
        this.countOfStorages = Constants.DEFAULT_COUNT_OF_STORAGE_PER_COMP;
    }

    @Override
    public void run() {
        JunkYard junkYard = new JunkYard();

        List<Storage> storageList = IntStream.range(0, countOfStorages)
                .mapToObj(i -> new Storage("Storage[" + (i+1) + "]", junkYard))
                .collect(Collectors.toList());
        junkYard.addStorage(storageList);
        junkYard.start();

        try {
            junkYard.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



        storageList.sort(Comparator.comparingInt(Storage::getResult));
        Storage winner = storageList.get(countOfStorages -1);
        System.out.println("\nWinner: " + winner.getName() + ", score = " + winner.getResult());
    }
}
