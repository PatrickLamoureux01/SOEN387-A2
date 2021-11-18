package com.example.a1;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class PollBusiness {



    public static void CreatePoll(Poll poll, String name, String question, ArrayList<Choice> choices) {
        try {
            poll.setName(name);
            poll.setQuestion(question);
            poll.setChoices(choices);
            poll.setStatus(Poll.PollStatus.CREATED);
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }

    public static void UpdatePoll(Poll poll, String name, String question, ArrayList<Choice> choices) {
        try {
            if(poll.getStatus() == Poll.PollStatus.RELEASED)
                throw new Exception("Error: This Poll has been released. Unrelease the Poll to update it.");

            if (poll.getStatus() == Poll.PollStatus.RUNNING || poll.getStatus() == Poll.PollStatus.CREATED) {
                poll.clear();
                poll.setName(name);
                poll.setQuestion(question);
                poll.setChoices(choices);
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

    }

    public static void ClearPoll(Poll poll) {
        try {
            if(poll.getStatus() == Poll.PollStatus.CREATED)
                throw new Exception("Error: Poll must be Running or Release to be cleared.");

            if (poll.getStatus() == Poll.PollStatus.RUNNING) {
                poll.clear();
            } else if (poll.getStatus() == Poll.PollStatus.RELEASED) {
                poll.clear();
                poll.setStatus(Poll.PollStatus.CREATED);
            }
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }

    public static void ClosePoll(Poll poll) {
        try {
            if(poll.getStatus() != Poll.PollStatus.RELEASED){
                throw new Exception("Error: Only Released Polls may be closed.");
            }

            //Remove the Polls Data from the system
            poll = null;
            System.gc();
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }

    public static void RunPoll(Poll poll) {
        try {
            if(poll.getStatus() == Poll.PollStatus.RUNNING) {
                throw new Exception("Error: Poll is already Running.");
            }
            if(poll.getStatus() == Poll.PollStatus.RELEASED) {
                throw new Exception("Error: Poll has been Released. Clear Poll to reset it to be ran again.");
            }
            poll.setStatus(Poll.PollStatus.RUNNING);
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }

    public static void ReleasePoll(Poll poll) {
        try {
            if(poll.getStatus() == Poll.PollStatus.CREATED) {
                throw new Exception("Error: Poll must be Running before it can be Released.");
            }
            if(poll.getStatus() == Poll.PollStatus.RELEASED) {
                throw new Exception("Error: Poll has already been Released.");
            }
            poll.setStatus(Poll.PollStatus.RELEASED);
            LocalDateTime dt = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            String formattedDT = dt.format(formatter);

            String x = formattedDT.replace(":","-");

            poll.setDateTime(x);
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void UnreleasePoll(Poll poll) {
        try {
            if(poll.getStatus() != Poll.PollStatus.RELEASED) {
                throw new Exception("Error: This Poll has not been Released.");
            }
            poll.setStatus(Poll.PollStatus.RUNNING);
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }

    public static void Vote(Poll poll, Participant user, int choice) {
        poll.upvote(choice);
    }

    public static HashMap<String[],Integer> GetPollResults(Poll poll) {
        return null;
    }

    public static void DownloadPollDetails(Poll poll) {
        try {
            if(poll.getStatus() != Poll.PollStatus.RELEASED) {
                throw new Exception("Error: This Poll has not been Released.");
            }
            String targetPath = "D:\\User\\OneDrive - Concordia University - Canada\\School\\Concordia\\Semester_6\\SOEN387\\Assignments\\A1\\";
            String filename = poll.getName()+"-"+poll.getDateTime()+".txt";
            File newFile = new File(targetPath,filename);
            System.out.println(newFile.getName());
            Boolean test = newFile.createNewFile();
            try {
                PrintWriter output = new PrintWriter(newFile);
                ArrayList<Choice> c = poll.getChoices();

                output.write("Poll name: " + poll.getName() + "\n");
                output.write("Poll Question: " + poll.getQuestion() + "\n");
                for (Choice x : c) {
                    output.write(x.getText() + ": " + x.getVotes() + "\n");
                }
                output.flush();
                output.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }

}
