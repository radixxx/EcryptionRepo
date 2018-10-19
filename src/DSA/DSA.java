package DSA;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.Signature;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.SignedObject;
import java.security.KeyPairGenerator;
import java.security.SignatureException;
import java.security.InvalidKeyException;
import java.security.NoSuchProviderException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class DSA
{

    private PrivateKey privateKey = null;//null
    private PublicKey  publicKey  = null;//null
    private final String ALGORITM_DSA = "DSA";
    private final String FILE_private = "private.key";
    private final String FILE_public  = "public.key" ;
    //KeyPairgenerator = 618;


    public DSA()
    {
        try {
            System.out.println("Enter your text: ");
            Scanner scanner = new Scanner(System.in);
            String myText;
            myText = scanner.nextLine();


            createKeys();
            saveKey(FILE_private, privateKey);
            saveKey(FILE_public , publicKey );

            privateKey = (PrivateKey) readKey(FILE_private);
            publicKey  = (PublicKey ) readKey(FILE_public );

            SignedObject signedObject = createSignedObject(myText, privateKey);

            // Проверка подписанного объекта
            boolean verified = verifySignedObject(signedObject, publicKey);
            System.out.println(myText + " \t" + verified);

            // Извлечение подписанного объекта
            String unsignedObject = (String) signedObject.getObject();

            System.out.println("Original object text: " + unsignedObject);

        } catch (ClassNotFoundException e) {
        } catch (IOException e) {
            System.err.println("Exception thrown during test: " + e.toString());
        } catch (InvalidKeyException e) {
            System.err.println(e.getMessage());
        } catch (SignatureException e) {
            System.err.println(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        } catch (NoSuchProviderException e) {
            System.err.println(e.getMessage());
        }
    }


    private SignedObject createSignedObject(final String message, PrivateKey key) throws InvalidKeyException,
            SignatureException, IOException, NoSuchAlgorithmException
    {
        Signature signature = Signature.getInstance(key.getAlgorithm());
        return new SignedObject(message, key, signature);
    }
    private boolean verifySignedObject(final SignedObject signedObject, PublicKey key)
            throws InvalidKeyException, SignatureException, NoSuchAlgorithmException
    {
        // Verify the signed object
        Signature signature = Signature.getInstance(key.getAlgorithm());
        return signedObject.verify(key, signature);
    }

   //Generate private and public key
    private void createKeys() throws NoSuchAlgorithmException, NoSuchProviderException
    {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITM_DSA);//SUN
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");//SUN
        keyPairGenerator.initialize(1024,random);//1024  //2048
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        privateKey = keyPair.getPrivate();
        publicKey  = keyPair.getPublic();
    }

    private void saveKey(final String filePath, final Object key)
            throws FileNotFoundException, IOException
    {
        if (key != null){
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(key);
            oos.close();
            fos.close();
        }
    }

    private Object readKey(final String filePath)
            throws FileNotFoundException, IOException, ClassNotFoundException
    {
        FileInputStream fis = new FileInputStream(filePath);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = ois.readObject();
        return object;
    }
    public static void main(String[] args)
    {
        new DSA();

        System.exit(0);
    }
}










/*создаются ключи PrivateKey, PublicKey;
ключи сохраняются в файлы;
ключи читаются из файлов;
создается подписанное объект/сообщение;
выполняется проверка подписанного сообщения;
извлекается исходный текст сообщения.*/