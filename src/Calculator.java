import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.JOptionPane;
import java.util.ArrayList;

public class Calculator {

    private static  final Logger logger = LogManager.getLogger();

    ArrayList<Double> stack = new ArrayList<Double>();
    String userInput;
    double result;

    public void enter() {
        JOptionPane.showMessageDialog(null, "Bienvenue dans votre calculette inversée !");
        letUserChoose();
    }

    public void letUserChoose() {
        do {
            String message = "";
            if (!stack.isEmpty()) {
                message += "Voici votre liste d'opérandes :\r\n";
                for (Double s : stack) {
                    message += " _ " + s;
                }
                message += "\r\n\r\n";
            }
            message += "Saisissez un opérande ou l'une des opérations suivantes :"
                    + "\r\n+ : addition"
                    + "\r\n- : soustraction"
                    + "\r\n* : multiplication"
                    + "\r\n/ : division"
                    + "\r\nv : vider la pile"
                    + "\r\ns : supprimer le dernier opérande"
                    + "\r\ni : inverser les deux derniers opérandes"
                    + "\r\nq : quitter";

            userInput = JOptionPane.showInputDialog(message);
            try {
                processUserInput(userInput);
            } catch (NumberFormatException e) {
                logger.error("L'utilisateur a saisi un caractère non proposé", e);
                JOptionPane.showMessageDialog(null, "Merci de saisir un nombre ou l'une des opérations autorisées.");
                letUserChoose();
            }
        } while (!userInput.equals("q"));
    }

    public void processUserInput(String userInput) {
        switch (userInput) {
            case "+":
            case "*":
            case "/":
            case "-":
                if (stack.size() >= 2) {
                    calculate(userInput);
                } else {
                    JOptionPane.showMessageDialog(null, "Votre pile doit avoir au minimum deux valeurs pour effectuer une opération mathématique, veuillez choisir un opérande.");
                }
                break;
            case "v":
                if (stack.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Votre pile est vide, veuillez choisir un opérande.");
                } else {
                    stack.clear();
                }
                break;
            case "s":
                if (stack.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Votre pile est vide, impossible de supprimer une valeur. Veuillez choisir un opérande.");
                } else {
                    stack.remove(stack.size() - 1);
                }
                break;
            case "i":
                if (stack.size() < 2) {
                    JOptionPane.showMessageDialog(null, "Votre pile ne comporte qu'une seule valeur. Veuillez choisir un autre opérande pour pouvoir les inverser.");
                } else {
                    reverse();
                }
                break;
            case "q":
                JOptionPane.showMessageDialog(null, "A bientôt !");
                break;
            default:
                double userInputConvertedToDouble = Double.parseDouble(userInput);
                stack.add(userInputConvertedToDouble);
        }
    }

    public void calculate(String operation) {
        switch (operation) {
            case "+":
                result = (stack.get(stack.size() - 1)) + (stack.get(stack.size() - 2));
                break;
            case "-":
                result = (stack.get(stack.size() - 1)) + (stack.get(stack.size() - 2));
                break;
            case "*":
                result = (stack.get(stack.size() - 1)) * (stack.get(stack.size() - 2));
                break;
            case "/":
                if ((stack.get(stack.size() - 2)) != 0) {
                    if ((stack.get(stack.size() - 1)) != 0) {
                        String isReverseOperande = JOptionPane.showInputDialog("Vous ne pouvez pas diviser par 0. Souhaitez-vous inverser vos deux dernières valeurs pour effectuer la division ?");
                        switch (isReverseOperande) {
                            case "yes":
                                reverse();
                                calculate(operation);
                                break;
                            default:
                                letUserChoose();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Vous ne pouvez pas diviser 0 par 0, veuillez choisir un autre opérande.");
                        letUserChoose();
                    }
                }
                result = (stack.get(stack.size() - 1)) / (stack.get(stack.size() - 2));
                break;
            default:
        }
        stack.remove(stack.size() - 1);
        stack.remove(stack.size() - 1);
        stack.add(result);
    }

    public void reverse() {
        double lastValue = stack.get(stack.size() - 1);
        double penultimateValue = stack.get(stack.size() - 2);
        stack.remove(stack.size() - 1);
        stack.remove(stack.size() - 1);
        stack.add(lastValue);
        stack.add(penultimateValue);
    }
}
