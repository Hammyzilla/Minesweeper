import java.awt.Graphics;
import java.awt.Color;

public class Tile {
    private Color black = new Color(0, 0, 0);
    private Color red = new Color(255, 0, 0);
    private Color grey = new Color(130, 130, 130);
    private Color lightGrey = new Color(175, 175, 175);
    private Color white = new Color(255, 255, 255);

    private Boolean isBomb;

    private int x;
    private int y;
    private int mode;
    private int surroundingBombs;

    public Tile(int xI, int yI) {
        x = xI;
        y = yI;
        mode = 1;    //1: Untouched  2: Selected  3: Dug  4: Flagged
        isBomb = false;
    }

    public void draw(Graphics g) {
        if (mode == 1) {    //Untouched
            g.setColor(lightGrey);
            g.fillRect(x, y, 20, 20);
            
            g.setColor(black);
            g.drawRect(x, y, 20, 20);
        } else if (mode == 2) {    //Selected
            g.setColor(grey);
            g.fillRect(x - 20, y - 20, 40, 40);

            g.setColor(lightGrey);
            g.fillRect(x, y, 20, 20);

            g.setColor(black);
            g.drawRect(x - 20, y - 20, 20, 20);
            g.drawRect(x, y - 20, 20, 20);
            g.drawRect(x - 20, y, 20, 20);
            g.drawRect(x, y, 20, 20);
            g.drawString("D", x - 15, y - 5);
            g.drawString("F", x + 5, y - 5);
            g.drawString("X", x - 15, y + 15);
        } else if (mode == 3) {    //Dug
            if (isBomb) {    //Bomb
                g.setColor(grey);
                g.fillRect(x, y, 20, 20);

                g.setColor(black);
                g.drawRect(x, y, 20, 20);
                g.fillRect(x + 5, y + 2, 10, 16);
                g.fillRect(x + 2, y + 5, 16, 10);
            } else {    //Safely dug
                g.setColor(grey);
                g.fillRect(x, y, 20, 20);

                g.setColor(black);
                g.drawRect(x, y, 20, 20);
                g.drawString("" + surroundingBombs, x + 8, y + 15);
            }
        } else if (mode == 4) {    //Flagged
            g.setColor(lightGrey);
            g.fillRect(x, y, 20, 20);

            g.setColor(black);
            g.drawRect(x, y, 20, 20);
            g.drawLine(x + 5, y + 5, x + 5, y + 15);

            g.setColor(red);
            
            int[] triangleX = {x + 6, x + 15, x + 6};
            int[] triangleY = {y + 5, y + 7, y + 10};

            g.fillPolygon(triangleX, triangleY, 3);
        }
    }

    public void changeMode(int modeI) {
        mode = modeI;
    }

    public void setBomb() {
        isBomb = true;
    }

    public void setNumBombs(int surroundingBombsI) {
        surroundingBombs = surroundingBombsI;
    }

    public int getMode() {
        return mode;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSurroundingBombs() {
        return surroundingBombs;
    }

    public Boolean checkIfBomb() {
        return isBomb;
    }
}