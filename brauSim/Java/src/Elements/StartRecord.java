package Elements;

public class StartRecord {
    public double simPeriod;
    public int nrStations;
    public int nrRessourcen;
    public int nrSude;
    public String workdays;
    public int[] bDays;
    public int[] bSud;
    public String[] sudnr;
    public String dates;
    public String listsep;

    public StartRecord(double simPeriod, int nrStations, int nrRessourcen, int nrSude, String workdays, String dates, String listsep, int[] bDays, int[] bSud, String[] sudnr) {
        this.simPeriod = simPeriod;
        this.nrStations = nrStations;
        this.nrRessourcen = nrRessourcen;
        this.nrSude = nrSude;
        this.workdays = workdays;
        this.dates = dates;
        this.listsep = listsep;
        this.bDays = bDays;
        this.bSud = bSud;
        this.sudnr = sudnr;
    }

}
