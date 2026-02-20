import java.util.HashMap;
import java.util.Map;

public class MatchingQuestion extends Question {
    private static final long serialVersionUID = 6961801526950881134L;
    private final HashMap<String,String> choices;

    public MatchingQuestion(String prompt) {
        choices = new HashMap<>();
        super(prompt);
    }

    public String returnType(){
        return "matching";
    }

    public void setPrompt(String prompt){
        this.prompt = prompt;
    }

    public void modify(){
        System.out.println("Prompt: "+ prompt);
        //System.out.println();
        printer.modifyPrompt();
        String ans = input.readString();
        if (ans.trim().equalsIgnoreCase("yes") || ans.trim().equalsIgnoreCase("y")) {
            System.out.println("Enter new prompt: ");
            setPrompt(input.readString());
        }
        System.out.println("Do you wish to modify choices? ");
        ans = input.readString();
        if (ans.trim().equalsIgnoreCase("yes") || ans.trim().equalsIgnoreCase("y")) {
            System.out.println("Which value do you want to modify? Please give the actual name of the value you wish to change. ");
            displayChoices();
            modifyChoice(input.readString());
        }
    }

    public String addResponse(){
        String ans = "";
        String eachAns = "";
        for (int i = 1; i <= numOfResponses; i++){
            for (int k = 0; k < choices.size(); k++) {
                eachAns += (input.readMatches(choices.size()) + " ");
            }
            ans += (eachAns + "|");
            eachAns = "";
        }
        return ans;
    }

    public void modifyChoice(String val){
        System.out.println("What do you want to change the value to?");
        String newVal = input.readString();
        for (Map.Entry<String, String> entry : choices.entrySet()) {
            //check if trying to change key, must remove that key and create new one with that same value
            if (entry.getKey().equalsIgnoreCase(val)) {
                String v = entry.getValue();
                choices.remove(entry.getKey());
                choices.put(newVal, v);
            } else if (entry.getValue().equalsIgnoreCase(val)) {
                //check if trying to change a value, can just change value of key
                choices.put(entry.getKey(), newVal);
            }
        }
    }

    public void addChoice(String key, String value){
        choices.put(key, value);
    }

    public void displayChoices() {
        char letter = 'a';
        int num = 1;
        for (Map.Entry<String, String> entry : choices.entrySet() ) {
            System.out.printf("%c) %-15s %d) %s\n", letter, entry.getKey(), num, entry.getValue());
            num++;
            letter++;
        }
    }

    public void display() {
        System.out.println(prompt);
        displayChoices();
    }

    public String take(){
        display();
        return addResponse();
    }
}
