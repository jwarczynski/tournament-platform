package pl.warczynski.jedrzej.backend.services.impl.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.warczynski.jedrzej.backend.models.tournament.duel.Duel;
import pl.warczynski.jedrzej.backend.models.tournament.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DuelsDrawerTest {

    @BeforeEach
    void setUp() {
    }

    private List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= 13; i++) {
            players.add(new Player("t@" + i, "l" + i, i));
        }
        return players;
    }

    @Test
    void generateDuels() {
        List<Player> externalPlayers = getPlayers();
        String tournamentId = "74520e2c837acf2ceebc12d0";
        Integer seedPlayers = 3;

        DuelsDrawer duelsDrawer = new DuelsDrawer();
        List<Duel> duels = duelsDrawer.generateDuels(externalPlayers, tournamentId, seedPlayers);
        assertEquals(15, duels.size());
    }
}