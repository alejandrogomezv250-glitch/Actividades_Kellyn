package com.example.pokenavigation.data.model.repository;

import com.example.pokenavigation.data.model.Pokemon;
import java.util.ArrayList;
import java.util.List;

public class FavoritesRepository {
    // Lista estática para almacenar los favoritos en memoria
    private static final List<Pokemon> favoritosList = new ArrayList<>();

    public static List<Pokemon> getFavoritos() {
        return favoritosList;
    }

    public static void agregarFavorito(Pokemon pokemon) {
        // Evitamos duplicados verificando por nombre
        boolean existe = false;
        for (Pokemon p : favoritosList) {
            if (p.getName().equalsIgnoreCase(pokemon.getName())) {
                existe = true;
                break;
            }
        }
        if (!existe) {
            favoritosList.add(pokemon);
        }
    }
}