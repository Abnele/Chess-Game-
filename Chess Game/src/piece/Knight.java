package piece;

import Main.GamePanel;
import Main.Main;

import java.awt.image.BufferedImage;


public class Knight extends Piece{
    BufferedImage whiteKnight = getImage("/piece/WhiteKnight");
    BufferedImage blackKnight = getImage("/piece/BlackKnight");

    public Knight(int color, int column, int row){
        super(color, column, row);

        if (color == GamePanel.WHITE){
            image = getImage("/piece/WhiteKnight");
        }
        else {
            image = getImage("/piece/BlackKnight");
        }





    }

    @Override
    public boolean canMove(int targetColumn, int targetRow)
    {
        checkMovedTwoSquares();


        // If the target square is within the board
        // And said target square is in an L shape in relation to the previous square
        // And is not its previous square
        if (isWithinBoard(targetColumn,targetRow)
                && (!Main.gamePanel.squareIsOccupied(targetColumn,targetRow) || Main.gamePanel.squareIsOccupiedByEnemyPiece(targetColumn,targetRow))
                && isNotInPreviousSquare(targetColumn,targetRow)
                && isMovingInLShape(targetColumn, targetRow)
                && (!kingIsInCheck() || isCapturingCheckingPiece(targetColumn, targetRow))
            )
        {
            // Then the piece may move to that target square
            return true;
        }
        else
        {
            // If not, then the piece may not move to that target square
            return false;
        }

    }

    public boolean has2To1SquareRatio(int targetColumn, int targetRow)
    {
        /// This function makes sure the knight is moving:
        /// 2 times as many squares in one direction than the other
        /// It is part of the knight's L-shape movement
        if ((Math.abs(targetColumn - preColumn) == 2 * (Math.abs(targetRow - preRow))
            || Math.abs(targetRow - preRow) == 2 * (Math.abs(targetColumn - preColumn))))
        {
            return true;
        }

        else
        {
            return false;
        }

    }

    public boolean hasTwoAndOneMovementDifference(int targetColumn, int targetRow)
    {
       /// This function will return true
       /// If the difference between the current and next row/column is 1
       /// And the difference between the current and next column/row is 2

            if
            (

                    ((Math.abs(targetColumn - preColumn) == 1) &&(Math.abs(targetRow - preRow) == 2))
                  ||((Math.abs(targetColumn - preColumn) == 2) &&(Math.abs(targetRow - preRow) == 1))
            )


            {
                return true;
            }

            return false;






        }


    public boolean isMovingInLShape(int targetColumn, int targetRow)
    {
            if (has2To1SquareRatio(targetColumn, targetRow) && hasTwoAndOneMovementDifference(targetColumn, targetRow))
            {
                return true;
            }

            return false;

        }









    @Override
    public boolean hasPieceBlockingIt(int targetColumn, int targetRow)
    {

        return false;
    }


}
