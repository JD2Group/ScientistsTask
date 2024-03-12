package org.example;

import org.example.threads.Competition;

public class App
{
    public static void main( String[] args ) throws InterruptedException {
        Competition competition1 = new Competition("Comp1", 2);
        //Competition competition2 = new Competition("Comp2", 3);

        competition1.start();
        //competition2.start();

    }
}
