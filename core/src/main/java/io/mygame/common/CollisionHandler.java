package io.mygame.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.*;
import io.mygame.entities.Entity;
import io.mygame.entities.NPC;

import java.util.List;

/**
 * Handles collision detection for an entity against objects in a TiledMap.
 * Provides methods for checking collisions, drawing debug information, and managing entity positions.
 */
public class CollisionHandler {
    /************ ENTITY ************/
    private final Entity entity;

    /************ MAP ************/
    private final TiledMap map;

    /************ DEBUG RENDERING ************/
    private final ShapeRenderer shapeRenderer;
    private final OrthographicCamera camera;

    /************ ENTITY POSITION ************/
    private float previousX;
    private float previousY;

    /************ NPC List ************/
    private final List<NPC> npcs;

    /************ INTERACTION DETECTION ************/
    private final Circle interactionCircle;

    /**
     * Constructs a CollisionHandler for managing collisions involving a specific entity
     * and NPCs on a TiledMap, with debug information rendered using the specified camera.
     *
     * @param entity  the main game entity whose collisions will be handled
     * @param npcs    the list of non-playable characters (NPCs) for interaction and collision
     * @param map     the TiledMap containing the collision objects
     * @param camera  the OrthographicCamera used for rendering debug information
     */
    public CollisionHandler(Entity entity, List<NPC> npcs, TiledMap map, OrthographicCamera camera) {
        this.entity = entity;
        this.npcs = npcs;
        this.map = map;
        this.camera = camera;
        this.shapeRenderer = new ShapeRenderer();

        Rectangle playerBox = entity.getCollisionBox();
        float circleRadius = Math.max(playerBox.width, playerBox.height) * 3.0f;
        this.interactionCircle = new Circle(playerBox.x + playerBox.width / 2, playerBox.y + playerBox.height / 2, circleRadius);
    }

