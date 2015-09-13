/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Comparator;


/**
 *
 * @author nayan
 */
public class ConversationComparator implements Comparator<ConversationCount>
{
    

    @Override
    public int compare(ConversationCount c, ConversationCount c1)
    {
        if (c1.getNumberOfMessages()>c.getNumberOfMessages())
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }

}
