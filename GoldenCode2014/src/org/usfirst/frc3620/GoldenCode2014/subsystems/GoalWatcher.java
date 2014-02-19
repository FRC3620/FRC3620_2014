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
    static long lastErrorTime = 0;
    static int errorCount = 0;
    static void handleError(Exception ex, String s) {
        long ellapseTime = System.currentTimeMillis() - lastErrorTime;
        errorCount++;
        if (ellapseTime > 5000) {
            System.out.println(errorCount + " vision errors in 5 seconds");
            System.out.println("last error message was " + ex + " in " + s);
            errorCount = 0;
            lastErrorTime = System.currentTimeMillis();
        }
    }
     public static boolean visionSeesHotGoal(NetworkTable visionTable){
        double blob;
        try{
            
            blob = visionTable.getNumber("BLOB_COUNT");  //Gets Blob count vairable from the visionTable and sets it as blob
            return blob>0 && cogCenter(visionTable, .15);  //if there is a big blob, then it is likely the goal
            
        }
        catch (edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException ex) {
            handleError(ex, "Blob");
            return false;
        }
    }
    //Can we see the goal?
public static boolean cogCenter(NetworkTable visionTable, double widthPer) {
    try{            
            double Cog_X, Image_Width, IMAGEHALF; 
            //if the center of the blob that we can see is near to the center, at 18' we should be able to see the entire robot with the 67 degree field of view
        Cog_X = visionTable.getNumber("COG_X");
        Image_Width = visionTable.getNumber("IMAGE_WIDTH");
        IMAGEHALF = Image_Width/2;// finds the center of the picture
        boolean retval = Math.abs(IMAGEHALF - Cog_X) <= (Image_Width * widthPer);
//        /////////////////////////////////////////////////////////////////////
//        // check Math.abs and lack of import!!!
//        System.out.print(Cog_X-IMAGEHALF + "    ");
//        System.out.print(Math.abs(IMAGEHALF - Cog_X ) <= Math.ceil(Image_Width/5));
//        System.out.print("     ");
//        System.out.println(Math.ceil(Image_Width/5));
//        
//        //////////////////////////////////////////////////////////////////////
            visionTable.putBoolean("centered", retval);
            return retval;
        } catch (edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException ex) {
            handleError(ex, "cog");
            return false;
        }
    }
}
