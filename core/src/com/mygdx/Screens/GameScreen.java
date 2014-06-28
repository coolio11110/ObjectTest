package com.mygdx.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.Controller.Controller;
import com.mygdx.Entities.Player;

public class GameScreen implements Screen, InputProcessor {
	private OrthographicCamera camera;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private TextureAtlas atlas;
	private Player player;
	private com.mygdx.Objects.Object[] collisions = new com.mygdx.Objects.Object[4], objects = new com.mygdx.Objects.Object[3];
	private BitmapFont bf;
	private SpriteBatch sb;
	private ShapeRenderer sr;
	private Controller controller;
	private Stage stage;
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	
		
		camera.position.set(player.getX(),player.getY(),0);
		camera.update();
		
		renderer.setView(camera);
		renderer.render();
		
		controller.update(delta);
		
		sr.setProjectionMatrix(camera.combined);
		sr.begin(ShapeType.Line);
		//System.out.println(player.getPolygon().getTransformedVertices()[2] + " " + player.getPolygon().getTransformedVertices()[7]);
		sr.rect(player.getPolygon().getTransformedVertices()[0] , player.getPolygon().getTransformedVertices()[1], 
				player.getBounds().width, player.getBounds().height);
		
		for(int i = 0; i < objects.length; i++) {
			sr.rect(objects[i].getPolygon().getTransformedVertices()[0] , objects[i].getPolygon().getTransformedVertices()[1], 
					objects[i].getBounds().width, objects[i].getBounds().height);
		}
		
		for(int i = 0; i < collisions.length; i++) {
			sr.rect(collisions[i].getPolygon().getTransformedVertices()[0] , collisions[i].getPolygon().getTransformedVertices()[1], 
					collisions[i].getBounds().width, collisions[i].getBounds().height);
		}
		
		sr.end();
		
		stage.act();
		stage.draw();
		
		renderer.getSpriteBatch().begin();
		renderSprites(renderer);
		renderer.getSpriteBatch().end();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
	}

	@Override
	public void show() {
		bf = new BitmapFont();
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		sr.setColor(new Color(1,1,0,0));
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		camera.zoom = 1;
		camera.update();
		
		map = new TmxMapLoader().load("ObjectMap.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		atlas = new TextureAtlas("Sprites.txt");

		stage = new Stage();
		stage.setViewport(new ExtendViewport(32*5,32*5));
		stage.getCamera().position.set(0, 0, 0);
		
		RectangleMapObject playerSpawn = (RectangleMapObject) map.getLayers().get("Entities").getObjects().get("PlayerSpawn");
		player = new Player(atlas.createSprite("Ninja"));
		player.setPosition(playerSpawn.getRectangle().x, playerSpawn.getRectangle().y);
		
		MapObjects collisionLayer =  map.getLayers().get("Collision").getObjects();
		for(int i = 0; i < collisionLayer.getCount(); i++) {
			RectangleMapObject col = (RectangleMapObject) collisionLayer.get(i);
			if(col.getName().equals("Fountain")) {
				collisions[i] = new com.mygdx.Objects.Object(atlas.createSprite("Fountain"),col.getRectangle());
				collisions[i].setPosition(collisions[i].getPolygon().getTransformedVertices()[0],collisions[i].getPolygon().getTransformedVertices()[1]);
				stage.addActor(collisions[i]);
			}
			else if(col.getName().equals("Mountains"))
				collisions[i] = new com.mygdx.Objects.Object(col.getRectangle());
		}
		
		MapObjects objectsLayer =  map.getLayers().get("Objects").getObjects();
		for(int i = 0; i < objectsLayer.getCount(); i++) {
			RectangleMapObject object = (RectangleMapObject) objectsLayer.get(i);
			if(object.getName().equals("Tree")) {
				objects[i] = new com.mygdx.Objects.Object(atlas.createSprite("Tree"), object.getRectangle());
				objects[i].setPosition(objects[i].getPolygon().getTransformedVertices()[0],objects[i].getPolygon().getTransformedVertices()[1]);
				stage.addActor(objects[i]);
			}
			else if(object.getName().equals("Sign")) {
				objects[i] = new com.mygdx.Objects.Object(atlas.createSprite("Sign"), object.getRectangle());
				objects[i].setPosition(objects[i].getPolygon().getTransformedVertices()[0],objects[i].getPolygon().getTransformedVertices()[1]);
				stage.addActor(objects[i]);
			}
		}
		
		controller = new Controller(player,collisions,objects);
		
		
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		atlas.dispose();
		bf.dispose();
		sb.dispose();
		sr.dispose();
		stage.dispose();
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}
	
	public void renderSprites(OrthogonalTiledMapRenderer renderer) {
		RectangleMapObject saracen = (RectangleMapObject) map.getLayers().get("Entities").getObjects().get("Saracen");
		
		renderer.getSpriteBatch().draw(player, player.getX(), player.getY());
		
		for(int i = 0; i < objects.length; i++) {
			//renderer.getSpriteBatch().draw(objects[i],objects[i].getPolygon().getTransformedVertices()[0],objects[i].getPolygon().getTransformedVertices()[1]);
		}

		//renderer.getSpriteBatch().draw(collisions[0],collisions[0].getPolygon().getTransformedVertices()[0],collisions[0].getPolygon().getTransformedVertices()[1]);
		renderer.getSpriteBatch().draw(atlas.createSprite("Saracen"),saracen.getRectangle().x,saracen.getRectangle().y);
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.A) {
			controller.aPressed();
		}
		else if(keycode == Keys.D) {
			controller.dPressed();
		}
		else if(keycode == Keys.W) {
			controller.wPressed();
		}
		else if(keycode == Keys.S) {
			controller.sPressed();
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.A) {
			controller.aReleased();
		}
		else if(keycode == Keys.D) {
			controller.dReleased();
		}
		else if(keycode == Keys.W) {
			controller.wReleased();
		}
		else if(keycode == Keys.S) {
			controller.sReleased();
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(!Gdx.app.getType().equals(ApplicationType.Android))
			return false;
		
		if(screenX < Gdx.graphics.getWidth()/2 && screenY > Gdx.graphics.getHeight()/2)
			controller.aPressed();
		else if(screenX > Gdx.graphics.getWidth()/2 && screenY > Gdx.graphics.getHeight()/2)
			controller.dPressed();
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(!Gdx.app.getType().equals(ApplicationType.Android))
			return false;
		
		if(screenX < Gdx.graphics.getWidth()/2 && screenY > Gdx.graphics.getHeight()/2)
			controller.aReleased();
		else if(screenX > Gdx.graphics.getWidth()/2 && screenY > Gdx.graphics.getHeight()/2)
			controller.dReleased();
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
