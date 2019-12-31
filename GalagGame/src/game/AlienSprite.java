package game;

import java.awt.Image;

public class AlienSprite extends Sprite{

	private GalagGame game;
	
	public AlienSprite(GalagGame game, Image image, int x, int y) {
		super(image, x, y);
		this.game = game;
		super.setDx(-3);
	}
	
	@Override
	public void move() {
		if(((dx<0) && (x<10)) || ((dx>0) && (x>800))) {
			setDx( -1 * getDx());
			setY(getY() + 10);
			if(y>600)
				game.endGame();
		}
		super.move();
	}

}
