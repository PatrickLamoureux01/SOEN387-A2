package Classes;

import org.w3c.dom.Text;

import java.io.Serializable;

public class Choice implements Serializable {

    private int id;
    private String text;
    private String description;
    private int votes;

    public Choice() {

    }

    public Choice(int i, String t, String d) {

        this.id = i;
        this.text = t;
        this.description = d;
        this.votes = 0;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVotes() { return votes; }

    public void setVotes(int vote) { this.votes = vote; }

    public void incrementVotes() { this.votes += 1; }

}
