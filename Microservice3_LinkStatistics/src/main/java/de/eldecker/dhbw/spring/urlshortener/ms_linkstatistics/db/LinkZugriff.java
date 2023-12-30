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


/**
 * Ein Objekt (Entity) dieser Entity-Klasse repräsentiert einen Tabellenzeile, die einen Zugriff auf 
 * einen Link beschreibt.
 * <br><br>
 * 
 * Es werden Klassen aus dem Paket {@code jakarta.persistence} verwendet, weil mit der Übergabe der 
 * <i>Java Enterprise Edition (Java EE)</i> an die <i>Eclipse Foundation</i> durch Oracle das Paket 
 * {@code javax.persistence} nicht mehr verwendet werden durfte.
 */
@Entity
@Table(name = "LINK_ZUGRIFFE", 
       indexes = { @Index(name = "IDX_KUERZEL"          , columnList = "kuerzel"           ),
                   @Index(name = "IDX_ZEITPUNKT"        , columnList = "zeitpunkt"         ), 
                   @Index(name = "IDX_KUERZEL_ZEITPUNKT", columnList = "kuerzel, zeitpunkt")
                 }
      )
public class LinkZugriff {
    
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
    public LinkZugriff() {}


    /**
     * Konstruktor, der alle Attribute (bis auf den Primärschlüssel) initialisiert.
     * 
     * @param kuerzel Das Kürzel, das beim Zugriff aufgelöst werden sollte.
     * 
     * @param zeitpunkt Zeitpunkt (Datum+Uhrzeit) des Zugriffs.
     * 
     * @param erfolgreich {@code true} genau dann, wenn die Kurz-URL aufgelöst werden konnte.
     */
    public LinkZugriff(String kuerzel, Date zeitpunkt, boolean erfolgreich) {

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


    public String getKuerzel() {

        return _kuerzel;
    }


    public void setKuerzel(String kuerzel) {

        _kuerzel = kuerzel;
    }


    public Date getZeitpunkt() {

        return _zeitpunkt;
    }


    public void setZeitpunkt(Date zeitpunkt) {

        _zeitpunkt = zeitpunkt;
    }


    public boolean isErfolgreich() {

        return _erfolgreich;
    }


    public void setErfolgreich(boolean erfolgreich) {

        _erfolgreich = erfolgreich;
    }


    @Override
    public boolean equals(Object obj) {

        if (obj == null) { 
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LinkZugriff)) {

            return false;
        }

        LinkZugriff that = (LinkZugriff) obj;
        return Objects.equals(_kuerzel, that._kuerzel)     &&              
               Objects.equals(_zeitpunkt, that._zeitpunkt) &&
               _erfolgreich == that._erfolgreich;
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
