package kr.ac.cau.embedded.a4chess;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import kr.ac.cau.embedded.a4chess.chess.Board;
import kr.ac.cau.embedded.a4chess.chess.Coordinate;
import kr.ac.cau.embedded.a4chess.chess.Game;
import kr.ac.cau.embedded.a4chess.chess.pieces.Piece;

public class BoardView extends View {

    private final Paint boardPaint = new Paint();
    private final Paint textPaint = new Paint();

    private Coordinate selection;

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        int boardSize = Board.getBoardSize();
        float cellWidth = canvas.getHeight() / (float) boardSize;
        Coordinate coordinate;
        Piece piece;
        textPaint.setTextSize(cellWidth);
        float textOffset = 0.15f * cellWidth;
        boardPaint.reset();
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                coordinate = new Coordinate(x, y);
                if (coordinate.isValid()) {
                    if ((x + y) % 2 == 0) boardPaint.setColor(Color.BLACK);
                    else boardPaint.setColor(Color.WHITE);
                    drawCoordinate(coordinate, canvas, cellWidth, boardPaint, boardSize);
                    if (isInEditMode()) {
                        continue;
                    }
                    piece = Board.getPiece(coordinate);
                    if (piece != null) {
                        textPaint.setColor(Game.getPlayerColor(piece.getPlayerId()));
                        canvas.drawText(piece.getString(), x * cellWidth,
                                (boardSize - y) * cellWidth - textOffset, textPaint);
                    }
                }
            }
            if (selection != null && (piece = Board.getPiece(selection)) != null) {
                boardPaint.setAlpha(128);
                boardPaint.setColor(Color.CYAN);
                canvas.drawCircle(selection.x * cellWidth + cellWidth / 2,
                        (boardSize - selection.y - 1) * cellWidth + cellWidth / 2, cellWidth / 2, boardPaint);
                textPaint.setColor(Game.getPlayerColor(piece.getPlayerId()));
                canvas.drawText(piece.getString(), selection.x * cellWidth,
                        (boardSize - selection.y) * cellWidth - 10, textPaint);
                for (Coordinate possible : piece.getPossiblePositions()) {
                    drawCoordinate(possible, canvas, cellWidth, boardPaint, boardSize);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int boardSize = Board.getBoardSize();
            int x = (int) (event.getX() / getHeight() * boardSize);
            int y = boardSize - 1 - (int) (event.getY() / getHeight() * boardSize);
            Coordinate coordinate = new Coordinate(x, y);
            if (coordinate.isValid() && Board.getPiece(coordinate) != null &&
                    Board.getPiece(coordinate).getPlayerId().equals(Game.currentPlayer())) {
                selection = coordinate;
                invalidate();
            } else {
                if (selection != null) { // we have a piece selected and clicked on a new position
                    if (Board.move(selection, coordinate)) {
                        selection = null;
                        invalidate();
                    }
                }
            }
        }
        return true;
    }

    private void drawCoordinate(final Coordinate coordinate, final Canvas canvas, final float cellWidth, final Paint paint, int boardSize) {
        canvas.drawRect(coordinate.x * cellWidth, (boardSize - coordinate.y - 1) * cellWidth, (coordinate.x + 1) * cellWidth,
                (boardSize - coordinate.y) * cellWidth, paint);
    }
}
