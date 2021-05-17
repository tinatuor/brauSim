package Elements;

import Utils.XMLimport;
import javaSimulation.Process;
import javaSimulation.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import static Utils.Time.returnDates;
import static Utils.Time.weekDay;
import static Utils.XMLimport.*;

public class BrewSimulation extends Process{


    /***
     * Implementation of the simulation and simulation-run
     *
     */
    String xmlPath;
    String outputPath;

    double simPeriod;
    public static int nrStations;
    public static Head[] freeLists;
    public static Head[] wLists;
    public static Head cleaningList = new Head();

    public static int nrResources;
    public static Head[] freeRLists;
    public static Head[] wRLists;
    public static Resource[] resources;

    public static int nrSuds;
    public static BatchRecord[] sude;
    public static int[] bSuds;

    public static ArrayList<BrewProcess> bP;

    //Create variables used for statistics and set startTime
    public static int noOfBrews;
    public static int total = 0;
    public long startTime = System.currentTimeMillis();
    public static int[] bDays;
    String[] sudnr;
    public static int buffDays = 0;

    public static BrewCleaning[] brewCleanings;
    public static String cleanlog = "";

    public static String[][] csv;

    public static String startDate;

    public static String workDays;
    public static String listsep;

    public static boolean backwards = false;

    public BrewSimulation(String xmlPath, String outputPath, StartRecord start) {
        simPeriod = start.simPeriod;
        nrStations = start.nrStations;
        bDays = start.bDays;
        //create automatically two Queues: Waiting Lists for Batches and Lists of free BrewProcesses
        freeLists = new Head[nrStations+2];
        wLists = new Head[nrStations+2];
        for (int i = 1; i <= nrStations + 1; i++) {
            freeLists[i] = new Head();
            wLists[i] = new Head();
        }
        //same for Resources
        nrResources = start.nrRessourcen;
        freeRLists = new Head[nrResources+1];
        wRLists = new Head[nrResources+1];
        for (int i = 1; i < nrResources + 1; i++) {
            freeRLists[i] = new Head();
            wRLists[i] = new Head();
        }

        nrSuds = start.nrSude;
        bSuds = start.bSud;
        sudnr = start.sudnr;

        workDays = start.workdays;
        startDate = start.dates;
        listsep = start.listsep;

        this.xmlPath = xmlPath;
        this.outputPath = outputPath;

    }

