package Elements;

import javaSimulation.Process;
import javaSimulation.*;

import static Elements.BrewSimulation.*;
import static Utils.Time.weekDay;

/***
 *
 * Class BrewProcess is used to model different activities in the Process like brewing or conditioning.
 *
 */

public class BrewProcess extends Process {

    public String name;
    int capacity;
    Double duration;
    Double postDuration;
    int station;
    ResourceRecord rRecord;

    public String sudname = "";

    public boolean condition;
    public String pred;
    public int category;

    public Head freeBP;
    public Head bpWaitingLine;
    public Head nextWaitingLine;

    Head shadowLine;

    double dur;

    public void actions() {
        while (true) {
            out();
            while (!workDays.contains(weekDay())) {
                hold(1);
            }
            if (!bpWaitingLine.empty()) {
                serveBatch();
                if (postDuration > 0) {
                    hold(postDuration);
                }
                tidyUp();
            } else {
                passivate();
            }
        }
    }

    public void serveBatch() {
            Batch served = (Batch) bpWaitingLine.first();
            served.out();
            //using resources
            //useResources();
            //actual activity
            //double dur;
            if (duration == -1) {
                dur = served.fermentationDuration;
            }
            else if (duration == -2) {
                dur = served.conditioningDuration;
            }
            else {
                dur = duration;
            }
            //put additional capacity into the List of available BP
            ShadowBP[] shadow = new ShadowBP[shadowLine.cardinal()];
            for (int i = 0; i < shadow.length; i++) {
                if (!shadowLine.empty()) {
                    shadow[i] = (ShadowBP) shadowLine.first();
                    if (this instanceof TankProcess) {
                        shadow[i].sudname = served.name;
                    }
                    //shadow.into(freeBP);
                    shadow[i].follow(freeBP);
                }
            }
            hold(dur);
            //make sure unused additional capacity is taken back
            for (int j = 0; j < shadow.length; j++) {
                if (!freeBP.empty()) {
                    BrewProcess current = ((BrewProcess) freeBP.first());
                    for (int i = 0; i < freeBP.cardinal(); i++) {
                        if (current != null) {
                            if (current.equals(shadow[j])) {
                                shadow[j].out();
                                shadow[j].sudname = "";
                                shadow[j].into(shadowLine);
                            }
                            current = (BrewProcess) current.suc();
                        }
                    }
                }
            }
            //served.program[station] = time();
            //served.stations[station] = name;
            served.laststation = name;
            for (int i = 1; i <= dur; i++) {
                //don't overwrite multiple capacity
                if (shadowLine.cardinal() < capacity) {
                    csv[bP.indexOf(this) + 1][((int) time() - i)] = served.name + " " + served.getID();
                }
            }
            served.into(nextWaitingLine);
            int next = this.station+1;
            if (next <= served.nrStations) {
                BrewProcess nextBP = checkNextStation(next, served);
                //Avoid collisions with other batches that enter the next station at the same time
                while (nextWaitingLine.cardinal() > freeLists[next].cardinal() && served.pred() != null) {
                    //don't overwrite multiple capacity
                    String otherBatch = "";
                    if (csv[bP.indexOf(this) + 1][((int) time())] != null) {
                        otherBatch = csv[bP.indexOf(this) + 1][((int) time())] + " + ";
                    }
                    csv[bP.indexOf(this) + 1][((int) time())] = otherBatch + served.name + " " + served.getID() + " (gepuffert)";
                    buffDays++;
                    hold(1);
                    dur++;
                    nextBP = checkNextStation(next, served);
                }
                if (nextBP != null) {
                    nextBP.useResources();
                }
                served.program[station] = time();
                served.stations[station] = name;
                activate(nextBP);
            } else {
                served.program[station] = time();
                served.stations[station] = name;
                activate(served);
            }
        }

