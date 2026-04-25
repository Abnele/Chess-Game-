package piece;

import Main.*;

public class Bishop extends Piece {
    public Bishop(int color, int column, int row){
        super(color, column, row);

        if (color == GamePanel.WHITE){
            image = getImage("/piece/WhiteBishop");
        }
        else {
            image = getImage("/piece/BlackBishop");
        }

    }

    public boolean canMove(int targetColumn, int targetRow)
    {
        checkMovedTwoSquares();


        // If the piece is within the board
        // And the target square is diagonal from the piece's current square
        try
        {
            if (isWithinBoard(targetColumn,targetRow)
                && (!Main.gamePanel.squareIsOccupied(targetColumn,targetRow) || Main.gamePanel.squareIsOccupiedByEnemyPiece(targetColumn,targetRow))
                && isMovingDiagonally(targetColumn, targetRow)
                && !hasPieceBlockingIt(targetColumn, targetRow)
                && (!kingIsInCheck() || isCapturingCheckingPiece(targetColumn, targetRow)))
            {
                // Then the piece can move to its target square
                return true;
            }
            else
            {
                // If not, then the piece can NOT move to its target square
                return false;
            }
        }
        // If the target square is the same as the previous square
        catch (ArithmeticException e)
        {
            // Then the piece cannot move to that square in its turn
            return false;
        }


    }

    @Override
    public boolean hasPieceBlockingIt(int targetColumn, int targetRow)
    {
        // Find a way to get the amount of squares diagonally the bishop goes
        // ...in its target square
        // Then turn it into an integer
        // If that integer is greater/lower than any piece's integer
        // Then the bishop is being blocked by the piece

        // Positive values of these integers mean moving down/right respectively
        // Negative values of these integers mean moving up/left respectively
        int changeInRow = targetRow - preRow;
        int changeInColumn = targetColumn - preColumn;

        // For every piece currently on the board
        for (Piece piece : GamePanel.simPieces)
        {
            // If the bishop is going down the board
            if (changeInRow > 0)
            {
                // If the bishop is going to the right
                if (changeInColumn > 0)
                {
                    {
                        // If the piece is directly diagonal from the bishop
                        // (If the difference in rows is equal to the difference in columns)
                        if (Math.abs(piece.row - preRow) == Math.abs(piece.column - preColumn)
                                // And the piece is down and to the right of the bishop
                                && piece.row > preRow && piece.column > preColumn
                                // And the target square is past the piece
                                && (targetRow > piece.row && targetColumn > piece.column)
                        )
                        {

                            // Then the piece is blocking the bishop
                            return true;


                        }
                    }
                }

                // If the bishop is going to the left
                else if (changeInColumn < 0)
                {
                    {
                        // If the piece is directly diagonal from the bishop
                        // (If the difference in rows is equal to the difference in columns)
                        if (Math.abs(piece.row - preRow) == Math.abs(piece.column - preColumn)
                                // And the piece is down and to the left of the bishop
                                && piece.row > preRow && piece.column < preColumn
                                // And the target square is past the piece
                                && (targetRow > piece.row && targetColumn < piece.column)
                        ) {
                            // Then the piece is blocking the bishop
                            return true;
                        }
                    }
                }

            }

            // If the bishop is going up the board
            else if (changeInRow < 0)
            {
                // If the bishop is going to the right
                if (changeInColumn > 0)
                {
                    {
                        // If the piece is directly diagonal from the bishop
                        // (If the difference in rows is equal to the difference in columns)
                        if (Math.abs(piece.row - preRow) == Math.abs(piece.column - preColumn)
                                // And the piece is up and to the right of the bishop
                                && piece.row < preRow && piece.column > preColumn
                                // And the target square is past the piece
                                && (targetRow < piece.row && targetColumn > piece.column)
                        )
                        {

                            // Then the piece is blocking the bishop
                            return true;


                        }
                    }
                }

                // If the bishop is going to the left
                else if (changeInColumn < 0)
                {
                    {
                        // If the piece is directly diagonal from the bishop
                        // (If the difference in rows is equal to the difference in columns)
                        if (Math.abs(piece.row - preRow) == Math.abs(piece.column - preColumn)
                                // And the piece is down and to the left of the bishop
                                && piece.row < preRow && piece.column < preColumn
                                // And the target square is past the piece
                                && (targetRow < piece.row && targetColumn < piece.column)
                        ) {
                            // Then the piece is blocking the bishop
                            return true;
                        }
                    }
                }

            }
        }




        return false;
    }

    public boolean isMovingDiagonally(int targetColumn, int targetRow)
    {
        if ((Math.abs(targetColumn - preColumn) == (Math.abs(targetRow - preRow)))
                && (Math.abs(targetColumn - preColumn) > 0 && Math.abs(targetRow - preRow) > 0))
        {
            return true;
        }
        return false;
    }






}
