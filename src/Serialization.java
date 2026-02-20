import java.io.*;

public class Serialization implements Serializable{
    private static final long serialVersionUID = 6961801526950881134L;

    //serialize survey to saved_surveys file
    public void serialize(Survey survey, String fileName, String type) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("saved_" + type + "s/" + fileName + ".ser"))) {
            oos.writeObject(survey);
            System.out.println("Successfully serialized and saved to " + fileName + ".ser");
        } catch (IOException e) {
            System.err.println("Serialization failed: " + e.getMessage());
            //e.printStackTrace();
        }
    }

    //serialize response to saved_responses file
    public void serializeResponse(Response response, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("saved_Responses/" + fileName + ".ser"))) {
            oos.writeObject(response);
            System.out.println("Response serialized and saved to " + fileName + ".ser");
        } catch (IOException e) {
            System.err.println("Response serialization failed: " + e.getMessage());
        }
    }

    //deserialize and return survey from saved_surveys file
    public Survey deserialize(String fileName, String type) {
        try (
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("saved_" + type + "s/"+ fileName + ".ser"))) {
                Survey loadedSurvey = (Survey) ois.readObject();
                System.out.println(fileName + " loaded from file.");
                //loadedSurvey.display();
                return loadedSurvey;
        } catch (IOException | ClassNotFoundException e) {
                System.err.println("Deserialization failed: " + e.getMessage());
                return null;
        }
    }

    //deserialize and return response from saved_responses file
    public Response deserializeResponse(String fileName) {
        try (
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("saved_Responses/" + fileName + ".ser"))) {
                Response loadedResponse = (Response) ois.readObject();
                System.out.println(fileName + " loaded from file.");
                //loadedResponse.display();
                return loadedResponse;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Response deserialization failed: " + e.getMessage());
            return null;
        }
    }
}
