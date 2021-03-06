package Code_Admin;

import DAO.filmlerDAO;
import DAO.seansDAO;
import DAO.sinema_salonlariDAO;
import DAO.vizyondaki_filmlerDAO;
import DAO.yonetmenlerDAO;
import entity.seans;
import Pattern.Creator;
import Pattern.Table;
import entity.Center;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class Seans extends Kullanici_islemleri {

    public void saat_combo(ComboBox<String> combo) {

        combo.getItems().clear();

        combo.getItems().addAll("10.00-12.00");
        combo.getItems().addAll("12.30-13.30");
        combo.getItems().addAll("14.00-16.00");
        combo.getItems().addAll("16.30-18.30");
        combo.getItems().addAll("19.00-21.00");

        combo.setPromptText("İstediğiniz saati seçiniz.");
    }

    public void seans_combo(ComboBox<String> combo, Label uyari_mesaj) {
        String[] arr = Creator.seansDao().select();
        combo.getItems().clear();
        if (arr.length == 0) {
            uyari_mesaj.setText("Kayıtlı Vizyondaki Film Bulunamadı. Lütfen önce bir vizyona film ekleyiniz ekleyiniz.");

        } else {
            for (String s : arr) {
                combo.getItems().addAll(s);
            }

            combo.setPromptText("İstediğiniz filmi seçiniz.");
        }
    }

    @FXML
    public void seans_islemleri_giris(ActionEvent event) {
        pnl_vizyondaki_filmler.setVisible(false);
        pnl_seans.setVisible(true);

        seans_grid.setVisible(true);
        seans_ekle_pane.setVisible(false);
        seans_degistir_sil_pane.setVisible(false);

        seans_geri_tusu.setVisible(true);
        seans_ekle_geri_tusu.setVisible(false);
        seans_degistir_geri_tusu.setVisible(false);
        
        seans_table();

    }

    public void seans_table() {

        ObservableList<seans> data = Table.data_Seans();

        seans_film_adi.setCellValueFactory(new PropertyValueFactory("film_name"));
        seans_salon_adi.setCellValueFactory(new PropertyValueFactory("salon_name"));
        seans_saat.setCellValueFactory(new PropertyValueFactory("saat"));

        FilteredList<seans> filteredData = new FilteredList<>(data, b -> true);

        filterField_seans.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(sea -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (sea.getFilm_name().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (sea.getSalon_name().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (sea.getSaat().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }

            });
        });

        SortedList<seans> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table_seans.comparatorProperty());

        table_seans.setItems(sortedData);
    }

    @FXML
    public void seans_ekle_giris(ActionEvent event) {
        seans_grid.setVisible(false);
        seans_ekle_pane.setVisible(true);
        seans_degistir_sil_pane.setVisible(false);

        seans_degistir_geri_tusu.setVisible(false);
        seans_ekle_geri_tusu.setVisible(true);
        seans_geri_tusu.setVisible(false);

        vizyondaki_filmler_combo(seans_ekle_film_combo, seans_ekle_uyari_mesaj);
        sinema_salonlari_goruntule_combo(seans_ekle_salon_combo, seans_ekle_uyari_mesaj);
        saat_combo(seans_ekle_saat_combo);
    }

    @FXML
    public void seans_degistir_sil_giris(ActionEvent event) {
        seans_grid.setVisible(false);
        seans_ekle_pane.setVisible(false);
        seans_degistir_sil_pane.setVisible(true);
        seans_degistir_sil_pane_2.setVisible(false);
        seans_degistir_sil_silmekten_emin_pane.setVisible(false);

        seans_degistir_geri_tusu.setVisible(true);
        seans_ekle_geri_tusu.setVisible(false);
        seans_geri_tusu.setVisible(false);

        seans_degistir_sil_film_combo.getItems().clear();
        seans_degistir_sil_salon_combo.getItems().clear();
        seans_degistir_sil_saat_combo.getItems().clear();

        seans_combo(seans_degistir_seans_getir_combo, seans_degistir_sil_uyari_mesaj_1);

    }

    @FXML
    public void seans_degistir_sil_seans_getir(ActionEvent event) {
        if (seans_degistir_seans_getir_combo.getValue() == null) {
            seans_degistir_sil_uyari_mesaj_1.setText("Lütfen bir seans seçiniz.");
        } else {
            String[] secilen = seans_degistir_seans_getir_combo.getValue().split(" | ");
            int seans_id = Integer.parseInt(secilen[0]);
            seans_degistir_sil_seans_id.setText(String.valueOf(seans_id));
            int vizyondaki_film_id = Creator.seansDao().search_int(seans_id, 1);
            int film_id = Creator.vizyondaki_filmlerDao().search_int(vizyondaki_film_id, 1, 1);

            String secilen_film = vizyondaki_film_id + " | " + Creator.vizyondaki_filmlerDao().search_string(vizyondaki_film_id, 1) + " | " + Creator.vizyondaki_filmlerDao().search_string(film_id, 2) + " | " + Creator.vizyondaki_filmlerDao().search_int(film_id, 2, 1) + " | " + Creator.vizyondaki_filmlerDao().search_string(Creator.vizyondaki_filmlerDao().search_int(vizyondaki_film_id, 4, 1), 3);

            vizyondaki_filmler_combo(seans_degistir_sil_film_combo, seans_degistir_sil_uyari_mesaj_2);
            seans_degistir_sil_film_combo.setValue(secilen_film);

            int salon_id = Creator.seansDao().search_int(seans_id, 2);

            String secilen_salon = salon_id + " | " + Creator.sinema_salonlariDao().search_string(salon_id) + " | " + Creator.sinema_salonlariDao().search_int(salon_id);

            sinema_salonlari_goruntule_combo(seans_degistir_sil_salon_combo, seans_degistir_sil_uyari_mesaj_2);
            seans_degistir_sil_salon_combo.setValue(secilen_salon);

            String saat = Creator.seansDao().search_string(seans_id, 3);

            saat_combo(seans_degistir_sil_saat_combo);
            seans_degistir_sil_saat_combo.setValue(saat);

            seans_degistir_sil_pane_2.setVisible(true);
        }
    }

    @FXML
    public void seans_degsitir_sil_sil(ActionEvent event) {
        seans_degistir_sil_silmekten_emin_pane.setVisible(true);
    }

    @FXML
    public void seans_degistir_sil_silmekten_emin_sil(ActionEvent event) {
        seansDAO sdao = new seansDAO();
        int sonuc = sdao.delete(Integer.parseInt(seans_degistir_sil_seans_id.getText()));

        if (sonuc == 1) {
            seans_degistir_sil_pane_2.setVisible(false);
            seans_combo(seans_degistir_seans_getir_combo, seans_degistir_sil_uyari_mesaj_1);
            seans_degistir_sil_uyari_mesaj_1.setText("İşlem Başarılı bir şekilde gerçekleşti.");

            seans_degistir_sil_silmekten_emin_pane.setVisible(false);
        } else {
            seans_degistir_sil_uyari_mesaj_2.setText("Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz.");
            seans_degistir_sil_silmekten_emin_pane.setVisible(false);
        }
    }

    @FXML
    public void seans_degistir_sil_silmekten_emin_vazgec(ActionEvent event) {
        seans_degistir_sil_silmekten_emin_pane.setVisible(false);
    }

    @FXML
    public void seans_degistir_sil_degistir(ActionEvent event) {
        if ((seans_degistir_sil_film_combo.getValue().length() == 0) || (seans_degistir_sil_salon_combo.getValue().length() == 0) || (seans_degistir_sil_saat_combo.getValue().length() == 0)) {
            seans_degistir_sil_uyari_mesaj_2.setText("Lütfen gerekli yerleri doldurunuz.");
        } else {
            String[] secilen_film = seans_degistir_sil_film_combo.getValue().split(" | ");
            int vizyondaki_film_id = Integer.parseInt(secilen_film[0]);

            String[] secilen_salon = seans_degistir_sil_salon_combo.getValue().split(" | ");
            int salon_id = Integer.parseInt(secilen_salon[0]);

            String saat = seans_degistir_sil_saat_combo.getValue();

            int seans_id = Integer.parseInt(seans_degistir_sil_seans_id.getText());

            seans s = new seans(seans_id, salon_id, vizyondaki_film_id, saat);

            seansDAO sdao = new seansDAO();

            Center nw = new Center(s);

            int sonuc = sdao.update(nw);

            switch (sonuc) {
                case 1:
                    seans_degistir_sil_uyari_mesaj_2.setText("İşlem Başarılı Bir Şekilde Gerçekleştirildi.");
                    break;
                case -1:
                    seans_degistir_sil_uyari_mesaj_2.setText("Aynı salon ve aynı satte başka film oynamaktadır.");
                    break;
                default:
                    seans_degistir_sil_uyari_mesaj_2.setText("Bir hata meydana geldi lütfen daha sonra tekrar deneyiniz.");
                    break;
            }
        }
    }

    @FXML
    public void seans_ekle_ekle(ActionEvent event) {
        if ((seans_ekle_film_combo.getValue() == null) || (seans_ekle_salon_combo.getValue() == null) || (seans_ekle_saat_combo == null)) {
            seans_ekle_uyari_mesaj.setText("Lütfen gerekli bilgileir seçiniz.");
        } else {
            seansDAO sdao = new seansDAO();
            String[] secilen_vizyondaki_film = seans_ekle_film_combo.getValue().split(" | ");
            int vizyondaki_film_id = Integer.parseInt(secilen_vizyondaki_film[0]);

            String[] secilen_salon = seans_ekle_salon_combo.getValue().split(" | ");
            int salon_id = Integer.parseInt(secilen_salon[0]);

            String saat = seans_ekle_saat_combo.getValue();

            seans s = new seans(salon_id, vizyondaki_film_id, saat);
            Center nw = new Center(s);
            int sonuc = sdao.create(nw);
            switch (sonuc) {
                case 1:

                    vizyondaki_filmler_combo(seans_ekle_film_combo, seans_ekle_uyari_mesaj);
                    sinema_salonlari_goruntule_combo(seans_ekle_salon_combo, seans_ekle_uyari_mesaj);
                    saat_combo(seans_ekle_saat_combo);

                    seans_ekle_uyari_mesaj.setText("İşlem Başarılı bir şekilde gerçekleştirldi.");

                    break;
                case -1:
                    seans_ekle_uyari_mesaj.setText("Bu saate aynı salonda seans zaten var. Lütfen ya farklı salo seçiniz ya da farklı saat seçiniz.");
                    break;
                default:
                    seans_ekle_uyari_mesaj.setText("Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz.");
                    break;
            }

        }
    }

    @FXML
    public void seans_ekle_sifirla(ActionEvent event) {
        vizyondaki_filmler_combo(seans_ekle_film_combo, seans_ekle_uyari_mesaj);
        sinema_salonlari_goruntule_combo(seans_ekle_salon_combo, seans_ekle_uyari_mesaj);
        saat_combo(seans_ekle_saat_combo);
    }

    @FXML
    public void seans_geri(MouseEvent event) {
        pnl_seans.setVisible(false);
        pnl_vizyondaki_filmler.setVisible(true);

        vizyondaki_filmler_resimli_gosterim.setVisible(true);
        vizyondaki_filmler_ekle_pane.setVisible(false);
        //vizyondaki_filmler_degistir_pane.setVisible(false);
        vizyondaki_filmler_grid.setVisible(false);

        vizyondaki_filmler_geri_tusu.setVisible(true);
        vizyondaki_filmler_ekle_geri_tusu.setVisible(false);
        //vizyondaki_filmler_degistir_geri_tusu.setVisible(false);

    }

    @FXML
    public void seans_ekle_geri(MouseEvent event) {
        seans_grid.setVisible(true);
        seans_ekle_pane.setVisible(false);

        seans_geri_tusu.setVisible(true);
        seans_ekle_geri_tusu.setVisible(false);

        seans_table();
    }

    @FXML
    public void seans_degistir_geri(MouseEvent event) {
        seans_grid.setVisible(true);
        seans_degistir_sil_pane.setVisible(false);

        seans_degistir_geri_tusu.setVisible(false);
        seans_geri_tusu.setVisible(true);

        seans_table();
    }
}
