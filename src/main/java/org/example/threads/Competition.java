package org.example.threads;

import org.example.JunkYard;
import org.example.Storage;
import org.example.utils.Constants;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Competition extends Thread {

    private final int countOfStorages;

    public Competition(String thName, int countOfStorages) {
        setName(thName);
        this.countOfStorages = countOfStorages;
    }

    public Competition() {
        setName(Constants.DEFAULT_COMPETITION_NAME);
        this.countOfStorages = Constants.DEFAULT_COUNT_OF_STORAGE_PER_COMP;
    }

    @Override
    public void run() {
        JunkYard junkYard = new JunkYard();

        List<Storage> storageList = IntStream.range(0, countOfStorages)
                .mapToObj(i -> new Storage(String.format("%s Storage[%d]", getName(), (i + 1)), junkYard))
                .collect(Collectors.toList());
        junkYard.addStorages(storageList);

        junkYard.run();

        findWinners(storageList);
    }

    private void findWinners(List<Storage> storageList) {
        storageList.sort(Comparator.comparingInt(Storage::getResult));

        List<Storage> winners = new ArrayList<>(storageList);
        winners.removeIf(s -> s.getResult() != storageList.get(countOfStorages - 1).getResult());

        winners.forEach(winner -> System.out.printf("\nWinner: %s, score = %d\n", winner.getName(), winner.getResult()));
        if (winners.isEmpty()) {
            System.out.println("Nobody won");
        }
    }
}
