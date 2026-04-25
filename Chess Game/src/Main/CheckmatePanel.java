package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CheckmatePanel extends JPanel implements Runnable, ActionListener {

    /** This class creates a 'GAME OVER' screen
     * once the king is checkmated
     *
     */

    // Dimensions
    private final int WIDTH = 420;
    private final int HEIGHT = 120;

    // Launcher
    Thread checkmateThread;

    // Starter
    public volatile boolean running = false;

    // Buttons
    JButton restart;
    JButton quit;
    JButton home;

    // Colors
    public static int losingColor = GamePanel.losingColor;





    // Constructor
    public CheckmatePanel()
    {
        // Panel Settings
        setLayout(null);
        setBounds(200, 300 , WIDTH, HEIGHT);
        setBackground(Color.BLACK);

        // Panel Add-ons
        JLabel gameOver = new JLabel("GAME OVER");
        gameOver.setForeground(Color.GREEN);
        gameOver.setFont(new Font("Consolas", Font.BOLD, 80));
        gameOver.setBounds(300, 300, WIDTH, HEIGHT);
        this.add(gameOver);

        JLabel winner = new JLabel(getWinningColor() + " WINS");
        winner.setForeground(Color.GREEN);
        winner.setFont(new Font("Consolas", Font.BOLD, 50));
        winner.setBounds(300, 400, WIDTH, HEIGHT);
        this.add(winner);

        // Button settings
        ImageIcon buttonImage = new ImageIcon("res/buttons/buttonTexture.png");
        Font cursiveFont = new Font("Segoe Script", Font.ITALIC, 40);
        restart = new JButton(buttonImage);
        restart.setLayout(null);
        restart.setBounds(125, 550, 350, 70);
        restart.setFont(cursiveFont);
        restart.setText("Restart");
        restart.setHorizontalTextPosition(SwingConstants.CENTER);
        restart.setVerticalTextPosition(SwingConstants.CENTER);
        restart.addActionListener(this);
        add(restart);


        ImageIcon quitImage = new ImageIcon("res/buttons/buttonTexture.png");
        cursiveFont = new Font("Segoe Script", Font.ITALIC, 40);
        quit = new JButton(buttonImage);
        quit.setLayout(null);
        quit.setBounds(625, 550, 350, 70);
        quit.setFont(cursiveFont);
        quit.setText("Quit");
        quit.setHorizontalTextPosition(SwingConstants.CENTER);
        quit.setVerticalTextPosition(SwingConstants.CENTER);
        quit.addActionListener(this);
        add(quit);




        // Panel Thread
        checkmateThread = new Thread(this);
        checkmateThread.start();

    }

    // Run Override
    @Override
    public void run()
    {
        while (running)
        {
            repaint();
            if (!running)
            {
                break;
            }
        }


    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if (source == restart){
            Main.screen.remove(Main.checkmatePanel);
            Main.homeScreen = new HomeScreen();
            Main.homeScreen.setLayout(null);
            Main.homeScreen.start();
            Main.screen.add(Main.homeScreen);
            clearComponents();
            Main.screen.revalidate();
            running = false;

        }

        if (source == quit){
            running = false;
            quitGame();
        }
    }

    private String getWinningColor(){

        if (losingColor == GamePanel.WHITE){
            return "BLACK";
        }
        else if(losingColor == GamePanel.BLACK){
            return "WHITE";
        }
        else{
            return "NOBODY";
        }
    }

    private void clearComponents(){
        Main.checkmatePanel.remove(restart);
        Main.checkmatePanel.remove(quit);
        restart = null;
        quit = null;
        checkmateThread.interrupt();
        checkmateThread = null;
        Main.checkmatePanel = null;

    }
    /// Exits the game
    private void quitGame(){
        System.exit(0);
    }
}
