package com.example.pokenavigation.data.model;

import com.example.pokenavigation.data.model.Pokemon;

import java.util.List;

public class PokemonResponse {
    private int count;
    private String next;
    private String previous;
    private List<Pokemon> results;

    public int getCount() {
        return count;
    }

    public List<Pokemon> getResults() {
        return results;
    }
}