package org.usfirst.frc3620.GoldenCode2014;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables2.type.NumberArray;
import java.io.PrintStream;

/**
 * a flight data recorder.
 * 
 * usage:
 * 
 * create it, passing the desired size into the constructor, then
 * stuff data into it with the add() method. When something significant
 * happens, use the printData() or sendToNetworkTable methods to print the 
 * data to System.out or send it into the network tables. You can clear the 
 * recorder out after an event with the clear() method.
 * 
 * @author WEGSCD
 */
public class FDR {
    /**
     * place to keep the data.
     */
    FDR.DataPoint[] dataPoints;
    /**
     * the index of the next element to use in dataPoints. we wrap it around
     * when the array gets full.
     */
    int nextSlot;
    /**
     * set to true once we filled up the array, since the logic
     * to get the stuff out changes a little once we are full. see
     * printData() for an example.
     */
    boolean filledUp;
    
    static class DataPoint {
        long timestamp;
        double datum;      
    }
    
    public FDR () {
        this(100);
    }
    
    public FDR (int _maxSize) {
        // allocate the place to keep it
        dataPoints = new FDR.DataPoint[_maxSize];
        // preallocate the datapoints, this will make
        // add() go quicker.
        for (int i = 0; i < dataPoints.length; i++)
            dataPoints[i] = new FDR.DataPoint();
        clear();
    }
    
    public void clear() {
        filledUp = false;
        nextSlot = 0;
    }
    
    public void add(double p) {
        // possible change to Robot.getCurrentTimeMillis()?
        add (System.currentTimeMillis(), p);
    }
    
    public void add (long ts, double p) {
        dataPoints[nextSlot].timestamp = ts;
        dataPoints[nextSlot].datum = p;

        nextSlot = nextIndex(nextSlot);
        // if the index wrapped around, we must have filled up!
        if (0 == nextSlot) filledUp = true;
    }
    
    public void printData (boolean provideDeltas) {
        printData (System.out, provideDeltas ? dataPoints[prevIndex(nextSlot)].timestamp : -1);
    }
    
    void printData (PrintStream out, long t0) {
        int start = 0;
        int n = nextSlot;
        if (filledUp) {
            start = nextSlot;
            n = dataPoints.length;
        }
        
        for (int i = start; i < start + n; i++) {
            FDR.DataPoint dp = dataPoints[i % dataPoints.length];
            out.print(dp.timestamp);
            out.print("\t");
            if (t0 > 0) {
                out.print (elapsed(t0, dp.timestamp));
                out.print("\t");
            }
            out.println (dp.datum);
        }
    }
    
    public void sendToNetworkTable (NetworkTable table, String key) {
        // where do I start fishing the data out?
        int start = 0;
        // how much do we have
        int n = nextSlot;
        
        if (filledUp) {
            // start with the old data
            start = nextSlot;
            n = dataPoints.length;
        }
        
        NumberArray outputArray = new NumberArray();
        int j = 0;
        for (int i = start; i < start + n; i++) {
            FDR.DataPoint dp = dataPoints[i % dataPoints.length];
            outputArray.add(dp.timestamp);
            outputArray.add(dp.datum);
        }
        table.putValue(key, outputArray);
    }
    
    double elapsed (long t0, long t1) {
        return (t1 - t0) / 1000.0;
    }
    
    /**
     * figure out the next array index after i, wrapping if
     * necessary
     * @param i
     * @return 
     */
    int nextIndex(int i) {
        int rv = i + 1;
        if (rv >= dataPoints.length) rv = 0;
        return rv;
    }
    
    /**
     * figure out the next array index before i, wrapping if
     * necessary
     */
    int prevIndex(int i) {
        int rv = i - 1;
        if (rv < 0) rv = dataPoints.length - 1;
        return rv;
    }
}
