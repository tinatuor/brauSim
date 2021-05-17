package Elements;

import javaSimulation.*;

import static Elements.BrewSimulation.*;
import static Utils.Time.weekDay;

/***
 *
 * ShadowBP is used to model additional capcity in the same Process
 *
 */

public class ShadowBP extends BrewProcess {

    Head shadowLine;
    BrewProcess parent;

    @Override
    public void actions() {
        while (true) {
            out();
            serveBatch();
            tidyUp();
        }
    }

    @Override
    public void serveBatch() {
        if (!bpWaitingLine.empty()) {
            Batch served = (Batch) bpWaitingLine.first();
            served.out();
            //checkanduseResource();
            double dur;
            if (duration == -1) {
                dur = served.fermentationDuration;
            }
            else if (duration == -2) {
                dur = served.conditioningDuration;
            }
            else {
                dur = duration;
            }
            hold(dur);
            //served.program[station] = time();
            //served.stations[station] = name;
            served.laststation = name;
            for (int i = 1; i <= dur; i++) {
                String otherBatch = "";
                //new: i-1 instead of i
                if (csv[bP.indexOf(parent) + 1][((int) time() - (i-1))] != null) {
                    otherBatch = csv[bP.indexOf(parent) + 1][((int) time() - i)] + " + ";
                }
                csv[bP.indexOf(parent) + 1][((int) time() - i)] = otherBatch + served.name + " " + served.getID();
            }
            served.into(nextWaitingLine);
            if (station+1 <= served.nrStations) {
                BrewProcess nextBP = checkNextStation(station+1, served);
                while (nextWaitingLine.cardinal() > freeLists[station+1].cardinal() && served.pred() != null) {
                    String otherBatch = "";
                    if (csv[bP.indexOf(parent) + 1][((int) time())] != null) {
                        otherBatch = csv[bP.indexOf(parent) + 1][((int) time())] + " + ";
                    }
                    csv[bP.indexOf(parent) + 1][((int) time())] = otherBatch + served.name + " " + served.getID() + " (gepuffert)";
                    buffDays++;
                    hold(1);
                    nextBP = checkNextStation(station+1, served);
                }
                if (nextBP != null) {
                    nextBP.useResources();
                } else {
                    nextBP = checkNextStation(station+1, served);
                    if (nextBP != null) {
                        nextBP.useResources();
                    }
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
    }

    @Override
    public BrewProcess checkNextStation(int next, Batch served) {
        BrewProcess nextBP = null;
        //while (freeLists[next].empty() || ((nextBP = Batch.checkPred(next, served)) == null) || Batch.checkCond(nextBP, next) || !nextBP.checkResources() || (!workDays.contains(weekDay()) && !this.condition)) {
        while (freeLists[next].empty() || ((nextBP = Batch.checkPred(next, served)) == null) || Batch.checkCond(nextBP, next) || !nextBP.checkResources() || (!workDays.contains(weekDay()) && (!this.condition && !(nextBP instanceof TankProcess)))) {
            hold(1);
            String otherBatch = "";
            if (csv[bP.indexOf(parent) + 1][((int) time() - 1)] != null) {
                otherBatch = csv[bP.indexOf(parent) + 1][((int) time() - 1)] + " + ";
            }
            csv[bP.indexOf(parent) + 1][((int) time() - 1)] = otherBatch + served.name + " " + served.getID() + " (gepuffert)";
            buffDays++;
            if (!served.equals(nextWaitingLine.first())) {
                break;
            }
        }
        return nextBP;
    }

    @Override
    public boolean checkResources() {
        boolean check = true;
        if (rRecord != null) {
            for (int i = 0; i < (rRecord.resources.length); i++) {
                double amount = rRecord.amount[i];
                Head freeR = rRecord.freeR[i];
                if (rRecord.var[i]) {
                    while (!rRecord.resources[i].isAvailable(amount) || freeR.empty()) {
                        check = false;
                    }
                }
            }
        }
        return check;
    }

    @Override
    public void useResources() {
        if (rRecord != null) {
            for (int i = 0; i < (rRecord.resources.length); i++) {
                double rdur = rRecord.duration[i];
                double amount = rRecord.amount[i];
                Head freeR = rRecord.freeR[i];
                Head rLine = rRecord.rLine[i];
                if (rRecord.var[i]) {
                    while (!rRecord.resources[i].isAvailable(amount) || freeR.empty()) {
                        hold(1);
                        String otherBatch = "";
                        if (csv[bP.indexOf(parent) + 1][((int) time() + 1)] != null) {
                            otherBatch = csv[bP.indexOf(parent) + 1][((int) time() + 1)] + " + ";
                        }
                        csv[bP.indexOf(parent) + 1][((int) time() - 1)] = otherBatch + ("Warten auf Ressource");
                        buffDays++;
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
    }

    @Override
    public void tidyUp() {
        this.sudname = "";
        wait(shadowLine);
    }

    public ShadowBP (String name, BrewProcess parent, int station, int capacity, double duration, double postDuration, int category, String pred, String condStr, Head shadowLine) {
        super(name, station, capacity, duration, postDuration, category, pred, condStr);
        this.parent = parent;
        this.bpWaitingLine = BrewSimulation.wLists[station];
        this.nextWaitingLine = BrewSimulation.wLists[station+1];
        this.freeBP = BrewSimulation.freeLists[station];
        this.shadowLine = shadowLine;
    }

}
