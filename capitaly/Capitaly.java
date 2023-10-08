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

/**
 * Represents the Capitaly game.
 */
public final class Capitaly {
    /**
     * The paths of the test files.
     */
    private static final String[] TEST_FILE_PATHS = {
        "test/test_01.txt",  // test real-estate purchase/upgrade/fee mechanics
        "test/test_02.txt",  // test player type decision-making methods
        "test/test_03.txt",  // test manual play
    };

    /**
     * The track of the game.
     */
    private final Track track;

    /**
     * The players playing the game.
     */
    private final List<Player> players;

    /**
     * The index of the current player.
     */
    private int currentPlayerIndex;

    /**
     * Constructs a new Capitaly game with the given track and players.
     *
     * @param track   The track of the game.
     * @param players The players playing the game.
     */
    public Capitaly(Track track, List<Player> players) {
        this.track = track;
        this.players = new ArrayList<>(players);
        this.currentPlayerIndex = 0;
    }

    /**
     * The entry point of the program.
     * Starts the game with the given arguments.
     *
     * @param args The command line arguments.
     * @throws InvalidInputException If the input is invalid.
     */
    public static void main(String[] args) throws InvalidInputException {
        InputDataParser parser = new InputDataParser(TEST_FILE_PATHS[2]);
        Capitaly game = new Capitaly(parser.getTrack(), parser.getPlayers());

        int[] rolls = parser.getDiceRolls();
        if (rolls == null) game.manualGame();
        else game.simulateGame(rolls);
    }

    /**
     * Starts the game in manual mode.
     */
    private void manualGame() {
        System.out.println("Manual mode");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("-------------------------------------------------------------");
            System.out.print(players.get(currentPlayerIndex % players.size()).getName() + ": ");
            if (scanner.hasNextLine()) {
                try {
                    int value = InputDataParser.parseDiceRoll(scanner.nextLine());
                    this.progress(value);
                    this.info();
                } catch (InvalidInputException exception) {
                    System.out.println("\nPlease provide an integer between 1 and 6 for the dice roll!");
                }
            }
        }
    }

    /**
     * Starts the game in simulation mode.
     *
     * @param rolls The rolls of the die.
     */
    private void simulateGame(int[] rolls) {
        System.out.println("Simulation mode");
        this.progress(rolls);
        this.info();
    }

    /**
     * Progresses the game with the given roll, rolled by the current player.
     *
     * @param roll The roll of the die.
     */
    private void progress(int roll) {
        if (this.currentPlayerIndex >= this.players.size()) this.currentPlayerIndex = 0;
        Player player = this.players.get(this.currentPlayerIndex++);
        track.progress(player, roll);
        if (player.isBankrupt()) this.eliminate(player);

        if (players.size() == 1) {
            System.out.println(players.get(0).getName() + " won the game!");
            this.info();
            System.exit(0);
        }
    }

    /**
     * Progresses the game with the given rolls.
     *
     * @param rolls The rolls of the die.
     */
    private void progress(int[] rolls) {
        for (Integer roll : rolls) this.progress(roll);
    }

    /**
     * Eliminates the given player from the game.
     *
     * @param player The player to eliminate.
     */
    private void eliminate(Player player) {
        player.lose();
        this.track.remove(player);
        this.players.remove(player);
        System.out.println(player.getName() + " got eliminated.");
    }

    /**
     * Prints the information about the players.
     */
    private void info() {
        for (Player player : this.players) {
            System.out.println(player);
        }
    }
}
