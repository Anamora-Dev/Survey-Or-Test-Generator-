import java.io.File;
import java.util.ArrayList;

public class MainMenu {
    private Survey currSurvey;
    private Input inputHandler;
    private Output printer;
    private Serialization serialization;

    public void createTest() {
        printer.testMenu();
        int option = inputHandler.readInt();
        while (option != 10) {
            if (option == 1) {
                //create new test
                currSurvey = new Test();
                currSurvey.setName(generateName("Test"));
                currSurvey.create(inputHandler);
            } else if (option == 2) {
                //display without correctAnswers
                displaySurvey();
            } else if (option == 3) {
                //display with correctAnswers
                displayTestWithAnswers();
            } else if (option == 4) {
                //load a test
                load("Test");
            } else if (option == 5) {
                //save test
                save("Test");
            } else if (option == 6) {
                //take test
                takeSurvey("Test");
            } else if (option == 7) {
                //modify test
                modifySurvey();
            } else if (option == 8) {
                //tabulate test
                tabulate("Test");
            } else if (option == 9){
                //grade test
                gradeTest();
            } else {
                printer.invalidChoice();
            }
            printer.testMenu();
            option = inputHandler.readInt();
        }
    }

    public void createSurvey(){
        printer.surveyMenu(); //output handler prints survey menu prompt
        int option = inputHandler.readInt();
        while (option != 8) {
            if (option == 1) {
                currSurvey = new Survey();
                currSurvey.setName(generateName("Survey"));
                currSurvey.create(inputHandler);
            } else if (option == 2) {
                displaySurvey();
            } else if (option == 3) {
                load("Survey");
            } else if (option == 4) {
                save("Survey");
            } else if (option == 5) {
                takeSurvey("Survey");
            } else if (option == 6) {
                modifySurvey();
            } else if (option == 7) {
                tabulate("Survey");
            } else {
                printer.invalidChoice();
            }
            printer.surveyMenu();
            option = inputHandler.readInt();
        }
    }

    public void tabulate(String type){
        if (saves(type)) {
            printer.toTabulate(type);
            int surveyNum = inputHandler.readInt();
            Survey survey = serialization.deserialize(type + " " + surveyNum, type);
            survey.tabulate();
        }
    }

    public void gradeTest(){
        printer.toGrade();
        int counter = 1;
        //shows the number of tests available
        for (int num = 1; num < 50; num++){
            File file = new File("saved_Tests/" + "Test " + num + ".ser");
            if (file.exists()){
                printer.printMsg(counter + ") Test " + num );
                counter++;
            }
        }
        //gets the test the user would like to grade
        int test = inputHandler.readInt();
        printer.gradeResponse();
        //shows the number of responses available
        counter = 1;
        String filename = "Test " + test + " - Response ";
        for (int num = 1; num < 50; num++){
            File file = new File("saved_Responses/" + filename + num + ".ser");
            if (file.exists()){
                printer.printMsg(counter + ") " + filename + num );
                counter++;
            }
        }
        //gets the response the user would like to grade
        int response = inputHandler.readInt();
        Test gradeTest = (Test) serialization.deserialize("Test " + test, "Test");
        Response gradeResponse = serialization.deserializeResponse(filename + response);
        //now print grade
        gradeTest.grade(gradeResponse);
    }

    public void displayTestWithAnswers(){
        if (checkSurvey("You must have a test loaded in order to display it and its answers.")) {
            Test placeholder = (Test) currSurvey;
            placeholder.displayWithAnswers();
        }
    }

    public void modifySurvey(){
        if (checkSurvey("You must have a survey/test loaded in order to modify it.")) {
            currSurvey.modify(inputHandler);
        }
    }

    public boolean saves(String type){
        int numOfSaves = 1;
        boolean saveExists = false;
        for (int num = 1; num < 50; num++){
            File file = new File("saved_" + type +"s/" + type + " " + num + ".ser");
            if (file.exists()){
                printer.printMsg(numOfSaves + ") " + type + " " + num );
                numOfSaves++;
                saveExists = true;
            }
        }
        if (!saveExists) {
            printer.printMsg("No " + type + "s saved.");
        }
        return saveExists;
    }

    public void takeSurvey(String type){
        if (saves(type)) {
            printer.toTake(type);
            int surveyNum = inputHandler.readInt();
            Survey survey = serialization.deserialize(type + " " + surveyNum, type);
            survey.take();
        }
    }

    public void displaySurvey(){
        if (checkSurvey("You must have a survey/test loaded in order to display it.")) {
            currSurvey.display();
        }
    }

    public void save(String type){
        if (checkSurvey("You must have a " + type + " loaded in order to save it.")) {
            String filename;
            if (currSurvey.getSurveyName() == null) {
                filename = generateName(type);
            } else {
                filename = currSurvey.getSurveyName();
            }
            currSurvey.save(currSurvey, filename, type);
        }
    }

    //checks what number to save the next survey or test to based on current survey or test numbers saved
    private int checkNextNum(String type){
        int nextNum = 1;
        for (int num = 1; num < 50; num++){
            File file = new File("saved_" + type +"s/" + type + " " + num + ".ser");
            if (file.exists()){
                //printer.printMsg("Survey " + num );
                nextNum = num + 1;
            }
        }
        return nextNum;
    }

    private String generateName(String type){
        int nextNum = 1;
        for (int num = 1; num < 50; num++){
            File file = new File("saved_" + type +"s/" + type + " " + num + ".ser");
            if (file.exists()){
                //printer.printMsg("Survey " + num );
                nextNum = num + 1;
            }
        }
        return type + " " + nextNum;
    }

    public void load(String type){
        ArrayList<Integer> saves = new ArrayList<>();
        //checks for any saved surveys or tests and keeps track of them in saves array
        for (int num = 1; num < 50; num++){
            File file = new File("saved_"+ type +"s/" + type + " " + num + ".ser");
            if (file.exists()){
                saves.add(num);
            }
        }

        //if there are any saved surveys or tests, ask which to load
        if (!saves.isEmpty()){
            printer.printMsg("Which " + type + " would you like to load?");
            for (Integer num : saves) {
                printer.printMsg(type + " " + num);
            }
            int surveyNum = inputHandler.readSurveyNum(saves);
            String surveyName = type + " " + surveyNum;
            currSurvey = serialization.deserialize(surveyName, type);
        } else {
            printer.printMsg("You must have a " + type + " saved in order to load one.");
        }
    }

    //checks if a current survey is loaded and if it has any questions
    public boolean checkSurvey(String error){
        if (currSurvey == null) {
            System.out.println(error);
            return false;
        }else if (currSurvey.Questions.isEmpty()) {
            System.out.println("There are no questions.");
            return false;
        } else {
            return true;
        }
    }


    void main () {
        serialization = new Serialization();
        inputHandler = new Input();
        printer = new Output();

        //Check if user wants to do a survey or a test
        printer.startUp();
        int input = inputHandler.readInt();
        while (input != 3) {
            if (input == 1) {
                createSurvey();
            } else  if (input == 2) {
                createTest();
            } else {
            printer.invalidChoice();
            }
            printer.startUp();
            input = inputHandler.readInt();
        }
    }
}