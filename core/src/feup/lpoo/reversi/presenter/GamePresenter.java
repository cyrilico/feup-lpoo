package feup.lpoo.reversi.presenter;

import feup.lpoo.reversi.Reversi;
import feup.lpoo.reversi.model.AIModel;
import feup.lpoo.reversi.model.GameModel;
import feup.lpoo.reversi.model.MoveModel;
import feup.lpoo.reversi.model.PlayerModel;
import feup.lpoo.reversi.model.UserModel;

/**
 * Created by antonioalmeida on 12/05/2017.
 */

public class GamePresenter {
    private Reversi reversi;
    private AIPresenter AI;
    private GameModel game;
    private PlayerModel blackPlayer;
    private PlayerModel whitePlayer;
    private int type;
    AIMoveStrategy strategy;

    public GamePresenter(int type, AIMoveStrategy strategyChosen, Reversi reversi) {
        this.reversi = reversi;
        this.type = type;
        this.strategy = strategyChosen;

        AI = new AIPresenter(strategyChosen);
        resetPlayers();
        game = new GameModel(blackPlayer, whitePlayer);
        AI.setGame(game);
    }

    public void restartGame() {
        AI = new AIPresenter(strategy);
        resetPlayers();
        game = new GameModel(blackPlayer, whitePlayer);
        AI.setGame(game);
    }

    public String getPlayer1Points() {
        String result = String.format("%02d", game.getBlackPoints());
        return result;
    }

    public String getPlayer2Points() {
        String result = String.format("%02d", game.getWhitePoints());
        return result;
    }

    public String getResult() {
        int black = game.getBlackPoints();
        int white = game.getWhitePoints();

        String result;
        if(black > white)
            result =  "Black wins!";
        else if(white > black)
            result =  "White wins!";
        else
            result = "It's a tie!";
        return result;
    }

    public void handleInput(int x, int y) throws CloneNotSupportedException {
        MoveModel move = game.getValidMove(x, y);

        if(move != null) {
            game.getCurrentPlayer().setMove(move);
            game.getCurrentPlayer().setReady();
        }
    }

    public void updateGame() {
        if(!game.isOver()) {
            if (game.getCurrentPlayer().isReady()) {
                game.getCurrentPlayer().resetReady();
                try {
                    game.updateGame();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean gameOver() {
        return game.isOver();
    }

    public void updateAchievements() {
        boolean victory = userWon();
        reversi.getPlayServices().matchCompleted(victory);
    }

    public boolean userWon() {
        int black = game.getBlackPoints();
        int white = game.getWhitePoints();

        if(black > white)
            return true;

        return false;
    }

    public char getCurrentPiece(int x, int y) {
        return game.getPieceAt(x,y);
    }

    public void resetPlayers() {
        blackPlayer = new UserModel('B');
        if(type == 0)
            whitePlayer = new UserModel('W'); //Replace with macros
        else
            whitePlayer = new AIModel('W', AI); //Replace with macros
    }

    public boolean isBlackTurn() {
        return game.getCurrentPlayer() == blackPlayer;
    }

    public boolean isWhiteTurn() {
        return game.getCurrentPlayer() == whitePlayer;
    }

    public void undoMove() {
        try {
            if(!game.undoMove()) {
                resetPlayers();
                game = new GameModel(blackPlayer, whitePlayer);
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
