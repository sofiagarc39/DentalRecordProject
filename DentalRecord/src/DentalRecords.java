import java.util.Scanner;

/**
 * Dental Records
 *
 * @author Student
 * @see DentalRecords
 */
public class DentalRecords {
    /** Reads keyboard input for the program. */
    private static final Scanner keyboard = new Scanner(System.in);
    /** Maximum number of family members allowed.
     */
    public static final int MAX_MEMBERS = 6;
    /** Minimum number of family members allowed.
     */
    public static final int MIN_MEMBERS = 1;
    /** Number of tooth layers for each family member.
     */
    public static final int NUMBER_OF_LAYERS = 2;
    /** Array index representing the upper tooth layer.
     */
    public static final int UPPER_LAYER = 0;
    /** Array index representing the lower tooth layer.
     */
    public static final int LOWER_LAYER = 1;
    /** Maximum number of teeth allowed in each layer.
     */
    public static final int MAX_TEETH = 8;
    /** Stores the names of the family members.
     */
    private static String[] familyNames;
    /** Stores the tooth types for each family member and layer.
     */
    private static char[][][] familyTeeth;
    /** Stores the number of teeth entered for each family member and layer.
     */
    private static int[][] toothLengths;
    /**
     * Runs the dental records program and processes menu selections.
     * @param args command-line arguments
     * @see DentalRecords
     */
    public static void main(String[] args) {
        String menuOption;

        System.out.println("Welcome to the Floridian Tooth Records");
        System.out.println("--------------------------------------");

        getFamilyInfo();

        do {
            System.out.println("(P)rint, (E)xtract, (R)oot, e(X)it: ");
            menuOption = keyboard.nextLine().trim().toUpperCase();

            switch (menuOption) {
                case "P" -> printFamilyRecord();
                case "E" -> extractTooth();
                case "R" -> printRootCanalIndices();
                case "X" -> System.out.println("Exiting the Floridian Tooth Records :-)");
                default -> System.out.println("Invalid menu option, try again");
            }

        } while (!menuOption.equals("X"));
    }

    /**
     * Reads and validates the family's names and teeth.
     * @see DentalRecords
     */
    public static void getFamilyInfo() {
        int familySize;
        int memberIndex;
        int layer;
        String toothString;
        String[] layerNames;
        boolean validInput;

        layerNames = new String[] {"uppers", "lowers"};

        System.out.print("Please enter number of people in the family: ");
        familySize = keyboard.nextInt();

        while (familySize < MIN_MEMBERS || familySize > MAX_MEMBERS) {
            System.out.print("Invalid number of people, try again: ");
            familySize = keyboard.nextInt();
        }

        keyboard.nextLine();

        familyNames = new String[familySize];
        familyTeeth = new char[familySize][NUMBER_OF_LAYERS][MAX_TEETH];
        toothLengths = new int[familySize][NUMBER_OF_LAYERS];

        for (memberIndex = 0;
             memberIndex < familySize; memberIndex++) {

            System.out.print("Please enter the name for family member " + (memberIndex + 1) + ": ");
            familyNames[memberIndex] = keyboard.nextLine().trim();

            for (layer = 0; layer < NUMBER_OF_LAYERS; layer++) {

                validInput = false;

                do {
                    System.out.print("Please enter the " + layerNames[layer] + " for " + familyNames[memberIndex] + ": ");

                    toothString = keyboard.nextLine()
                            .trim().toUpperCase();

                    if (toothString.length() > MAX_TEETH) {
                        System.out.println("Too many teeth, try again.");
                    }
                    else if (toothString.length() == 0) {
                        System.out.println("Please enter at least one tooth.");
                    }
                    else if (!validToothTypes(toothString)) {
                        System.out.println("Invalid teeth types, try again.");
                    }
                    else {
                        validInput = true;
                    }

                } while (!validInput);

                familyTeeth[memberIndex][layer] =
                        toothString.toCharArray();

                toothLengths[memberIndex][layer] =
                        toothString.length();
            }
        }
    }

    /**
     * Checks whether every character is I, B, or M.
     * @param toothString string containing the tooth types to check
     * @return true if every character is valid, otherwise false
     * @see DentalRecords
     */
    public static boolean validToothTypes(String toothString) {
        int toothIndex;
        char toothType;

        for (toothIndex = 0; toothIndex < toothString.length(); toothIndex++) {

            toothType = toothString.charAt(toothIndex);

            if (toothType != 'I' && toothType != 'B' && toothType != 'M') {
                return false;
            }
        }

        return true;
    }

