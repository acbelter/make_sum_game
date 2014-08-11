/*
 * Copyright 2014 acbelter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.acbelter.makesumgame;

public class Utils {
    public static final boolean DEBUG_MODE = true;
    public static final String DEBUG_TAG = "MSG_DEBUG";

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
