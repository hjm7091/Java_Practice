package game;

import java.awt.Image;

public class StarShipSprite extends Sprite{
	
	private GalagGame game;

	public StarShipSprite(GalagGame game, Image image, int x, int y) {
		super(image, x, y);
		this.game = game;
		super.setDx(0);
		super.setDx(0);
	}
	
	@Override
	public void move() {
		if((dx<0) && (x<0))
			return;
		if((dx>0) && (x>800))
			return;
		if((dy<0) && (y<0))
			return;
		if((dy>0) && (y>550))
			return;
		super.move();
	}
	
	@Override
	public void handleCollision(Sprite other) {
		if(other instanceof AlienSprite)
			game.endGame();
	}

}
