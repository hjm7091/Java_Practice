package jin.game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class GalagGame extends JPanel implements KeyListener{

	private final long GAME_SPEED = 10L;
	private final List<Sprite> sprites = new CopyOnWriteArrayList<>();
	private Sprite starship;
	
	private BufferedImage alienImage;
	private BufferedImage shotImage;
	private BufferedImage shipImage;

	private boolean end = false;
	
	public GalagGame() {
		JFrame frame = new JFrame("Galag Game");
		frame.setSize(850, 620);
		frame.add(this);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		try {
			shotImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/fire.png")));
			shipImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/starship.png")));
			alienImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/alien.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.requestFocus();
		this.initSprites();
		addKeyListener(this);
	}
	
	private void initSprites() {
		starship = new StarShipSprite(this, shipImage, 370, 550);
		sprites.add(starship);
		for(int y=0; y<5; y++) {
			for(int x=0; x<12; x++) {
				Sprite alien = new AlienSprite(this, alienImage, 100 + (x * 50), 50 + (y * 30));
				sprites.add(alien);
			}
		}
	}
	
	public void endGame() {
		System.out.println( "end." );
		this.end = true;
		new EndBox();
	}

	public void removeSprite(Sprite sprite) {
		sprites.remove(sprite);
	}
	
	public void fire() {
		ShotSprite shot = new ShotSprite(this, shotImage, starship.getX() + 10, starship.getY() - 30);
		sprites.add(shot);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, 850, 620);
		for (Sprite sprite : sprites) {
			sprite.draw(g);
		}
	}
	
	public void gameLoop() {
		while(!end) {

			if(killAll()) {
				endGame();
			}

			try {

				moveSprites();
				compareSprites();
				repaint();

				Thread.sleep(GAME_SPEED);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void moveSprites() {
		for (Sprite sprite : sprites) {
			sprite.move();
		}
	}

	private void compareSprites() throws InterruptedException {
		for(int p=0; p<sprites.size(); p++) {
			for(int s=p+1; s<sprites.size(); s++) {
				Sprite me = sprites.get(p);
				Sprite other = sprites.get(s);

				if(!end && me.checkCollision(other)) {
					me.handleCollision(other);
					other.handleCollision(me);
				}
			}
		}

//		if (killAll())
//			throw new InterruptedException("kill all.");
	}

	public boolean killAll() {
		return sprites.size() == 1;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			starship.setDx(-3);
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			starship.setDx(+3);
		if(e.getKeyCode() == KeyEvent.VK_UP)
			starship.setDy(-3);
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			starship.setDy(+3);
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
			fire();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			starship.setDx(0);
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			starship.setDx(0);
		if(e.getKeyCode() == KeyEvent.VK_UP)
			starship.setDy(0);
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			starship.setDy(0);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		GalagGame g = new GalagGame();
		g.gameLoop();
	}
}

class EndBox extends JFrame{

	public EndBox() {
		setTitle("종료");
		JLabel label = new JLabel("게임종료!!");
		add(label, BorderLayout.CENTER);
		label.setHorizontalAlignment(JLabel.CENTER);
		setSize(300, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
