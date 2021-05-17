package Elements;

import javaSimulation.Head;
import javaSimulation.Process;

/***
 *
 * Class Resource is used to model usage of different resources like Water, Workforce, etc.
 * //Create a Resource for every employee
 */

public class Resource extends Process {
    public String name;
    String type;

    double maxCap;
    double startCap;
    double costs;
    double filltime;
    double fillthreshold;

    Head freeR;
    Head rLine;

    double level;
    public double totalcost;
    public double usedDur;
    public double usedAmount;
    public double totalfill;

    double duration;
    double amount;

    public void actions() {
        while (true) {
            //out();
            if(!rLine.empty()) {
                Process brewProcess = (Process) rLine.first();
                brewProcess.out();
                level = level - amount;
                usedAmount = usedAmount + amount;
                if (level == 0) {
                    out();
                }
                if (duration > 0) {
                    hold(duration);
                }
                usedDur = usedDur + duration;
                totalcost = totalcost + (costs*amount);
                fillResource();
                activate(brewProcess);
            }
            wait(freeR);
        }
    }

    public void setParams(double duration, double amount) {
        this.duration = duration;
        this.amount = amount;
    }

    public void fillResource() {
        if (level < maxCap) {
            if ((!type.equals("Personal")) && level <= fillthreshold) {
                totalfill = totalfill + (maxCap - level);
                hold(filltime);
                level = maxCap;
            } else if (type.equals("Personal")) {
                level = startCap;
            }
        }
    }

    public boolean isAvailable(double amount) {
        if (amount >= level) {
            return false;
        } else{
            return true;
        }
    }

    public Resource(String name, String type, double maxCap, double startCap, double costs, double filltime, double fillthreshold, Head freeR, Head rLine) {
        this.name = name;
        this.type = type;
        this.startCap = startCap;
        if (type != null && type.equals("Personal")) {
            this.maxCap = startCap;
        } else {
            this.maxCap = maxCap;
        }
        this.level = startCap;
        this.costs = costs;
        this.filltime = filltime;
        this.fillthreshold = fillthreshold;
        this.freeR = freeR;
        this.usedDur = 0.0;
        this.rLine = rLine;
        this.into(freeR);
    }


}
