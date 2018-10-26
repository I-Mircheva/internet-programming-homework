

package org.elsys.netprog.model;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerator {

    // Reference: https://stackoverflow.com/a/41762/7680845
    public static String idGen() {
        return UUID.randomUUID().toString();
    }

    public static Integer secretGen() {
        Integer number = ThreadLocalRandom.current().nextInt(1023, 9877);
        while (!hasDistinctDigits(number)) {
            number = ThreadLocalRandom.current().nextInt(1023, 9877);
        }
        System.out.println(number);
       return number;
    }

    // Reference: https://stackoverflow.com/a/26748946/7680845
    public static boolean hasDistinctDigits(int number) {
        int numMask = 0;
        int numDigits = (int) Math.ceil(Math.log10(number+1));
        for (int digitIdx = 0; digitIdx < numDigits; digitIdx++) {
            int curDigit = (int)(number / Math.pow(10,digitIdx)) % 10;
            int digitMask = (int)Math.pow(2, curDigit);
            if ((numMask & digitMask) > 0) return false;
            numMask = numMask | digitMask;
        }
        return true;
    }
}