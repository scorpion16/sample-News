package com.example.myapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.myapplication.data.model.Article
import com.example.myapplication.data.repository.Repository
import com.example.myapplication.ui.main.viewmodel.MainViewModel
import com.example.myapplication.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * @author Richi on 8/30/2020 AD.
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()


    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<Resource<Article>>

    @Mock
    private lateinit var popularMovie: Article

    @Before
    fun setUp() {
        `when`(popularMovie.results).thenReturn(emptyList())
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {


        testCoroutineRule.runBlockingTest {

//            doReturn(popularMovie).`when`(repository).getPopularMovies(1)
            `when`(repository.getPopularMovies(ArgumentMatchers.anyInt())).thenReturn(popularMovie)

            val viewModel = MainViewModel(repository)
            viewModel.fetchList().observeForever(observer)
            verify(repository).getMovieDetail(1)
            verify(observer).onChanged(Resource.success(popularMovie))
            viewModel.fetchList().removeObserver(observer)
        }
    }

//    @Test
//    fun givenServerResponseError_whenFetch_shouldReturnError() {
//        `when`(popularMovie.results).thenReturn(emptyList())
//        testCoroutineRule.runBlockingTest {
//            val errorMessage = "Error Message For You"
//            doThrow(RuntimeException(errorMessage))
//                .`when`(repository).getPopularMovies(ArgumentMatchers.any())
//            val viewModel = MainViewModel(repository)
//            viewModel.getMorePopularMovies(1).observeForever(apiUsersObserver)
//            verify(repository).getMovieDetail(1)
//            verify(apiUsersObserver).onChanged(
////                Resource.error(RuntimeException(errorMessage).toString(), null)
//                Resource.error(popularMovie, "Error Occurred")
//            )
//            viewModel.getMorePopularMovies(1).removeObserver(apiUsersObserver)
//        }
//    }

    @After
    fun tearDown() {
        // do something if required
    }

}