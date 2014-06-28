package com.mygdx.Entities;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Entity{
	Rectangle bounds;
	Polygon polygon;
	
	public Player(Sprite sprite) {
		super(sprite);
		bounds = new Rectangle(0,0,getWidth(),getHeight());
		polygon = new Polygon(new float[]{0,0,bounds.width,0,bounds.width,bounds.height,0,bounds.height});
	}

	public void draw(SpriteBatch spriteBatch) {
		super.draw(spriteBatch);
	}
	
	public void update(float delta) {
		if(velocity.x == speed) {
			setX(getX() + (float) (Math.pow(speed, delta)));
		}
		else if(velocity.x == -speed) {
			setX(getX() - (float) (Math.pow(speed, delta)));
		}
		else if(velocity.y == speed) {
			setY(getY() + (float) (Math.pow(speed, delta)));
		}
		else if(velocity.y == -speed) {
			setY(getY() - (float) (Math.pow(speed, delta)));
		}
	}
	
	public Polygon getPolygon() {
		polygon.setPosition(getX(), getY());
		polygon.setRotation(getRotation());
		return polygon;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
}