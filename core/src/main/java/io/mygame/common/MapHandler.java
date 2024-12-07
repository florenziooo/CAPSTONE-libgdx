package io.mygame.common;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;
import java.util.List;

public class MapHandler {
    private OrthogonalTiledMapRenderer renderer;
    private List<TiledMapTileLayer> background;
    private List<TiledMapTileLayer> foreground;

    public MapHandler(TiledMap map) {
        MapLayers mapLayers = map.getLayers();
        renderer = new OrthogonalTiledMapRenderer(map);

        background = new ArrayList<>();
        foreground = new ArrayList<>();

        // Iterate through each layer
        for (MapLayer layer : mapLayers) {
            try {
                String layerName = layer.getName();
                System.out.println("Layer Name: " + (layerName != null ? layerName : "Unnamed Layer"));

                if (layer instanceof TiledMapTileLayer tileLayer) {
                    String type;
                    try {
                        type = tileLayer.getProperties().get("type", String.class);
                    } catch (ClassCastException e) {
                        System.err.println("Error: 'type' property in layer '" + layerName + "' is not a String.");
                        throw new RuntimeException("Error: " + e.getMessage());
                    }

                    if (type != null) {
                        if (type.equals("foreground")) {
                            foreground.add(tileLayer);
                            System.out.println("FOREGROUND FOUND");
                        } else {
                            background.add(tileLayer);
                            System.out.println("BACKGROUND FOUND");
                        }
                    } else {
                        background.add(tileLayer);
                        System.out.println("BACKGROUND FOUND");
                    }
                }
            } catch (Exception e) {
                System.err.println("An error occurred while processing a layer: " + e.getMessage());
                throw new RuntimeException("Error: " + e.getMessage());
            }
        }
    }

    public void loadBackground(OrthographicCamera camera) {
        renderer.setView(camera);

        renderer.getBatch().begin();

        for(TiledMapTileLayer layer : background) {
            renderer.renderTileLayer(layer);
        }
        renderer.getBatch().end();
    }

    public void loadForeground(OrthographicCamera camera) {
        renderer.getBatch().begin();
        for(TiledMapTileLayer layer : foreground) {
            renderer.renderTileLayer(layer);
        }
        renderer.getBatch().end();
    }

    public void dispose() {
        renderer.dispose();
    }
}
