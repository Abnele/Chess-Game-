package Main;

import piece.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class PromotionGUI extends JPanel implements Runnable, ActionListener {

    // GUI Dimensions
    public static final int WIDTH = 420;
    public static final int HEIGHT = 120;

    // User Input (Unused for now)
    Mouse mouse = new Mouse();

    // Running state
    public volatile boolean running = false;

    // Buttons
    JButton knight;
    JButton bishop;
    JButton rook;
    JButton queen;

    // Launcher
    Thread promotionThread;


    // Constructor
    public PromotionGUI()
    {
        // Panel Settings
        setLayout(null);
        setBounds(200, 300 , WIDTH, HEIGHT);
        setBackground(Color.lightGray);


        // Button settings
        ImageIcon knightIcon = new ImageIcon("res/piece/WhiteKnight.png");
        knight = new JButton(knightIcon);
        knight.setLayout(null);
        knight.setBounds(10, 10, 100, 100);

        ImageIcon bishopIcon = new ImageIcon("res/piece/WhiteBishop.png");
        bishop = new JButton(bishopIcon);
        bishop.setLayout(null);
        bishop.setBounds(110, 10, 100, 100);

        ImageIcon rookIcon = new ImageIcon("res/piece/WhiteRook.png");
        rook = new JButton(rookIcon);
        rook.setLayout(null);
        rook.setBounds(210, 10, 100, 100);

        ImageIcon queenIcon = new ImageIcon("res/piece/WhiteQueen.png");
        queen = new JButton(queenIcon);
        queen.setLayout(null);
        queen.setBounds(310, 10, 100, 100);

        knight.addActionListener(this);
        bishop.addActionListener(this);
        rook.addActionListener(this);
        queen.addActionListener(this);

        add(knight);
        add(bishop);
        add(rook);
        add(queen);

        running = true;
        promotionThread = new Thread(this);
        promotionThread.start();

    }


    // Main Loop
    @Override
    public void run()
    {
        while (running) {
            /// Runs updating and redrawing methods
            update();
            repaint();
            if (!running)
            {
                break;
            }
        }
        /// Gets rid of GUI after it finishes running
        clearComponents();


    }

    // Update method
    private void update()
    {
        /// Updates GUI and whatnot idk


    }

    // GUI Repainter
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

    }

    // GUI Closer
    /// Gets rid of the GUI along with its components
    private void clearComponents()
    {
        knight = null;
        bishop = null;
        rook = null;
        queen = null;
        mouse = null;
        Main.gamePanel.remove(this);
        promotionThread.interrupt();
        promotionThread = null;
        Main.promotionGUI = null;

    }

    // User Input
    @Override
    public void actionPerformed(ActionEvent e)
    {

        Object source = e.getSource();

        ArrayList<Piece> snapshot = new ArrayList<>(GamePanel.simPieces);
        for (Piece piece : snapshot)
        {
            if (piece instanceof Pawn && piece.color != GamePanel.currentColor && ((Pawn) piece).isInPromotionSquare())
            {
                if (source == knight)
                {
                    // Set the button's image to a black knight for confirmation
                    knight.setIcon(new ImageIcon("res/piece/BlackKnight.png"));

                    // Promote the pawn
                    promotePawn((Pawn)piece, Knight.class);

                }

                if (source == bishop)
                {
                    bishop.setIcon(new ImageIcon("res/piece/BlackBishop.png"));
                    // Promote the pawn
                    promotePawn((Pawn)piece, Bishop.class);
                }

                if (source == rook)
                {
                    rook.setIcon(new ImageIcon("res/piece/BlackRook.png"));
                    // Promote the pawn
                    promotePawn((Pawn)piece, Knight.class);
                }

                if (source == queen)
                {
                    queen.setIcon(new ImageIcon("res/piece/BlackQueen.png"));
                    // Promote the pawn
                    promotePawn((Pawn)piece, Queen.class);
                }
            }
        }



    }

    /// Makes two arraylists to use as modifiers of simPieces and pieces
    ///
    /// Makes an arraylist of all promoteable piece subClasses
    ///
    /// Iterates over the list to find the class provided in the function
    ///
    /// Makes an instance of that piece
    ///
    /// Updates GamePanel's promotee and newPiece objects
    ///  <?> is part of java generics
    ///
    ///   It represents a class that will be decided when the method is called
    // Pawn Promoter
    private void promotePawn(Pawn promotee, Class<? extends Piece> pieceSubClass)
    {

        List<Piece> removesAll = new ArrayList<>();
        List<Piece> addsAll = new ArrayList<>();

        ArrayList<Class<? extends Piece>> promotedPieces = new ArrayList<>();
        promotedPieces.add(Knight.class);
        promotedPieces.add(Bishop.class);
        promotedPieces.add(Rook.class);
        promotedPieces.add(Queen.class);

        for (Class<? extends Piece> pieceType : promotedPieces)
        {
            if (pieceSubClass == pieceType)
            {
                try {
                    Piece newPiece = pieceType
                            .getDeclaredConstructor(int.class, int.class, int.class)
                            .newInstance(promotee.color, promotee.column, promotee.row);

                    Main.gamePanel.newPiece = newPiece;
                    Main.gamePanel.promotee = promotee;

                    addsAll.add(newPiece);
                    removesAll.add(promotee);

                    GamePanel.simPieces.removeAll(removesAll);
                    GamePanel.simPieces.addAll(addsAll);
                    GamePanel.pieces.removeAll(removesAll);
                    GamePanel.pieces.addAll(addsAll);


                }

                catch (Exception e){
                    e.printStackTrace();
                }

            }
        }





    }




}


