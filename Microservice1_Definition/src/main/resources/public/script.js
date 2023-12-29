"use strict";

function formularPruefen() {

    const x = document.forms["dasFormular"]["urlLang"].value;
    if (x.trim().length == 0) {

        alert("FEHLER: Keine Ziel-URL eingegeben");
        return false;
    }
}