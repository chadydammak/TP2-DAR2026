package Client;
import activite22.Operation;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client2_2 {
    public static void main(String[] args) {

        try (Scanner clavier = new Scanner(System.in)) {

            System.out.print("Premier nombre : ");
            double a = Double.parseDouble(clavier.nextLine());

            System.out.print("Opérateur (+, -, *, /) : ");
            String operateur = clavier.nextLine().trim();

            System.out.print("Deuxième nombre : ");
            double b = Double.parseDouble(clavier.nextLine());

            if (!operateur.matches("[+*/-]")) {
                System.out.println("Opérateur invalide.");
                return;
            }

            if (operateur.equals("/") && b == 0) {
                System.out.println("Division par zéro interdite.");
                return;
            }

            Operation op = new Operation(a, operateur, b);

            try (Socket socket = new Socket("localhost", 5000);
                 ObjectOutputStream sortie = new ObjectOutputStream(
                         socket.getOutputStream())) {

                sortie.flush();

                try (ObjectInputStream entree = new ObjectInputStream(
                        socket.getInputStream())) {

                    sortie.writeObject(op);
                    sortie.flush();

                    Object reponse = entree.readObject();

                    if (reponse instanceof Double) {
                        System.out.println(
                                "Résultat reçu : " + reponse);
                    } else if (reponse instanceof String) {
                        System.out.println("Erreur : " + reponse);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}
