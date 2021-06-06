package jin.game;

import java.awt.Image;

public class ShotSprite extends Sprite{

	private final GalagGame game;
	
	public ShotSprite(GalagGame game, Image image, int x, int y) {
		super(image, x, y);
		this.game = game;
		setDy(-3);
	}
	
	@Override
	public void move() {
		super.move();
		if(y<-100)
			game.removeSprite(this);
	}
	
	@Override
	public void handleCollision(Sprite other) {
		if(other instanceof AlienSprite) {
			game.removeSprite(this);
			game.removeSprite(other);
		}
	}

}
