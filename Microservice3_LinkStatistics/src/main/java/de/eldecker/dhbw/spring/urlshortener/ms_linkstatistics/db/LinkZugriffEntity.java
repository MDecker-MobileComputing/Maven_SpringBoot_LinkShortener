package de.eldecker.dhbw.spring.urlshortener.ms_linkstatistics.db;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Index;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;


/**
 * Ein Objekt (Entity) dieser Entity-Klasse repräsentiert einen Tabellenzeile, die einen Zugriff auf
 * einen Link beschreibt.
 * <br><br>
 *
 * Es werden Klassen aus dem Paket {@code jakarta.persistence} verwendet, weil mit der Übergabe der
 * <i>Java Enterprise Edition (Java EE)</i> an die <i>Eclipse Foundation</i> durch Oracle das Paket
 * {@code javax.persistence} nicht mehr verwendet werden durfte.
 * <br><br>
 *
 * Achtung: In den <i>Named Queries</i> (JPQL) müssen die Klassennamen und Attribut-Namen verwendet
 * werden, die in der Entity-Klasse definiert sind, nicht die Namen der Tabellen und Spalten in der
 * Datenbank, also z.B. {@code LinkZugriffEntity} statt {@code LINK_ZUGRIFFE} oder {@code _kuerzel}
 * statt {@code KUERZEL}.
 */
@Entity
@Table(name = "LINK_ZUGRIFFE",
       indexes = { @Index(name = "IDX_KUERZEL"          , columnList = "kuerzel"           ),
                   @Index(name = "IDX_ZEITPUNKT"        , columnList = "zeitpunkt"         ),
                   @Index(name = "IDX_KUERZEL_ZEITPUNKT", columnList = "kuerzel, zeitpunkt")
                 }
      )
@NamedQueries({
    @NamedQuery(name = "LinkZugriff.countByKuerzel",
                query = "SELECT COUNT(lz) FROM LinkZugriffEntity lz WHERE lz._kuerzel = :kuerzel"),
    @NamedQuery(name = "LinkZugriff.countErfolgByKuerzel",
                query = "SELECT lz._erfolgreich, COUNT(lz) FROM LinkZugriffEntity lz WHERE lz._kuerzel = :kuerzel GROUP BY lz._erfolgreich"),
    @NamedQuery(name = "LinkZugriff.countByKuerzelAndPeriod",
                query = "SELECT SUM(CASE WHEN lz._zeitpunkt >= :oneDayAgo     THEN 1 ELSE 0 END) AS anzahl1Tag, " +
                               "SUM(CASE WHEN lz._zeitpunkt >= :sevenDaysAgo  THEN 1 ELSE 0 END) AS anzahl7Tage, " +
                               "SUM(CASE WHEN lz._zeitpunkt >= :thirtyDaysAgo THEN 1 ELSE 0 END) AS anzahl30Tage " +
                            "FROM LinkZugriffEntity lz WHERE lz._kuerzel = :kuerzel")
})
public class LinkZugriffEntity {

    /** Primärschlüssel, ist verpflichtend bei Verwendung von JPA. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long _id;

    /** Das Kürzel, das beim Zugriff aufgelöst werden sollte. */
    @Column(name = "kuerzel")
    private String _kuerzel;

    /** Zeitpunkt (Datum+Uhrzeit) des Zugriffs. */
    @Column(name = "zeitpunkt" )
    private Date _zeitpunkt;

    /** {@code true} genau dann, wenn die Kurz-URL aufgelöst werden konnte. */
    @Column(name = "erfolgreich" )
    private boolean _erfolgreich;


    /**
     * Default-Konstruktor, der für JPA benötigt wird.
     */
    public LinkZugriffEntity() {}


    /**
     * Konstruktor, der alle Attribute (bis auf den Primärschlüssel) initialisiert.
     *
     * @param kuerzel Das Kürzel, das beim Zugriff aufgelöst werden sollte.
     *
     * @param zeitpunkt Zeitpunkt (Datum+Uhrzeit) des Zugriffs.
     *
     * @param erfolgreich {@code true} genau dann, wenn die Kurz-URL aufgelöst werden konnte.
     */
    public LinkZugriffEntity(String kuerzel, Date zeitpunkt, boolean erfolgreich) {

        _kuerzel     = kuerzel;
        _zeitpunkt   = zeitpunkt;
        _erfolgreich = erfolgreich;
    }


