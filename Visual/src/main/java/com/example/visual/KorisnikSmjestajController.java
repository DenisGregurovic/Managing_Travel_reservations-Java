package com.example.visual;

import com.example.visual.production.BazaPodataka.Baza;
import com.example.visual.production.Entiteti.*;
import com.example.visual.production.Iznimke.Oznacene.NemoguceOtvoritiMailException;
import com.example.visual.production.Niti.UcitajDogadajeThread;
import com.example.visual.production.Niti.UcitajSmjestajeThread;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class KorisnikSmjestajController
{
    @FXML
    private TextField pretrazivanjeTextField;
    @FXML
    private Button resetButton;
    @FXML
    private Button kontaktButton;
    @FXML
    private Label nazivLabel;
    @FXML
    private Label vrstaLabel;
    @FXML
    private Label adresaLabel;
    @FXML
    private Label redovnaCijenaLabel;
    @FXML
    private Label akcijskaCijenaLabel;
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
    Korisnik aktivniKorisnik= new Serijalizacija<Korisnik>().ucitajKorisnickiRacun("Datoteke/AktivniKorisnik.dat");
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
        ObservableList<Smjestaj> smjestaji= Baza.dohvatiSmjestaje();
        UcitajSmjestajeThread dohvacanjeNit = new UcitajSmjestajeThread(smjestaji);
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(dohvacanjeNit, 0, 10, TimeUnit.SECONDS);
        smjestajTableView.setItems(smjestaji);
        pretrazivanjeTextField.textProperty().addListener((ObservableList,oldValue,newValue) -> {pretrazi(newValue);});
        ocisti();
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
    public void ocisti()
    {
        pretrazivanjeTextField.clear();
        resetButton.setDisable(true);
        kontaktButton.setDisable(true);
        nazivLabel.setText("Naziv");
        adresaLabel.setText("Adresa");
        redovnaCijenaLabel.setText("Redovna cijena");
        akcijskaCijenaLabel.setText("Akcijska cijena");
        vrstaLabel.setText("Vrsta smještaja");
        kontaktButton.setDisable(true);
        resetButton.setDisable(true);
    }
    public void mouseClicked()
    {
        try
        {
            Smjestaj smjestaj=smjestajTableView.getSelectionModel().getSelectedItem();
            smjestaj=new Smjestaj(smjestaj.getId(), smjestaj.getNaziv(),smjestaj.getAdresa(),smjestaj.getVrstaSmjestaja(),smjestaj.getRegularnaCijena(),smjestaj.getPopust(),smjestaj.getSnizenaCijena());
            this.smjestaj=smjestaj;
            nazivLabel.setText(smjestaj.getNaziv());
            adresaLabel.setText(smjestaj.getAdresa());
            redovnaCijenaLabel.setText(smjestaj.getRegularnaCijena().toString());
            akcijskaCijenaLabel.setText(smjestaj.getRegularnaCijena().toString());
            vrstaLabel.setText(smjestaj.getVrstaSmjestaja().getOpis());
            resetButton.setDisable(false);
            kontaktButton.setDisable(false);
        }
        catch (Exception e)
        {
            logger.error("Ne moze se odabrati smjestaj. "+e);
        }
    }
    public void kontaktiraj() throws URISyntaxException, IOException {
        Desktop desktop;
        if (Desktop.isDesktopSupported() && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL))
        {
            URI mailto = new URI("mailto:gregurovicdenis@gmail.com" );
            desktop.mail(mailto);
        } else {
            throw new NemoguceOtvoritiMailException();
        }
    }
}
