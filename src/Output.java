import java.io.Serializable;

public class Output implements Serializable {
    private static final long serialVersionUID = 6961801526950881134L;

    public Output() {}

    public void printMsg(String msg){
        System.out.println(msg);
    }

    public void startUp() {
        System.out.println("Main Menu:");
        System.out.println("1. Go to Survey Menu");
        System.out.println("2. Go to Test Menu");
        System.out.println("3. Exit");
    }

    public void surveyMenu() {
        System.out.println("Survey Menu:");
        System.out.println("1. Create a new Survey");
        System.out.println("2. Display the current Survey");
        System.out.println("3. Load an existing Survey");
        System.out.println("4. Save the current Survey");
        System.out.println("5. Take an existing Survey");
        System.out.println("6. Modify the current Survey");
        System.out.println("7. Tabulate a survey");
        System.out.println("8. Go Back to Previous Menu");
    }

    public void testMenu() {
        System.out.println("Test Menu:");
        System.out.println("1. Create a new Test");
        System.out.println("2. Display current Test without correct answers");
        System.out.println("3. Display current Test with correct answers ");
        System.out.println("4. Load an existing Test");
        System.out.println("5. Save the current Test");
        System.out.println("6. Take an existing Test");
        System.out.println("7. Modify the current Test");
        System.out.println("8. Tabulate a Test");
        System.out.println("9. Grade a Test");
        System.out.println("10. Go Back to Previous Menu");
    }

    public void addQuestionPrompt(){
        System.out.println("1) Add a new T/F question");
        System.out.println("2) Add a new multiple-choice question");
        System.out.println("3) Add a new short answer question");
        System.out.println("4) Add a new essay question");
        System.out.println("5) Add a new date question");
        System.out.println("6) Add a new matching question");
        System.out.println("7) Return to previous menu");
    }

    public void toGrade(){
        System.out.println("Select an existing test to grade:");
    }

    public void gradeResponse(){
        System.out.println("Select an existing response set:");
    }

    public void toTake(String type) { System.out.println("Which " + type + " would you like to take?"); }

    public void toTabulate(String type) { System.out.println("Which " + type + " would you like to tabulate?"); }

    public void correctAnswer() { System.out.println("What is the correct answer?:"); }

    public void noResponseQ() { System.out.println("This question has no responses."); }

    public void noResponseS() { System.out.println("This survey/test has no responses."); }

    public void invalidChoice(){
        System.out.println("Please enter a valid choice.");
    }

    public void enterPrompt(String type){
        System.out.println("Enter the prompt for your " + type + " question: ");
    }

    public void numOfResponses(){ System.out.println("How many responses will this question have?"); }

    public void modifyPrompt(){ System.out.println("Do you wish to modify the prompt?"); }

    public void modifySurveyPrompt(){
        System.out.println("How do you wish to modify the survey?");
        System.out.println("1) Add a new question");
        System.out.println("2) Delete a question");
        System.out.println("3) Modify a question");
        System.out.println("4) Return to previous menu");
    }
}
