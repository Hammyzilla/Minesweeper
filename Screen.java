import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen extends JPanel implements MouseListener {
    private Color black = new Color(0, 0, 0);

    private MainMenu menu = new MainMenu();
    private GameScreen gameBG = new GameScreen();

    private Tile[][] gameBoard;

    private JTextField widthInput;
    private JTextField heightInput;

    private Boolean displayingMenu = true;
    private Boolean tileSelected = false;
    private Boolean gameActive;

    private int height;
    private int width;
    private int safeTilesLeft;

    public Screen() {
        setLayout(null);
        
        widthInput = new JTextField();
        widthInput.setBounds(600, 325, 100, 25);
        this.add(widthInput);

        heightInput = new JTextField();
        heightInput.setBounds(600, 395, 100, 25);
        this.add(heightInput);

        addMouseListener(this);

        setFocusable(true);
    }

    public Dimension getPreferredSize() {
        return new Dimension(1280, 800);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (displayingMenu) {
            widthInput.setVisible(true);
            heightInput.setVisible(true);

            menu.draw(g);
        } else {
            widthInput.setVisible(false);
            heightInput.setVisible(false);

            gameBG.draw(g);

            for (int i = 0; i < gameBoard.length; i ++) {
                for (int j = 0; j < gameBoard[i].length; j ++) {
                    gameBoard[i][j].draw(g);

                    if (!gameActive && gameBoard[i][j].checkIfBomb()) {
                        gameBoard[i][j].changeMode(3);
                    }
                }
            }

            if (!gameActive) {
                g.setColor(black);
                g.drawString("YOU DIED", 5, 20);
            }

            if (safeTilesLeft == 0) {
                gameActive = false;

                g.setColor(black);
                g.drawString("YOU WIN", 5, 20);
            }
        }
    }

    public void animate() {
		while(true) {
            try {
                Thread.sleep(100);     //1 millisecond
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

			repaint();
		}
	}

    public void mousePressed(MouseEvent e) {
        //System.out.println("X: " + e.getX() + ", Y: " + e.getY());    //Print location of x and y

        if (e.getX() >= 500 && e.getX() <= 700 && e.getY() >= 500 && e.getY() <= 600 && displayingMenu) {    //Play button
            height = Integer.valueOf(heightInput.getText());
            width = Integer.valueOf(widthInput.getText());

            if (height <= 4 || width <= 4) {    //Default size
                height = 16;
                width = 30;
            } else if (height > 36 || width > 60) {
                height = 36;
                width = 60;
            }

            gameBoard = new Tile[height + 2][width + 2];
            for (int i = 0; i < gameBoard.length; i ++) {
                for (int j = 0; j < gameBoard[i].length; j ++) {
                    if (i == 0 || i == gameBoard.length - 1 || j == 0 || j == gameBoard[i].length - 1) {
                        gameBoard[i][j] = new Tile(-30, -30);
                    } else {
                        gameBoard[i][j] = new Tile(60 + ((j - 1) * 20), 60 + ((i - 1) * 20));
                    }
                }
            }

            int numBombs = (height * width) / 5;
            safeTilesLeft = (height * width) - numBombs;

            while (numBombs != 0) {
                int gridX = (int)(Math.random() * width + 1);
                int gridY = (int)(Math.random() * height + 1);

                if (gameBoard[gridY][gridX].checkIfBomb() == false) {
                    gameBoard[gridY][gridX].setBomb();
                    numBombs --;
                }
            }

            for (int i = 1; i < gameBoard.length - 1; i ++) {
                for (int j = 1; j < gameBoard[i].length - 1; j ++) {
                    int numOfSurroundingBombs = 0;

                    if (gameBoard[i - 1][j - 1].checkIfBomb()) {
                        numOfSurroundingBombs ++;
                    }
                    if (gameBoard[i - 1][j].checkIfBomb()) {
                        numOfSurroundingBombs ++;
                    }
                    if (gameBoard[i - 1][j + 1].checkIfBomb()) {
                        numOfSurroundingBombs ++;
                    }
                    if (gameBoard[i][j + 1].checkIfBomb()) {
                        numOfSurroundingBombs ++;
                    }
                    if (gameBoard[i + 1][j + 1].checkIfBomb()) {
                        numOfSurroundingBombs ++;
                    }
                    if (gameBoard[i + 1][j].checkIfBomb()) {
                        numOfSurroundingBombs ++;
                    }
                    if (gameBoard[i + 1][j - 1].checkIfBomb()) {
                        numOfSurroundingBombs ++;
                    }if (gameBoard[i][j - 1].checkIfBomb()) {
                        numOfSurroundingBombs ++;
                    }

                    gameBoard[i][j].setNumBombs(numOfSurroundingBombs);
                }
            }

            displayingMenu = !displayingMenu;
            gameActive = true;
        } else if (e.getX() >= 1225 && e.getX() <= 1275 && e.getY() >= 5 && e.getY() <= 25 && !displayingMenu) {     //Replay button
            displayingMenu = !displayingMenu;
        }

        //Tiles
        if (!displayingMenu) {
            for (int i = 0; i < gameBoard.length; i ++) {
                for (int j = 0; j < gameBoard[i].length; j ++) {
                    if (e.getX() >= gameBoard[i][j].getX() && e.getX() <= gameBoard[i][j].getX() + 20 && e.getY() >= gameBoard[i][j].getY() && e.getY() <= gameBoard[i][j].getY() + 20 && (gameBoard[i][j].getMode() == 1 || gameBoard[i][j].getMode() == 4) && tileSelected == false && gameActive) {
                        gameBoard[i][j].changeMode(2);    //Selecting tiles
                        tileSelected = true;
                    } else if (e.getX() >= gameBoard[i][j].getX() - 20 && e.getX() <= gameBoard[i][j].getX() && e.getY() >= gameBoard[i][j].getY() && e.getY() <= gameBoard[i][j].getY() + 20 && gameBoard[i][j].getMode() == 2 && tileSelected) {
                        gameBoard[i][j].changeMode(1);    //Unselecting tile
                        tileSelected = false;
                    } else if (e.getX() >= gameBoard[i][j].getX() && e.getX() <= gameBoard[i][j].getX() + 20 && e.getY() >= gameBoard[i][j].getY() - 20 && e.getY() <= gameBoard[i][j].getY() && gameBoard[i][j].getMode() == 2 && tileSelected) {
                        gameBoard[i][j].changeMode(4);    //Flagging tile
                        tileSelected = false;
                    } else if (e.getX() >= gameBoard[i][j].getX() - 20 && e.getX() <= gameBoard[i][j].getX() && e.getY() >= gameBoard[i][j].getY() - 20 && e.getY() <= gameBoard[i][j].getY() && gameBoard[i][j].getMode() == 2 && tileSelected) {
                        gameBoard[i][j].changeMode(3);    //Digging tile
                        tileSelected = false;

                        if (gameBoard[i][j].checkIfBomb()) {
                            gameActive = false;
                        } else {
                            safeTilesLeft --;

                            if (gameBoard[i][j].getSurroundingBombs() == 0) {
                                if (!gameBoard[i - 1][j - 1].checkIfBomb()) {
                                    gameBoard[i - 1][j - 1].changeMode(3);
                                }
                                if (!gameBoard[i - 1][j].checkIfBomb()) {
                                    gameBoard[i - 1][j].changeMode(3);
                                }
                                if (!gameBoard[i - 1][j + 1].checkIfBomb()) {
                                    gameBoard[i - 1][j + 1].changeMode(3);
                                }
                                if (!gameBoard[i][j + 1].checkIfBomb()) {
                                    gameBoard[i][j + 1].changeMode(3);
                                }
                                if (!gameBoard[i + 1][j + 1].checkIfBomb()) {
                                    gameBoard[i + 1][j + 1].changeMode(3);
                                }
                                if (!gameBoard[i + 1][j].checkIfBomb()) {
                                    gameBoard[i + 1][j].changeMode(3);
                                }
                                if (!gameBoard[i + 1][j - 1].checkIfBomb()) {
                                    gameBoard[i + 1][j - 1].changeMode(3);
                                }
                                if (!gameBoard[i][j - 1].checkIfBomb()) {
                                    gameBoard[i][j - 1].changeMode(3);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
}