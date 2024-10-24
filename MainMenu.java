import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class MainMenu {
    private Color black = new Color(0, 0, 0);
    private Color red = new Color(255, 0, 0);
    private Color grey = new Color(189, 189, 189);
    private Color white = new Color(255, 255, 255);

    private Font base;
    private Font title = new Font(Font.SERIF, Font.BOLD, 100);
    private Font selections = new Font(Font.DIALOG, Font.PLAIN, 30);
    private Font play = new Font(Font.SERIF, Font.BOLD, 50);

    public MainMenu() {}

    public void draw(Graphics g) {
        base = g.getFont();

        g.setColor(grey);
        g.fillRect(0, 0, 1280, 800);    //Background

        g.setColor(black);
        g.setFont(title);
        g.drawString("MINESWEEPER", 250, 200);

        g.setFont(selections);
        g.drawString("Width:", 500, 347);
        g.drawString("Height:", 492, 417);

        g.setFont(base);
        g.drawString("Minimum size of 5 x 5", 590, 440);
        g.drawString("Maximum size of 60w x 36h", 580, 455);

        g.setColor(red);
        g.fillRoundRect(500, 500, 200, 100, 20, 20);

        g.setColor(black);
        g.drawRoundRect(500, 500, 200, 100, 20, 20);

        g.setColor(white);
        g.setFont(play);
        g.drawString("PLAY", 530, 565);
    }
}