package piece;

import Main.GamePanel;
import Main.Main;

public class Pawn extends Piece {


    // Movement Variables
    public boolean movedTwoSquares = false;

    // Piece variables
    public Piece enPassantVictim;

    public Pawn(int color, int column, int row){
        super(color, column, row);

        if (color == GamePanel.WHITE){
            image = getImage("/piece/WhitePawn");
        }
        else {
            image = getImage("/piece/BlackPawn");
        }







    }

    @Override
    public boolean canMove(int targetColumn, int targetRow)
    {

        checkMovedTwoSquares();


        // If it is a white pawn in its starting position
        if (color == GamePanel.WHITE && this.preRow == 6)
        {

            // And it is within the board
            // And its target square is in its same column as before
            // And said target square is one OR two squares ahead of it
            // And has no pieces obstructing it
            // OR has an enemy piece diagonally ahead of it
            if (
                    isWithinBoard(targetColumn, targetRow)
                    && ((this.canMoveForwards(targetColumn ,targetRow) || this.canMoveDiagonallyForwards(targetColumn, targetRow))
                    || (canEnPassant(targetColumn, targetRow)))
                    && (!kingIsInCheck() || isCapturingCheckingPiece(targetColumn, targetRow))


            )

            {
                // Then the pawn can move to the target square
                return true;
            }
            else
            {
                // If not, then it cannot move to the target square
                return false;
            }
        }

        // If it is a white pawn NOT in its starting position
        else if (color == GamePanel.WHITE && this.preRow != 6)
        {
            // And it is within the board
            // And its target square is in its same column as before
            // And said target square is one square ahead of it

            if (
                    isWithinBoard(targetColumn, targetRow)
                    && ((targetColumn == this.preColumn)
                    && (this.canMoveForwards(targetColumn, targetRow))
                    || this.canMoveDiagonallyForwards(targetColumn, targetRow)
                    || (canEnPassant(targetColumn, targetRow)))
                    && (!kingIsInCheck() || isCapturingCheckingPiece(targetColumn, targetRow))

            )
            {
                // Then the pawn can move to the target square
                return true;
            }

            else
            {
                // If not, then it cannot move to the target square
                return  false;
            }
        }

        // If it is a black pawn in its starting position
        if (color == GamePanel.BLACK && this.preRow == 1)
        {
            // And it is within the board
            // And its target square is in its same column as before
            // And said target square is one OR two squares ahead of it
            if (isWithinBoard(targetColumn, targetRow)
                    && ((targetColumn == this.preColumn)
                    && (this.canMoveForwards(targetColumn, targetRow))
                    || this.canMoveDiagonallyForwards(targetColumn, targetRow)
                    || (canEnPassant(targetColumn, targetRow)))
                    && (!kingIsInCheck() || isCapturingCheckingPiece(targetColumn, targetRow))
            )
            {
                // Then the pawn can move to the target square
                return true;
            }

            else
            {
                // If not, then it cannot move to the target square
                return false;
            }
        }

        // If it is a black pawn NOT in its starting position
        else if (color == GamePanel.BLACK && this.preRow != 1)
        {
            // And it is within the board
            // And its target square is in its same column as before
            // And said target square is one square ahead of it
            if (isWithinBoard(targetColumn, targetRow)
                    && (((this.canMoveForwards(targetColumn, targetRow)) || this.canMoveDiagonallyForwards(targetColumn, targetRow))
                    || (canEnPassant(targetColumn, targetRow)))
                    && (!kingIsInCheck() || isCapturingCheckingPiece(targetColumn, targetRow))
            )
            {
                // Then the pawn can move to the target square
                return true;
            }

            else
            {
                // If not, then it cannot move to the target square
                return  false;
            }
        }

        else
        {
            // If none of the above apply, then you broke the game
            return false;
        }



    }


    // These methods help determine whether the pawn can move or not

    public boolean hasEnemyPieceDiagonallyForwards(int column, int row)
    {
            // If there is a piece is diagonally ahead of the pawn
            if(color == GamePanel.WHITE)
            {
                if ((Main.gamePanel.squareIsOccupiedByEnemyPiece(column-1,row-1))
                || (Main.gamePanel.squareIsOccupiedByEnemyPiece(column+1,row-1)))
                {
                    //Then the pawn has a piece diagonally ahead
                    return true;
                        }

                else
                {
                    // Otherwise, it does not
                    return false;
                }

            }

            else
            {
                if ((Main.gamePanel.squareIsOccupiedByEnemyPiece(column-1,row+1))
                        || (Main.gamePanel.squareIsOccupiedByEnemyPiece(column+1,row+1)))
                {
                    //Then the pawn has a piece diagonally ahead
                    return true;
                }

                else
                {
                    // Otherwise, it does not
                    return false;
                }

            }
    }

