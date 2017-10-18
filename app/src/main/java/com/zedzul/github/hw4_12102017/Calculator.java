package com.zedzul.github.hw4_12102017;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Calculator implements ICalculator {

    private static final String OPERATION_SIGN_REGEX = "\\+|-|\\*|/";
    private static final String NUMBER_PATTERN_REGEX = "[0-9]+";

    @Override
    public String solve(final String value) {
        //TODO: It works but can be better
        String result = "";
        final Pattern p = Pattern.compile(NUMBER_PATTERN_REGEX);
        final Pattern p2 = Pattern.compile(OPERATION_SIGN_REGEX);
        try {
            final Matcher numbers = p.matcher(value);
            final Matcher operation = p2.matcher(value);

            long a = 0L, b = 0L;
            if (numbers.find()) {
                a = Long.valueOf(value.substring(numbers.start(), numbers.end()));
            }
            if (numbers.find()) {
                b = Long.valueOf(value.substring(numbers.start(), numbers.end()));
            }

            if(operation.find()) {
                final String k = value.substring(operation.start(), operation.end());
                switch (k) {
                    case "+":
                        result = String.valueOf(a + b);
                        break;
                    case "-":
                        result = String.valueOf(a - b);
                        break;
                    case "*":
                        result = String.valueOf(a * b);
                        break;
                    case "/":
                        result = String.valueOf(a / b);
                        break;
                    default:
                        break;
                }
            }
        } catch (final Exception e) {
            result = e.getMessage();
        }
        return result;
    }

}
