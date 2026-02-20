import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MultipleChoiceQuestion extends Question {
    private static final long serialVersionUID = 6961801526950881134L;
    protected ArrayList<String> choices;

    public MultipleChoiceQuestion(String p) {
        choices = new ArrayList<>();
        super(p);
    }

    public String returnType(){
        return "multiple choice";
    }

    public void setPrompt(String p) {
        this.prompt = p;
    }

    public void modify() {
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
            System.out.println("Which choice do you want to modify? Please give a number.");
            displayChoices();
            int choice = input.readInt();
            System.out.println("What do you want to change it too? ");
            modifyChoice(choice - 1, input.readString());
        }
    }

    public String addResponse() {
        String ans = "";
        for (int i = 1; i <= numOfResponses; i++){
            ans += (input.readAnswer(choices.size()) + "|");
        }
        return ans;
    }

    public void modifyChoice(int index, String choice) {
        choices.set(index, choice);
    }

    public void addChoice(String choice) {
        choices.add(choice);
    }

    public void displayChoices() {
        char letter = 'a';
        for (String choice : choices) {
            System.out.println(letter+ ") " + choice + "   ");
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
