package Main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import piece.*;

public class GamePanel extends JPanel implements Runnable {

    public static final int WIDTH = 1100;
    public static final int HEIGHT = 800;
    final int FPS = 60;
    public Thread gameThread;
    Board board = new Board();
    Mouse mouse = new Mouse();

    // PIECES
    public static ArrayList<Piece> pieces = new ArrayList();
    public static ArrayList<Piece> simPieces = new ArrayList();
    public Piece activePiece;

    // Promotion
    Pawn promotee;
    Piece newPiece;




    // COLOR
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    public static int currentColor = WHITE;
    public static int losingColor;

    // BOOLEANS
    boolean canMove;
    boolean validSquare;
    public boolean gameIsOver;


    /// Debug Booleans
    boolean castledRookExists;
    public boolean isOnSquaresAroundIt;
    public boolean isInCheck;
    public boolean movedTwoSquares;
    public boolean isInPromotionSquare;
    public boolean promotionGUIExists;
    public boolean isInCheckMate;
    public boolean isInStaleMate;
    public boolean moveablePieceExists;
    public boolean CheckmatePanelExists;

    /// Debug Pieces
    public Piece moveablePiece;
    public ArrayList<Piece> checkingPieces = new ArrayList<>();


    /// Debug Integers
    public int moveableCol;
    public int moveableRow;


    // Constructor
    public GamePanel()
    {
        currentColor = WHITE;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        addMouseMotionListener(mouse);
        addMouseListener(mouse);
        setPieces();
        copyPieces(pieces, simPieces);



    }

    // Voids
    private void update()
    {

        synchronized (simPieces)
        {
            // If Mouse Button Pressed
            // And there is no active piece
            if (mouse.pressed && activePiece == null)
            {
                // For every piece currently on the board
                for (Piece piece : simPieces)
                {
                    // If the piece your mouse is on is an ally piece
                    if (piece.color == currentColor &&
                            piece.column == mouse.x/Board.SQUARE_SIZE &&
                            piece.row == mouse.y/Board.SQUARE_SIZE)
                    {
                        // Make the piece your mouse is on the active piece
                        activePiece = piece;
                    }


                }
            }

            else if (activePiece != null)
            {
                dragPiece();

            }

            // If Mouse Button Released
            // And there is an active piece
            if (!mouse.pressed && activePiece != null)
            {
                // And if the piece can move to the square hovered
                if (validSquare)
                {

                    // Then move the piece to that square
                    // And update the new position
                    copyPieces(simPieces,pieces);
                    activePiece.updatePosition();
                    activePiece = null;




                    // If white just moved a piece
                    if (currentColor == WHITE)
                    {
                        // Then it is black's turn
                        currentColor = BLACK;
                    }


                    else
                    {
                        // Otherwise, it is white's turn
                        currentColor = WHITE;
                    }
                }

                else
                {
                    copyPieces(pieces, simPieces);
                    activePiece.resetPosition();
                    activePiece = null;

                }





            }
        }

        gameOver();
        setPromotionGUI();




    }

    public void dragPiece()
    {

        canMove = false;
        validSquare = false;

        copyPieces(pieces,simPieces);


        // When a piece is held, update its position
        activePiece.x = mouse.x - Board.HALF_SQUARE_SIZE;
        activePiece.y = mouse.y - Board.HALF_SQUARE_SIZE;
        activePiece.row = activePiece.getRow(activePiece.y);
        activePiece.column = activePiece.getColumn(activePiece.x);

        // Check if the piece is hovering a valid square
        if (activePiece.canMove(activePiece.column,activePiece.row))
        {
            canMove = true;
            activePiece.hoveredPiece = activePiece.getHoveredPiece(activePiece.column, activePiece.row);

            // If the piece is hovering another, remove it from the simpiece list
            if (activePiece.hoveredPiece != null)

            {
                simPieces.remove(activePiece.hoveredPiece.getIndex());
            }

            validSquare = true;

        }

        else
        {
            validSquare = false;
            canMove = false;
        }





    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;


        // BOARD
        board.draw(g2);


        /// Uses Snapshot of the board to draw pieces
        /// This prevents paintComponent from iterating over simPieces
        /// While another thread modifies it
        ArrayList<Piece> snapshot = new ArrayList<>(simPieces);
        // PIECES
        for (Piece p : snapshot) // Update pieces on the board
        {
            p.draw(g2);
        }

        if (activePiece != null)
        {
            if (validSquare)
            {
                g2.setColor(Color.white);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                g2.fillRect(activePiece.column * Board.SQUARE_SIZE, activePiece.row * Board.SQUARE_SIZE,
                        Board.SQUARE_SIZE,Board.SQUARE_SIZE);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }


            activePiece.draw(g2);
        }




    }

