package com.mycompany.medicino;
import android.net.Uri;
import android.provider.ContactsContract;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.*;
/**
 * Created by Alex Boldt on 11/7/2015.
 */
public class DrugScanner
{
    //stores each collection(set) of similar drugs
    public ArrayList<Node> myNodeList;
    TreeMap<String,String> brandToGeneric;
    TreeMap<String,Node> genericToNode;

    //Builds a TreeMap which stores generic names with brand names as keys
    public void brandToGenericMap(){
        brandToGeneric = new TreeMap<String, String>();
        for(int i = 0; i < myNodeList.size(); i++){
            String generic = myNodeList.get(i).genericName;
            TreeSet<String> tempBrands = myNodeList.get(i).brandNames;
            for(String brand:tempBrands){
                brandToGeneric.put(brand, generic);
            }
        }
    }

    //Builds a TreeMap which stores Nodes with generic names as keys
    public void genericToNodeMap(){
        genericToNode = new TreeMap<String, Node>();
        for(int i = 0; i < myNodeList.size(); i++){
            Node tempNode = myNodeList.get(i);
            String generic = myNodeList.get(i).genericName;
            genericToNode.put(generic,tempNode);
        }
    }

    public DrugScanner(){
        myNodeList = new ArrayList<Node>();
        // toScan = new File(getAssets().open(String.format("medicine.txt")));
        FileInputStream fis;
        final StringBuffer storedString = new StringBuffer();

        //File toScan = null;
        //try {
            //toScan = new File(getClass().getClassLoader().getResource(fileName).toURI());
        //} catch (URISyntaxException e) {
           // e.printStackTrace();
        //}
        //File pathName = new File(path.getPath());

        scanTheFile();
        brandToGenericMap();
        genericToNodeMap();
    }

    //each drug is going to be its own node
    public static class Node
    {
        String genericName;
        TreeSet<String> brandNames;
        TreeSet<String> alternatives;

        public Node()
        {
            genericName = "";
            brandNames = null;
            alternatives = null;
        }

        public Node(String myGeneric, TreeSet<String> myBrandNames, TreeSet<String> myAlternatives)
        {
            genericName = myGeneric;
            brandNames = myBrandNames;
            alternatives = myAlternatives;
        }
        public String[] info(){
            String[] ret = new String[brandNames.size()+1];
            ret[0] = genericName;
            int i = 1;
            for(String brand: brandNames){
                ret[i]= brand;
                i++;
            }
            return ret;
        }
            public String[] infoAlt(){
                    String[] ret = new String[alternatives.size()];
                    int i = 0;
                    for(String alt: alternatives){
                            ret[i]= alt;
                            i++;
                    }
                    return ret;
            }
    }



