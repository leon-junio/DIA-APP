package com.get.dia.ui.assistente;

import com.get.dia.ui.loads.LoadActivity;

public class BaseDados {
    private String linha;
    private String p = "", s;
    private boolean teste = false;

    /**
     * Método para ver a pergunta no banco de dados e procurar por uma resposta válida
     *
     * @param pergunta pergunta ja processada
     * @return Uma resposta do Banco de questões
     */
    public String ler(String pergunta) {
        try {
            s = "";
            linha = LoadActivity.linha;
            for (int i = 0; i < linha.length(); i++) {
                if (linha.charAt(i) != '?') {
                    p += linha.charAt(i);
                    System.out.println(pergunta + " " + p);
                }
                if (p.equalsIgnoreCase(pergunta)) {
                    System.out.println(p);
                    for (int j = i + 3; j < linha.length(); j++) {
                        if (linha.charAt(j) != '!') {
                            s += linha.charAt(j);
                            System.out.println(s + " resposta");
                        } else {
                            teste = true;
                            break;
                        }
                    }
                }
                if (linha.charAt(i) == '?') {
                    int s = i;
                    for (int z = i; z < linha.length(); z++) {
                        if (linha.charAt(z) != '!') {
                            s++;
                        } else {
                            i = s + 1;
                            break;
                        }
                    }
                    p = "";
                }
                if (teste) {
                    break;
                }
            }
            p = "";
            if (teste) {
                teste = false;
                return s;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.print("Reader erro");
            e.printStackTrace();
            return null;
        }

    }
}