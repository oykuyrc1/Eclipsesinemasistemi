package ilkUygulama;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Film Sınıfı
class Film {
    private String ad;
    private String tur;
    private int mevcutBiletSayisi;

    public Film(String ad, String tur, int mevcutBiletSayisi) {
        this.ad = ad;
        this.tur = tur;
        this.mevcutBiletSayisi = mevcutBiletSayisi;
    }

    public String getAd() {
        return ad;
    }

    public String getTur() {
        return tur;
    }

    public int getMevcutBiletSayisi() {
        return mevcutBiletSayisi;
    }

    public void biletSatisiYap() {
        if (mevcutBiletSayisi > 0) {
            mevcutBiletSayisi--;
        }
    }

    public void biletSatisiIptal() {
        mevcutBiletSayisi++;
    }
}

// Müşteri Sınıfı
class Musteri {
    private String ad;
    private String telefon;
    private String email;

    public Musteri(String ad, String telefon, String email) {
        this.ad = ad;
        this.telefon = telefon;
        this.email = email;
    }

    public String getAd() {
        return ad;
    }

    public void bilgilerGoster() {
        System.out.println("Müşteri Adı: " + ad);
        System.out.println("Telefon: " + telefon);
        System.out.println("E-mail: " + email);
    }
}

// Rezervasyon Sınıfı
class Rezervasyon {
    private Musteri musteri;
    private Film film;
    private String seans;
    private boolean rezervasyonTamamlandi;

    public Rezervasyon(Musteri musteri, Film film, String seans) {
        this.musteri = musteri;
        this.film = film;
        this.seans = seans;
        this.rezervasyonTamamlandi = false;
    }

    public Musteri getMusteri() {
        return musteri;
    }

    public Film getFilm() {
        return film;
    }

    public String getSeans() {
        return seans;
    }

    public boolean isRezervasyonTamamlandi() {
        return rezervasyonTamamlandi;
    }

    public void tamamlaRezervasyon() {
        if (film.getMevcutBiletSayisi() > 0) {
            rezervasyonTamamlandi = true;
            film.biletSatisiYap();  // Bilet satışını gerçekleştirdik
            System.out.println("Rezervasyon tamamlandı. Biletiniz başarıyla alındı.");
        } else {
            System.out.println("Maalesef yeterli bilet yok. Rezervasyon iptal edildi.");
        }
    }

    public void iptalEt() {
        film.biletSatisiIptal();
        rezervasyonTamamlandi = false;
        System.out.println("Rezervasyon iptal edildi.");
    }

    public void rezervasyonBilgileriniGoster() {
        musteri.bilgilerGoster();
        System.out.println("Film Adı: " + film.getAd());
        System.out.println("Seans: " + seans);
        System.out.println("Rezervasyon Durumu: " + (rezervasyonTamamlandi ? "Tamamlandı" : "Bekliyor"));
    }
}

// Ana Program (Sinema Sistemi)
public class SinemaSistemi {
    private List<Film> filmler;
    private Musteri musteri;

    public SinemaSistemi() {
        filmler = new ArrayList<>();
    }

    // Filmleri ekle
    public void filmEkle(Film film) {
        filmler.add(film);
    }

    // Müşteri bilgilerini al
    public void musteriBilgileriniAl() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Müşteri adı girin:");
        String ad = scanner.nextLine();
        System.out.println("Telefon numarası girin:");
        String telefon = scanner.nextLine();
        System.out.println("E-posta adresi girin:");
        String email = scanner.nextLine();
        musteri = new Musteri(ad, telefon, email);
    }

    // Bilet rezervasyonu yap
    public void rezervasyonYap() {
        if (musteri == null) {
            System.out.println("Lütfen önce müşteri bilgilerini girin.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        // Filmleri listele
        System.out.println("Filmler:");
        for (int i = 0; i < filmler.size(); i++) {
            System.out.println((i + 1) + ". " + filmler.get(i).getAd() + " (" + filmler.get(i).getMevcutBiletSayisi() + " bilet mevcut)");
        }

        System.out.println("Bir film seçin:");
        int filmSecim = scanner.nextInt();
        scanner.nextLine(); // consume newline
        Film secilenFilm = filmler.get(filmSecim - 1);

        // Seansları seçme
        System.out.println("Seanslar:");
        System.out.println("1. 12:00");
        System.out.println("2. 15:00");
        System.out.println("3. 18:00");
        System.out.println("Bir seans seçin:");
        int seansSecim = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String secilenSeans = seansSecim == 1 ? "12:00" : seansSecim == 2 ? "15:00" : "18:00";

        // Rezervasyon oluştur
        Rezervasyon rezervasyon = new Rezervasyon(musteri, secilenFilm, secilenSeans);
        rezervasyon.rezervasyonBilgileriniGoster();

        // Rezervasyon tamamla
        System.out.println("Rezervasyonu tamamlamak ister misiniz? (E/H)");
        String cevap = scanner.nextLine();
        if (cevap.equalsIgnoreCase("E")) {
            rezervasyon.tamamlaRezervasyon();
        } else {
            rezervasyon.iptalEt();
        }
    }

    public static void main(String[] args) {
        SinemaSistemi sistem = new SinemaSistemi();
        Scanner scanner = new Scanner(System.in);

        // Örnek filmleri ekleyelim
        Film film1 = new Film("Aksiyon Filmi", "Aksiyon", 10);
        Film film2 = new Film("Romantik Film", "Romantik", 5);
        Film film3 = new Film("Komedi Filmi", "Komedi", 3);

        sistem.filmEkle(film1);
        sistem.filmEkle(film2);
        sistem.filmEkle(film3);

        while (true) {
            System.out.println("\nAna Menü:");
            System.out.println("1. Müşteri Bilgisi Gir");
            System.out.println("2. Rezervasyon Yap");
            System.out.println("3. Çıkış");
            System.out.print("Seçiminizi yapın (1-3): ");
            int secim = scanner.nextInt();
            scanner.nextLine(); 

            switch (secim) {
                case 1:
                    sistem.musteriBilgileriniAl();
                    break;
                case 2:
                    sistem.rezervasyonYap();
                    break;
                case 3:
                    System.out.println("Çıkılıyor...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Geçersiz seçim. Lütfen tekrar deneyin.");
            }
        }
    }
}