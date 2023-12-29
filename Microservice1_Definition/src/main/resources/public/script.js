"use strict";

/**
 * Diese Funktion prüft, ob eine URL eingegeben wurde.
 * Die Prüfung erfolgt, wenn das Formular abgeschickt wird.
 * Wenn keine URL eingegeben wurde, dann wird eine Fehlermeldung angezeigt
 * und das Formular wird nicht abgeschickt.
 *
 * @returns {Boolean} true, wenn eine URL eingegeben wurde, sonst false
 */
function formularPruefen() {

    const x = document.forms["dasFormular"]["urlLang"].value;
    if (x.trim().length == 0) {

        alert("FEHLER: Keine Ziel-URL eingegeben");
        return false;

    }

    return true;
}