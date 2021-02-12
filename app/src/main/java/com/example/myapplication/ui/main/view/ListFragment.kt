package com.example.myapplication.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.model.Article
import com.example.myapplication.data.model.News
import com.example.myapplication.databinding.FragmentListBinding
import com.example.myapplication.ui.adapter.MainAdapter
import com.example.myapplication.ui.base.OnItemClick
import com.example.myapplication.ui.base.PaginationScrollListener
import com.example.myapplication.ui.main.viewmodel.MainViewModel
import com.example.myapplication.utils.Status
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var adapter: MainAdapter
    private lateinit var binding : FragmentListBinding
    var isLastPage: Boolean = false

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return if (::binding.isInitialized) {
            binding.root
        } else {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)

            binding = FragmentListBinding.inflate(inflater,container,false)
            binding.lifecycleOwner = this
            binding.viewModel = viewModel

            with(binding) {
                //some stuff
                root
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!::adapter.isInitialized) {
            setupRecyclerView()
            setupRefrehLayout()
            fetchNewsList(false)
        }
    }


    private fun setupRefrehLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            fetchNewsList(false)
            swipeRefreshLayout.isRefreshing = false
        }
    }
    private fun setupRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(activity , 1)
        adapter = MainAdapter(arrayListOf())
        adapter.setItemClickListener(object :OnItemClick<Article> {
            override fun onItemClicked(item: Article) {
                var bundle = bundleOf("article" to item )
                findNavController().navigate(R.id.action_Show_Details , bundle , getNavOptions())
            }
        })
        recyclerView.addOnScrollListener(object : PaginationScrollListener(recyclerView.layoutManager as GridLayoutManager) {


            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return viewModel.isProgressing.value!!
            }

            override fun loadMoreItems() {
                fetchNewsList(true)
            }
        })
        recyclerView.adapter = adapter

    }

    private fun fetchNewsList(fetchMore:Boolean) {
        viewModel.isProgressing.value = true
        viewModel.fetchList(fetchMore).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->

                when (resource.status) {
                    Status.SUCCESS -> {
                        viewModel.isProgressing.value = false
                        resource.data?.let { dto -> retrieveList(dto) }
                    }
                    Status.ERROR -> {
                        viewModel.isProgressing.value = false
                        Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        viewModel.isProgressing.value = true
                    }
                }
            }
        })
    }

    private fun retrieveList(news: News) {
        isLastPage = news.articles?.size?:0 <= 0
        adapter.apply {
            addItems(news.articles)
            notifyDataSetChanged()
        }
    }
    protected fun getNavOptions(): NavOptions? {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_left)
            .setExitAnim(R.anim.slide_out_right)
            .setPopEnterAnim(R.anim.slide_in_right)
            .setPopExitAnim(R.anim.slide_out_left)
            .build()
//        return NavOptions.Builder()
//            .setEnterAnim(R.anim.nav_default_enter_anim)
//            .setExitAnim(R.anim.nav_default_exit_anim)
//            .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
//            .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
//            .build()
    }
}