    /***
     * Check availability of the next station
     */
    public BrewProcess checkNextStation(int next, Batch served) {
        BrewProcess nextBP = null;
        while (freeLists[next].empty() || ((nextBP = Batch.checkPred(next, served)) == null) || Batch.checkCond(nextBP, next) || !nextBP.checkResources() || (!workDays.contains(weekDay()) && (!this.condition && !(nextBP instanceof TankProcess)))) {
            hold(1);
            String otherBatch = "";
            if (csv[bP.indexOf(this) + 1][((int) time() - 1)] != null) {
                otherBatch = csv[bP.indexOf(this) + 1][((int) time() - 1)] + " + ";
            }
            csv[bP.indexOf(this) + 1][((int) time() - 1)] = otherBatch + served.name + " " + served.getID() + " (gepuffert)";
            buffDays++;
            //in case "served" was already picked from waitingLine to be processed
            if (!served.equals(nextWaitingLine.first())) {
                break;
            }
        }
        return nextBP;
    }

    public boolean checkResources() {
        boolean check = true;
        if (rRecord != null) {
            for (int i = 0; i < (rRecord.resources.length); i++) {
                double amount = rRecord.amount[i];
                Head freeR = rRecord.freeR[i];
                if (!rRecord.resources[i].isAvailable(amount) || freeR.empty()) {
                    check = false;
                }
            }
        }
        return check;
    }

    public void useResources() {
        if (rRecord != null) {
            for (int i = 0; i < (rRecord.resources.length); i++) {
                double rdur = rRecord.duration[i];
                double amount = rRecord.amount[i];
                Head rLine = rRecord.rLine[i];
                rRecord.resources[i].setParams(rdur, amount);
                this.into(rLine);
                activate(rRecord.resources[i]);
            }
        }
    }

    public void tidyUp() {
        into(freeBP);
        passivate();
    }

    //Constructor without any Resources
    public BrewProcess(String name, int station, int capacity, double duration, double postDuration, int category, String pred, String condStr) {
        this.name = name;
        this.station = station;
        this.capacity = capacity;
        this.duration = duration;
        this.postDuration = postDuration;
        this.category = category;
        this.pred = pred;
        this.bpWaitingLine = BrewSimulation.wLists[station];
        this.nextWaitingLine = BrewSimulation.wLists[station+1];
        this.freeBP = BrewSimulation.freeLists[station];
        this.shadowLine = new Head();
        if (capacity > 1) {
            for (int i = 2; i <= capacity; i++) {
                new ShadowBP(name, this, station, 1, duration, postDuration, category, pred, condStr, shadowLine).into(shadowLine);
            }
        }
        if (condStr.equals("ein")) {
            this.condition = false;
        } else {
            //this.condition = (BrewSimulation.wLists[station+1].empty());
            this.condition = true;
        }
        into(freeBP);
    }

    //Constructor with Resources
    public BrewProcess(String name, int station, int capacity, double duration, double postDuration, int category, String pred, String condStr, ResourceRecord rRecord) {
        this.name = name;
        this.station = station;
        this.capacity = capacity;
        this.duration = duration;
        this.postDuration = postDuration;
        this.category = category;
        this.pred = pred;
        this.bpWaitingLine = BrewSimulation.wLists[station];
        this.nextWaitingLine = BrewSimulation.wLists[station+1];
        this.freeBP = BrewSimulation.freeLists[station];
        this.shadowLine = new Head();
        this.rRecord = rRecord;
        if (capacity > 1) {
            for (int i = 2; i <= capacity; i++) {
                new ShadowBP(name, this, station, 1, duration, postDuration, category, pred, condStr, shadowLine).into(shadowLine);
            }
        }
        if (condStr.equals("ein")) {
            this.condition = false;
        } else if (this.station == nrStations){
            this.condition = false;
        }
        else {
            //this.condition = (BrewSimulation.wLists[station+1].empty());
            this.condition = true;
        }
        into(freeBP);
    }

}


