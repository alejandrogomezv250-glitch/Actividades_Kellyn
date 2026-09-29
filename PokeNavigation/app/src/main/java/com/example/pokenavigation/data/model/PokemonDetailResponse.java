package com.example.pokenavigation.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PokemonDetailResponse {
    private String name;
    private int height;
    private int weight;

    @SerializedName("base_experience")
    private int baseExperience;

    private Sprites sprites;
    private List<TypeSlot> types;

    public String getName() { return name; }
    public int getHeight() { return height; }
    public int getWeight() { return weight; }
    public int getBaseExperience() { return baseExperience; }
    public Sprites getSprites() { return sprites; }
    public List<TypeSlot> getTypes() { return types; }

    public static class Sprites {
        @SerializedName("front_default")
        private String frontDefault;

        public String getFrontDefault() { return frontDefault; }
    }

    public static class TypeSlot {
        private Type type;

        public Type getType() { return type; }
    }

    public static class Type {
        private String name;

        public String getName() { return name; }
    }
}