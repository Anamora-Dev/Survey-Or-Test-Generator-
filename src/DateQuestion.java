public class DateQuestion extends Question{
    private static final long serialVersionUID = 6961801526950881134L;

    public DateQuestion(String prompt){
        super(prompt);
    }

    public String returnType(){
        return "date";
    }

    public void setPrompt(String p) {
        this.prompt = p;
    }

    public void modify() {
        System.out.println("Prompt: "+ prompt);
        //System.out.println();
        printer.modifyPrompt();
        String ans = input.readString();
        if (ans.trim().equalsIgnoreCase("yes")) {
            System.out.println("Enter new prompt: ");
            setPrompt(input.readString());
        }
    }

    public void display(){
        System.out.println(prompt + " Note: A date should be entered in the following format: MM/DD/YYYY");
    }

    @Override
    public String addResponse(){
        String ans = input.readString();
        while (!validFormat(ans)){
            System.out.println("Invalid format");
            ans = input.readString();
        }
        return ans;
    }

    public String take(){
        display();
        String finalAns = "";
        for (int i = 1; i <= numOfResponses; i++) {
            finalAns += (addResponse() + "|");
        }
        return finalAns;
    }

    private boolean validFormat(String ans){
        //need to make sure date is structure as 00/00/0000
        String[] ansArr = ans.trim().split("/");
        //the date has three categories: month, day, and year
        if (ansArr.length != 3) {
            return false;
        } else if ((ansArr[0].length() != 2) || !isInteger(ansArr[0]) || (Integer.parseInt(ansArr[0]) < 0) || (Integer.parseInt(ansArr[0]) > 12)) {
            //month has a length of 2, is an integer, can't be negative, and can't be over 12
            return false;
        } else if (ansArr[1].length() != 2 || !isInteger(ansArr[1]) || (Integer.parseInt(ansArr[1]) < 0) || (Integer.parseInt(ansArr[1]) > 31)) {
            return false;
        } else if (ansArr[2].length() != 4 || !isInteger(ansArr[2]) || (Integer.parseInt(ansArr[2]) < 0)) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isInteger(String s){
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            //System.out.println("Not an integer, try again.");
            return false;
        }
        return true;
    }
}
