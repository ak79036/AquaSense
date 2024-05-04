package Models

import IssueRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dataclass.problems

class ReachOutViewModel : ViewModel(){

    private val repository : IssueRepository
    private val _allItems = MutableLiveData<List<problems>>()
    val allItems : LiveData<List<problems>> = _allItems

    init {
        repository=IssueRepository().getInstance()
        repository.loadItems(_allItems)
    }
}