    public void launchGame()
    {
        gameThread = new Thread(this);
        gameIsOver = false;
        gameThread.start();
    }

    @Override
    public void run()
    {
        // Game Loop
        // Test variable for updates
        int updateIterations = 0;
        // Interval of ever 1/60 of a second
        double drawInterval = 1000000000/FPS;
        double delta = 0; // Will determine if interval is met
        long lastTime = System.nanoTime(); //Gets the play time for future comparison
        long currentTime;

        System.out.println("Game is running...");
        while(!gameIsOver){

            currentTime = System.nanoTime(); // Gets the current play time

            // Compares the time elapsed with the update interval via division
            delta += (currentTime - lastTime)/drawInterval;
            lastTime = currentTime; // Sets the new past time

            // Runs when the interval is met
            if (delta >= 1) {
                // Updates information such as piece positions
                update();

                // List-based debug loops
                for (Piece piece : simPieces)
                {
                    if (piece instanceof King)
                    {
                        if (((King) piece).castledRook != null)
                        {
                            castledRookExists = true;
                        }
                        if ((((King) piece).isOnSquaresAroundIt == true))
                        {
                            isOnSquaresAroundIt = true;
                            ((King) piece).castledRook = null;
                        }
                        if (((King) piece).isInCheck())
                        {
                            isInCheck = true;
                            checkingPieces = ((King) piece).checkingPieces;
                        }

                    }
                    if (piece instanceof Pawn)
                    {
                        if (((Pawn) piece).movedTwoSquares)
                        {
                            movedTwoSquares = true;
                        }
                        if (((Pawn) piece).isInPromotionSquare())
                        {
                            isInPromotionSquare = true;
                        }
                    }
                    if (Main.promotionGUI != null)
                    {
                        promotionGUIExists = true;
                    }


                }

                // Non-list-based debug loops
                {
                    if (kingIsCheckmated())
                    {
                        isInCheckMate = true;

                    }
                    if (kingIsStalemated()){
                        isInStaleMate = true;
                    }
                    if (moveablePieceExists())
                    {
                        moveablePieceExists = true;
                    }

                }

                //Shows the amount of times information is updated + Debug loops
                System.out.print("\rUpdate Iterations: "  + updateIterations + " IsInStaleMate: "  + isInStaleMate);
                updateIterations++;

                // List based debug loops

                for (Piece piece : simPieces)
                {
                    if ((piece instanceof King))
                    {
                        if (((King) piece).castledRook != null)
                        {
                            castledRookExists = false;
                        }
                        if ((((King) piece).isOnSquaresAroundIt != true))
                        {
                            isOnSquaresAroundIt = false;
                        }
                        if (!(((King) piece).isInCheck()))
                        {
                            isInCheck = false;
                            checkingPieces = null;
                        }

                    }
                    if (piece instanceof Pawn)
                    {
                        if (!(((Pawn) piece).movedTwoSquares))
                        {
                            movedTwoSquares = false;
                        }

                        if (!(((Pawn) piece).isInPromotionSquare()))
                        {
                            isInPromotionSquare = false;
                        }
                    }

                    if (Main.promotionGUI == null)
                    {
                        promotionGUIExists = false;
                    }

                }

                // Non-list-based debug loops
                {
                    if (!kingIsCheckmated()) {
                        isInCheckMate = false;
                    }
                    if (!kingIsStalemated()) {
                        isInStaleMate = false;
                    }
                    if (!moveablePieceExists()) {
                        moveablePieceExists = false;
                    }
                }




                // Draws new information out such as piece positions
                repaint();
                delta--; // Resets the determiner of update interval




            }
            if (gameIsOver){
                gameThread = null;
                Main.gamePanel = null;


            }
        }

    }

