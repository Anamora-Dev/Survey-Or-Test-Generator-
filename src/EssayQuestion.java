import java.util.ArrayList;

public class EssayQuestion extends Question{
    private static final long serialVersionUID = 6961801526950881134L;

    public EssayQuestion(String prompt){
        super(prompt);
    }

    public void setPrompt(String p){
        this.prompt = p;
    }

    public String returnType(){
        return "essay";
    }

    public String addResponse(){
        String ans = "";
        for (int i = 1; i <= numOfResponses; i++){
            ans += (input.readString() + "|");
        }
        Response r = new Response();
        r.addResponse(ans);
        userResponses.add(r);
        return ans;
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
    }

    public void display(){
        System.out.println(prompt);
    }

    @Override
    public void tabulate(ArrayList<String> response){
        System.out.println("Tabulation: ");
        boolean empty = false;

        for (String r : response) {
            if (r.isEmpty()) {
                empty = true;
            } else {
                System.out.println(r);
            }
        }

        if (empty) {
            printer.noResponseQ();
        }
    }

    public String take(){
        display();
        return addResponse();
    }
}
