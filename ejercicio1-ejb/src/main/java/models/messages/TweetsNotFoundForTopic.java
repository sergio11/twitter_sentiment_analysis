/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.messages;

import java.io.Serializable;
import utils.IVisitable;
import visitor.IProcessMessageVisitor;

/**
 *
 * @author sergio
 */
public class TweetsNotFoundForTopic implements Serializable, IVisitable<IProcessMessageVisitor>{

    @Override
    public void accept(IProcessMessageVisitor visitor) {
        visitor.visit(this);
    }
}
