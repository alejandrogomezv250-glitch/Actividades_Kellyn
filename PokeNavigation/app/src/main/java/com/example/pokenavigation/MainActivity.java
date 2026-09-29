package com.example.pokenavigation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.pokenavigation.FavoritesFragment;
import com.example.pokenavigation.HomeFragment;
import com.example.pokenavigation.InfoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initObjects();

        // 1. Cargamos un fragmento por defecto al abrir la app (opcional pero recomendado)
        if (savedInstanceState == null) {
            cargarFragment(new HomeFragment());
        }

        // 2. ¡Importante! Faltaba llamar a este método aquí para activar los clics del menú
        configurarBottomNavigation();
    }

    private void initObjects() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
    }

    private Fragment obtenerFragment(int itemId) {
        if (itemId == R.id.navigation_home) {
            return new HomeFragment();
        }

        if (itemId == R.id.navigation_favorites) {
            return new FavoritesFragment();
        }

        if (itemId == R.id.navigation_info) {
            return new InfoFragment();
        }

        return null;
    }

    private void cargarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    private void configurarBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment fragment = obtenerFragment(item.getItemId());

            if (fragment == null) {
                return false;
            }

            cargarFragment(fragment);

            return true;
        });
    }
}