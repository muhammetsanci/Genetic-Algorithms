import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        String password = setPassword();
        int numOfChr = setNumOfChr();
        getModel(password, numOfChr);

        ArrayList<String[]> oldGen = setFirstGen(numOfChr, password);
        int[] fitValues = fitControl(oldGen, password);

        int genNumber = 1;
        boolean flag = true;

        while (flag) {
            System.out.println("Generation: " + genNumber);

            ArrayList<String[]> newGen = elitism(oldGen, fitValues);

            crossover(newGen, password, numOfChr);

            mutation(newGen, password);

            fitValues = fitControl(newGen, password);

            flag = exitControl(newGen, fitValues, password);

            oldGen = newGen;

            genNumber++;
        }
    }

    //METHOD "setFirstGen": Generates the very first generation by shuffling.
    public static ArrayList<String[]> setFirstGen(int numOfChr, String password) {
        ArrayList<String[]> firstGen = new ArrayList<>();

        for (int i = 0; i < numOfChr; i++) {
            firstGen.add(shuffle(password));
        }

        return firstGen;
    }

    //METHOD "shuffle": Shuffles the characters in a given string.
    public static String[] shuffle(String password) {
        String[] chars = password.split("");

        Random rnd = ThreadLocalRandom.current();
        for (int i = chars.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            String a = chars[index];
            chars[index] = chars[i];
            chars[i] = a;
        }

        return chars;
    }

    //METHOD "elitism": Controls a generation's chromosomes and takes the best ones to the new generation:
    public static ArrayList<String[]> elitism(ArrayList<String[]> oldGen, int[] fitValues) {
        ArrayList<String[]> newGen = new ArrayList<>();

        for (int i = 0; i < fitValues.length / 5; i++) {
            int maxIndex = findMax(fitValues);
            newGen.add(oldGen.get(maxIndex));
            fitValues[maxIndex] = 0;
        }

        return newGen;
    }

    //METHOD "fitness": Controls a chromosome's genes and get a fitness value as similarity to the password.
    public static int fitness(String password, String[] chars) {
        int fit = 0;
        String[] pChars = password.split("");

        for (int i = 0; i < password.length(); i++) {
            if (chars[i].equals(pChars[i])) {
                fit++;
            }
        }

        return fit;
    }

    //METHOD "crossover": Makes crossover between chromosomes that selected by elitism.
    public static void crossover(ArrayList<String[]> newGen, String password, int numOfChr) {
        int size = newGen.size();

        for (int i = 0; i < (numOfChr / 5) * 4; i++) {
            Random rnd = ThreadLocalRandom.current();
            String[] chr1 = newGen.get(rnd.nextInt(size));
            String[] chr2 = newGen.get(rnd.nextInt(size));

            String[] newChr = new String[password.length()];
            String charPool = password;

            int n = rnd.nextInt(password.length());
            for (int j = 0; j < n; j++) {
                newChr[j] = chr1[j];
                charPool = charPool.replaceFirst(chr1[j], "*");
            }

            for (String a : chr2) {
                if (charPool.contains(a)) {
                    newChr[n] = a;
                    n++;
                    charPool = charPool.replaceFirst(a, "*");
                }
            }

            newGen.add(newChr);
        }
    }

    //METHOD "mutation": Get chromosomes in a generation and apply mutation on them by changing genes locations.
    public static void mutation(ArrayList<String[]> newGen, String password) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = 0; i < newGen.size(); i++) {
            int gene1 = rnd.nextInt(password.length());
            int gene2 = rnd.nextInt(password.length());

            String temp = newGen.get(i)[gene1];
            newGen.get(i)[gene1] = newGen.get(i)[gene2];
            newGen.get(i)[gene2] = temp;
        }
    }

    //METHOD "fitControl": Makes fitness control on a generation.
    public static int[] fitControl(ArrayList<String[]> newGen, String password) {
        int[] fitValues = new int[newGen.size()];

        for (int i = 0; i < newGen.size(); i++) {
            fitValues[i] = fitness(password, newGen.get(i));
        }

        return fitValues;
    }

    //METHOD "exitControl": If password cracked at current generation, stops the model.
    public static boolean exitControl(ArrayList<String[]> newGen, int[] fitValues, String password) {
        for (int i = 0; i < fitValues.length; i++) {
            if (fitValues[i] == password.length()) {
                System.out.println("┏━━━━━━━━━━━━━━━━━━━┓");
                System.out.println("┃ PASSWORD CRACKED! ┃");
                System.out.println("┣━━━━━━━━━━━━━━━━━━━┫");
                System.out.print("┃ ");

                for (String a : newGen.get(i)) {
                    System.out.print(a);
                }

                System.out.print(new String(new char[18 - password.length()]).replace("\0", " "));
                System.out.println("┃");
                System.out.println("┗━━━━━━━━━━━━━━━━━━━┛");

                return false;
            }
        }

        int maxIndex = findMax(fitValues);

        System.out.print("Closest try: ");
        for (String a : newGen.get(maxIndex)) {
            System.out.print(a);
        }
        System.out.println();
        System.out.println();
        return true;
    }

    //METHOD "findMax": Finds maximum value in a list and returns its index value.
    public static int findMax(int[] values) {
        int maxValue = 0;
        int maxIndex = 0;

        for (int j = 0; j < values.length; j++) {
            if (values[j] > maxValue) {
                maxValue = values[j];
                maxIndex = j;
            }
        }

        return maxIndex;
    }

    //METHOD "setPassword": Gets password for the model.
    public static String setPassword() {
        Scanner reader = new Scanner(System.in);
        System.out.print("Type genetic password: ");

        return reader.nextLine();
    }

    //METHOD "setNumOfChr": Gets number of chromosomes for the model.
    public static int setNumOfChr() {
        Scanner reader = new Scanner(System.in);
        System.out.print("Set number of chromosomes: ");

        return reader.nextInt();
    }

    //METHOD "getModel": Prints details of the model.
    public static void getModel(String password, int numOfChr) {
        System.out.println("┏━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃    Password ┃ " + password + new String(new char[18 - password.length()]).replace("\0", " ") + "┃");
        System.out.println("┣━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━┫");
        System.out.println("┃      Length ┃ " + password.length() + new String(new char[18 - Integer.toString(password.length()).length()]).replace("\0", " ") + "┃");
        System.out.println("┣━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━┫");
        System.out.println("┃ Chromosomes ┃ " + numOfChr + new String(new char[18 - Integer.toString(numOfChr).length()]).replace("\0", " ") + "┃");
        System.out.println("┗━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━┛");
    }
}