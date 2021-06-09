package com.hpdev.bangkitcapstone.ui.professional

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hpdev.bangkitcapstone.R
import com.hpdev.bangkitcapstone.data.ProfessionalEntity
import com.hpdev.bangkitcapstone.ui.professional.ProfessionalAdapter

class ProfessionalActivity : AppCompatActivity() {
    private val professionalList = ArrayList<ProfessionalEntity>()
    private lateinit var rv: RecyclerView
    private lateinit var adapter: ProfessionalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professional)

        rv = findViewById(R.id.rv_professional)
        rv.setHasFixedSize(true)

        val dummyProfessionals = generateDummyProfessionals()
        professionalList.addAll(dummyProfessionals)

        adapter = ProfessionalAdapter(professionalList, this)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }

    private fun generateDummyProfessionals(): ArrayList<ProfessionalEntity> {
        val dummyProfessionals = ArrayList<ProfessionalEntity>()

        dummyProfessionals.add(
            ProfessionalEntity(
                title = "Efni Kartini, M.Psi, S.Psi",
                description = "Kaliawi - 300m",
                rate = 5F,
                image = "https://cdn.iconscout.com/icon/free/png-256/doctor-567-1118047.png"
            )
        )

        dummyProfessionals.add(
            ProfessionalEntity(
                title = "Toni Rizki, M.Psi, S.Psi",
                description = "Antasari - 4km",
                rate = 4.8F,
                image = "https://cdn.iconscout.com/icon/free/png-256/doctor-1659516-1410038.png"
            )
        )

        dummyProfessionals.add(
            ProfessionalEntity(
                title = "Andrianna K., M.Psi, S.Psi",
                description = "Palapa - 900m",
                rate = 4.4F,
                image = "https://cdn.iconscout.com/icon/free/png-256/doctor-567-1118047.png"
            )
        )

        dummyProfessionals.add(
            ProfessionalEntity(
                title = "Resti Pratiwi, M.Psi, S.Psi",
                description = "Enggal - 1.5km",
                rate = 4.3F,
                image = "https://cdn.iconscout.com/icon/free/png-256/doctor-567-1118047.png"
            )
        )

        dummyProfessionals.add(
            ProfessionalEntity(
                title = "Ilham Batubara, M.Psi, S.Psi",
                description = "Teluk Betung - 2km",
                rate = 4.2F,
                image = "https://cdn.iconscout.com/icon/free/png-256/doctor-1659516-1410038.png"
            )
        )

        dummyProfessionals.add(
            ProfessionalEntity(
                title = "Sandra Wati, M.Psi, S.Psi",
                description = "Tanjung Senang - 4km",
                rate = 4F,
                image = "https://cdn.iconscout.com/icon/free/png-256/doctor-567-1118047.png"
            )
        )

        return dummyProfessionals
    }
}