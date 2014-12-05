/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.net.URL;
import java.io.BufferedReader;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedInputStream;

/**
 *
 * @author Seomyungwon
 */
public class CompoundMatching {

    public static void main(String[] args) throws FileNotFoundException, IOException {

 //       ArrayList<String> cidCol = new ArrayList<>();
        ArrayList<String> nameCol = new ArrayList<>();
        File errOutFile = new File ("F:\\ChemblDB\\List of OCT1_err1.txt");
        BufferedWriter errOut = new BufferedWriter(new FileWriter(errOutFile));
        String line = "";
        File inputFile = new File("F:\\ChemblDB\\List of OCT1_err.txt");
        BufferedReader in = new BufferedReader(new FileReader(inputFile));
        while ((line = in.readLine()) != null) {
 //           String[] column = line.split("\t");
  //          cidCol.add(column[1]);
            nameCol.add(line);
        }
        //cidCol.remove(cidCol.get(0));
        nameCol.remove(nameCol.get(0));

        for (int i = 0; i < nameCol.size(); i++) {
        //for (int i = 57; i < nameCol.size(); i++) {
            CompoundMatching.getSdf(nameCol.get(i), errOut);
                //System.out.println(nameCol.get(i));
            //System.out.println(CompoundMatching.getSynonymCidList(nameCol.get(i)));
        }

//            for (int i = 0; i < cidCol.size(); i++) {
//                CompoundMatching.Matching(nameCol.get(i), cidCol.get(i));                             
//            }
        errOut.close();
    }

    public static ArrayList<String> getSynonymList(String CID) throws MalformedURLException, IOException {

        String line = null;

        ArrayList<String> urlText = new ArrayList<>();
        URL target = new URL("http://pubchem.ncbi.nlm.nih.gov/summary/summary.cgi?q=nama&cid=" + CID);
        InputStream is = target.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
        while ((line = br.readLine()) != null) {
            urlText.add(line);
        }
        String urlTextWithoutTag = urlText.get(urlText.size() - 1);
        urlTextWithoutTag = urlTextWithoutTag.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", " ");
        String[] theTextArray = urlTextWithoutTag.split(",");
        theTextArray = urlTextWithoutTag.split(" ");

        ArrayList<String> resultUrlText = new ArrayList<>();

        for (int i = 0, End = theTextArray.length; i < End; i++) {
            if (!theTextArray[i].isEmpty()) {
                resultUrlText.add(theTextArray[i]);
            }
        }
        return resultUrlText;

    }

    public static ArrayList<String> getSynonymCidList(String CompoundName) throws MalformedURLException, IOException {

        String line = null;
        String line2 = "";
        
        ArrayList<String> cidText = new ArrayList<>();
        
        URL target2 = new URL("http://www.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pccompound&retmax=1000&term=" + CompoundName);
        InputStream is2 = target2.openStream();
        BufferedReader br2 = new BufferedReader(new InputStreamReader(is2, "utf-8"));
        while ((line = br2.readLine()) != null) {
            cidText.add(line);
            cidText.add(" ");

        }

        cidText.remove(cidText.get(0));
        cidText.remove(cidText.get(1));
        cidText.remove(cidText.get(2));
        cidText.remove(cidText.size() - 2);

        for (int a = 0; a < cidText.size(); a++) {
            line2 = line2 + cidText.get(a);
        }

        String cidTextWithoutTag = line2.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", " ");
        String[] theCidArray = cidTextWithoutTag.split(" ");
//        theCidArray = cidTextWithoutTag.split("@");

        ArrayList<String> resultCidText = new ArrayList<>();
        for (int b = 0; b < theCidArray.length; b++) {
            if (!theCidArray[b].isEmpty()) {
                resultCidText.add(theCidArray[b]);
            }
        }

        return resultCidText;

    }

    public static void getSdf(String compoundName) throws IOException {
        try{
        ArrayList<String> theCID = CompoundMatching.getSynonymCidList(compoundName);
        String line = "";
        String resultFilePath = "F:\\ChemblDB\\output\\" + compoundName + ".sdf";
        if (!theCID.isEmpty()) {
            for (int i = 0; i < theCID.size(); i++) {

                URL SDF = new URL("http://pubchem.ncbi.nlm.nih.gov/summary/summary.cgi?cid=" + theCID.get(i) + "&disopt=DisplaySDF");
                InputStream is = SDF.openStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
                String sdf = new String();
                while ((line = br.readLine()) != null) {
                    sdf = sdf + line + "\n";
                }
                br.close();
                BufferedWriter bw = new BufferedWriter(new FileWriter(resultFilePath));
                bw.flush();
                bw.write(sdf);
                bw.close();

            }

            System.out.println("Save!!");
            System.out.println();
            System.out.println();
        }
        }catch (Exception e){
            
        }
    }
    
    public static void getSdf(String compoundName, BufferedWriter out) throws IOException {
        try{
        ArrayList<String> theCID = CompoundMatching.getSynonymCidList(compoundName);
        String line = "";
        String resultFilePath = "F:\\ChemblDB\\output\\" + compoundName + ".sdf";
        if (!theCID.isEmpty()) {
            for (int i = 0; i < theCID.size(); i++) {

                URL SDF = new URL("http://pubchem.ncbi.nlm.nih.gov/summary/summary.cgi?cid=" + theCID.get(i) + "&disopt=DisplaySDF");
                InputStream is = SDF.openStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
                String sdf = new String();
                while ((line = br.readLine()) != null) {
                    sdf = sdf + line + "\n";
                }
                br.close();
                BufferedWriter bw = new BufferedWriter(new FileWriter(resultFilePath));
                bw.flush();
                bw.write(sdf);
                bw.close();

            }

            System.out.println("Save!!");
            System.out.println();
            System.out.println();
        }
        }catch (Exception e){
            out.write(compoundName + "\n");
        }
    }

    public static void Matching(String theName, String theCID) throws MalformedURLException, IOException {
        ArrayList<String> Name = CompoundMatching.getSynonymList(theCID);
        ArrayList<String> CID = CompoundMatching.getSynonymCidList(theName);

        System.out.println(theName);
        System.out.println(Name);
        System.out.println(theCID);
        System.out.println(CID);

        if (Name.contains(theName)) {
            String line = "";
            String theResultFilePath = "F:\\ChemblDB\\output\\" + theName + ".sdf";
            URL SDF = new URL("http://pubchem.ncbi.nlm.nih.gov/summary/summary.cgi?cid=" + theCID + "&disopt=DisplaySDF");
            InputStream is = SDF.openStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            String sdf = new String();
            while ((line = br.readLine()) != null) {
                sdf = sdf + line + "\n";
            }
            /*
            
             if (CID.contains(theCID)) {
             String line = "";
             String theResultFilePath = "C:\\Users\\Seomyungwon\\Desktop\\?��?��?�� data\\output\\"+theName+".sdf";
             URL SDF = new URL ("http://pubchem.ncbi.nlm.nih.gov/summary/summary.cgi?cid="+theCID+"&disopt=DisplaySDF");
             InputStream is = SDF.openStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8")); 
             String sdf = new String();              
             while ((line=br.readLine()) !=null) {
             sdf = sdf + line + "\n";
             }
             */

            br.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter(theResultFilePath));
            bw.flush();
            bw.write(sdf);
            bw.close();

            System.out.println("Compound is saved!!");
            System.out.println();
            System.out.println();
        }

    }

}
