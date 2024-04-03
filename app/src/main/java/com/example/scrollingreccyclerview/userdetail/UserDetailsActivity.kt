package com.example.scrollingreccyclerview.userdetail

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.scrollingreccyclerview.EndlessRecyclerViewScrollListener
import com.example.scrollingreccyclerview.R
import com.example.scrollingreccyclerview.adapter.UserDetailAdapter
import com.example.scrollingreccyclerview.databinding.ActivityUserDetailsBinding
import com.example.scrollingreccyclerview.models.Data
import com.example.scrollingreccyclerview.models.DataModel
import com.example.scrollingreccyclerview.showdetails.ShowUserDetails
import com.example.scrollingreccyclerview.ui.Status
import kotlinx.android.synthetic.main.activity_user_details.*

class UserDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityUserDetailsBinding
    lateinit var userDetailViewModel: UserDetailViewModel
    private lateinit var progressDialog: ProgressDialog
    private var userDetailAdapter : UserDetailAdapter? = null
    private lateinit var userList: MutableList<Data>

    var isLoading = true
    var pageData : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_details)

        userList = mutableListOf()

        userDetailViewModel = ViewModelProvider(this)[UserDetailViewModel::class.java]
        binding.userDetailViewModel = userDetailViewModel
        progressDialog = ProgressDialog(this)


        userDetailViewModel.userDetail(pageData)

        userDetailViewModel.liveData.observe(this){

            when(it.status){
                Status.SUCCESS -> {

                    if (userDetailAdapter == null){
                        userList.clear()
                        userList.addAll(it.data?.data!!)

                        userDetailAdapter = UserDetailAdapter(userList,this){ DataModel,_,_ ->

                            val i = Intent(this,ShowUserDetails::class.java)
                            i.putExtra("detail",DataModel)
                            startActivity(i)
                        }
                       binding.recyclerView.adapter = userDetailAdapter
                    }else{
                        userList.removeAt(userList.size - 1)
                        userDetailAdapter?.notifyItemRemoved(userList.size-1)
                        val lastPosition: Int = userList.size
                        userList.addAll(it.data?.data!!)
                        userDetailAdapter?.notifyItemMoved(lastPosition, userList.size)

                    }
                    isLoading = it.data.data?.size == 0
                    progressDialog.dismiss()

                }
                Status.LOADING -> {
                    if (userDetailAdapter == null) {
                        progressDialog.setMessage(resources.getString(R.string.loading))
                        progressDialog.setCancelable(false)
                        progressDialog.show()
                    }
                }
                Status.ERROR -> {
                    Log.d("data_info", it.message!!)
                    progressDialog.dismiss()
                }
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        binding.recyclerView.addOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                if (!isLoading) {
                    isLoading = true
                    pageData++
                    userList.add(userList.size, Data(null, null, null, null))
                    userDetailAdapter?.notifyItemInserted(userList.size - 1)
                    val handler = Handler(Looper.myLooper()!!)
                    handler.postDelayed({
                        userDetailViewModel.userDetail(page= pageData)
                    }, 1000)
                }

            }

        })
    }

}
