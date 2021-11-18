package com.example.a1;

import org.w3c.dom.Text;

public class Choice {

    String text;
    String description;
    int votes;


    public Choice(String t, String d) {

        text = t;
        description = d;
        votes = 0;
    }

    public Choice(String t) {
        text = t;
        description = null;
        votes = 0;
    }

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
