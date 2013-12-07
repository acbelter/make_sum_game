package com.acbelter.makesumgame.game;

import android.util.Log;
import com.acbelter.makesumgame.Utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class FieldGenerator {
    public enum Level {
        EASY, // numbers from [1, 50]
        MEDIUM, // numbers from [50, 99]
        HARD // numbers from [100, 999]
    }

    private FieldGenerator() {}

    public static int[][] generateNewField(int fieldSize, Level level) {
        int[][] field = new int[fieldSize][fieldSize];
        Set<Integer> generatedNumbers = new HashSet<Integer>();
        Random rnd = new Random(System.nanoTime());
        switch (level) {
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
        HashSet<Integer> fieldSet = new HashSet<Integer>(oneDimField.length);
        for (int i = 0; i < oneDimField.length; i++) {
            fieldSet.add(oneDimField[i]);
        }
        Set<Integer> indexes = new HashSet<Integer>(numbers.length);

        int sum;
        outer:
        while (true) {
            rnd.setSeed(System.nanoTime());
            indexes.clear();
            fieldSet.clear();
            sum = 0;

            for (int i = 0; i < numbers.length; i++) {
                int n;
                do {
                    n = rnd.nextInt(oneDimField.length);
                } while (indexes.contains(n));
                numbers[i] = oneDimField[n];
                indexes.add(n);
            }

            for (int i = 0; i < numbers.length; i++) {
                sum += numbers[i];
            }

            if (fieldSet.contains(sum)) {
                continue;
            }

            for (int i = 0; i < oneDimField.length; i++) {
                if (fieldSet.contains(sum-oneDimField[i])) {
                    continue outer;
                }
            }

            break;
        }

        Log.d("DEBUG", "Sum " + sum + " : " + Arrays.toString(numbers));
        return sum;
    }
}
