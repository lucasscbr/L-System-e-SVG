import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        String axioma = "F-F-F-F";
        int tamanho = 5;
        int passos = 4;
        double angulo = Math.toRadians(90);
        String[] regras = {"F", "FF-F-F-F-FF"};
        String path = "gramatica.txt";

        try {
            BufferedReader buffRead = new BufferedReader(new FileReader(path));
            String linha = "";
            int numeroLinha = 1;
            while (true) {
                if (linha != null) {

                    linha = buffRead.readLine();

                    if (numeroLinha == 1)
                        passos = Integer.parseInt(linha);
                    else if (numeroLinha == 2)
                        axioma = linha;
                    else if (numeroLinha == 3)
                        angulo = Math.toRadians(Double.parseDouble(linha));
                    else if (numeroLinha >= 4) {
                        if (linha == null)
                            break;
                        regras = linha.split("→");
                    }

                    numeroLinha++;

                } else
                    break;

            }
            buffRead.close();

        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            System.out.println("Arquivo de texto não encontrado");
            e1.printStackTrace();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("Erro de leitura");
            e.printStackTrace();
        }

        String sentenca = gerarSentenca(axioma, passos, regras);

        desenharSentenca(sentenca, tamanho, angulo);
    }

    public static String gerarSentenca(String axioma, int passos, String[] regras) {
        String sentenca = axioma;
        int p = 0;

        do {
            sentenca = sentenca.replaceAll(regras[0], regras[1]);
            p++;
        } while (p != passos);


        System.out.println(sentenca);
        return sentenca;
    }

    public static void desenharSentenca(String sentenca, int len, double angulo) {
        double x = 0;
        double y = -len;
        //double sentido = angulo;
        double sentido = Math.toRadians(90);
        boolean girou = false;

        String path = "<path d=\"m 800, 500";

        for (int i = 0; i < sentenca.length(); i++) {
            if (sentenca.charAt(i) == 'F') {
                if (girou) {
                    if (sentido < Math.toRadians(90)) { // 1º quadrante
                        x = len * Math.sin(angulo);
                        y = len * Math.cos(angulo);
                    } else if (sentido > Math.toRadians(90) && sentido < Math.toRadians(180)) {    // 2º quadrante
                        x = len * Math.sin(angulo) * -1;
                        y = len * Math.cos(angulo);
                    } else if (sentido > Math.toRadians(180) && sentido < Math.toRadians(270)) {   // 3º quadrante
                        x = len * Math.cos(angulo) * -1;
                        y = len * Math.sin(angulo) * -1;
                    } else if (sentido > Math.toRadians(270) && sentido < Math.toRadians(360)) {   // 4º quadrante
                        x = len * Math.cos(angulo);
                        y = len * Math.sin(angulo) * -1;
                    } else if (sentido == Math.toRadians(90)) {
                        x = 0;
                        y = -len;
                    } else if (sentido == Math.toRadians(180)) {
                        x = -len;
                        y = 0;
                    } else if (sentido == Math.toRadians(270)) {
                        x = 0;
                        y = len;
                    } else if (sentido == Math.toRadians(360)) {
                        x = len;
                        y = 0;
                    }
                    path += ", " + x + " " + y;

                }
            } else if (sentenca.charAt(i) == '+') {
                girou = true;
                sentido -= angulo;
                if (sentido < Math.toRadians(0))
                    sentido = sentido + Math.toRadians(360);

            } else if (sentenca.charAt(i) == '-') {
                girou = true;
                sentido += angulo;
                if (sentido > Math.toRadians(360))
                    sentido = sentido - Math.toRadians(360);

            }


            try {
                FileWriter writer = new FileWriter("Desenho.svg");
                writer.write("<svg width=\"2000\" height=\"2000\"  viewBox=\"0 0 1000 1000\" xmlns=\"http://www.w3.org/2000/svg\">"
                        + "\n" + path + "\"" + "\n" + "fill=\"transparent\" stroke=\"black\"/>" + "\n" + "</svg>");
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

