package piece;

import Main.*;


public class Rook extends Piece{
    public Rook(int color, int column, int row){
        super(color, column, row);

        if (color == GamePanel.WHITE){
            image = getImage("/piece/WhiteRook");
        }
        else {
            image = getImage("/piece/BlackRook");
        }

        preColumn = column;
        preRow = row;
        startColumn = column;
        startRow = row;

    }

    @Override
    public boolean canMove(int targetColumn, int targetRow)
    {

        checkMovedTwoSquares();


        // If the piece is within the board
        // And the target square is directly vertical from the piece's current square
        // OR directly horizontal from the piece's current square
        // And the target square is not occupied by an ally piece
        // And there are no pieces blocking it from reaching the target square
        if (isWithinBoard(targetColumn,targetRow)
                && (!Main.gamePanel.squareIsOccupied(targetColumn,targetRow) || Main.gamePanel.squareIsOccupiedByEnemyPiece(targetColumn,targetRow))
                && ((targetColumn != preColumn && targetRow == preRow)
            || (targetRow != preRow && targetColumn == preColumn))
            && !hasPieceBlockingIt(targetColumn, targetRow)
            && (!kingIsInCheck() || isCapturingCheckingPiece(targetColumn, targetRow))
        )
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

    public boolean isMovingCardinally(int targetColumn, int targetRow){
        if ((targetColumn != preColumn && targetRow == preRow)
                || (targetRow != preRow && targetColumn == preColumn)){
            return true;
        }
        return false;
    }

    @Override
    public boolean hasPieceBlockingIt(int targetColumn, int targetRow)
    {
        // For every piece on the board
        for (Piece piece : GamePanel.simPieces)
        {
            // If there's any piece on the same column as the rook
            // And it is in between the rook and its target square
            // Then the rook is blocked by that piece
            if (piece.column == column)
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
            else if (piece.row == row)
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

        return false;
    }

















    }
