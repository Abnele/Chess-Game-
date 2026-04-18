package piece;

import Main.*;

import java.util.ArrayList;
import java.util.Collections;


public class King extends Piece{
    // Pieces
    public Piece castledRook;
    public Piece checkingPiece;
    public ArrayList<Piece> checkingPieces;

    // Movement Variables
    public boolean hasMoved;
    public boolean isOnSquaresAroundIt;







    // Contructor
    public King(int color, int column, int row){
        super(color, column, row);

        // Movement variables
        hasMoved = false; /// Starting position king has not moved

        // Color
        if (color == GamePanel.WHITE){
            image = getImage("/piece/WhiteKing");
        } /// Makes the king white
        else if (color == GamePanel.BLACK) {
            image = getImage("/piece/BlackKing");
        } /// Makes the king black




    }

    // Piece Movement Boolean Functions
        @Override
        public boolean canMove(int targetColumn, int targetRow)
        {
            // If the king is within the board
            // And is moving one square in any direction
            // Or is castling
            if (isWithinBoard(targetColumn,targetRow)
                    && !isInCheckSquare()
                    && !isInCheckSquare_THEORETICAL(targetColumn, targetRow)
                    && (isOnSquaresAroundIt(targetColumn,targetRow)
                    || canCastle(targetColumn, targetRow))
            )
            {
                // If the square is a valid square and is not its previous square
                if (isValidSquare(targetColumn, targetRow) && isNotInPreviousSquare(targetColumn, targetRow))
                {
                    // Then the king can move there
                    return true;
                }

                else
                {
                    // Otherwise, it can't
                    return false;
                }

            }


            else
            {
                return false;
            }

        }



        public boolean isOnSquaresAroundIt(int targetColumn, int targetRow)
        {
            if (Math.abs(targetColumn - this.preColumn) + Math.abs(targetRow - this.preRow) == 1
                    || Math.abs(targetColumn - this.preColumn) * Math.abs(targetRow - this.preRow) == 1)
            {
                isOnSquaresAroundIt = true;
                return true;
            }

            else
            {
                isOnSquaresAroundIt = false;
                return false;
            }
        }




    // Piece States
        public boolean isInCheckSquare()
        {
            /// This function makes it so the king cannot move...
            /// ...to a square that would put it in check

            // For all pieces on the board
            for (Piece piece : GamePanel.simPieces)
            {
                // If that piece is of the opposite color
                if (piece.color != GamePanel.currentColor && color == GamePanel.currentColor)
                {
                    // If the piece is a pawn
                    if (piece instanceof Pawn)
                    {
                        // If the piece is a white pawn
                        if (piece.color == GamePanel.WHITE)
                        {
                            // If the king of opposite color
                            // ...is on any squares forwards and diagonal from it
                            if ((column == piece.column + 1 || column == piece.column - 1)
                                && (row == piece.row - 1))
                            {
                                return true;
                            }
                        }

                        // If the piece is a black pawn
                        else
                        {
                            // If the king of opposite color
                            // ...is on any squares forwards and diagonal from it
                            if ((column == piece.column + 1 || column == piece.column - 1)
                               && (row == piece.row + 1))
                            {
                                return true;
                            }
                        }



                    }

                    // If the piece is a king
                    else if (piece instanceof King)
                    {
                        // If it is the opposite color king
                        if (piece.color != color)
                        {
                            // If this instance of the king
                            // Is hovering a square around the other king
                            /// We pass in this king's square into the opposite king's
                            /// Movememnt function to determine if
                            /// It is in its available squares
                            if (((King) piece).isOnSquaresAroundIt(column, row))
                            {
                                return true;
                            }


                        }
                    }

                    // If the piece is a rook
                    else if (piece instanceof Rook)
                    {
                        // If it is the opposite color rook
                        if (piece.color != GamePanel.currentColor && color == GamePanel.currentColor)
                        {
                            // And the king is in that rook's line of sight
                            if (piece.canMove(column, row))
                            {
                                return true;
                            }
                        }

                    }

                    // If the piece is a bishop
                    else if (piece instanceof Bishop)
                    {
                        // If it is the opposite color bishop
                        if (piece.color != GamePanel.currentColor && color == GamePanel.currentColor)
                        {
                            // And the king is in that bishop's line of sight
                            if (piece.canMove(column, row))
                            {
                                return true;
                            }
                        }

                    }

                    // If the piece is a queen
                    else if (piece instanceof Queen)
                    {
                        // If it is the opposite color queen
                        if (piece.color != GamePanel.currentColor && color == GamePanel.currentColor)
                        {
                            // And the king is in that queen's line of sight
                            if (piece.canMove(column, row))
                            {
                                return true;
                            }
                        }


                    }

                    // If the piece is a knight
                    else if (piece instanceof Knight)
                    {
                        // If it is the opposite color knight
                        if (piece.color != GamePanel.currentColor && color == GamePanel.currentColor)
                        {
                            // And the king is in that knight's line of sight
                            if (piece.canMove(column,row))
                            {
                                return true;
                            }
                        }



                    }







                }

            }

            return  false;
        }

