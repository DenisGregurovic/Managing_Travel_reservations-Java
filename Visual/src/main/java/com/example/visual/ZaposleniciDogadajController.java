package com.example.visual;

import com.example.visual.production.BazaPodataka.Baza;
import com.example.visual.production.Entiteti.*;
import com.example.visual.production.Iznimke.Oznacene.BrojException;
import com.example.visual.production.Iznimke.Neoznacene.ProsliDatumException;
import com.example.visual.production.Konstante.Konstante;
import com.example.visual.production.Niti.AzurirajDogadajeThread;
import com.example.visual.production.Niti.BrisiDogadajeThread;
import com.example.visual.production.Niti.SpremiDogadajeThread;
import com.example.visual.production.Niti.UcitajDogadajeThread;
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

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ZaposleniciDogadajController
{
    @FXML
    private TextField nazivTextField;
    @FXML
    private TextField cijenaTextField;
    @FXML
    private TextField kolicinaTextField;
    @FXML
    private TextField opisTextField;
    @FXML
    private TextField pretrazivanjeTextField;
    @FXML
    private Button noviButton;
    @FXML
    private Button dodajButton;
    @FXML
    private Button azurirajButton;
    @FXML
    private Button brisiButton;
    @FXML
    private DatePicker datumDatePicker;
    @FXML
    private TableView<Dogadaj> dogadajTableView;
    @FXML
    private TableColumn<Dogadaj,String> dogadajIDTableColumn;
    @FXML
    private TableColumn<Dogadaj,String> dogadajNazivTableColumn;
    @FXML
    private TableColumn<Dogadaj, String> dogadajOpisTableColumn;
    @FXML
    private TableColumn<Dogadaj, String> dogadajDatumTableColumn;
    @FXML
    private TableColumn<Dogadaj, String> dogadajCijenaTableColumn;
    @FXML
    private TableColumn<Dogadaj, String> dogadajKolicinaTableColumn;
    private Dogadaj dogadaj;
    private static final Logger logger = LoggerFactory.getLogger(Pokreni.class);
    Zaposlenik aktivniZaposlenik= new Serijalizacija<Zaposlenik>().ucitajZaposlenickiRacun("Datoteke/AktivniKorisnik.dat");
    public void initialize()
    {
        dogadajIDTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Dogadaj, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Dogadaj, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getId().toString());
            }
        });
        dogadajNazivTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Dogadaj, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Dogadaj, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getNaziv());
            }
        });
        dogadajOpisTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Dogadaj, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Dogadaj, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getOpis());
            }
        });
        dogadajCijenaTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Dogadaj, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Dogadaj, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getCijena().toString());
            }
        });
        dogadajKolicinaTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Dogadaj, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Dogadaj, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getKolicina().toString());
            }
        });
        dogadajDatumTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Dogadaj,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Dogadaj, String> param) {
                LocalDate datum = param.getValue().getDatumDogadaja();
                String datumString = datum.format(Konstante.DATE_TIME_FORMAT);
                return new ReadOnlyStringWrapper(datumString);
            }
        });
        ObservableList<Dogadaj> dogadaji= Baza.dohvatiDogadaje();
        UcitajDogadajeThread dohvacanjeNit = new UcitajDogadajeThread(dogadaji);
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(dohvacanjeNit, 0, 1, TimeUnit.SECONDS);
        dogadajTableView.setItems(dogadaji);
        ocisti();
        azurirajButton.setDisable(true);
        brisiButton.setDisable(true);
        noviButton.setDisable(false);
        dodajButton.setDisable(true);
        pretrazivanjeTextField.textProperty().addListener((ObservableList,oldValue,newValue) -> {pretrazi(newValue);});
    }
    public void mouseClicked()
    {
        try
        {
            Dogadaj dogadaj=dogadajTableView.getSelectionModel().getSelectedItem();
            dogadaj=new Dogadaj(dogadaj.getId(), dogadaj.getNaziv(),dogadaj.getDatumDogadaja(),dogadaj.getOpis(),dogadaj.getCijena(),dogadaj.getKolicina());
            this.dogadaj=dogadaj;
            nazivTextField.setText(dogadaj.getNaziv());
            cijenaTextField.setText(dogadaj.getCijena().toString());
            opisTextField.setText(dogadaj.getOpis());
            kolicinaTextField.setText(dogadaj.getKolicina().toString());
            datumDatePicker.setValue(dogadaj.getDatumDogadaja());
            azurirajButton.setDisable(false);
            brisiButton.setDisable(false);
            noviButton.setDisable(false);
            dodajButton.setDisable(false);
        }
        catch (Exception e)
        {
            logger.error("Ne moze se odabrati smjestaj. "+e);
        }
    }
    public void azuriraj()
    {
        Dogadaj stariDogadaj=this.dogadaj;
        if (nazivTextField.getText().isEmpty() || datumDatePicker.getValue() == null || opisTextField.getText().isEmpty() || cijenaTextField.getText().isEmpty() || kolicinaTextField.getText().isEmpty())
        {
            upozorenje();
        }
        else
        {
            try
            {
                String naziv = nazivTextField.getText();
                LocalDate datum = datumDatePicker.getValue();
                String opis = opisTextField.getText();
                BigDecimal cijena = new BigDecimal(this.cijenaTextField.getText());
                Integer kolicina = Integer.parseInt(this.kolicinaTextField.getText());
                Metode.provjeriDatumDogadaja(datum);
                Metode.ProvjeriBroj(cijena);
                Metode.ProvjeriBroj(new BigDecimal(kolicina));
                Dogadaj dogadaj = new Dogadaj(this.dogadaj.getId(), naziv, datum, opis, cijena,kolicina);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Confirmation");
                dialog.setHeaderText("Jesi siguran");
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.setContentText("Stari događaj: " + "\n-Naziv: "+stariDogadaj.getNaziv() + "\n-Opis: " + stariDogadaj.getOpis()+"\n-Datum:"+stariDogadaj.getDatumDogadaja().format(Konstante.DATE_TIME_FORMAT)+"\n-Cijena: "+stariDogadaj.getCijena()+"\n-Količina: "+stariDogadaj.getKolicina()+
                "\nNovi događaj: " + "\n-Naziv: "+dogadaj.getNaziv() + "\n-Opis: " + dogadaj.getOpis()+"\n-Datum:"+dogadaj.getDatumDogadaja().format(Konstante.DATE_TIME_FORMAT)+"\n-Cijena: "+dogadaj.getCijena()+"\n-Količina: "+dogadaj.getKolicina());
                ButtonType ok = new ButtonType("Da", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("Ne", ButtonBar.ButtonData.CANCEL_CLOSE);
                dialog.getDialogPane().getButtonTypes().addAll(ok, no);
                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get().equals(ok))
                {
                    AzurirajDogadajeThread thread = new AzurirajDogadajeThread(dogadaj);
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(thread);
                    Serijalizacija<Promjena<Dogadaj, Zaposlenik>> promjenaSerijalizacija = new Serijalizacija();
                    Promjena<Dogadaj, Zaposlenik> promjena = (new PromjenaBuilder()).setPocetni(stariDogadaj).setKrajnji(dogadaj).setUloga(aktivniZaposlenik).setVrijeme(LocalDateTime.now()).createPromjena();
                    promjenaSerijalizacija.serializiraj(promjena, "Datoteke/dogadajPromjene.dat");
                }
            }
            catch (ProsliDatumException iznimka)
            {
                logger.error("Došlo je do greška pri unosu datuma "+iznimka);
            }
            catch (BrojException iznimka)
            {
                logger.error("Došlo je do greška pri upisu broja "+iznimka);
            }
            catch (InputMismatchException iznimka)
            {
                logger.error("Došlo je do greška pri broja "+iznimka);
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
        if (nazivTextField.getText().isEmpty() || datumDatePicker.getValue() == null || opisTextField.getText().isEmpty() || cijenaTextField.getText().isEmpty() || kolicinaTextField.getText().isEmpty())
        {
            upozorenje();
        }
        else
        {
            try
            {
                String naziv = nazivTextField.getText();
                LocalDate datum = datumDatePicker.getValue();
                String opis = opisTextField.getText();
                BigDecimal cijena = new BigDecimal(this.cijenaTextField.getText());
                Integer kolicina = Integer.parseInt(this.kolicinaTextField.getText());
                Metode.provjeriDatumDogadaja(datum);
                Metode.ProvjeriBroj(cijena);
                Metode.ProvjeriBroj(new BigDecimal(kolicina));
                Dogadaj dogadaj = new Dogadaj(1L, naziv, datum, opis, cijena,kolicina);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Confirmation");
                dialog.setHeaderText("Jesi siguran");
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.setContentText("Kreiranje događaja: " + "\n-Naziv: "+dogadaj.getNaziv() + "\n-Opis: " + dogadaj.getOpis()+"\n-Datum:"+dogadaj.getDatumDogadaja().format(Konstante.DATE_TIME_FORMAT)+"\n-Cijena: "+dogadaj.getCijena()+"\n-Količina: "+dogadaj.getKolicina());
                ButtonType ok = new ButtonType("Da", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("Ne", ButtonBar.ButtonData.CANCEL_CLOSE);
                dialog.getDialogPane().getButtonTypes().addAll(ok, no);
                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get().equals(ok))
                {
                    SpremiDogadajeThread thread = new SpremiDogadajeThread(dogadaj);
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(thread);
                    Serijalizacija<Promjena<Dogadaj, Zaposlenik>> promjenaSerijalizacija = new Serijalizacija();
                    Promjena<Dogadaj, Zaposlenik> promjena = (new PromjenaBuilder()).setPocetni((Object)null).setKrajnji(dogadaj).setUloga(aktivniZaposlenik).setVrijeme(LocalDateTime.now()).createPromjena();
                    promjenaSerijalizacija.serializiraj(promjena, "Datoteke/dogadajPromjene.dat");
                }
            }
            catch (ProsliDatumException iznimka)
            {
                logger.error("Došlo je do greška pri unosu datuma "+iznimka);
            }
            catch (BrojException iznimka)
            {
                logger.error("Došlo je do greška pri upisu broja "+iznimka);
            }
            catch (InputMismatchException iznimka)
            {
                logger.error("Došlo je do greška pri broja "+iznimka);
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
        if (nazivTextField.getText().isEmpty() || datumDatePicker.getValue() == null || opisTextField.getText().isEmpty() || cijenaTextField.getText().isEmpty() || kolicinaTextField.getText().isEmpty())
        {
            upozorenje();
        }
        else
        {
            try
            {
                String naziv = nazivTextField.getText();
                LocalDate datum = datumDatePicker.getValue();
                String opis = opisTextField.getText();
                BigDecimal cijena = new BigDecimal(this.cijenaTextField.getText());
                Integer kolicina = Integer.parseInt(this.kolicinaTextField.getText());
                Metode.provjeriDatumDogadaja(datum);
                Metode.ProvjeriBroj(cijena);
                Metode.ProvjeriBroj(new BigDecimal(kolicina));
                Dogadaj dogadaj = new Dogadaj(this.dogadaj.getId(), naziv, datum, opis, cijena,kolicina);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Confirmation");
                dialog.setHeaderText("Jesi siguran");
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.setContentText("Brisanje događaja: " + "\n-Naziv: "+dogadaj.getNaziv() + "\n-Opis: " + dogadaj.getOpis()+"\n-Datum:"+dogadaj.getDatumDogadaja().format(Konstante.DATE_TIME_FORMAT)+"\n-Cijena: "+dogadaj.getCijena()+"\n-Količina: "+dogadaj.getKolicina());
                ButtonType ok = new ButtonType("Da", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("Ne", ButtonBar.ButtonData.CANCEL_CLOSE);
                dialog.getDialogPane().getButtonTypes().addAll(ok, no);
                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get().equals(ok))
                {
                    BrisiDogadajeThread thread = new BrisiDogadajeThread(dogadaj);
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(thread);
                }
            }
            catch (ProsliDatumException iznimka)
            {
                logger.error("Došlo je do greška pri unosu datuma "+iznimka);
            }
            catch (BrojException iznimka)
            {
                logger.error("Došlo je do greška pri upisu broja "+iznimka);
            }
            catch (InputMismatchException iznimka)
            {
                logger.error("Došlo je do greška pri broja "+iznimka);
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
        if (nazivTextField.getText().isEmpty())
        {
            poruka = poruka + "\n- Naziv događaja";
        }
        if (datumDatePicker.getValue() == null)
        {
            poruka = poruka + "\n- Datum događaja";
        }
        if (opisTextField.getText().isEmpty())
        {
            poruka = poruka + "\n- Opis događaja";
        }
        if (cijenaTextField.getText().isEmpty())
        {
            poruka = poruka + "\n- Cijena karte za događaj";
        }
        if (kolicinaTextField.getText().isEmpty())
        {
            poruka = poruka + "\n- Broj karata za događaj";
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greška u unosu");
        alert.setHeaderText("Molimo unesite sve potrebne informacije");
        alert.setContentText(poruka);
        alert.showAndWait();
    }

    public void ocisti()
    {
        opisTextField.clear();
        nazivTextField.clear();
        cijenaTextField.clear();
        opisTextField.clear();
        kolicinaTextField.clear();
        datumDatePicker.setValue(null);
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
        ObservableList<Dogadaj> pocetnaLista = Baza.dohvatiDogadaje();

        ObservableList<Dogadaj> filtriranaLista = pocetnaLista.stream()
                .filter(dogadaj ->
                        dogadaj.getNaziv().toLowerCase().contains(uvjet.toLowerCase()) ||
                                dogadaj.getOpis().toLowerCase().contains(uvjet.toLowerCase()) ||
                                dogadaj.getCijena().toString().toLowerCase().contains(uvjet.toLowerCase()) ||
                                dogadaj.getId().toString().toLowerCase().contains(uvjet.toLowerCase()) ||
                                dogadaj.getKolicina().toString().toLowerCase().contains(uvjet.toLowerCase()) ||
                                dogadaj.getDatumDogadaja().format(Konstante.DATE_TIME_FORMAT).toLowerCase().contains(uvjet.toLowerCase()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));

        dogadajTableView.setItems(filtriranaLista);
    }
}

