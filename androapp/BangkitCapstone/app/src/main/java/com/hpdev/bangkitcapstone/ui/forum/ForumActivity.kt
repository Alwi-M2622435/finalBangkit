package com.hpdev.bangkitcapstone.ui.forum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hpdev.bangkitcapstone.R
import com.hpdev.bangkitcapstone.data.ForumEntity

class ForumActivity : AppCompatActivity() {
    private val forumList = ArrayList<ForumEntity>()
    private lateinit var rv: RecyclerView
    private lateinit var adapter: ForumAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum)

        rv = findViewById(R.id.rv_forum)
        rv.setHasFixedSize(true)

        val dummyForums = generateDummyForums()
        forumList.addAll(dummyForums)

        adapter = ForumAdapter(forumList, this)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }

    private fun generateDummyForums(): ArrayList<ForumEntity> {
        val dummyForums = ArrayList<ForumEntity>()

        dummyForums.add(
            ForumEntity(
                title = "Pentingnya Mental Health",
                description = "Beberapa alasan mengapa mental health itu penting...",
                likes = 590,
                image = "https://www.un.org/en/healthy-workforce/images/Mental-Health-visual-identity.jpg"
            )
        )

        dummyForums.add(
            ForumEntity(
                title = "Cara Bermeditasi",
                description = "Meditasi adalah melatih fokus pikiran sehingga...",
                likes = 344,
                image = "https://indoprogress.com/wp-content/uploads/2019/03/1-5.jpg"
            )
        )

        dummyForums.add(
            ForumEntity(
                title = "Gimana biar produktif?",
                description = "Produktif bukan hanya soal kerja keras...",
                likes = 278,
                image = "https://cms.daihatsu.co.id/uploads/tipsandtrick/1606926493156.png"
            )
        )

        dummyForums.add(
            ForumEntity(
                title = "Tips Semangat Olahraga?",
                description = "Olahraga adalah aktivitas untuk melatih...",
                likes = 199,
                image = "https://res.cloudinary.com/dk0z4ums3/image/upload/v1614664558/attached_image/beragam-manfaat-olahraga.jpg"
            )
        )

        dummyForums.add(
            ForumEntity(
                title = "Gimana mengatasi stres?",
                description = "Stres adalah reaksi tubuh yang muncul saat...",
                likes = 199,
                image = "https://akcdn.detik.net.id/visual/2020/09/14/ilustrasi-stress_169.jpeg?w=650"
            )
        )

        dummyForums.add(
            ForumEntity(
                title = "Definisi Kebahagiaan",
                description = "kebahagiaan sebagai keadaan psikologis yang positif dimana seseorang memiliki emosi positif...",
                likes = 142,
                image = "https://asset.kompas.com/crops/6r_gGanmHDbUFESVRNa6x_seGdw=/32x41:711x494/750x500/data/photo/2018/10/18/2782377278.jpg"
            )
        )

        return dummyForums
    }
}