    public void setPieces()
    {
        //WHITE PAWNS
        for (int i = 0; i<=7; i++){
            pieces.add(new Pawn(WHITE, i, 6));
        }

        //WHITE PIECES
        pieces.add(new Rook(WHITE, 0, 7));
        pieces.add(new Rook(WHITE, 7, 7));
        pieces.add(new Knight(WHITE, 1, 7));
        pieces.add(new Knight(WHITE, 6, 7));
        pieces.add(new Bishop(WHITE, 2, 7));
        pieces.add(new Bishop(WHITE, 5, 7));
        pieces.add(new Queen(WHITE, 3, 7));
        pieces.add(new King(WHITE, 4, 7));


        // BLACK PAWNS
        for (int i = 0; i<=7; i++){
            pieces.add(new Pawn(BLACK, i, 1));
        }

        //BLACK PIECES
        pieces.add(new Rook(BLACK, 0, 0));
        pieces.add(new Rook(BLACK, 7, 0));
        pieces.add(new Knight(BLACK, 1, 0));
        pieces.add(new Knight(BLACK, 6, 0));
        pieces.add(new Bishop(BLACK, 2, 0));
        pieces.add(new Bishop(BLACK, 5, 0));
        pieces.add(new Queen(BLACK, 3, 0));
        pieces.add(new King(BLACK, 4, 0));





    }

