package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeScreen extends JPanel implements Runnable, ActionListener {


    // Dimensions
    public static final int WIDTH = 1100;
    public static final int HEIGHT = 800;

    // Launcher
    Thread homeThread;

    // Starter
    private boolean running = false;

    // Buttons
    JButton start;
    JButton quit;

    // Constructor
    public HomeScreen(){
        // Panel Settings
        setLayout(null);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);

        // Panel Add-ons
        JLabel title = new JLabel("Chess Game");
        title.setForeground(Color.WHITE);
        Font cursiveFont = new Font("Segoe Script", Font.ITALIC, 80);
        title.setFont(cursiveFont);
        title.setBounds(300, -250, WIDTH, HEIGHT);
        this.add(title);

        JLabel author = new JLabel("by: Abnele");
        author.setForeground(Color.GREEN);
        cursiveFont = new Font("Segoe Script", Font.ITALIC, 20);
        author.setFont(cursiveFont);
        author.setBounds(350, -200, WIDTH, HEIGHT);
        this.add(author);

        // Button settings
        ImageIcon buttonImage = new ImageIcon("res/buttons/buttonTexture.png");
        cursiveFont = new Font("Segoe Script", Font.ITALIC, 40);
        start = new JButton(buttonImage);
        start.setLayout(null);
        start.setBounds(125, 500, 350, 70);
        start.setFont(cursiveFont);
        start.setText("Start");
        start.setHorizontalTextPosition(SwingConstants.CENTER);
        start.setVerticalTextPosition(SwingConstants.CENTER);
        start.addActionListener(this);
        add(start);

        ImageIcon quitImage = new ImageIcon("res/buttons/buttonTexture.png");
        cursiveFont = new Font("Segoe Script", Font.ITALIC, 40);
        quit = new JButton(buttonImage);
        quit.setLayout(null);
        quit.setBounds(625, 500, 350, 70);
        quit.setFont(cursiveFont);
        quit.setText("Quit");
        quit.setHorizontalTextPosition(SwingConstants.CENTER);
        quit.setVerticalTextPosition(SwingConstants.CENTER);
        quit.addActionListener(this);
        add(quit);


    }



    @Override
    public void run() {
        long drawingIterations = 0;
        System.out.println("Home screen active...");
        while (running)
        {
            try {
                homeThread.sleep(10);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            repaint();
            drawingIterations++;
            System.out.print("\rDrawing Iterations: " + drawingIterations);
            if (!running)
            {
                System.out.println();
                System.out.println("Home screen closed");
                clearComponents();
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

        if (source == start){
            running = false;
        }

        if (source == quit){
            running = false;
            quitGame();
        }

    }


    /// Gets rid of the home screen
    /// and its components
    ///
    /// Then Starts the game
    private void clearComponents(){
        Main.screen.remove(this);
        Main.gamePanel = new GamePanel();
        Main.gamePanel.setLayout(null);
        Main.screen.add(Main.gamePanel);
        Main.gamePanel.gameIsOver = false;
        Main.gamePanel.launchGame();
        Main.gameIsRunning = true;
        start = null;
        quit = null;
        homeThread.interrupt();
        homeThread = null;

    }



    /// Exits the game
    private void quitGame(){
        System.exit(0);
    }

    public void start(){
        homeThread = new Thread(this);
        running = true;
        homeThread.start();
    }
}
