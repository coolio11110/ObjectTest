package com.mygdx.Objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Object extends Actor{
	Rectangle bounds;
	Polygon polygon;
	TextureRegion texture;
	InputListener clicked;
	
	public Object(Rectangle rectMapObject) {
		bounds = new Rectangle(0,0,rectMapObject.getWidth()-8,rectMapObject.getHeight()-8);
		polygon = new Polygon(new float[]{0,0,bounds.width,0,bounds.width,bounds.height,0,bounds.height});
		polygon.setPosition(rectMapObject.x,rectMapObject.y);
	}
	
	public Object(TextureRegion textureRegion, Rectangle rectMapObject) {
		texture = textureRegion;
		setBounds(getX(),getY(),texture.getRegionWidth(), texture.getRegionHeight());
		
		bounds = new Rectangle(0,0,rectMapObject.getWidth()-8,rectMapObject.getHeight()-8);
		polygon = new Polygon(new float[]{0,0,bounds.width,0,bounds.width,bounds.height,0,bounds.height});
		polygon.setPosition(rectMapObject.x,rectMapObject.y);
		
		this.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttons){
                System.out.println("Touched");
                setVisible(false);
                return true;
            }
        });
	}
	
	public void draw(Batch batch, float alpha){
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());
    }
	
	public Polygon getPolygon() {
		polygon.setRotation(getRotation());
		return polygon;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
}
