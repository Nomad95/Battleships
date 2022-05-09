package pl.igor.battleships;

import pl.igor.battleships.application.BattleshipsFacade;
import pl.igor.battleships.application.game_configuration.StandardBattleshipsConfiguration;
import pl.igor.battleships.domain.adapters.InMemoryPlayerRepository;
import pl.igor.battleships.domain.model.BattlefieldCreator;
import pl.igor.battleships.domain.model.GameService;
import pl.igor.battleships.domain.model.PlayerCreator;
import pl.igor.battleships.domain.model.PlayerService;
import pl.igor.battleships.presentation.BattlefieldDto;
import pl.igor.battleships.presentation.Difficulty;
import pl.igor.battleships.presentation.PlayerDto;

public class BattleshipGame {
    public static void main(String[] args) {
        //first inject dependencies
        InMemoryPlayerRepository playerRepository = new InMemoryPlayerRepository();
        PlayerCreator playerCreator = new PlayerCreator();
        PlayerService players = new PlayerService(playerCreator, playerRepository);
        GameService gameService = new GameService(players, new BattlefieldCreator());
        BattleshipsFacade battleships = new BattleshipsFacade(players, gameService);

        //create players and a game
        PlayerDto lukasz = battleships.createHumanPlayer("Łukasz");
        //read difficulty
        PlayerDto r2D2 = battleships.createComputerPlayer("R2D2", Difficulty.EASY);

        StandardBattleshipsConfiguration config = new StandardBattleshipsConfiguration();
        BattlefieldDto battleshipsGame = battleships.createGame(lukasz, r2D2, config);


        //create players
        //generate battleships
        //let the play begin
    }
}
