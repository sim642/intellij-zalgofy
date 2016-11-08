package eu.sim642.idea.zalgofy;

import java.util.function.UnaryOperator;

public class ZalgoIdentifierOperator implements UnaryOperator<String> {
    private final UnaryOperator<String> zalgoOperator = new ZalgoOperator();

    @Override
    public String apply(String s) {
        return "$" + zalgoOperator.apply(s);
    }
}