    /**
     * Prints each family member's teeth and their positions.
     * @see DentalRecords
     */
    public static void printFamilyRecord() {
        int memberIndex;
        int layer;
        int toothIndex;

        for (memberIndex = 0; memberIndex < familyNames.length; memberIndex++) {

            System.out.println(familyNames[memberIndex]);

            for (layer = 0; layer < NUMBER_OF_LAYERS; layer++) {

                if (layer == UPPER_LAYER) {
                    System.out.print("  Uppers: ");
                }
                else {
                    System.out.print("  Lowers: ");
                }

                for (toothIndex = 0; toothIndex < toothLengths[memberIndex][layer];toothIndex++) {

                    System.out.print((toothIndex + 1) + ":"
                            + familyTeeth[memberIndex][layer][toothIndex] + " ");
                }
                System.out.println();
            }
        }
    }

    /**
     * Finds a family member, then marks a selected tooth missing.
     * @see DentalRecords
     */
    public static void extractTooth() {
        String memberName;
        String layerInput;
        int memberIndex;
        int layer;
        int toothNumber;
        int index;

        System.out.print("Which family member: ");
        memberName = keyboard.nextLine().trim();
        memberIndex = -1;

        while (memberIndex == -1) {
            for (index = 0; index < familyNames.length; index++) {
                if (familyNames[index].equalsIgnoreCase(memberName)) {
                    memberIndex = index;
                }
            }

            if (memberIndex == -1) {
                System.out.print("Invalid family member, try again: ");
                memberName = keyboard.nextLine().trim();
            }
        }

        System.out.print("Which tooth layer (U)pper or (L)ower: ");
        layerInput = keyboard.nextLine().trim().toUpperCase();

        while (!layerInput.equals("U") && !layerInput.equals("L")) {
            System.out.print("Invalid layer, try again: ");
            layerInput = keyboard.nextLine().trim().toUpperCase();
        }

        if (layerInput.equals("U")) {
            layer = UPPER_LAYER;
        }
        else {
            layer = LOWER_LAYER;
        }

        System.out.print("Which tooth number: ");
        toothNumber = keyboard.nextInt();
        keyboard.nextLine();

        while (toothNumber < 1 || toothNumber > toothLengths[memberIndex][layer] ||
                familyTeeth[memberIndex][layer][toothNumber - 1] == 'M') {

            if (toothNumber < 1 || toothNumber > toothLengths[memberIndex][layer]) {
                System.out.print("Invalid tooth number, try again: ");
            }
            else {
                System.out.print("Missing tooth, try again: ");
            }

            toothNumber = keyboard.nextInt();
            keyboard.nextLine();
        }

        familyTeeth[memberIndex][layer][toothNumber - 1] = 'M';
    }

    /**
     * Counts the family's tooth types and calculates the roots
     * of I*x^2 + B*x - M = 0.
     * @see DentalRecords
     */
    public static void printRootCanalIndices() {
        int memberIndex;
        int layer;
        int toothIndex;
        int incisors;
        int bicuspids;
        int missing;
        double discriminant;
        double firstRoot;
        double secondRoot;

        incisors = 0;
        bicuspids = 0;
        missing = 0;

        for (memberIndex = 0; memberIndex < familyNames.length; memberIndex++) {

            for (layer = 0; layer < NUMBER_OF_LAYERS; layer++) {

                for (toothIndex = 0; toothIndex < toothLengths[memberIndex][layer]; toothIndex++) {

                    if (familyTeeth[memberIndex][layer][toothIndex] == 'I') {
                        incisors++;
                    }
                    else if (familyTeeth[memberIndex][layer][toothIndex] == 'B') {
                        bicuspids++;
                    }
                    else if (familyTeeth[memberIndex][layer][toothIndex] == 'M') {
                        missing++;
                    }
                }
            }
        }

        if (incisors > 0) {
            discriminant = bicuspids * bicuspids + 4.0 * incisors * missing;

            firstRoot = (-bicuspids + Math.sqrt(discriminant)) / (2.0 * incisors);

            secondRoot = (-bicuspids - Math.sqrt(discriminant)) / (2.0 * incisors);

            System.out.printf("One root canal at %.2f%n", firstRoot);
            System.out.printf("Another root canal at %.2f%n", secondRoot);
        }
        else if (bicuspids > 0) {
            firstRoot = (double) missing / bicuspids;

            System.out.printf("One root canal at %.2f%n", firstRoot);
        }
        else {
            System.out.println("The equation has no unique root.");
        }
    }
}