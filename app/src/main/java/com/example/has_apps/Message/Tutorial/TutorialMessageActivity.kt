package com.example.has_apps.Message.Tutorial

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.has_apps.R
import com.example.has_apps.databinding.ActivityTutorialMessageBinding

class TutorialMessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTutorialMessageBinding

    private val fragments = listOf(
        Tutorial1Fragment(),
        Tutorial2Fragment(),
        Tutorial3Fragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTutorialMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupViewPager()
        setupIndicators()
        setCurrentIndicator(0)

        binding.btnTutorialAction.setOnClickListener {
            val currentItem = binding.viewPagerTutorial.currentItem
            if (currentItem < fragments.size - 1) {
                binding.viewPagerTutorial.currentItem = currentItem + 1
            } else {
                // Selesai tutorial, tutup activity atau arahkan ke Home
                finish()
            }
        }
    }

    private fun setupViewPager() {
        val adapter = TutorialFragmentAdapter(this, fragments)
        binding.viewPagerTutorial.adapter = adapter

        // PERBAIKAN LOGIKA: PageTransformer Efek Fade & Scale yang Benar
        binding.viewPagerTutorial.setPageTransformer { page, position ->
            when {
                position < -1 -> { // [-Infinity,-1) halaman di luar layar sebelah kiri
                    page.alpha = 0f
                }
                position <= 0 -> { // [-1,0] Halaman yang sedang bergeser ke kiri
                    page.alpha = 1f
                    page.translationX = 0f
                    page.scaleX = 1f
                    page.scaleY = 1f
                }
                position <= 1 -> { // (0,1] Halaman yang sedang masuk dari sebelah kanan
                    page.alpha = 1f - position
                    // Counteract default slide transition
                    page.translationX = -position * page.width

                    val scaleFactor = 1f - (position * 0.25f)
                    page.scaleX = scaleFactor
                    page.scaleY = scaleFactor
                }
                else -> { // (1,+Infinity] halaman di luar layar sebelah kanan
                    page.alpha = 0f
                }
            }
        }

        binding.viewPagerTutorial.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)

                // Perbaikan warna & teks tombol berdasarkan posisi halaman
                if (position == fragments.size - 1) {
                    binding.btnTutorialAction.text = "Mulai Sekarang"
                    binding.btnTutorialAction.backgroundTintList = ContextCompat.getColorStateList(
                        this@TutorialMessageActivity, android.R.color.holo_green_dark
                    )
                } else {
                    binding.btnTutorialAction.text = "Lanjut"
                    binding.btnTutorialAction.backgroundTintList = ContextCompat.getColorStateList(
                        this@TutorialMessageActivity, android.R.color.holo_blue_dark
                    )
                }
            }
        })
    }

    private fun setupIndicators() {
        binding.layoutIndicators.removeAllViews()
        val indicators = arrayOfNulls<ImageView>(fragments.size)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(8, 0, 8, 0)
        }

        for (i in indicators.indices) {
            val imageView = ImageView(this)
            imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_inactive))
            imageView.layoutParams = layoutParams
            indicators[i] = imageView
            binding.layoutIndicators.addView(imageView)
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = binding.layoutIndicators.childCount
        for (i in 0 until childCount) {
            val imageView = binding.layoutIndicators.getChildAt(i) as? ImageView
            if (i == index) {
                imageView?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_active))
            } else {
                imageView?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_inactive))
            }
        }
    }
}