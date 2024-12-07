package io.mygame.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.*;
import io.mygame.entities.GameObject;
import io.mygame.entities.NPC;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles collision detection for an entity against objects in a TiledMap.
 * Provides methods for checking collisions, drawing debug information, and managing entity positions.
 */
public class CollisionHandler {
    private final GameObject entity;
    private final TiledMap map;
    private final ShapeRenderer shapeRenderer;
    private final OrthographicCamera camera;
    private float previousX, previousY;
    private final Vector2 movement = new Vector2();
    private float currentX, currentY;
    private float currentNpcX, currentNpcY;
    private List<NPC> npcs;
    private Circle interactionCircle;

    private final boolean debugMode = true;

    /**
     * Constructs a CollisionHandler for handling collisions of a specific entity on a TiledMap.
     *
     * @param entity  The GameObject entity that will be checked for collisions.
     * @param map     The TiledMap that contains the collision objects.
     * @param camera  The camera used for rendering debug information.
     */
    public CollisionHandler(GameObject entity, List<NPC> npcs, TiledMap map, OrthographicCamera camera) {
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
     * Handles the collision detection and resolution for the entity.
     * This method checks if the entity collides with objects in the map and adjusts its position accordingly.
     * If the entity collides, it attempts to move in a way that avoids the collision.
     * It also draws debug information if debug mode is enabled.
     *
     * @throws RuntimeException If there is an error related to the collision detection process (e.g., null or cast exceptions).
     */
    public void handlePlayerCollision() {
        String collisionName = "COLLISION";
        try {
            MapLayer objectLayer = map.getLayers().get(collisionName);
            if (objectLayer == null) throw new NullPointerException();

            currentX = entity.getX();
            currentY = entity.getY();
            movement.x = currentX - previousX;
            movement.y = currentY - previousY;

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

    private Set<String> interactedObjects = new HashSet<>();

    private void checkObjectInteractions() {
        MapLayer interactionLayer = map.getLayers().get("interaction layer");
        if (interactionLayer == null) return;

        for (MapObject object : interactionLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                String objectName = object.getName();

                if (Intersector.overlaps(interactionCircle, rectangle) &&
                    Gdx.input.isKeyJustPressed(Input.Keys.E) &&
                    !interactedObjects.contains(objectName)) {

                    System.out.println("HAS INTERACTED WITH: " + objectName);
                    interactedObjects.add(objectName);
                }

                // Optional: Allow re-interaction if the player moves away and returns
                if (!Intersector.overlaps(interactionCircle, rectangle)) {
                    interactedObjects.remove(objectName);
                }
            }
            // Similar logic can be added for PolygonMapObject if needed
        }
    }

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

    public void handleNpcCollision() {
        String collisionName = "COLLISION";
        try {
            MapLayer objectLayer = map.getLayers().get(collisionName);
            if (objectLayer == null) throw new NullPointerException();

            for (NPC npc : npcs) {

                currentNpcX = npc.getX();
                currentNpcY = npc.getY();

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
     * Checks if the entity collides with any objects in the specified MapLayer.
     * This method checks if the entity's collision box overlaps with rectangular or polygonal objects in the map layer.
     *
     * @param objectLayer The MapLayer containing collision objects.
     * @return true if the entity collides with any object in the MapLayer, false otherwise.
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

    private boolean checkNpcPlayerCollision(NPC npc) {
        if (entity.getCollisionBox().overlaps(npc.getCollisionBox()) ||
            Intersector.overlapConvexPolygons(entity.getCollisionPolygon(), npc.getCollisionPolygon())) {
            return true;
        }
        return false;
    }

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
     * Saves the current position of the entity to be used later for collision handling.
     * This method records the current X and Y positions of the entity.
     */
    public void savePreviousPosition() {
        previousX = entity.getX();
        previousY = entity.getY();
    }

    /**
     * Reverts the entity's position to its previous saved position.
     * This is used to restore the entity's position if a collision occurs.
     */
    public void revertToPreviousPosition() {
        entity.setX(previousX);
        entity.setY(previousY);
    }

    public void savePreviousPositionNpc(NPC npc) {
        npc.setPreviousX(npc.getX());
        npc.setPreviousY(npc.getY());
    }

    public void revertToPreviousPositionNpc(NPC npc) {
        npc.setX(npc.getPreviousX());
        npc.setY(npc.getPreviousY());
    }

    /**
     * Disposes of resources used by the CollisionHandler, including the ShapeRenderer and the TiledMap.
     */
    public void dispose() {
        shapeRenderer.dispose();
        map.dispose();
    }
}
