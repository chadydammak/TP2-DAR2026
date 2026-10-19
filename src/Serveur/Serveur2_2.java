package Serveur;
import activite22.Operation;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur2_2 {
    public static void main(String[] args) {

        try (ServerSocket serveur = new ServerSocket(5000)) {

            System.out.println("Serveur en attente du client...");

            try (Socket socket = serveur.accept();
                 ObjectOutputStream sortie = new ObjectOutputStream(
                         socket.getOutputStream())) {

                sortie.flush();

                try (ObjectInputStream entree = new ObjectInputStream(
                        socket.getInputStream())) {

                    System.out.println("Client connecté !");

                    Operation op = (Operation) entree.readObject();

                    System.out.println("Premier nombre : " + op.getA());
                    System.out.println("Opérateur : " + op.getOperateur());
                    System.out.println("Deuxième nombre : " + op.getB());

                    double a = op.getA();
                    double b = op.getB();
                    String operateur = op.getOperateur();

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
                                sortie.writeObject(
                                        "Erreur : division par zéro.");
                                sortie.flush();
                                return;
                            }
                            resultat = a / b;
                            break;

                        default:
                            sortie.writeObject("Erreur : opérateur inconnu.");
                            sortie.flush();
                            return;
                    }

                    sortie.writeObject(Double.valueOf(resultat));
                    sortie.flush();

                    System.out.println("Résultat envoyé : " + resultat);
                }
            }

        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
