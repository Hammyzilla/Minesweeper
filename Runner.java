import javax.swing.JFrame;

public class Runner {
    public static void main(String[] args) {
        JFrame frame = new JFrame("");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create panel and add it to the frame
        Screen sc = new Screen();
       
        frame.add(sc);
        frame.pack();
        frame.setVisible(true);
       
        sc.animate();
    }
}

/*
PLAN
 - Custom grid size
   - User inputs size at start (DONE)
   - 20% bomb density (DONE)
 - Replayability (DONE)
 - Quit button (DONE)
 - When clicking on tile opens up menu
   - Three boxes surround selected tile
     - Dig
     - Flag (DONE)
     - Unselect (DONE)
   - When clicked set some boolean to true to allow for new hitboxes (DONE)
   - Tile is 20 x 20 pixels (DONE)
*/