        public boolean isCheckSquare(int column, int row)
        {
            /// This function determines if a square...
            /// ...is attacked by an enemy piece

            // For all pieces on the board
            for (Piece piece : GamePanel.simPieces)
            {
                // If that piece is of the opposite color
                if (piece.color != GamePanel.currentColor && color == GamePanel.currentColor)
                {
                    // If the piece is a pawn
                    if (piece instanceof Pawn)
                    {
                        // If the piece is a white pawn
                        if (piece.color == GamePanel.WHITE)
                        {
                            // If the given square
                            // ...is on any squares forwards and diagonal from it
                            if ((column == piece.column + 1 || column == piece.column - 1)
                                    && (row == piece.row - 1))
                            {
                                return true;
                            }
                        }

                        // If the piece is a black pawn
                        else
                        {
                            // If the king of opposite color
                            // ...is on any squares forwards and diagonal from it
                            if ((column == piece.column + 1 || column == piece.column - 1)
                                    && (row == piece.row + 1))
                            {
                                return true;
                            }
                        }



                    }

                    // If the piece is a king
                    else if (piece instanceof King)
                    {
                        // If it is the opposite color king
                        if (piece.color != color)
                        {
                            // If this instance of the king
                            // Is hovering a square around the other king
                            /// We pass in this king's square into the opposite king's
                            /// Movememnt function to determine if
                            /// It is in its available squares
                            if (((King) piece).isOnSquaresAroundIt(column, row))
                            {
                                return true;
                            }


                        }
                    }

                    // If the piece is a rook
                    else if (piece instanceof Rook)
                    {
                        // If it is the opposite color rook
                        if (piece.color != GamePanel.currentColor && color == GamePanel.currentColor)
                        {
                            // And the king is in that rook's line of sight
                            if (piece.canMove(column, row))
                            {
                                return true;
                            }
                        }

                    }

                    // If the piece is a bishop
                    else if (piece instanceof Bishop)
                    {
                        // If it is the opposite color bishop
                        if (piece.color != GamePanel.currentColor && color == GamePanel.currentColor)
                        {
                            // And the king is in that bishop's line of sight
                            if (piece.canMove(column, row))
                            {
                                return true;
                            }
                        }

                    }

                    // If the piece is a queen
                    else if (piece instanceof Queen)
                    {
                        // If it is the opposite color queen
                        if (piece.color != GamePanel.currentColor && color == GamePanel.currentColor)
                        {
                            // And the king is in that queen's line of sight
                            if (piece.canMove(column, row))
                            {
                                return true;
                            }
                        }


                    }

                    // If the piece is a knight
                    else if (piece instanceof Knight)
                    {
                        // If it is the opposite color knight
                        if (piece.color != GamePanel.currentColor && color == GamePanel.currentColor)
                        {
                            // And the king is in that knight's line of sight
                            if (piece.canMove(column,row))
                            {
                                return true;
                            }
                        }



                    }







                }

            }

            return false;
        }

