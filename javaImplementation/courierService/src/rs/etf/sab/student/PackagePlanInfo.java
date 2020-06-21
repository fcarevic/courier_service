/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

import javafx.util.Pair;

/**
 *
 * @author CAR
 */
public class PackagePlanInfo {
    private int idPackage;
    private int xCordFrom;
    private int yCordFrom;
    private int idAdressFrom;
    private int xCordTo;
    private int yCordTo;
    private int idAdressTo;
    private boolean toDeliver;
    private int status;

    public PackagePlanInfo(int idPackage, int idAdress, Pair<Integer, Integer> cords) {
        this.idPackage = idPackage;
        this.idAdressFrom = idAdress;
        this.xCordFrom= cords.getKey();
        this.yCordFrom=cords.getValue();
    }
    
    public void setInfoTo(int adressTo, Pair<Integer, Integer> cordsTo){
        this.idAdressTo=adressTo;
        this.xCordTo=cordsTo.getKey();
        this.yCordTo=cordsTo.getValue();
        this.toDeliver=true;
    
    
    }
    public void setStatus(int status){
    this.status=status;
    
    }
     public void setToDeliver(boolean deliver){
    this.toDeliver=deliver;
    
    }

    public int getIdPackage() {
        return idPackage;
    }

    public int getxCordFrom() {
        return xCordFrom;
    }

    public int getyCordFrom() {
        return yCordFrom;
    }

    public int getIdAdressFrom() {
        return idAdressFrom;
    }

    public int getxCordTo() {
        return xCordTo;
    }

    public int getyCordTo() {
        return yCordTo;
    }

    public int getIdAdressTo() {
        return idAdressTo;
    }

    public boolean isToDeliver() {
        return toDeliver;
    }

    public int getStatus() {
        return status;
    }

   
    
    
    
}
