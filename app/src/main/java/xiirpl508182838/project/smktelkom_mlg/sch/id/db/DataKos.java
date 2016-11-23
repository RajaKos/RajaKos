package xiirpl508182838.project.smktelkom_mlg.sch.id.db;

/**
 * Created by SMK Telkom SP Malang on 11/23/2016.
 */

public class DataKos {
    public String namaKos;
    public String alamat;
    public String namaPemilik;
    public String kontakPemilik;
    public String hargaBulanan;
    public String hargaSemester;
    public String status;
    public String jenis;

    public DataKos(String namaKos, String alamat, String namaPemilik, String kontakPemilik, String hargaBulanan, String hargaSemester, String status, String jenis) {
        this.namaKos = namaKos;
        this.alamat = alamat;
        this.namaPemilik = namaPemilik;
        this.kontakPemilik = kontakPemilik;
        this.hargaBulanan = hargaBulanan;
        this.hargaSemester = hargaSemester;
        this.status = status;
        this.jenis = jenis;
    }
}
