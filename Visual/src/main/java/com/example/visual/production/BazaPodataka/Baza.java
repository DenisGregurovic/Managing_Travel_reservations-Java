package com.example.visual.production.BazaPodataka;

import com.example.visual.KorisnikIzbornikController;
import com.example.visual.Pokreni;
import com.example.visual.production.Datoteke.Datoteka;
import com.example.visual.production.Entiteti.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Baza
{
    private static final String DATOTEKA_BAZE = "BazaPostavke/Baza.properties";
    private static final Logger logger = LoggerFactory.getLogger(Pokreni.class);
    private static Connection connectToDatabase() throws SQLException, IOException
    {
            Properties svojstva = new Properties();
            svojstva.load(new FileReader(DATOTEKA_BAZE));
            String urlBazePodataka = svojstva.getProperty("bazaUrl");
            String korisnickoIme = svojstva.getProperty("korisnickoIme");
            String lozinka = svojstva.getProperty("lozinka");
            Connection veza = DriverManager.getConnection(urlBazePodataka, korisnickoIme, lozinka);
            return veza;
    }
    public static ObservableList<Zaposlenik> dohvatiZaposlenike()
    {
        ObservableList<Zaposlenik> lista = FXCollections.observableArrayList();
        try (Connection connection = connectToDatabase())
        {
            String upit = "SELECT * FROM ZAPOSLENIK";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(upit);
            while (resultSet.next())
            {
                Long id = resultSet.getLong("ID");
                String ime = resultSet.getString("IME");
                String prezime = resultSet.getString("PREZIME");
                String korisnickoIme = resultSet.getString("KORISNICKO_IME");
                String lozinka=Metode.vratiLozinkuZaposlenika(korisnickoIme);
                LocalDate datumRodenja = resultSet.getDate("DATUM_RODENJA").toLocalDate();
                String ulogaString=resultSet.getString("ULOGA");
                Uloga uloga=Metode.vratiUlogu(ulogaString);
                lista.add(new ZaposlenikBuilder().setId(id).setIme(ime).setLozinka(lozinka).setPrezime(prezime).setKorisnikoIme(korisnickoIme).setDatumRodjenja(datumRodenja).setUloga(uloga).createZaposlenik());
            }
        }
        catch (SQLException | IOException iznimka)
        {
            String poruka="Ne mogu se ucitati podaci iz baze o zaposlenicima!";
            System.out.println(poruka);
            logger.error(poruka+ iznimka);
        }
        return lista;
    }
    public static void obrisiZaposlenika(Zaposlenik zaposlenik)
    {
        try (Connection connection = connectToDatabase())
        {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ZAPOSLENIK WHERE ID=?;");
            preparedStatement.setLong(1,zaposlenik.getId());
            preparedStatement.execute();
            logger.info("Uspješno izbrisan zaposlenik.");
        }
        catch (SQLException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public static void spremiZaposlenika(Zaposlenik zaposlenik)
    {

        try (Connection connection = connectToDatabase())
        {
            String insertCarSql = "INSERT INTO ZAPOSLENIK(IME, PREZIME, KORISNICKO_IME, DATUM_RODENJA, ULOGA) VALUES(?, ?, ?, ?, ?);";
            PreparedStatement pstmt = connection.prepareStatement(insertCarSql);
            pstmt.setString(1, zaposlenik.getIme());
            pstmt.setString(2, zaposlenik.getPrezime());
            pstmt.setString(3, zaposlenik.getKorisnikoIme());
            pstmt.setDate(4, new Date(zaposlenik.getDatumRodjenja().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            pstmt.setString(5, zaposlenik.getUloga().getOpis());
            pstmt.execute();
            logger.info("Usješno spremljen zaposlenik.");
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public static void azurirajZaposlenika(Zaposlenik zaposlenik)
    {
        try (Connection connection = connectToDatabase())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE ZAPOSLENIK " +
                            "SET " +
                            " IME = ?, " +
                            " PREZIME = ?, " +
                            " KORISNICKO_IME = ?, " +
                            " DATUM_RODENJA = ?, " +
                            " ULOGA = ? " +
                            "WHERE ID = ?;"
            );
            preparedStatement.setString(1, zaposlenik.getIme());
            preparedStatement.setString(2, zaposlenik.getPrezime());
            preparedStatement.setString(3, zaposlenik.getKorisnikoIme());
            preparedStatement.setDate(4,  new Date(zaposlenik.getDatumRodjenja().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            preparedStatement.setString(5, zaposlenik.getUloga().getOpis());
            preparedStatement.setLong(6, zaposlenik.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0)
            {
                System.out.println("Uspešno izvršen UPDATE.");
                logger.info("Uspešno izvršen UPDATE zaposlenika.");
            }
            else
            {
                System.out.println("Nije pronađen zapis za ažuriranje.");
                logger.info("Nije pronađen zapis za ažuriranje zaposlenika.");
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public static ObservableList<Korisnik> dohvatiKorisnike()
    {
        ObservableList<Korisnik> lista = FXCollections.observableArrayList();
        try (Connection connection = connectToDatabase())
        {
            String upit = "SELECT * FROM KORISNIK";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(upit);
            while (resultSet.next())
            {
                Long id = resultSet.getLong("ID");
                String ime = resultSet.getString("IME");
                String prezime = resultSet.getString("PREZIME");
                String korisnickoIme = resultSet.getString("KORISNICKO_IME");
                String lozinka=Metode.vratiLozinkuKlijenta(korisnickoIme);
                LocalDate datumRodenja = resultSet.getDate("DATUM_RODENJA").toLocalDate();
                String adresa=resultSet.getString("ADRESA");
                lista.add(new KorisnikBuilder().setId(id).setIme(ime).setLozinka(lozinka).setPrezime(prezime).setKorisnikoIme(korisnickoIme).setDatumRodjenja(datumRodenja).setAdresa(adresa).createKorisnik());
            }
            logger.info("Uspješno učitani korisnici iz baze.");
        }
        catch (SQLException | IOException iznimka)
        {
            String poruka="Ne mogu se ucitati podaci iz baze o korisnicima!";
            System.out.println(poruka);
            logger.error(poruka+ iznimka);
        }
        return lista;
    }
    public static void obrisiKorisnika(Korisnik korisnik)
    {
        try (Connection connection = connectToDatabase())
        {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM KORISNIK WHERE ID=?;");
            preparedStatement.setLong(1, korisnik.getId());
            Datoteka.obrisiKorisnika(korisnik);
            preparedStatement.execute();
            logger.info("Uspješno izbrisan korisnik.");
        }
        catch (SQLException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public static void spremiKorisnika(Korisnik korisnik)
    {
        try (Connection connection = connectToDatabase())
        {
            String insertCarSql = "INSERT INTO KORISNIK(IME, PREZIME, KORISNICKO_IME, DATUM_RODENJA, ADRESA) VALUES(?, ?, ?, ?, ?);";
            PreparedStatement pstmt = connection.prepareStatement(insertCarSql);
            pstmt.setString(1, korisnik.getIme());
            pstmt.setString(2, korisnik.getPrezime());
            pstmt.setString(3, korisnik.getKorisnikoIme());
            pstmt.setDate(4, new Date(korisnik.getDatumRodjenja().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            pstmt.setString(5, korisnik.getAdresa());
            pstmt.execute();
            logger.info("Uspješno spremljen korisnik.");
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public static void azurirajKorisnika(Korisnik korisnik)
    {
        try (Connection connection = connectToDatabase())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE KORISNIK " +
                            " SET " +
                            " IME = ?, " +
                            " PREZIME = ?, " +
                            " KORISNICKO_IME = ?, " +
                            " DATUM_RODENJA = ?, " +
                            " ADRESA = ? "  +
                            " WHERE ID = ?;"
            );
            preparedStatement.setString(1, korisnik.getIme());
            preparedStatement.setString(2, korisnik.getPrezime());
            preparedStatement.setString(3, korisnik.getKorisnikoIme());
            preparedStatement.setDate(4,  new Date(korisnik.getDatumRodjenja().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            preparedStatement.setString(5, korisnik.getAdresa());
            preparedStatement.setLong(6, korisnik.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0)
            {
                System.out.println("Uspešno izvršen UPDATE.");
                logger.info("Uspešno izvršen UPDATE korisnika.");
            }
            else
            {
                System.out.println("Nije pronađen zapis za ažuriranje.");
                logger.error("Nije moguće ažurirati korisnika.");
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public static ObservableList<Dogadaj> dohvatiDogadaje()
    {
        ObservableList<Dogadaj> lista = FXCollections.observableArrayList();
        try (Connection connection = connectToDatabase())
        {
            String upit = "SELECT * FROM DOGADAJ";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(upit);
            while (resultSet.next())
            {
                Long id = resultSet.getLong("ID");
                String naziv = resultSet.getString("NAZIV");
                String opis=resultSet.getString("OPIS");
                Integer kolicina = resultSet.getInt("KOLICINA");
                BigDecimal cijena = resultSet.getBigDecimal("CIJENA");
                LocalDate datum = resultSet.getDate("DATUM").toLocalDate();
                lista.add(new DogadajBuilder().setId(id).setNaziv(naziv).setOpis(opis).setKolicina(kolicina).setCijena(cijena).setDatumDogadaja(datum).createDogadaj());
                logger.info("Uspješno učitani događaji iz baze.");
            }
        }
        catch (SQLException | IOException iznimka)
        {
            String poruka="Ne mogu se ucitati podaci iz baze o dogadajima!";
            System.out.println(poruka);
            logger.error(poruka+ iznimka);
        }
        return lista;
    }
    public static void obrisiDogadaj(Dogadaj dogadaj)
    {
        try (Connection connection = connectToDatabase())
        {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM DOGADAJ WHERE ID=?;");
            preparedStatement.setLong(1, dogadaj.getId());
            preparedStatement.execute();
            logger.info("Uspješno je izbrisan događaj.");
        }
        catch (SQLException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public static void spremiDogadaj(Dogadaj dogadaj)
    {

        try (Connection connection = connectToDatabase())
        {
            String insertCarSql = "INSERT INTO DOGADAJ(NAZIV, DATUM, OPIS, CIJENA, KOLICINA) VALUES(?, ?, ?, ?, ?);";
            PreparedStatement pstmt = connection.prepareStatement(insertCarSql);
            pstmt.setString(1, dogadaj.getNaziv());
            pstmt.setDate(2, new Date(dogadaj.getDatumDogadaja().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            pstmt.setString(3, dogadaj.getOpis());
            pstmt.setBigDecimal(4,dogadaj.getCijena());
            pstmt.setInt(5, dogadaj.getKolicina());
            pstmt.execute();
            logger.info("Uspješno je spremljen događaj u bazu.");
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        catch
        (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public static void azurirajDogadaj(Dogadaj dogadaj)
    {
        try (Connection connection = connectToDatabase())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE DOGADAJ SET " +
                            " NAZIV = ?, " +
                            " DATUM = ?, " +
                            " OPIS = ?, " +
                            " CIJENA = ? ," +
                            " KOLICINA = ? " +
                            " WHERE ID = ?;"
            );
            preparedStatement.setString(1, dogadaj.getNaziv());
            preparedStatement.setDate(2, new Date(dogadaj.getDatumDogadaja().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            preparedStatement.setString(3, dogadaj.getOpis());
            preparedStatement.setBigDecimal(4,dogadaj.getCijena());
            preparedStatement.setInt(5, dogadaj.getKolicina());
            preparedStatement.setLong(6, dogadaj.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0)
            {
                System.out.println("Uspešno izvršen UPDATE.");
                logger.info("Uspješno je izvršen UPDATE događaja.");
            }
            else
            {
                System.out.println("Nije pronađen zapis za ažuriranje.");
                logger.error("Nije pronađen zapis događaja koji je moguće ažurirati.");
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public static ObservableList<Smjestaj> dohvatiSmjestaje()
    {
        ObservableList<Smjestaj> lista = FXCollections.observableArrayList();
        try (Connection connection = connectToDatabase())
        {
            String upit = "SELECT * FROM SMJESTAJ ";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(upit);
            while (resultSet.next())
            {
                Long id = resultSet.getLong("ID");
                String naziv = resultSet.getString("NAZIV");
                String adresa=resultSet.getString("ADRESA");
                String vrstaString=resultSet.getString("VRSTA");
                Vrsta vrsta=Metode.vratiVrstu(vrstaString);
                BigDecimal redovnaCijena = resultSet.getBigDecimal("REDOVNA_CIJENA");
                BigDecimal akcijskaCijena = resultSet.getBigDecimal("SNIZENA_CIJENA");
                BigDecimal iznosPopusta=resultSet.getBigDecimal("POPUST");
                Popust popust=new Popust(iznosPopusta);
                lista.add(new SmjestajBuilder().setId(id).setNaziv(naziv).setAdresa(adresa).setRegularnaCijena(redovnaCijena).setPopust(popust).setSnizenaCijena(akcijskaCijena).setVrstaSmjestaja(vrsta).createSmjestaj());
            }
            logger.info("Uspješno učitani smještaji iz baze.");
        }
        catch (SQLException | IOException iznimka)
        {
            String poruka="Ne mogu se ucitati podaci iz baze o smjestajima!";
            System.out.println(poruka);
            logger.error(poruka+ iznimka);
        }
        return lista;
    }
    public static void obrisiSmjestaj(Smjestaj smjestaj)
    {
        try (Connection connection = connectToDatabase())
        {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM SMJESTAJ WHERE ID=?;");
            preparedStatement.setLong(1, smjestaj.getId());
            preparedStatement.execute();
            logger.info("Uspješno je obrisan smještaj iz baze.");
        }
        catch (SQLException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public static void spremiSmjestaj(Smjestaj smjestaj)
    {

        try (Connection connection = connectToDatabase())
        {

            String insertCarSql = "INSERT INTO SMJESTAJ(NAZIV, ADRESA, VRSTA, REDOVNA_CIJENA,  POPUST, SNIZENA_CIJENA) VALUES(?, ?, ?, ?, ?, ?);";
            PreparedStatement pstmt = connection.prepareStatement(insertCarSql);
            pstmt.setString(1, smjestaj.getNaziv());
            pstmt.setString(2, smjestaj.getAdresa());
            pstmt.setString(3,smjestaj.getVrstaSmjestaja().getOpis());
            pstmt.setBigDecimal(4,smjestaj.getRegularnaCijena());
            pstmt.setBigDecimal(5,smjestaj.getPopust().iznos());
            pstmt.setBigDecimal(6,smjestaj.getSnizenaCijena());
            pstmt.execute();
            logger.info("Uspješno je spremljen smještaj u bazu.");
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public static void azurirajSmjestaj(Smjestaj smjestaj)
    {
        try (Connection connection = connectToDatabase())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE SMJESTAJ " +
                            "SET " +
                            " NAZIV = ?," +
                            " ADRESA = ?," +
                            " VRSTA = ?," +
                            " REDOVNA_CIJENA = ?," +
                            " POPUST = ?, " +
                            " SNIZENA_CIJENA = ? " +
                            " WHERE ID = ?;"
            );
            preparedStatement.setString(1, smjestaj.getNaziv());
            preparedStatement.setString(2, smjestaj.getAdresa());
            preparedStatement.setString(3,smjestaj.getVrstaSmjestaja().getOpis());
            preparedStatement.setBigDecimal(4,smjestaj.getRegularnaCijena());
            preparedStatement.setBigDecimal(5,smjestaj.getPopust().iznos());
            preparedStatement.setBigDecimal(6,smjestaj.getSnizenaCijena());
            preparedStatement.setLong(7, smjestaj.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0)
            {
                System.out.println("Uspešno izvršen UPDATE.");
                logger.info("Uspješno izvršen UPDATE smještaja.");
            }
            else
            {
                System.out.println("Nije pronađen zapis za ažuriranje.");
                logger.error("Nije pronađen zapis za ažuriranje smještaja.");
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}

