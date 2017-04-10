package productcypher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Thilan K Bandara
 */
public class ProductCypher {

    private static final int blockLength = 5;
    private static final byte substitutionKey = -5;
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private static byte[] encordingSubstitution(byte[] initialArray) {
        while (initialArray.length % 5 != 0) {// adjusting the block size
            byte[] arr = new byte[initialArray.length + 1];
            for (int i = 0; i < initialArray.length; i++) {
                arr[i] = initialArray[i];
            }
            arr[initialArray.length] = 32;
            initialArray = arr;
        }

        for (int i = 0; i < initialArray.length; i++) {//substituting each letter
            initialArray[i] += substitutionKey;
        }
//        System.out.println(
//                "SUbstituted= " + Arrays.toString(initialArray));
        return initialArray;// returning the substituted Array
    }

    private static byte[] encordingPermutation(byte[] initialArray) {
        byte a, b, c, d, e;
        ArrayList<Byte> array = new ArrayList<Byte>();
        Stack<Byte> stack = new Stack<Byte>();

        for (int i = initialArray.length - 1; i >= 0; i--) {
            stack.add(initialArray[i]);
        }
        while (stack.size() != 0) {
            a = stack.pop();
            b = stack.pop();
            c = stack.pop();
            d = stack.pop();
            e = stack.pop();
            /////////////////// permutation pattern a,b,c,d,e into d,a,b,e,c
            array.add(d);
            array.add(a);
            array.add(b);
            array.add(e);
            array.add(c);
        }
        byte[] returnArray = new byte[initialArray.length];
        for (int i = 0; i < initialArray.length; i++) {
            returnArray[i] = array.get(i);
        }

        return returnArray;
    }

    private static byte[] decordingPermutation(byte[] initialArray) {

        byte a, b, c, d, e;
        ArrayList<Byte> array = new ArrayList<Byte>();
        Stack<Byte> stack = new Stack<Byte>();

        for (int i = initialArray.length - 1; i >= 0; i--) {
            stack.add(initialArray[i]);
        }
        while (stack.size() != 0) {
            d = stack.pop();
            a = stack.pop();
            b = stack.pop();
            e = stack.pop();
            c = stack.pop();
            /////////////////// permutation pattern d,a,b,e,c into a,b,c,d,e
            array.add(a);
            array.add(b);
            array.add(c);
            array.add(d);
            array.add(e);
        }
        byte[] returnArray = new byte[initialArray.length];
        for (int i = 0; i < initialArray.length; i++) {
            returnArray[i] = array.get(i);
        }

        return returnArray;
    }

    private static byte[] decordingSubstitution(byte[] initialArray) {
        /////////////////
       

        for (int i = 0; i < initialArray.length; i++) {
            initialArray[i] -= substitutionKey;
        }
        return initialArray;// returning the substitution decorded array
    }

    private static byte[] dec(byte[] array) {// decoding function
        return decordingPermutation(decordingSubstitution(array));
    }

    private static byte[] enc(byte[] array) {// encoding function
        return encordingPermutation(encordingSubstitution(array));
    }

    public static void main(String[] args) {

        int operation = 0;

        System.out.println("Enter your file path: ");
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.nextLine();// reading the filepath

        String message = "";
        try {
            FileReader reader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                message += line;
            }
            reader.close();
            System.out.println("For Encription: Enter 1");
            System.out.println("For Decription: Enter 2");
            String ops = scanner.nextLine();
            operation = Integer.parseInt(ops);
            ///////////////////////////
            byte[] bytes = message.getBytes(UTF_8);
            if (operation == 1) {
                System.out.println("Encripted Message=" + new String(enc(bytes), UTF_8));
            }
            if (operation == 2) {
                System.out.println(
                        "Decripted Message=" + new String(dec(bytes), UTF_8));
            }
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("Invalid Filepath!!!");
        }

    }

}
