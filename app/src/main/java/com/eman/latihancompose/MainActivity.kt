package com.eman.latihancompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.eman.latihancompose.ui.theme.LatihanComposeTheme

data class Barang(
    val id: Int,
    val nama: String,
    val jumlah: Int,
    val deskripsi: String
)

fun dataContoh(): List<Barang> = listOf(
    Barang(1, "Keyboard", 5, "Perangkat input untuk komputer laboratorium."),
    Barang(2, "Mouse", 8, "Mouse optik untuk meja praktikum."),
    Barang(3, "Monitor", 4, "Monitor LCD untuk kegiatan pembelajaran."),
    Barang(4, "Proyektor", 2, "Perangkat presentasi di ruang laboratorium."),
    Barang(5, "Kabel HDMI", 12, "Penghubung laptop dan layar presentasi.")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LatihanComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF5F6FA)
                ) {
                    InventarisApp()
                }
            }
        }
    }
}
@Composable
fun InventarisApp() {

    var menuTerpilih by remember {
        mutableStateOf("Dashboard")
    }
    var barangDipilih by remember {
        mutableStateOf<Barang?>(null)
    }

    val daftarBarang = dataContoh()
    val totalStok = daftarBarang.sumOf { it.jumlah }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "INVENTARIS LABORATORIUM",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1565C0)
        )

        Text(
            text = "Sistem Informasi Data Barang",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            listOf("Dashboard", "Barang", "Tentang").forEach { menu ->

                if (menuTerpilih == menu) {
                    Button(
                        onClick = {
                            menuTerpilih = menu
                            barangDipilih = null
                        },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(
                            horizontal = 4.dp,
                            vertical = 10.dp
                        )
                    ) {
                        Text(menu, fontSize = 12.sp)
                    }
                } else {
                    OutlinedButton(
                        onClick = {
                            menuTerpilih = menu
                            barangDipilih = null
                        },
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(
                            horizontal = 4.dp,
                            vertical = 10.dp
                        )
                    ) {
                        Text(menu, fontSize = 12.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        when (menuTerpilih) {
            "Dashboard" -> {

                Text(
                    text = "Dashboard",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1565C0)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Total Jenis Barang",
                            color = Color.White
                        )

                        Text(
                            text = "${daftarBarang.size} Barang",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF2E7D32)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Total Stok Barang",
                            color = Color.White
                        )

                        Text(
                            text = "$totalStok Unit",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Barang Tersedia",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(daftarBarang.take(3)) { barang ->
                        BarangCard(
                            barang = barang,
                            onClick = {
                                barangDipilih = barang
                                menuTerpilih = "Barang"
                            }
                        )
                    }
                }
            }
            "Barang" -> {

                if (barangDipilih == null) {

                    Text(
                        text = "Daftar Barang",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(daftarBarang) { barang ->

                            BarangCard(
                                barang = barang,
                                onClick = {
                                    barangDipilih = barang
                                }
                            )
                        }
                    }

                } else {

                    Text(
                        text = "Detail Barang",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 3.dp
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {

                            Text(
                                text = barangDipilih!!.nama,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1565C0)
                            )

                            Spacer(modifier = Modifier.height(15.dp))

                            Text(
                                text = "ID Barang: ${barangDipilih!!.id}"
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Nama Barang: ${barangDipilih!!.nama}"
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Jumlah Stok: ${barangDipilih!!.jumlah} Unit"
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Deskripsi:",
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = barangDipilih!!.deskripsi,
                                color = Color.Gray
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Button(
                                onClick = {
                                    barangDipilih = null
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Kembali")
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun BarangCard(
    barang: Barang,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = barang.nama,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1565C0)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Jumlah: ${barang.jumlah} Unit",
                fontWeight = FontWeight.Medium
            )

            Text(
                text = barang.deskripsi,
                fontSize = 13.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Klik untuk melihat detail barang →",
                fontSize = 12.sp,
                color = Color(0xFF1565C0)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InventarisPreview() {
    LatihanComposeTheme {
        InventarisApp()
    }
}
