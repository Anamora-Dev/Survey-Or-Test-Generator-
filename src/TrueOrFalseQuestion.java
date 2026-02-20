public class TrueOrFalseQuestion extends MultipleChoiceQuestion{
    private static final long serialVersionUID = 6961801526950881134L;

    public TrueOrFalseQuestion(String prompt){
        super(prompt);
        choices.add("True");
        choices.add("False");
    }

    @Override
    public void addChoice(String choice){
        System.out.println("Cannot add another choice to a true or false question.");
    }

    @Override
    public void modify(){
        System.out.println(prompt);
        //System.out.println();
        printer.modifyPrompt();
        String ans = input.readString();
        if (ans.trim().equalsIgnoreCase("yes") ||  ans.trim().equalsIgnoreCase("y")) {
            System.out.println("Enter new prompt: ");
            setPrompt(input.readString());
        }
    }

}
