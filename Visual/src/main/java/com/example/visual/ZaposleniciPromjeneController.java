package com.example.visual;

import com.example.visual.production.Entiteti.*;
import com.example.visual.production.Konstante.Konstante;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ZaposleniciPromjeneController {
    @FXML
    private TableView<Smjestaj> smjestajTableView;
    @FXML
    private TableColumn<Smjestaj, String> smjestajNazivTableColumn;
    @FXML
    private TableColumn<Smjestaj, String> smjestajAdresaTableColumn;
    @FXML
    private TableColumn<Smjestaj, String> smjestajVrstaTableColumn;
    @FXML
    private TableColumn<Smjestaj, String> smjestajRedovnaCijenaTableColumn;
    @FXML
    private TableColumn<Smjestaj, String> smjestajAkcijskaCijenaTableColumn;
    @FXML
    private TableView<Dogadaj> dogadajTableView;
    @FXML
    private TableColumn<Dogadaj, String> dogadajNazivTableColumn;
    @FXML
    private TableColumn<Dogadaj, String> dogadajOpisTableColumn;
    @FXML
    private TableColumn<Dogadaj, String> dogadajDatumTableColumn;
    @FXML
    private TableColumn<Dogadaj, String> dogadajCijenaTableColumn;
    @FXML
    private TableColumn<Dogadaj, String> dogadajKolicinaTableColumn;
    @FXML
    private TableView<Zaposlenik> zaposlenikTableView;
    @FXML
    private TableColumn<Zaposlenik, String> zaposlenikImeTableColumn;
    @FXML
    private TableColumn<Zaposlenik, String> zaposlenikPrezimeTableColumn;
    @FXML
    private TableColumn<Zaposlenik, String> zaposlenikKorisnickoImeTableColumn;
    @FXML
    private TableColumn<Zaposlenik, String> zaposlenikDatumRodenjaImeTableColumn;
    @FXML
    private TableColumn<Zaposlenik, String> zaposlenikUlogaTableColumn;
    @FXML
    private TableView<Korisnik> korisnikTableView;
    @FXML
    private TableColumn<Korisnik, String> korisnikImeTableColumn;
    @FXML
    private TableColumn<Korisnik, String> korisnikPrezimeTableColumn;
    @FXML
    private TableColumn<Korisnik, String> korisnikKorisnickoImeTableColumn;
    @FXML
    private TableColumn<Korisnik, String> korisnikDatumRodenjaImeTableColumn;
    @FXML
    private TableColumn<Korisnik, String> korisnikAdresaTableColumn;
    @FXML
    private TableView<Dogadaj> dogadajprodanoTableView;
    @FXML
    private TableColumn<Dogadaj, String> dogadajprodanoNazivTableColumn;
    @FXML
    private TableColumn<Dogadaj, String> dogadajprodanoOpisTableColumn;
    @FXML
    private TableColumn<Dogadaj, String> dogadajprodanoDatumTableColumn;
    @FXML
    private TableColumn<Dogadaj, String> dogadajprodanoCijenaTableColumn;
    @FXML
    private TableColumn<Dogadaj, String> dogadajprodanoKolicinaTableColumn;
    private List<Promjena<Smjestaj, Zaposlenik>> listaPromjenaSmjestaj = new ArrayList<>();
    private List<Promjena<Dogadaj, Zaposlenik>> listaPromjenaDogadaj = new ArrayList<>();
    private List<Promjena<Korisnik, Zaposlenik>> listaPromjenaKorisnik = new ArrayList<>();
    private List<Promjena<Zaposlenik, Zaposlenik>> listaPromjenaZaposlenik = new ArrayList<>();
    private List<Promjena<Dogadaj, Korisnik>> listaProdajaDogadajKorisnik = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(Pokreni.class);

    public void initialize() {
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
        smjestajRedovnaCijenaTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Smjestaj, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Smjestaj, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getRegularnaCijena().toString());
            }
        });
        smjestajAkcijskaCijenaTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Smjestaj, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Smjestaj, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getSnizenaCijena().toString());
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
        dogadajDatumTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Dogadaj, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Dogadaj, String> param) {
                LocalDate datum = param.getValue().getDatumDogadaja();
                String datumString = datum.format(Konstante.DATE_TIME_FORMAT);
                return new ReadOnlyStringWrapper(datumString);
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
        zaposlenikDatumRodenjaImeTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Zaposlenik, String>, ObservableValue<String>>() {
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
        korisnikDatumRodenjaImeTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Korisnik, String>, ObservableValue<String>>() {
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
        dogadajprodanoNazivTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Dogadaj, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Dogadaj, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getNaziv());
            }
        });
        dogadajprodanoOpisTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Dogadaj, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Dogadaj, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getOpis());
            }
        });
        dogadajprodanoCijenaTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Dogadaj, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Dogadaj, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getCijena().toString());
            }
        });
        dogadajprodanoKolicinaTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Dogadaj, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Dogadaj, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getKolicina().toString());
            }
        });
        dogadajprodanoDatumTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Dogadaj, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Dogadaj, String> param) {
                LocalDate datum = param.getValue().getDatumDogadaja();
                String datumString = datum.format(Konstante.DATE_TIME_FORMAT);
                return new ReadOnlyStringWrapper(datumString);
            }
        });

        this.listaPromjenaSmjestaj = (new Serijalizacija()).ucitaj("Datoteke/smjestajPromjene.dat");
        List<Smjestaj> noviSmjestajLista = new ArrayList();
        this.listaPromjenaSmjestaj.forEach((promjena) -> {
            noviSmjestajLista.add((Smjestaj) promjena.getKrajnji());
        });
        this.smjestajTableView.setItems(FXCollections.observableArrayList(noviSmjestajLista));

        this.listaPromjenaDogadaj = (new Serijalizacija()).ucitaj("Datoteke/dogadajPromjene.dat");
        List<Dogadaj> noviDogadajLista = new ArrayList();
        this.listaPromjenaDogadaj.forEach((promjena) -> {
            noviDogadajLista.add((Dogadaj) promjena.getKrajnji());
        });
        this.dogadajTableView.setItems(FXCollections.observableArrayList(noviDogadajLista));

        this.listaPromjenaKorisnik = (new Serijalizacija()).ucitaj("Datoteke/korisnikPromjene.dat");
        List<Korisnik> noviKorisnikLista = new ArrayList();
        this.listaPromjenaKorisnik.forEach((promjena) -> {
            noviKorisnikLista.add((Korisnik) promjena.getKrajnji());
        });
        this.korisnikTableView.setItems(FXCollections.observableArrayList(noviKorisnikLista));

        this.listaPromjenaZaposlenik = (new Serijalizacija()).ucitaj("Datoteke/zaposlenikPromjene.dat");
        List<Zaposlenik> noviZaposlenikLista = new ArrayList();
        this.listaPromjenaZaposlenik.forEach((promjena) -> {
            noviZaposlenikLista.add((Zaposlenik) promjena.getKrajnji());
        });
        this.zaposlenikTableView.setItems(FXCollections.observableArrayList(noviZaposlenikLista));

        this.listaProdajaDogadajKorisnik = (new Serijalizacija()).ucitaj("Datoteke/dogadajProdanoPromjene.dat");
        List<Dogadaj> noviProdaniDogadajList = new ArrayList();
        this.listaProdajaDogadajKorisnik.forEach((promjena) -> {
            noviProdaniDogadajList.add((Dogadaj) promjena.getKrajnji());
        });
        this.dogadajprodanoTableView.setItems(FXCollections.observableArrayList(noviProdaniDogadajList));
    }
    public void odaberiKupljenuKartu()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Podaci prodaje");
        Promjena promjena = (Promjena)this.listaProdajaDogadajKorisnik.get(this.dogadajprodanoTableView.getSelectionModel().getSelectedIndex());
        alert.setHeaderText("Kupljen od strane korisnika: " + ((Korisnik)promjena.getUloga()).getKorisnikoIme());
        Dogadaj pocetni = (Dogadaj) ((Promjena)this.listaProdajaDogadajKorisnik.get(this.dogadajprodanoTableView.getSelectionModel().getSelectedIndex())).getPocetni();
        Dogadaj finalniDogadaj=(Dogadaj) ((Promjena)this.listaProdajaDogadajKorisnik.get(this.dogadajprodanoTableView.getSelectionModel().getSelectedIndex())).getKrajnji();
        LocalDateTime vrijeme = ((Promjena)this.listaProdajaDogadajKorisnik.get(this.dogadajprodanoTableView.getSelectionModel().getSelectedIndex())).getVrijeme();
        if (pocetni == null)
        {
            String tekst = "Vrijeme kupnje: " + vrijeme.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            alert.setContentText(tekst);
            alert.showAndWait();
        }
    }

    public void odaberiDogadaj()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Prijašnje vrijednosti");
        Promjena promjena = (Promjena)this.listaPromjenaDogadaj.get(this.dogadajTableView.getSelectionModel().getSelectedIndex());
        alert.setHeaderText("Promijenjen od strane zaposlenika: " + ((Zaposlenik)promjena.getUloga()).getKorisnikoIme());
        Dogadaj pocetni = (Dogadaj) ((Promjena)this.listaPromjenaDogadaj.get(this.dogadajTableView.getSelectionModel().getSelectedIndex())).getPocetni();
        Dogadaj finalniDogadaj=(Dogadaj) ((Promjena)this.listaPromjenaDogadaj.get(this.dogadajTableView.getSelectionModel().getSelectedIndex())).getKrajnji();
        LocalDateTime vrijeme = ((Promjena)this.listaPromjenaDogadaj.get(this.dogadajTableView.getSelectionModel().getSelectedIndex())).getVrijeme();
        if (pocetni == null)
        {
            String tekst = "Unesen je novi događaj: " + "\n-Naziv: " + finalniDogadaj.getNaziv() + "\n-Opis: " + finalniDogadaj.getOpis() + "\n-Datum:" + finalniDogadaj.getDatumDogadaja().format(Konstante.DATE_TIME_FORMAT) + "\n-Cijena: " + finalniDogadaj.getCijena() + "\n-Količina: " + finalniDogadaj.getKolicina()
                    + "\n Vrijeme promjene: " + vrijeme.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            alert.setContentText(tekst);
            alert.showAndWait();
        }
        else
        {
            String tekst = "Stari podaci događaja: " + "\n-Naziv: " + pocetni.getNaziv() + "\n-Opis: " + pocetni.getOpis() + "\n-Datum:" + pocetni.getDatumDogadaja().format(Konstante.DATE_TIME_FORMAT) + "\n-Cijena: " + pocetni.getCijena() + "\n-Količina: " + pocetni.getKolicina()
                    + "\n Vrijeme promjene: " + vrijeme.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            alert.setContentText(tekst);
            alert.showAndWait();
        }
    }
    public void odaberiSmjestaja()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Prijašnje vrijednosti");
        Promjena promjena = (Promjena)this.listaPromjenaSmjestaj.get(this.smjestajTableView.getSelectionModel().getSelectedIndex());
        alert.setHeaderText("Promijenjen od strane zaposlenika: " + ((Zaposlenik)promjena.getUloga()).getKorisnikoIme());
        Smjestaj pocetni = (Smjestaj) ((Promjena)this.listaPromjenaSmjestaj.get(this.smjestajTableView.getSelectionModel().getSelectedIndex())).getPocetni();
        Smjestaj finalniSmjestaj=(Smjestaj) ((Promjena)this.listaPromjenaSmjestaj.get(this.smjestajTableView.getSelectionModel().getSelectedIndex())).getKrajnji();
        LocalDateTime vrijeme = ((Promjena)this.listaPromjenaSmjestaj.get(this.smjestajTableView.getSelectionModel().getSelectedIndex())).getVrijeme();
        if (pocetni == null)
        {
            String tekst = "Unesen je novi smještaj: " + "\n-Naziv: " + finalniSmjestaj.getNaziv() + "\n-Adresa: " + finalniSmjestaj.getAdresa() + "\nVrsta smještaja: " + finalniSmjestaj.getVrstaSmjestaja().getOpis() + "\n-Regularna cijena: " + finalniSmjestaj.getRegularnaCijena() + "\n-Popust:" + finalniSmjestaj.getPopust().iznos() + "\n-Snižena cijena: " + finalniSmjestaj.getSnizenaCijena()
                    + "\n Vrijeme promjene: " + vrijeme.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            alert.setContentText(tekst);alert.setContentText(tekst);
            alert.showAndWait();
        }
        else
        {
            String tekst = "Stari podaci smještaja: " + "\n-Naziv: " + pocetni.getNaziv() + "\n-Adresa: " + pocetni.getAdresa() + "\nVrsta smještaja: " + pocetni.getVrstaSmjestaja().getOpis() + "\n-Regularna cijena: " + pocetni.getRegularnaCijena() + "\n-Popust:" + pocetni.getPopust().iznos() + "\n-Snižena cijena: " + pocetni.getSnizenaCijena()
                    + "\n Vrijeme promjene: " + vrijeme.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            alert.setContentText(tekst);
            alert.showAndWait();
        }
    }
    public void odaberiZaposlenika()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Prijašnje vrijednosti");
        Promjena promjena = (Promjena)this.listaPromjenaZaposlenik.get(this.zaposlenikTableView.getSelectionModel().getSelectedIndex());
        alert.setHeaderText("Promijenjen od strane zaposlenika: " + ((Zaposlenik)promjena.getUloga()).getKorisnikoIme());
        Zaposlenik pocetni = (Zaposlenik) ((Promjena)this.listaPromjenaZaposlenik.get(this.zaposlenikTableView.getSelectionModel().getSelectedIndex())).getPocetni();
        Zaposlenik finalniZaposlenik=(Zaposlenik) ((Promjena)this.listaPromjenaZaposlenik.get(this.zaposlenikTableView.getSelectionModel().getSelectedIndex())).getKrajnji();
        LocalDateTime vrijeme = ((Promjena)this.listaPromjenaZaposlenik.get(this.zaposlenikTableView.getSelectionModel().getSelectedIndex())).getVrijeme();
        if (pocetni == null)
        {
            String tekst = "Unesen je novi zaposlenik: " +"\n-Ime: "+finalniZaposlenik.getIme() + "\n-Prezime: " + finalniZaposlenik.getPrezime()+"\n-Korisničko ime: "+finalniZaposlenik.getKorisnikoIme()+"\n-Datum rođenja: "+finalniZaposlenik.getDatumRodjenja().format(Konstante.DATE_TIME_FORMAT)+"\n-Uloga: "+finalniZaposlenik.getUloga().getOpis()
                    + "\n Vrijeme promjene: " + vrijeme.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            alert.setContentText(tekst);
            alert.showAndWait();
        }
        else
        {
            String tekst = "Stari podaci zaposlenika: " +"\n-Ime: "+pocetni.getIme() + "\n-Prezime: " + pocetni.getPrezime()+"\n-Korisničko ime: "+pocetni.getKorisnikoIme()+"\n-Datum rođenja: "+pocetni.getDatumRodjenja().format(Konstante.DATE_TIME_FORMAT)+"\n-Uloga: "+pocetni.getUloga().getOpis()
                    + "\n Vrijeme promjene: " + vrijeme.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            alert.setContentText(tekst);
            alert.showAndWait();
        }
    }
    public void odaberiKorisnika()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Prijašnje vrijednosti");
        Promjena promjena = (Promjena)this.listaPromjenaKorisnik.get(this.korisnikTableView.getSelectionModel().getSelectedIndex());
        alert.setHeaderText("Promijenjen od strane zaposlenika: " + ((Zaposlenik)promjena.getUloga()).getKorisnikoIme());
        Korisnik pocetni = (Korisnik) ((Promjena)this.listaPromjenaKorisnik.get(this.korisnikTableView.getSelectionModel().getSelectedIndex())).getPocetni();
        Korisnik finalniKorisnik=(Korisnik) ((Promjena)this.listaPromjenaKorisnik.get(this.korisnikTableView.getSelectionModel().getSelectedIndex())).getKrajnji();
        LocalDateTime vrijeme = ((Promjena)this.listaPromjenaKorisnik.get(this.korisnikTableView.getSelectionModel().getSelectedIndex())).getVrijeme();
        if (pocetni == null)
        {
            String tekst = "Unesen je novi korisnik: " + "\n-Ime: " + finalniKorisnik.getIme() + "\n-Prezime: " + finalniKorisnik.getPrezime() + "\n-Korisničko ime: " + finalniKorisnik.getKorisnikoIme() + "\n-Datum rođenja: " + finalniKorisnik.getDatumRodjenja().format(Konstante.DATE_TIME_FORMAT) + "\n-Adresa: " + finalniKorisnik.getAdresa()
                    + "\n Vrijeme promjene: " + vrijeme.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            alert.setContentText(tekst);
            alert.showAndWait();
        }
        else
        {
            String tekst = "Stari podaci korisnika: " + "\n-Ime: " + pocetni.getIme() + "\n-Prezime: " + pocetni.getPrezime() + "\n-Korisničko ime: " + pocetni.getKorisnikoIme() + "\n-Datum rođenja: " + pocetni.getDatumRodjenja().format(Konstante.DATE_TIME_FORMAT) + "\n-Adresa: " + pocetni.getAdresa()
                    + "\n Vrijeme promjene: " + vrijeme.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            alert.setContentText(tekst);
            alert.showAndWait();
        }
    }
}