    public void scanTheFile()
    {
        //FileInputStream fis;
        //final StringBuffer storedString = new StringBuffer();

        //use these fillers to make the variables of the Node class later
        TreeSet<String> currentBrandSet = new TreeSet<String>();
        TreeSet<String> currentAlternativeSet = new TreeSet<String>();
        String currentLine = "";
        String currentGeneric = "";

        //try
        //{
            //fis = new FileInputStream(textFile);
            //DataInputStream dataIO = new DataInputStream(fis);
            //String strLine = null;
            //while((strLine = textFile.readLine()) != null){
            //storedString.append(strLine);
        //}
            String medicineString = "\n" +
                    "levothyroxine\n" +
                    "Levothroid\n" +
                    "Levoxyl\n" +
                    "Synthroid\n" +
                    "Tirosint\n" +
                    "Unithroid\n" +
                    "Synthroid\n" +
                    "\n" +
                    "rosuvastatin\n" +
                    "Crestor\n" +
                    "*\n" +
                    "atorvastatin\n" +
                    "simvastatin\n" +
                    "Lipitor\n" +
                    "Zetia\n" +
                    "Zocor\n" +
                    "lovastatin\n" +
                    "\n" +
                    "albuterol inhalation\n" +
                    "ProAir HFA\n" +
                    "ProAir RespiClick\n" +
                    "Proventil HFA\n" +
                    "Ventolin HFA\n" +
                    "\n" +
                    "esomeprazole\n" +
                    "Esomeprazole Strontium\n" +
                    "NexIUM\n" +
                    "\n" +
                    "lisdexamfetamine\n" +
                    "Vyvanse\n" +
                    "*\n" +
                    "Adderall\n" +
                    "Ritalin\n" +
                    "Strattera\n" +
                    "Concerta\n" +
                    "methylphenidate\n" +
                    "Adderall XR\n" +
                    "Focalin\n" +
                    "amphetamine\n" +
                    "dextroamphetamine\n" +
                    "Focalin XR\n" +
                    "dexmethylphenidate\n" +
                    "atomoxetine\n" +
                    "Quillivant XR\n" +
                    "\n" +
                    "pregabalin\n" +
                    "Lyrica\n" +
                    "*\n" +
                    "prednisone\n" +
                    "Cymbalta\n" +
                    "amitriptyline\n" +
                    "duloxetine\n" +
                    "Elavil\n" +
                    "\n" +
                    "Tiotropium Bromide\n" +
                    "Spiriva Respimat\n" +
                    "*\n" +
                    "Symbicort\n" +
                    "albuterol\n" +
                    "Advair Diskus\n" +
                    "Ventolin\n" +
                    "ProAir HFA\n" +
                    "ipratropium\n" +
                    "Atrovent\n" +
                    "DuoNeb\n" +
                    "Xopenex\n" +
                    "Combivent\n" +
                    "Proventil\n" +
                    "\n" +
                    "sitagliptin\n" +
                    "Januvia\n" +
                    "*\n" +
                    "metformin\n" +
                    "insulin aspart\n" +
                    "glipizide\n" +
                    "glimepiride\n" +
                    "Lantus\n" +
                    "Victoza\n" +
                    "Invokana\n" +
                    "Levemir\n" +
                    "\n" +
                    "insulin glargine\n" +
                    "Lantus\n" +
                    "Lantus OptiClik Cartridge\n" +
                    "Lantus Solostar Pen\n" +
                    "Toujeo SoloStar\n" +
                    "\n" +
                    "aripiprazole\n" +
                    "Abilify\n" +
                    "*\n" +
                    "Seroquel\n" +
                    "lithium\n" +
                    "clonazepam\n" +
                    "Klonopin\n" +
                    "Lamictal\n" +
                    "Depakote\n" +
                    "\n" +
                    "budesonide/formoterol\n" +
                    "Symbicort\n" +
                    "*\n" +
                    "Spiriva\n" +
                    "albuterol\n" +
                    "Advair Diskus\n" +
                    "Ventolin\n" +
                    "ProAir HFA\n" +
                    "\n" +
                    "oseltamivir\n" +
                    "Tamiflu\n" +
                    "*\n" +
                    "amantadine\n" +
                    "Symmetrel\n" +
                    "oseltamivir\n" +
                    "Dologesic\n" +
                    "Coricidin HBP\n" +
                    "zanamivir\n" +
                    "\n" +
                    "tadalafil\n" +
                    "Cialis\n" +
                    "Viagra\n" +
                    "\n" +
                    "buprenorphine\n" +
                    "Bunavail\n" +
                    "Suboxone\n" +
                    "Zubsolv\n" +
                    "\n" +
                    "naloxone\n" +
                    "Bunavail\n" +
                    "Suboxone\n" +
                    "Zubsolv\n" +
                    "\n" +
                    "ezetimibe\n" +
                    "Zetia\n" +
                    "\n" +
                    "rivaroxaban\n" +
                    "Xarelto\n" +
                    "*\n" +
                    "digoxin\n" +
                    "diltiazem\n" +
                    "Cardizem\n" +
                    "sotalol\n" +
                    "flecainide\n" +
                    "\n" +
                    "nebivolol\n" +
                    "Bystolic\n" +
                    "*\n" +
                    "lisinopril\n" +
                    "amlodipine\n" +
                    "metoprolol\n" +
                    "furosemide\n" +
                    "hydrochlorothiazide\n" +
                    "losartan\n" +
                    "atenolol\n" +
                    "\n" +
                    "celecoxib\n" +
                    "celebrex\n" +
                    "*\n" +
                    "tramadol\n" +
                    "oxycodone\n" +
                    "acetaminophen\n" +
                    "naproxen\n" +
                    "Tylenol\n" +
                    "\n" +
                    "mometasone\n" +
                    "Nasonex\n" +
                    "*\n" +
                    "beclomethasone\n" +
                    "beconase\n" +
                    "\n" +
                    "memantine\n" +
                    "Namenda\n" +
                    "Namenda XR\n" +
                    "*\n" +
                    "Aricept\n" +
                    "Exelon\n" +
                    "donepezil\n" +
                    "rivastigmine\n" +
                    "\n" +
                    "fluticasone\n" +
                    "Arnuity Ellipta\n" +
                    "Flovent Diskus\n" +
                    "Flovent HFA\n" +
                    "Flovent\n" +
                    "Flovent Rotadisk\n" +
                    "Flovent Diskus \n" +
                    "\n" +
                    "oxycodone\n" +
                    "oxyontin\n" +
                    "oxecta\n" +
                    "oxyfast\n" +
                    "roxicodone\n" +
                    "\n" +
                    "hydrochlorothiazide/valsartan\n" +
                    "Diovan HCT\n" +
                    "Benicar HCT\n" +
                    "*\n" +
                    "lisinopril\n" +
                    "amlodipine\n" +
                    "metoprolol\n" +
                    "furosemide\n" +
                    "\n" +
                    "thyroid dessicated\n" +
                    "Armour Thyroid\n" +
                    "Nature-Throid\n" +
                    "Westhroid\n" +
                    "Thyroid Porcine\n" +
                    "\n" +
                    "diclofenac topical \n" +
                    "Pennsaid\n" +
                    "Solaraze\n" +
                    "Voltaren Topical\n" +
                    "\n" +
                    "ethinyl estradiol/etonogestrel\n" +
                    "NuvaRing\n" +
                    "*\n" +
                    "Mirena\n" +
                    "Depo-Provera\n" +
                    "medroxyprogesterone\n" +
                    "Provera\n" +
                    "\n" +
                    "influenza virus vaccine\n" +
                    "Afluria\n" +
                    "Agriflu\n" +
                    "Fluarix\n" +
                    "Quadrivalent\n" +
                    "Flublok\n" +
                    "Flucelvax\n" +
                    "FluLaval\n" +
                    "Fluvirin\n" +
                    "Fluzone\n" +
                    "\n" +
                    "dexlansoprazole\n" +
                    "Dexilant\n" +
                    "Kapidex\n" +
                    "\n" +
                    "olmesartan\n" +
                    "Diovan HCT\n" +
                    "Benicar HCT\n" +
                    "\n" +
                    "albuterol\n" +
                    "ProAir HFA\n" +
                    "ProAir RespiClick\n" +
                    "Proventil HFA\n" +
                    "Ventolin HFA\n" +
                    "\n" +
                    "insulin lispro\n" +
                    "HumaLOG\n" +
                    "HumaLOG Cartridge\n" +
                    "HumaLOG KwikPen\n" +
                    "\n" +
                    "insulin aspart\n" +
                    "NovoLOG\n" +
                    "NovoLog FlexPen\n" +
                    "NovoLOG PenFill\n" +
                    "NovoLog FlexTouch\n" +
                    "\n" +
                    "bimatoprost ophthalmic\n" +
                    "Lumigan\n" +
                    "timolol ophthalmic\n" +
                    "Xalatan\n" +
                    "Travatan\n" +
                    "Alphagan\n" +
                    "latanoprost ophthalmic\n" +
                    "\n" +
                    "solifenacin\n" +
                    "VESIcare\n" +
                    "\n" +
                    "conjugated estrogens\n" +
                    "premarin\n" +
                    "*\n" +
                    "estradiol topical\n" +
                    "Estrace\n" +
                    "Prempro\n" +
                    "Vagifem\n" +
                    "Estrace Vaginal Cream\n" +
                    "\n" +
                    "metformin\n" +
                    "Janumet\n" +
                    "Janumet XR\n" +
                    "\n" +
                    "sitagliptin\n" +
                    "Janumet\n" +
                    "Janumet XR\n" +
                    "\n" +
                    "Olopatadine opthalmic\n" +
                    "Pataday\n" +
                    "Patanol\n" +
                    "Pazeo\n" +
                    "\n" +
                    "ethinyl estradiol\n" +
                    "Mononessa\n" +
                    "Ortho Tri-Cyclen\n" +
                    "Ortho Tri-Cyclen Lo\n" +
                    "Ortho-Cyclen\n" +
                    "Previfem\n" +
                    "Sprintec\n" +
                    "Tri-Linyah\n" +
                    "TriNessa\n" +
                    "Tri-Previfem\n" +
                    "Tri-Sprintec\n" +
                    "Tri-Lo-Sprintec\n" +
                    "Mono-Linyah\n" +
                    "Estarylla\n" +
                    "Tri-Estarylla\n" +
                    "\n" +
                    "norgestimate\n" +
                    "Mononessa\n" +
                    "Ortho Tri-Cyclen\n" +
                    "Ortho Tri-Cyclen Lo\n" +
                    "Ortho-Cyclen\n" +
                    "Previfem\n" +
                    "Sprintec\n" +
                    "Tri-Linyah\n" +
                    "TriNessa\n" +
                    "Tri-Previfem\n" +
                    "Tri-Sprintec\n" +
                    "Tri-Lo-Sprintec\n" +
                    "Mono-Linyah\n" +
                    "Estarylla\n" +
                    "Tri-Estarylla\n" +
                    "\n" +
                    "travoprost ophthalmic\n" +
                    "travatan\n" +
                    "*\n" +
                    "Lumigan\n" +
                    "timolol ophthalmic\n" +
                    "Xalatan\n" +
                    "\n" +
                    "albuterol/ipratropium\n" +
                    "Combivent Respimat\n" +
                    "DuoNeb\n" +
                    "\n" +
                    "metoprolol\n" +
                    "Lopressor\n" +
                    "Metoprolol Succinate ER\n" +
                    "Metoprolol Tartrate\n" +
                    "Toprol-XL\n" +
                    "\n" +
                    "desvenlafaxine\n" +
                    "khedezla\n" +
                    "pristiq\n" +
                    "\n" +
                    "Canagliflozin\n" +
                    "Invokana\n" +
                    "\n" +
                    "Norethindrone acetate and ethinyl estradiol, and ferrous fumarate\n" +
                    "Minastrin 24 Fe\n" +
                    "\n" +
                    "atomoxetine\n" +
                    "Strattera\n" +
                    "\n" +
                    "quetiapine\n" +
                    "Seroquel\n" +
                    "Seroquel XR\n" +
                    "\n" +
                    "ezetimibe/simvastatin\n" +
                    "Vytorin\n" +
                    "*\n" +
                    "atorvastatin\n" +
                    "simvastatin\n" +
                    "Crestor\n" +
                    "Lipitor\n" +
                    "Zetia\n" +
                    "Zocor\n" +
                    "lovastatin\n" +
                    "\n" +
                    "dexmethylphenidate\n" +
                    "Focalin XR\n" +
                    "Focalin\n" +
                    "*\n" +
                    "Adderall\n" +
                    "Vyvanse\n" +
                    "Ritalin\n" +
                    "Strattera\n" +
                    "Concerta\n" +
                    "methylphenidate\n" +
                    "\n" +
                    "formoterol/mometasone\n" +
                    "Dulera\n" +
                    "\n" +
                    "insulin detemir\n" +
                    "Levemir Flexpen\n" +
                    "Levemir\n" +
                    "\n" +
                    "zoster vaccine live\n" +
                    "Zostavax\n" +
                    "\n" +
                    "dutasteride\n" +
                    "Avodart\n" +
                    "*\n" +
                    "Cialis\n" +
                    "Tamsulosin\n" +
                    "finasteride\n" +
                    "Flomax\n" +
                    "doxazosin\n" +
                    "prazosin\n" +
                    "\n" +
                    "dabigatran\n" +
                    "Pradaxa\n" +
                    "*\n" +
                    "aspirin\n" +
                    "Xarelto\n" +
                    "warfarin\n" +
                    "Coumadin\n" +
                    "Eliquis\n" +
                    "apixaban\n" +
                    "\n" +
                    "varenicline\n" +
                    "Chantix\n" +
                    "*\n" +
                    "bupropion\n" +
                    "nicotine\n" +
                    "Zyban\n" +
                    "Nicoderm CQ\n" +
                    "Nicorette\n" +
                    "\n" +
                    "apixaban\n" +
                    "Eliquis\n" +
                    "*\n" +
                    "aspirin\n" +
                    "Xarelto\n" +
                    "warfarin\n" +
                    "Pradaxa\n" +
                    "Lovenox\n" +
                    "heparin\n" +
                    "enoxaparin\n" +
                    "rivaroxaan\n" +
                    "\n" +
                    "adalimumab\n" +
                    "Humira\n" +
                    "*\n" +
                    "budesonide\n" +
                    "hyoscyamine\n" +
                    "azathioprine\n" +
                    "Celebrex\n" +
                    "Asacol\n" +
                    "prednisone\n" +
                    "diclofenac\n" +
                    "\n" +
                    "liraglutide\n" +
                    "Victoza\n" +
                    "*\n" +
                    "metformin\n" +
                    "insulin aspart\n" +
                    "glipizide\n" +
                    "Januvia\n" +
                    "glimepiride\n" +
                    "Lantus\n" +
                    "Invokana\n" +
                    "\n" +
                    "brimonidine/timolol ophthalmic\n" +
                    "Combigan\n" +
                    "*\n" +
                    "Lumigan\n" +
                    "Xalatan\n" +
                    "pilocarpine\n" +
                    "Travatan\n" +
                    "Alphagan\n" +
                    "nadolol\n" +
                    "pilocarpine\n" +
                    "\n" +
                    "rivastigmine\n" +
                    "Exelon\n" +
                    "*\n" +
                    "Aricept\n" +
                    "donepezil\n" +
                    "Namenda\n" +
                    "Memantine\n" +
                    "vitamin e\n" +
                    "galantamine\n" +
                    "ropinirole\n" +
                    "benztropine\n" +
                    "Sinemet\n" +
                    "\n" +
                    "linagliptin\n" +
                    "Tradjenta\n" +
                    "*\n" +
                    "metformin\n" +
                    "insulin aspart\n" +
                    "Januvia\n" +
                    "Lantus\n" +
                    "Levemir\n" +
                    "Invokana\n" +
                    "Actos\n" +
                    "\n" +
                    "conjugated estrogens\n" +
                    "Premarin Vaginal\n" +
                    "*\n" +
                    "Premarin\n" +
                    "estradiol topical\n" +
                    "Estrace\n" +
                    "Prempro\n" +
                    "Vagifem\n" +
                    "\n" +
                    "etanercept\n" +
                    "Enbrel\n" +
                    "*\n" +
                    "prednisone\n" +
                    "naproxen\n" +
                    "Humira\n" +
                    "diclofenac\n" +
                    "methotrexate\n" +
                    "triamcinolone\n" +
                    "Decadron\n" +
                    "sulfasalazine\n" +
                    "\n" +
                    "saxagliptin\n" +
                    "Onglyza\n" +
                    "*\n" +
                    "metformin\n" +
                    "insulin aspart\n" +
                    "glipizide\n" +
                    "Victoza\n" +
                    "glyburide\n" +
                    "Farxiga\n" +
                    "\n" +
                    "ranolazine\n" +
                    "Ranexa\n" +
                    "*\n" +
                    "amlodipine\n" +
                    "metoprolol\n" +
                    "atenolol\n" +
                    "carvedilol\n" +
                    "propranolol\n" +
                    "Norvasc\n" +
                    "Coreg\n" +
                    "Nitrostat\n" +
                    "\n" +
                    "emtricitabine/tenofovir\n" +
                    "Truvada\n" +
                    "*\n" +
                    "Atripla\n" +
                    "Stribild\n" +
                    "Norvir\n" +
                    "Iamivudine\n" +
                    "Viread\n" +
                    "Complera\n" +
                    "\n" +
                    "colesevelam\n" +
                    "Welchol\n" +
                    "*\n" +
                    "atorvastatin\n" +
                    "simvastatin\n" +
                    "Crestor\n" +
                    "Lipitor\n" +
                    "Zetia\n" +
                    "Zocor\n" +
                    "metformin\n" +
                    "Januvia\n" +
                    "Lantus\n" +
                    "fenofibrate\n" +
                    "\n" +
                    "linaclotide\n" +
                    "Linzess\n" +
                    "*\n" +
                    "lactulose\n" +
                    "Amitiza\n" +
                    "Relistor\n" +
                    "GoLYTELY\n" +
                    "lubiprostone\n" +
                    "linaclotide\n" +
                    "MoviPrep\n" +
                    "Enulose\n" +
                    "Bentyl\n" +
                    "Amitiza\n" +
                    "Librax\n" +
                    "Dulcolax\n" +
                    "\n" +
                    "lurasidone\n" +
                    "Latuda\n" +
                    "*\n" +
                    "Seroquel\n" +
                    "lithium\n" +
                    "clonazepam\n" +
                    "Abilify\n" +
                    "Klonopin\n" +
                    "Lamictal\n" +
                    "Depakote\n" +
                    "quetiapine\n" +
                    "olanzapine\n" +
                    "Risperdal\n" +
                    "Zyprexa\n" +
                    "\n" +
                    "brimonidine ophthalmic\n" +
                    "Alphagan P\n" +
                    "*\n" +
                    "Lumigan\n" +
                    "timolol\n" +
                    "Xalatan\n" +
                    "Travatan\n" +
                    "Alphagan\n" +
                    "Azopt\n" +
                    "latanoprost ophthalmic\n" +
                    "\n" +
                    "vilazodone\n" +
                    "Viibryd\n" +
                    "*\n" +
                    "trazodone\n" +
                    "sertraline\n" +
                    "Zoloft\n" +
                    "Lexapro\n" +
                    "Cymbalta\n" +
                    "Prozac\n" +
                    "Wellbutrin\n" +
                    "Abilify\n" +
                    "fluoxetine\n" +
                    "venlafaxine\n" +
                    "bupropion\n" +
                    "\n" +
                    "prasugrel\n" +
                    "Effient\n" +
                    "*\n" +
                    "Plavix\n" +
                    "clopidogrel\n" +
                    "Lovenox\n" +
                    "enoxaparin\n" +
                    "Brilinta\n" +
                    "ticagrelor\n" +
                    "prasugrel\n" +
                    "Integrillin\n" +
                    "eptifibatide\n" +
                    "tirofiban\n" +
                    "\n" +
                    "ritonavir\n" +
                    "Norvir\n" +
                    "*\n" +
                    "Truvada\n" +
                    "Atripia\n" +
                    "Stribild\n" +
                    "Isentress\n" +
                    "lamivudine\n" +
                    "Viread\n" +
                    "Complera\n" +
                    "\n" +
                    "lubiprostone\n" +
                    "Amitiza\n" +
                    "*\n" +
                    "lactulose\n" +
                    "Linzess\n" +
                    "Relistor\n" +
                    "GoLYTELY\n" +
                    "Movantik\n" +
                    "Bentyl\n" +
                    "hyoscyamine\n" +
                    "Metamucil\n" +
                    "\n" +
                    "amlodipine/olmesartan\n" +
                    "Azor\n" +
                    "*\n" +
                    "lisinopril\n" +
                    "amlodipine\n" +
                    "metoprolol\n" +
                    "furosemide\n" +
                    "hydrochlorothlazide\n" +
                    "losartan\n" +
                    "atenolol\n" +
                    "\n" +
                    "fluticasone/salmeterol\n" +
                    "Advair HFA\n" +
                    "*\n" +
                    "Symbicort\n" +
                    "Spiriva\n" +
                    "albuterol\n" +
                    "Ventolin\n" +
                    "ipraropium\n" +
                    "Atrovent\n" +
                    "DuoNeb\n" +
                    "Xopenex\n" +
                    "Combivent\n" +
                    "Proventil\n" +
                    "\n" +
                    "febuxostat\n" +
                    "Uloric\n" +
                    "*\n" +
                    "allopurinol\n" +
                    "Zyloprim\n" +
                    "probenecid\n" +
                    "sulfinpyrazone\n" +
                    "Benemid\n" +
                    "Alprim\n" +
                    "Krystexxa\n" +
                    "\n" +
                    "loteprednol ophthalmic\n" +
                    "Lotemax\n" +
                    "*\n" +
                    "diclofenac ophthalmic\n" +
                    "dexamethasone ophthalmic\n" +
                    "triamcinolone\n" +
                    "ceftriaxone\n" +
                    "Rocephin\n" +
                    "cortisone\n" +
                    "ketorolac ophthalmic\n" +
                    "Pataday\n" +
                    "Patanol\n" +
                    "Pazeo\n" +
                    "Alrex\n" +
                    "Acular\n" +
                    "\n" +
                    "mirabegron\n" +
                    "Myrbetriq\n" +
                    "*\n" +
                    "oxybutynin\n" +
                    "VESIcare\n" +
                    "Ditropan\n" +
                    "tolterodine\n" +
                    "Toviaz\n" +
                    "Detrol\n" +
                    "Botox\n" +
                    "\n" +
                    "mometasone inhalation\n" +
                    "Asmanex Twisthaler\n" +
                    "*\n" +
                    "Symbicort\n" +
                    "albuterol\n" +
                    "fluticasone\n" +
                    "montelukast\n" +
                    "Singulair\n" +
                    "methylprednisone\n" +
                    "Ventolin\n" +
                    "\n" +
                    "adapalene/benzoyl peroxide\n" +
                    "Epiduo\n" +
                    "*\n" +
                    "doxycycline\n" +
                    "clindamycin topical\n" +
                    "Accutane\n" +
                    "Yaz\n" +
                    "\n" +
                    "levalbuterol inhalation\n" +
                    "Xopenex HFA\n" +
                    "*\n" +
                    "Symbicort\n" +
                    "Spiriva\n" +
                    "albuterol\n" +
                    "Ventolin\n" +
                    "Singulair\n" +
                    "montelukast\n" +
                    "\n" +
                    "diflprednate ophthalmic emulsion\n" +
                    "Durezol\n" +
                    "*\n" +
                    "triamcinolone\n" +
                    "atropine ophthalmic\n" +
                    "azathioprine\n" +
                    "Imuran\n" +
                    "TobraDex\n" +
                    "cortisone\n" +
                    "Ocufen\n" +
                    "\n" +
                    "olopatadine ophthalmic\n" +
                    "Patanol\n" +
                    "*\n" +
                    "ketorolac\n" +
                    "Pataday\n" +
                    "Lotemax\n" +
                    "Pazeo\n" +
                    "Alrex\n" +
                    "Acular\n" +
                    "\n" +
                    "efavirenz/emtricitabine/tenofovir\n" +
                    "Atripla\n" +
                    "*\n" +
                    "Truvada\n" +
                    "Stribild\n" +
                    "Isentress\n" +
                    "Norvir\n" +
                    "Viread\n" +
                    "Complera\n" +
                    "Kaletra\n" +
                    "Prezista\n" +
                    "abacavir\n" +
                    "\n" +
                    "aspirin/dipyridamole\n" +
                    "Aggrenox\n" +
                    "*\n" +
                    "Plavix\n" +
                    "clopidogrel\n" +
                    "pravastatin\n" +
                    "Pravachol\n" +
                    "Ecotrin\n" +
                    "Bayer Aspirin\n" +
                    "\n" +
                    "amlodipine/valsartan\n" +
                    "Exforge\n" +
                    "*\n" +
                    "lisinopril\n" +
                    "amlodipine\n" +
                    "metoprolol\n" +
                    "furosemide\n" +
                    "hydrochlorothiazide\n" +
                    "losartan\n" +
                    "Lasix\n" +
                    "\n" +
                    "Insulin regular\n" +
                    "Humulin R\n" +
                    "*\n" +
                    "metformin\n" +
                    "insulin aspart\n" +
                    "Januvia\n" +
                    "Lantus\n" +
                    "Novolog\n" +
                    "Humalog\n" +
                    "Lantus Solostar\n" +
                    "\n" +
                    "sucralfate\n" +
                    "Carafate\n" +
                    "*\n" +
                    "omeprazole\n" +
                    "pantoprazole\n" +
                    "ranitidine\n" +
                    "famotidine\n" +
                    "Nexium\n" +
                    "Protonix\n" +
                    "Prilosec\n" +
                    "\n" +
                    "insulin aspart\n" +
                    "Novolog Flexpen\n" +
                    "*\n" +
                    "metformin\n" +
                    "glipizide\n" +
                    "Januvia\n" +
                    "Lantus\n" +
                    "Victoza\n" +
                    "Actos\n" +
                    "\n" +
                    "eletriptan\n" +
                    "Relpax\n" +
                    "*\n" +
                    "diclofenac\n" +
                    "sumatriptan\n" +
                    "Imtrex\n" +
                    "Reglan\n" +
                    "Maxalt\n" +
                    "\n";
            Scanner sc = new Scanner(medicineString);

            if( sc.hasNextLine())
            {
                currentLine = sc.nextLine();
            }

            while( sc.hasNextLine())
            {
                if(currentLine.length()==0)
                {

                    if(sc.hasNextLine())
                    {
                        currentGeneric = sc.nextLine();
                        //System.out.println(currentGeneric);
                        currentLine = sc.nextLine();
                        //System.out.println(currentLine);

                        //brand loop
                        while( currentLine.length()!=0 && currentLine.indexOf('*') < 0)
                        {
                            currentBrandSet.add(currentLine);
                            currentLine = sc.nextLine();
                        }

                        //alternatives loop
                        if( currentLine.indexOf('*') > -1)
                        {
                            currentLine = sc.nextLine();
                            while(currentLine.length()!=0)
                            {
                                //System.out.println(currentLine);
                                currentAlternativeSet.add(currentLine);
                                currentLine = sc.nextLine();
                            }
                        }

                        if( currentLine.length()==0)
                        {
                            //System.out.println(currentGeneric);
                            myNodeList.add(new Node( currentGeneric, new TreeSet<String>(currentBrandSet),
                                    new TreeSet<String>(currentAlternativeSet)));
                            currentBrandSet.clear();
                            currentAlternativeSet.clear();
                        }
                    }
                }

            }
        }




    //}


    public ArrayList<Node> genericLookUp(String theGeneric)
    {
        ArrayList<Node> nodeList = new ArrayList<Node>();
        if(theGeneric.length()<6){
            return null;
        }
        for(String generic: genericToNode.keySet()){
            if(generic.contains(theGeneric)){
                nodeList.add(genericToNode.get(generic));
            }
        }
        if(nodeList.size()==0){
            return null;
        }
        return nodeList;
    }

    public ArrayList<Node> brandLookUp(String theBrand)
    {	if(theBrand.length()<5){
        return null;
    }
        for(String brand: brandToGeneric.keySet()){
            //System.out.println(brand.equalsIgnoreCase(theBrand));
            if(brand.equalsIgnoreCase(theBrand)){
                //System.out.println(brand.equalsIgnoreCase(theBrand));
                String generic = brandToGeneric.get(brand);
                return genericLookUp(generic);
            }
        }
        return null;
    }

    public Node[] search(String input){
        ArrayList<Node> answer = new ArrayList<Node>();

        answer = brandLookUp(input);

        if(answer==null){
            answer = genericLookUp(input);
        }
        if(answer == null){
            return null;
        }
        return answer.toArray(new Node[answer.size()]);

    }


}