        public boolean isInCheck()
        {
            ArrayList<Piece> snapshot = new ArrayList<>(GamePanel.simPieces);
            if (checkingPieces != null){
                checkingPieces.clear();
            }

            else {
                checkingPieces = new ArrayList<>();
            }



            // If it is the king's turn
            if (color == GamePanel.currentColor)
            {
                // For all pieces on the board
                for (Piece piece : snapshot)
                {
                    // If the piece is of the opposite color
                    if (piece.color != color)
                    {
                        // If the piece is a pawn
                        if (piece instanceof Pawn)
                        {
                            // And it is a white pawn
                            if (piece.color == GamePanel.WHITE)
                            {
                                // And it is attacking the king
                                if ((piece.preRow == preRow + 1)
                                    && (preColumn == piece.preColumn + 1 || preColumn == piece.preColumn - 1))
                                {
                                    checkingPiece = piece;
                                    checkingPieces.add(piece);
                                    return true;
                                }

                            }

                            // Or a black pawn
                            if (piece.color == GamePanel.BLACK)
                            {
                                // And it is attacking the king
                                if ((piece.preRow == preRow - 1)
                                    && (preColumn == piece.preColumn + 1 || preColumn == piece.preColumn - 1))
                                {
                                    checkingPiece = piece;
                                    checkingPieces.add(piece);
                                    return true;
                                }
                            }
                        }
                        // If the piece is a rook
                        if (piece instanceof Rook)
                        {
                            // And the rook is attacking the king
                            if (((preColumn != piece.preColumn && preRow == piece.preRow)
                                || (preRow != piece.preRow && preColumn == piece.preColumn)))
                            {
                                // And is not blocked by any pieces
                                if (!piece.hasPieceBlockingIt(preColumn, preRow))
                                {
                                    checkingPiece = piece;
                                    checkingPieces.add(piece);
                                    return true;
                                }
                            }
                        }
                        // If the piece is a bishop
                        if (piece instanceof Bishop)
                        {
                            // And the bishop is attacking the king
                            if ((Math.abs(preColumn - piece.preColumn) == (Math.abs(preRow - piece.preRow)))
                                && (Math.abs(preColumn - piece.preColumn) > 0 && Math.abs(preRow - piece.preRow) > 0))
                            {
                                // And is not blocked by any pieces
                                if (!piece.hasPieceBlockingIt(preColumn, preRow))
                                {
                                    checkingPiece = piece;
                                    checkingPieces.add(piece);

                                    return true;
                                }
                            }
                        }
                        // If the piece is a queen
                        if (piece instanceof Queen)
                        {
                            // And is attacking the king
                            if (
                                    ((preColumn != piece.preColumn && preRow == piece.preRow)
                                    || (preRow != piece.preRow && preColumn == piece.preColumn))

                                    || ((Math.abs(preColumn - piece.preColumn) == (Math.abs(preRow - piece.preRow)))
                                    && (Math.abs(preColumn - piece.preColumn) > 0 && Math.abs(preRow - piece.preRow) > 0))
                            )
                            {
                                // And is not blocked by any pieces
                                if (!piece.hasPieceBlockingIt(preColumn, preRow))
                                {
                                    checkingPiece = piece;
                                    checkingPieces.add(piece);
                                    return true;
                                }
                            }
                        }
                        // If the piece is a knight
                        if (piece instanceof Knight)
                        {
                            // And it is attacking the king
                            if (((Knight)piece).isMovingInLShape(preColumn, preRow))
                            {
                                checkingPiece = piece;
                                checkingPieces.add(piece);
                                return true;
                            }
                        }

                    }

                }
            }



            checkingPiece = null;
            checkingPieces = new ArrayList<Piece>();
            return  false;

        }

        /** If the king is in check
         * @And it cannot move in the squares around it
         * @Then the king is in checkmate (it's really not, but for now it is)
         *



         */
        public boolean isCheckMated()
        {
            if (isInCheck())
            {
                for (int row = 0; row <= 7 ; row++)
                {
                    for (int col = 0; col <=7 ; col++)
                    {
                        if (isOnSquaresAroundIt(col, row) )
                        {
                            if (!isInCheckSquare_THEORETICAL(col, row) && isValidSquare(col, row))
                            {
                                return false;
                            }

                            else if(Main.gamePanel.moveablePieceExists())
                            {
                                return false;
                            }
                        }
                    }
                }

            }
            else {
                return false;
            }
            GamePanel.losingColor = color;
            return true;
        }

