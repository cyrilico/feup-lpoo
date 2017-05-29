package feup.lpoo.reversi.presenter;

import feup.lpoo.reversi.Reversi;
import feup.lpoo.reversi.model.GameModel;
import feup.lpoo.reversi.model.UserModel;

/**
 * Created by antonioalmeida on 29/05/2017.
 */

public class OnlineMultiplayerGamePresenter extends GamePresenter {
    String data;

    public OnlineMultiplayerGamePresenter(Reversi reversi) {
        super(reversi);
        initPlayers();
        game = new GameModel(blackPlayer, whitePlayer);
        data = reversi.getPlayServices().getMatchData();
    }

    @Override
    public void restartGame() {
        initPlayers();
        game = new GameModel(blackPlayer, whitePlayer);
    }

    @Override
    public void updateAchievements() {

    }

    @Override
    public void initPlayers() {
        blackPlayer = new UserModel('B');
        whitePlayer = new UserModel('W'); //Replace with macros
    }

    @Override
    public void undoMove() {
        System.out.println(data);
        reversi.getPlayServices().takeTurn("Teste 123");
    }
}
