package com.view.rubbledumpsterrental.main;


import com.view.rubbledumpsterrental.model.entities.RubbleDumpster;

import static com.view.rubbledumpsterrental.model.entities.RubbleDumpsterStatus.*;


public class Main {
    public static void main(String[] args) {

        RubbleDumpster rubbleDumpster = new RubbleDumpster(1,50.0, 100.0, AVAILABLE);
        System.out.println(rubbleDumpster.toString());;


    }
}