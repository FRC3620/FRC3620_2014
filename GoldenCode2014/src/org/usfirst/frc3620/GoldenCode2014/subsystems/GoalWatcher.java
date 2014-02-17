/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.usfirst.frc3620.GoldenCode2014.subsystems;

import edu.wpi.first.wpilibj.networktables.NetworkTable;


/**
 *
 * @author Developer
 */
class GoalWatcher {
     public static boolean visionSeesHotGoal(NetworkTable visionTable){
        double blob;
        try{
            
            blob = visionTable.getNumber("BLOB_COUNT");
            cogCenter(visionTable);
            return blob>0;
            
        }
        catch(edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException ex){
            return false;
        }
    }
    //Can we see the goal?
    public static boolean cogCenter(NetworkTable visionTable){
        try{            
            double Cog_X, Image_Width, IMAGEHALF;
            
        Cog_X = visionTable.getNumber("COG_X");
        Image_Width = visionTable.getNumber("IMAGE_WIDTH");
        IMAGEHALF = Image_Width/2;
        
        /////////////////////////////////////////////////////////////////////
        // check Math.abs and lack of import!!!
        System.out.print(Cog_X-IMAGEHALF + "    ");
        System.out.print(Math.abs(IMAGEHALF - Cog_X ) <= Math.ceil(Image_Width/5));
        System.out.print("     ");
        System.out.println(Math.ceil(Image_Width/5));
        
        //////////////////////////////////////////////////////////////////////
        
        visionTable.putBoolean("centered", Math.abs(IMAGEHALF - Cog_X ) <= Math.ceil(Image_Width/5));
        return Math.abs(IMAGEHALF - Cog_X ) <= Math.ceil(Image_Width/5);
        }
        
        catch(edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException ex){
            return false;
        }
    }  
}
