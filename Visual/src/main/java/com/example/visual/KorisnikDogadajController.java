package com.example.visual;

import com.example.visual.production.BazaPodataka.Baza;
import com.example.visual.production.Entiteti.*;
import com.example.visual.production.Iznimke.Neoznacene.PrevelikaKolicinaException;
import com.example.visual.production.Iznimke.Oznacene.BrojException;
import com.example.visual.production.Konstante.Konstante;
import com.example.visual.production.Niti.AzurirajDogadajeThread;
import com.example.visual.production.Niti.UcitajDogadajeThread;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class KorisnikDogadajController
{
    @FXML
    private TextField kolicinaTextField;
    @FXML
    private TextField pretrazivanjeTextField;
    @FXML
    private Button kupiButton;
    @FXML
    private Label nazivLabel;
    @FXML
    private Label datumLabel;
    @FXML
    private Label opisLabel;
    @FXML
    private Label cijenaLabel;
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
    Korisnik aktivniKorisnik= new Serijalizacija<Korisnik>().ucitajKorisnickiRacun("Datoteke/AktivniKorisnik.dat");
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
        kupiButton.setDisable(true);
        pretrazivanjeTextField.textProperty().addListener((ObservableList,oldValue,newValue) -> {pretrazi(newValue);});
    }
    public void ocisti()
    {
        nazivLabel.setText("Naziv");
        opisLabel.setText("Opis");
        cijenaLabel.setText("Cijena");
        datumLabel.setText("Datum");
        pretrazivanjeTextField.clear();
        kolicinaTextField.clear();
        kupiButton.setDisable(true);
    }
    public void pretrazi(String uvjet)
    {
        ObservableList<Dogadaj> filtriranaLista = FXCollections.observableArrayList();
        ObservableList<Dogadaj> pocetnaLista = Baza.dohvatiDogadaje();
        for (Dogadaj dogadaj : pocetnaLista)
        {
            if (dogadaj.getNaziv().toLowerCase().contains(uvjet.toLowerCase()) || dogadaj.getOpis().toLowerCase().contains(uvjet.toLowerCase()) || dogadaj.getCijena().toString().toLowerCase().contains(uvjet.toLowerCase()) || dogadaj.getId().toString().toLowerCase().contains(uvjet.toLowerCase()) || dogadaj.getKolicina().toString().toLowerCase().contains(uvjet.toLowerCase())|| dogadaj.getDatumDogadaja().format(Konstante.DATE_TIME_FORMAT).toLowerCase().contains(uvjet.toLowerCase()))
            {
                filtriranaLista.add(dogadaj);
            }
        }
        dogadajTableView.setItems(filtriranaLista);
    }
    public void mouseClicked()
    {
        try
        {
            Dogadaj dogadaj=dogadajTableView.getSelectionModel().getSelectedItem();
            dogadaj=new Dogadaj(dogadaj.getId(), dogadaj.getNaziv(),dogadaj.getDatumDogadaja(),dogadaj.getOpis(),dogadaj.getCijena(),dogadaj.getKolicina());
            this.dogadaj=dogadaj;
            kolicinaTextField.setText("1");
            nazivLabel.setText(dogadaj.getNaziv());
            cijenaLabel.setText(dogadaj.getCijena().toString());
            datumLabel.setText(dogadaj.getDatumDogadaja().format(Konstante.DATE_TIME_FORMAT));
            opisLabel.setText(dogadaj.getOpis());
            kupiButton.setDisable(false);
        }
        catch (Exception e)
        {
            logger.error("Ne moze se odabrati smjestaj. "+e);
        }
    }
    public void kupi()
    {
        if (kolicinaTextField.getText().isEmpty())
        {
            upozorenje();
        }
        try
        {
            Integer kolicina=Integer.parseInt(kolicinaTextField.getText());
            Metode.provjeriKolicinu(kolicina,dogadaj);
            Metode.ProvjeriBroj(new BigDecimal(kolicina));
            Integer novaKolicina=dogadaj.getKolicina()-kolicina;
            dogadaj.setKolicina(kolicina);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Confirmation");
            dialog.setHeaderText("Jesi siguran");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setContentText("Kupnja karte za događaj: " + "\n-Naziv: "+dogadaj.getNaziv() + "\n-Opis: " + dogadaj.getOpis()+"\n-Datum:"+dogadaj.getDatumDogadaja().format(Konstante.DATE_TIME_FORMAT)+"\n-Cijena: "+dogadaj.getCijena()+"\n-Količina: "+dogadaj.getKolicina());
            ButtonType ok = new ButtonType("Da", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("Ne", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(ok, no);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get().equals(ok))
            {
                Serijalizacija<Promjena<Dogadaj, Korisnik>> promjenaSerijalizacija = new Serijalizacija();
                Promjena<Dogadaj, Korisnik> promjena = (new PromjenaBuilder()).setPocetni((Object)null).setKrajnji(dogadaj).setUloga(aktivniKorisnik).setVrijeme(LocalDateTime.now()).createPromjena();
                promjenaSerijalizacija.serializiraj(promjena, "Datoteke/dogadajProdanoPromjene.dat");
                dogadaj.setKolicina(novaKolicina);
                AzurirajDogadajeThread thread = new AzurirajDogadajeThread(dogadaj);
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(thread);
            }
            ocisti();
        }
        catch (BrojException iznimka)
        {
            logger.error("Došlo je do greška pri upisu broja "+iznimka);
        }
        catch (InputMismatchException iznimka)
        {
            logger.error("Došlo je do greška pri broju "+iznimka);
        }
        catch (PrevelikaKolicinaException iznimka)
        {
            logger.error("Došlo je do greška pri broja "+iznimka);
        }
        catch (Exception iznimka)
        {
            logger.error("Došlo je do greške "+iznimka);
        }
        ocisti();
    }
    private void upozorenje()
    {
        String poruka = "Sljedeća polja su prazna:";
        if (kolicinaTextField.getText().isEmpty())
        {
            poruka = poruka + "\n- Količina";
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greška u unosu");
        alert.setHeaderText("Molimo unesite sve potrebne informacije");
        alert.setContentText(poruka);
        alert.showAndWait();
    }
    public void povijestKupnji()
    {
        List<Promjena<Dogadaj,Korisnik>> listaSvihPromjena= (new Serijalizacija()).ucitaj("Datoteke/dogadajProdanoPromjene.dat");
        List<Dogadaj> dogadaji=new ArrayList<>();
        String poruka="";
        for (Promjena<Dogadaj,Korisnik> promjena:listaSvihPromjena)
        {
            if (promjena.getUloga().getKorisnikoIme().equals(aktivniKorisnik.getKorisnikoIme()))
            {
                dogadaji.add(promjena.getKrajnji());
                poruka=poruka+ "\n-Naziv: "+promjena.getKrajnji().getNaziv() + "\n-Opis: " + promjena.getKrajnji().getOpis()+"\n-Datum:"+promjena.getKrajnji().getDatumDogadaja().format(Konstante.DATE_TIME_FORMAT)+"\n-Cijena: "+promjena.getKrajnji().getCijena()+"\n-Količina: "+promjena.getKrajnji().getKolicina()+"\n-Vrijeme kupnje: "+promjena.getVrijeme().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))+"\n";
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Povijest kupnji!");
        alert.setHeaderText("Sve vaše kupljene karte.");
        alert.setContentText(poruka);
        alert.showAndWait();
    }
}
