package dev;

import java.io.*;

public class SerializableTest {

    public static Object testMe(Serializable in) {

        try {

            ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
            ObjectOutputStream outStream = new ObjectOutputStream(byteOutStream);

            outStream.writeObject(in);

            ByteArrayInputStream byteInStream =
                    new ByteArrayInputStream(byteOutStream.toByteArray());


            ObjectInputStream inStream = new ObjectInputStream(byteInStream);

            return inStream.readObject();

        } catch (OptionalDataException e) {

            throw new RuntimeException("Optional data found. " + e.getMessage());

        } catch (StreamCorruptedException e) {

            throw new RuntimeException("Serialized object got corrupted. " + e.getMessage());

        } catch (ClassNotFoundException e) {

            throw new RuntimeException("A class could not be found during deserialization. " + e.getMessage());

        } catch (NotSerializableException ex) {

            ex.printStackTrace();
            throw new IllegalArgumentException("Object is not serializable: " + ex.getMessage());

        } catch (IOException e) {

            throw new RuntimeException("IO operation failed during serialization. " + e.getMessage());
        }
    }

}