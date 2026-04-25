package piece;

import Main.*;


public class Queen extends Piece{
    public Queen(int color, int column, int row)
    {
        super(color, column, row);

        if (color == GamePanel.WHITE){
            image = getImage("/piece/WhiteQueen");
        }
        else {
            image = getImage("/piece/BlackQueen");
        }

    }

    public boolean canMove(int targetColumn, int targetRow)
    {
        checkMovedTwoSquares();


        // If the piece is within the board and is not on an enemy piece
        if (isWithinBoard(targetColumn,targetRow) && !Main.gamePanel.squareIsOccupiedByAllyPiece(targetColumn, targetRow))
        {
            // And the target square is directly vertical from the piece's current square
            // OR directly horizontal from the piece's current square
            // OR diagonal from the piece's current square
            if (
                    isMovingLikeAQueen(targetColumn, targetRow)
                && (!kingIsInCheck() || isCapturingCheckingPiece(targetColumn, targetRow))

                )
            {
                if (!hasPieceBlockingIt(targetColumn, targetRow))
                {
                    // Then the piece can move to its target square
                    return true;
                }

                else
                {
                    return false;
                }

            }
                else
            {
                // If not, then the piece can NOT move to its target square
                return false;
            }
        }

        else
        {
            return false;
        }

    }

    public boolean isOnCardinalOrDiagonalSquare (int targetColumn, int targetRow)
    {

            // If it is moving vertically
            // And is not moving to its previous square
            if (
                    ((targetColumn != preColumn && targetRow == preRow)
                            || (targetRow != preRow && targetColumn == preColumn))
            )
            {
                // Then the piece is on a vertical square
                return true;
            }



            else if ((Math.abs(targetColumn - preColumn) == (Math.abs(targetRow - preRow))))
            {
                return true;
            }
            else
            {
                return false;
            }




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

    public boolean isMovingLikeAQueen(int targetColumn, int targetRow){
        if (((targetColumn != preColumn && targetRow == preRow)
                || (targetRow != preRow && targetColumn == preColumn))
                || ((Math.abs(targetColumn - preColumn) == (Math.abs(targetRow - preRow)))
                && (Math.abs(targetColumn - preColumn) > 0 && Math.abs(targetRow - preRow) > 0))){
            return true;
        }
        return false;
    }



    @Override
    public boolean hasPieceBlockingIt(int targetColumn, int targetRow)
    {

        // If the queen is moving like a rook
        if ((targetColumn != preColumn && targetRow == preRow)
                || (targetRow != preRow && targetColumn == preColumn))
        {
            // Then adopt the rook's piece blocking patterns
            // For every piece on the board
            for (Piece piece : GamePanel.simPieces)
            {
                // If there's any piece on the same column as the rook
                // And it is in between the rook and its target square
                // Then the rook is blocked by that piece
                if (piece.column == preColumn && !(piece instanceof King))
                {
                    // If the previous row is lower down than the piece
                    // and the piece is lower down than the target row
                    if (preRow > piece.row && piece.row > targetRow)
                    {
                        // Then the piece is blocking the rook
                        return true;
                    }

                    // If the previous row is higher up than the piece
                    // and the piece's row is higher up than the target row
                    else if (preRow < piece.row && piece.row < targetRow)
                    {
                        // Then the piece is blocking the rook
                        return true;
                    }
                }

                // If there's any piece on the same row as the rook
                // And it is in between the rook and its target square
                // Then the rook is blocked by that piece
                else if (piece.row == row && !(piece instanceof King))
                {
                    // If the previous column is to the right of the piece's column
                    // And the piece's column in to the right of the target column
                    if (preColumn > piece.column && piece.column > targetColumn)
                    {
                        // Then the piece is blocking the rook
                        return true;
                    }

                    // If the previous column is to the left of the piece's column
                    // And the piece's column is to the left of the target column
                    else if (preColumn < piece.column && piece.column < targetColumn)
                    {
                        // Then the piece is blocking the rook
                        return true;
                    }
                }

            }
        }

        // If the queen is moving like a bishop
        if ((Math.abs(targetColumn - preColumn) == (Math.abs(targetRow - preRow)))
                && (Math.abs(targetColumn - preColumn) > 0 && Math.abs(targetRow - preRow) > 0))
        {
            // Then adopt the bishop's piece blocking patterns

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





        return false;
    }







}




