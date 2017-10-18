package com.zedzul.github.hw.backend;

class UsersCounter {


    private final long mCounter;

    UsersCounter(final long pCounter) {
        mCounter = pCounter;
    }

    public String getData() {
        return String.valueOf(mCounter);
    }
}
