package capitaly;
/*
Szimuláljuk az alábbi egyszerűsített Capitaly társasjátékot! Adott néhány eltérő stratégiájú játékos
és egy körpálya, amelyen különféle mezők sorakoznak egymás után. A pályát körbe-körbe újra
és újra bejárják a játékosok úgy, hogy egy kockával dobva mindig annyit lépnek, amennyit a
kocka mutat.

A mezők három félék lehetnek: ingatlanok, szolgáltatások és szerencse mezők. Az
ingatlant meg lehet vásárolni 1000 Petákért, majd újra rálépve házat is lehet rá építeni 4000
Petákért. Ha ezután más játékos erre a mezőre lép, akkor a mező tulajdonosának fizet: ha még
nincs rajta ház, akkor 500 Petákot, ha van rajta ház, akkor 2000 Petákot. A szolgáltatás mezőre
lépve a banknak kell befizetni a mező paramétereként megadott összeget. A szerencse mezőre
lépve a mező paramétereként megadott összegű pénzt kap a játékos.

Háromféle stratégiájú játékos vesz részt a játékban. Kezdetben mindenki kap egy induló tőkét (10000 Peták), majd a „mohó” játékos ha egy még
gazdátlan ingatlan mezőjére lépett, vagy övé az ingatlan, de még nincs rajta ház, továbbá van
elég tőkéje, akkor vásárol. Az „óvatos” játékos egy körben csak a tőkéjének a felét vásárolja
el, a „taktikus” játékos minden második vásárlási lehetőséget kihagyja. Ha egy játékosnak
fizetnie kell, de nincs elégendő pénze, akkor kiesik a játékból, házai elvesznek, ingatlanjai
megvásárolhatókká válnak.

A játék paramétereit egy szövegfájlból olvassuk be. Ez megadja a pálya hosszát, majd a pálya
egyes mezőit. Minden mezőről megadjuk annak típusát, illetve ha szolgáltatás vagy szerencse
mező, akkor annak pénzdíját. Ezt követően a fájl megmutatja a játékosok számát, majd sorban
minden játékos nevét és stratégiáját. A tesztelhetőséghez fel kell készíteni a megoldó
programot olyan szövegfájl feldolgozására is, amely előre rögzített módon tartalmazza a
kockadobások eredményét.

Írjuk ki, hogy adott számú kör után hogyan állnak (mennyi a tőkéjük, milyen ingatlanokat
birtokolnak) a versenyzők!
 */

import capitaly.player.Player;
import capitaly.io.InputDataParser;
import capitaly.io.InvalidInputException;

import java.util.*;

public final class Capitaly {
    private static final String[] TEST_FILE_PATHS = {
        "in.txt",
        "test_01.txt",  // test for _
        "test_02.txt",  // test for _
        "test_03.txt",  // test for _
        "test_04.txt"   // test for _
    };

    private final Track track;
    private final List<Player> players;
    private int currentPlayerIndex;

    public Capitaly(Track track, List<Player> players) {
        this.track = track;
        this.players = new ArrayList<>(players);
        this.currentPlayerIndex = 0;
    }

    public static void main(String[] args) throws InvalidInputException {
        InputDataParser parser = new InputDataParser(TEST_FILE_PATHS[0]);

        Capitaly game = new Capitaly(parser.getTrack(), parser.getPlayers());
        game.progress(parser.getDiceRolls());
        game.info();
    }

    private void progress(int roll) {
        if (this.currentPlayerIndex >= this.players.size()) this.currentPlayerIndex = 0;
        Player player = this.players.get(this.currentPlayerIndex++);
        track.progress(player, roll);
        if (player.isBankrupt()) this.eliminate(player);

        if (players.size() == 1) System.out.println(player.getName() + " won the game!");
        this.info();
        System.exit(0);
    }

    private void progress(int[] rolls) {
        for (Integer roll : rolls) this.progress(roll);
    }

    private void eliminate(Player player) {
        player.lose();
        this.track.remove(player);
        this.players.remove(player);
        System.out.println(player.getName() + " just got eliminated.");
    }

    private void info() {
        for (Player player : this.players) {
            System.out.println(player);
        }
    }
}
