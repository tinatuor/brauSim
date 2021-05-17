package Elements;

import javaSimulation.Head;
import javaSimulation.Process;

import static Elements.BrewSimulation.*;

/***
 * Cleaning Procedure for brewhouses
 *
 */

public class BrewCleaning extends Process {

    double duration;
    Integer station;
    ResourceRecord brewCleanRes;

    public void actions() {
        while (freeLists[station].empty()) {
            hold(1);
        }
        BrewProcess toClean = ((BrewProcess) freeLists[station].first());
        useCResources();
        hold(duration);
        cleanlog = cleanlog + "\tSudhausreinigung: " + time() + "\n";
        for (int i = 1; i <= duration; i++) {
            csv[bP.indexOf(toClean) + 1][((int) time()) - i] = "Reinigung";
        }
        toClean.into(freeLists[station]);
        passivate();
    }

    public void useCResources() {
        if (brewCleanRes != null) {
            for (int i = 0; i < (brewCleanRes.resources.length); i++) {
                double rdur = brewCleanRes.duration[i];
                double amount = brewCleanRes.amount[i];
                Head freeR = brewCleanRes.freeR[i];
                Head rLine = brewCleanRes.rLine[i];
                while (!brewCleanRes.resources[i].isAvailable(amount) || freeR.empty()) {
                    hold(1);
                    brewCleanRes.resources[i].fillResource();
                }
                brewCleanRes.resources[i].setParams(rdur, amount);
                this.into(rLine);
                activate(brewCleanRes.resources[i]);
            }
        }
    }

    public BrewCleaning(double duration, Integer station, ResourceRecord brewCleanRes) {
        this.duration = duration;
        this.station = station;
        this.brewCleanRes = brewCleanRes;
    }

}
