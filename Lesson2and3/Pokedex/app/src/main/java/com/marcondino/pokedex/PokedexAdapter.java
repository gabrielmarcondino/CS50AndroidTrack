package com.marcondino.pokedex;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PokedexAdapter extends RecyclerView.Adapter<PokedexAdapter.PokedexViewHolder> {

    public static class PokedexViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout containerView;
        public TextView textView;

        PokedexViewHolder(View view) {
            super(view);
            containerView = view.findViewById(R.id.pokedex_entry);
            textView = view.findViewById(R.id.pokedex_entry_text_view);

            containerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Pokemon current = (Pokemon) containerView.getTag();
                    Intent intent = new Intent(v.getContext(), PokemonActivity.class);
                    intent.putExtra("url", current.getUrl());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    private List<Pokemon> pokemon = new ArrayList<>();
    private RequestQueue requestQueue;

    PokedexAdapter(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        loadPokemon();
    }

    public void loadPokemon() {
        String url = "https://pokeapi.co/api/v2/pokemon?limit=151";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            // "And then lastly, we're going to define a method that's going to be called when the
            // request finishes."
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject result = results.getJSONObject(i);
                        String name = result.getString("name");
                        // Capitalize pokemon name
                        pokemon.add(new Pokemon(name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase(),
                                                result.getString("url")));
                    }
                    notifyDataSetChanged(); // method extended from extends RecyclerView.ViewHolder
                } catch (JSONException e) {
                    Log.d("cs50", "Json error", e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("cs50","Pokemon list error", error);
            }
        });
        requestQueue.add(request);
        // This is what's actually going to kick off the request and actually make the request.
        // In this first line here, all I've done is define an object that represents a request,
        // but I haven't actually used it yet. This queue is going to actually make that request.
    }

    @NonNull
    @Override
    public PokedexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // "So this is a method that's called when I want to create a new view holder.
        // So the first thing we wnat to do is get our layout file. So we want to go from a layout to a view.
        // And so the way to do that is we're going to create a new variable, called View.
        // And this is going to use this Android class, called LayoutInflater.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokedex_entry, parent, false);
        // So inflate is going to say I want to go from some XML file to a Java view. And so we need to pass
        // in what file we want to inflate. So now what we've done is we've converted this XML file into a Java
        // object in memory. So this is our view that we want to hold."
        return new PokedexViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull PokedexViewHolder holder, int position) {
        // "Now, this is the method that's going to be called whenever a view scrolls into screen and we say:
        // we need to set the values inside of this row. So here is where we're going to want to actually set
        // the different properties of the view that we've created.
        Pokemon current = pokemon.get(position);
        holder.textView.setText(current.getName());

        holder.containerView.setTag(current);
    }

    @Override
    public int getItemCount() {
        // "Now the last method we have to define on our adapter is how many rows to display."
        return pokemon.size();
    }

}
