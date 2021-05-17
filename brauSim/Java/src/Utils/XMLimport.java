package Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import Elements.*;
import generated.*;
import generated.Record;
import javaSimulation.Head;

public class XMLimport {
    //public static void main(String[] args) {getStart();}

        private static List<Instance> connect (String xmlPath) {
            List <Instance> brauInstanceList = null;
            try {
                JAXBContext jc = JAXBContext.newInstance("generated");
                Unmarshaller u = jc.createUnmarshaller();
                Adoxml myxml = (Adoxml) u.unmarshal(new FileInputStream(xmlPath));

                Model brauModel = myxml.getModels().get(0).getModel().get(0);
                brauInstanceList = brauModel.getInstance();
            }
            catch (JAXBException | FileNotFoundException e) {
                e.printStackTrace();
            }
            return brauInstanceList;
        }

        public static StartRecord getStart(String xmlPath) {
            double simPeriod = 0;
            int nrStations = 0;
            int nrRessourcen = 0;
            int nrSude = 0;
            String workdays = null;
            String listsep = ";";
            int[] bDays = null;
            int[] bSuds = null;
            String[] sudnr = null;

            String[] dateStrings = null;

            StartRecord start = null;

            List <Instance> brauInstanceList = connect(xmlPath);

            for (Instance i : brauInstanceList) {
                if (i.getClazz().equals("brauStart")) {
                    List <Object> attributeList = i.getAttributeOrRecordOrInterref();
                    for (Object o : attributeList) {
                        if (o instanceof Attribute) {
                            Attribute a = (Attribute) o;
                            if (a.getName().contains("Laufzeit")) {
                                simPeriod = Double.parseDouble(a.getContent());
                            }
                            else if (a.getName().equals("Stationen")) {
                                nrStations = Integer.parseInt(a.getContent());
                            }
                            else if (a.getName().equals("Ressourcen")) {
                                nrRessourcen = Integer.parseInt(a.getContent());
                            }
                            else if (a.getName().equals("Sude")) {
                                nrSude = Integer.parseInt(a.getContent());
                            }
                            else if (a.getName().equals("Arbeitstage")) {
                                workdays = a.getContent();
                            }
                            else if (a.getName().equals("Listentrennzeichen")) {
                                if (a.getContent().equals("Komma")) {
                                    listsep = ",";
                                }
                                else {
                                    listsep = ";";
                                }
                            }
                        }
                        else if (o instanceof Record) {
                            Record r = (Record) o;
                            int rLength = Integer.parseInt(r.getRowcount());
                            bDays = new int[rLength];
                            bSuds = new int[rLength];
                            sudnr = new String[rLength];
                            dateStrings = new String[rLength];
                            int rowCounter = 0;
                            List <Row> rRows = r.getRow();
                            for (Row ro : rRows) {
                                List <Object> roAttr = ro.getAttributeOrRecordOrInterref();
                                for (Object roA : roAttr) {
                                    if (roA instanceof Attribute) {
                                        Attribute attr = (Attribute) roA;
                                        if (attr.getName().equals("Tag")) {
                                            bDays[rowCounter] = Integer.parseInt(attr.getContent());
                                        }
                                        if (attr.getName().equals("SudID")) {
                                            bSuds[rowCounter] = Integer.parseInt(attr.getContent());
                                        }
                                        if (attr.getName().equals("Datum")) {
                                            dateStrings[rowCounter] = attr.getContent();
                                        }
                                        if (attr.getName().equals("Sudnr")) {
                                            sudnr[rowCounter] = attr.getContent();
                                        }
                                    }
                                }
                                rowCounter++;
                            }
                        }
                    }
                    if (bDays[bDays.length-1] == 0) {
                        try {
                            bDays = Time.parseDates(dateStrings);
                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if (sudnr[0].equals("")) {
                        sudnr = null;
                    }
                    if (dateStrings == null || dateStrings[0].equals("0001:01:01")) {
                        dateStrings = new String[1];
                        dateStrings[0] = "";
                    }
                    String startDate;
                    startDate = dateStrings[0];
                    start = new StartRecord(simPeriod, nrStations, nrRessourcen, nrSude, workdays, startDate, listsep, bDays, bSuds, sudnr);
                }
            }
            return start;
        }

        public static Resource[] getR(String xmlPath, int nrResourcen, Head[] freeRList, Head[] rLineList) {
            Resource[] resources = new Resource[nrResourcen+1];
            List <Instance> brauInstanceList = connect(xmlPath);

            String name = null;
            String type = null;
            int ID = 0;
            double maxCap = 0;
            double startCap= 0;
            double costs = 0;
            double filltime = 0;
            double fillthreshold = 0;
            Head freeR = null;
            Head rLine = null;

            for (Instance i : brauInstanceList) {
                if (i.getClazz().equals("brauRessource")) {
                    name = i.getName();
                    List<Object> attributeList = i.getAttributeOrRecordOrInterref();
                    for (Object o : attributeList) {
                        if (o instanceof Attribute) {
                            Attribute a = (Attribute) o;
                            if (a.getName().contains("Startkapazität")) {
                                startCap = Integer.parseInt(a.getContent());
                            }
                            else if (a.getName().contains("Maximalkapazität")) {
                                maxCap = Integer.parseInt(a.getContent());
                            }
                            else if (a.getName().contains("Kosten")) {
                                costs = Integer.parseInt(a.getContent());
                            }
                            else if (a.getName().contains("Auffüllzeit")) {
                                filltime = Integer.parseInt(a.getContent());
                            }
                            else if (a.getName().contains("Auffüllschwelle")) {
                                fillthreshold = Double.parseDouble(a.getContent());
                            }
                            else if (a.getName().contains("ID")) {
                                ID = Integer.parseInt(a.getContent());
                            }
                            else if (a.getName().contains("Typ")) {
                                type = a.getContent();
                            }
                        }
                    }
                }
                freeR = freeRList[ID];
                rLine = rLineList[ID];
                resources[ID] = new Resource(name, type, maxCap, startCap, costs, filltime, fillthreshold, freeR, rLine);
            }
            return resources;
        }

        public static ResourceRecord getResourceTable (Record r, Resource[] resources, Head[] freeR, Head[] rLine) {
            int rLength = Integer.parseInt(r.getRowcount());
            ResourceRecord rRecord = new ResourceRecord(rLength);
            int rID = 0;
            double dur = 0;
            double amount = 0;
            int rowCounter = 0;
            boolean var = true;
            List<Row> rRows = r.getRow();
            for (Row ro : rRows) {
                List<Object> roAttr = ro.getAttributeOrRecordOrInterref();
                for (Object roA : roAttr) {
                    if (roA instanceof Attribute) {
                        Attribute attr = (Attribute) roA;
                        if (attr.getName().equals("ID")) {
                            rID = Integer.parseInt(attr.getContent());
                        } else if (attr.getName().equals("Dauer")) {
                            dur = Integer.parseInt(attr.getContent());
                        } else if (attr.getName().equals("Verbrauch")) {
                            amount = Integer.parseInt(attr.getContent());
                        } else if (attr.getName().equals("Verbrauchs- und Kostenstruktur")) {
                            if (!attr.getContent().contains("variabel")) {
                                var = false;
                            }
                        }
                    }
                }
                rRecord.addRecord(rowCounter, resources[rID], dur, amount, var, freeR[rID], rLine[rID]);
                rowCounter++;
            }
            return rRecord;
        }

        public static TankCleaning getTCleaning(String xmlPath, Resource[] resources, Head[] freeR, Head[] rLine) {
            TankCleaning tankCleaner = null;
            double duration = 0;
            Integer tankNr = 1;
            ResourceRecord resourceRecord = null;
            List <Instance> brauInstanceList = connect(xmlPath);

            for (Instance i : brauInstanceList) {
                if (i.getClazz().equals("Tankreinigung")) {

                    List<Object> attributeList = i.getAttributeOrRecordOrInterref();
                    for (Object o : attributeList) {
                        if (o instanceof Attribute) {
                            Attribute a = (Attribute) o;
                            if (a.getName().equals("Bearbeitungsdauer")) {
                                duration = Double.parseDouble(a.getContent());
                            } else if (a.getName().equals("Anzahl Tanks")) {
                                tankNr = Integer.parseInt(a.getContent());
                            }
                        }
                        if (o instanceof Record) {
                            Record r = (Record) o;
                            resourceRecord = getResourceTable(r, resources, freeR, rLine);
                        }

                    }
                }
            }
            tankCleaner = new TankCleaning(duration, tankNr, resourceRecord);
            return tankCleaner;
        }

        public static BrewCleaning[] getBrewClean(String xmlPath, Resource[] resources, Head[] freeR, Head[] rLine) {
            BrewCleaning[] brewCleanings = new BrewCleaning[1];
            double duration = 1;
            Integer station = 1;
            ResourceRecord brewCleanRes = null;

            List<Instance> brauInstanceList = connect(xmlPath);
            for (Instance i : brauInstanceList) {
                if (i.getClazz().equals("Sudhausreinigung")) {

                    List<Object> attributeList = i.getAttributeOrRecordOrInterref();
                    for (Object o : attributeList) {
                        if (o instanceof Attribute) {
                            Attribute a = (Attribute) o;
                            if (a.getName().equals("Stationen")) {
                                station = Integer.parseInt(a.getContent());
                            } else if (a.getName().equals("Bearbeitungsdauer")) {
                                duration = Double.parseDouble(a.getContent());
                            }
                        }
                        if (o instanceof Record) {
                            Record r = (Record) o;
                            brewCleanRes = getResourceTable(r, resources, freeR, rLine);
                        }
                    }
                }
            }
            brewCleanings[0] = new BrewCleaning(duration, station, brewCleanRes);
            return brewCleanings;
        }

        public static ArrayList<BrewProcess> getBP(String xmlPath, Resource[] resources, Head[] freeR, Head[] rLine) {
            TankCleaning tankCleaning = getTCleaning(xmlPath, resources, freeR, rLine);
            ArrayList<BrewProcess> brewProcesses = new ArrayList<BrewProcess>();
            List <Instance> brauInstanceList = connect(xmlPath);

            String name = null;
            int station = 0;
            int capacity = 0;
            double duration = 0;
            double postDuration = 0;
            int category = 0;
            String pred = null;
            String condStr = null;
            ResourceRecord rRecord = null;

            for (Instance i : brauInstanceList) {
                if (i.getClazz().equals("Sudhaus") || i.getClazz().equals("Gärtank") || i.getClazz().equals("Lagertank") || i.getClazz().equals("Lager") || i.getClazz().equals("Abfüllen") || i.getClazz().equals("Sonstiges")) {

                    List<Object> attributeList = i.getAttributeOrRecordOrInterref();
                    for (Object o : attributeList) {
                        if (o instanceof Attribute) {
                            Attribute a = (Attribute) o;
                            if (a.getName().equals("Station")) {
                                station = Integer.parseInt(a.getContent());
                            } else if (a.getName().equals("Kapazität")) {
                                capacity = Integer.parseInt(a.getContent());
                            } else if (a.getName().equals("Bearbeitungsdauer")) {
                                duration = Double.parseDouble(a.getContent());
                            } else if (a.getName().equals("Postdauer")) {
                                postDuration = Double.parseDouble(a.getContent());
                            } else if (a.getName().equals("Kategorie")) {
                                category = Integer.parseInt(a.getContent());
                            } else if (a.getName().equals("Vorgänger")) {
                                pred = a.getContent();
                            } else if (a.getName().equals("Puffer")) {
                                    condStr = a.getContent();
                            }
                        }
                        if (o instanceof Record) {
                            Record r = (Record) o;
                            rRecord = getResourceTable(r, resources, freeR, rLine);
                        }

                    }
                    name = i.getName();
                    if ((!i.getClazz().equals("Gärtank")) && !(i.getClazz().equals("Lagertank"))) {
                        if (rRecord != null) {
                            brewProcesses.add(new BrewProcess(name, station, capacity, duration, postDuration, category, pred, condStr, rRecord));
                        } else {
                            brewProcesses.add(new BrewProcess(name, station, capacity, duration, postDuration, category, pred, condStr));
                        }
                    } else {
                        if (rRecord != null) {
                            brewProcesses.add(new TankProcess(name, station, capacity, duration, postDuration, category, pred, condStr, tankCleaning, rRecord));
                        } else {
                            brewProcesses.add(new TankProcess(name, station, capacity, duration, postDuration, category, pred, condStr, tankCleaning));
                        }
                    }
                    rRecord = null;
                }
            }
            return brewProcesses;
        }

    public static BatchRecord[] getSuds(String xmlPath, int nrSuds, Resource[] resources, Head[] freeR, Head[] rLine) {
        BatchRecord[] suds = new BatchRecord[nrSuds+1];
        List <Instance> brauInstanceList = connect(xmlPath);

        int ID = 0;
        String name = null;
        double fermDuration = 0;
        double fermDurMax = 0;
        double condDuration = 0;
        int category = 0;
        int nrStations = 0;
        ResourceRecord rRecord = null;

        for (Instance i : brauInstanceList) {
            if (i.getClazz().equals("Sud")) {
                name = i.getName();
                List<Object> attributeList = i.getAttributeOrRecordOrInterref();
                for (Object o : attributeList) {
                    if (o instanceof Attribute) {
                        Attribute a = (Attribute) o;
                        if (a.getName().equals("ID")) {
                            ID = Integer.parseInt(a.getContent());
                        } else if (a.getName().equals("Gärdauer")) {
                            fermDuration = Double.parseDouble(a.getContent());
                        } else if (a.getName().equals("Gärdauer Max")) {
                            fermDurMax = Double.parseDouble(a.getContent());
                        } else if (a.getName().equals("Lagerdauer")) {
                            condDuration = Double.parseDouble(a.getContent());
                        } else if (a.getName().equals("Kategorie")) {
                            category = Integer.parseInt(a.getContent());
                        } else if (a.getName().equals("Stationen")) {
                            nrStations = Integer.parseInt(a.getContent());
                        }
                    }
                    else if (o instanceof Record) {
                        Record r = (Record) o;
                        rRecord = getResourceTable(r, resources, freeR, rLine);
                    }
                }
                if (fermDurMax < fermDuration) {
                    fermDurMax = fermDuration;
                }
                suds[ID] = new BatchRecord(ID, name, fermDuration, fermDurMax, condDuration, category, nrStations, rRecord);
            }
        }
        return suds;
    }

    }



