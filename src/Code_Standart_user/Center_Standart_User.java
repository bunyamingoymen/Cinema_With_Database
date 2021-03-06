package Code_Standart_user;

import Pattern.Creator;
import entity.*;
import DAO.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Center_Standart_User extends Abonelik implements Initializable {

    //////////////////////////////////////////////////////////////////////////
    //hem appController da hem de appStandart_userController da karışık ve ortak olanlar için ayrılan alan
    //program da aktif olarak çalışan paneler
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //bu method şifreyi gösterme işine yarıyor. Kullanıcı şifreyi göster simgesine tıklarsa passwordfield'ın buludnuğu hbox'ı kapatıp textfield'ın bulunduğu hbox'ı açıyor ve passwordfiled'Dan veriyi alıp kendine yazıyor.
    @FXML
    protected void goster(MouseEvent event) {
        acik.setVisible(true);
        sifre_gosterim = true;
        gizli.setVisible(false);
        tf_user_password.setText(user_password.getText());

    }

    //bu method şifreyi gizleme işine yarıyor. Kullanıcı şifreyi gizle simgesine tıklarsa textfield'ın buludnuğu hbox'ı kapatıp passwordfield'ın bulunduğu hbox'ı açıyor ve textfiled'dan veriyi alıp kendine yazıyor.
    @FXML
    protected void gizle(MouseEvent event) {
        gizli.setVisible(true);
        sifre_gosterim = false;
        acik.setVisible(false);
        user_password.setText(tf_user_password.getText());
    }

    @FXML
    protected void close(MouseEvent event) {

        Stage stage = (Stage) standart_pane.getScene().getWindow();
        stage.close();
    }

    //görünüş açısından gizlenen ve manuel olarak elle eklenen tam ekran moda geçme tuşunun methodu
    @FXML
    protected void max(MouseEvent event) {
        Stage stage = (Stage) standart_pane.getScene().getWindow();

        if (stage.isMaximized()) {
            stage.setMaximized(false);
        } else {
            stage.setMaximized(true);
        }
    }

    //görünüş açısından gizlenen ve manuel olarak elle eklenen aşağı alma tuşunun methodu
    @FXML
    protected void min(MouseEvent event) {
        Stage stage = (Stage) standart_pane.getScene().getWindow();
        stage.setIconified(true);
    }

    //bu metot ise kullnıcının çıkış yapmasına olanak sağlıyor. Yani kısacası app_standart_user.fxml ile app.fxml'i kapatıp login.fxml'i açıyor.
    @FXML
    protected void cikis(MouseEvent event) throws IOException {

        usersDAO udao = new usersDAO();

        Parent root = FXMLLoader.load(getClass().getResource("/FXML/login.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);

        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        stage.setTitle("Cinema-Login");

        Stage stage2 = (Stage) standart_pane.getScene().getWindow();
        stage2.close();

    }

    //setting pane'in içinde bulunan Güncelle mbutonunun metodudur. Yaptığı şey kısaca kullanıcının verilerini textfield gibi yerlerden alıp dosyaya kaydetme işlevini görüyor.
    @FXML
    protected void guncelle(ActionEvent event) {
        //Bu metot ayarlar kısmındaki Güncelle butonunun işlevini görmektedir. Yani kısacası Bu butona basıldığında kullanıcıların verileri isteklerine göre değişebiliyor.

        //Dosya işlemlerini yapabilmek adına bir dosya nesnesi oluşturuluyor.
        //Bu 2 satır var olan kullanıcıları bir bağlı listeye aktarıyor (dosya işlemleri ile)
        usersDAO udao = new usersDAO();

        //Her giriş yapıldığında. Giriş yapan kullanıcının id'sini bir txt dosyasında tutuluyor(bilgi.txt) oradan en son giriş yapan kullanıcının id'sine erişiliyor. 
        int user_id = Creator.getU().getUser_id();

        //bu if ve else gerekli olan TextField ve PasswordField'lerin dolu olup olmadığını kontrol ediyor. Eğer doldurulması gereken bir yeri doldurmamışsa kullanıcı o zaman if'in içine giriyor ve bir uyarı veriyor. Doldurmuşsa da else'nin içine giriyor ve işlemleri yapyıor.
        if ((user_name.getText().length() == 0) || (user_mail.getText().length() == 0)) {
            guncelle_mesaj.setText("Lütfen Gerekli Yerleri Doldurunuz.");

        } else if ((user_password.getText().length() == 0) && (sifre_gosterim == false)) {
            guncelle_mesaj.setText("Lütfen Gerekli Yerleri Doldurunuz.");
        } else if ((sifre_gosterim == true) && (tf_user_password.getText().length() == 0)) {
            guncelle_mesaj.setText("Lütfen Gerekli Yerleri Doldurunuz.");
        } else {
            //Kullanıcının TextField ve PasswordFiled'lere yazdığı verileri bir String'e aktarıyoruz.
            String name = user_name.getText();
            String mail = user_mail.getText();
            String password = null;
            if (sifre_gosterim == false) {
                password = user_password.getText();
            } else {
                password = tf_user_password.getText();
            }

            int user_type = udao.search_int(user_id);

            //yukarıda aldığımız kullanıcının girdiği bilgileri User adlı sınıfın içindeki metoda yolluyor. Bu metodun yaptığı işlevi ksaca anlatmak gerekirse. Yapılan değiişikliği ilk önce bağlı listede değiştiriyor ardından ise bunu dosyaya yazıp kalıcı hale getiriyor. 
            users u = new users(user_id, name, mail, password, user_type);
            Center nw = new Center(u);
            int control = udao.update(nw);

            //Az önce gönderdiğimiz metot bir değer yolluyor bu değer 1 ise işlem herhangi bir hataya uğramadan başarılı bir şekilde gerçekleştiğini yazıyor. Eğer başarılı bir şekilde gerçekleşmiyor ise de Hata meydana gleidğini ekrana yazdırıyor.
            if (control == 1) {
                guncelle_mesaj.setText("İşlem Başarılı Bir Şekilde Gerçekleştirildi");
            } else {
                guncelle_mesaj.setText("Bir Hata Meydana Geldi (Hata kodu = -12)");
            }
        }

    }
}
