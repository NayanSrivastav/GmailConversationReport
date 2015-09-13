/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author nayan
 */
public class ConversationCount
{

    private String email;
    private int numberOfMessages;

    public String getEmail()
    {
        return email;
    }

    public ConversationCount(String email, int numberOfMessages)
    {
        this.email = email;
        this.numberOfMessages = numberOfMessages;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getNumberOfMessages()
    {
        return numberOfMessages;
    }

    public void setNumberOfMessages(int numberOfMessages)
    {
        this.numberOfMessages = numberOfMessages;
    }
}
