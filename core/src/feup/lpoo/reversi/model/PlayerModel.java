package feup.lpoo.reversi.model;

/**
 * Created by antonioalmeida on 04/05/2017.
 */

public abstract class PlayerModel {
    protected int points;
    protected MoveModel move;
    protected char piece;
    protected boolean ready;

    public PlayerModel(char piece) {
        points = 2;
        this.piece = piece;
    }

    public void setMove(MoveModel move) {
        this.move = move;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public MoveModel getMove() {
        return move;
    }

    public char getPiece() {
        return piece;
    }

    public int getPoints() {
        return points;
    }

    public void setReady() {
        ready = true;
    }

    public void resetReady() {
        ready = false;
    }

    public boolean isReady() {
        return ready;
    }

}
