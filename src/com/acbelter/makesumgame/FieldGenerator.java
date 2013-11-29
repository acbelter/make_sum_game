package com.acbelter.makesumgame;

import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class FieldGenerator {
    private static Level mLevel = Level.EASY;

    public enum Level {
        EASY, // numbers from [1, 50]
        MEDIUM, // numbers from [50, 99]
        HARD // numbers from [100, 999]
    }

    public static void setLevel(Level newLevel) {
        mLevel = newLevel;
    }

    public static int[][] generateNewField(int fieldSize) {
        int[][] field = new int[fieldSize][fieldSize];
        Set<Integer> generatedNumbers = new HashSet<Integer>();
        Random rnd = new Random(System.nanoTime());
        switch (mLevel) {
            case EASY: {
                for (int i = 0; i < fieldSize; i++) {
                    for (int j = 0; j < fieldSize; j++) {
                        int n;
                        do {
                            n = rnd.nextInt(50)+1;
                        } while (generatedNumbers.contains(n));
                        field[i][j] = n;
                        generatedNumbers.add(n);
                    }
                }
                break;
            }
            case MEDIUM: {
                for (int i = 0; i < fieldSize; i++) {
                    for (int j = 0; j < fieldSize; j++) {
                        int n;
                        do {
                            n = rnd.nextInt(50)+50;
                        } while (generatedNumbers.contains(n));
                        field[i][j] = n;
                        generatedNumbers.add(n);
                    }
                }
                break;
            }
            case HARD: {
                for (int i = 0; i < fieldSize; i++) {
                    for (int j = 0; j < fieldSize; j++) {
                        int n;
                        do {
                            n = rnd.nextInt(900)+100;
                        } while (generatedNumbers.contains(n));
                        field[i][j] = n;
                        generatedNumbers.add(n);
                    }
                }
                break;
            }
        }
        return field;
    }

    public static int getRandomSum(int[][] field) {
        int size = Utils.getDimension(field);
        Random rnd = new Random(System.nanoTime());
        // Numbers count range: [size-1, 2*(size-1)]
        int[] numbers = new int[rnd.nextInt(size)+(size-1)];
        int[] oneDimField = Utils.toOneDimensionArray(field);
        Set<Integer> indexes = new HashSet<Integer>(numbers.length);
        rnd.setSeed(System.nanoTime());
        for (int i = 0; i < numbers.length; i++) {
            int n;
            do {
                n = rnd.nextInt(oneDimField.length);
            } while (indexes.contains(n));
            numbers[i] = oneDimField[n];
            indexes.add(n);
        }

        int sum = 0;
        for (int i = 0; i < numbers.length; i++) {
            sum += numbers[i];
        }

        Log.d("DEBUG", "Sum " + sum + " : " + Arrays.toString(numbers));
        return sum;
    }
}
