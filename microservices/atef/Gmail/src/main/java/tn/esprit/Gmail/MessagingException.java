package tn.esprit.Gmail;

public class MessagingException extends Exception {
    private String message;

    // Constructeur par défaut
    public MessagingException() {
        super();
    }

    // Constructeur avec message personnalisé
    public MessagingException(String message) {
        super(message);
        this.message = message;
    }

    // Constructeur avec message et cause
    public MessagingException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    // Retourne le message d'erreur
    public String getMessage() {
        return message;
    }

    // Retourne les informations complètes de l'exception
    @Override
    public String toString() {
        return "MessagingException: " + message;
    }

    // Permet de lister la stack trace de l'exception
    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
