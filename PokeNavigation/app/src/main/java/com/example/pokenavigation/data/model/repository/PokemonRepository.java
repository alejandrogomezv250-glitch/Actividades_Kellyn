package com.example.pokenavigation.data.model.repository;

import com.example.pokenavigation.data.model.PokemonDetailResponse;
import com.example.pokenavigation.data.model.PokemonResponse;
import com.example.pokenavigation.data.model.remote.PokeApiService;
import com.example.pokenavigation.data.model.remote.RetrofitClient;
import retrofit2.Call;

public class PokemonRepository {
    private final PokeApiService service;

    public PokemonRepository() {
        service = RetrofitClient.getService();
    }

    public Call<PokemonResponse> obtenerPokemon(
            int limit,
            int offset
    )
    {

        return service.getPokemon(limit, offset);
    }
    public Call<PokemonDetailResponse> obtenerDetallePokemon(String name) {
        return service.getPokemonDetail(name);
    }

}