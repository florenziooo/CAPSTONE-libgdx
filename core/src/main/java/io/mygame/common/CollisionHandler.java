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
import com.badlogic.gdx.math.Vector2;
import io.mygame.entities.GameObject;

public class CollisionHandler {
    private final GameObject entity;
    private final TiledMap map;
    private final boolean debugMode = true;
    private final ShapeRenderer shapeRenderer;
    private final OrthographicCamera camera;
    private float previousX, previousY;
    private final Vector2 movement = new Vector2();
    private float currentX, currentY;

    public CollisionHandler(GameObject entity, TiledMap map, OrthographicCamera camera) {
        this.entity = entity;
        this.map = map;
        this.camera = camera;
        this.shapeRenderer = new ShapeRenderer();
    }

    public void handleCollision() {
        MapLayer objectLayer = map.getLayers().get("basic collisions");
        if (objectLayer == null) return;

        currentX = entity.getX();
        currentY = entity.getY();
        movement.x = currentX - previousX;
        movement.y = currentY - previousY;

        if (checkCollision(objectLayer)) {
            float tempY = entity.getY();
            entity.setY(previousY);

            if (checkCollision(objectLayer)) {
                entity.setY(tempY);
                entity.setX(previousX);

                if (checkCollision(objectLayer)) {
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

        drawDebug(objectLayer);
    }

    private boolean checkCollision(MapLayer objectLayer) {
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
            }
        }
        return false;
    }

    private void drawDebug(MapLayer objectLayer) {
        if (!debugMode) return;

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Draw player collision box
        shapeRenderer.setColor(0, 1, 0, 1);
        Rectangle playerBox = entity.getCollisionBox();
        shapeRenderer.rect(playerBox.x, playerBox.y, playerBox.width, playerBox.height);

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