        public boolean canCastle(int targetColumn, int targetRow)
        {



            // If the king has moved already
            if (hasMoved)
            {
                // Then he cannot castle
                castledRook = null;
                return false;
            }

            // If it's white's turn
            else if (GamePanel.currentColor == GamePanel.WHITE)
            {
                // For every piece on the board
                for (Piece piece : GamePanel.simPieces)
                {
                    // If a piece is a white rook in its starting position
                    if (piece instanceof Rook && (piece.preColumn == piece.column && piece.preRow == piece.row)
                            && piece.color == GamePanel.WHITE)
                    {
                        // If the king is trying to castle kingside
                        // ... and there are no pieces blocking the king
                        // ... and it is not going through a square that would put it in check
                        if ((targetColumn == 6 && piece.column == 7 && piece.row == 7)
                                && (!Main.gamePanel.squareIsOccupied(targetColumn - 1, 7)
                                && !Main.gamePanel.squareIsOccupied(targetColumn - 2, 7))
                                && (!isCheckSquare(targetColumn - 1, 7) && !isCheckSquare(targetColumn - 2, 7))
                                && !(isOnSquaresAroundIt(targetColumn, targetRow)
                                && !(Main.gamePanel.isOnSquaresAroundIt)))
                        {
                            // Then the kingside rook is to be castled with
                            castledRook = piece;
                        }

                        // If the king is trying to castle queenside
                        // ... and there are no pieces blocking the king
                        // ... and the king is not passing a square that would put it in check
                        else if (targetColumn == 2 && piece.column == 0 && piece.row == 7
                                && !Main.gamePanel.squareIsOccupied(targetColumn + 1, 7)
                                && !Main.gamePanel.squareIsOccupied(targetColumn - 1, 7)
                                && (!isCheckSquare(targetColumn + 1, 7) && !isCheckSquare(targetColumn - 1, 7)))
                        {
                            // Then the queenside rook is to be castled with
                            castledRook = piece;
                        }





                    }




                }

            }

            // If it's black's turn
            else if (GamePanel.currentColor == GamePanel.BLACK)
            {
                // For every piece on the board
                for (Piece piece : GamePanel.simPieces)
                {
                    // If the piece is a black rook in its starting position
                    if (piece instanceof Rook && (piece.preColumn == piece.column && piece.preRow == piece.row )
                            && piece.color == GamePanel.BLACK)
                    {


                        // If the king tries to castle kingside
                        if (targetColumn == 6 && targetRow == 0 && piece.column == 7 && piece.row == 0)
                        {
                            // And there are no pieces blocking the king
                            // ... and the king is not passing a square that would put it in check
                            if (!Main.gamePanel.squareIsOccupied(targetColumn-1, 0)
                               &&  (!Main.gamePanel.squareIsOccupied(targetColumn-2, 0))
                                && (!isCheckSquare(targetColumn-1, 0) && ! isCheckSquare(targetColumn-2, 0)))
                            {
                                // Then the kingisde rook will be castled
                                castledRook = piece;
                            }


                        }

                        // If the king tries to castle queenside
                        // And there are no pieces blocking the king
                        // And the king is not passing a square that would put it in check
                        else if ((targetColumn == 2 && targetRow == 0 && piece.column == 0 && piece.row == 0)
                                && (!Main.gamePanel.squareIsOccupied(targetColumn + 1, 0)
                                && !Main.gamePanel.squareIsOccupied(targetColumn + 2, 0))
                                && (!isCheckSquare(targetColumn + 1, 0) && !isCheckSquare(targetColumn + 2, 0)))
                        {
                            // The queenside rook will be castled
                            castledRook = piece;
                        }




                    }
                }
            }

            else
            {
                castledRook = null;
                return false;
            }

            for (Piece piece : GamePanel.simPieces)
            {
                // If there is a rook to be castled with on either back rank
                if ((castledRook != null ))
                {
                    if (Main.gamePanel.isOnSquaresAroundIt)
                    {
                        castledRook = null;
                        return false;
                    }
                    else
                    {
                        // Given that the king is hovering one of the squares where you can castle
                        if ((targetColumn == 2 || targetColumn == 6) && (targetRow == 0 || targetRow == 7))
                        {


                            // Then the king can castle
                            return true;



                        }

                        else
                        {
                            castledRook = null;
                        }
                    }






                }


            }

            castledRook = null;
            return false;

        }

