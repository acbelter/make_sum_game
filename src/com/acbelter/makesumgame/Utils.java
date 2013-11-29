package com.acbelter.makesumgame;

public class Utils {
    public static int[] toOneDimensionArray(int[][] array) {
        int size = getDimension(array);
        if (size == -1) {
            throw new IllegalArgumentException("Parameter must be a square array");
        }

        int[] result = new int[size*size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i*size+j] = array[i][j];
            }
        }
        return result;
    }

    public static int[][] toTwoDimensionArray(int[] array) {
        int size = (int) Math.sqrt(array.length);
        if (size*size != array.length) {
            throw new IllegalArgumentException("Parameter must be a square array");
        }

        int result[][] = new int[size][size];
        for (int i = 0; i < size*size; i++) {
            result[i/size][(i+size)%size] = array[i];
        }
        return result;
    }

    /**
     * Return -1 if parameter array isn't a square array.
     */
    public static int getDimension(int[][] array) {
        int size = array.length;
        for (int i = 0; i < size; i++) {
            if (array[i].length != size) {
                return -1;
            }
        }
        return size;
    }
}
