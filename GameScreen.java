import java.awt.Graphics;
import java.awt.Color;

public class GameScreen {
    private Color black = new Color(0, 0, 0);
    private Color grey = new Color(120, 120, 120);
    private Color lightGrey = new Color(200, 200, 200);
    private Color cloudyWhite = new Color(235, 235, 235);
    private Color white = new Color(255, 255, 255);

    public GameScreen() {}

    public void draw(Graphics g) {
        g.setColor(lightGrey);
        g.fillRect(0, 0, 1280, 800);

        g.setColor(grey);
        g.fillRect(0, 0, 1280, 30);
        
        g.setColor(black);
        g.drawRect(0, 0, 1280, 30);

        g.setColor(cloudyWhite);
        g.fillRect(1225, 5, 50, 20);

        g.setColor(black);
        g.drawRect(1225, 5, 50, 20);
        g.drawString("Replay", 1232, 20);
    }
}