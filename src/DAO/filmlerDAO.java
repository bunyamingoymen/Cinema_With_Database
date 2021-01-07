/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import entity.filmler;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.DBConnector;

/**
 *
 * @author bgoymen
 */
public class filmlerDAO {

    public int filmler_ekle_id_gonder(filmler f) {
        int id = 0;
        int sonuc = -1;

        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();
            String komut = "insert into filmler (film_name,film_type,film_suresi,yonetmen_id) values ('" + f.getFilm_name() + "','" + f.getFilm_type() + "','" + f.getFilm_suresi() + "','" + f.getYonetmen_id() + "')";
            sonuc = st.executeUpdate(komut);

            if (sonuc != 1) {
                System.out.println("FilmlerDAO, filmler_vizytondaki_filmler, Hata kodu 130");
            } else {
                String komut2 = "select * from filmler order by film_id desc limit 1";
                ResultSet rs = st.executeQuery(komut2);
                rs.next();
                id = rs.getInt("film_id");
            }

            c.close();
            st.close();

        } catch (SQLException e) {
            System.out.println("Hata kodu: 100, " + e.getMessage());;
        }

        return id;

    }

    public String filmler_film_adi_getir(int id) {
        String film_adi = null;

        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();
            String komut = "select * from filmler where film_id ='" + id + "'";
            ResultSet rs = st.executeQuery(komut);
            rs.next();
            film_adi = rs.getString("film_name");

            c.close();
            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println("Hata kodu: 101  " + e.getMessage());;
        }

        return film_adi;
    }

    public String filmler_film_type_getir(int id) {
        String film_type = null;

        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();
            String komut = "select * from filmler where film_id ='" + id + "'";
            ResultSet rs = st.executeQuery(komut);
            rs.next();
            film_type = rs.getString("film_type");

            c.close();
            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println("Hata kodu: 102  " + e.getMessage());;
        }

        return film_type;
    }

    public int filmler_film_suresi_getir(int id) {
        int film_suresi = 0;

        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();
            String komut = "select * from filmler where film_id ='" + id + "'";
            ResultSet rs = st.executeQuery(komut);
            rs.next();
            film_suresi = rs.getInt("film_suresi");

            c.close();
            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println("Hata kodu: 103  " + e.getMessage());;
        }

        return film_suresi;
    }

    public int filmler_yonetmen_id_getir(int id) {
        int yonetmen_id = 0;

        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();
            String komut = "select * from filmler where film_id ='" + id + "'";
            ResultSet rs = st.executeQuery(komut);
            rs.next();
            yonetmen_id = rs.getInt("yonetmen_id");

            c.close();
            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println("Hata kodu: 104  " + e.getMessage());;
        }

        return yonetmen_id;
    }

    public int filmler_dao_delete(int film_id) {
        int sonuc = 0;

        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();
            String komut = "delete from filmler where film_id=" + film_id;
            sonuc = st.executeUpdate(komut);

            c.close();
            st.close();
        } catch (SQLException e) {
            System.out.println("Hata kodu :149" + e.getMessage());;
        }

        return sonuc;
    }

    public String[][] filmler_combo_doldur() {
        String[][] arr = new String[kac_tane_film_var()][2];
        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();
            String komut = "select * from filmler ";
            ResultSet rs = st.executeQuery(komut);
            int i = 0;
            yonetmenlerDAO ydao = new yonetmenlerDAO();
            while (rs.next()) {
                String vizyondaki_filmler_combo = filmler_film_adi_getir(rs.getInt("film_id")) + " | " + filmler_film_type_getir(rs.getInt("film_id")) + " | " + filmler_film_suresi_getir(rs.getInt("film_id")) + " | " + ydao.yonetmenler_yonetmen_getir(filmler_yonetmen_id_getir(rs.getInt("film_id")));
                int id = rs.getInt("film_id");
                arr[i][0] = vizyondaki_filmler_combo;
                arr[i][1] = String.valueOf(id);
                i++;
            }

            c.close();
            st.close();
            rs.close();

            return arr;

        } catch (SQLException e) {
            System.out.println("Hata kodu: 165  " + e.getMessage());;
        }

        return null;
    }

    public int kac_tane_film_var() {
        int sonuc = -1;

        try {
            DBConnector d = new DBConnector();
            Connection c = d.connect();
            Statement st = c.createStatement();
            String komut = "select count (film_id) from filmler ";
            ResultSet rs = st.executeQuery(komut);
            rs.next();
            sonuc = rs.getInt("count");

            c.close();
            st.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println("Hata kodu: 166  " + e.getMessage());;
        }

        return sonuc;
    }
}