package Serveur;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Serveur2_1 {
    public static void main(String[] args) {

        try (ServerSocket serveur = new ServerSocket(5000)) {

            System.out.println("Serveur en attente du client...");

            try (Socket socket = serveur.accept();
                 BufferedReader entree = new BufferedReader(
                         new InputStreamReader(socket.getInputStream()));
                 PrintWriter sortie = new PrintWriter(
                         socket.getOutputStream(), true)) {

                System.out.println("Client connecté !");

                String operation = entree.readLine();

                System.out.println("Opération reçue : " + operation);

                String[] parties = operation.trim().split("\\s+");

                if (parties.length != 3) {
                    sortie.println("Erreur : opération invalide.");
                    return;
                }

                double a = Double.parseDouble(parties[0]);
                String operateur = parties[1];
                double b = Double.parseDouble(parties[2]);

                double resultat;

                switch (operateur) {
                    case "+":
                        resultat = a + b;
                        break;

                    case "-":
                        resultat = a - b;
                        break;

                    case "*":
                        resultat = a * b;
                        break;

                    case "/":
                        if (b == 0) {
                            sortie.println("Erreur : division par zéro.");
                            return;
                        }
                        resultat = a / b;
                        break;

                    default:
                        sortie.println("Erreur : opérateur inconnu.");
                        return;
                }

                sortie.println(resultat);

                System.out.println("Résultat envoyé : " + resultat);
            }

        } catch (NumberFormatException e) {
            System.out.println("Erreur : les opérandes doivent être numériques.");
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

}