   // Duplicates slightly tweaked
       public boolean canMove_THEORETICAL(int targetColumn, int targetRow)
       {
           // If the king is within the board
           // And is moving one square in any direction
           // Or is castling
           if (isWithinBoard(targetColumn,targetRow)
                   && (isOnSquaresAroundIt(targetColumn,targetRow)
                   || canCastle(targetColumn, targetRow))
                   && (!isInCheckSquare_THEORETICAL(targetColumn, targetRow))
           )
           {
               // If the square is a valid square and is not its previous square
               if (isValidSquare(targetColumn, targetRow) && isNotInPreviousSquare(targetColumn, targetRow))
               {
                   // Then the king can move there
                   return true;
               }

               else
               {
                   // Otherwise, it can't
                   return false;
               }

           }


           else
           {
               return false;
           }

       }

       /// Tells you if the king would be in check were he to be in the square specified
       public boolean isInCheck_THEORETICAL(int preColumn, int preRow)
       {
            // For all pieces on the board
            for (Piece piece : GamePanel.simPieces)
            {
                // If the piece is of the opposite color
                if (piece.color != color)
                {
                    // If the piece is a pawn
                    if (piece instanceof Pawn)
                    {
                        // And it is a white pawn
                        if (piece.color == GamePanel.WHITE)
                        {
                            // And it is attacking the king
                            if ((piece.preRow == preRow + 1)
                                    && (preColumn == piece.preColumn + 1 || preColumn == piece.preColumn - 1))
                            {

                                return true;
                            }

                        }

                        // Or a black pawn
                        if (piece.color == GamePanel.BLACK)
                        {
                            // And it is attacking the king
                            if ((piece.preRow == preRow - 1)
                                    && (preColumn == piece.preColumn + 1 || preColumn == piece.preColumn - 1))
                            {

                                return true;
                            }
                        }
                    }
                    // If the piece is a rook
                    if (piece instanceof Rook)
                    {
                        // And the rook is attacking the king
                        if (((preColumn != piece.preColumn && preRow == piece.preRow)
                                || (preRow != piece.preRow && preColumn == piece.preColumn)))
                        {
                            // And is not blocked by any pieces
                            if (!piece.hasPieceBlockingIt(preColumn, preRow))
                            {
                                return true;
                            }
                        }
                    }
                    // If the piece is a bishop
                    if (piece instanceof Bishop)
                    {
                        // And the bishop is attacking the king
                        if ((Math.abs(preColumn - piece.preColumn) == (Math.abs(preRow - piece.preRow)))
                                && (Math.abs(preColumn - piece.preColumn) > 0 && Math.abs(preRow - piece.preRow) > 0))
                        {
                            // And is not blocked by any pieces
                            if (!piece.hasPieceBlockingIt(preColumn, preRow))
                            {
                                return true;
                            }
                        }
                    }
                    // If the piece is a queen
                    if (piece instanceof Queen)
                    {
                        // And is attacking the king
                        if (
                                ((((preColumn != piece.preColumn && preRow == piece.preRow)
                                        || (preRow != piece.preRow && preColumn == piece.preColumn))))
                                        || ((Math.abs(preColumn - piece.preColumn) == (Math.abs(preRow - piece.preRow)))
                                        && (Math.abs(preColumn - piece.preColumn) > 0 && Math.abs(preRow - piece.preRow) > 0))
                        )
                        {
                            // And is not blocked by any pieces
                            if (!piece.hasPieceBlockingIt(preColumn, preRow))
                            {
                                return true;
                            }
                        }
                    }
                    // If the piece is a knight
                    if (piece instanceof Knight)
                    {
                        // And it is attacking the king
                        if (((Knight)piece).isMovingInLShape(preColumn, preRow))
                        {
                            return true;
                        }
                    }

                }

            }

        return false;

    }


