package de.eldecker.dhbw.spring.urlshortener.ms_urlresolver.model;

public record AufgeloesterLink( String urlOriginal,
                                String beschreibung,
                                String zeitpunktErzeugung,
                                String zeitpunktAktualisierung,
                                boolean istAktiv
                              ) {
}