package eu.sim642.idea.zalgofy;

import java.util.Random;
import java.util.function.UnaryOperator;

public class ZalgoOperator implements UnaryOperator<String> {

    private static final Random RANDOM = new Random();

    @Override
    public String apply(String s) {
        double mean = Math.max(1.5, 10.0 / s.length());
        double std = mean / 2;

        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            sb.append(c);
            int max = (int) Math.max(0, RANDOM.nextGaussian() * std + mean);
            for (int i = 0; i < max; i++)
                sb.append(getRandomZalgoChar());
        }

        return sb.toString();
    }

    private static char getRandomZalgoChar() {
        return (char) (0x0300 + RANDOM.nextInt(0x036F - 0x0300));
    }
}
