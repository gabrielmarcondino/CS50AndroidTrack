package com.marcondino.examplejava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Track> tracks = new ArrayList<>();
        tracks.add(new Track("Mobile","Tommy"));
        tracks.add(new Track("Web","Brian"));
        tracks.add(new Track("Games","Colton"));

        List<String> students = Arrays.asList("Harry","Ron","Hermione"); // Classe com método estático: não há instância

        Map<String, Track> assigments = new HashMap<>(); // Map é a interface, HashMap é a classe

        Random random = new Random();
        for (String student: students) {
            int index = random.nextInt(tracks.size());
            assigments.put(student, tracks.get(index));
        }

        for (Map.Entry<String, Track> entry : assigments.entrySet()) {
            //Log.d("cs50", entry.getKey() + " got " + entry.getValue().name + " with " +
            //        entry.getValue().instructor);
            // getKey() and getValue(): convenção de getter and setter. Não expor a variável diretamente.
            // possibilita manipulação do dado antes de devolver para o usuário.
            // O Log acima não funciona mais porque as variáveis .name e .instructor foram privadas e
            // seu valor é acessado por um getter, conforme Log abaixo.

            Track track = entry.getValue();
            Log.d("cs50", entry.getKey() + " got " + track.getName() + " with " +
                    track.getInstructor());
        }
    }
}