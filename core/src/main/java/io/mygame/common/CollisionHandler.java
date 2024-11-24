package io.mygame.common;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import io.mygame.entities.GameObject;
import io.mygame.entities.NPC;

public class CollisionHandler {
    private final GameObject entity;
    private final TiledMap map;
    private final NPC npc;
    // DEBUGGING
    private final boolean debugMode = true;
    private final ShapeRenderer shapeRenderer;
    private final OrthographicCamera camera;

    private float previousX, previousY;

    public CollisionHandler(GameObject entity, NPC npc, TiledMap map, OrthographicCamera camera) {
        this.entity = entity;
        this.npc = npc;
        this.map = map;

        // DEBUGGING COLLISION
        shapeRenderer  = new ShapeRenderer();
        this.camera = camera;
    }

    public void handleCollision() {
        if (debugMode) {
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            // Draw the player's collision box
            shapeRenderer.setColor(0, 1, 0, 1); // Green for the player
            Rectangle playerCollisionBox = entity.getCollisionBox();
            shapeRenderer.rect(playerCollisionBox.x, playerCollisionBox.y, playerCollisionBox.width, playerCollisionBox.height);

            // Get the "basic collisions" layer as an Object Layer
            MapLayer objectLayer = map.getLayers().get("basic collisions");

            if (objectLayer != null) {
                for (MapObject object : objectLayer.getObjects()) {
                    if (object instanceof RectangleMapObject) {
                        // Draw RectangleMapObject
                        Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                        shapeRenderer.setColor(1, 0, 0, 1); // Red for rectangles
                        shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                    } else if (object instanceof PolygonMapObject) {
                        // Draw PolygonMapObject
                        Polygon polygon = ((PolygonMapObject) object).getPolygon();
                        shapeRenderer.setColor(0, 0, 1, 1); // Blue for polygons
                        shapeRenderer.polygon(polygon.getTransformedVertices());
                    }
                }
            } else {
                System.out.println("Object layer 'basic collisions' not found!");
            }
            System.out.println("draw buddy");
            shapeRenderer.end();
        }

        // Collision logic (unchanged)
        MapLayer objectLayer = map.getLayers().get("basic collisions");
        if (objectLayer != null) {
            Rectangle npcCollisionRectangle = new Rectangle(npc.getX(), npc.getY()+8, npc.getWidth(), npc.getHeight()-24);
            boolean collisionDetected = entity.getCollisionBox().overlaps(npcCollisionRectangle);
            for (MapObject object : objectLayer.getObjects()) {
                if (object instanceof RectangleMapObject) {
                    Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                    if (entity.getCollisionBox().overlaps(rectangle)) {
                        collisionDetected = true;
                        break;
                    }
                } else if (object instanceof PolygonMapObject) {
                    Polygon polygon = ((PolygonMapObject) object).getPolygon();
                    if (Intersector.overlapConvexPolygons(entity.getCollisionPolygon(), polygon)) {
                        collisionDetected = true;
                        break;
                    }
                }
            }

            if (collisionDetected) {
                revertToPreviousPosition();
            } else {
                savePreviousPosition();
            }
        }
    }

    public void savePreviousPosition() {
        previousX = entity.getX();
        previousY = entity.getY();
    }

    public void revertToPreviousPosition() {
        entity.setX(previousX);
        entity.setY(previousY);
    }

    public void dispose() {
        shapeRenderer.dispose();
        map.dispose();
    }
}
