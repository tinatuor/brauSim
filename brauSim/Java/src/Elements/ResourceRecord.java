package Elements;

import javaSimulation.Head;

/***
 *
 * ResourceRecord stores the necessary information about resource-usage
 *
 */

public class ResourceRecord {
    Resource[] resources;
    double[] duration;
    double[] amount;
    boolean[] var;
    Head[] freeR;
    Head[] rLine;

    public void addRecord(int position, Resource resource, double duration, double amount, boolean var, Head freeR, Head rLine) {
        this.resources[position] = resource;
        this.duration[position] = duration;
        this.amount[position] = amount;
        this.var[position] = var;
        this.freeR[position] = freeR;
        this.rLine[position] = rLine;
    }

    public ResourceRecord(int nrRecords) {
        resources = new Resource[nrRecords];
        duration = new double[nrRecords];
        amount = new double[nrRecords];
        var = new boolean[nrRecords];
        freeR = new Head[nrRecords];
        rLine = new Head[nrRecords];
    }
}
