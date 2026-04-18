package Main;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

import piece.*;


public class Main {
    // Booleans
    public static boolean gameIsRunning;
    // GamePanel Instance
    public static volatile GamePanel gamePanel = new GamePanel();
    // CheckmatePanel Instance
    public static CheckmatePanel checkmatePanel = null;
    // GUI Instance
    public static PromotionGUI promotionGUI = null;
    // Layout
    public static FlowLayout layout = new FlowLayout(FlowLayout.CENTER);

    public static JFrame screen = new JFrame("Chess Game By Abnel");
    public static void main(String [] args){

        // Debugging Settings
        boolean runDebuggingLoops = true;
        boolean runPieceLoop = false;
        boolean runKingLoop = false;
        boolean runGUILoop = false;
        boolean runCheckmateLoop = false;




        // Window Settings
        screen.setMinimumSize(new Dimension(1000, 800));
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setResizable(false);



        // Add the game panel to the screen
        gamePanel.setLayout(null); /// Gets rid of layouts to manually add GUI's in the future
        screen.add(gamePanel);
        screen.pack(); /// Makes the screen adjust its size to the panel




        screen.setLocationRelativeTo(null); /// Puts the window on the center of the screen
        screen.setVisible(true); /// Makes the screen visible
        gamePanel.launchGame(); /// Launches the game
        gameIsRunning = true;


        // Debugging Loops
        if (runDebuggingLoops)
        {
            if (runGUILoop)
            {
                promotionGUI = new PromotionGUI();
                gamePanel.add(promotionGUI);

            }

            if (runPieceLoop)
            {
                for (Piece piece : GamePanel.simPieces)
                {
                    if (runKingLoop)
                    {
                        if (piece instanceof King && piece.color == GamePanel.WHITE)
                        {
                            System.out.println("KingColumn Loop:");
                            System.out.println("    KingColumn: " + piece.column);
                            System.out.println("    RookColumn based on king: " + (piece.column + 3));

                        }
                    }

                    if (piece instanceof Rook && (piece.startColumn == piece.column && piece.startRow == piece.row )
                            && piece.color == GamePanel.WHITE)
                    {
                        System.out.println("White starting position rook found @ column: " + piece.column + " startColumn: " + piece.startColumn + " row: " + piece.row + " startRow: " + piece.startRow);
                    }
                }
            }



        }

















    }


}

