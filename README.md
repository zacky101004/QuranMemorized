📖 Quran Memorization App
Aplikasi Quran Memorization dirancang untuk memudahkan mahasiswa dalam melakukan setoran hafalan Al-Qur'an secara digital, dengan antarmuka yang intuitif, modern, dan ramah pengguna.

📱 Fitur Utama
🔐 LoginScreen
Layar login yang elegan dan responsif:
Gradien abu-abu untuk latar belakang.
Logo UIN SUSKA RIAU (120dp).
Judul “Quran Memorization” dan subjudul “Setoran Hafalan Mahasiswa”.
Form login berbasis Card dengan desain melengkung.
Komponen input responsif dengan umpan balik visual warna hijau & kuning.
Tombol login dengan sudut tumpul dan kontras tinggi.

🏠 MainScreen
Layar utama dengan Scaffold:
TopAppBar hijau gelap dengan tombol logout.
BottomNavigationBar konsisten dengan ikon “Beranda” dan “Profil”.
Komponen utama:
  Kartu sambutan pengguna.
  Kartu progres hafalan (dengan CircularProgressIndicator dan LinearProgressIndicator).
  Menu grid untuk navigasi fitur setoran.

📋 DetailSetoranScreen
Menampilkan daftar setoran hafalan:
Gradien latar dari hijau muda ke putih.
Navigasi atas dan bawah dengan tema gradien hijau.
Daftar Card satu kolom:
  Menampilkan nama, label, dan status setoran (Sudah/Belum).
  Warna latar berbeda untuk status (hijau muda dan merah muda).

👤 ProfileScreen
Menampilkan profil pengguna:
Gradien hijau lembut di latar belakang.
Header “Profile” dengan tombol kembali.
Lingkaran inisial nama pengguna.
Kartu profil dengan info nama dan NIM.

🎨 Palet Warna
Nama	Warna Hex
Hijau Gelap	#2E7D32
Kuning Keemasan	#DDAA33
Hijau Muda	#E8F5E8
Abu-Abu Muda	#E0E0E0
Abu-Abu Gelap	#9E9E9E
Putih Bersih	#FAFAFA

🛠️ Teknologi
Jetpack Compose untuk UI deklaratif di Android.
Kotlin sebagai bahasa pemrograman utama.
Material3 Components untuk gaya desain modern.

👨‍💻 Panduan untuk Developer
Gunakan Brush.verticalGradient untuk latar belakang bertema.
Gunakan Card, RoundedCornerShape, dan elevation untuk efek bayangan dan lapisan.
Navigasi dikelola menggunakan Scaffold, TopAppBar, dan BottomNavigationBar.
Status visual ditandai melalui warna dan ikon, seperti setoran hafalan.

📦 Struktur Utama
bash
Salin
Edit
.
├── LoginScreen.kt
├── MainScreen.kt
├── DetailSetoranScreen.kt
├── ProfileScreen.kt
├── components/
│   ├── WelcomeCard.kt
│   ├── ProgressCard.kt
│   └── MenuCard.kt
└── model/
    └── MenuItem.kt

