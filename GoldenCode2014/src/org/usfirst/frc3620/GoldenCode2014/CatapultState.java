/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc3620.GoldenCode2014;

/**
 *
 * @author Developer
 */
public class CatapultState {

    public final String label;

    public static final CatapultState COCKED = new CatapultState("cocked");
    public static final CatapultState SHOOTING = new CatapultState("shooting");
    public static final CatapultState SHOT = new CatapultState("shot");
    public static final CatapultState COCKING_CAM = new CatapultState("cocking cam");
    public static final CatapultState COCKING_TIMER = new CatapultState("cocking timer");
    public static final CatapultState SHOOTING_DELAY = new CatapultState("shooting delay");
    public static final CatapultState QUICK_SHOOTING_DELAY = new CatapultState("quick delay");
    private CatapultState(String l) {
        this.label = l;
    }

    public String toString() {
        return label;
    }
}
