/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.Serializable;

/**
 *
 * @author Moon
 */
public class UnAndSubscribe implements Serializable{
    boolean Sports;
    boolean Political;
    boolean Entertainment;

    public UnAndSubscribe() {
        this.Sports = true;
        this.Political = true;
        this.Entertainment = true;
    }

    public boolean isSports() {
        return Sports;
    }

    public void setSports(boolean Sports) {
        this.Sports = Sports;
    }

    public boolean isPolitical() {
        return Political;
    }

    public void setPolitical(boolean Political) {
        this.Political = Political;
    }

    public boolean isEntertainment() {
        return Entertainment;
    }

    public void setEntertainment(boolean Entertainment) {
        this.Entertainment = Entertainment;
    }
    
    
}
