package Elements;

import javaSimulation.Head;

import static Elements.BrewSimulation.*;
import static Utils.Time.weekDay;

/***
 *
 * TankProcess specifies a BrewProcess with a Cleaning-Procedure
 * Tanks are queued to be cleaned, a certain number of tanks will be cleaned together
 *
 */

public class TankProcess extends BrewProcess {

    Head cleaningList;
    Integer maxCleanListSize;
    Double cleanDur;
    ResourceRecord cleanResources;

    @Override
    // Doesn't check the workday since a tank should be filled right after the previous action
    public void actions() {
        while (true) {
            out();
            if (!bpWaitingLine.empty()) {
                serveBatch();
                if (postDuration > 0) {
                    hold(postDuration);
                }
                tidyUp();
            } else {
                //passivate();
                into(freeBP);
                passivate();
            }
        }
    }

    @Override
    public void tidyUp() {
        //wait until TankProcess is fully empty
        while (shadowLine.cardinal() < capacity-1) {
            hold(1);
        }
        //Queue in waiting List for cleaning
        if (dur > 0) {
            into(cleaningList);
            //clean if conditions of Brewcleaning are satisfied or if there are no free tanks left
            if (cleaningList.cardinal() >= maxCleanListSize || this.freeBP.empty()) {
                clean();
            } else {
                //activate((Batch) bpWaitingLine.first());
                passivate();
            }
        } else {
            into(this.freeBP);
            //passivate();
            //activate((Batch) bpWaitingLine.first());
        }
    }

    public void clean() {
        while (!BrewSimulation.workDays.contains(weekDay()) || !checkCleanResources()) {
            hold(1);
        }
        useCleanResources();
        hold(cleanDur);
        String cleanedTanks = "";
        while (!cleaningList.empty()){
            TankProcess cleaned = (TankProcess) cleaningList.first();
            cleaned.out();
            cleanedTanks = cleaned.name;
            cleanlog = cleanlog + "\tTank-Reinigung: " + time() + " (" + cleanedTanks +")\n";
            for (int i = 1; i <= cleanDur; i++) {
                csv[bP.indexOf(cleaned) + 1][((int) time()) - i] = "Reinigung";
            }
            cleaned.into(cleaned.freeBP);
        }
        //wait(freeBP);
    }

    public boolean checkCleanResources() {
        boolean check = true;
        if (cleanResources != null) {
            for (int i = 0; i < (cleanResources.resources.length); i++) {
                double amount = cleanResources.amount[i];
                Head freeR = cleanResources.freeR[i];
                if (!cleanResources.resources[i].isAvailable(amount) || freeR.empty()) {
                    check = false;
                }
            }
        }
        return check;
    }

    public void useCleanResources() {
        if (cleanResources != null) {
            for (int i = 0; i < (cleanResources.resources.length); i++) {
                double rdur = cleanResources.duration[i];
                double amount = cleanResources.amount[i];
                Head freeR = cleanResources.freeR[i];
                Head rLine = cleanResources.rLine[i];
                while (!cleanResources.resources[i].isAvailable(amount) || freeR.empty()) {
                    hold(1);
                    if (!freeR.empty()) {
                        cleanResources.resources[i].fillResource();
                    }
                }
                cleanResources.resources[i].setParams(rdur, amount);
                this.precede(rLine); //last Place in the list
                activate(cleanResources.resources[i]);
                this.into(cleaningList);
            }
        }
    }


    public TankProcess(String name, int station, int capacity, double duration, double postDuration, int category, String pred, String condStr, TankCleaning tankCleaning) {
        super(name, station, capacity, duration, postDuration, category, pred, condStr);
        this.cleaningList = BrewSimulation.cleaningList;
        this.cleanDur = tankCleaning.duration;
        this.maxCleanListSize = tankCleaning.nrTanks;
        this.cleanResources = tankCleaning.resources;
    }

    public TankProcess(String name, int station, int capacity, double duration, double postDuration, int category, String pred, String condStr, TankCleaning tankCleaning, ResourceRecord rRecord) {
        super(name, station, capacity, duration, postDuration, category, pred, condStr, rRecord);
        this.cleaningList = BrewSimulation.cleaningList;
        this.cleanDur = tankCleaning.duration;
        this.maxCleanListSize = tankCleaning.nrTanks;
        this.cleanResources = tankCleaning.resources;
    }


}

