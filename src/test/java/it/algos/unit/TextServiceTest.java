package it.algos.unit;

import it.algos.test.*;
import static it.algos.vaad23.backend.boot.VaadCost.*;
import it.algos.vaad23.backend.service.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.*;
import java.util.stream.*;

/**
 * Project vaadin23
 * Created by Algos
 * User: gac
 * Date: lun, 07-mar-2022
 * Time: 19:46
 * <p>
 * Unit test di una classe di servizio (di norma) <br>
 * Estende la classe astratta ATest che contiene le regolazioni essenziali <br>
 * Nella superclasse ATest vengono iniettate (@InjectMocks) tutte le altre classi di service <br>
 * Nella superclasse ATest vengono regolati tutti i link incrociati tra le varie classi singleton di service <br>
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("testAllValido")
@DisplayName("Text service")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TextServiceTest extends ATest {


    /**
     * Classe principale di riferimento <br>
     * Gia 'costruita' nella superclasse <br>
     */
    private TextService service;


    //--testoIn
    //--primaMaiuscola
    //--primaMinuscola
    protected static Stream<Arguments> NOMI() {
        return Stream.of(
                Arguments.of(null, VUOTA, VUOTA),
                Arguments.of(VUOTA, VUOTA, VUOTA),
                Arguments.of("MARIO", "MARIO", "mARIO"),
                Arguments.of("mario", "Mario", "mario"),
                Arguments.of("Mario", "Mario", "mario"),
                Arguments.of("maRio", "MaRio", "maRio"),
                Arguments.of("MaRio", "MaRio", "maRio"),
                Arguments.of(" mario", "Mario", "mario"),
                Arguments.of("mario ", "Mario", "mario"),
                Arguments.of(" mario ", "Mario", "mario"),
                Arguments.of(" Mario", "Mario", "mario"),
                Arguments.of("Mario ", "Mario", "mario"),
                Arguments.of(" Mario ", "Mario", "mario")
        );
    }

    /**
     * Execute only once before running all tests <br>
     * Esegue una volta sola, chiamato dalle sottoclassi <br>
     * Invocare PRIMA il metodo setUpAll() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeAll
    protected void setUpAll() {
        super.setUpAll();

        //--reindirizzo l'istanza della superclasse
        service = textService;
    }


    /**
     * Qui passa prima di ogni test <br>
     * Invocare PRIMA il metodo setUpEach() della superclasse <br>
     * Si possono aggiungere regolazioni specifiche <br>
     */
    @BeforeEach
    protected void setUpEach() {
        super.setUpEach();
    }


    @Test
    @Order(1)
    @DisplayName("1 - Controlla testo vuoto")
    void testIsEmpty() {
        ottenutoBooleano = service.isEmpty(null);
        assertTrue(ottenutoBooleano);

        ottenutoBooleano = service.isEmpty(VUOTA);
        assertTrue(ottenutoBooleano);

        ottenutoBooleano = service.isEmpty(PIENA);
        assertFalse(ottenutoBooleano);

        System.out.println("1 - Controlla testo vuoto");
        System.out.println("Fatto");
    }


    @Test
    @Order(2)
    @DisplayName("2 - Controlla validità")
    void testIsValid() {
        ottenutoBooleano = service.isValid(null);
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.isValid(VUOTA);
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.isValid(PIENA);
        assertTrue(ottenutoBooleano);

        System.out.println("2 - Controlla validità di una stringa");
        System.out.println("Fatto");
        System.out.println(VUOTA);

        ottenutoBooleano = service.isValid((List) null);
        assertFalse(ottenutoBooleano);

        ottenutoBooleano = service.isValid((new ArrayList()));
        assertFalse(ottenutoBooleano);

        System.out.println("2 - Controlla validità di un oggetto generico");
        System.out.println("Fatto");
    }

    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(3)
    @DisplayName("3 - Prima maiuscola")
        //--testoIn
        //--primaMaiuscola
        //--primaMinuscola
    void primaMaiuscola(final String sorgente, final String previsto, final String nonUsata) {
        System.out.println("3 - Prima maiuscola");
        System.out.println(VUOTA);
        ottenuto = service.primaMaiuscola(sorgente);
        assertEquals(previsto, ottenuto);
        System.out.println(String.format("%s%s%s", sorgente, FORWARD, ottenuto));
        System.out.println(VUOTA);
    }


    @ParameterizedTest
    @MethodSource(value = "NOMI")
    @Order(4)
    @DisplayName("4 - Prima minuscola")
    void primaMinuscola(final String sorgente, final String nonUsata, final String previsto) {
        System.out.println("4 - Prima minuscola");
        System.out.println(VUOTA);
        ottenuto = service.primaMinuscola(sorgente);
        assertEquals(previsto, ottenuto);
        System.out.println(String.format("%s%s%s", sorgente, FORWARD, ottenuto));
    }


    @Test
    @Order(5)
    @DisplayName("5 - Restituisce un array di stringhe")
    void getArray() {
        System.out.println("5 - Restituisce un array da una stringa di valori multipli separati da virgole");
        System.out.println(VUOTA);

        ottenutoArray = service.getArray(null);
        assertNull(ottenutoArray);

        ottenutoArray = service.getArray(sorgente);
        assertNull(ottenutoArray);
        printLista(ottenutoArray);

        sorgente = "codedescrizioneordine";
        String[] stringArray2 = {"codedescrizioneordine"};
        previstoArray = new ArrayList(Arrays.asList(stringArray2));
        ottenutoArray = service.getArray(sorgente);
        assertNotNull(ottenutoArray);
        assertEquals(ottenutoArray, previstoArray);
        printLista(ottenutoArray);

        sorgente = "code,descrizione,ordine";
        String[] stringArray3 = {"code", "descrizione", "ordine"};
        previstoArray = new ArrayList(Arrays.asList(stringArray3));
        ottenutoArray = service.getArray(sorgente);
        assertNotNull(ottenutoArray);
        assertEquals(ottenutoArray, previstoArray);
        printLista(ottenutoArray);

        sorgente = " code, descrizione , ordine ";
        String[] stringArray4 = {"code", "descrizione", "ordine"};
        previstoArray = new ArrayList(Arrays.asList(stringArray4));
        ottenutoArray = service.getArray(sorgente);
        assertNotNull(ottenutoArray);
        assertEquals(ottenutoArray, previstoArray);
        printLista(ottenutoArray);
    }


    @Test
    @Order(6)
    @DisplayName("6 - Sostituisce una parte di testo")
    public void sostituisce() {
        System.out.println("6 - Sostituisce una parte di testo");

        sorgente = "{{Simbolo|Italian Province (Crown).svg|24}} {{IT-SU}}";
        sorgente2 = "Province";
        sorgente3 = "Regioni";
        previsto = "{{Simbolo|Italian Regioni (Crown).svg|24}} {{IT-SU}}";

        ottenuto = service.sostituisce(sorgente, sorgente2, sorgente3);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        System.out.println(VUOTA);
        System.out.println(String.format("Testo originale %s%s", FORWARD, sorgente));
        System.out.println(String.format("Da levare %s%s", FORWARD, sorgente2));
        System.out.println(String.format("Da mettere %s%s", FORWARD, sorgente3));
        System.out.println(String.format("Testo ottenuto %s%s", FORWARD, previsto));
    }

    @Test
    @Order(7)
    @DisplayName("7 - Elimina un tag da un testo")
    public void levaTesto() {
        System.out.println("7 - Elimina un tag da un testo");

        sorgente = "{{Simbolo|Italian Province (Crown).svg|24}} {{IT-SU}}";
        sorgente2 = "i";
        previsto = "{{Smbolo|Italan Provnce (Crown).svg|24}} {{IT-SU}}";

        ottenuto = service.levaTesto(sorgente, sorgente2);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        System.out.println(VUOTA);
        System.out.println(String.format("Testo originale %s%s", FORWARD, sorgente));
        System.out.println(String.format("Da levare %s%s", FORWARD, sorgente2));
        System.out.println(String.format("Testo ottenuto %s%s", FORWARD, previsto));
    }

    @Test
    @Order(8)
    @DisplayName("8 - Elimina le virgole dal testo")
    public void levaVirgole() {
        System.out.println("8 - Elimina le virgole dal testo");

        sorgente = "{{Sim,bolo, Italian, Prov,ince, (Crown).svg|24}}, {{IT,SU}}";
        previsto = "{{Simbolo Italian Province (Crown).svg|24}} {{ITSU}}";

        ottenuto = service.levaVirgole(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        System.out.println(VUOTA);
        System.out.println(String.format("Testo originale %s%s", FORWARD, sorgente));
        System.out.println(String.format("Testo ottenuto %s%s", FORWARD, previsto));

        sorgente = "125,837,655";
        previsto = "125837655";

        ottenuto = service.levaVirgole(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        System.out.println(VUOTA);
        System.out.println(String.format("Testo originale %s%s", FORWARD, sorgente));
        System.out.println(String.format("Testo ottenuto %s%s", FORWARD, previsto));
    }

    @Test
    @Order(9)
    @DisplayName("9 - Elimina i punti dal testo")
    public void levaPunti() {
        System.out.println("9 - Elimina i punti dal testo");

        sorgente = "/Users.gac.Documents.IdeaProjects.operativi.vaadflow14.src.main.java.it.algos.vaadflow14.wizard";
        previsto = "/UsersgacDocumentsIdeaProjectsoperativivaadflow14srcmainjavaitalgosvaadflow14wizard";

        ottenuto = service.levaPunti(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        System.out.println(VUOTA);
        System.out.println(String.format("Testo originale %s%s", FORWARD, sorgente));
        System.out.println(String.format("Testo ottenuto %s%s", FORWARD, previsto));

        sorgente = "125.837.655";
        previsto = "125837655";

        ottenuto = service.levaPunti(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        System.out.println(VUOTA);
        System.out.println(String.format("Testo originale %s%s", FORWARD, sorgente));
        System.out.println(String.format("Testo ottenuto %s%s", FORWARD, previsto));
    }


    @Test
    @Order(10)
    @DisplayName("10 - Sostituisce gli slash con punti")
    public void slashToPoint() {
        System.out.println("10 - Sostituisce gli slash con punti");

        sorgente = " Users/gac/Documents/IdeaProjects/operativi/vaadflow14/src/main/java/it/algos/vaadflow14/wizard ";
        previsto = "Users.gac.Documents.IdeaProjects.operativi.vaadflow14.src.main.java.it.algos.vaadflow14.wizard";

        ottenuto = service.slashToPoint(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        System.out.println(VUOTA);
        System.out.println(String.format("Testo originale %s%s", FORWARD, sorgente));
        System.out.println(String.format("Testo ottenuto %s%s", FORWARD, previsto));

        sorgente = " /Users/gac/Documents/IdeaProjects/operativi/vaadflow14/src/main/java/it/algos/vaadflow14/wizard";
        previsto = "/Users.gac.Documents.IdeaProjects.operativi.vaadflow14.src.main.java.it.algos.vaadflow14.wizard";

        ottenuto = service.slashToPoint(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        System.out.println(VUOTA);
        System.out.println(String.format("Testo originale %s%s", FORWARD, sorgente));
        System.out.println(String.format("Testo ottenuto %s%s", FORWARD, previsto));
    }


    @Test
    @Order(11)
    @DisplayName("11 - Sostituisce i punti con slash")
    public void pointToSlash() {
        System.out.println("11 - Sostituisce i punti con slash");

        sorgente = " Users.gac.Documents.IdeaProjects.operativi.vaadflow14.src.main.java.it.algos.vaadflow14.wizard ";
        previsto = "Users/gac/Documents/IdeaProjects/operativi/vaadflow14/src/main/java/it/algos/vaadflow14/wizard";

        ottenuto = service.pointToSlash(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        System.out.println(VUOTA);
        System.out.println(String.format("Testo originale %s%s", FORWARD, sorgente));
        System.out.println(String.format("Testo ottenuto %s%s", FORWARD, previsto));

        sorgente = " /Users.gac.Documents.IdeaProjects.operativi.vaadflow14.src.main.java.it.algos.vaadflow14.wizard ";
        previsto = "/Users/gac/Documents/IdeaProjects/operativi/vaadflow14/src/main/java/it/algos/vaadflow14/wizard";

        ottenuto = service.pointToSlash(sorgente);
        assertNotNull(ottenuto);
        assertEquals(previsto, ottenuto);

        System.out.println(VUOTA);
        System.out.println(String.format("Testo originale %s%s", FORWARD, sorgente));
        System.out.println(String.format("Testo ottenuto %s%s", FORWARD, previsto));
    }

    /**
     * Qui passa al termine di ogni singolo test <br>
     */
    @AfterEach
    void tearDown() {
    }


    /**
     * Qui passa una volta sola, chiamato alla fine di tutti i tests <br>
     */
    @AfterAll
    void tearDownAll() {
    }

}