package com.mygdx.Controller;

import com.badlogic.gdx.math.Intersector;
import com.mygdx.Entities.Player;

public class Controller {
	enum Keys {
		LEFT, RIGHT, DOWN, UP;
	}
	
	private Player player;
	private com.mygdx.Objects.Object[] collisions,objects;
	private boolean left = false, right = false, down = false, up = false;
	private Thread thread;
	
	public Controller(Player player, com.mygdx.Objects.Object[] collisions, com.mygdx.Objects.Object[] objects) {
		this.player = player;
		this.collisions = collisions;
		this.objects = objects;
	}
	
	public void update(float delta) {
		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				checkCollision();
			}
			
		});
		
		processInput();
		player.update(delta);
	}
	
	public void processInput() {
		thread.start();
		if(left) {
			player.setVelocityX(-player.getSpeed());			
		}
		else if(right) {
			player.setVelocityX(player.getSpeed());
		}
		else if (up) {
			player.setVelocityY(player.getSpeed());
			
		}
		else if(down) {
			player.setVelocityY(-player.getSpeed());
			
		}
	}
	
	public void checkCollision() {
		if(left) {			
			for(com.mygdx.Objects.Object c:collisions) {
				if(Intersector.overlapConvexPolygons(player.getPolygon(),c.getPolygon())) {
					player.setPosition(player.getX() + 10, player.getY());
					player.setVelocityX(player.getSpeed());
					break;
				}
			}
			
			for(com.mygdx.Objects.Object o:objects) {
				if(Intersector.overlapConvexPolygons(player.getPolygon(),o.getPolygon())) {
					player.setPosition(player.getX() + 10, player.getY());
					player.setVelocityX(player.getSpeed());
					break;
				}
			}
			
		}
		else if(right) {			
			for(com.mygdx.Objects.Object c:collisions) {
				if(Intersector.overlapConvexPolygons(player.getPolygon(),c.getPolygon())) {
					player.setPosition(player.getX() - 10, player.getY());
					player.setVelocityX(-player.getSpeed());
					break;
				}
			}
			
			for(com.mygdx.Objects.Object o:objects) {
				if(Intersector.overlapConvexPolygons(player.getPolygon(),o.getPolygon())) {
					player.setPosition(player.getX() - 10, player.getY());
					player.setVelocityX(-player.getSpeed());
					break;
				}
			}
		}
		else if (up) {			
			for(com.mygdx.Objects.Object c:collisions) {
				if(Intersector.overlapConvexPolygons(player.getPolygon(),c.getPolygon())) {
					player.setPosition(player.getX(), player.getY() - 10);
					player.setVelocityY(-player.getSpeed());
					break;
				}
			}
			
			for(com.mygdx.Objects.Object o:objects) {
				if(Intersector.overlapConvexPolygons(player.getPolygon(),o.getPolygon())) {
					player.setPosition(player.getX(), player.getY() - 10);
					player.setVelocityY(-player.getSpeed());
					break;
				}
			}
		}
		else if(down) {			
			for(com.mygdx.Objects.Object c:collisions) {
				if(Intersector.overlapConvexPolygons(player.getPolygon(),c.getPolygon())) {
					player.setPosition(player.getX(), player.getY() + 10);
					player.setVelocityY(player.getSpeed());
					break;
				}
			}
			
			for(com.mygdx.Objects.Object o:objects) {
				if(Intersector.overlapConvexPolygons(player.getPolygon(),o.getPolygon())) {
					player.setPosition(player.getX(), player.getY() + 10);
					player.setVelocityY(player.getSpeed());
					break;
				}
			}
		}
	}
	
	public void aPressed() {
		left = true;
	}
	
	public void sPressed() {
		down = true;
	}
	
	public void dPressed() {
		right = true;
	}
	
	public void wPressed() {
		up = true;
	}
	public void aReleased() {
		left = false;
		player.setVelocityX(0);
		player.setVelocityY(0);
	}
	
	public void sReleased() {
		down = false;
		player.setVelocityX(0);
		player.setVelocityY(0);
	}
	
	public void dReleased() {
		right = false;
		player.setVelocityX(0);
		player.setVelocityY(0);
	}
	
	public void wReleased() {
		up = false;
		player.setVelocityX(0);
		player.setVelocityY(0);
	}
}
