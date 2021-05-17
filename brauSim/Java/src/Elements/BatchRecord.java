package Elements;

public class BatchRecord {

    public int ID;
    public String name;
    public double fermentationDuration;
    public double fermDurMax;
    public double conditioningDuration;
    public int category;
    public int nrStations;
    public ResourceRecord rRecord;

    public BatchRecord(int ID, String name, double fermentationDuration, double fermDurMax, double conditioningDuration, int category, int nrStations, ResourceRecord rRecord) {
        this.ID = ID;
        this.name = name;
        this.fermentationDuration = fermentationDuration;
        this.fermDurMax = fermDurMax;
        this.conditioningDuration = conditioningDuration;
        this.category = category;
        this.nrStations = nrStations;
        this.rRecord = rRecord;
    }


}
