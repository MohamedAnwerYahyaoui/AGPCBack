package com.example.chaima.utils;


public class BadWordFilter {

    private static final String[] BAD_WORDS = {
            "bad", "trash", "hate", "damn", "crap", "fool", "jerk", "stupid", "idiot", "khra","nul","msattek", "bhim"
    };

    // Méthode pour censurer les bad words (remplacement par des *)
    public static String filterBadWords(String content) {
        if (content == null || content.trim().isEmpty()) {
            return content;
        }

        String lowerCaseContent = content.toLowerCase();
        StringBuilder censoredContent = new StringBuilder(content);

        for (String badWord : BAD_WORDS) {
            int index = lowerCaseContent.indexOf(badWord.toLowerCase());
            while (index >= 0) {
                for (int i = index; i < index + badWord.length(); i++) {
                    censoredContent.setCharAt(i, '*');
                }
                index = lowerCaseContent.indexOf(badWord.toLowerCase(), index + 1);
            }
        }

        return censoredContent.toString();
    }

    // Méthode pour vérifier si un contenu contient des bad words
    public static boolean containsBadWords(String content) {
        if (content == null || content.trim().isEmpty()) {
            System.out.println("⚠️ Commentaire vide ou null");
            return false;
        }

        String lowerCaseContent = content.toLowerCase();

        for (String badWord : BAD_WORDS) {
            if (lowerCaseContent.contains(badWord.toLowerCase())) {
                System.out.println("🚨 Bad word détecté : " + badWord);
                return true;
            }
        }

        System.out.println("✅ Aucun bad word détecté");
        return false;
    }

}
