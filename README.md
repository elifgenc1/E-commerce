# E-commerce Uygulaması

## Genel Bakış

E-commerce, Enoca firması için geliştirilen bir e-ticaret platformudur. Bu platform, müşterilerin ürünleri incelemesine, sepete eklemesine ve sipariş vermelerine olanak tanır. Ayrıca, ürün yönetimi ve müşteri sipariş görüntüleme sağlar.

## Teknolojiler

-   Spring Boot
-   Spring Data JPA
-   Hibernate
-   MySQL
-  Spring Security
-  RESTFul Services 

## Proje Yapısı

Proje, MVC (Model-View-Controller) mimarisini takip eder. Temel paketler ve sınıflar aşağıdaki gibidir:

-   **controller**: HTTP isteklerini işleyen ve iş mantığını yürüten controller sınıfları bulunur.
-   **service**: İş mantığını gerçekleştiren servis sınıfları bulunur.
-   **repository**: Veritabanı işlemlerini gerçekleştiren repository sınıfları bulunur.
-   **model**: Veritabanı tablolarını temsil eden model sınıfları bulunur.
-   **dto**: Veri taşınmasını gerçekleştirecek sınıflar bulunur.
-   **enum**: Sabit veri yapıları bulunur.

## Kurulum ve Çalıştırma

1.  Projeyi klonlayın: `git clone https://github.com/username/proje-adı.git`
2.  Proje dizinine gidin: `cd proje-adı`
3.  Uygulamayı başlatmak için  komutu çalıştırın: `mvn spring-boot:run`

## Veritabanı Bağlantısı

Uygulamanın çalışması için bir MySQL veritabanına ihtiyacı vardır. Aşağıdaki adımları izleyerek veritabanı bağlantısı yapılandırılmalıdır:

1.  **MySQL Kurulumu**: MySQL veritabanı sunucusunu bilgisayarınıza kurun.
2.  **Veritabanı Oluşturma**: MySQL üzerinde "ecommerce" adında bir veritabanı oluşturun.
3.  **Uygulama Yapılandırması**: `application.properties` dosyasında aşağıdaki ayarları yapın:
     
        spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
        spring.datasource.username={}
        spring.datasource.password={}
        spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver` 

Bu ayarlar, uygulamanın MySQL veritabanına bağlanmasını sağlayacaktır. Gerekli olan veritabanı adı, kullanıcı adı ve parola bilgilerini bu ayarlara uygun şekilde güncellemeniz gerekebilir.

