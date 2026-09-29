package com.example.pokenavigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokenavigation.FavoritesDatabaseHelper;
import com.example.pokenavigation.data.model.Pokemon;
import com.example.pokenavigation.ui.adapter.PokemonAdapter;

import java.util.List;

public class FavoritesFragment extends Fragment {

    // 1. Declaración de la variable global de la clase
    private RecyclerView recyclerFavorites;
    private PokemonAdapter adapter;
    private FavoritesDatabaseHelper dbHelper;

    public FavoritesFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 2. Enlace correcto con el componente del XML (sin poner R.id. antes de la variable)
        recyclerFavorites = view.findViewById(R.id.recyclerFavorites);
        recyclerFavorites.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerFavorites.setHasFixedSize(true);

        dbHelper = new FavoritesDatabaseHelper(requireContext());

        adapter = new PokemonAdapter(pokemon -> {
            if (pokemon != null && pokemon.getName() != null) {
                String nombreMinuscula = pokemon.getName().toLowerCase();
                PokemonDetailFragment detailFragment = PokemonDetailFragment.newInstance(nombreMinuscula);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, detailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        recyclerFavorites.setAdapter(adapter);
        cargarFavoritos();
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarFavoritos();
    }

    private void cargarFavoritos() {
        if (dbHelper != null && adapter != null) {
            List<Pokemon> listaFavoritos = dbHelper.obtenerFavoritos();
            adapter.actualizarDatos(listaFavoritos);
        }
    }
}