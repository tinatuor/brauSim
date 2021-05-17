package Elements;

import javaSimulation.Head;
import javaSimulation.Process;

import static Elements.BrewSimulation.*;

/***
 *
 * Batch-Class used for elements moving through the system
 *
 */

public class Batch extends Process {
    //attributes of the class
    public double fermentationDuration;
    public double conditioningDuration;
    public int category;
    public int nrStations;

    //attributes of the instance
    public String name;
    private String batchID;
    public Double[] program;
    public String[] stations;
    public String laststation;
    public ResourceRecord rRecord;

    public void actions() {
        BrewSimulation.noOfBrews++;
        program[0] = time();
        useResources();
        into(wLists[1]);
        BrewProcess nextBP = null;
        while (freeLists[1].empty() || (this != wLists[1].first()) || ((nextBP = (checkPred(1, this))) == null) || !nextBP.checkResources() || checkCond(nextBP, 1)) {
            hold(1);
            buffDays++;
        }
        nextBP.useResources();
        activate(nextBP);
        passivate();
    }

    public static BrewProcess checkPred(int i, Batch sud) {
        BrewProcess bp = null;
        //only select BPs which are preceded by the last station and - if additional capacity of TankProcess - contain the same Batch-recipe
        //additionally prefers stations with the same category
        BrewProcess current = ((BrewProcess) freeLists[i].first());
        for (int j = 0; j < freeLists[i].cardinal(); j++) {
            if (current.pred.contains(sud.laststation) && (current.sudname.equals("") || current.sudname.equals(sud.name))) {
                bp = current;
                if (sud.category == bp.category) {
                    break;
                }
            }
            current = (BrewProcess) current.suc();
        }
        return bp;
    }

    public static boolean checkCond (BrewProcess bp, int i) {
        boolean cond = false;
        if (bp.condition) {
            cond = freeLists[i+1].empty();
        }
        if (BrewSimulation.backwards) {
            cond = false; //buffer-function makes no sense backwards
        }
        return cond;
    }

    public void useResources() {
        if (rRecord != null) {
            for (int i = 0; i < (rRecord.resources.length); i++) {
                double rdur = rRecord.duration[i];
                double amount = rRecord.amount[i];
                Head freeR = rRecord.freeR[i];
                Head rLine = rRecord.rLine[i];
                while (!rRecord.resources[i].isAvailable(amount) || freeR.empty()) {
                    hold(1);
                    if (!freeR.empty()) {
                        rRecord.resources[i].fillResource();
                    }
                }
                rRecord.resources[i].setParams(rdur, amount);
                this.into(rLine);
                activate(rRecord.resources[i]);
            }
        }
    }

    public void setID (String n) {
        batchID = n;
    }

    public String getID () {
        return batchID;
    }


    public Batch(String name, double fermentationDuration, double conditioningDuration, int category, int nrStations, ResourceRecord rRecord) {
        this.name = name;
        this.fermentationDuration = fermentationDuration;
        this.conditioningDuration = conditioningDuration;
        this.category = category;
        this.nrStations = nrStations;
        this.program = new Double[nrStations+1];
        this.stations = new String[nrStations+1];
        this.laststation = "Start";
        this.rRecord = rRecord;
    }

}
