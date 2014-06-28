package com.mygdx.Entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Entity extends Sprite {
	protected Vector2 velocity = new Vector2();
	protected float speed = 8.0f;
	
	
	public Entity(Sprite sprite) {
		super(sprite);
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}
	
	public void setVelocityX(float delta) {
		velocity.x = delta;
	}
	
	public void setVelocityY(float delta) {
		velocity.y = delta;
	}
	
	public float getSpeed() {
		return speed;
	}
}
