package se.chasacademy.fritidslagret.exception;

public class SearchStringMustBeAtleastTwoCharactersLongException extends RuntimeException {
    public SearchStringMustBeAtleastTwoCharactersLongException() {
        String message = "Du måste ange minst 2 tecken i söksträngen!";
        super(message);
    }
}
