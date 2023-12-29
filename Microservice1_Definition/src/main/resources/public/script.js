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


/**
 * Formluar mit HTTP-Post absenden und Ergebnis in selber Seite anzeigen.
 *
 * @param {Event} event - Event-Objekt, das das Event ausgelöst hat; wird benötigt,
 *                        um Default-Verhalten des Browsers zu unterdrücken (nämlich
                          JSON-Response im Browser anzuzeigen).
 */
function formularAbsenden(event) {

    // Verhindern, dass die JSON-Response im Browser angezeigt wird
    event.preventDefault();

    if (!formularPruefen()) {

        return;
    }

    const form = document.forms["dasFormular"];
    const url  = form.action;

    const formData = new FormData(form);

    fetch(url, {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {

        ergebnisBox.style.display = "block"; // Ergebnis-Box auf "sichtbar" schalten

        const resultBox         = document.getElementById("ergebnisBox");
        const ergebnisNachricht = document.getElementById("ergebnisNachricht");
        const ergebnisUrl       = document.getElementById("ergebnisUrl");

        if (data.erfolgreich) {

            ergebnisNachricht.textContent = "Kurz-URL wurde angelegt:";

            const neueKurzUrl = `http://localhost:8123/k/${data.kuerzel}`;

            ergebnisUrl.href        = neueKurzUrl;
            ergebnisUrl.textContent = neueKurzUrl;

            resultBox.style.color = "green";

        } else {

            ergebnisNachricht.textContent = "FEHLER: " + data.fehler;
            resultBox.style.color = "red";
        }
    })
    .catch((error) => {

         alert("FEHLER: " + error);
    });
}


/**
 * Bei Klick auf Button "Eingaben zurücksetzen" wird hiermit auch die
 * "ergebnisBox" ausgeblendet.
 */
function hideErgebnisBox() {

    const ergebnisBox = document.getElementById("ergebnisBox");
    ergebnisBox.style.display = "none";
}
