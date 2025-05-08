//import androidx.compose.runtime.LaunchedEffect
//
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.animation.core.tween
//import androidx.compose.animation.fadeIn
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.quranmemorization.ui.theme.IslamicGold
//import com.example.quranmemorization.ui.theme.IslamicGreen
//import com.example.quranmemorization.ui.theme.IslamicWhite
//import com.example.quranmemorization.ui.theme.QuranMemorizationTheme
//import androidx.compose.ui.tooling.preview.Preview
//
//data class ProfileData(
//    val nama: String,
//    val nim: String,
//    val email: String,
//    val angkatan: String,
//    val semester: Int,
//    val dosenPa: DosenPa,
//    val setoran: Setoran
//)
//
//data class DosenPa(
//    val nip: String,
//    val nama: String,
//    val email: String
//)
//
//data class Setoran(
//    val log: List<Log>,
//    val infoDasar: InfoDasar,
//    val ringkasan: List<Ringkasan>,
//    val detail: List<Detail>
//)
//
//data class Log(
//    val id: Int,
//    val keterangan: String,
//    val aksi: String,
//    val ip: String,
//    val userAgent: String,
//    val timestamp: String,
//    val nim: String,
//    val dosenYangMengesahkan: DosenPa
//)
//
//data class InfoDasar(
//    val totalWajibSetor: Int,
//    val totalSudahSetor: Int,
//    val totalBelumSetor: Int,
//    val persentaseProgresSetor: Double,
//    val tglTerakhirSetor: String,
//    val terakhirSetor: String
//)
//
//data class Ringkasan(
//    val label: String,
//    val totalWajibSetor: Int,
//    val totalSudahSetor: Int,
//    val totalBelumSetor: Int,
//    val persentaseProgresSetor: Double
//)
//
//data class Detail(
//    val id: String,
//    val nama: String,
//    val label: String,
//    val sudahSetor: Boolean,
//    val infoSetoran: InfoSetoran?
//)
//
//data class InfoSetoran(
//    val id: String,
//    val tglSetoran: String,
//    val tglValidasi: String,
//    val dosenYangMengesahkan: DosenPa
//)
//
//@Composable
//fun ProfileScreen() {
//    var isLoading by remember { mutableStateOf(true) }
//    val profileData = remember {
//        ProfileData(
//            nama = "Muhammad Zacky",
//            nim = "12350110010",
//            email = "12350110010@students.uin-suska.ac.id",
//            angkatan = "2023",
//            semester = 4,
//            dosenPa = DosenPa(
//                nip = "198010182007101002",
//                nama = "Muhammad Fikry, S.T., M.Sc.",
//                email = "muhammad.fikri@uin-suska.ac.id"
//            ),
//            setoran = Setoran(
//                log = listOf(
//                    Log(
//                        id = 14,
//                        keterangan = "An-Naba', An-Nazi'at, Abasa, serta memilih tanggal setoran 2025-04-27",
//                        aksi = "VALIDASI",
//                        ip = "10.2.31.220",
//                        userAgent = "PostmanRuntime/7.43.4",
//                        timestamp = "2025-05-02T02:24:05.871Z",
//                        nim = "12350110010",
//                        dosenYangMengesahkan = DosenPa(
//                            nip = "198010182007101002",
//                            nama = "Muhammad Fikry, S.T., M.Sc.",
//                            email = "muhammad.fikri@uin-suska.ac.id"
//                        )
//                    )
//                ),
//                infoDasar = InfoDasar(
//                    totalWajibSetor = 37,
//                    totalSudahSetor = 3,
//                    totalBelumSetor = 34,
//                    persentaseProgresSetor = 8.11,
//                    tglTerakhirSetor = "2025-04-27T00:00:00.000Z",
//                    terakhirSetor = "1 Minggu yang lalu"
//                ),
//                ringkasan = listOf(
//                    Ringkasan("KP", 8, 3, 5, 37.5),
//                    Ringkasan("SEMKP", 8, 0, 8, 0.0),
//                    Ringkasan("DAFTAR_TA", 6, 0, 6, 0.0),
//                    Ringkasan("SEMPRO", 12, 0, 12, 0.0),
//                    Ringkasan("SIDANG_TA", 3, 0, 3, 0.0)
//                ),
//                detail = listOf(
//                    Detail("9fc4e7c0-f23c-43a9-8b2a-5f4e9e94d9d1", "An-Naba'", "KP", true, InfoSetoran(
//                        "c06d332c-814d-454e-859d-d5f6944bfa64",
//                        "2025-04-27",
//                        "2025-05-02",
//                        DosenPa("198010182007101002", "Muhammad Fikry, S.T., M.Sc.", "muhammad.fikri@uin-suska.ac.id")
//                    )),
//                    Detail("cbe746c8-3ffb-4d44-b4ab-fc4e4c6d95ea", "An-Nazi'at", "KP", true, InfoSetoran(
//                        "db42beff-f7c3-48b5-ae22-a6353936aa81",
//                        "2025-04-27",
//                        "2025-05-02",
//                        DosenPa("198010182007101002", "Muhammad Fikry, S.T., M.Sc.", "muhammad.fikri@uin-suska.ac.id")
//                    )),
//                    Detail("1955f5c1-4c70-4f0e-95b7-6bd50269f6f6", "Abasa", "KP", true, InfoSetoran(
//                        "30cf816d-aa9c-44c9-a482-8ce6e46e7e3f",
//                        "2025-04-27",
//                        "2025-05-02",
//                        DosenPa("198010182007101002", "Muhammad Fikry, S.T., M.Sc.", "muhammad.fikri@uin-suska.ac.id")
//                    )),
//                    Detail("47c9d3e3-2e4c-4856-b35b-8c2ff9c6cf67", "At-Takwir", "KP", false, null),
//                    Detail("5c7d4f83-c57e-4c1e-a5d7-8cdd5580a3a8", "Al-Infitar", "KP", false, null),
//                    Detail("4d4cb33f-cd1a-4266-9f3c-cd4eb543d865", "Al-Mutaffifin", "KP", false, null),
//                    Detail("e4ab051e-3483-4215-9f89-7b4f8a3cb2aa", "Al-Inshiqaq", "KP", false, null),
//                    Detail("6d742e5d-2c9c-4c8c-b8f6-8b2c064b7c65", "Al-Buruj", "KP", false, null)
//                )
//            )
//        )
//    }
//
//    LaunchedEffect(Unit) {
//        isLoading = false
//    }
//
//    if (isLoading) {
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            CircularProgressIndicator(color = IslamicGreen)
//        }
//    } else {
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(IslamicWhite)
//                .padding(16.dp)
//        ) {
//            item {
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clip(RoundedCornerShape(16.dp))
//                        .background(
//                            Brush.linearGradient(
//                                colors = listOf(IslamicGreen, IslamicGold)
//                            )
//                        ),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
//                ) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text(
//                            text = "Profil Mahasiswa",
//                            style = MaterialTheme.typography.titleLarge.copy(
//                                fontWeight = FontWeight.Bold,
//                                color = IslamicWhite
//                            ),
//                            modifier = Modifier.padding(bottom = 8.dp)
//                        )
//                        Text(
//                            text = "Nama: ${profileData.nama}",
//                            style = MaterialTheme.typography.bodyLarge.copy(color = IslamicWhite)
//                        )
//                        Text(
//                            text = "NIM: ${profileData.nim}",
//                            style = MaterialTheme.typography.bodyLarge.copy(color = IslamicWhite)
//                        )
//                        Text(
//                            text = "Email: ${profileData.email}",
//                            style = MaterialTheme.typography.bodyLarge.copy(color = IslamicWhite)
//                        )
//                        Text(
//                            text = "Angkatan: ${profileData.angkatan} - Semester: ${profileData.semester}",
//                            style = MaterialTheme.typography.bodyLarge.copy(color = IslamicWhite)
//                        )
//                        Text(
//                            text = "Dosen PA: ${profileData.dosenPa.nama} (NIP: ${profileData.dosenPa.nip})",
//                            style = MaterialTheme.typography.bodyLarge.copy(color = IslamicWhite)
//                        )
//                        Text(
//                            text = "Email Dosen: ${profileData.dosenPa.email}",
//                            style = MaterialTheme.typography.bodyLarge.copy(color = IslamicWhite)
//                        )
//                    }
//                }
//                Spacer(modifier = Modifier.height(16.dp))
//            }
//
//            item {
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clip(RoundedCornerShape(16.dp)),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//                ) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text(
//                            text = "Ringkasan Setoran",
//                            style = MaterialTheme.typography.titleMedium.copy(
//                                fontWeight = FontWeight.SemiBold,
//                                color = IslamicGreen
//                            ),
//                            modifier = Modifier.padding(bottom = 8.dp)
//                        )
//                        Text(
//                            text = "Total Wajib Setor: ${profileData.setoran.infoDasar.totalWajibSetor}",
//                            style = MaterialTheme.typography.bodyLarge
//                        )
//                        Text(
//                            text = "Sudah Setor: ${profileData.setoran.infoDasar.totalSudahSetor}",
//                            style = MaterialTheme.typography.bodyLarge
//                        )
//                        Text(
//                            text = "Belum Setor: ${profileData.setoran.infoDasar.totalBelumSetor}",
//                            style = MaterialTheme.typography.bodyLarge
//                        )
//                        Text(
//                            text = "Progres: ${profileData.setoran.infoDasar.persentaseProgresSetor}%",
//                            style = MaterialTheme.typography.bodyLarge
//                        )
//                        Text(
//                            text = "Terakhir Setor: ${profileData.setoran.infoDasar.terakhirSetor}",
//                            style = MaterialTheme.typography.bodyLarge
//                        )
//                    }
//                }
//                Spacer(modifier = Modifier.height(16.dp))
//            }
//
//            item {
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clip(RoundedCornerShape(16.dp)),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//                ) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text(
//                            text = "Detail Setoran",
//                            style = MaterialTheme.typography.titleMedium.copy(
//                                fontWeight = FontWeight.SemiBold,
//                                color = IslamicGreen
//                            ),
//                            modifier = Modifier.padding(bottom = 8.dp)
//                        )
//                        profileData.setoran.detail.forEach { detail ->
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(vertical = 4.dp),
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                Text(
//                                    text = "${detail.nama} (${detail.label})",
//                                    style = MaterialTheme.typography.bodyLarge,
//                                    modifier = Modifier.weight(1f)
//                                )
//                                Text(
//                                    text = if (detail.sudahSetor) "✓ Setor" else "✗ Belum Setor",
//                                    style = MaterialTheme.typography.bodyLarge.copy(
//                                        color = if (detail.sudahSetor) IslamicGreen else Color.Red
//                                    )
//                                )
//                                detail.infoSetoran?.let {
//                                    Text(
//                                        text = "(${it.tglValidasi})",
//                                        style = MaterialTheme.typography.bodySmall,
//                                        modifier = Modifier.padding(start = 8.dp)
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewProfileScreen() {
//    QuranMemorizationTheme {
//        ProfileScreen()
//    }
//}