    private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target)
    {

        synchronized (target)
        {
            target.clear();
            for (int i = 0; i<=source.size()-1; i++){
                target.add(source.get(i));
            }
        }


    }


    // Promotion GUI
    /// Checks if a pawn is promoting
    /// Then provides a GUI for the player to choose their promoted piece
    /// Will promote the pawn once the player chooses their piece
    public void setPromotionGUI()
    {

        for (Piece piece : GamePanel.simPieces)
        {
            // If a pawn has reached a promotion square, and there is no GUI
            if (piece instanceof Pawn && ((Pawn) piece).isInPromotionSquare()
                    && Main.promotionGUI == null && (promotee == null && newPiece == null))
            {
                Main.promotionGUI = new PromotionGUI();
                Main.gamePanel.add(Main.promotionGUI);

            }

            // If there is a GUI, but the pawn hasn't promoted
            else if (Main.promotionGUI != null && piece instanceof Pawn)
            {/* Do nothing
                 Wait for the player to pick their piece*/}

            else if (Main.promotionGUI != null && (promotee != null && newPiece != null))
            {
                closePromotion();
            }
        }
        revalidate();




    }

    // Pawn Promotion Cleanup
    public void closePromotion()
    {
        Main.promotionGUI.running = false;
        promotee = null;
        newPiece = null;

    }

    // Game Over
    public void gameOver()
    {
        if (isInCheckMate || isInStaleMate)
        {

            Main.checkmatePanel = null;
            Main.checkmatePanel = new CheckmatePanel();
            CheckmatePanelExists = true;
            removeComponents();
            revalidate();

        }
    }

    /// Closes the program after a game over
    private void removeComponents(){
        Main.screen.add(Main.checkmatePanel);
        Main.screen.remove(this);
        gameIsOver = true;
        Main.gameIsRunning = false;
        pieces = new ArrayList<>();
        simPieces = new ArrayList<>();
        gameThread.interrupt();
        Main.screen.revalidate();
        Main.screen.repaint();


    }


    // Booleans
    public boolean squareIsOccupied(int column, int row)
    {
        int numberOfPiecesOnSquare = 0;

        // For every piece currently on the board
        for (Piece piece : simPieces)
        {
            // If any of them are in the square speicified in the function
            if ((piece.column == column && piece.row == row) && piece!= activePiece)
            {
                // Then the variable increases
                numberOfPiecesOnSquare++;
            }

            else
            {}

        }

        if (numberOfPiecesOnSquare>0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public boolean squareIsOccupiedByEnemyPiece(int column, int row)
    {
        int numberOfPiecesOnSquare = 0;

        // For every piece currently on the board
        for (Piece piece : simPieces)
        {
            // If any of them are in the square speicified in the function
            // Without being an ally piece
            if ((piece.column == column && piece.row == row) && piece!= activePiece && piece.color != currentColor)
            {
                // Then the variable increases
                numberOfPiecesOnSquare++;
            }

            else
            {}

        }

        if (numberOfPiecesOnSquare == 1)
        {
            return true;
        }
        else if (numberOfPiecesOnSquare>1)
        {
            System.out.println("ERROR");
            return false;
        }
        else
        {
            return false;
        }

    }

    public boolean squareIsOccupiedByAllyPiece(int column, int row)
    {
        int numberOfPiecesOnSquare = 0;

        // For every piece currently on the board
        for (Piece piece : simPieces)
        {
            // If any of them are in the square speicified in the function
            // Without being an ally piece
            if ((piece.column == column && piece.row == row) && piece!= activePiece && piece.color == currentColor)
            {
                // Then the variable increases
                numberOfPiecesOnSquare++;
            }

            else
            {}

        }

        if (numberOfPiecesOnSquare>0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }


    public boolean kingIsCheckmated(){
        ArrayList<Piece> snapshot = new ArrayList<>(simPieces);
        for (Piece piece : snapshot)
        {
            if (piece instanceof King && ((King) piece).isCheckMated())
            {
                return true;
            }
        }

        return false;
    }

    public boolean kingIsStalemated(){
        ArrayList<Piece> snapshot = new ArrayList<>(simPieces);
        for (Piece piece : snapshot)
        {
            if (piece instanceof King && ((King) piece).isStaleMated())
            {
                return true;
            }
        }

        return false;
    }


    public boolean moveablePieceExists()
    {
        ArrayList<Piece> snapshot = new ArrayList<>(simPieces);

        for (Piece piece : snapshot){
            if (piece.color == currentColor ){

                for (int row = 0; row <= 7 ; row++) {
                    for (int col = 0; col <= 7; col++) {

                        if (piece.canBlockCheckingPiece(col, row) || piece.isCapturingCheckingPiece(col, row)) {
                                moveablePiece = piece;
                                moveableCol = col;
                                moveableRow = row;
                                return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean moveablePieceExists_STALEMATE()
    {
        ArrayList<Piece> snapshot = new ArrayList<>(simPieces);

        for (Piece piece : snapshot){
            if (piece.color == currentColor ){

                for (int row = 0; row <= 7 ; row++) {
                    for (int col = 0; col <= 7; col++) {

                        if (piece instanceof Pawn) {
                            if ((((Pawn) piece).canMoveForwards(col, row) || ((Pawn) piece).canMoveDiagonallyForwards(col, row) || ((Pawn) piece).canEnPassant(col, row))
                                    && piece.isValidSquare(col, row) ){
                                moveablePiece = piece;
                                moveableCol = col;
                                moveableRow = row;
                                return true;
                            }

                        }
                        if (piece instanceof Bishop){
                            if (((Bishop) piece).isMovingDiagonally(col, row) || piece.isValidSquare(col, row)){
                                moveablePiece = piece;
                                moveableCol = col;
                                moveableRow = row;
                                return true;
                            }
                        }
                        if (piece instanceof Knight){
                            if (((Knight) piece).isMovingInLShape(col, row) && piece.isValidSquare(col, row)){
                                moveablePiece = piece;
                                moveableCol = col;
                                moveableRow = row;
                                return true;
                            }
                        }
                        if (piece instanceof Rook){
                            if (((Rook) piece).isMovingCardinally(col, row) && piece.isValidSquare(col, row)){
                                moveablePiece = piece;
                                moveableCol = col;
                                moveableRow = row;
                                return true;
                            }
                        }
                        if (piece instanceof Queen){
                            if (piece.isValidSquare(col, row) && ((Queen) piece).isMovingLikeAQueen(col, row)){
                                moveablePiece = piece;
                                moveableCol = col;
                                moveableRow = row;
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

   // MISC
   public int size(ArrayList<Piece> list){
        if (list == null){
            return 0;
        }
        else if (!list.isEmpty()){
            return list.size();
        }
        else {
            return 0;
        }



   }



















}
