package org.example.utils;

import java.util.Random;

public interface Constants {

    Random random = new Random();

    int DEFAULT_COUNT_OF_SCIENTISTS_PER_COMP = 2;
    String DEFAULT_COMPETITION_NAME = "Competition";
    int START_COUNT_OF_ROBOT_PARTS = 20;
    int COUNT_OF_NIGHT = 100;
    int TIME_DAY_SWAP = 10;
    int MAX_COUNT_OF_PARTS_PER_NIGHT = 4;
    int MIN_COUNT_OF_PARTS_PER_NIGHT = 1;
}
