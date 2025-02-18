package com.example.javafxproject.production.main;

import com.example.javafxproject.production.Files.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.javafxproject.production.builderPatterns.AddressBuilder;
import com.example.javafxproject.production.enums.City;
import com.example.javafxproject.production.enums.TypeOfTransportation;
import com.example.javafxproject.production.exceptions.checkedExceptions.DateAfterToday;
import com.example.javafxproject.production.exceptions.checkedExceptions.DateBeforeToday;
import com.example.javafxproject.production.exceptions.uncheckedExceptions.AccommodationNameAlreadyExistsException;
import com.example.javafxproject.production.exceptions.uncheckedExceptions.ValidNumberException;
import com.example.javafxproject.production.model.*;
import com.example.javafxproject.production.records.Discount;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Main
{
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)
    {
        logger.info("Program je pokrenut.");
        Scanner inputScanner=new Scanner(System.in);
        Set<Passenger> passengers=getPassengers(inputScanner);
        List<Outing> outings=getOutings(inputScanner,passengers);
        List<Transport> transports=getTransports(inputScanner,passengers);
        List<Accommodation> accommodations=getAccommodations(inputScanner,passengers);
        List<User> userList= FileUtils.getUsersFromFile();
        for (User user: userList)
        {
            System.out.println(user.getId()+" "+user.getUsername()+" "+user.getPassword());
        }
        //Ispis svih putnika
        outputPassangers(passengers);
        //Ispis svih prijevoza
        outputTransportations(transports);
        //Ispis svih izleta
        outputOutings(outings);
        //Ispis svih smještajeva
        outputAccommodations(accommodations);
        //Ispis izleta na kojima je pojedini putnik bio
        printOutingPassengerMap(outings,passengers);

        //Kreiranje liste putnika i sortiranje iste po godinama (od najstarijeg)
        List<Passenger> passengerList=new ArrayList<>(passengers);
        passengerList.sort(Comparator.comparing(Passenger::getDateOfBirth));
        //Ispis sortiranog seta Putnika prema datumu (od najstarijeg)
        passengerList.forEach(objekt -> System.out.println(objekt.getName()));
        //Sortiranje izleta abecedno po imenu
        outings.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
        //Ispis sortiranih izleta
        outings.forEach(objekt -> System.out.println(objekt.getName()));
        //Sortiranje prijevoza po akcijskoj cijeni od naveće
        transports.sort((obj1, obj2) -> obj2.getReducedPrice().compareTo(obj1.getReducedPrice()));
        //Ispis sortiranih prijevoza
        transports.forEach(objekt -> System.out.println(objekt.getName()));
        //Sortiranje smještaja prema zauzetosti (od najmanje)
        accommodations.sort((obj1, obj2) -> {
            long dif1 = ChronoUnit.DAYS.between(obj1.getCheckInDate(), obj1.getCheckOutDate());
            long dif2 = ChronoUnit.DAYS.between(obj2.getCheckInDate(), obj2.getCheckOutDate());
            return Long.compare(dif1, dif2);
        });
        //ispis sortiranih smjestajeva
        accommodations.forEach(objekt -> System.out.println(objekt.getName()));

        // Filtriranje i ispisivanje putnika koji su rođeni prije 2000. godine
        passengers.stream()
                .filter(obj -> obj.getDateOfBirth().isBefore(LocalDate.of(2000, 1, 1)))
                .forEach(obj -> System.out.println(obj.getName()));
        // Filtriranje i ispisivanje prijevoza čija je redovna cijena manja od 200
        transports.stream()
                .filter(obj -> obj.getRegularPrice().compareTo(new BigDecimal("200")) < 0)
                .forEach(obj -> System.out.println(obj.getName()));
        // Filtriranje i ispisivanje izleta koji se događaju u Parizu
        outings.stream()
                .filter(obj -> obj.getAddress().getCity().getName().equals("Paris"))
                .forEach(obj -> System.out.println(obj.getName()));
        // Filtriranje i ispisivanje smještaja koji su instance Hostel i imaju broj ljudi manji od 2
        accommodations.stream()
                .filter(obj -> obj instanceof Hostel && ((Hostel) obj).getNumberOfPassengersPerRoom() < 2)
                .forEach(obj -> System.out.println( obj.getName()));
        //Korištenje generičke klase koja ima metodu za ispis svih elemenata seta koje prima kao parametar
        FirstGenericClassPassengers<Passenger> genericClassPassengers = new FirstGenericClassPassengers<>(passengers);
        genericClassPassengers.outputList();
        //Korištenje generičke klase koja ima metodu za ispis svih elemenata liste i seta koje prima kao parametar
        SecondGenericClassPO<Outing,Passenger> genericClassPO=new SecondGenericClassPO<>(outings,passengers);
        genericClassPO.outputList();
        genericClassPO.outputSet();
    }

    public static  <T extends Number> void checkIfNumberIsValid(T number) throws ValidNumberException
    {
        if (number.doubleValue() < 0)
        {
            throw new ValidNumberException("Ne možete upisati broj koji je negativan!");
        }
    }
    public static  void checkIfObjectsAlreadyExists(String accommodationName,List<Accommodation> accommodationList) throws AccommodationNameAlreadyExistsException
    {
      for (Accommodation acc:accommodationList)
      {
          if (acc.getName().equals(accommodationName))
          {
              throw new AccommodationNameAlreadyExistsException("Oprez, već postoji smještaj s tim imenom, neka korisnik pripazi kod odabira.");
          }
      }
    }
    public static void checkIfDateIsOlder(LocalDate inputDate) throws DateBeforeToday
    {
        LocalDate currentDate = LocalDate.now();
        if (inputDate.isBefore(currentDate))
        {
            throw new DateBeforeToday("Ne možete upisati datum za događaj u prošlosti!");
        }
    }
    public static void checkIfDateIsYounger(LocalDate inputDate) throws DateAfterToday
    {
        LocalDate currentDate = LocalDate.now();
        if (inputDate.isAfter(currentDate))
        {
            throw new DateAfterToday("Ne možete upisati datum rođenja za osobu koja se još nije rodila!");
        }
    }
    public static void printOutingPassengerMap(List<Outing> outings, Set<Passenger> passengers) {
        Map<Passenger, List<Outing>> passengerOutingMap = new HashMap<>();

        for (Passenger passenger : passengers) {
            for (Outing outing : outings) {
                if (outing.getPassengers().contains(passenger)) {
                    passengerOutingMap.computeIfAbsent(passenger, k -> new ArrayList<>()).add(outing);
                }
            }
        }

        for (Map.Entry<Passenger, List<Outing>> entry : passengerOutingMap.entrySet()) {
            System.out.println("Putovanja za putnika " + entry.getKey().getName() + ":");
            for (Outing outing : entry.getValue()) {
                System.out.println("- " + outing.getName());
            }
        }
    }
    private static List<Accommodation> getAccommodations(Scanner inputScanner, Set<Passenger> passengers)
    {
        boolean error;
        int numberOfAccommodations=0;
        do
        {
            error=false;
            System.out.println("Unesite broj smješaja koje želite unijeti: ");
            try
            {
                numberOfAccommodations=inputScanner.nextInt();
                checkIfNumberIsValid(numberOfAccommodations);
            }
            catch (InputMismatchException exception) {
                System.out.println("Molimo Vas da unesete broj.");
                logger.error("Pogreška! Korisnik nije upisao broj pri odabiru broja smještaja za unos. " + exception);
                error = true;
            }
            catch (ValidNumberException exception)
            {
                System.out.println("Molimo Vas da unesete pozitivan broj.");
                logger.error("Pogreška! Korisnik je upisao broj manji od 0 pri unosu broja smještaja. " + exception);
                error = true;
            }
            inputScanner.nextLine();
        }
        while (error);
        List<Accommodation> accommodations=new ArrayList<>();
        for (int i=0;i<numberOfAccommodations;i++)
        {
            System.out.println("Unesite podatke za "+(i+1)+". smještaj: ");
            enterAccommodationTypes(i,inputScanner,accommodations,passengers);
        }
        logger.info("Uspješno kreirano polje smještaja");
        return accommodations;
    }

    private static void enterAccommodationTypes(int i,Scanner inputScanner,List<Accommodation> accommodations,Set<Passenger> passengers)
    {
        int choiceRoom=0;
        boolean error;
        do
        {
            error=false;
            try
            {
                System.out.println("Unesite 1 za Turistički smještaj, 2 za Kamp, a 3 za ostale smještaje. ");
                choiceRoom=inputScanner.nextInt();
            }
            catch (InputMismatchException exception)
            {
                System.out.println("Molimo Vas da unesete broj.");
                logger.error("Pogreška! Korisnik nije upisao broj pri odabiru vrste smještaja. " + exception);
                error = true;
            }
            inputScanner.nextLine();
        }
        while (error);
        switch (choiceRoom) {
            case 1:
                accommodations.add(nextRoomAccommodation(inputScanner, i, passengers,accommodations));
                break;
            case 2:
                accommodations.add(nextCamp(inputScanner,i, passengers,accommodations));
                logger.info("Ispravan unos kapa.");
                break;
            case 3:
                accommodations.add(enterAccommodation(inputScanner, i,passengers,accommodations));
                logger.info("Ispravan unos smjestaja.");
                break;
            default:
                System.out.println("Neispravan unos");
                logger.info("Neirspravan unos odabira artikla.");
                enterAccommodationTypes(i,inputScanner,accommodations,passengers);
        }
    }
    private static Accommodation nextRoomAccommodation(Scanner inputScanner,int i, Set<Passenger> passengers,List<Accommodation> accommodations)
    {
        int choiceHotelOrHostel=0;
        boolean error;
        do
        {
            error = false;
            System.out.println("Unesite 1 za Hotel, a 2 za Hostel ");

            try
            {
                choiceHotelOrHostel = inputScanner.nextInt();
            }
            catch (InputMismatchException exception)
            {
                System.out.println("Molimo Vas da unesete broj.");
                logger.error("Pogreška! Korisnik nije upisao broj pri odabiru vrste smještaja. " + exception);
                error = true;
            }
            inputScanner.nextLine();
        }
        while (error);
        Accommodation accommodation=null;
        switch (choiceHotelOrHostel) {
            case 1:
                accommodation= nextHotel(inputScanner,i, passengers,accommodations);
                break;
            case 2:
                accommodation = nextHostel(inputScanner,i, passengers,accommodations);
                break;
            default:
                System.out.println("Neispravan unos");
                logger.info("Neispravan unos smještaja.");
                nextRoomAccommodation(inputScanner,i,passengers,accommodations);
        }

        return accommodation;
    }
    private static Hotel nextHotel(Scanner inputScanner,int i, Set<Passenger> passengers,List<Accommodation> accommodations)
    {
        Accommodation accommodation=enterAccommodation(inputScanner,i,passengers,accommodations);
        return new Hotel(accommodation,accommodation.getPassengers().size());
    }
    private static Hostel nextHostel(Scanner inputScanner,int i, Set<Passenger> passengers,List<Accommodation> accommodations)
    {
        Accommodation accommodation=enterAccommodation(inputScanner,i,passengers,accommodations);
        return new Hostel(accommodation,accommodation.getPassengers().size());
    }
    private static Camp nextCamp(Scanner inputScanner,int i, Set<Passenger> passengers,List<Accommodation> accommodations)
    {
        Accommodation accommodation=enterAccommodation(inputScanner,i,passengers,accommodations);
        boolean error;
        BigDecimal area = new BigDecimal(0);

        do {
            error = false;
            System.out.println("Unesite površinu kapa u metrima kvadratnim: ");

            try
            {
                area = inputScanner.nextBigDecimal();
                checkIfNumberIsValid(area);
            }
            catch (InputMismatchException exception)
            {
                System.out.println("Molimo Vas da unesete broj.");
                logger.error("Pogreška! Korisnik nije upisao broj pri upisu površine kampa. " + exception);
                error = true;
            }
            catch (ValidNumberException exception)
            {
                System.out.println("Molimo Vas da unesete pozitivan broj.");
                logger.error("Pogreška! Korisnik je upisao broj manji od 0 pri unosu površine kampa. " + exception);
                error = true;
            }
            inputScanner.nextLine();
        }
        while(error);
        return new Camp(accommodation,area);
    }
    private static Accommodation enterAccommodation(Scanner inputScanner,int i, Set<Passenger> passengers,List<Accommodation> accommodations)
    {
        Long accommodationId=(long) i;
        System.out.println("Unesite naziv "+(i+1)+". smještaja: ");
        String accommodationName="Smjestaj";
        try
        {
            accommodationName=inputScanner.nextLine();
            checkIfObjectsAlreadyExists(accommodationName,accommodations);
        }
        catch(AccommodationNameAlreadyExistsException exception)
        {
            System.out.println("Već postoji objekt s tim nazivom, pripazite na unos ostalih podataka.");
            logger.info("Oprez! Korisnik je već upisao objekt s identičnim nazivom. " + exception);
        }
        Address accommodationAddress=getAddress(inputScanner);
        boolean error;
        BigDecimal accommodationRegularPrice=new BigDecimal(0);
        do
        {
            error=false;
            System.out.println("Unesite cijenu "+(i+1)+".jednog noćenja: ");
            try
            {
                accommodationRegularPrice=inputScanner.nextBigDecimal();
                checkIfNumberIsValid(accommodationRegularPrice);
            }
            catch (InputMismatchException exception)
            {
                System.out.println("Molimo Vas da unesete broj.");
                logger.error("Pogreška! Korisnik nije upisao broj pri unosu cijene noćenja. " + exception);
                error = true;
            }
            catch (ValidNumberException exception)
            {
                System.out.println("Molimo Vas da unesete pozitivan broj.");
                logger.error("Pogreška! Korisnik je upisao broj manji od 0 pri unosu cijene smještaja. " + exception);
                error = true;
            }
            inputScanner.nextLine();
        }
        while (error);
        BigDecimal accommodationDiscountBigDecimal=new BigDecimal(0);
        do
        {
            error=false;
            System.out.println("Unesite popust cijene "+(i+1)+". jednog noćenja: ");
            try
            {
                accommodationDiscountBigDecimal=inputScanner.nextBigDecimal();
                checkIfNumberIsValid(accommodationRegularPrice);
            }
            catch (InputMismatchException exception)
            {
                System.out.println("Molimo Vas da unesete broj.");
                logger.error("Pogreška! Korisnik nije upisao broj pri unosu popusta na cijenu noćenja. " + exception);
                error = true;
            }
            catch (ValidNumberException exception)
            {
                System.out.println("Molimo Vas da unesete pozitivan broj.");
                logger.error("Pogreška! Korisnik je upisao broj manji od 0 pri unosu popusta smještaja. " + exception);
                error = true;
            }
            inputScanner.nextLine();
        }
        while (error);
        Discount accommodationDiscount=new Discount(accommodationDiscountBigDecimal);
        BigDecimal accommodationReducedPrice=accommodationRegularPrice.subtract(accommodationRegularPrice.multiply(accommodationDiscountBigDecimal).multiply(new BigDecimal("0.01")));
        LocalDate checkInDate=LocalDate.now();
        do
        {
            error=false;
            System.out.println("Unesite datum dolaska "+(i+1)+". (dd.MM.gggg.):");
            try
            {
                String checkInDateString=inputScanner.nextLine();
                checkInDate = LocalDate.parse(checkInDateString, DATE_TIME_FORMAT);
                checkIfDateIsOlder(checkInDate);

            }
            catch (DateTimeParseException exception)
            {
                System.out.println("Molimo Vas da unesete datum prema zadanom obliku.");
                logger.error("Pogreška! Korisnik nije upisao datum prema uputama. " + exception);
                error = true;
            }
            catch (DateBeforeToday exception)
            {
                System.out.println("Molimo Vas da unesete datum koji se dogodio prije današnjeg.");
                logger.error("Pogreška! Korisnik nije upisao datum prije današnjeg. " + exception);
                error = true;
            }

        }
        while (error);
        LocalDate checkOutDate=LocalDate.now();
        do
        {
            error=false;
            System.out.println("Unesite datum odlaska "+(i+1)+". (dd.MM.gggg.):");
            try
            {
                String checkInDateString=inputScanner.nextLine();
                checkOutDate = LocalDate.parse(checkInDateString, DATE_TIME_FORMAT);
                checkIfDateIsOlder(checkOutDate);
            }
            catch (DateTimeParseException exception)
            {
                System.out.println("Molimo Vas da unesete datum prema zadanom obliku.");
                logger.error("Pogreška! Korisnik nije upisao datum prema uputama. " + exception);
                error = true;
            }
            catch (DateBeforeToday exception)
            {
                System.out.println("Molimo Vas da unesete datum koji se dogodio prije današnjeg.");
                logger.error("Pogreška! Korisnik nije upisao datum prije današnjeg. " + exception);
                error = true;
            }
        }
        while (error);
        System.out.println("Koliko putnika želite dodati (odaberite 0 za izlazak): "+passengers.size()+" je trenutno dostupno.");
        Passenger[] selectedPassengersArray=selectedPassengers(inputScanner,passengers);
        Set<Passenger> selectedPassengers=new HashSet<>(List.of(selectedPassengersArray));
        logger.info("Uspješno kreiran smještaj.");
        return new Accommodation(accommodationId,accommodationName,accommodationAddress,accommodationRegularPrice,accommodationDiscount,accommodationReducedPrice,checkInDate,checkOutDate,selectedPassengers);
    }
    private static List<Transport> getTransports(Scanner inputScanner,Set<Passenger> passengers)
    {
        boolean error;
        int numberOfTransports=0;
        do
        {
            error=false;
            System.out.println("Unesite broj prijevoza koje želite unijeti: ");
            try
            {
                numberOfTransports=inputScanner.nextInt();
                checkIfNumberIsValid(numberOfTransports);
            }
            catch (InputMismatchException exception)
            {
                System.out.println("Molimo Vas da unesete broj.");
                logger.error("Pogreška! Korisnik nije upisao broj pri odabiru broja prijevoza za unos. " + exception);
                error = true;
            }
            catch (ValidNumberException exception)
            {
                System.out.println("Molimo Vas da unesete pozitivan broj.");
                logger.error("Pogreška! Korisnik je upisao broj manji od 0 pri unosu broja transporta. " + exception);
                error = true;
            }
            inputScanner.nextLine();
        }
        while (error);
        List<Transport> transports=new ArrayList<>();
        for (int i=0;i<numberOfTransports;i++)
        {
            System.out.println("Unesite podatke za "+(i+1)+". izlet: ");
            enterTransport(i,inputScanner,transports,passengers);
        }
        logger.info("Uspješno kreirano polje izleta");
        return transports;
    }
    private static void enterTransport(int i, Scanner inputScanner,List<Transport> transports,Set<Passenger> passengers)
    {
        Long transportId=(long) i;
        System.out.println("Unesite naziv "+(i+1)+". prijevoza: ");
        String transportName=inputScanner.nextLine();
        TypeOfTransportation vehicle=getTypeOfTransportatio(inputScanner);
        System.out.println("Unesite polazište "+(i+1)+". prijevoza: ");
        City transportStartingPoint=getCity(inputScanner);
        System.out.println("Unesite odredište "+(i+1)+". prijevoza: ");
        City transportDestination=getCity(inputScanner);
        boolean error;
        LocalDate transportDate = LocalDate.now();
        do
        {
            error=false;
            System.out.println("Unesite datum "+(i+1)+". prijevoza (dd.MM.gggg.): ");
            try
            {
                String trasportDateString=inputScanner.nextLine();
                transportDate = LocalDate.parse(trasportDateString, DATE_TIME_FORMAT);
                checkIfDateIsOlder(transportDate);
            }
            catch (DateTimeParseException exception)
            {
                System.out.println("Molimo Vas da unesete datum prema zadanom obliku.");
                logger.error("Pogreška! Korisnik nije upisao datum prema uputama. " + exception);
                error = true;
            }
            catch (DateBeforeToday exception)
            {
                System.out.println("Molimo Vas da unesete datum koji se dogodio prije današnjeg.");
                logger.error("Pogreška! Korisnik nije upisao datum prije današnjeg. " + exception);
                error = true;
            }
        }
        while (error);
        BigDecimal transportRegularPrice=new BigDecimal(0);
        do
        {
            error=false;
            System.out.println("Unesite cijenu "+(i+1)+". prijevoza: ");
            try
            {
                transportRegularPrice=inputScanner.nextBigDecimal();
                checkIfNumberIsValid(transportRegularPrice);
            }
            catch (InputMismatchException exception)
            {
                System.out.println("Molimo Vas da unesete broj.");
                logger.error("Pogreška! Korisnik nije upisao broj pri unosu cijene prijevoza. " + exception);
                error = true;
            }
            catch (ValidNumberException exception)
            {
                System.out.println("Molimo Vas da unesete pozitivan broj.");
                logger.error("Pogreška! Korisnik je upisao broj manji od 0 pri unosu cijene prijevoza. " + exception);
                error = true;
            }
            inputScanner.nextLine();
        }
        while (error);
        BigDecimal transportDiscountBigDecimal=new BigDecimal(0);
        do
        {
            error=false;
            System.out.println("Unesite popust cijene "+(i+1)+". prijevoza: ");
            try
            {
                transportDiscountBigDecimal=inputScanner.nextBigDecimal();
                checkIfNumberIsValid(transportDiscountBigDecimal);
            }
            catch (InputMismatchException exception)
            {
                System.out.println("Molimo Vas da unesete broj.");
                logger.error("Pogreška! Korisnik nije upisao broj pri unosu popusta na cijenu prijevoza. " + exception);
                error = true;
            }
            catch (ValidNumberException exception)
            {
                System.out.println("Molimo Vas da unesete pozitivan broj.");
                logger.error("Pogreška! Korisnik je upisao broj manji od 0 pri unosu popusta prijevoza. " + exception);
                error = true;
            }
            inputScanner.nextLine();
        }
        while (error);
        Discount transportDiscount=new Discount(transportDiscountBigDecimal);
        BigDecimal transportReducedPrice=transportRegularPrice.subtract(transportRegularPrice.multiply(transportDiscountBigDecimal).multiply(new BigDecimal("0.01")));
        System.out.println("Koliko putnika želite dodati (odaberite 0 za izlazak): "+passengers.size()+" je trenutno dostupno.");
        Passenger[] selectedPassengersArray=selectedPassengers(inputScanner,passengers);
        Set<Passenger> selectedPassengers=new HashSet<>(List.of(selectedPassengersArray));
        transports.add(new Transport(transportId,transportName,vehicle,transportStartingPoint,transportDestination,transportDate,transportRegularPrice,transportDiscount,transportReducedPrice,selectedPassengers));
        logger.info("Uspješno kreiran prijevoz.");
    }
    private static List<Outing> getOutings(Scanner inputScanner,Set<Passenger> passengers)
    {
        boolean error;
        int numberOfOutings=0;
        do
        {
            error=false;
            System.out.println("Unesite broj izleta koje želite unijeti: ");
            try
            {
                numberOfOutings=inputScanner.nextInt();
                checkIfNumberIsValid(numberOfOutings);
            }
            catch (InputMismatchException exception)
            {
                System.out.println("Molimo Vas da unesete broj.");
                logger.error("Pogreška! Korisnik nije upisao broj pri odabiru broja izleta za unos. " + exception);
                error = true;
            }
            catch (ValidNumberException exception)
            {
                System.out.println("Molimo Vas da unesete pozitivan broj.");
                logger.error("Pogreška! Korisnik je upisao broj manji od 0 pri unosu broja izleta. " + exception);
                error = true;
            }
            inputScanner.nextLine();
        }
        while (error);
        List<Outing> outings=new ArrayList<>();
        for (int i=0;i<numberOfOutings;i++)
        {
            System.out.println("Unesite podatke za "+(i+1)+". izlet: ");
            enterOuting(i,inputScanner,outings,passengers);
        }
        logger.info("Uspješno kreirano polje izleta");
        return outings;
    }
    private static void enterOuting(int i, Scanner inputScanner, List<Outing> outings,Set<Passenger> passengers)
    {
        Long outingId=(long) i;
        System.out.println("Unesite naziv "+(i+1)+". izleta: ");
        String outingName=inputScanner.nextLine();
        System.out.println("Unesite opis "+(i+1)+". izleta: ");
        String outingDescripiton=inputScanner.nextLine();
        boolean error;
        BigDecimal outingRegularPrice=new BigDecimal(0);
        do
        {
            error=false;
            System.out.println("Unesite cijenu "+(i+1)+". izleta: ");
            try
            {
                outingRegularPrice=inputScanner.nextBigDecimal();
                checkIfNumberIsValid(outingRegularPrice);
            }
            catch (InputMismatchException exception)
            {
                System.out.println("Molimo Vas da unesete broj.");
                logger.error("Pogreška! Korisnik nije upisao broj pri unosu cijene izleta. " + exception);
                error = true;
            }
            catch (ValidNumberException exception)
            {
                System.out.println("Molimo Vas da unesete pozitivan broj.");
                logger.error("Pogreška! Korisnik je upisao broj manji od 0 pri unosu cijene izleta. " + exception);
                error = true;
            }
            inputScanner.nextLine();
        }
        while (error);
        BigDecimal outingDiscountBigDecimal=new BigDecimal(0);
        do
        {
            error=false;
            System.out.println("Unesite popust cijene "+(i+1)+". izleta: ");
            try
            {
                outingDiscountBigDecimal=inputScanner.nextBigDecimal();
                checkIfNumberIsValid(outingDiscountBigDecimal);
            }
            catch (InputMismatchException exception)
            {
                System.out.println("Molimo Vas da unesete broj.");
                logger.error("Pogreška! Korisnik nije upisao broj pri unosu popusta na cijenu izleta. " + exception);
                error = true;
            }
            catch (ValidNumberException exception)
            {
                System.out.println("Molimo Vas da unesete pozitivan broj.");
                logger.error("Pogreška! Korisnik je upisao broj manji od 0 pri unosu popusta izleta. " + exception);
                error = true;
            }
            inputScanner.nextLine();
        }
        while (error);
        Discount outingDiscount=new Discount(outingDiscountBigDecimal);
        BigDecimal outingReducedPrice=outingRegularPrice.subtract(outingRegularPrice.multiply(outingDiscountBigDecimal).multiply(new BigDecimal("0.01")));
        LocalDate outingDate = LocalDate.now();
        do
        {
            error=false;
            System.out.println("Unesite datum "+(i+1)+". izleta (dd.MM.gggg.): ");
            try
            {
                String outingDateString=inputScanner.nextLine();
                outingDate = LocalDate.parse(outingDateString, DATE_TIME_FORMAT);
                checkIfDateIsOlder(outingDate);
            }
            catch (DateTimeParseException exception)
            {
                System.out.println("Molimo Vas da unesete datum prema zadanom obliku.");
                logger.error("Pogreška! Korisnik nije upisao datum prema uputama. " + exception);
                error = true;
            }
            catch (DateBeforeToday exception)
            {
                System.out.println("Molimo Vas da unesete datum koji se dogodio prije današnjeg.");
                logger.error("Pogreška! Korisnik nije upisao datum prije današnjeg. " + exception);
                error = true;
            }
        }
        while (error);
        Address address=getAddress(inputScanner);
        System.out.println("Koliko putnika želite dodati (odaberite 0 za izlazak): "+passengers.size()+" je trenutno dostupno.");
        Passenger[] selectedPassengersArray=selectedPassengers(inputScanner,passengers);
        Set<Passenger> selectedPassengers=new HashSet<>(List.of(selectedPassengersArray));
        outings.add(new Outing(outingId,outingName,outingDescripiton,outingRegularPrice,outingDiscount,outingReducedPrice,outingDate,address,selectedPassengers));
        logger.info("Uspješno kreiran izlet.");
    }
    private static Passenger[] selectedPassengers(Scanner inputScanner,Set<Passenger> passengers)
    {
        int numberOfPassengers = 0;

        for(boolean validInput = false; !validInput; inputScanner.nextLine())
        {
            try
            {
                numberOfPassengers = inputScanner.nextInt();
                if (numberOfPassengers >= 0 && numberOfPassengers <= passengers.size())
                {
                    validInput = true;
                }
                else
                {
                    System.out.println("Krivi unos. Pokušajte ponovno.");
                }
            }
            catch (InputMismatchException exception)
            {
                System.out.println("Molimo Vas da unesete broj.");
                logger.error("Pogreška! Korisnik nije upisao broj pri odabiru broja putnika za unos. " + exception);
            }
        }

        Passenger[] selection = new Passenger[numberOfPassengers];
        if (numberOfPassengers == 0)
        {
            return selection;
        }
        else
        {
            System.out.println("Odaberite željene putnike:");

            int count=1;
            for(Passenger passenger:passengers)
            {
                System.out.println(count + ". " + passenger.getName());
                count++;
            }

            for (count = 0; count != numberOfPassengers; inputScanner.nextLine())
            {
                boolean error;
                int in = 0;
                do
                {
                    error=false;
                    try
                    {
                        in = inputScanner.nextInt();
                    }
                    catch (InputMismatchException exception)
                    {
                        System.out.println("Molimo Vas da unesete broj.");
                        logger.error("Pogreška! Korisnik nije upisao broj pri odabiru broja putnika za unos. " + exception);
                        error=true;
                    }
                }
                while (error);
                try {
                    if (in > 0 && in <= passengers.size()) {
                        boolean passengerAlreadySelected = false;
                        Passenger selected = null;

                        for (Passenger passenger : passengers)
                        {
                            if (in == 1) {
                                selected = passenger;
                                break;
                            }
                            in--;
                        }

                        for (int i = 0; i < count; ++i)
                        {
                            if (selection[i] == selected)
                            {
                                assert selected != null;
                                System.out.println("Putnik " + selected.getName() + " se već nalazi u odabiru. Molimo odaberite drugog putnika.");
                                passengerAlreadySelected = true;
                                break;
                            }
                        }

                        if (!passengerAlreadySelected)
                        {
                            selection[count++] = selected;
                        }
                    }
                    else
                    {
                        System.out.println("Krivi unos. Pokušajte ponovno.");
                    }
                }
                catch (InputMismatchException exception)
                {
                    System.out.println("Molimo Vas da unesete broj.");
                    logger.error("Pogreška! Korisnik nije upisao broj pri odabiru broja putnika za unos. " + exception);
                    inputScanner.nextLine();
                }
            }

            return selection;
        }
    }
    private static Set<Passenger> getPassengers(Scanner inputScanner)
    {
        boolean error;
        int numberOfPassengers=0;
        do
        {
            error=false;
            System.out.println("Unesite broj putnika koje želite unijeti: ");
            try
            {
                numberOfPassengers=inputScanner.nextInt();
                checkIfNumberIsValid(numberOfPassengers);
            }
            catch (InputMismatchException exception)
            {
                System.out.println("Molimo Vas da unesete broj.");
                logger.error("Pogreška! Korisnik nije upisao broj pri odabiru broja putnika za unos. " + exception);
                error = true;
            }
            catch (ValidNumberException exception)
            {
                System.out.println("Molimo Vas da unesete pozitivan broj.");
                logger.error("Pogreška! Korisnik je upisao broj manji od 0 pri unosu broja putnika. " + exception);
                error = true;
            }
            inputScanner.nextLine();
        }
        while (error);
        Set<Passenger> passengers=new HashSet<>();
        for (int i=0;i<numberOfPassengers;i++)
        {
            System.out.println("Unesite podatke za "+(i+1)+". putnika: ");
            enterPassenger(i,inputScanner,passengers);
        }
        logger.info("Uspješno kreiran set putnika");
        return passengers;
    }
    private static void enterPassenger(int i,Scanner inputScanner,Set<Passenger> passengers)
    {
        Long passengerId=(long) i;
        System.out.println("Unesite ime "+(i+1)+". putnika: ");
        String passengerName=inputScanner.nextLine();
        System.out.println("Unesite prezime "+(i+1)+". putnika: ");
        String passengerSurname=inputScanner.nextLine();
        System.out.println("Unesite mail adresu "+(i+1)+". putnika: ");
        String passengerEmail=inputScanner.nextLine();
        System.out.println("Unesite broj telefona "+(i+1)+". putnika");
        String passengerPhoneNumber=inputScanner.nextLine();
        boolean error;
        LocalDate passengerDateOfBirth=LocalDate.now();
        do
        {
            error=false;
            System.out.println("Unesite datum rođenja "+(i+1)+". putnika (dd.MM.gggg.):");
            try
            {
                String passengerDateOfBirthString=inputScanner.nextLine();
                passengerDateOfBirth = LocalDate.parse(passengerDateOfBirthString, DATE_TIME_FORMAT);
                checkIfDateIsYounger(passengerDateOfBirth);
            }
            catch (DateTimeParseException exception)
            {
                System.out.println("Molimo Vas da unesete datum prema zadanom obliku.");
                logger.error("Pogreška! Korisnik nije upisao datum prema uputama. " + exception);
                error = true;
            }
            catch (DateAfterToday exception)
            {
                System.out.println("Molimo Vas da unesete datum koji se dogodio nakon današnjeg.");
                logger.error("Pogreška! Korisnik nije upisao datum nakon današnjeg. " + exception);
                error = true;
            }
        }
        while (error);

        passengers.add(new Passenger(passengerId,passengerName,passengerSurname,passengerEmail,passengerPhoneNumber,passengerDateOfBirth));
        logger.info("Uspješno kreiran putnik.");
    }
    private static Address getAddress(Scanner inputScanner)
    {
            System.out.println("Molimo Vas da unesete potrebne podatke za adresu.");
            System.out.println("Ulica: ");
            String street = inputScanner.nextLine();
            System.out.println("Kucni broj: ");
            String houseNumber = inputScanner.nextLine();
            City city = getCity(inputScanner);
            logger.info("Uspješno kreirana adresa.");
            return (new AddressBuilder()).setStreet(street).setHouseNumber(houseNumber).setCity(city).setPostalCode(city.getPostalCode()).createAddress();
    }
    private static TypeOfTransportation getTypeOfTransportatio(Scanner inputScanner)
    {
        boolean error;
        TypeOfTransportation typeOfTransportation = TypeOfTransportation.PLANE;
        do
        {
            error=false;
            try
            {
                System.out.println("Ovo su svi dostupni načini prijevoza: ");
                TypeOfTransportation[] typesOfTransportation=TypeOfTransportation.values();
                for (int i=0;i<typesOfTransportation.length;i++)
                {
                    if (i!=(typesOfTransportation.length-1))
                        System.out.print(typesOfTransportation[i].getName()+", ");
                    else System.out.print(typesOfTransportation[i].getName());
                }
                System.out.println();
                System.out.println("Prijevoz: ");
                String string = inputScanner.nextLine();
                String typeOfTransportationString = string.toUpperCase();
                typeOfTransportation=TypeOfTransportation.valueOf(typeOfTransportationString);
            }
            catch (IllegalArgumentException exception)
            {
                System.out.println("Molimo Vas da unesete dostupan način prijevoza koji postoji u enumeraciji.");
                logger.error("Pogreška! Korisnik nije upisao način prijevoza iz enumeracija. " + exception);
                error=true;
            }
        }
        while (error);
        return typeOfTransportation;
    }
    private static City getCity(Scanner inputScanner)
    {
        boolean error;
        City city = City.ZAGREB;
        do
        {
            error=false;
            try
            {
                System.out.println("Ovo su svi dostupni gradovi: ");
                City[] cities=City.values();
                for (int i=0;i<cities.length;i++)
                {
                    if (i!=(cities.length-1))
                    System.out.print(cities[i].getName()+", ");
                    else System.out.print(cities[i].getName());
                }
                System.out.println();
                System.out.println("Grad: ");
                String string = inputScanner.nextLine();
                String cityString = string.toUpperCase();
                city=City.valueOf(cityString);
            }
            catch (IllegalArgumentException exception)
            {
                System.out.println("Molimo Vas da unesete grad koji postoji u enumeraciji.");
                logger.error("Pogreška! Korisnik nije upisao grad iz enumeracija. " + exception);
                error=true;
            }
        }
        while (error);
        return city;
    }

    private static void outputPassangers(Set<Passenger> passengers)
    {
        for (Passenger passenger:passengers)
        {
            System.out.println("ID: "+passenger.getId()+", Ime: "+passenger.getName()+", Prezime: "+passenger.getSurname()+", Email: "+passenger.getEmail()+", Broj telefona: "+ passenger.getPhoneNumber()+", Datum rođenja: "+passenger.getDateOfBirth().format(DATE_TIME_FORMAT));
        }
    }
    private static void outputOutings(List<Outing> outings)
    {
        for (Outing outing:outings)
        {
            System.out.println("ID: "+outing.getId()+", Naziv: "+outing.getName()+", Opis: "+outing.getDescription()+ ", Redovna cijena: "+outing.getRegularPrice()+", Popust: "+outing.getDiscount()+", Akcijska cijena: "+outing.getReducedPrice()+", Datum: "+outing.getDate().format(DATE_TIME_FORMAT)+", Adresa: "+outing.getAddress().getStreet()+" "+outing.getAddress().getHouseNumber()+" "+outing.getAddress().getCity()+" "+outing.getAddress().getPostalCode());
            for (Passenger passenger:outing.getPassengers())
            {
                System.out.println(passenger.getName() +" ");
            }
        }
    }
    private static void outputTransportations(List<Transport> transports)
    {
        for (Transport transport:transports)
        {
            System.out.println("ID: "+transport.getId()+", Naziv: "+transport.getName()+", Prijevozno sredstvo: "+transport.getVehicle().getName()+ ", Izvorišni grad: "+transport.getStartingPoint().getName()+", Odredište: "+transport.getDestination().getName()+", Datum: "+transport.getDate().format(DATE_TIME_FORMAT)+", Redovna cijena: "+transport.getRegularPrice()+", Popust: "+transport.getDiscount()+", Akcijska cijena: "+transport.getReducedPrice());
            for (Passenger passenger:transport.getPassengers())
            {
                System.out.println(passenger.getName() +" ");
            }
        }
    }
    private static void outputAccommodations(List<Accommodation> accommodations)
    {
        for (Accommodation accommodation:accommodations) {
            System.out.println("ID: " + accommodation.getId() + ", Naziv: " + accommodation.getName() + ", Redovna cijena: " + accommodation.getRegularPricePerNight() + ", Popust: " + accommodation.getDiscount() + ", Akcijska cijena: " + accommodation.getReducedPricePerNight() + ", Datum dolaska: " + accommodation.getCheckInDate().format(DATE_TIME_FORMAT) + ", Datum odlaska: " + accommodation.getCheckOutDate().format(DATE_TIME_FORMAT));
            for (Passenger passenger : accommodation.getPassengers())
            {
                System.out.println(passenger.getName() + " ");
            }
            if (accommodation instanceof Hostel roomAccommodation)
            {
                System.out.println("Broj osoba u sobi: "+roomAccommodation.getNumberOfPassengersPerRoom()+", Ukupna cijena za noćenje: "+roomAccommodation.calculatePricePerNight(roomAccommodation.getPassengers())+", Ukupna cijena za sva noćenja: "+roomAccommodation.calculateFinalPrice());
            }
            if (accommodation instanceof Hotel roomAccommodation)
            {
                System.out.println("Broj osoba u sobi: "+roomAccommodation.getNumberOfPassengersPerRoom()+", Ukupna cijena za noćenje: "+roomAccommodation.calculatePricePerNight(roomAccommodation.getPassengers())+", Ukupna cijena za sva noćenja: "+roomAccommodation.calculateFinalPrice());
            }
            if (accommodation instanceof Camp roomAccommodation)
            {
                System.out.println(roomAccommodation.getArea());
            }

        }
    }
}