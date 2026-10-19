package Client;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client2_1 {
    public static void main(String[] args) {

        try ( Scanner clavier = new Scanner(System.in)) {

            System.out.print("Entrez une opération (ex : 55 * 25) : ");
            String operation = clavier.nextLine();

            String[] parties = operation.trim().split("\\s+");

            if (parties.length != 3) {
                System.out.println("Syntaxe incorrecte.");
                return;
            }

            try {
                Double.parseDouble(parties[0]);
                Double.parseDouble(parties[2]);
            } catch (NumberFormatException e) {
                System.out.println("Les deux opérandes doivent être des nombres.");
                return;
            }

            if (!parties[1].matches("[+*/-]")) {
                System.out.println("Opérateur invalide.");
                return;
            }

            if (parties[1].equals("/") &&
                    Double.parseDouble(parties[2]) == 0) {
                System.out.println("Division par zéro interdite.");
                return;
            }

            try (Socket socket = new Socket("localhost", 5000);
                 PrintWriter sortie = new PrintWriter(
                         socket.getOutputStream(), true);
                 BufferedReader entree = new BufferedReader(
                         new InputStreamReader(socket.getInputStream()))) {

                sortie.println(operation);

                String resultat = entree.readLine();

                System.out.println("Résultat reçu : " + resultat);
            }

        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

}
