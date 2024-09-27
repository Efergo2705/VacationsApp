/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vacations.bean;

/**
 *
 * @author ESTEBAN-GOMEZ
 */
public class State {
    private String state;

    public State(String state) {
        this.state = state;
    }
    public State(){
        
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return state;
    }
}
