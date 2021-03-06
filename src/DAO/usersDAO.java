package DAO;

import DAO_Controller.Users_Controller;
import Pattern.Creator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entity.Center;
import entity.users;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import util.DBConnector;

/*
Bu sınıfın asıl amacı veritabanındaki users tablosundaki verileri okumak, yeni veri yazmak ve gerekli olan başka işlemleri yapmak için tasarlandı.
 */
public class usersDAO implements IDAO {

    @Override
    public int create(Center nw) {
        int sonuc = -1;

        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();
            String komut = "insert into users (user_name,user_mail,user_password,user_type) values ('" + nw.getUsers().getUser_name() + "','" + nw.getUsers().getUser_mail() + "','" + nw.getUsers().getUser_password() + "','" + nw.getUsers().getUser_type() + "')";
            sonuc = st.executeUpdate(komut);

            c.close();
            st.close();
        } catch (SQLException e) {
            System.out.println("Hata kodu: 179 - " + e.getMessage());
        }

        return sonuc;
    }

    @Override
    public LinkedList read() {
        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();
            String komut = "select * from users";
            ResultSet rs = st.executeQuery(komut);
            LinkedList<users> list = new LinkedList<>();

            while (rs.next()) {

                users u = new users(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("user_mail"), rs.getString("user_password"), rs.getInt("user_type"), Creator.abonelerDao().search(rs.getInt("user_id")));

                list.add(u);

            }

            c.close();
            st.close();
            rs.close();

            return list;

        } catch (SQLException e) {
            System.out.println("Hata kodu: 240 - " + e.getMessage());
            return null;
        }

    }

    @Override
    public int update(Center nw) {
        int sonuc = 0;

        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();

            String komut = "update users set user_name='" + nw.getUsers().getUser_name() + "', user_mail='" + nw.getUsers().getUser_mail() + "', user_password = '" + nw.getUsers().getUser_password() + "', user_type = '" + nw.getUsers().getUser_type() + "' where user_id = " + nw.getUsers().getUser_id();
            sonuc = st.executeUpdate(komut);

            c.close();
            st.close();

        } catch (SQLException e) {
            System.out.println("Hata kodu: 188 - " + e.getMessage());
        }

        return sonuc;
    }

    @Override
    public int delete(int user_id) {
        int sonuc = 0;

        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();
            String komut = "delete from users where user_id = " + user_id;
            sonuc = st.executeUpdate(komut);

            c.close();
            st.close();

        } catch (SQLException e) {
            System.out.println("Hata kodu :241 - " + e.getMessage());
        }

        return sonuc;
    }

    @Override
    public int count() {

        LinkedList<users> list = Users_Controller.getUser_list();

        return list.size();
    }

    public int count_user_name(String user_name) {

        int sonuc = 0;

        LinkedList<users> list = Users_Controller.getUser_list();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUser_name().equals(user_name)) {
                sonuc++;
            }
        }

        return sonuc;

    }

    public int count_user_mail(String user_mail) {

        int sonuc = 0;

        LinkedList<users> list = Users_Controller.getUser_list();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUser_name().equals(user_mail)) {
                sonuc++;
            }
        }

        return sonuc;

    }

    public ObservableList<users> select(ObservableList<users> data, Label kullanici_islemleri_user_id, TextField kullanici_islemleri_user_name, TextField kullanici_islemleri_user_mail, PasswordField kullanici_islemleri_gizli_pf, ComboBox<String> kullanici_islemleri_user_turu, ComboBox<String> kullanici_islemleri_abone_turu, Pane gizli_pane, Pane acik_pane, Pane tablo_pane, Pane yonet_pane, FontAwesomeIconView geri_tusu, FontAwesomeIconView yonet_geri_tusu, Pane sil_pane) {

        LinkedList<users> list = Users_Controller.getUser_list();

        for (int i = 0; i < list.size(); i++) {

            Button yonet = new Button();
            yonet.setText("Yönet");
            yonet.setStyle("-fx-background-color : #393351; -fx-background-radius :  20; -fx-text-fill: white");

            data.addAll(FXCollections.observableArrayList(new users(list.get(i).getUser_id(), list.get(i).getUser_name(), list.get(i).getUser_mail(), list.get(i).getUser_password(), list.get(i).getUser_type(), list.get(i).getAbonelik_turu(), yonet, kullanici_islemleri_user_id, kullanici_islemleri_user_name, kullanici_islemleri_user_mail, kullanici_islemleri_gizli_pf, kullanici_islemleri_user_turu, kullanici_islemleri_abone_turu, gizli_pane, acik_pane, tablo_pane, yonet_pane, geri_tusu, yonet_geri_tusu, sil_pane)));

        }

        return data;
    }

    public String search_string(int user_id, int secim) {
        String sonuc = null;

        LinkedList<users> list = Users_Controller.getUser_list();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUser_id() == user_id) {
                switch (secim) {
                    case 1:
                        sonuc = list.get(i).getUser_password();
                        break;
                    case 2:
                        sonuc = list.get(i).getUser_name();
                        break;
                    case 3:
                        sonuc = list.get(i).getUser_mail();
                        break;
                    default:
                        break;
                }
                break;
            }
        }

        return sonuc;
    }

    public int search_int(int user_id) {
        int sonuc = 0;
        LinkedList<users> list = Users_Controller.getUser_list();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUser_id() == user_id) {
                sonuc = list.get(i).getUser_type();
            }
        }
        return sonuc;
    }

    public int search_int(String user_name) {
        int sonuc = -1;

        LinkedList<users> list = Users_Controller.getUser_list();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUser_name().equals(user_name)) {
                sonuc = list.get(i).getUser_id();
                break;
            }
        }

        return sonuc;
    }

    public int control_login(String user_name, String password) {

        int kullanici_adi_var_mi = count_user_name(user_name);

        switch (kullanici_adi_var_mi) {

            case 1: //girilen kullanıcı adından 1 tane var ve şifre kontrolü başlıyor.
                return control_password(user_name, password);
            case 0: //kullanıcı adı yok
                int mail_var_mi = count_user_mail(user_name);//Kullanıcı adı yerine Mail adresi yazılmış olabilir. Bu sebeple maili de kullanıcı adı gibi kontrol edioyruz.
                switch (mail_var_mi) {
                    case 1:
                        return control_password(user_name, password);
                    case 0:
                        return 0; //mail  yok
                    default:
                        return -1;  //birdem fazla mail var. Bu yüzden hata meydana geldi.

                }
            default:
                return -1; //birdem fazla kullanıcı adı var. Bu yüzden hata meydana geldi.

        }

    }

    public boolean control_user_name(String user_name) {
        boolean a = false;
        int sonuc = count_user_name(user_name);

        if (sonuc == 0) {
            a = true;
        }

        return a;
    }

    public boolean control_user_mail(String user_mail) {
        boolean a = false;
        int sonuc = count_user_mail(user_mail);

        if (sonuc == 0) {
            a = true;
        }

        return a;
    }

    public int control_password(String user_name, String password) {
        int user_id = search_int(user_name);
        String sql_password = search_string(user_id, 1);
        if (sql_password == null) {
            return -2; //kullanici adı 1 tane var ama kullanıcının şifesi Veri tabanında yok. Hata meydana geldi.
        } else if (sql_password.equals(password)) {
            return user_id; //girilen değerler doğru ve kullanının bilgilerini gönderiyoruz.
        } else {
            return 0; //girilen şifre yanlış
        }
    }

    /*

    //user tablosuna veri eklemek içim kullandığımız method
    //public int user_ekle(users User) {

        int sonuc = -1;

        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();
            String komut = "insert into users (user_name,user_mail,user_password,user_type) values ('" + User.getUser_name() + "','" + User.getUser_mail() + "','" + User.getUser_password() + "','" + User.getUser_type() + "')";
            sonuc = st.executeUpdate(komut);

            c.close();
            st.close();
        } catch (SQLException e) {
            System.out.println("Hata kodu: 179 - " + e.getMessage());
        }

        return sonuc;

    }

    //user tablosundan veri okumak için yazılan method
    //public int user_giris_kontrol(String user_name, String password) {

        int kullanici_adi_var_mi = user_name_sayaci(user_name);

        switch (kullanici_adi_var_mi) {

            case 1: //girilen kullanıcı adından 1 tane var ve şifre kontrolü başlıyor.
                return user_password_control(user_name, password);
            case 0: //kullanıcı adı yok
                int mail_var_mi = user_mail_sayaci(user_name);//Kullanıcı adı yerine Mail adresi yazılmış olabilir. Bu sebeple maili de kullanıcı adı gibi kontrol edioyruz.
                switch (mail_var_mi) {
                    case 1:
                        return user_password_control(user_name, password);
                    case 0:
                        return 0; //mail  yok
                    default:
                        return -1;  //birdem fazla mail var. Bu yüzden hata meydana geldi.

                }
            default:
                return -1; //birdem fazla kullanıcı adı var. Bu yüzden hata meydana geldi.

        }

    }

    //public int user_password_control(String user_name, String password) {
        int user_id = user_id(user_name);
        String sql_password = user_password(user_id);
        if (sql_password == null) {
            return -2; //kullanici adı 1 tane var ama kullanıcının şifesi Veri tabanında yok. Hata meydana geldi.
        } else if (sql_password.equals(password)) {
            return user_id; //girilen değerler doğru ve kullanının bilgilerini gönderiyoruz.
        } else {
            return 0; //girilen şifre yanlış
        }
    }

    //public int user_id(String user_name) {
        int sonuc = -1;

        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();
            String komut = "select * from users where user_name='" + user_name + "'";
            ResultSet rs = st.executeQuery(komut);
            rs.next();
            sonuc = rs.getInt("user_id");

            c.close();
            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println("Hata kodu: 180 - " + e.getMessage());
        }

        return sonuc;
    }

    //public int user_name_sayaci(String user_name) {

        int sonuc = -1;

        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();
            String komut = "select count (user_id) from users where user_name='" + user_name + "'";
            ResultSet rs = st.executeQuery(komut);
            rs.next();
            sonuc = rs.getInt("count");

            c.close();
            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println("Hata kodu: 181 - " + e.getMessage());
        }

        return sonuc;

    }

    //public int user_mail_sayaci(String user_mail) {

        int sonuc = -1;

        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();
            String komut = "select count (user_id) from users where user_mail='" + user_mail + "'";
            ResultSet rs = st.executeQuery(komut);
            rs.next();
            sonuc = rs.getInt("count");

            c.close();
            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println("Hata kodu: 182 - " + e.getMessage());
        }

        return sonuc;

    }

    //public String user_password(int id) {

        String password = null;

        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();
            String komut = "select * from users where user_id=" + id;
            ResultSet rs = st.executeQuery(komut);
            rs.next();
            password = rs.getString("user_password");

            c.close();
            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println("Hata kodu: 183 - " + e.getMessage());
        }

        return password;
    }

    //public boolean user_name_control(String user_name) {
        boolean a = false;
        int sonuc = user_name_sayaci(user_name);

        if (sonuc == 0) {
            a = true;
        }

        return a;
    }

    //public boolean user_mail_control(String user_mail) {
        boolean a = false;
        int sonuc = user_mail_sayaci(user_mail);

        if (sonuc == 0) {
            a = true;
        }

        return a;
    }

    //public int user_type_getir(int user_id) {
        int user_type = 0;

        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();

            String komut = "select * from users where user_id=" + user_id;
            ResultSet rs = st.executeQuery(komut);
            rs.next();
            user_type = rs.getInt("user_type");

            c.close();
            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println("Hata kodu: 187 - " + e.getMessage());
        }

        return user_type;
    }

    //public int user_guncelle(users u) {
        int sonuc = 0;

        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();

            String komut = "update users set user_name='" + u.getUser_name() + "', user_mail='" + u.getUser_mail() + "', user_password = '" + u.getUser_password() + "', user_type = '" + u.getUser_type() + "' where user_id = " + u.getUser_id();
            sonuc = st.executeUpdate(komut);

            c.close();
            st.close();

        } catch (SQLException e) {
            System.out.println("Hata kodu: 188 - " + e.getMessage());
        }

        return sonuc;
    }

    //public int user_guncelle_aboneli(users u, int abone_type) {
        int sonuc = 0;
        int sonuc2 = 0;

        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();

            String komut = "update users set user_name='" + u.getUser_name() + "', user_mail='" + u.getUser_mail() + "', user_password = '" + u.getUser_password() + "', user_type = '" + u.getUser_type() + "' where user_id = " + u.getUser_id();
            sonuc = st.executeUpdate(komut);
            if (abone_type != 0) {
                abonelerDAO adao = new abonelerDAO();
                sonuc2 = adao.abonelik_turu_guncelle_kullanici_yonetimi(abone_type, u.getUser_id());
            } else {
                sonuc2 = 1;
            }
    
    //Not: Bu metot biraz sıkjıntılı. Normalde abone 0 gelirse de aboneler tablosunda var ise oradan silinmeli. sıfırdan farklı geldniğinde de güncellenmeli.

            c.close();
            st.close();

        } catch (SQLException e) {
            System.out.println("Hata kodu: 242 - " + e.getMessage());
        }

        if (sonuc == 1 && sonuc2 == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    //public int kac_tane_user_var() {
        int sonuc = -1;

        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();
            String komut = "select count (user_id) from users";
            ResultSet rs = st.executeQuery(komut);
            rs.next();
            sonuc = rs.getInt("count");

            c.close();
            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println("Hata kodu: 189 - " + e.getMessage());
        }

        return sonuc;
    }

    //public String user_name_getir(int id) {
        String name = null;
        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();
            String komut = "select *  from users where user_id='" + id + "'";
            ResultSet rs = st.executeQuery(komut);
            rs.next();
            name = rs.getString("user_name");

            c.close();
            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println("Hata kodu: 190 - " + e.getMessage());
        }

        return name;
    }

    //public String user_mail_getir(int id) {
        String name = null;
        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();
            String komut = "select *  from users where user_id='" + id + "'";
            ResultSet rs = st.executeQuery(komut);
            rs.next();
            name = rs.getString("user_mail");

            c.close();
            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println("Hata kodu: 191 - " + e.getMessage());
        }

        return name;
    }

    //public String user_password_getir(int id) {
        String name = null;
        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();
            String komut = "select *  from users where user_id='" + id + "'";
            ResultSet rs = st.executeQuery(komut);
            rs.next();
            name = rs.getString("user_password");

            c.close();
            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println("Hata kodu: 192 - " + e.getMessage());
        }

        return name;
    }

    //bu metot yerine abonelerDAO sınıfındaki search metodu kullanılabilir.
    public int abone_sorgula(int user_id) {
        int abonelik_turu = -2;
        abonelerDAO adao = new abonelerDAO();
        abonelik_turu = adao.abonelik_turu_bul(user_id);
        return abonelik_turu;
    }

    //public ObservableList<users> user_select(ObservableList<users> data, Label kullanici_islemleri_user_id, TextField kullanici_islemleri_user_name, TextField kullanici_islemleri_user_mail, PasswordField kullanici_islemleri_gizli_pf, ComboBox<String> kullanici_islemleri_user_turu, ComboBox<String> kullanici_islemleri_abone_turu, Pane gizli_pane, Pane acik_pane, Pane tablo_pane, Pane yonet_pane, FontAwesomeIconView geri_tusu, FontAwesomeIconView yonet_geri_tusu, Pane sil_pane) {

        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();
            String komut = "select * from users";
            ResultSet rs = st.executeQuery(komut);

            while (rs.next()) {
                int user_id = rs.getInt("user_id");
                String user_name = rs.getString("user_name");
                String user_mail = rs.getString("user_mail");
                String user_password = rs.getString("user_password");
                int user_type = rs.getInt("user_type");
                int abone_type = abone_sorgula(user_id);

                Button yonet = new Button();
                yonet.setText("Yönet");
                yonet.setStyle("-fx-background-color : #393351; -fx-background-radius :  20; -fx-text-fill: white");

                data.addAll(FXCollections.observableArrayList(new users(user_id, user_name, user_mail, user_password, user_type, abone_type, yonet, kullanici_islemleri_user_id, kullanici_islemleri_user_name, kullanici_islemleri_user_mail, kullanici_islemleri_gizli_pf, kullanici_islemleri_user_turu, kullanici_islemleri_abone_turu, gizli_pane, acik_pane, tablo_pane, yonet_pane, geri_tusu, yonet_geri_tusu, sil_pane)));
            }

            c.close();
            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println("Hata kodu: 240 - " + e.getMessage());
        }

        return data;
    }

    //public int user_dao_sil(int user_id) {
        int sonuc = 0;

        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();
            String komut = "delete from users where user_id = " + user_id;
            sonuc = st.executeUpdate(komut);

            c.close();
            st.close();

        } catch (SQLException e) {
            System.out.println("Hata kodu :241 - " + e.getMessage());
        }

        return sonuc;
    }

     */
}
