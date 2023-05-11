package pl.warczynski.jedrzej.backend.services.impl.util;

import org.springframework.stereotype.Component;
import pl.warczynski.jedrzej.backend.models.tournament.duel.Duel;
import pl.warczynski.jedrzej.backend.models.tournament.duel.status.DuelStatus;
import pl.warczynski.jedrzej.backend.models.tournament.duel.status.PlayerStatus;
import pl.warczynski.jedrzej.backend.models.tournament.players.Player;

import java.util.*;

@Component
public class DuelsDrawer {
    private static final Random random = new Random();
    private List<Player> players;
    private String tournamentId;
    private Integer seedPlayers;
    private List<Duel> duels;
    int duelsNum;
    int emptyPlayers;
    int phasesNum;

    public List<Duel> generateDuels(List<Player> players, String tournamentId, Integer seedPlayers) {
        init(players, tournamentId, seedPlayers);
        drawDuels();
        return duels;
    }

    private void init(List<Player> players, String tournamentId, Integer seedPlayers) {
        this.players = players;
        this.tournamentId = tournamentId;
        this.seedPlayers = seedPlayers;
        duels = new ArrayList<>();

        setDuelsAndPhasesNumber();
        setEmptyPlayersNum();
    }

    private void setDuelsAndPhasesNumber() {
        int nearestPowerOfTwo = Integer.highestOneBit(players.size() - 1) << 1;
        this.duelsNum = nearestPowerOfTwo / 2;
        this.phasesNum = (int) (Math.log(duelsNum) / Math.log(2)) + 1;
    }

    private void setEmptyPlayersNum() {
        emptyPlayers = duelsNum * 2 - players.size();
    }

    private void drawDuels() {
        players.sort(Comparator.comparing(Player::getRank).reversed());
        drawFirstPhaseDuels();
        drawNextPhases();
    }

    private void drawFirstPhaseDuels() {
        for (int i = 0; i < duelsNum; i++) {
            int duelNum = getDuelNum();
            if (i < seedPlayers) {
                drawSeedPlayerDuel(i, duelNum);
            } else {
                drawUnseededDuel(i, duelNum);
            }
        }
    }

    private int getDuelNum() {
        return duels.size() % 2 == 0 ? duels.size() / 2 : duelsNum - duels.size() / 2 - 1;
    }

    private void drawSeedPlayerDuel(int topNPlayer, int duelNum) {
        if (topNPlayer < emptyPlayers) {
            drawSeedDuelWithoutOpponent(topNPlayer, duelNum);
        } else {
           drawOrdinarySeedDuel(topNPlayer, duelNum);
        }
    }

    private void drawSeedDuelWithoutOpponent(int topNPlayer, int duelNum) {
        Player player = players.get(topNPlayer);
        player.setPlayerStatus(PlayerStatus.WIN);
        duels.add(new Duel(tournamentId, player, Player.createEmptyPlayer(), player,
                DuelStatus.APPROVED, 1, duelNum));
    }

    private void drawOrdinarySeedDuel(int topNPlayer, int duelNum) {
        Player player1 = players.get(topNPlayer);
        player1.setPlayerStatus(PlayerStatus.DURING_GAMEPLAY);
        Player player2 = players.get(players.size() - topNPlayer - 1 + emptyPlayers);
        player2.setPlayerStatus(PlayerStatus.DURING_GAMEPLAY);
        duels.add(new Duel(tournamentId, player1, player2, Player.createEmptyPlayer(),
                DuelStatus.NOT_PLAYED, 1, duelNum));
    }

    private void drawUnseededDuel(int topNPlayer, int duelNum) {
        if (topNPlayer < emptyPlayers) {
            drawDuelWithEmptyOpponent(duelNum);
        } else {
            drawUnseededOrdinaryDuel(duelNum);
        }
    }

    private void drawDuelWithEmptyOpponent(int duelNum) {
        Player player = drawPlayer();
        while (isPaired(player)) {
            player = drawPlayer();
        }
        player.setPlayerStatus(PlayerStatus.WIN);
        duels.add(new Duel(tournamentId, player, Player.createEmptyPlayer(), player,
                DuelStatus.APPROVED, 1, duelNum));
    }

    private void drawUnseededOrdinaryDuel(int duelNum) {
        Player player1 = drawUnpairedPlayer();
        player1.setPlayerStatus(PlayerStatus.DURING_GAMEPLAY);
        Player player2 = drawOpponent(player1);
        player2.setPlayerStatus(PlayerStatus.DURING_GAMEPLAY);
        duels.add(new Duel(tournamentId, player1, player2, Player.createEmptyPlayer(),
                DuelStatus.NOT_PLAYED, 1, duelNum));
    }

    private Player drawOpponent(Player player1) {
        Player opponent = null;
        while (opponent == null) {
            Player potentialOpponent = drawPlayer();
            if (potentialOpponent != player1 && !isPaired(potentialOpponent)) {
                opponent = potentialOpponent;
            }
        }
        return opponent;
    }

    private Player drawUnpairedPlayer() {
        Player player = drawPlayer();
        while (isPaired(player)) {
            player = drawPlayer();
        }
        return player;
    }

    private Player drawPlayer() {
        int randomIndex = random.nextInt(players.size());
        return players.get(randomIndex);
    }

    private boolean isPaired(Player player) {
        for (Duel duel : duels) {
            if (duel.getPlayer1() == player || duel.getPlayer2() == player) {
                return true;
            }
        }

        return false;
    }

    private void drawNextPhases() {
        for (int phase = 2; phase <= phasesNum; phase++) {
            for (int duelNum = 0; duelNum < duelsNum / Math.pow(2, (double) phase - 1) ; duelNum += 1) {
                addDuel(phase, duelNum);
            }
        }
    }

    private void addDuel(Integer phase, Integer duelNum) {
        includePreviousPhaseWinners(phase, duelNum);
    }

    private void includePreviousPhaseWinners(Integer phase, Integer duelNum) {
        Player player1 = new Player(getWinner(phase-1, duelNum * 2));
        Player player2 = new Player(getWinner(phase-1 , duelNum * 2 + 1));

        player1.setPlayerStatus(PlayerStatus.DURING_GAMEPLAY);
        player2.setPlayerStatus(PlayerStatus.DURING_GAMEPLAY);

        duels.add(new Duel(tournamentId, player1,
                player2, Player.createEmptyPlayer(),
                DuelStatus.NOT_PLAYED ,phase, duelNum));
    }
    private Player getWinner(Integer phase, Integer duelNumber) {
        Optional<Player> winner = duels.stream()
                .filter(duel -> Objects.equals(duel.getPhase(), phase) && Objects.equals(duel.getDuelNumber(), duelNumber))
                .map(Duel::getWinner)
                .findFirst();
        return winner.orElseThrow(() -> new IllegalStateException("NO PREVIOUS WINNER"));
    }
}
