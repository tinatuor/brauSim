package Elements;

/***
 * Configuration of Tankcleaning-Conditions
 *
 */

public class TankCleaning {

    double duration;
    Integer nrTanks;
    ResourceRecord resources;

    public TankCleaning (double duration, Integer nrTanks, ResourceRecord resources) {
        this.duration = duration;
        this.nrTanks = nrTanks;
        this.resources = resources;
    }
}
