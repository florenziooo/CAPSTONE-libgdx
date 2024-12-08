package io.mygame.common;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * The MapHandler class manages the loading and rendering of layers in a TiledMap.
 * It separates the map layers into background and foreground layers and provides methods
 * to load and render them using an OrthogonalTiledMapRenderer.
 */
public class MapHandler {
    /************ FIELDS ************/
    private final OrthogonalTiledMapRenderer renderer;
    private final List<TiledMapTileLayer> background;
    private final List<TiledMapTileLayer> foreground;

    /**
     * Constructor that initializes the MapHandler with a given TiledMap.
     * Processes each layer in the map, categorizing them as either background or foreground
     * based on their properties.
     *
     * @param map The TiledMap to be processed.
     */
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

    /**
     * Loads and renders the background layers.
     * This method sets the camera view and renders each background layer.
     *
     * @param camera The camera to set the view for the renderer.
     */
    public void loadBackground(OrthographicCamera camera) {
        renderer.setView(camera);

        renderer.getBatch().begin();

        for (TiledMapTileLayer layer : background) {
            renderer.renderTileLayer(layer);
        }
        renderer.getBatch().end();
    }

    /**
     * Loads and renders the foreground layers.
     * This method renders each foreground layer.
     */
    public void loadForeground() {
        renderer.getBatch().begin();
        for (TiledMapTileLayer layer : foreground) {
            renderer.renderTileLayer(layer);
        }
        renderer.getBatch().end();
    }

    /**
     * Disposes of the renderer to free up resources.
     */
    public void dispose() {
        renderer.dispose();
    }
}
