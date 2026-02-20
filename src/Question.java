import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Question implements Serializable {
    private static final long serialVersionUID = 6961801526950881134L;
    //add id?
    private final Serialization serialization =  new Serialization();
    protected Input input = new Input();
    protected Output printer = new Output();
    protected String prompt;
    protected ArrayList<Response> userResponses = new ArrayList<>();
    protected int numOfResponses;

    public Question(String prompt) {
        numOfResponses = 1;
        this.prompt = prompt;
    }

    public abstract String addResponse();

    public abstract void setPrompt(String prompt);

    public void setNumOfResponses(int num) {
        this.numOfResponses = num;
    }

    public abstract void modify();

    public abstract String take();

    public abstract void display();

    public abstract String returnType();

    public void tabulate(ArrayList<String> responses){
        toTabulate(responses);
    }

    protected void toTabulate(ArrayList<String> responses){
        System.out.println("Responses: ");
        //Show the responses without any formatting or tabulation
        boolean empty = false;

        for (String r : responses) {
            if (r.isEmpty()) {
                empty = true;
            } else {
                System.out.println(r);
            }
        }

        if (empty) {
            printer.noResponseQ();
        } else {
            System.out.println();
            System.out.println("Tabulation: ");
            HashMap<String, Integer> response = new HashMap<>();
            for (String r : responses) {
                //if hashmap has that response already, add to counter, else put in hashMap
                if (response.containsKey(r)) {
                    int value = response.get(r);
                    response.replace(r, value, value + 1);
                } else {
                    response.put(r, 1);
                }

            }

            //prints tabulation format
            for (Map.Entry<String, Integer> entry : response.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }
}
