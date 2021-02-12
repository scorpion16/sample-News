package com.example.myapplication.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.myapplication.data.model.Article
import com.example.myapplication.databinding.FragmentDetailsBinding
import com.example.myapplication.ui.main.viewmodel.DetailViewModel
import com.example.myapplication.utils.ImageDownloader
import com.example.myapplication.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailsFragment : Fragment() {


    private val viewModel : DetailViewModel by viewModel()

    private lateinit var binding : FragmentDetailsBinding
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.details.value = Article()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var model : Article? = arguments?.getParcelable<Article>("article")
        if (model != null) {
            retrieveData(model)
        }
        viewModel.details.observe(viewLifecycleOwner , Observer {
            ImageDownloader.download(binding.imageViewAvatar , it.urlToImage)
        })
//        fetchDetails(model?.authorid?:0)
    }

    private fun fetchDetails(id:Int) {
        viewModel.isProgressing.value = true
        viewModel.getDetail(id).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->

                when (resource.status) {
                    Status.SUCCESS -> {
                        viewModel.isProgressing.value = false
                        resource.data?.let { movie -> retrieveData(movie) }
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

    private fun retrieveData(movie: Article) {
        viewModel.details.value = movie
    }
}