    /**
     * Handles collision detection and resolution for the entity.
     * This method checks if the entity collides with objects in the TiledMap and attempts to resolve the collision
     * by adjusting the entity's position. It also updates the interaction circle's position and renders debug
     * information if debug mode is enabled.
     *
     * @throws RuntimeException if an error occurs during the collision detection process, such as a missing collision
     *                          layer or an incompatible object type
     */
    public void handlePlayerCollision() {
        String collisionName = "COLLISION";
        try {
            MapLayer objectLayer = map.getLayers().get(collisionName);
            if (objectLayer == null) throw new NullPointerException();

            if (checkPlayerNpcCollision() || checkPlayerTileCollision(objectLayer)) {
                float tempY = entity.getY();
                entity.setY(previousY);

                if (checkPlayerNpcCollision() || checkPlayerTileCollision(objectLayer)) {
                    entity.setY(tempY);
                    entity.setX(previousX);

                    if (checkPlayerNpcCollision() || checkPlayerTileCollision(objectLayer)) {
                        revertToPreviousPosition();
                    } else {
                        savePreviousPosition();
                    }
                } else {
                    savePreviousPosition();
                }
            } else {
                savePreviousPosition();
            }

            Rectangle playerBox = entity.getCollisionBox();
            interactionCircle.x = playerBox.x + playerBox.width / 2;
            interactionCircle.y = playerBox.y + playerBox.height / 2;

            drawDebug(objectLayer);
        } catch (NullPointerException e) {
            System.err.println("NullPointerException: Collision object (" + collisionName + ") not found in the tiled map:  " + e.getMessage());
            throw new RuntimeException("Error: " + e.getMessage());
        } catch (ClassCastException e) {
            System.err.println("ClassCastException: Object is not a MapLayer class" + e.getMessage());
            throw new RuntimeException("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    /**
     * Checks for object interactions in the interaction layer of the TiledMap.
     * Returns the name of the layer where an interaction is detected.
     *
     * @return the name of the interaction layer, or null if no interaction occurs
     * @throws RuntimeException if the interaction layer is not found
     */
    public String checkObjectInteractions() {
        try {
            MapLayer interactionGroupLayer = map.getLayers().get("InteractionLayer");
            if (interactionGroupLayer == null) throw new IllegalArgumentException("MapLayer not found on checkObjectInteraction method not found");

            if(interactionGroupLayer instanceof MapGroupLayer groupLayer) {

                for(MapLayer childLayer : groupLayer.getLayers()) {
                    String layerName = childLayer.getName();

                    for(MapObject object : childLayer.getObjects()) {
                        if (object instanceof RectangleMapObject) {
                            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                            if (Intersector.overlaps(interactionCircle, rectangle) &&
                                Gdx.input.isKeyJustPressed(Input.Keys.E)) {

                                return layerName;
                            }
                        }
                    }
                }
            }
        } catch (IllegalArgumentException e){
            throw new RuntimeException(e.getMessage());
        }

        return null;
    }

    /**
     * Checks for NPC interactions within the interaction circle.
     * Returns the type of the NPC being interacted with.
     *
     * @return the type of the NPC, or null if no interaction occurs
     */
    public String checkNPCInteractions() {
        for (NPC npc : npcs) {
            if (Intersector.overlaps(interactionCircle, npc.getCollisionBox())) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                    return npc.getType();
                }
            }
        }
        return null;
    }

    /**
     * Handles collisions between NPCs and other objects or tiles in the map.
     * Updates NPC positions to avoid overlapping.
     *
     * @throws RuntimeException if a critical error occurs during NPC collision handling
     */
    public void handleNpcCollision() {
        String collisionName = "COLLISION";
        try {
            MapLayer objectLayer = map.getLayers().get(collisionName);
            if (objectLayer == null) throw new NullPointerException();

            for (NPC npc : npcs) {


                if (checkNpcPlayerCollision(npc) || checkNpcTileCollision(objectLayer, npc) || checkNpcCollision(npc)) {
                    float tempY = npc.getY();
                    npc.setY(npc.getPreviousY());

                    if (checkNpcPlayerCollision(npc) || checkNpcTileCollision(objectLayer, npc) || checkNpcCollision(npc)) {
                        npc.setY(tempY);
                        npc.setX(npc.getPreviousX());

                        if (checkNpcPlayerCollision(npc) || checkNpcTileCollision(objectLayer, npc) || checkNpcCollision(npc)) {
                            revertToPreviousPositionNpc(npc);
                        } else {
                            savePreviousPositionNpc(npc);
                        }
                    } else {
                        savePreviousPositionNpc(npc);
                    }
                } else {
                    savePreviousPositionNpc(npc);
                }

                drawDebug(objectLayer);
            }
        } catch (NullPointerException e) {
            System.err.println("NullPointerException: Collision object (" + collisionName + ") not found in the tiled map: " + e.getMessage());
            throw new RuntimeException("Error: " + e.getMessage());
        } catch (ClassCastException e) {
            System.err.println("ClassCastException: Object is not a MapLayer class: " + e.getMessage());
            throw new RuntimeException("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    /**
     * Checks if the player collides with any tile objects in the specified layer.
     *
     * @param objectLayer the layer containing collision objects
     * @return true if a collision is detected, false otherwise
     */
    private boolean checkPlayerTileCollision(MapLayer objectLayer) {
        try {
            for (MapObject object : objectLayer.getObjects()) {
                if (object instanceof RectangleMapObject) {
                    Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                    if (entity.getCollisionBox().overlaps(rectangle)) {
                        return true;
                    }
                } else if (object instanceof PolygonMapObject) {
                    Polygon polygon = ((PolygonMapObject) object).getPolygon();
                    if (Intersector.overlapConvexPolygons(entity.getCollisionPolygon(), polygon)) {
                        return true;
                    }
                } else {
                    throw new ClassCastException("Object is not a RectangleMapObject nor a PolygonMapObject");
                }
            }
            return false;
        } catch (ClassCastException e) {
            System.err.println("ClassCastException: " + e.getMessage());
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    /**
     * Checks if the player collides with any NPC.
     *
     * @return true if a collision with an NPC is detected, false otherwise
     */
    private boolean checkPlayerNpcCollision() {
        if(npcs == null || npcs.isEmpty()) return false;
        for (NPC npc : npcs) {
            if (entity.getCollisionBox().overlaps(npc.getCollisionBox()) ||
                Intersector.overlapConvexPolygons(entity.getCollisionPolygon(), npc.getCollisionPolygon())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if an NPC collides with the player.
     *
     * @param npc the NPC to check for collisions
     * @return true if a collision is detected, false otherwise
     */
    private boolean checkNpcPlayerCollision(NPC npc) {
        return entity.getCollisionBox().overlaps(npc.getCollisionBox()) ||
            Intersector.overlapConvexPolygons(entity.getCollisionPolygon(), npc.getCollisionPolygon());
    }

    /**
     * Checks if an NPC collides with another NPC.
     *
     * @param npc1 the NPC to check for collisions
     * @return true if a collision is detected, false otherwise
     */
    private boolean checkNpcCollision(NPC npc1) {
        for (NPC npc2 : npcs) {
            if(!(npc1.equals(npc2))) {
                if (npc1.getCollisionBox().overlaps(npc2.getCollisionBox()) ||
                    Intersector.overlapConvexPolygons(npc1.getCollisionPolygon(), npc2.getCollisionPolygon())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if an NPC collides with tiles in the specified layer.
     *
     * @param objectLayer the layer containing collision objects
     * @param npc         the NPC to check for collisions
     * @return true if a collision is detected, false otherwise
     */
    private boolean checkNpcTileCollision(MapLayer objectLayer, NPC npc) {
        for (MapObject object : objectLayer.getObjects()) {
            if (object instanceof RectangleMapObject rectangleObj) {
                Rectangle rectangle = rectangleObj.getRectangle();
                if (npc.getCollisionBox().overlaps(rectangle)) {
                    return true;
                }
            } else if (object instanceof PolygonMapObject polygonObj) {
                Polygon polygon = polygonObj.getPolygon();
                if (Intersector.overlapConvexPolygons(npc.getCollisionPolygon(), polygon)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Draws debug information to the screen, such as the player's collision box, movement vector, and collision objects.
     * The drawing is done using a ShapeRenderer.
     *
     * @param objectLayer The MapLayer containing collision objects to be drawn.
     */
    private void drawDebug(MapLayer objectLayer) {
        boolean debugMode = false;
        if (!debugMode) return;

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Draw player collision box
        shapeRenderer.setColor(0, 1, 0, 1);
        Rectangle playerBox = entity.getCollisionBox();
        shapeRenderer.rect(playerBox.x, playerBox.y, playerBox.width, playerBox.height);

        // Draw interaction circle
        shapeRenderer.setColor(0, 1, 1, 1);  // Cyan color for interaction circle
        shapeRenderer.circle(interactionCircle.x, interactionCircle.y, interactionCircle.radius);

        // Draw movement vector
        shapeRenderer.setColor(1, 1, 0, 1);
        shapeRenderer.line(previousX, previousY, entity.getX(), entity.getY());

        // Draw collision objects
        for (MapObject object : objectLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                shapeRenderer.setColor(1, 0, 0, 1);
                shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            } else if (object instanceof PolygonMapObject) {
                Polygon polygon = ((PolygonMapObject) object).getPolygon();
                shapeRenderer.setColor(0, 0, 1, 1);
                shapeRenderer.polygon(polygon.getTransformedVertices());
            }
        }

        shapeRenderer.end();
    }

    /**
     * Saves the current position of the player entity for collision rollback.
     */
    public void savePreviousPosition() {
        previousX = entity.getX();
        previousY = entity.getY();
    }

    /**
     * Reverts the player entity's position to the last saved position.
     */
    public void revertToPreviousPosition() {
        entity.setX(previousX);
        entity.setY(previousY);
    }

    /**
     * Saves the current position of an NPC for collision rollback.
     *
     * @param npc the NPC whose position will be saved
     */
    public void savePreviousPositionNpc(NPC npc) {
        npc.setPreviousX(npc.getX());
        npc.setPreviousY(npc.getY());
    }

    /**
     * Reverts the position of an NPC to the last saved position.
     *
     * @param npc the NPC whose position will be reverted
     */
    public void revertToPreviousPositionNpc(NPC npc) {
        npc.setX(npc.getPreviousX());
        npc.setY(npc.getPreviousY());
    }

    /**
     * Releases resources used by the CollisionHandler.
     */
    public void dispose() {
        shapeRenderer.dispose();
        map.dispose();
    }
}
