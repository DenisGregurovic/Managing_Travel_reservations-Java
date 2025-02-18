package com.example.visual;

import com.example.visual.production.BazaPodataka.Baza;
import com.example.visual.production.Entiteti.*;
import com.example.visual.production.Iznimke.Oznacene.BrojException;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ZaposleniciSmjestajiController
{
    @FXML
    private TextField nazivTextField;
    @FXML
    private TextField adresaTextField;
    @FXML
    private TextField redovnaCijenaTextField;
    @FXML
    private TextField popustTextField;
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
    private ComboBox<String> vrstaComboBox;
    @FXML
    private TableView<Smjestaj> smjestajTableView;
    @FXML
    private TableColumn<Smjestaj,String> smjestajIDTableColumn;
    @FXML
    private TableColumn<Smjestaj,String> smjestajNazivTableColumn;
    @FXML
    private TableColumn<Smjestaj,String> smjestajAdresaTableColumn;
    @FXML
    private TableColumn<Smjestaj, String> smjestajVrstaTableColumn;
    @FXML
    private TableColumn<Smjestaj, String> smjestajRedovnaCijenaTableColumn;
    @FXML
    private TableColumn<Smjestaj, String> smjestajAkcijskaCijenaTableColumn;
    private Smjestaj smjestaj;
    private static final Logger logger = LoggerFactory.getLogger(Pokreni.class);
    Zaposlenik aktivniZaposlenik= new Serijalizacija<Zaposlenik>().ucitajZaposlenickiRacun("Datoteke/AktivniKorisnik.dat");
    public void initialize()
    {
        smjestajIDTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Smjestaj, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Smjestaj, String> param)
            {
                return new ReadOnlyStringWrapper(param.getValue().getId().toString());
            }
        });
        smjestajNazivTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Smjestaj, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Smjestaj, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getNaziv());
            }
        });
        smjestajAdresaTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Smjestaj, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Smjestaj, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getAdresa());
            }
        });
        smjestajVrstaTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Smjestaj, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Smjestaj, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getVrstaSmjestaja().getOpis());
            }
        });
        smjestajRedovnaCijenaTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Smjestaj,String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Smjestaj, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getRegularnaCijena().toString());
            }
        });
        smjestajAkcijskaCijenaTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Smjestaj, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Smjestaj, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getSnizenaCijena().toString());
            }
        });
        List<String> sveVrste= Metode.ispisiVrsteSmjestaja();
        vrstaComboBox.getItems().addAll(sveVrste);
        ObservableList<Smjestaj> smjestaji= Baza.dohvatiSmjestaje();
        UcitajSmjestajeThread dohvacanjeNit = new UcitajSmjestajeThread(smjestaji);
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(dohvacanjeNit, 0, 1, TimeUnit.SECONDS);
        smjestajTableView.setItems(smjestaji);
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
            Smjestaj smjestaj=smjestajTableView.getSelectionModel().getSelectedItem();
            smjestaj=new Smjestaj(smjestaj.getId(), smjestaj.getNaziv(),smjestaj.getAdresa(),smjestaj.getVrstaSmjestaja(),smjestaj.getRegularnaCijena(),smjestaj.getPopust(),smjestaj.getSnizenaCijena());
            this.smjestaj=smjestaj;
            nazivTextField.setText(smjestaj.getNaziv());
            adresaTextField.setText(smjestaj.getAdresa());
            redovnaCijenaTextField.setText(smjestaj.getRegularnaCijena().toString());
            popustTextField.setText(smjestaj.getPopust().iznos().toString());
            vrstaComboBox.setValue(smjestaj.getVrstaSmjestaja().getOpis());
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
    public void dodaj()
    {
        if (nazivTextField.getText().isEmpty() || adresaTextField.getText().isEmpty() || redovnaCijenaTextField.getText().isEmpty() || popustTextField.getText().isEmpty() || vrstaComboBox.getValue()==null)
        {
            upozorenje();
        }
        else
        {
            try
            {
                String naziv = nazivTextField.getText();
                String adresa = adresaTextField.getText();
                BigDecimal redovnaCijena = new BigDecimal(redovnaCijenaTextField.getText());
                BigDecimal popustIznos = new BigDecimal(popustTextField.getText());
                String vrstaString=vrstaComboBox.getValue();
                Vrsta vrsta=Metode.vratiVrstu(vrstaString);
                Popust popust=new Popust(popustIznos);
                BigDecimal akcijskaCijena=redovnaCijena.subtract(redovnaCijena.multiply(popustIznos.multiply(new BigDecimal("0.01"))));
                Metode.ProvjeriBroj(redovnaCijena);
                Metode.ProvjeriBroj(popustIznos);
                Smjestaj smjestaj = new Smjestaj(1L, naziv, adresa, vrsta, redovnaCijena,popust,akcijskaCijena);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Confirmation");
                dialog.setHeaderText("Jesi siguran");
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.setContentText("Kreiranje smještaja: " +"\n-Naziv: "+ smjestaj.getNaziv() + "\n-Adresa: " + smjestaj.getAdresa()+"\nVrsta smještaja: "+smjestaj.getVrstaSmjestaja().getOpis()+"\n-Regularna cijena: "+smjestaj.getRegularnaCijena()+"\n-Popust:"+smjestaj.getPopust().iznos()+"\n-Snižena cijena: "+smjestaj.getSnizenaCijena());
                ButtonType ok = new ButtonType("Da", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("Ne", ButtonBar.ButtonData.CANCEL_CLOSE);
                dialog.getDialogPane().getButtonTypes().addAll(ok, no);
                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get().equals(ok))
                {
                        SpremiSmjestajeThread thread = new SpremiSmjestajeThread(smjestaj);
                        Executor executor = Executors.newSingleThreadExecutor();
                        executor.execute(thread);
                    Serijalizacija<Promjena<Smjestaj, Zaposlenik>> promjenaSerijalizacija = new Serijalizacija();
                    Promjena<Smjestaj, Zaposlenik> promjena = (new PromjenaBuilder()).setPocetni((Object) null).setKrajnji(smjestaj).setUloga(aktivniZaposlenik).setVrijeme(LocalDateTime.now()).createPromjena();
                    promjenaSerijalizacija.serializiraj(promjena, "Datoteke/smjestajPromjene.dat");
                    
                }
            }
            catch (BrojException iznimka)
            {
                logger.error("Došlo je do greška pri upisu broja "+iznimka);
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
    public void azuriraj()
    {
        Smjestaj stariSmjestaj=this.smjestaj;
        if (nazivTextField.getText().isEmpty() || adresaTextField.getText().isEmpty() || redovnaCijenaTextField.getText().isEmpty() || popustTextField.getText().isEmpty() || vrstaComboBox.getValue()==null)
        {
            upozorenje();
        }
        else
        {
            try
            {
                String naziv = nazivTextField.getText();
                String adresa = adresaTextField.getText();
                BigDecimal redovnaCijena = new BigDecimal(redovnaCijenaTextField.getText());
                BigDecimal popustIznos = new BigDecimal(popustTextField.getText());
                String vrstaString=vrstaComboBox.getValue();
                Vrsta vrsta=Metode.vratiVrstu(vrstaString);
                Popust popust=new Popust(popustIznos);
                BigDecimal akcijskaCijena=redovnaCijena.subtract(redovnaCijena.multiply(popustIznos.multiply(new BigDecimal("0.01"))));
                Metode.ProvjeriBroj(redovnaCijena);
                Metode.ProvjeriBroj(popustIznos);
                Smjestaj smjestaj = new Smjestaj(this.smjestaj.getId(), naziv, adresa, vrsta, redovnaCijena,popust,akcijskaCijena);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Confirmation");
                dialog.setHeaderText("Jesi siguran");
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.setContentText("Stari smještaja: " +"\n-Naziv: "+ stariSmjestaj.getNaziv() + "\n-Adresa: " + stariSmjestaj.getAdresa()+"\nVrsta smještaja: "+stariSmjestaj.getVrstaSmjestaja().getOpis()+"\n-Regularna cijena: "+stariSmjestaj.getRegularnaCijena()+"\n-Popust:"+stariSmjestaj.getPopust().iznos()+"\n-Snižena cijena: "+stariSmjestaj.getSnizenaCijena()+
                        "\nNovi smještaja: " +"\n-Naziv: "+ smjestaj.getNaziv() + "\n-Adresa: " + smjestaj.getAdresa()+"\nVrsta smještaja: "+smjestaj.getVrstaSmjestaja().getOpis()+"\n-Regularna cijena: "+smjestaj.getRegularnaCijena()+"\n-Popust:"+smjestaj.getPopust().iznos()+"\n-Snižena cijena: "+smjestaj.getSnizenaCijena());
                ButtonType ok = new ButtonType("Da", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("Ne", ButtonBar.ButtonData.CANCEL_CLOSE);
                dialog.getDialogPane().getButtonTypes().addAll(ok, no);
                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get().equals(ok))
                {
                        AzurirajSmjestajeThread thread = new AzurirajSmjestajeThread(smjestaj);
                        Executor executor = Executors.newSingleThreadExecutor();
                        executor.execute(thread);
                    Serijalizacija<Promjena<Smjestaj, Zaposlenik>> promjenaSerijalizacija = new Serijalizacija();
                    Promjena<Smjestaj, Zaposlenik> promjena = (new PromjenaBuilder()).setPocetni(stariSmjestaj).setKrajnji(smjestaj).setUloga(aktivniZaposlenik).setVrijeme(LocalDateTime.now()).createPromjena();
                    promjenaSerijalizacija.serializiraj(promjena, "Datoteke/smjestajPromjene.dat");
                }
            }
            catch (BrojException iznimka)
            {
                logger.error("Došlo je do greška pri upisu broja "+iznimka);
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
    public void obrisi()
    {
        if (nazivTextField.getText().isEmpty() || adresaTextField.getText().isEmpty() || redovnaCijenaTextField.getText().isEmpty() || popustTextField.getText().isEmpty() || vrstaComboBox.getValue()==null)
        {
            upozorenje();
        }
        else
        {
            try
            {
                String naziv = nazivTextField.getText();
                String adresa = adresaTextField.getText();
                BigDecimal redovnaCijena = new BigDecimal(redovnaCijenaTextField.getText());
                BigDecimal popustIznos = new BigDecimal(popustTextField.getText());
                String vrstaString=vrstaComboBox.getValue();
                Vrsta vrsta=Metode.vratiVrstu(vrstaString);
                Popust popust=new Popust(popustIznos);
                BigDecimal akcijskaCijena=redovnaCijena.subtract(redovnaCijena.multiply(popustIznos.multiply(new BigDecimal("0.01"))));
                Metode.ProvjeriBroj(redovnaCijena);
                Metode.ProvjeriBroj(popustIznos);
                Smjestaj smjestaj = new Smjestaj(this.smjestaj.getId(), naziv, adresa, vrsta, redovnaCijena,popust,akcijskaCijena);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Confirmation");
                dialog.setHeaderText("Jesi siguran");
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.setContentText("Brisanje smještaja: " +"\n-Naziv: "+ smjestaj.getNaziv() + "\n-Adresa: " + smjestaj.getAdresa()+"\nVrsta smještaja: "+smjestaj.getVrstaSmjestaja().getOpis()+"\n-Regularna cijena: "+smjestaj.getRegularnaCijena()+"\n-Popust:"+smjestaj.getPopust().iznos()+"\n-Snižena cijena: "+smjestaj.getSnizenaCijena());
                ButtonType ok = new ButtonType("Da", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("Ne", ButtonBar.ButtonData.CANCEL_CLOSE);
                dialog.getDialogPane().getButtonTypes().addAll(ok, no);
                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get().equals(ok))
                {
                    Platform.runLater(() -> {
                        BrisiSmjestajeThread thread = new BrisiSmjestajeThread(smjestaj);
                        Executor executor = Executors.newSingleThreadExecutor();
                        executor.execute(thread);
                    });
                }
            }
            catch (BrojException iznimka)
            {
                logger.error("Došlo je do greška pri upisu broja "+iznimka);
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
        if (nazivTextField.getText().isEmpty())
        {
            poruka = poruka + "\n- Naziv smještaja";
        }
        if (adresaTextField.getText().isEmpty() )
        {
            poruka = poruka + "\n- Adresa smještaja";
        }
        if (redovnaCijenaTextField.getText().isEmpty())
        {
            poruka = poruka + "\n- Redovna cijena";
        }
        if (popustTextField.getText().isEmpty())
        {
            poruka = poruka + "\n- Popust";
        }
        if ( vrstaComboBox.getValue()==null)
        {
            poruka = poruka + "\n- Vrsta smještaja";
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greška u unosu");
        alert.setHeaderText("Molimo unesite sve potrebne informacije");
        alert.setContentText(poruka);
        alert.showAndWait();
    }

    public void ocisti()
    {
        nazivTextField.clear();
        adresaTextField.clear();
        redovnaCijenaTextField.clear();
        popustTextField.clear();
        vrstaComboBox.setValue(null);
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
        ObservableList<Smjestaj> pocetnaLista = Baza.dohvatiSmjestaje();

        ObservableList<Smjestaj> filtriranaLista = pocetnaLista.stream()
                .filter(smjestaj ->
                        smjestaj.getNaziv().toLowerCase().contains(uvjet.toLowerCase()) ||
                                smjestaj.getAdresa().toLowerCase().contains(uvjet.toLowerCase()) ||
                                smjestaj.getPopust().iznos().toString().toLowerCase().contains(uvjet.toLowerCase()) ||
                                smjestaj.getVrstaSmjestaja().getOpis().toLowerCase().contains(uvjet.toLowerCase()) ||
                                smjestaj.getId().toString().toLowerCase().contains(uvjet.toLowerCase()) ||
                                smjestaj.getRegularnaCijena().toString().toLowerCase().contains(uvjet.toLowerCase()) ||
                                smjestaj.getSnizenaCijena().toString().toLowerCase().contains(uvjet.toLowerCase()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));

        smjestajTableView.setItems(filtriranaLista);
    }
}
