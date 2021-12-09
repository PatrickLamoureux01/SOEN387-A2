package Classes;


import Classes.Choice;

import java.io.Serializable;
import java.util.ArrayList;

public class Poll implements Serializable {


    private String id;
    private String name;
    private String question;
    private String status;
    private ArrayList<Choice> choices;
    private String dt;

    public Poll() {

    }

    public Poll(String _id,String _name,String _question) {
        this.id = _id;
        this.name = _name;
        this.question = _question;
    }

    public Poll(String _id,String _name,String _question,String _status) {
        this.id = _id;
        this.name = _name;
        this.question = _question;
        this.status = _status;
    }

    public Poll(String _name,String _question,ArrayList<Choice> _choices) {
        this.name = _name;
        this.question = _question;
        this.choices = _choices;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Choice> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<Choice> choices) {
        this.choices = choices;
    }

    public String getDateTime() { return dt; }

    public void setDateTime(String dt) { this.dt = dt; }

    public void upvote(int index) { this.choices.get(index).incrementVotes(); }


}
