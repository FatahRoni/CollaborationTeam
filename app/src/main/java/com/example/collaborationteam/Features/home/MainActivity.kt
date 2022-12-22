package com.example.collaborationteam.Features.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.collaborationteam.Features.adapters.UserAdapter
import com.example.collaborationteam.R
import com.example.collaborationteam.data.model.User
import com.example.collaborationteam.data.network.api.ReqresApi
import com.example.collaborationteam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),ReqrestContract.View {

    private lateinit var binding : ActivityMainBinding
    private val adapter: UserAdapter by lazy { UserAdapter() }

    private val recipeLiveData = MutableLiveData<List<User>>(listOf())
    private lateinit var presenter: ReqrestPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = ReqrestPresenter(this@MainActivity, ReqresApi()).apply {
            onAttach()
        }

        binding.rvRecipes.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        Log.e("Data Ditemukan:", adapter.toString())

        recipeLiveData.observe(this){
            adapter.submitList(it)
        }
    }

    override fun onLoading() {
        if (!isFinishing){
            binding.loader.visibility = View.VISIBLE
            binding.rvRecipes.visibility = View.GONE
        }
    }

    override fun onFinishedLoading() {
        if (!isFinishing){
            binding.loader.visibility = View.GONE
            binding.rvRecipes.visibility = View.VISIBLE
        }
    }

    override fun onError(message: String) {
    }

    override fun onSuccessGetUser(user: List<User>) {
        adapter.submitList(user)
    }

    private fun showProgress(isShown: Boolean){
    }
}