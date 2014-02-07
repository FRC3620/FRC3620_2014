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
public class RobotMode {

    public final String label;

    public static final RobotMode DISABLED = new RobotMode("disabled");
    public static final RobotMode AUTONOMOUS = new RobotMode("autonomous");
    public static final RobotMode TELEOP = new RobotMode("teleop");
    public static final RobotMode TEST = new RobotMode("test");

    private RobotMode(String l) {
        this.label = l;
    }

    public String toString() {
        return label;
    }
}
