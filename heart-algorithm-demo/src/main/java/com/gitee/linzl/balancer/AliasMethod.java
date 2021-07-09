package com.gitee.linzl.balancer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @description 权重随机算法
 * @url https://www.pianshen.com/article/18091149820/
 * @url https://www.keithschwarz.com/darts-dice-coins/
 */
public final class AliasMethod {
    /**
     * The random number generator used to sample from the distribution.
     */
    private final Random random;
    /**
     * The probability and alias tables.
     */
    private final int[] alias;
    private final double[] probability;

    /**
     * Constructs a new AliasMethod to sample from a discrete distribution and
     * hand back outcomes based on the probability distribution.
     * <p>
     * Given as input a list of probabilities corresponding to outcomes 0, 1,
     * ..., n - 1, this constructor creates the probability and alias tables
     * needed to efficiently sample from this distribution.
     *
     * @param probabilities The list of probabilities.
     */
    public AliasMethod(List<Double> probabilities) {
        this(probabilities, new Random());
    }

    /**
     * Constructs a new AliasMethod to sample from a discrete distribution and
     * hand back outcomes based on the probability distribution.
     * <p>
     * Given as input a list of probabilities corresponding to outcomes 0, 1,
     * ..., n - 1, along with the random number generator that should be used
     * as the underlying generator, this constructor creates the probability
     * and alias tables needed to efficiently sample from this distribution.
     *
     * @param probabilities The list of probabilities. 概率
     * @param random        The random number generator
     */
    public AliasMethod(List<Double> probabilities, Random random) {
        /* Begin by doing basic structural checks on the inputs. */
        if (probabilities == null || random == null) {
            throw new NullPointerException();
        }
        if (probabilities == null || probabilities.size() == 0) {
            throw new IllegalArgumentException("Probability vector must be nonempty.");
        }

        /* Allocate space for the probability and alias tables. */
        probability = new double[probabilities.size()];
        alias = new int[probabilities.size()];

        /* Store the underlying generator. */
        this.random = random;

        /**
         * Make a copy of the probabilities list, since we will be making
         * changes to it.
         */

        List<Double> probabilitiesTmp = new ArrayList<>(probabilities);

        /* Create two stacks to act as worklists as we populate the tables. */
        Deque<Integer> small = new ArrayDeque<>();
        Deque<Integer> large = new ArrayDeque<>();

        /* Populate the stacks with the input probabilities. */
        for (int index = 0, length = probabilitiesTmp.size(); index < length; ++index) {

            probabilitiesTmp.set(index, probabilitiesTmp.get(index) * probabilitiesTmp.size());

            /**
             * If the probability is below the average probability, then we add
             * it to the small list; otherwise we add it to the large list.
             */
            if (probabilitiesTmp.get(index) >= 1.0) {
                large.add(index);
            } else {
                small.add(index);
            }
        }

        /* As a note: in the mathematical specification of the algorithm, we
         * will always exhaust the small list before the big list. However,
         * due to floating point inaccuracies, this is not necessarily true.
         * Consequently, this inner loop (which tries to pair small and large
         * elements) will have to check that both lists aren't empty.
         */
        while (!small.isEmpty() && !large.isEmpty()) {
            /* Get the index of the small and the large probabilities. */
            int less = small.removeLast();
            int more = large.removeLast();

            probability[less] = probabilitiesTmp.get(less);
            alias[less] = more;

            /**
             * 大于等于1.0的,多出部分拿来填充less不足1.0部分
             */
            probabilitiesTmp.set(more, probabilitiesTmp.get(more) - (1.0 - probability[less]));
            /**
             * If the new probability is less than the average, add it into the
             * small list; otherwise add it to the large list.
             */
            if (probabilitiesTmp.get(more) >= 1.0) {
                large.add(more);
            } else {
                small.add(more);
            }
        }

        /**
         * At this point, everything is in one list, which means that the
         * remaining probabilities should all be 1/n. Based on this, set them
         * appropriately. Due to numerical issues, we can't be sure which
         * stack will hold the entries, so we empty both.
         */
        while (!small.isEmpty()) {
            probability[small.removeLast()] = 1.0;
        }
        while (!large.isEmpty()) {
            probability[large.removeLast()] = 1.0;
        }
    }

    /**
     * 返回随机概率命中的下标
     * Samples a value from the underlying distribution.
     *
     * @return A random value sampled from the underlying distribution.
     */
    public int next() {
        /* Generate a fair die roll to determine which column to inspect. */
        int column = random.nextInt(probability.length);
        /* Generate a biased coin toss to determine which option to pick. */
        boolean coinToss = random.nextDouble() < probability[column];
        /* Based on the outcome, return either the column or its alias. */
        return coinToss ? column : alias[column];
    }

    public static void main(String[] args) {
        List<Double> list = new ArrayList<>();
        list.add(new BigDecimal("1").divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP).doubleValue());

        list.add(new BigDecimal("1").divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP).doubleValue());
        list.add(new BigDecimal("1").divide(new BigDecimal("12"), 2, RoundingMode.HALF_UP).doubleValue());
        list.add(new BigDecimal("1").divide(new BigDecimal("12"), 2, RoundingMode.HALF_UP).doubleValue());

        AliasMethod aliasMethod = new AliasMethod(list, ThreadLocalRandom.current());
        int index = 0;
        Map<Integer, Integer> map = new HashMap<>();
        while (index++ < 1000) {
            int idx = aliasMethod.next();
            if (map.containsKey(idx)) {
                map.put(idx, map.get(idx) + 1);
            } else {
                map.put(idx, 1);
            }
        }

        map.keySet().stream().forEach(integer -> {
            System.out.println("次数：" + map.get(integer) + ",:" + list.get(integer));
        });
    }
}