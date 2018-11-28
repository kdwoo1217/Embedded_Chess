package kr.ac.cau.embedded.a4chess;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import kr.ac.cau.embedded.a4chess.chess.Board;
import kr.ac.cau.embedded.a4chess.chess.Coordinate;
import kr.ac.cau.embedded.a4chess.chess.Game;
import kr.ac.cau.embedded.a4chess.chess.pieces.Piece;

public class BoardView extends View {

    private final Paint boardPaint = new Paint();
    private final Paint textPaint = new Paint();

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
        }
    }

    private void drawCoordinate(final Coordinate c, final Canvas canvas, final float cellWidth, final Paint paint, int boardSize) {
        canvas.drawRect(c.x * cellWidth, (boardSize - c.y - 1) * cellWidth, (c.x + 1) * cellWidth,
                (boardSize - c.y) * cellWidth, paint);
    }
}
