package com.get.dia.ui.assistente;

import android.content.Context;

import android.os.StrictMode;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;

public class IA {
    private ArrayList<String> listaPalavras = new ArrayList<String>();
    private BaseDados baseDados = new BaseDados();
    private Context context;
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    private String texto;

    /**
     * Metodo para verificar e monatar uma frase para a analise da Assistente
     *
     * @param t é o texto escrito no textfield
     * @return Retorna uma resposta da IA
     */
    public String verificar(String t, Context context) {
        try {
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            setTexto(t);
            setContext(context);
            boolean ver = false;
            String result = "";
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                String total = "";
                t += " ";
                for (int i = 0; i < t.length(); i++) {
                    if (t.charAt(i) == ' ') {
                        listaPalavras.add(total);
                        total = "";
                    } else {
                        if (t.charAt(i) == '?') {
                            total += "";
                        } else {
                            total += t.charAt(i);
                        }
                    }
                }
                for (int i = 0; i < listaPalavras.size(); i++) {
                    String a = listaPalavras.get(i);
                    if (a.equalsIgnoreCase("Bom") || a.equalsIgnoreCase("Boa")) {
                        if (listaPalavras.size() < 2) {
                            result = "Melhor te ajudando!! Diga algo";
                            listaPalavras.clear();
                            break;
                        }
                        if (listaPalavras.get(i + 1).equalsIgnoreCase("dia")) {
                            result = "Bom dia como posso ajudar ?";
                            listaPalavras.clear();
                            break;
                        }
                        if (listaPalavras.get(i + 1).equalsIgnoreCase("tarde")) {
                            result = "Boa tarde como posso ajudar ?";
                            listaPalavras.clear();
                            break;
                        }
                        if (listaPalavras.get(i + 1).equalsIgnoreCase("noite")) {
                            result = "Boa noite como posso ajudar ?";
                            listaPalavras.clear();
                            break;
                        }
                    } else if (a.equalsIgnoreCase("Tudo")) {
                        if (listaPalavras.size() < 2) {
                            result = "Tudo pra ti!";
                            listaPalavras.clear();
                            break;
                        }
                        if (listaPalavras.get(i + 1).equalsIgnoreCase("bem") || listaPalavras.get(i + 1).equalsIgnoreCase("bom")) {
                            result = "Tudo ótimo! Como posso ajudar ?";
                            listaPalavras.clear();
                            break;
                        }
                    } else if (a.equalsIgnoreCase("Oi") || a.equalsIgnoreCase("Ola")
                            || a.equalsIgnoreCase("Opa") || a.equalsIgnoreCase("Ei")) {
                        if (listaPalavras.size() < 2) {
                            result = "Oi!! Como posso te ajudar ?";
                            listaPalavras.clear();
                            break;
                        }
                    } else {
                        try {
                            if (listaPalavras.size() > 1) {
                                String pergunta = "";
                                for (String s : listaPalavras) {
                                    if (s.equalsIgnoreCase("Como") || s.equalsIgnoreCase("o'que")
                                            || s.equalsIgnoreCase("Oque")) {
                                        s = "como";
                                    } else if (s.equalsIgnoreCase("Queria") || s.equalsIgnoreCase("Necessito")
                                            || s.equalsIgnoreCase("Quero") || s.equalsIgnoreCase("Descobrir")
                                            || s.equalsIgnoreCase("Ver") || s.equalsIgnoreCase("Acessar")) {
                                        s = "acessar";
                                    }
                                    pergunta += s;
                                }
                                String resposta = baseDados.ler(pergunta);

                                if (resposta != null) {
                                    result = resposta;
                                    listaPalavras.clear();
                                    return result;
                                } else {
                                    setText(getWiki(getContext()));
                                    if (!text.equals(" ")) {
                                        return text;
                                    }
                                }
                            }
                            setText(getWiki(getContext()));
                            if (!getText().equals(" ")) {
                                System.out.println("Fui true");
                                return text;
                            } else {
                                result = "Seja mais especifico :D \n" +
                                        "A dia pode te ajudar  com aprendizado e educação.";
                                listaPalavras.clear();
                                return result;
                            }
                        } catch (Exception erro) {
                            System.out.println(erro.getMessage());
                            erro.printStackTrace();
                            return "contate o suporte";
                        }
                    }
                }
            }
            return "Sem respostas no momento \n" +
                    "Que tal um feedback sobre o caso ?";
        } catch (Exception e) {
            e.printStackTrace();
            return "contate o suporte";
        }
    }

    public String getWiki(Context cxt) {
        try {
            Document doc = Jsoup.connect("https://www.google.com/search?q=" + getTexto()).get();
            String html = doc.html();
            System.out.println(html);
            File file = new File(cxt.getFilesDir(), "iaWeb.txt");
            FileOutputStream outputStream;
            outputStream = cxt.openFileOutput("iaWeb.txt", Context.MODE_PRIVATE);
            outputStream.write(html.getBytes());
            outputStream.close();
            Elements el = doc.getElementsByClass("LuVEUc B03h3d P6OZi V14nKc ptcLIOszQJu__wholepage-card wp-ms");
            Elements al = null;
            int i = 0;
            for (Element oi : el) {
                al = oi.getElementsByTag("span");
                break;
            }
            if (al == null) {
                return " ";
            } else {
                System.out.println(al.text());
                return al.text();
            }
        } catch (IOException ie) {
            ie.printStackTrace();
            System.out.println("IOEXCEPTION");
            return " ";
        } catch (Exception e) {
            e.printStackTrace();
            return " ";
        }

    }
}

