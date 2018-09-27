import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //Enter with Scanner your text-msg
        System.out.println("Enter your text: ");
        Scanner scanner = new Scanner(System.in);
        String myText;
        myText = scanner.nextLine();

        //Exeption block
        try {
            String k = "7qw8sd4h";
            System.out.println("Text: " + myText);
            byte[] enc = DES.encrypt(myText.getBytes(),k.getBytes());
            System.out.println("Text cryptet DES: " + new String(enc));

            //Decryption block
            byte[] dec = DES.decrypt(enc, k.getBytes());
            System.out.println("Text decryptet DES: " + new String(dec));

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
