package Code_Admin;

import DAO.yonetmenlerDAO;
import entity.yonetmenler;
import Pattern.Creator;
import Pattern.Table;
import entity.Center;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class Yonetmenler extends Vizyondaki_Filmler {

    @FXML
    public void yonetmenler_ekle_giris(ActionEvent event) {
        yonetmenler_grid.setVisible(false);
        yonetmenler_ekle_pane.setVisible(true);
        yonetmenler_geri_tusu.setVisible(false);
        yonetmenler_ekle_geri_tusu.setVisible(true);
        yonetmenler_degistir_geri_tusu.setVisible(false);
        yonetmenler_degistir_pane.setVisible(false);
        yonetmenler_ekle_film_sayisi.setText("0");
    }

    @FXML
    public void yonetmenler_ekle(ActionEvent event) {
        if ((yonetmenler_ekle_ad.getText().length() == 0) || (yonetmenler_ekle_soyad.getText().length() == 0) || (yonetmenler_ekle_film_sayisi.getText().length() == 0)) {
            yonetmenler_ekle_uyari_mesaj.setText("Lütfen gerekli yerleri doldurunuz");
        } else {
            String ad = yonetmenler_ekle_ad.getText();
            String soyad = yonetmenler_ekle_soyad.getText();
            int film_sayisi = 0;
            int control = -1;
            try {
                film_sayisi = Integer.parseInt(yonetmenler_ekle_film_sayisi.getText());
                control = 1;
            } catch (NumberFormatException e) {
                yonetmenler_ekle_uyari_mesaj.setText("Lütfen film sayısını sadeca sayı olarak giriniz");
            }
            if (control != -1) {
                yonetmenler y = new yonetmenler(ad, soyad, film_sayisi);
                
                Center nw = new Center(y);

                int sonuc = Creator.yonetmenlerDao().create(nw);

                if (sonuc == 1) {
                    yonetmenler_ekle_uyari_mesaj.setText("İşlem Başarılı Bir şekilde gerçekleşti");
                } else {
                    yonetmenler_ekle_uyari_mesaj.setText("Bir hata meydana geldi lütfen daha sonra tekrar deneyiniz");
                }
            }
        }

    }

    @FXML
    public void yonetmenler_ekle_sifirla() {
        yonetmenler_ekle_ad.setText("");
        yonetmenler_ekle_soyad.setText("");
        yonetmenler_ekle_film_sayisi.setText("0");
    }

    @FXML
    public void yonetmenler_degistir_sil_giris(ActionEvent event) {
        yonetmenler_grid.setVisible(false);
        yonetmenler_degistir_pane.setVisible(true);
        yonetmen_combo(yonetmenler_degistir_sil_combo, yonetmenler_degistir_sil_uyari_mesaj_1);
        yonetmenler_degistir_pane_1.setVisible(true);
        yonetmenler_degistir_pane_2.setVisible(false);
        yonetmenler_degistir_geri_tusu.setVisible(true);
        yonetmenler_geri_tusu.setVisible(false);
        yonetmenler_degistir_sil_emin_misin.setVisible(false);

    }

    @FXML
    public void yonetmenler_degistir_sil_yonetmen_getir(ActionEvent event) {
        if (yonetmenler_degistir_sil_combo.getValue() == null) {
            yonetmenler_degistir_sil_uyari_mesaj_1.setText("Lütfen bir değer seçiniz");
        } else {

            String yonetmen = yonetmenler_degistir_sil_combo.getValue();

            String arr[][] = Creator.yonetmenlerDao().select();

            int yonetmen_id = 0;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i][0].equals(yonetmen)) {
                    yonetmen_id = Integer.valueOf(arr[i][1]);
                    break;
                }
            }
            if (yonetmen_id != 0) {
                yonetmenler_degistir_pane_2.setVisible(true);
                yonetmenler_degistir_sil_ad.setText(Creator.yonetmenlerDao().search_string(yonetmen_id,1));
                yonetmenler_degistir_sil_soyad.setText(Creator.yonetmenlerDao().search_string(yonetmen_id,2));
                yonetmenler_degistir_sil_film_sayisi.setText(String.valueOf(Creator.yonetmenlerDao().search_int(yonetmen_id)));
                yonetmen_degistir_sil_yonetmen_id.setText(String.valueOf(yonetmen_id));
            } else {
                yonetmenler_degistir_sil_uyari_mesaj_1.setText("Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz. ");
            }
        }

    }

    @FXML
    public void yonetmenler_degistir_sil_degistir(ActionEvent event) {
        if ((yonetmenler_degistir_sil_ad.getText().length() == 0) || (yonetmenler_degistir_sil_soyad.getText().length() == 0) || (yonetmenler_degistir_sil_film_sayisi.getText().length() == 0)) {
            yonetmenler_degistir_sil_uyari_mesaj_2.setText("Lütfen Gerekli Yerleri Doldurunuz. ");
        } else {
            try {
                int film_sayisi = Integer.parseInt(yonetmenler_degistir_sil_film_sayisi.getText());
                String ad = yonetmenler_degistir_sil_ad.getText();
                String soyad = yonetmenler_degistir_sil_soyad.getText();
                int yonetmen_id = Integer.parseInt(yonetmen_degistir_sil_yonetmen_id.getText());
                yonetmenler y = new yonetmenler(yonetmen_id, ad, soyad, film_sayisi);
                
                Center nw = new Center(y);

                int sonuc = Creator.yonetmenlerDao().update(nw);

                if (sonuc == 1) {
                    yonetmenler_degistir_sil_uyari_mesaj_2.setText("İşlem Başarılı bir şekilde gerçekleştirildi ");
                } else {
                    yonetmenler_degistir_sil_uyari_mesaj_2.setText("Bir hata meydana geldi lütfen daha sonra tekrar deneyiniz. ");
                }

            } catch (NumberFormatException e) {
                yonetmenler_degistir_sil_uyari_mesaj_2.setText("Lütfen Film sayısını sayı olarak giriniz.");
            }
        }
    }

    @FXML
    public void yonetmenler_degistir_sil_sil(ActionEvent event) {
        yonetmenler_degistir_sil_emin_misin.setVisible(true);
    }

    @FXML
    public void yonetmenler_degistir_sil_emin_misin_vazgec(ActionEvent event) {
        yonetmenler_degistir_sil_emin_misin.setVisible(false);
    }

    @FXML
    public void yonetmenler_degistir_sil_emin_misin_sil(ActionEvent event) {
        int yonetmen_id = Integer.parseInt(yonetmen_degistir_sil_yonetmen_id.getText());

        int sonuc = Creator.yonetmenlerDao().delete(yonetmen_id);

        yonetmenler_degistir_sil_emin_misin.setVisible(false);

        if (sonuc == 1) {
            yonetmenler_degistir_sil_ad.setText("");
            yonetmenler_degistir_sil_soyad.setText("");
            yonetmenler_degistir_sil_film_sayisi.setText("");

            yonetmen_combo(yonetmenler_degistir_sil_combo, yonetmenler_degistir_sil_uyari_mesaj_1);

            yonetmenler_degistir_pane_2.setVisible(false);

            yonetmenler_degistir_sil_uyari_mesaj_1.setText("İşlem Başarılı Bir şekilde gerçekleştirildi.");
        } else {
            yonetmenler_degistir_sil_uyari_mesaj_2.setText("Bir hata meydana geldi. Lütfen daha sonra tekrar deneyiniz.");
        }
    }

    @FXML
    public void yonetmenler_ekle_geri(MouseEvent event) {
        yonetmenler_grid.setVisible(true);
        yonetmenler_ekle_pane.setVisible(false);

        yonetmenler_geri_tusu.setVisible(true);
        yonetmenler_ekle_geri_tusu.setVisible(false);

        yonetmenler_table();
    }

    @FXML
    public void yonetmenler_geri(MouseEvent event) {
        geri_don_admin();
    }

    @FXML
    public void yonetmenler_degistir_geri(MouseEvent event) {
        yonetmenler_grid.setVisible(true);
        yonetmenler_degistir_pane.setVisible(false);

        yonetmenler_degistir_geri_tusu.setVisible(false);
        yonetmenler_geri_tusu.setVisible(true);

        yonetmenler_table();

    }

    public void yonetmenler_table() {

        yonetmenlerDAO ydao = new yonetmenlerDAO();

        ObservableList<yonetmenler> data = Table.data_Yonetmenler();

        yonetmenler_yonetmen_id.setCellValueFactory(new PropertyValueFactory("yonetmen_id"));
        yonetmenler_ad.setCellValueFactory(new PropertyValueFactory("yonetmen_name"));
        yonetmenler_soyad.setCellValueFactory(new PropertyValueFactory("yonetmen_sur_name"));
        yonetmenler_film_sayisi.setCellValueFactory(new PropertyValueFactory("film_sayisi"));

        FilteredList<yonetmenler> filteredData = new FilteredList<>(data, b -> true);

        filterField_yonetmenler.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(yon -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(yon.getYonetmen_id()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (yon.getYonetmen_name().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (yon.getYonetmen_sur_name().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(yon.getFilm_sayisi()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }

            });
        });

        SortedList<yonetmenler> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(table_yonetmenler.comparatorProperty());

        table_yonetmenler.setItems(sortedData);
    }
}
