import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Input implements Serializable {

    public Input(){}

    public String readString() {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        return s.trim();
    }

    public String readMatches(Integer numOfChoices){
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine().toLowerCase().trim();
        if (s.length() != 2){
            //match answer must have one letter and one number
            System.out.println("Too many characters, must be one letter followed by one number.");
        }
        String firstHalf = s.substring(0, 1);
        String secondHalf = s.substring(1);

        //check if first half is a letter of one of the options
        char c = firstHalf.charAt(0);
        char biggest = (char) ('a' + numOfChoices - 1);
        try {
            int i = Integer.parseInt(firstHalf);
            System.out.println("The first character of your match cannot be the number.");
            return readMatches(numOfChoices);
        } catch(NumberFormatException e){
            //if string is not one letter
            if (c < 'a' || c > biggest) { //string can't be bigger than the largest letter option
                System.out.println("This is not an option.");
                return readMatches(numOfChoices);
            }
        }

        //check if second half is a number
        try{
            //checks if i is a number
            int i = Integer.parseInt(secondHalf);
            //checks if i is negative
            if (i < 0){
                System.out.println("Second character can't be negative, must be a given option.");
                return readMatches(numOfChoices);
            } else if (i > numOfChoices){
                System.out.println("Second character can't be greater than " + numOfChoices + ".");
                return readMatches(numOfChoices);
            }
            return s;
        } catch(NumberFormatException e){
            System.out.println("Second character should be an integer.");
            return readMatches(numOfChoices);
        }

    }

    public String readAnswer(Integer numOfChoices) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine().toLowerCase().trim();
        //check if string is not one letter
        if (s.length() != 1) {
            System.out.println("Please enter a given one letter option.");
            return readAnswer(numOfChoices);
        }
        //check if answer given is one of the choice options
        char c = s.charAt(0);
        char biggest = (char) ('a' + numOfChoices - 1);
        try {
            int i = Integer.parseInt(s);
            System.out.println("Please enter a given one letter option.");
            return readAnswer(numOfChoices);
        } catch(NumberFormatException e){
            if (c < 'a' || c > biggest) { //string can't be bigger than the largest letter option
                System.out.println("This is not an option.");
                return readAnswer(numOfChoices);
            }
            return s;
        }
    }

    public int readInt() {
        Scanner scanner = new Scanner(System.in);
        //System.out.print(prompt);
        int i = 0;
        try{
            //checks if i is a number
            i = Integer.parseInt(scanner.nextLine());
            //checks if i is negative
            if (i < 0){
                System.out.println("Number can't be negative.");
                return readInt();
            }
            return i;
        } catch(NumberFormatException e){
            System.out.println("Not an integer, try again.");
            return readInt();
        }
    }

    //number of responses cant be one
    public int readResponseNum(){
        int i = readInt();
        if (i == 0){
            System.out.println("Question can't have no response number.");
            return readResponseNum();
        } else return i;
    }

    public int readQuestionNum(ArrayList<Question> questions){
        //make sure number is not 0 and less than array size
            int i = readInt();
            if (i <= questions.size() && i != 0) {
                return i;
            } else {
                System.out.println("Question number not found");
                return readQuestionNum(questions);
            }
    }

    public int readSurveyNum(ArrayList<Integer> surveys){
        int i = readInt();
        //check if i is an existing survey number
        for (Integer survey : surveys){
            if (survey == i){
                return i;
            }
        }

        System.out.println("This survey does not exist.");
        return readSurveyNum(surveys);

    }
}
