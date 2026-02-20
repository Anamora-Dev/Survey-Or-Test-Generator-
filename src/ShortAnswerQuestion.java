import java.util.ArrayList;
import java.util.HashMap;

public class ShortAnswerQuestion extends EssayQuestion {
    private static final long serialVersionUID = 6961801526950881134L;

    public ShortAnswerQuestion(String prompt) {
        super(prompt);
    }

    @Override
    public String returnType(){
        return "short answer";
    }

    @Override
    public String addResponse(){
        String finalResponse = "";
        for (int i = 1; i <= numOfResponses; i++){
            String ans = checkLength();
            finalResponse += (ans + "|");
        }
        return finalResponse;
    }

    @Override
    public void tabulate(ArrayList<String> responses) {
        toTabulate(responses);
    }

    private String checkLength(){
        String ans = input.readString();
        while(ans.length() > 20){
            System.out.println("Response is too long for a short answer question. Please use less than 20 characters.");
            ans  = input.readString();
        }
        return ans;
    }
}
