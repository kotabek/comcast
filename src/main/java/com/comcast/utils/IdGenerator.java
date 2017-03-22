package com.comcast.utils;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by kotabek on 3/22/17.
 */
public class IdGenerator {
    private Random random = new Random();

    public String nextId() {
        return new BigInteger(130, random).toString(32);
    }
}
