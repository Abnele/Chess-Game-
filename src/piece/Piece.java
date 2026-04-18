package piece;

import Main.Board;
import Main.GamePanel;
import Main.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;


public class Piece {
    // Variables
    public BufferedImage image;
    public int x, y;
    public int column, row, preColumn, preRow, startColumn, startRow;
    public int color;
    public Piece hoveredPiece;

    // Lists
    ArrayList<? extends Piece> promotedPieces = new ArrayList<>(Arrays.asList());





    // Constructor
    public Piece(int color, int column, int row) {
        this.x = x;
        this.y = y;
        this.row = row;
        this.column = column;
        this.color = color;
        x = getX(column);
        y = getY(row);
        preRow = row;
        preColumn = column;
        startRow = row;
        startColumn = column;
    }

    // Image Getter
    public BufferedImage getImage(String imagePath){
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    // Piece Drawing method
    public void draw(Graphics2D g2){
        g2.drawImage(image, x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
    }

    // Position Updater
    public void updatePosition()
    {
        Piece p = this;
        x = getX(column);
        y = getY(row);
        preColumn = getColumn(x);
        preRow = getRow(y);

        /// Special Case for king piece
        // If the updated piece is a king
        if (p instanceof King)
        {
            // And it is white's turn
            if (GamePanel.currentColor == GamePanel.WHITE)
            {
                // And there is a rook to be castled
                if (((King) p).castledRook != null)
                {
                    // For every piece on the board
                    for (Piece piece : GamePanel.simPieces)
                    {
                        // If the piece is a rook in its starting position
                        if (piece instanceof Rook && (piece.preColumn == piece.column && piece.preRow == piece.row ))
                        {
                            // And it is a white kingside rook
                            // ...that the king is trying to castle with
                            if (piece.row == 7 && piece.column == 7 && piece == ((King) p).castledRook)

                            {
                                // Move the rook to the left two squares
                                // And update its position variables accordingly
                                piece.column -= 2;
                                piece.preColumn = piece.column;
                                piece.x = getX(piece.column);
                                piece.y = getY(piece.row);
                            }

                            // Or if it is a white queenside rook
                            // ...that the king is trying to castle with
                            else if (piece.row == 7 && piece.column == 0 && piece == ((King) p).castledRook)
                            {
                                // Move the rook to the right three squares
                                // And update its position variables accordingly
                                piece.column += 3;
                                piece.preColumn = piece.column;
                                piece.x = getX(piece.column);
                                piece.y = getY(piece.row);
                            }

                        }
                    }
                    x = getX(column);
                    y = getY(row);
                    preColumn = getColumn(x);
                    preRow = getRow(y);
                    ((King) p).castledRook = null;

                }
            }

            // Or it is black's turn
            else
            {
                if (((King) p).castledRook != null)
                {
                    for (Piece piece : GamePanel.simPieces)
                    {
                        if (piece instanceof Rook && (piece.preColumn == piece.column && piece.preRow == piece.row ))
                        {
                            if (piece.row == 0 && piece.column == 7 && piece == ((King) p).castledRook)

                            {
                                piece.column -= 2;
                                piece.preColumn = piece.column;
                                piece.x = getX(piece.column);
                                piece.y = getY(piece.row);
                            }

                            else if (piece.row == 0 && piece.column == 0 && piece == ((King) p).castledRook)
                            {
                                piece.column += 3;
                                piece.preColumn = piece.column;
                                piece.x = getX(piece.column);
                                piece.y = getY(piece.row);
                            }
                        }
                    }
                    x = getX(column);
                    y = getY(row);
                    preColumn = getColumn(x);
                    preRow = getRow(y);

                }
            }

            /// Update the king's hasMoved variable
            ((King) p).hasMoved = true;


        }

        /// Special case for en passant pawns
        // If the updated piece is a pawn
        if (p instanceof Pawn)
        {
            // And its en passant victim variable exists
            if (((Pawn) p).enPassantVictim != null)
            {
                // Then set it back to null
                ((Pawn) p).enPassantVictim = null;
            }
        }


    }


    // GETTER METHODS

    public int getX(int column)
    {
        x = column * Board.SQUARE_SIZE;
        return x;
    }

    public int getY(int row)
    {
        y = row * Board.SQUARE_SIZE;
        return y;
    }

    public int getRow(int y)
    {

        return (y + Board.HALF_SQUARE_SIZE)/Board.SQUARE_SIZE;
    }

    public int getColumn(int x)
    {
        return (x + Board.HALF_SQUARE_SIZE)/Board.SQUARE_SIZE;

    }



    public Piece getHoveredPiece(int targetColumn, int targetRow)
    {
        for (Piece piece : GamePanel.simPieces)
        {
            if (piece.preColumn == targetColumn && piece.preRow == targetRow && piece != this)
            {
                return piece;
            }

            /// For pawns doing en passant
            // If both the active and hovered piece are pawns of opposite color
            else if (piece instanceof Pawn && this instanceof Pawn
                    && piece.color != this.color)
            {
                // And the active piece is hovering an en passant square
                if (((Pawn) this).canEnPassant(targetColumn, targetRow))
                {
                    // And it is going to take the currently scanned pawn
                    if (((Pawn) this).enPassantVictim != null && piece == ((Pawn) this).enPassantVictim)
                    {
                        return piece;
                    }
                }
            }
        }

        return null;
    }

    public int getIndex()
    {
        for (int index = 0; index <GamePanel.simPieces.size(); index++)
        {
            if (GamePanel.simPieces.get(index) == this)
            {
                return index;
            }
        }
        return 0;
    }

    // BOOLEAN METHODS
    public boolean canMove(int targetColumn, int targetRow)
    {

        return true;
    }

    public boolean isWithinBoard(int targetColumn, int targetRow)
    {
        if (targetColumn >=0 && targetColumn <=7
                && targetRow >=0 && targetRow<=7)
        {
            return true;
        }

        else
        {
            return false;
        }
    }

    public boolean isNotInPreviousSquare(int targetColumn, int targetRow)
    {
        if (!(targetColumn == this.preColumn && targetRow == this.preRow))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isValidSquare(int targetColumn, int targetRow)
    {
        Piece hoveredPiece = getHoveredPiece(targetColumn,targetRow);
        // If the square is not occupied
        if (hoveredPiece == null)
        {
            return true;

        }



        // If the square is occupied
        else
        {
            if (hoveredPiece.color != this.color)
            {
                return true;
            }

            else
            {
                hoveredPiece = null;
            }
        }



        return false;
    }

    public boolean hasPieceBlockingIt(int targetColumn, int targetRow)
    {

        return false;
    }

    /// Checks if the allied king is in check
    public boolean kingIsInCheck()
    {

        // If the piece executing this is an ally piece
        if (color == GamePanel.currentColor)
        {
            // For all pieces currently on the board
            for (Piece piece : GamePanel.simPieces)
            {
                // If any of those pieces are a king of the same color
                if (piece instanceof King && piece.color == color)
                {
                    // And that king is in check
                    if (((King) piece).isInCheck())
                    {
                        // Then the king is in check (no shit)
                        return true;
                    }
                }
            }
        }




        return false;
    }

    public boolean isCapturingCheckingPiece(int targetColumn, int targetRow)
    {

        // If the allied king is in check
        if (kingIsInCheck())
        {
            // Scan all piece in the board to find the king
            for (Piece piece : GamePanel.simPieces)
            {
                if (piece instanceof King && piece.color == color)
                {
                    // If the piece is hovering over the king's checking piece AND there is only one checking piece
                    if (getHoveredPiece(targetColumn, targetRow) == ((King) piece).checkingPiece && ((King) piece).checkingPieces.size() == 1)
                    {
                        // If the piece is a pawn
                        if (this instanceof Pawn)
                        {
                            // And the pawn can move there
                            if (((Pawn) this).canMoveDiagonallyForwards(targetColumn, targetRow))
                            {
                                return true;
                            }
                        }
                        // If the piece is a bishop
                        if (this instanceof Bishop)
                        {
                            // And the bishop can move there
                            if (
                                    ((Math.abs(targetColumn - preColumn) == (Math.abs(targetRow - preRow)))
                                && (Math.abs(targetColumn - preColumn) > 0 && Math.abs(targetRow - preRow) > 0))
                                && !this.hasPieceBlockingIt(targetColumn, targetRow))
                            {
                                return true;
                            }
                        }
                        // If the piece is a rook
                        if (this instanceof Rook)
                        {
                            // And the rook can move there
                            if (((targetColumn != preColumn && targetRow == preRow)
                                || (targetRow != preRow && targetColumn == preColumn))
                                && !this.hasPieceBlockingIt(targetColumn, targetRow))
                            {
                                return true;
                            }
                        }
                        // If the piece is a queen
                        if (this instanceof Queen)
                        {
                            // And the queen can move there
                            if ((((targetColumn != preColumn && targetRow == preRow)
                                    || (targetRow != preRow && targetColumn == preColumn))
                                    || ((Math.abs(targetColumn - preColumn) == (Math.abs(targetRow - preRow)))
                                    && (Math.abs(targetColumn - preColumn) > 0 && Math.abs(targetRow - preRow) > 0)))
                                    && !hasPieceBlockingIt(targetColumn, targetRow))
                            {
                                return true;
                            }
                        }
                        // If the piece is a knight
                        if (this instanceof Knight)
                        {
                            // And the knight can move there
                            if (((Knight) this).isMovingInLShape(targetColumn, targetRow))
                            {
                                return true;
                            }
                        }

                    }
                }
            }
        }


        return false;
    }

    public boolean canBlockCheckingPiece(int targetColumn, int targetRow)
    {
        // For each piece, make a condition that lists all squares...
        // ...between the king and checking piece
        // If the given square is any of those squares, then check if the piece can move there
        // If it can, return true

        ArrayList<Piece> snapshot = new ArrayList<>(GamePanel.simPieces);

        // Coordinates
        ArrayList<int[]> blockingCoordinates = new ArrayList<>();


        for (Piece king : snapshot){
            // Look for the checked king
            if (king instanceof King && ((King) king).isInCheck() && king.color == GamePanel.currentColor && ((King) king).checkingPieces.size() == 1){

                // Checking piece coordinates
                int checkerColumn = ((King) king).checkingPiece.preColumn;
                int checkerRow = ((King) king).checkingPiece.preRow;

                // If the checking piece is a bishop
                if (((King) king).checkingPiece instanceof Bishop){

                    // If the king and checking bishop...
                    // ...are diagonal to each other
                    if ((Math.abs(checkerColumn - king.preColumn) == Math.abs(checkerRow - king.preRow))
                         && (Math.abs(checkerColumn - king.preColumn) > 0 && Math.abs(checkerRow - king.preRow) > 0))
                    {


                        int colStep = Integer.compare(king.preColumn, checkerColumn);
                        int rowStep = Integer.compare(king.preRow, checkerRow);
                        int blockingCol = checkerColumn + colStep;
                        int blockingRow = checkerRow + rowStep;

                        while (blockingCol != king.preColumn || blockingRow != king.preRow){
                            blockingCoordinates.add(new int[]{blockingCol, blockingRow});

                            blockingCol += colStep;
                            blockingRow += rowStep;
                        }

                        /// Check if the target square matches any of the coordinates
                        for (int[] coordinate : blockingCoordinates){
                            if (targetColumn == coordinate[0] && targetRow == coordinate[1]){
                                if (this instanceof Bishop){
                                    if (((Bishop) this).isMovingDiagonally(targetColumn, targetRow)){
                                        return true;
                                    }

                                }
                                if (this instanceof Knight){
                                    if (((Knight) this).isMovingInLShape(targetColumn, targetRow)){
                                        return true;
                                    }
                                }
                                if (this instanceof Queen){
                                    if (((Queen) this).isOnCardinalOrDiagonalSquare(targetColumn, targetRow)){
                                        return true;
                                    }
                                }
                                if (this instanceof Rook){
                                    if (((Rook) this).isMovingCardinally(targetColumn, targetRow)){
                                        return true;
                                    }
                                }
                                if (this instanceof Pawn){
                                    if (((Pawn) this).canMoveDiagonallyForwards(targetColumn, targetRow)
                                    || ((Pawn) this).canEnPassant(targetColumn, targetRow)
                                    || ((Pawn) this).canMoveForwards(targetColumn, targetRow)){
                                        return true;
                                    }
                                }
                            }
                        }


                    }

                }
                // If the checking piece is a knight
                if (((King) king).checkingPiece instanceof Knight){
                    // Then you cannot block it
                    return false;
                }
                // If the checking piece is a rook
                if (((King) king).checkingPiece instanceof Rook){
                    // If the king and checking rook...
                    // ... are directly across from each other
                    if (((Rook) ((King) king).checkingPiece).isMovingCardinally(checkerColumn, checkerRow)){

                        int colStep = Integer.compare(king.preColumn, checkerColumn);
                        int rowStep = Integer.compare(king.preRow, checkerRow);
                        int blockingCol = checkerColumn + colStep;
                        int blockingRow = checkerRow + rowStep;

                        while (blockingCol != king.preColumn || blockingRow != king.preRow){
                            blockingCoordinates.add(new int[]{blockingCol, blockingRow});

                            blockingCol += colStep;
                            blockingRow += rowStep;
                        }

                        /// Check if the target square matches any of the coordinates
                        for (int[] coordinate : blockingCoordinates){
                            if (targetColumn == coordinate[0] && targetRow == coordinate[1]){
                                if (this instanceof Bishop){
                                    if (((Bishop) this).isMovingDiagonally(targetColumn, targetRow)){
                                        return true;
                                    }

                                }
                                if (this instanceof Knight){
                                    if (((Knight) this).isMovingInLShape(targetColumn, targetRow)){
                                        return true;
                                    }
                                }
                                if (this instanceof Queen){
                                    if (((Queen) this).isOnCardinalOrDiagonalSquare(targetColumn, targetRow)){
                                        return true;
                                    }
                                }
                                if (this instanceof Rook){
                                    if (((Rook) this).isMovingCardinally(targetColumn, targetRow)){
                                        return true;
                                    }
                                }
                                if (this instanceof Pawn){
                                    if (((Pawn) this).canMoveDiagonallyForwards(targetColumn, targetRow)
                                            || ((Pawn) this).canEnPassant(targetColumn, targetRow)
                                            || ((Pawn) this).canMoveForwards(targetColumn, targetRow)){
                                        return true;
                                    }
                                }
                            }
                        }
                    }


                }
                // If the checking piece is a queen
                if (((King) king).checkingPiece instanceof Queen){
                    if (((Queen) ((King) king).checkingPiece).isMovingLikeAQueen(king.preColumn, king.preRow)){

                        int colStep = Integer.compare(king.preColumn, checkerColumn);
                        int rowStep = Integer.compare(king.preRow, checkerRow);
                        int blockingCol = checkerColumn + colStep;
                        int blockingRow = checkerRow + rowStep;

                        while (blockingCol != king.preColumn || blockingRow != king.preRow){
                            blockingCoordinates.add(new int[]{blockingCol, blockingRow});

                            blockingCol += colStep;
                            blockingRow += rowStep;
                        }

                        /// Check if the target square matches any of the coordinates
                        for (int[] coordinate : blockingCoordinates){
                            if (targetColumn == coordinate[0] && targetRow == coordinate[1]){
                                if (this instanceof Bishop){
                                    if (((Bishop) this).isMovingDiagonally(targetColumn, targetRow)){
                                        return true;
                                    }

                                }
                                if (this instanceof Knight){
                                    if (((Knight) this).isMovingInLShape(targetColumn, targetRow)){
                                        return true;
                                    }
                                }
                                if (this instanceof Queen){
                                    if (((Queen) this).isOnCardinalOrDiagonalSquare(targetColumn, targetRow)){
                                        return true;
                                    }
                                }
                                if (this instanceof Rook){
                                    if (((Rook) this).isMovingCardinally(targetColumn, targetRow)){
                                        return true;
                                    }
                                }
                                if (this instanceof Pawn){
                                    if (
                                            (((Pawn) this).canMoveDiagonallyForwards(targetColumn, targetRow))
                                            || (((Pawn) this).canEnPassant(targetColumn, targetRow))
                                            || (((Pawn) this).canMoveForwards(targetColumn, targetRow))
                                    ){
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
                // If the checking piece is a pawn
                if (((King) king).checkingPiece instanceof Pawn){
                    // Return false since you cannot block a pawn's check
                    return false;
                }
            }
        }




        return false;
    }



    // VOID METHODS

    public void resetPosition()
    {
        column = preColumn;
        row = preRow;
        x = getX(preColumn);
        y = getY(preRow);

    }

    public void checkMovedTwoSquares()
    {

            /// This code resets the movedTwoSquares variable after each turn
            for (Piece piece : GamePanel.simPieces)
            {
                if (piece instanceof Pawn && piece.color == this.color)
                {
                    if (((Pawn) piece).movedTwoSquares)
                    {
                        ((Pawn) piece).movedTwoSquares = false;
                    }
                }

            }

    }


    // Misc
    /// Edits the object tags given to the piece; used for debugging
    @Override
    public String toString()
    {
        String name = this.getClass().getName();

        return (name + "(Color: " + color + ", preColumn: " + preColumn + ", preRow: " + preRow + ")");

    }

    // Not Used WIP
    public boolean canBlockCheckingPiece_OLD(int targetColumn, int targetRow)
    {
        // For each piece, make a condition that lists all squares...
        // ...between the king and checking piece
        // If the given square is any of those squares, then check if the piece can move there
        // If it can, return true

        ArrayList<Piece> snapshot = new ArrayList<>(GamePanel.simPieces);

        // Coordinates
        ArrayList<int[]> blockingCoordinates = new ArrayList<>();


        for (Piece king : snapshot){
            // Look for the checked king
            if (king instanceof King && ((King) king).isInCheck() && king.color == GamePanel.currentColor){

                // Checking piece coordinates
                int checkerColumn = ((King) king).checkingPiece.preColumn;
                int checkerRow = ((King) king).checkingPiece.preRow;

                // If the checking piece is a bishop
                if (((King) king).checkingPiece instanceof Bishop){

                    // If the king and checking bishop...
                    // ...are diagonal to each other
                    if ((Math.abs(checkerColumn - king.preColumn) == Math.abs(checkerRow - king.preRow))
                            && (Math.abs(checkerColumn - king.preColumn) > 0 && Math.abs(checkerRow - king.preRow) > 0)){

                        // If the king is down and to the right from the checking bishop
                        if (king.preColumn > checkerColumn && king.preRow > checkerRow){

                            // For all squares
                            /// Find all squares between the king and bishop
                            for (int row = 0; row <= 7 ; row++) {
                                for (int col = 0; col <= 7; col++) {
                                    // If any square is between the king and bishop
                                    if ((checkerColumn < col && col < king.preColumn)
                                            && (checkerRow < row && row < king.preRow)){

                                        // Then add it to the list
                                        blockingCoordinates.add(new int[]{col, row});



                                    }
                                }
                            }

                            /// Check if the piece executing this can...
                            /// ...move to any of those squares
                            for (int[] coordinate : blockingCoordinates){
                                if (this instanceof Bishop && ((Bishop) this).isMovingDiagonally(coordinate[0], coordinate[1])){
                                    // And if the given square is any of those squares
                                    if (targetColumn == coordinate[0] && targetRow == coordinate[1]){
                                        // If it can, return true
                                        return true;
                                    }

                                }
                            }


                        }


                        // If the king is down and to the left from the checking bishop
                        if (king.preColumn < checkerColumn && king.preRow > checkerRow){

                        }

                        // If the king is up and to the right from the checking bishop
                        if (king.preColumn > checkerColumn && king.preRow < checkerRow){

                        }

                        // If the king is up and to the left from the checking bishop
                        if (king.preColumn < checkerColumn && king.preRow < checkerRow){

                        }

                    }

                }
            }
        }












        return false;
    }



}
