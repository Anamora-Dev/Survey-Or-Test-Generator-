import java.util.ArrayList;

public class Test extends Survey{
    protected ArrayList<String> correctAnswers = new ArrayList<>();
    //contains a list of responses, each response is equal to each question's correct response

    @Override
    public void addQuestion(Question question, Input inputHandler) {
        Questions.add(question);
        if (question.returnType().equals("essay")) {
            correctAnswers.add("No correct answer");
        } else {
            question.display();
            String ans = "";
            for (int i = 1; i <= question.numOfResponses; i++) {
                System.out.println("For Response " + i);
                printer.correctAnswer(); //ask user to give correct answer (need to exclude essay question!!)
                System.out.println("Note: For matching questions, please put on same line with space to differentiate.");
                ans += inputHandler.readString() + "|";
            }
            correctAnswers.add(ans);
            //System.out.println("Correct answer is: " + ans);
        }
    }

    public void displayWithAnswers(){
        int qNum = 1;
        for (Question question : Questions) {
            System.out.print(qNum + ". ");
            question.display();
            String currCorrectAns = correctAnswers.get(qNum-1);
            System.out.print("Correct Answer: " +  currCorrectAns);
            System.out.println();
            qNum++;
        }
    }

    public void grade(Response gradeResponse){
        //calculate how much each question is worth
        double qPoints = (double) 100 / correctAnswers.size();
        double score = 100;
        int numOfEssays = 0;
        ArrayList<String> theirResponse = gradeResponse.getResponse();
        for (int i = 0; i < theirResponse.size(); i++) {
            if (Questions.get(i).returnType().equals("essay")) {
                numOfEssays++;
                score -= qPoints;
            } else if (!(theirResponse.get(i).equals(correctAnswers.get(i)))) {
                //System.out.println("Their response: " + theirResponse.get(i));
                //System.out.println("Correct answer: " + correctAnswers.get(i));
                score -= qPoints;
            }
        }

        if (numOfEssays == 0) {
            System.out.println("You received an " + score +  " on the test. The test was worth 100 points.");
        } else if (numOfEssays == 1) {
            int totalPoints = 100 - numOfEssays;
            System.out.println("You received an " + score +  " on the test. The test was worth 100 points, but only " + totalPoints + " of those points could be auto-graded because there was " + numOfEssays + " essay question. ");
        }
    }

}
