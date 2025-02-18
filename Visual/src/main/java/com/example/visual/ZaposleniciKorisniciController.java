package com.example.visual;

import com.example.visual.production.BazaPodataka.Baza;
import com.example.visual.production.Datoteke.Datoteka;
import com.example.visual.production.Entiteti.*;
import com.example.visual.production.Iznimke.Oznacene.KorisnickoImePostojiException;
import com.example.visual.production.Iznimke.Neoznacene.BuduciDatumException;
import com.example.visual.production.Konstante.Konstante;
import com.example.visual.production.Niti.*;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ZaposleniciKorisniciController
{
    @FXML
    private TextField imeTextField;
    @FXML
    private TextField prezimeTextField;
    @FXML
    private  TextField adresaTextField;
    @FXML
    private TextField korisnickoImeTextField;
    @FXML
    private TextField pretrazivanjeTextField;
    @FXML
    private PasswordField lozinkaPasswordField;
    @FXML
    private DatePicker datumRodenjaDatePicker;
    @FXML
    private Button noviButton;
    @FXML
    private Button dodajButton;
    @FXML
    private Button azurirajButton;
    @FXML
    private Button brisiButton;
    @FXML
    private TableView<Korisnik> korisnikTableView;
    @FXML
    private TableColumn<Korisnik,String> korisnikIDTableColumn;
    @FXML
    private TableColumn<Korisnik,String> korisnikImeTableColumn;
    @FXML
    private TableColumn<Korisnik,String> korisnikPrezimeTableColumn;
    @FXML
    private TableColumn<Korisnik, String> korisnikKorisnickoImeTableColumn;
    @FXML
    private TableColumn<Korisnik, String> korisnikDatumRodenjaImeTableColumn;
    @FXML
    private TableColumn<Korisnik, String> korisnikAdresaTableColumn;
    Zaposlenik aktivniZaposlenik= new Serijalizacija<Zaposlenik>().ucitajZaposlenickiRacun("Datoteke/AktivniKorisnik.dat");
    private Korisnik korisnik;
    private static final Logger logger = LoggerFactory.getLogger(Pokreni.class);
    public void initialize()
    {
        korisnikIDTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Korisnik, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Korisnik, String> param)
            {
                return new ReadOnlyStringWrapper(param.getValue().getId().toString());
            }
        });
        korisnikImeTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Korisnik, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Korisnik, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getIme());
            }
        });
        korisnikPrezimeTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Korisnik, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Korisnik, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getPrezime());
            }
        });
        korisnikKorisnickoImeTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Korisnik, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Korisnik, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getKorisnikoIme());
            }
        });
        korisnikDatumRodenjaImeTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Korisnik,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Korisnik, String> param) {
                LocalDate datumRodenja = param.getValue().getDatumRodjenja();
                String datumRodenjaString = datumRodenja.format(Konstante.DATE_TIME_FORMAT);
                return new ReadOnlyStringWrapper(datumRodenjaString);
            }
        });
        korisnikAdresaTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Korisnik, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Korisnik, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getAdresa());
            }
        });
        ObservableList<Korisnik> korisnici= Baza.dohvatiKorisnike();
        UcitajKorisnikeThread dohvacanjeNit = new UcitajKorisnikeThread(korisnici);
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(dohvacanjeNit, 0, 1, TimeUnit.SECONDS);
        korisnikTableView.setItems(korisnici);
        azurirajButton.setDisable(true);
        brisiButton.setDisable(true);
        noviButton.setDisable(false);
        dodajButton.setDisable(true);
        pretrazivanjeTextField.textProperty().addListener((ObservableList,oldValue,newValue) -> {pretrazi(newValue);});
        Zaposlenik aktivniZaposlenik= new Serijalizacija<Zaposlenik>().ucitajZaposlenickiRacun("Datoteke/AktivniKorisnik.dat");
    }
    public void azuriraj()
    {
        Korisnik stariKorisnik=this.korisnik;
        if (imeTextField.getText().isEmpty() || datumRodenjaDatePicker.getValue() == null || prezimeTextField.getText().isEmpty() || korisnickoImeTextField.getText().isEmpty() || lozinkaPasswordField.getText().isEmpty()|| adresaTextField.getText().isEmpty())
        {
            upozorenje();
        }
        else
        {
            try
            {
                String ime = imeTextField.getText();
                String prezime = prezimeTextField.getText();
                String korisnickoIme = korisnickoImeTextField.getText();
                String lozinka = lozinkaPasswordField.getText();
                LocalDate datum = datumRodenjaDatePicker.getValue();
                String adresa = adresaTextField.getText();
                Metode.provjeriDatumRodenja(datum);
                Korisnik korisnik = new Korisnik(this.korisnik.getId(), ime, prezime,korisnickoIme,lozinka,datum, adresa);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Confirmation");
                dialog.setHeaderText("Jesi siguran");
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.setContentText("Stari korisnika: " + "\n-Ime: "+stariKorisnik.getIme() + "\n-Prezime: " + stariKorisnik.getPrezime()+"\n-Korisničko ime: "+stariKorisnik.getKorisnikoIme()+"\n-Datum rođenja: "+stariKorisnik.getDatumRodjenja().format(Konstante.DATE_TIME_FORMAT)+"\n-Adresa: "+stariKorisnik.getAdresa()
                +"\nNovi korisnika: " + "\n-Ime: "+korisnik.getIme() + "\n-Prezime: " + korisnik.getPrezime()+"\n-Korisničko ime: "+korisnik.getKorisnikoIme()+"\n-Datum rođenja: "+korisnik.getDatumRodjenja().format(Konstante.DATE_TIME_FORMAT)+"\n-Adresa: "+korisnik.getAdresa());
                ButtonType ok = new ButtonType("Da", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("Ne", ButtonBar.ButtonData.CANCEL_CLOSE);
                dialog.getDialogPane().getButtonTypes().addAll(ok, no);
                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get().equals(ok))
                {
                    AzurirajKorisnikeUBaziThread thread = new AzurirajKorisnikeUBaziThread(korisnik);
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(thread);
                    Platform.runLater(() -> {
                        Korisnik upit=Metode.ispisiKorisnika(korisnik);
                        AzurirajKorisnikeUDatoteciThread thread1 = new AzurirajKorisnikeUDatoteciThread(upit);
                        Executor executor1 = Executors.newSingleThreadExecutor();
                        executor1.execute(thread1);
                    });
                    Serijalizacija<Promjena<Korisnik, Zaposlenik>> promjenaSerijalizacija = new Serijalizacija();
                    Promjena<Korisnik, Zaposlenik> promjena = (new PromjenaBuilder()).setPocetni(stariKorisnik).setKrajnji(korisnik).setUloga(aktivniZaposlenik).setVrijeme(LocalDateTime.now()).createPromjena();
                    promjenaSerijalizacija.serializiraj(promjena, "Datoteke/korisnikPromjene.dat");
                }
            }
            catch (BuduciDatumException iznimka)
            {
                logger.error("Došlo je do greška pri unosu datuma "+iznimka);
            }
            catch (InputMismatchException iznimka)
            {
                logger.error("Došlo je do greška "+iznimka);
            }
            catch (Exception iznimka)
            {
                logger.error("Došlo je do greške "+iznimka);
            }
        }
        ocisti();
    }
    public void dodaj()
    {
        if (imeTextField.getText().isEmpty() || datumRodenjaDatePicker.getValue() == null || prezimeTextField.getText().isEmpty() || korisnickoImeTextField.getText().isEmpty() || lozinkaPasswordField.getText().isEmpty()|| adresaTextField.getText().isEmpty())
        {
            upozorenje();
        }
        else
        {
            try
            {
                String ime = imeTextField.getText();
                String prezime = prezimeTextField.getText();
                String korisnickoIme = korisnickoImeTextField.getText();
                String lozinka = lozinkaPasswordField.getText();
                LocalDate datum = datumRodenjaDatePicker.getValue();
                String adresa = adresaTextField.getText();
                Metode.provjeriDatumRodenja(datum);
                Korisnik korisnik = new Korisnik(Datoteka.dohvatiIduciID(), ime, prezime,korisnickoIme,lozinka,datum, adresa);
                Metode.ProvjeriKorisnickoImeKorisnika(korisnik);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Confirmation");
                dialog.setHeaderText("Jesi siguran");
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.setContentText("Kreiranje korisnika: " + "\n-Ime: "+korisnik.getIme() + "\n-Prezime: " + korisnik.getPrezime()+"\n-Korisničko ime: "+korisnik.getKorisnikoIme()+"\n-Datum rođenja: "+korisnik.getDatumRodjenja().format(Konstante.DATE_TIME_FORMAT)+"\n-Adresa: "+korisnik.getAdresa());
                ButtonType ok = new ButtonType("Da", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("Ne", ButtonBar.ButtonData.CANCEL_CLOSE);
                dialog.getDialogPane().getButtonTypes().addAll(ok, no);
                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get().equals(ok))
                {
                    SpremiKorisnikeUBazuThread thread = new SpremiKorisnikeUBazuThread(korisnik);
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(thread);
                    Platform.runLater(() -> {
                        korisnik.setId(Metode.zadnjiIDKorisnika());
                        SpremiKorisnikaUDatotekuThread thread1 = new SpremiKorisnikaUDatotekuThread(korisnik);
                        Executor executor1 = Executors.newSingleThreadExecutor();
                        executor1.execute(thread1);
                    });
                    Serijalizacija<Promjena<Korisnik, Zaposlenik>> promjenaSerijalizacija = new Serijalizacija();
                    Promjena<Korisnik, Zaposlenik> promjena = (new PromjenaBuilder()).setPocetni((Object) null).setKrajnji(korisnik).setUloga(aktivniZaposlenik).setVrijeme(LocalDateTime.now()).createPromjena();
                    promjenaSerijalizacija.serializiraj(promjena, "Datoteke/korisnikPromjene.dat");
                }
            }
            catch (BuduciDatumException iznimka)
            {
                logger.error("Došlo je do greške pri unosu datuma "+iznimka);
            }
            catch (InputMismatchException iznimka)
            {
                logger.error("Došlo je do greška pri formatu "+iznimka);
            }
            catch (KorisnickoImePostojiException iznimka)
            {
                logger.error("Došlo je do greške pri korisničkom imenu "+iznimka);
            }
            catch (Exception iznimka)
            {
                logger.error("Došlo je do greške "+iznimka);
            }
        }
        ocisti();
    }

    public void obrisi()
    {
        if (imeTextField.getText().isEmpty() || datumRodenjaDatePicker.getValue() == null || prezimeTextField.getText().isEmpty() || korisnickoImeTextField.getText().isEmpty() || lozinkaPasswordField.getText().isEmpty()|| adresaTextField.getText().isEmpty())
        {
            upozorenje();
        }
        else
        {
            try
            {
                String ime = imeTextField.getText();
                String prezime = prezimeTextField.getText();
                String korisnickoIme = korisnickoImeTextField.getText();
                String lozinka = lozinkaPasswordField.getText();
                LocalDate datum = datumRodenjaDatePicker.getValue();
                String adresa = adresaTextField.getText();
                Metode.provjeriDatumRodenja(datum);
                Korisnik korisnik = new Korisnik(this.korisnik.getId(), ime, prezime,korisnickoIme,lozinka,datum, adresa);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Confirmation");
                dialog.setHeaderText("Jesi siguran");
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.setContentText("Brisanje korisnika: " + "\n-Ime: "+korisnik.getIme() + "\n-Prezime: " + korisnik.getPrezime()+"\n-Korisničko ime: "+korisnik.getKorisnikoIme()+"\n-Datum rođenja: "+korisnik.getDatumRodjenja().format(Konstante.DATE_TIME_FORMAT)+"\n-Adresa: "+korisnik.getAdresa());
                ButtonType ok = new ButtonType("Da", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("Ne", ButtonBar.ButtonData.CANCEL_CLOSE);
                dialog.getDialogPane().getButtonTypes().addAll(ok, no);
                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get().equals(ok))
                {
                    BrisiKorisnikeIzBazeThread thread = new BrisiKorisnikeIzBazeThread(korisnik);
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(thread);
                    Platform.runLater(() -> {
                        BrisiKorisnikeIzDatotekeThread thread1 = new BrisiKorisnikeIzDatotekeThread(korisnik);
                        Executor executor1 = Executors.newSingleThreadExecutor();
                        executor1.execute(thread1);
                    });
                }
            }
            catch (BuduciDatumException iznimka)
            {
                logger.error("Došlo je do greška pri unosu datuma "+iznimka);
            }
            catch (InputMismatchException iznimka)
            {
                logger.error("Došlo je do greška pri formatu "+iznimka);
            }
            catch (Exception iznimka)
            {
                logger.error("Došlo je do greške "+iznimka);
            }
        }
        ocisti();
    }

    private void upozorenje() {
        String poruka = "Sljedeća polja su prazna:";
        if (imeTextField.getText().isEmpty())
        {
            poruka = poruka + "\n- Ime korisnika";
        }
        if (datumRodenjaDatePicker.getValue() == null)
        {
            poruka = poruka + "\n- Datum rođenja";
        }
        if (prezimeTextField.getText().isEmpty())
        {
            poruka = poruka + "\n- Prezime korisnika";
        }
        if (korisnickoImeTextField.getText().isEmpty())
        {
            poruka = poruka + "\n- Korisničko ime";
        }
        if (lozinkaPasswordField.getText().isEmpty())
        {
            poruka = poruka + "\n- Lozinka";
        }
        if (adresaTextField.getText().isEmpty())
        {
            poruka = poruka + "\n- Adresa";
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greška u unosu");
        alert.setHeaderText("Molimo unesite sve potrebne informacije");
        alert.setContentText(poruka);
        alert.showAndWait();
    }

    public void mouseClicked()
    {
        try
        {
            Korisnik korisnik=korisnikTableView.getSelectionModel().getSelectedItem();
            String lozinka=Metode.vratiLozinkuKlijenta(korisnik.getKorisnikoIme());
            korisnik=new Korisnik(korisnik.getId(),korisnik.getIme(),korisnik.getPrezime(),korisnik.getKorisnikoIme(),lozinka,korisnik.getDatumRodjenja(),korisnik.getAdresa());
            this.korisnik=korisnik;
            imeTextField.setText(korisnik.getIme());
            prezimeTextField.setText(korisnik.getPrezime());
            korisnickoImeTextField.setText(korisnik.getKorisnikoIme());
            datumRodenjaDatePicker.setValue(korisnik.getDatumRodjenja());
            lozinkaPasswordField.setText(korisnik.getLozinka());
            adresaTextField.setText(korisnik.getAdresa());
            azurirajButton.setDisable(false);
            brisiButton.setDisable(false);
            noviButton.setDisable(false);
            dodajButton.setDisable(false);
        }
        catch (Exception e)
        {
            logger.error("Ne moze se odabrati korisnik. "+e);
        }
    }
    public void ocisti()
    {
        imeTextField.clear();
        prezimeTextField.clear();
        korisnickoImeTextField.clear();
        datumRodenjaDatePicker.setValue(null);
        lozinkaPasswordField.clear();
        adresaTextField.clear();
        pretrazivanjeTextField.clear();
        noviButton.setDisable(false);
        dodajButton.setDisable(true);
        azurirajButton.setDisable(true);
        brisiButton.setDisable(true);
    }
    public void novi()
    {
        ocisti();
        dodajButton.setDisable(false);
    }
    public void pretrazi(String uvjet)
    {
        ObservableList<Korisnik> pocetnaLista = Baza.dohvatiKorisnike();

        ObservableList<Korisnik> filtriranaLista = pocetnaLista.stream()
                .filter(osoba ->
                        osoba.getIme().toLowerCase().contains(uvjet.toLowerCase()) ||
                                osoba.getPrezime().toLowerCase().contains(uvjet.toLowerCase()) ||
                                osoba.getAdresa().toLowerCase().contains(uvjet.toLowerCase()) ||
                                osoba.getKorisnikoIme().toLowerCase().contains(uvjet.toLowerCase()) ||
                                osoba.getId().toString().toLowerCase().contains(uvjet.toLowerCase()) ||
                                osoba.getDatumRodjenja().format(Konstante.DATE_TIME_FORMAT).toLowerCase().contains(uvjet.toLowerCase()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));

        korisnikTableView.setItems(filtriranaLista);
    }
}
