package com.example.pokenavigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.pokenavigation.FavoritesDatabaseHelper;
import com.example.pokenavigation.data.model.PokemonDetailResponse;
import com.example.pokenavigation.data.model.repository.PokemonRepository;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonDetailFragment extends Fragment {

    private static final String ARG_POKEMON_NAME = "pokemon_name";
    private String pokemonName;

    private ImageView imgPokemonDetail;
    private TextView tvDetailName, tvDetailHeight, tvDetailWeight, tvDetailExperience, tvDetailTypes;
    private MaterialButton btnFavorite;

    private PokemonRepository repository;
    private FavoritesDatabaseHelper dbHelper;

    public static PokemonDetailFragment newInstance(String name) {
        PokemonDetailFragment fragment = new PokemonDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_POKEMON_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pokemonName = getArguments().getString(ARG_POKEMON_NAME);
        }
        repository = new PokemonRepository();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_detail, container, false);

        imgPokemonDetail = view.findViewById(R.id.imgPokemonDetail);
        tvDetailName = view.findViewById(R.id.tvDetailName);
        tvDetailHeight = view.findViewById(R.id.tvDetailHeight);
        tvDetailWeight = view.findViewById(R.id.tvDetailWeight);
        tvDetailExperience = view.findViewById(R.id.tvDetailExperience);
        tvDetailTypes = view.findViewById(R.id.tvDetailTypes);
        btnFavorite = view.findViewById(R.id.btnFavorite);

        dbHelper = new FavoritesDatabaseHelper(requireContext());

        cargarDetallePokemon();

        // Configuración segura del botón de favoritos
        btnFavorite.setOnClickListener(v -> {
            if (pokemonName != null && !pokemonName.isEmpty()) {
                String url = "https://pokeapi.co/api/v2/pokemon/" + pokemonName.toLowerCase();
                boolean guardado = dbHelper.agregarFavorito(pokemonName.toLowerCase(), url);

                if (guardado) {
                    Toast.makeText(getContext(), pokemonName.toUpperCase() + " añadido a Favoritos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error al guardar en favoritos", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Nombre de Pokémon no disponible", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void cargarDetallePokemon() {
        if (pokemonName == null) return;

        repository.obtenerDetallePokemon(pokemonName).enqueue(new Callback<PokemonDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<PokemonDetailResponse> call, @NonNull Response<PokemonDetailResponse> response) {
                if (!isAdded()) {
                    return;
                }

                if (response.isSuccessful() && response.body() != null) {
                    PokemonDetailResponse detail = response.body();

                    // Aseguramos respaldar el nombre real obtenido de la respuesta si difiere
                    if (detail.getName() != null) {
                        pokemonName = detail.getName();
                    }

                    tvDetailName.setText(pokemonName.toUpperCase());
                    tvDetailHeight.setText("Altura: " + detail.getHeight());
                    tvDetailWeight.setText("Peso: " + detail.getWeight());
                    tvDetailExperience.setText("Experiencia Base: " + detail.getBaseExperience());

                    StringBuilder typesStr = new StringBuilder("Tipos: ");
                    if (detail.getTypes() != null) {
                        for (PokemonDetailResponse.TypeSlot slot : detail.getTypes()) {
                            typesStr.append(slot.getType().getName()).append(" ");
                        }
                    }
                    tvDetailTypes.setText(typesStr.toString());

                    if (detail.getSprites() != null && detail.getSprites().getFrontDefault() != null) {
                        Glide.with(requireContext())
                                .load(detail.getSprites().getFrontDefault())
                                .into(imgPokemonDetail);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PokemonDetailResponse> call, @NonNull Throwable t) {
                if (!isAdded()) {
                    return;
                }
                Toast.makeText(getContext(), "Error al cargar detalles del Pokémon", Toast.LENGTH_SHORT).show();
            }
        });
    }
}