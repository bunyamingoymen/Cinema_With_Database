package entity;

import java.time.LocalDate;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class haberler extends duyurular {

    private Button sil;

    public haberler() {
    }

    public haberler(int haber_id, int hangi_kullanici_turu, String Title, String Haber, LocalDate Tarih, String Haber_Kategorisi, Button sil, Label haberler_silmekten_emin_haber_id, Pane haberler_sil_emin_misin_pane) {
        super(haber_id, hangi_kullanici_turu, Title, Haber, Tarih, Haber_Kategorisi);

        haber_controller(sil, haberler_silmekten_emin_haber_id, haberler_sil_emin_misin_pane);

    }

    public void haber_controller(Button sil, Label haberler_silmekten_emin_haber_id, Pane haberler_sil_emin_misin_pane) {
        this.sil = sil;

        this.sil.setOnAction(e -> {
            haberler_silmekten_emin_haber_id.setText(String.valueOf(super.getId()));
            haberler_sil_emin_misin_pane.setVisible(true);
        });
    }

    public haberler(int haber_id, int hangi_kullanici_turu, String Title, String Haber, LocalDate Tarih, String Haber_Kategorisi) {
        super(haber_id, hangi_kullanici_turu, Title, Haber, Tarih, Haber_Kategorisi);
    }

    public haberler(int hangi_kullanici_turu, String Title, String Haber, LocalDate Tarih, String Haber_Kategorisi) {
        super(hangi_kullanici_turu, Title, Haber, Tarih, Haber_Kategorisi);
    }

    public haberler(String Title, String Haber, LocalDate Tarih, String Haber_Kategorisi) {
        super(Title, Haber, Tarih, Haber_Kategorisi);
    }

    public Button getSil() {
        return sil;
    }

    public void setSil(Button sil) {
        this.sil = sil;
    }

}