       public boolean isInCheckSquare_THEORETICAL(int targetColumn, int targetRow)
       {
            /// This function makes it so the king cannot move...
            /// ...to a square that would put it in check

            // For all pieces on the board
            for (Piece piece : GamePanel.simPieces)
            {
                // If that piece is of the opposite color
                if (piece.color != GamePanel.currentColor && color == GamePanel.currentColor)
                {
                    // If the piece is a pawn
                    if (piece instanceof Pawn)
                    {
                        // If the piece is a white pawn
                        if (piece.color == GamePanel.WHITE)
                        {
                            // If the king of opposite color
                            // ...is on any squares forwards and diagonal from it
                            if ((targetColumn == piece.column - 1)
                                    && (targetRow == piece.row - 1))
                            {
                                return true;
                            }
                        }

                        // If the piece is a black pawn
                        else
                         {
                            // If the king of opposite color
                            // ...is on any squares forwards and diagonal from it
                            if ((targetColumn == piece.column + 1)
                                    && (targetRow == piece.row + 1))
                            {
                                return true;
                            }
                        }



                    }

                    // If the piece is a king
                    else if (piece instanceof King)
                    {
                        // If it is the opposite color king
                        if (piece.color != color)
                        {
                            // If this instance of the king
                            // Is hovering a square around the other king
                            /// We pass in this king's square into the opposite king's
                            /// Movememnt function to determine if
                            /// It is in its available squares
                            if (((King) piece).isOnSquaresAroundIt(targetColumn, targetRow))
                            {
                                return true;
                            }


                        }
                    }

                    // If the piece is a rook
                    else if (piece instanceof Rook)
                    {
                        // If it is the opposite color rook
                        if (piece.color != GamePanel.currentColor && color == GamePanel.currentColor)
                        {
                            // And the king is in that rook's line of sight
                            if (piece.canMove(targetColumn, targetRow))
                            {
                                return true;
                            }
                        }

                    }

                    // If the piece is a bishop
                    else if (piece instanceof Bishop)
                    {
                        // If it is the opposite color bishop
                        if (piece.color != GamePanel.currentColor && color == GamePanel.currentColor)
                        {
                            // And the king is in that bishop's line of sight
                            if (piece.canMove(targetColumn, targetRow))
                            {
                                return true;
                            }
                        }

                    }

                    // If the piece is a queen
                    else if (piece instanceof Queen)
                    {
                        // If it is the opposite color queen
                        if (piece.color != GamePanel.currentColor && color == GamePanel.currentColor)
                        {
                            // And the king is in that queen's line of sight
                            if (piece.canMove(targetColumn, targetRow))
                            {
                                return true;
                            }
                        }


                    }

                    // If the piece is a knight
                    else if (piece instanceof Knight)
                    {
                        // If it is the opposite color knight
                        if (piece.color != GamePanel.currentColor && color == GamePanel.currentColor)
                        {
                            // And the king is in that knight's line of sight
                            if (piece.canMove(targetColumn,targetRow))
                            {
                                return true;
                            }
                        }



                    }







                }

            }

            return  false;
        }














    // NOT USED IN THE KING CLASS
        @Override
        public boolean hasPieceBlockingIt(int targetColumn, int targetRow)
        {

            return false;
        }

     // Old/Deprecated Methods

        public boolean canCastle_DEPRECATED(int targetColumn, int targetRow)
        {
        // If the king has already moved
        if (hasMoved)
        {
            // Then it cannot castle
            return false;
        }

        // If it is a white king
        if (color == GamePanel.WHITE)
        {
            /// Kingside castling
            // For all pieces on the board
            for (Piece piece : GamePanel.simPieces)
            {
                // If there is a white rook on the white kingside
                if (piece instanceof Rook && piece.column == 7 && piece.row == 7)
                {
                    // And the king is hovering over the square needed to castle
                    if (targetColumn == 6 && targetRow == 7)
                    {
                        // Then the rook will be stored to castle
                        castledRook = piece;
                    }
                }
            }




        }

        // If there is a rook to be castled
        if (castledRook != null)
        {
            // And the king is hovering over the right square
            if (Main.gamePanel.activePiece.column == 6 && Main.gamePanel.activePiece.row == 7 && !Main.gamePanel.isOnSquaresAroundIt)
            {
                // Then the king can castle
                return true;
            }
            else
            {
                castledRook = null;
            }

        }

        return false;
    }



}