    public boolean canMoveForwards(int targetColumn, int targetRow)
    {
        // If it is a white pawn
        if (color == GamePanel.WHITE)
        {
            // If the pawn isn't in its starting position
            if (this.preRow != 6)
            {
                // If the pawn is advancing only one square
                if ((targetRow == this.preRow - 1)
                     && !Main.gamePanel.squareIsOccupied(this.preColumn, this.preRow - 1)
                     && targetColumn == this.preColumn)
                {
                    return true;
                }

                else
                {
                    return false;
                }
            }

            // If it is
            else
            {
                // And its trying to move one or two squares forward
                if (
                        targetColumn == this.preColumn
                                && ((targetRow == this.preRow - 1 && !Main.gamePanel.squareIsOccupied(this.preColumn, targetRow))
                                || (targetRow == this.preRow - 2 && !Main.gamePanel.squareIsOccupied(this.preColumn, targetRow)))
                )
                {
                    // If it moved one
                    if (targetRow == this.preRow - 1)
                    {
                        return true;
                    }
                    // If it moved two
                    else if (targetRow == this.preRow - 2)
                    {
                        // Update its movement variable
                        movedTwoSquares = true;
                        return true;
                    }
                }

                else
                {
                    return false;
                }
            }


        }

        // If it is a black pawn
        else if (color == GamePanel.BLACK)
        {
            // If it is not a pawn in its starting position
            if (this.preRow != 1)
            {
                if ((targetRow == this.preRow + 1)
                  && !Main.gamePanel.squareIsOccupied(this.preColumn, this.preRow + 1)
                  && targetColumn == this.preColumn)
                {
                    return true;
                }

                else
                {
                    return false;
                }
            }

            // If it is a pawn in its starting position
            else
            {
                // And it is trying to move one or two squares forward
                if (    (targetRow == this.preRow + 1 && !Main.gamePanel.squareIsOccupied(this.preColumn, targetRow))
                     || (targetRow == this.preRow + 2 && !Main.gamePanel.squareIsOccupied(this.preColumn, targetRow)))


                {
                    {
                        // If it moved one square up
                        if (targetRow == this.preRow + 1 && targetColumn == preColumn)
                        {
                            return true;
                        }
                        // If it moved two squares up
                        else if (targetRow == this.preRow + 2 && targetColumn == preColumn)
                        {
                            // Update its movement variable
                            movedTwoSquares = true;
                            return true;
                        }
                    }
                }

                else
                {
                    return false;
                }
            }

        }

        return false;

    }

    public boolean canMoveDiagonallyForwards(int targetColumn, int targetRow)
    {
        if (this.hasEnemyPieceDiagonallyForwards(this.preColumn,this.preRow))
        {

        if ((targetColumn == preColumn - 1 || targetColumn == preColumn + 1)
                /// For white Pawns
            && ((targetRow  == preRow - 1 && color == GamePanel.WHITE ) ||
                /// For black pawns
                (targetRow == preRow + 1 && color == GamePanel.BLACK))

            && (Main.gamePanel.squareIsOccupiedByEnemyPiece(targetColumn,targetRow) || canEnPassant(targetColumn, targetRow)))
        {
            return true;
        }

        else
        {
            return false;
        }

        }


        else
        {
            return false;
        }


    }

    public boolean canEnPassant(int targetColumn, int targetRow)
    {
        // Scan all pieces on the board
        for (Piece piece : GamePanel.simPieces)
        {
            // If the piece is an opposite color pawn
            if (piece instanceof Pawn && piece.color != color)
            {
                // And the piece has just moved 2 squares
                // ... past its starting square
                if (((Pawn) piece).movedTwoSquares)
                {
                    // And the pawn is directly next to the one calling the method
                    if (piece.row == preRow
                    && (preColumn == piece.column - 1 || preColumn == piece.column + 1))
                    {
                        // And the target square is same column the pawn
                        if (targetColumn == piece.column)
                        {
                            enPassantVictim = piece;

                            // If the activePiece is a white pawn
                            if (color == GamePanel.WHITE)
                            {
                                // The pawn may move towards black's back rank
                                if (targetRow == piece.row - 1)
                                {
                                    return true;
                                }
                            }
                            // If it's a black pawn
                            else if (color == GamePanel.BLACK)
                            {
                                // The pawn may move towards white's back rank
                                if (targetRow == piece.row + 1)
                                {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        enPassantVictim = null;
        return false;
    }

    // Piece states
    public boolean isInPromotionSquare()
    {
        // If this is a white pawn
        if (color == GamePanel.WHITE)
        {
            // And the pawn is in black's back rank
            if (preRow == 0)
            {
                // Then the pawn is in a promotion square
                return true;
            }
        }

        // If this is a black pawn
        else
        {
            // And the pawn is in white's back rank
            if (preRow == 7)
            {
                // Then the pawn is in a promotion square
                return true;
            }
        }


        return false;
    }




    // NOT USED IN THE PAWN CLASS
    @Override
    public boolean hasPieceBlockingIt(int targetColumn, int targetRow)
    {

        return false;
    }








}