    /**
     * Für den Primärschlüssel darf es nur einen Getter, aber keine Setter geben.
     *
     * @return Primärschlüssel
     */
    public Long getId() {

        return _id;
    }


    /**
     * Getter für das Kürzel, das aufgelöst werden sollte.
     *
     * @return Kürzel, z.B. "ab3"
     */
    public String getKuerzel() {

        return _kuerzel;
    }


    /**
     * Setter für das Kürzel, das aufgelöst werden sollte.
     *
     * @param kuerzel Kürzel, z.B. "ab3"
     */
    public void setKuerzel(String kuerzel) {

        _kuerzel = kuerzel;
    }


    /**
     * Getter für den Zeitpunkt (Datum+Uhrzeit) des Zugriffs.
     *
     * @return Zeitpunkt (Datum+Uhrzeit) des Zugriffs
     */
    public Date getZeitpunkt() {

        return _zeitpunkt;
    }


    /**
     * Setter für den Zeitpunkt (Datum+Uhrzeit) des Zugriffs.
     *
     * @param zeitpunkt Zeitpunkt (Datum+Uhrzeit) des Zugriffs
     */
    public void setZeitpunkt(Date zeitpunkt) {

        _zeitpunkt = zeitpunkt;
    }


    /**
     * Getter für Flag, das genau dann {@code true} ist,  wenn die Kurz-URL aufgelöst
     * werden konnte.
     *
     * @return {@code true} genau dann, wenn die Kurz-URL aufgelöst werden konnte,
     *         sonst {@code false}
     */
    public boolean isErfolgreich() {

        return _erfolgreich;
    }

    /**
     * Setter für Flag, das genau dann {@code true} ist, wenn die Kurz-URL aufgelöst
     * werden konnte.
     *
     * @param erfolgreich {@code true} genau dann, wenn die Kurz-URL aufgelöst werden
     *                    konnte, sonst {@code false}
     */
    public void setErfolgreich(boolean erfolgreich) {

        _erfolgreich = erfolgreich;
    }


    /**
     * Vergleicht zwei Objekte auf Gleichheit. Zwei Objekte sind genau dann gleich, wenn
     * sie die gleichen Werte für die Attribute {@code _kuerzel}, {@code _zeitpunkt} und
     * {@code _erfolgreich} haben.
     *
     * @param obj Objekt, mit dem dieses Objekt verglichen werden soll
     */
    @Override
    public boolean equals(Object obj) {

        if (obj == null) {

            return false;
        }
        if (this == obj) {

            return true;
        }
        if (obj instanceof LinkZugriffEntity that) {

            return Objects.equals(_kuerzel  , that._kuerzel  ) &&
                   Objects.equals(_zeitpunkt, that._zeitpunkt) &&
                  _erfolgreich == that._erfolgreich;

        } else {

            return false;
        }
    }


    /**
     * Erzeugt eine String-Repräsentation des Objekts.
     *
     * @return String-Repräsentation des Objekts, z.B.
     *         <pre>LinkZugriff [id=1, kuerzel=abc, zeitpunkt=2021-01-01 12:20:33, erfolgreich=true]</pre>
     */
    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer();
        sb.append("LinkZugriff [id=");
        sb.append(_id);
        sb.append(", kuerzel=");
        sb.append(_kuerzel);
        sb.append(", zeitpunkt=");
        sb.append(_zeitpunkt);
        sb.append(", erfolgreich=");
        sb.append(_erfolgreich);
        sb.append("]");
        return sb.toString();
    }


    /**
     * Hash-Code für das Objekt, berücksichtigt die Attribute {@code _kuerzel}, {@code _zeitpunkt} und
     * {@code _erfolgreich}.
     *
     * @return Hash-Code für das Objekt
     */
    @Override
    public int hashCode() {

        return Objects.hash(_kuerzel, _zeitpunkt, _erfolgreich);
    }

}
