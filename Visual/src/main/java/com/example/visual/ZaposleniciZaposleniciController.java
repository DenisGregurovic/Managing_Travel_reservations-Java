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
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ZaposleniciZaposleniciController
{
    @FXML
    private TextField imeTextField;
    @FXML
    private TextField prezimeTextField;
    @FXML
    private TextField korisnickoImeTextField;
    @FXML
    private TextField pretrazivanjeTextField;
    @FXML
    private PasswordField lozinkaPasswordField;
    @FXML
    private DatePicker datumRodenjaDatePicker;
    @FXML
    private ComboBox<String> ulogaComboBox;
    @FXML
    private Button noviButton;
    @FXML
    private Button dodajButton;
    @FXML
    private Button azurirajButton;
    @FXML
    private Button brisiButton;
    @FXML
    private TableView<Zaposlenik> zaposlenikTableView;
    @FXML
    private TableColumn<Zaposlenik,String> zaposlenikIDTableColumn;
    @FXML
    private TableColumn<Zaposlenik,String> zaposlenikImeTableColumn;
    @FXML
    private TableColumn<Zaposlenik,String> zaposlenikPrezimeTableColumn;
    @FXML
    private TableColumn<Zaposlenik, String> zaposlenikKorisnickoImeTableColumn;
    @FXML
    private TableColumn<Zaposlenik, String> zaposlenikDatumRodenjaImeTableColumn;
    @FXML
    private TableColumn<Zaposlenik, String> zaposlenikUlogaTableColumn;
    Zaposlenik aktivniZaposlenik= new Serijalizacija<Zaposlenik>().ucitajZaposlenickiRacun("Datoteke/AktivniKorisnik.dat");
    private Zaposlenik zaposlenik;
    private static final Logger logger = LoggerFactory.getLogger(Pokreni.class);

    public void initialize()
    {
        zaposlenikIDTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Zaposlenik, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Zaposlenik, String> param)
            {
                return new ReadOnlyStringWrapper(param.getValue().getId().toString());
            }
        });
        zaposlenikImeTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Zaposlenik, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Zaposlenik, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getIme());
            }
        });
        zaposlenikPrezimeTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Zaposlenik, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Zaposlenik, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getPrezime());
            }
        });
        zaposlenikKorisnickoImeTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Zaposlenik, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Zaposlenik, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getKorisnikoIme());
            }
        });
        zaposlenikDatumRodenjaImeTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Zaposlenik,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Zaposlenik, String> param) {
                LocalDate datumRodenja = param.getValue().getDatumRodjenja();
                String datumRodenjaString = datumRodenja.format(Konstante.DATE_TIME_FORMAT);
                return new ReadOnlyStringWrapper(datumRodenjaString);
            }
        });
        zaposlenikUlogaTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Zaposlenik, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Zaposlenik, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getUloga().getOpis());
            }
        });
        List<String> sveUloge= Metode.ispisiUloge();
        ulogaComboBox.getItems().addAll(sveUloge);
        ObservableList<Zaposlenik> zaposlenici= Baza.dohvatiZaposlenike();
        UcitajZaposlenikeThread dohvacanjeNit = new UcitajZaposlenikeThread(zaposlenici);
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(dohvacanjeNit, 0, 1, TimeUnit.SECONDS);
        zaposlenikTableView.setItems(zaposlenici);
        azurirajButton.setDisable(true);
        brisiButton.setDisable(true);
        noviButton.setDisable(false);
        dodajButton.setDisable(true);
        pretrazivanjeTextField.textProperty().addListener((ObservableList,oldValue,newValue) -> {pretrazi(newValue);});
        Zaposlenik aktivniZaposlenik= new Serijalizacija<Zaposlenik>().ucitajZaposlenickiRacun("Datoteke/AktivniKorisnik.dat");
    }
    public void mouseClicked()
    {
        try
        {
            Zaposlenik zaposlenik=zaposlenikTableView.getSelectionModel().getSelectedItem();
            zaposlenik=new Zaposlenik(zaposlenik.getId(), zaposlenik.getIme(),zaposlenik.getPrezime(),zaposlenik.getKorisnikoIme(),zaposlenik.getLozinka(),zaposlenik.getDatumRodjenja(),zaposlenik.getUloga());
            this.zaposlenik=zaposlenik;
            imeTextField.setText(zaposlenik.getIme());
            prezimeTextField.setText(zaposlenik.getPrezime());
            korisnickoImeTextField.setText(zaposlenik.getKorisnikoIme());
            datumRodenjaDatePicker.setValue(zaposlenik.getDatumRodjenja());
            lozinkaPasswordField.setText(zaposlenik.getLozinka());
            ulogaComboBox.setValue(zaposlenik.getUloga().getOpis());
            azurirajButton.setDisable(false);
            brisiButton.setDisable(false);
            noviButton.setDisable(false);
            dodajButton.setDisable(false);
        }
        catch (Exception e)
        {
           logger.error("Ne moze se odabrati zaposlenik. "+e);
        }
    }
    public void dodaj()
    {
        if (imeTextField.getText().isEmpty() || datumRodenjaDatePicker.getValue() == null || prezimeTextField.getText().isEmpty() || korisnickoImeTextField.getText().isEmpty() || lozinkaPasswordField.getText().isEmpty()|| ulogaComboBox.getValue() == null)
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
                String ulogaString = ulogaComboBox.getValue();
                Uloga uloga=Metode.vratiUlogu(ulogaString);
                Metode.provjeriDatumRodenja(datum);
                Zaposlenik zaposlenik = new Zaposlenik(1L, ime, prezime,korisnickoIme,lozinka,datum, uloga);
                Metode.ProvjeriKorisnickoImeZaposlenika(zaposlenik);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Confirmation");
                dialog.setHeaderText("Jesi siguran");
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.setContentText("Kreiranje zaposlenika: " + "\n-Ime: "+zaposlenik.getIme() + "\n-Prezime: " + zaposlenik.getPrezime()+"\n-Korisničko ime: "+zaposlenik.getKorisnikoIme()+"\n-Datum rođenja: "+zaposlenik.getDatumRodjenja().format(Konstante.DATE_TIME_FORMAT)+"\n-Uloga: "+zaposlenik.getUloga().getOpis());
                ButtonType ok = new ButtonType("Da", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("Ne", ButtonBar.ButtonData.CANCEL_CLOSE);
                dialog.getDialogPane().getButtonTypes().addAll(ok, no);
                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get().equals(ok))
                {
                    SpremiZaposlenikeUBazuThread thread = new SpremiZaposlenikeUBazuThread(zaposlenik);
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(thread);
                    Platform.runLater(() -> {
                        zaposlenik.setId(Metode.zadnjiIDZaposlenika());
                        SpremiZaposlenikaUDatotekuThread thread1 = new SpremiZaposlenikaUDatotekuThread(zaposlenik);
                        Executor executor1 = Executors.newSingleThreadExecutor();
                        executor1.execute(thread1);
                    });
                    Serijalizacija<Promjena<Zaposlenik, Zaposlenik>> promjenaSerijalizacija = new Serijalizacija();
                    Promjena<Zaposlenik, Zaposlenik> promjena = (new PromjenaBuilder()).setPocetni((Object) null).setKrajnji(zaposlenik).setUloga(aktivniZaposlenik).setVrijeme(LocalDateTime.now()).createPromjena();
                    promjenaSerijalizacija.serializiraj(promjena, "Datoteke/zaposlenikPromjene.dat");
                }
            }
            catch (BuduciDatumException iznimka)
            {
                logger.error("Došlo je do greške pri unosu datuma "+iznimka);
            }
            catch (InputMismatchException iznimka)
            {
                logger.error("Došlo je do greške "+iznimka);
            }
            catch (KorisnickoImePostojiException iznimka)
            {
                logger.error("Došlo je do greške pri korisničkom imenu "+iznimka);
            }
            catch (Exception iznimka)
            {
                logger.error("Došlo je do greške: "+iznimka);
            }
        }
        ocisti();
    }

    public void azuriraj()
    {
        Zaposlenik stariZaposlenik=this.zaposlenik;
        if (imeTextField.getText().isEmpty() || datumRodenjaDatePicker.getValue() == null || prezimeTextField.getText().isEmpty() || korisnickoImeTextField.getText().isEmpty() || lozinkaPasswordField.getText().isEmpty()|| ulogaComboBox.getValue() == null)
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
                String ulogaString = ulogaComboBox.getValue();
                Uloga uloga=Metode.vratiUlogu(ulogaString);
                Metode.provjeriDatumRodenja(datum);
                Zaposlenik zaposlenik = new Zaposlenik(this.zaposlenik.getId(), ime, prezime,korisnickoIme,lozinka,datum, uloga);
                Metode.ProvjeriKorisnickoImeZaposlenika(zaposlenik);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Confirmation");
                dialog.setHeaderText("Jesi siguran");
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.setContentText("Stari zaposlenika: " + "\n-Ime: "+stariZaposlenik.getIme() + "\n-Prezime: " + stariZaposlenik.getPrezime()+"\n-Korisničko ime: "+stariZaposlenik.getKorisnikoIme()+"\n-Datum rođenja: "+stariZaposlenik.getDatumRodjenja().format(Konstante.DATE_TIME_FORMAT)+"\n-Uloga: "+stariZaposlenik.getUloga().getOpis()
                +"Novi zaposlenik: " + "\n-Ime: "+zaposlenik.getIme() + "\n-Prezime: " + zaposlenik.getPrezime()+"\n-Korisničko ime: "+zaposlenik.getKorisnikoIme()+"\n-Datum rođenja: "+zaposlenik.getDatumRodjenja().format(Konstante.DATE_TIME_FORMAT)+"\n-Uloga: "+zaposlenik.getUloga().getOpis());
                ButtonType ok = new ButtonType("Da", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("Ne", ButtonBar.ButtonData.CANCEL_CLOSE);
                dialog.getDialogPane().getButtonTypes().addAll(ok, no);
                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get().equals(ok))
                {
                    AzuriraZaposlenikeUBaziThread thread = new AzuriraZaposlenikeUBaziThread(zaposlenik);
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(thread);
                    Platform.runLater(() -> {
                        Zaposlenik upit=Metode.ispisiZaposlenika(zaposlenik);
                        AzurirajZaposlenikeUDatoteciThread thread1 = new AzurirajZaposlenikeUDatoteciThread(upit);
                        Executor executor1 = Executors.newSingleThreadExecutor();
                        executor1.execute(thread1);
                    });
                    Serijalizacija<Promjena<Zaposlenik, Zaposlenik>> promjenaSerijalizacija = new Serijalizacija();
                    Promjena<Zaposlenik, Zaposlenik> promjena = (new PromjenaBuilder()).setPocetni(stariZaposlenik).setKrajnji(zaposlenik).setUloga(aktivniZaposlenik).setVrijeme(LocalDateTime.now()).createPromjena();
                    promjenaSerijalizacija.serializiraj(promjena, "Datoteke/zaposlenikPromjene.dat");
                }
            }
            catch (BuduciDatumException iznimka)
            {
                logger.error("Došlo je do greške pri unosu datuma "+iznimka);
            }
            catch (InputMismatchException iznimka)
            {
                logger.error("Došlo je do greške "+iznimka);
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
        if (imeTextField.getText().isEmpty() || datumRodenjaDatePicker.getValue() == null || prezimeTextField.getText().isEmpty() || korisnickoImeTextField.getText().isEmpty() || lozinkaPasswordField.getText().isEmpty()|| ulogaComboBox.getValue() == null)
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
                String ulogaString = ulogaComboBox.getValue();
                Uloga uloga=Metode.vratiUlogu(ulogaString);
                Metode.provjeriDatumRodenja(datum);
                Zaposlenik zaposlenik = new Zaposlenik(this.zaposlenik.getId(), ime, prezime,korisnickoIme,lozinka,datum, uloga);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Confirmation");
                dialog.setHeaderText("Jesi siguran");
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.setContentText("Brisanje zaposlenika: " + "\n-Ime: "+zaposlenik.getIme() + "\n-Prezime: " + zaposlenik.getPrezime()+"\n-Korisničko ime: "+zaposlenik.getKorisnikoIme()+"\n-Datum rođenja: "+zaposlenik.getDatumRodjenja().format(Konstante.DATE_TIME_FORMAT)+"\n-Uloga: "+zaposlenik.getUloga().getOpis());
                ButtonType ok = new ButtonType("Da", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("Ne", ButtonBar.ButtonData.CANCEL_CLOSE);
                dialog.getDialogPane().getButtonTypes().addAll(ok, no);
                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get().equals(ok))
                {
                    BrisIZaposlenikezBazeThread thread = new BrisIZaposlenikezBazeThread(zaposlenik);
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(thread);
                    Platform.runLater(() -> {
                        BrisiZaposlenikeIzDatotekeThread thread1 = new BrisiZaposlenikeIzDatotekeThread(zaposlenik);
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
                logger.error("Došlo je do greška "+iznimka);
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
            poruka = poruka + "\n- Ime zaposlenika";
        }
        if (datumRodenjaDatePicker.getValue() == null)
        {
            poruka = poruka + "\n- Datum rođenja";
        }
        if (prezimeTextField.getText().isEmpty())
        {
            poruka = poruka + "\n- Prezime zaposlenika";
        }
        if (korisnickoImeTextField.getText().isEmpty())
        {
            poruka = poruka + "\n- Korisničko ime";
        }
        if (lozinkaPasswordField.getText().isEmpty())
        {
            poruka = poruka + "\n- Lozinka";
        }
        if (ulogaComboBox.getValue() == null)
        {
            poruka = poruka + "\n- Uloga";
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greška u unosu");
        alert.setHeaderText("Molimo unesite sve potrebne informacije");
        alert.setContentText(poruka);
        alert.showAndWait();
    }

    public void ocisti()
    {
        imeTextField.clear();
        prezimeTextField.clear();
        korisnickoImeTextField.clear();
        lozinkaPasswordField.clear();
        datumRodenjaDatePicker.setValue(null);
        ulogaComboBox.setValue(null);
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
        ObservableList<Zaposlenik> pocetnaLista = Baza.dohvatiZaposlenike();
        ObservableList<Zaposlenik> filtriranaLista = pocetnaLista.stream()
                .filter(osoba ->
                        osoba.getIme().toLowerCase().contains(uvjet.toLowerCase()) ||
                                osoba.getPrezime().toLowerCase().contains(uvjet.toLowerCase()) ||
                                osoba.getUloga().getOpis().toLowerCase().contains(uvjet.toLowerCase()) ||
                                osoba.getKorisnikoIme().toLowerCase().contains(uvjet.toLowerCase()) ||
                                osoba.getId().toString().toLowerCase().contains(uvjet.toLowerCase()) ||
                                osoba.getDatumRodjenja().format(Konstante.DATE_TIME_FORMAT).toLowerCase().contains(uvjet.toLowerCase()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));

        zaposlenikTableView.setItems(filtriranaLista);
    }
}
