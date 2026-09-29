package com.example.pokenavigation.data.model.remote;

import com.example.pokenavigation.data.model.remote.PokeApiService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // URL principal de la API
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";
    // Instancia única de Retrofit
    private static Retrofit retrofit;

    // Constructor privado para evitar crear objetos de esta clase
    private RetrofitClient() {
    }

    // Método para obtener el servicio de la API
    public static PokeApiService getService() {
        // Retrofit se crea solamente una vez
        if (retrofit == null) {
            // Permite visualizar en Logcat las peticiones HTTP
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // Muestra información básica:
            // método, URL y código de respuesta
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

            // Cliente HTTP encargado de realizar las peticiones
            OkHttpClient client = new OkHttpClient.Builder()
                    // Se agrega el interceptor de Logs
                    .addInterceptor(logging)
                    .build();

            // Configuración de Retrofit
            retrofit = new Retrofit.Builder()
                    // URL base de la API
                    .baseUrl(BASE_URL)
                    // Se utiliza el cliente OkHttp configurado
                    .client(client)
                    // Convierte el JSON de la API en objetos Java
                    .addConverterFactory(GsonConverterFactory.create())
                    // Construye Retrofit
                    .build();
        }

        // Retrofit implementa automáticamente PokeApiService
        return retrofit.create(PokeApiService.class);
    }
}