package com.Naufaldo.Submisssion1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.Naufaldo.Submisssion1.R
import com.Naufaldo.Submisssion1.PageAdapter
import com.Naufaldo.Submisssion1.User
import kotlinx.android.synthetic.main.activity_detail.*
import com.Naufaldo.Submisssion1.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER= "extra_data"
    }

    private lateinit var binding : ActivityDetailBinding
    private var users = User()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setData()
        viewPagerConfig()
    }

    private fun viewPagerConfig() {
        val sectionPagerAdapter = PageAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionPagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.elevation = 0f
    }


    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            this.title = title
        }
    }

    @SuppressLint("SetTextI18n", "StringFormatInvalid")
    private fun setData() {
        val newData = intent.getParcelableExtra<User>(EXTRA_USER) as User
        newData.name?.let { setActionBarTitle(it) }
        binding.tvName.text = newData.name
        binding.tvUsername.text = newData.userName
        binding.tvCompany.text = newData.company
        binding.tvLocation.text = newData.location
        binding.tvRepository.text = newData.repository
        Glide.with(this)
            .load(newData.avatar)
            .into(binding.tvAvatar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_share) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

}
