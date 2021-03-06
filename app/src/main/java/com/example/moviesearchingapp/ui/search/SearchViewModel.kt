package com.example.moviesearchingapp.ui.search

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.moviesearchingapp.repository.MovieRepository
import dagger.hilt.android.scopes.ViewScoped

@ViewScoped
class SearchViewModel
@ViewModelInject constructor(
    repository: MovieRepository,
    @Assisted state: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = ""
    }


    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val searchedMovies = currentQuery.switchMap { queryString ->
        repository.pagingSearch(queryString).cachedIn(viewModelScope)
    }


    fun searchWithQuery(query: String) {
        currentQuery.value = query
    }


}