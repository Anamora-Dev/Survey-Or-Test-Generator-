import java.io.Serializable;
import java.util.ArrayList;

public class Response implements Serializable {
    private static final long serialVersionUID = 6961801526950881134L;
    protected ArrayList<String> response; //each survey has multiple responses for multiple questions

    public Response(){
        response = new ArrayList<>();
    }

    public void setResponse(ArrayList<String> r){ response = r; }

    public ArrayList<String> getResponse(){ return response; }

    public void addResponse(String r){
        response.add(r);
    }

    public void deleteResponse(int index){
        response.remove(index);
    }

    public void display(){
        int i = 1;
        for (String r : response){
            System.out.println(r);
            i++;
        }
    }
}
