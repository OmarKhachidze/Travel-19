package ge.bootcamp.travel19.ui.fragments.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ge.bootcamp.travel19.data.repository.RestrictionsRepository
import ge.bootcamp.travel19.model.singup.UserInfo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(private val repository: RestrictionsRepository): ViewModel() {

    fun data() = repository.getVaccines()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun nationalities() = repository.getNationalities()
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    fun postUser(userInfo: UserInfo) = repository.postUserInfo(userInfo)
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())
}