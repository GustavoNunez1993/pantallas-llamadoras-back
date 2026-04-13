package com.datalock.datalock.utils;

public class DigitoVerificador {
    /**
     * Devuelve el digito verificador del RUC a partir de un String.<br>
     * Si el String no representa un numero devuelve -1
     *
     * @param numero
     * @return
     */
    public static int getDigitoVerificador(String numero) {
        Double ret = -1.0;
        double factor = 2;
        double acumulador = 0;
        String nuevonro = "";
        if (numero != null) {
            numero = numero.trim();
            for (int i = 0; i < numero.length(); i++) {
                Character ch = numero.charAt(i);
                if (!Character.isDigit(ch)) {
                    nuevonro += (int) ch;
                } else {
                    nuevonro += ch.toString();
                }
            }
            int len = nuevonro.length();
            for (int i = 0; i < len; i++) {
                String tmp = nuevonro.substring(len - (i + 1), len - i);
                acumulador += (factor * Double.parseDouble(tmp));
                factor++;
            }
            ret = (acumulador * 10) % 11;
        }
        if (ret >= 10) {
            ret = 0.0;
        }
        return ret.intValue();
    }
}
