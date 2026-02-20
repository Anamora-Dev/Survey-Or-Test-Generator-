import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Survey implements Serializable {
    private static final long serialVersionUID = 6961801526950881134L;
    protected ArrayList<Question> Questions;
    protected ArrayList<Response> totalUserResponses;
    Serialization s = new Serialization();
    protected Output printer;
    String surveyName = null;

    public Survey(){
        totalUserResponses = new ArrayList<>();
        Questions = new ArrayList<>();
        printer = new Output();
    }

    public void create(Input inputHandler){
        System.out.println();
        printer.addQuestionPrompt();
        int input = inputHandler.readInt();
        while (input != 7) {
            if (input == 1) {
                //true or false
                printer.enterPrompt("True/False");
                TrueOrFalseQuestion q = new TrueOrFalseQuestion(inputHandler.readString());
                printer.numOfResponses();
                q.setNumOfResponses(inputHandler.readInt());
                addQuestion(q, inputHandler);
                //q.display();
            } else if (input == 2) {
                //multiple choice
                printer.enterPrompt("multiple-choice");
                MultipleChoiceQuestion q = new MultipleChoiceQuestion(inputHandler.readString());
                printer.numOfResponses();
                q.setNumOfResponses(inputHandler.readInt());
                printer.printMsg("Enter the number of choices for your multiple choice question");
                int numOfChoices = inputHandler.readInt();
                for  (int i = 1; i <= numOfChoices; i++) {
                    printer.printMsg("Enter choice #" + i );
                    q.addChoice(inputHandler.readString());
                }
                addQuestion(q, inputHandler);
            } else if(input == 3) {
                // short answer
                printer.enterPrompt("short answer");
                ShortAnswerQuestion q = new ShortAnswerQuestion(inputHandler.readString());
                printer.numOfResponses();
                q.setNumOfResponses(inputHandler.readInt());
                addQuestion(q, inputHandler);
            } else if(input == 4) {
                //essay
                printer.enterPrompt("essay");
                EssayQuestion q = new EssayQuestion(inputHandler.readString());
                printer.numOfResponses();
                q.setNumOfResponses(inputHandler.readInt());
                addQuestion(q, inputHandler);
            }  else if(input == 5) {
                //date
                printer.enterPrompt("date");
                DateQuestion q = new DateQuestion(inputHandler.readString());
                printer.numOfResponses();
                q.setNumOfResponses(inputHandler.readInt());
                addQuestion(q, inputHandler);
            }  else if(input == 6) {
                //matching
                printer.enterPrompt("matching");
                MatchingQuestion q = new MatchingQuestion(inputHandler.readString());
                printer.printMsg("How many matches will there be?");
                int matches = inputHandler.readInt();
                q.setNumOfResponses(matches);
                for  (int i = 1; i <= matches; i++) {
                    printer.printMsg("Left hand side of Match #" + i );
                    String key = inputHandler.readString();
                    printer.printMsg("Right hand side of Match #" + i );
                    String value = inputHandler.readString();
                    q.addChoice(key, value);
                }
                addQuestion(q, inputHandler);
            } else {
                printer.invalidChoice();
            }
            printer.addQuestionPrompt();
            input = inputHandler.readInt();
        }
    }

    public void addQuestion(Question q, Input inputHandler){
        Questions.add(q);
    }

    public void display(){
        int qNum = 1;
        for (Question question : Questions) {
            System.out.print(qNum + ". ");
            question.display();
            System.out.println();
            qNum++;
        }
    }

    public void tabulate(){
        //need to display each question and then all of their responses
        //go through all the responses for each question and print them?

        ArrayList<Response> responses = new ArrayList<>();
        //this loop will go through all the saved responses for this survey or test and save them to responses
        for (int num = 1; num < 50; num++){
            String filename = getSurveyName() + " - Response " + num;
            File file = new File("saved_Responses/" + filename + ".ser");
            if (file.exists()){
                //System.out.println("Response " + num);
                Response r = s.deserializeResponse(filename);
                responses.add(r);
            }
        }
        //if the survey/test has no responses
        if (responses.isEmpty()){
            printer.noResponseS();
        } else {
            System.out.println();
            //For each question, go through each response, gather into one list all the responses for that question, and let the question decide how to tabulate
            for (int q = 0; q < Questions.size(); q++) {
                ArrayList<String> response = new ArrayList<>();
                for (Response r : responses) {
                    ArrayList<String> placeholder = r.getResponse();
                    if (!placeholder.isEmpty()) {
                        response.add(placeholder.get(q));
                    }
                }
                Questions.get(q).display();
                System.out.println();
                if (!response.isEmpty()) {
                    Questions.get(q).tabulate(response);
                } else {
                    printer.noResponseQ();
                }
                System.out.println();
            }
        }
    }

    public void setName(String name){
        surveyName = name;
    }

    public String getSurveyName(){
        return surveyName;
    }

    public void save(Survey survey, String fileName, String type){
        Serialization serialization = new Serialization();
        serialization.serialize(survey, fileName, type);
    }

    public void modify(Input inputHandler){
        printer.modifySurveyPrompt();
        int input = inputHandler.readInt();
        while (input != 4) {
            if (input == 1) {
                create(inputHandler);
            } else if (input == 2) {
                display();
                printer.printMsg("What question would you like to delete?");
                int question = inputHandler.readQuestionNum(Questions);
                Questions.remove(question - 1);
            } else if (input == 3) {
                display();
                printer.printMsg("What question would you like to modify?");
                int question = inputHandler.readQuestionNum(Questions) - 1;
                Question q = Questions.get(question);
                q.modify();
            } else {
                printer.invalidChoice();
            }
            printer.modifySurveyPrompt();
            input = inputHandler.readInt();
        }
    }

    public void take(){
        ArrayList<String> response = new ArrayList<>();
        for (Question q : Questions) {
            String resp = q.take();
            response.add(resp);
        }
        Response response1 = new Response();
        response1.setResponse(response);
        saveResponse(response1); //saves total survey response to saved_Surveys or saved_Tests file
        totalUserResponses.add(response1);
        //save the user response
    }

    public void saveResponse(Response r){
        //keep track of which response is next
        String filename = generateResponseName();
        s.serializeResponse(r, filename);
    }

    public Response loadResponse(Integer responseNum){
        return totalUserResponses.get(responseNum);
    }

    public void displayResponses(){
        for (Response r : totalUserResponses){
            r.display();
            System.out.println();
        }
    }

    private String generateResponseName(){
        int nextNum = 1;
        for (int num = 1; num < 50; num++){
            File file = new File("saved_Responses/"+ getSurveyName() + " - Response " + num + ".ser");
            if (file.exists()){
                //System.out.println("Response " + num);
                nextNum = num+1;
            }
        }
        return getSurveyName() + " - Response " + nextNum;
    }

}
