package com.alincode.hammock.configuration;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Matcher {
    private final Set<String> matchSet;
    /**
     * This string is a host:port pairing to match.
     */
    public Matcher(Set<String> match) {
       this.matchSet = match;
    }

    public Matcher(Configuration config) {
        Set<String> matchSet = new HashSet<>();
        for(Map<String, String> socketPairs : config.sockets) {
            matchSet.addAll(socketPairs.keySet());
        }
        this.matchSet = matchSet;
    }

    public boolean match(String s) {
        return matchSet.contains(s);
    }
}
