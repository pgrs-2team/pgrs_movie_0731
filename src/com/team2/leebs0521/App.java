package com.team2.leebs0521;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        new Theater(MovieManagerImpl.getInstance(),
                ReservationManagerImpl.getInstance(),
                new Scanner(System.in)
        ).run();
    }
}