    public void actions() {

        try {
            initElements();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        startProgram();
        //hold(simPeriod + 100000);
        hold(simPeriod+(0.3*simPeriod));
        reportSuds();
        csvWriter();
    }

    public void initElements() throws ParseException {
         /***
         * initialise Elements from Model through XMLimport
         ***/
        resources = getR(xmlPath, nrResources, freeRLists, wRLists);
        sude = getSuds(xmlPath, nrSuds, resources, freeRLists, wRLists);
        brewCleanings = getBrewClean(xmlPath, resources, freeRLists, wRLists);
        bP = getBP(xmlPath, resources, freeRLists, wRLists);
        //prepare csv
        //csv = new String[bP.size()+1][((int) simPeriod)+1];
        //csv = new String[bP.size()+3][((int) simPeriod)+1];
        csv = new String[bP.size()+3][((int) (simPeriod+(0.3*simPeriod)+1))];
        csv[0][0] = "";
        csv[bP.size()+2][0] = "" + (nrStations+2) + "_Tag";
        csv[bP.size()+1][0] = "" + (nrStations+1) + "_Wochentag";
        for (int i = 1; i <= simPeriod; i++) {
            if (startDate.equals("")) {
                csv[0][i] = "" + i;
                csv[bP.size()+2][i] = "" + i;
                csv[bP.size()+1][i] = weekDay(i);
            } else {
                try {
                    csv[0][i] = returnDates(startDate, i);
                    csv[bP.size()+2][i] = "" + i;
                    csv[bP.size()+1][i] = weekDay(i);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 1; i <= bP.size(); i++) {
            csv[i][0] = bP.get(i-1).station + "_" + bP.get(i-1).name;
        }
    }

    public void startProgram() {
        activate(new BatchProgram(bDays, bSuds, sudnr));
    }


    public void reportSuds() {
         /***
         * Prints data and statistics for the simulation run
         */
        double avgTime = 0;
        int endStation = wLists.length-1;
        int nrSuds = wLists[endStation].cardinal();
        try {
            FileWriter logWriter = new FileWriter(outputPath + "\\Simulationlog.txt", false);
            while (!wLists[endStation].empty()) {
                Batch toPrint = (Batch) wLists[endStation].first();
                toPrint.out();
                System.out.println("Sud nr: " + toPrint.getID() + " " + toPrint.name);
                logWriter.write("Sud nr: " + toPrint.getID() + " " + toPrint.name + "\n");
                System.out.println("Startzeit: " + toPrint.program[0]);
                logWriter.write("Startzeit: " + toPrint.program[0]+ "\n");
                for (int i = 1; i <= endStation - 1; i++) {
                    System.out.println("Station " + i + " " + toPrint.stations[i] + ": " + toPrint.program[i]);
                    logWriter.write("Station " + i + " " + toPrint.stations[i] + ": " + toPrint.program[i]+ "\n");
                }
                System.out.println("Zeit total: " + (toPrint.program[endStation - 1] - toPrint.program[0]) + " Tage\n");
                logWriter.write("Zeit total: " + (toPrint.program[endStation - 1] - toPrint.program[0]) + " Tage\n" + "\n");
                avgTime = avgTime + (toPrint.program[endStation - 1] - toPrint.program[0]);
            }

            System.out.println("Reinigung: \n" + cleanlog);
            logWriter.write("Reinigung: \n" + cleanlog+ "\n");

            System.out.println("Anzahl abgeschlossener Sude: " + nrSuds);
            logWriter.write("Anzahl abgeschlossener Sude: " + nrSuds+ "\n");
            System.out.println("Anzahl Sude im System: " + (noOfBrews - nrSuds));
            logWriter.write("Anzahl Sude im System: " + (noOfBrews - nrSuds)+ "\n");
            System.out.println("Durchschnittszeit pro Sud: " + String.format("%.2f", avgTime / nrSuds) + " Tage\n");
            logWriter.write("Durchschnittszeit pro Sud: " + String.format("%.2f", avgTime / nrSuds)+ " Tage\n\n");
            for (int i = 1; i < resources.length; i++) {
                System.out.println(resources[i].name + "-Verbrauch: " + resources[i].usedAmount);
                logWriter.write(resources[i].name + "-Verbrauch: " + resources[i].usedAmount + "\n");
                System.out.println(resources[i].name + "-Kosten: " + resources[i].totalcost);
                logWriter.write(resources[i].name + "-Kosten: " + resources[i].totalcost + "\n");
            }

            System.out.println("Puffertage: " + buffDays + "\n\n");
            logWriter.write("Puffertage: " + buffDays + "\n\n");

            System.out.println("Ausführungszeit: " + String.format("%.2f", (System.currentTimeMillis()
                    - startTime) / 1000.0) + " Sekunden");
            logWriter.write("Ausführungszeit: " + String.format("%.2f", (System.currentTimeMillis()
                    - startTime) / 1000.0) + " Sekunden");
            logWriter.close();
        }
        catch (Exception e) {
            System.out.println("Datei simulationlog.txt konnte nicht geöffnet werden. Bitte schliessen Sie die Datei, falls sie noch offen ist.");
        }
    }

    public void csvWriter() {
        /***
         * Stores the detailed results of the simulation
         *
         */

        try {
            Writer csvout = new OutputStreamWriter(new FileOutputStream(outputPath + "\\Simulationrun.csv", false), StandardCharsets.ISO_8859_1);
            Arrays.sort(csv, Comparator.comparing(o -> o[0]));
            for (int i = 0; i <= bP.size()+2; i++) {
                String line = "";
                for (int j = 0; j <= simPeriod; j++) {
                    if (csv[i][j] != null) {
                        line = line + csv[i][j] + listsep;
                    } else {
                        line = line + listsep;
                    }
                }
                csvout.write(line+"\n");
            }
            csvout.close();
            System.out.println("\nErgebnisse gespeichert unter Simulationrun.csv und Simulationlog.txt");
        } catch (IOException e) {
            System.out.println("Datei simulationrun.csv konnte nicht geöffnet werden. Bitte schliessen Sie die Datei, falls Sie noch offen ist. ");
        }
    }

    public static class BatchProgram extends Process {
        /***
         * Executes BrewProgram
         */

        int[] bDays;
        int[] bSuds;
        String[] sudnr;

        public void actions() {
                for (int i = 0; i < bDays.length; i++) {
                    if (i == 0) {
                        hold(bDays[i]);
                    } else {
                        hold(bDays[i] - bDays[i - 1]);
                    }
                    while ((!workDays.contains(weekDay()))) {
                        hold(1);
                    }
                    total++;
                    Random random = new Random();
                    if (bSuds[i] <= nrSuds) {
                        BatchRecord sRec = sude[bSuds[i]];
                        Batch thisSud = new Batch(sRec.name, random.randInt((int) sRec.fermentationDuration, (int) sRec.fermDurMax), sRec.conditioningDuration, sRec.category, sRec.nrStations, sRec.rRecord);
                        if (sudnr == null) {
                            thisSud.setID("" + total);
                        }
                        else {
                            thisSud.setID(sudnr[i]);
                        }
                        activate(thisSud);
                    } else {
                        activate(brewCleanings[0]);
                    }
                }

        }

        public BatchProgram (int[] bDays, int[] bSuds, String[] sudnr) {
            this.bDays = bDays;
            this.bSuds = bSuds;
            this.sudnr = sudnr;
        }

    }


    public static void main (String[] args) {
        String xmlPath = args[0] + "\\brauModel.xml";
        String outputPath = args[0];
        if (args[1] != null && args[1].equals("backwards")) {
            backwards = true;
        }
        StartRecord start = XMLimport.getStart(xmlPath);
        activate(new BrewSimulation(xmlPath, outputPath, start));
    }

}
