/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.etf.sab.student;

import java.math.BigDecimal;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author CAR
 */
public class RouteInfo {
    private List<PackagePlanInfo> route;
    private int currentIdAdress;
    private int xcordl, ycord;
    private double distancepassed;
    private BigDecimal totalPriceOfDelivery;

    
    public BigDecimal getTotalPriceOfDelivery(){
     return totalPriceOfDelivery;
     
    }
    
    public void setTotalPriceOfDelivery(BigDecimal total){
        this.totalPriceOfDelivery=total;
        
    }
    public void setCoords(Pair<Integer, Integer> coords){
    
    xcordl = coords.getKey();
    ycord = coords.getValue();
    }
    public List<PackagePlanInfo> getRoute() {
        return route;
    }

    public int getCurrentIdAdress() {
        return currentIdAdress;
    }

    public int getXcordl() {
        return xcordl;
    }

    public int getYcord() {
        return ycord;
    }

    public double getDistancepassed() {
        return distancepassed;
    }

    public void setRoute(List<PackagePlanInfo> route) {
        this.route = route;
    }

    public void setCurrentIdAdress(int currentIdAdress) {
        this.currentIdAdress = currentIdAdress;
    }

    public void setXcordl(int xcordl) {
        this.xcordl = xcordl;
    }

    public void setYcord(int ycord) {
        this.ycord = ycord;
    }

    public void setDistancepassed(double distancepassed) {
        this.distancepassed = distancepassed;
    }
    
    
}
