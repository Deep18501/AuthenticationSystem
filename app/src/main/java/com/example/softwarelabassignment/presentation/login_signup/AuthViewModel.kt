package com.example.softwarelabassignment.presentation.login_signup

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.softwarelabassignment.Result
import com.example.softwarelabassignment.common.TextFieldState
import com.example.softwarelabassignment.common.UiEvents
import com.example.softwarelabassignment.data.model.BusinessHours
import com.example.softwarelabassignment.data.model.UserLoginDetails
import com.example.softwarelabassignment.data.model.UserRegisterDetails
import com.example.softwarelabassignment.domain.repository.LoginSignUpRepository
import com.example.softwarelabassignment.presentation.Screens
import com.example.softwarelabassignment.presentation.components.SignInResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: LoginSignUpRepository
) : ViewModel() {


    private var _loginState = MutableStateFlow(false)
    val loginState = _loginState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()


    private var _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState.asStateFlow()

    private val _emailState = mutableStateOf(TextFieldState())
    val emailState: State<TextFieldState> = _emailState

    private val _passwordState = mutableStateOf(TextFieldState())
    val passwordState: State<TextFieldState> = _passwordState

    private val _otpState = mutableStateOf(TextFieldState())
    val otpState: State<TextFieldState> = _otpState

    private val _tokenState = mutableStateOf(TextFieldState())

    private val _rpasswordState = mutableStateOf(TextFieldState())
    val rpasswordState: State<TextFieldState> = _rpasswordState

    private val _fullNameState = mutableStateOf(TextFieldState())
    val fullNameState: State<TextFieldState> = _fullNameState

    private val _phoneState = mutableStateOf(TextFieldState())
    val phoneState: State<TextFieldState> = _phoneState

    private var registerDetails: UserRegisterDetails? = null


    fun setEmailState(newText: String) {
        _emailState.value = _emailState.value.copy(text = newText)
    }

    fun setFullNameState(newText: String) {
        _fullNameState.value = _fullNameState.value.copy(text = newText)
    }

    fun setPhoneState(newText: String) {
        _phoneState.value = _phoneState.value.copy(text = newText)
    }

    suspend fun logout() {

        _loadingState.update {
            false
        }
    }

    fun setPasswordState(text: String) {
        _passwordState.value = _passwordState.value.copy(text = text)
    }

    fun setOtpState(text: String) {
        _otpState.value = _otpState.value.copy(text = text)
    }

    fun setTokenState(text: String) {
        _tokenState.value = _tokenState.value.copy(text = text)
    }

    fun setrPasswordState(text: String) {
        _rpasswordState.value = _rpasswordState.value.copy(text = text)
    }

    fun setLoadingState(isLoading: Boolean) {
        _loadingState.value = isLoading
    }


    fun onSocialSignInResult(result: SignInResult, type: String) {

        viewModelScope.launch {
            setLoadingState(true)
            Log.d("AuthViewModel", "Login Started with data $result")

            _loginState.value = loginState.value

            result.data?.let {
                UserLoginDetails(
                    email = it.userEmail,
                    type = type,
                    social_id = result.data.userId
                )
            }?.let {
                repository.loginPost(
                    it
                ).collectLatest { result ->
                    result.message?.let { Log.d("Login Result", it) }
                    when (result) {
                        is Result.Error -> {
                            snackBarEvent(result.message ?: "Error!")
                        }

                        is Result.Success -> {
                            if (result.data?.success.toBoolean()) {
                                _eventFlow.emit(UiEvents.SnackbarEvent("Logined Succesfully"))
                                delay(2000)
                                _loginState.update {
                                    result.data?.success.toBoolean()
                                }
                            } else {
                                Log.d("AuthViewModel", "Login error ${result.data?.message}")
                                snackBarEvent(result.data?.message ?: "Error!")
                            }

                        }
                    }
                    setLoadingState(false)

                    Log.d("AuthViewModel", "Login ended $_loadingState")

                }
            }

        }

    }

    fun onSocialRegisterInResult(result: SignInResult, type: String) {
        viewModelScope.launch {
            setLoadingState(true)
            Log.d("AuthViewModel", "Login Started $result")

            _loginState.value = loginState.value

            result.data?.let {
                result.data.username?.let { it1 ->
                    UserRegisterDetails(
                        email = it.userEmail,
                        type = type,
                        full_name = it1,
                        social_id = result.data.userId
                    )
                }
            }?.let {
                repository.registrationPost(
                    it
                ).collectLatest { result ->
                    result.message?.let { Log.d("Register Result", it) }
                    Log.d("AuthViewModel", "Register Started ${result.data}")

                    when (result) {

                        is Result.Error -> {
                            snackBarEvent(result.message ?: "Error!")
                        }

                        is Result.Success -> {
                            if (result.data?.success.toBoolean()) {
                                Log.d("AuthViewModel", "Register success ${result.data?.token}")

                                _eventFlow.emit(UiEvents.SnackbarEvent("Registered Succesfully"))
                                delay(2000)

                                _loginState.update {
                                    result.data?.success.toBoolean()
                                }
                            } else {
                                Log.d("AuthViewModel", "Login error ${result.data?.token}")
                                snackBarEvent(result.data?.message ?: "Error!")
                            }

                        }
                    }
                    setLoadingState(false)
                }
            }

        }

    }

    fun resetState() {
        _loginState.update { false }
    }

    suspend fun snackBarEvent(message: String) {
        _eventFlow.emit(
            UiEvents.SnackbarEvent(message)
        )
    }

    suspend fun navigationEvent(route: String) {
        _eventFlow.emit(
            UiEvents.NavigateEvent(
                route
            )
        )

    }

    fun loginUser() {
        viewModelScope.launch {
            setLoadingState(true)
            Log.d("AuthViewModel", "Login Started $_loadingState")

            _loginState.value = loginState.value

            repository.loginPost(
                UserLoginDetails(
                    emailState.value.text,
                    passwordState.value.text
                )
            ).collectLatest { result ->
                result.message?.let { Log.d("Login Result", it) }
                Log.d("AuthViewModel", "Login Started ${result.data}")

                when (result) {

                    is Result.Error -> {
                        snackBarEvent(result.message ?: "Error!")
                    }

                    is Result.Success -> {
                        if (result.data?.success.toBoolean()) {
                            _eventFlow.emit(UiEvents.SnackbarEvent("Logined Succesfully"))
                            delay(2000)
                            _loginState.update {
                                result.data?.success.toBoolean()
                            }
                        } else {
                            Log.d("AuthViewModel", "Login error ${result.data?.message}")
                            snackBarEvent(result.data?.message ?: "Error!")
                        }

                    }
                }
                setLoadingState(false)

                Log.d("AuthViewModel", "Login ended $_loadingState")

            }

        }
    }

    fun provideRegisterDetails(): UserRegisterDetails? {
        return registerDetails
    }

    fun update1() {
        registerDetails = UserRegisterDetails(
            email = emailState.value.text,
            password = passwordState.value.text,
            full_name = fullNameState.value.text,
            phone = phoneState.value.text
        )
    }

    fun update2(
        businessName: String,
        informalName: String,
        streetAddress: String,
        city: String,
        state: String,
        zipCode: String
    ) {
        try {
            registerDetails = registerDetails?.copy(
                business_name = businessName,
                informal_name = informalName,
                address = streetAddress,
                city = city,
                state = state,
                zip_code = zipCode.toInt()
            )
        } catch (e: Exception) {
            registerDetails = registerDetails?.copy(
                business_name = businessName,
                informal_name = informalName,
                address = streetAddress,
                city = city,
                state = state,
            )
        }
    }

    fun update3(document: String) {
        registerDetails = registerDetails?.copy(
            registration_proof = document
        )
    }

    fun update4(businessHours: BusinessHours) {
        registerDetails = registerDetails?.copy(
            business_hours = businessHours
        )
    }

    fun signUpUser() {
        viewModelScope.launch {
            setLoadingState(true)
            Log.d("AuthViewModel", "Login Started $_loadingState")

            _loginState.value = loginState.value
//                UserRegisterDetails(email = emailState.value.text, password = passwordState.value.text, full_name = fullNameState.value.text, phone = phoneState.value.text)

            registerDetails?.let {
                repository.registrationPost(
                    it
                ).collectLatest { result ->
                    result.message?.let { Log.d("Register Result", it) }
                    Log.d("AuthViewModel", "Register Started ${result.data}")

                    when (result) {

                        is Result.Error -> {
                            snackBarEvent(result.message ?: "Error!")
                        }

                        is Result.Success -> {
                            if (result.data?.success.toBoolean()) {
                                Log.d("AuthViewModel", "Register success ${result.data?.token}")

                                _eventFlow.emit(UiEvents.SnackbarEvent("Registered Succesfully"))
                                delay(2000)
                                _loginState.update {
                                    result.data?.success.toBoolean()
                                }
                            } else {
                                Log.d("AuthViewModel", "Login error ${result.data?.token}")
                                snackBarEvent(result.data?.message ?: "Error!")
                            }

                        }
                    }
                    setLoadingState(false)
                }
            }

        }
    }


    fun forgotPassword() {
        viewModelScope.launch {
            setLoadingState(true)
            repository.forgotPassword(phoneState.value.text).collectLatest { result ->
                Log.d("AuthViewModel", result.toString())
                when (result) {
                    is Result.Error -> {
                        snackBarEvent(result.message ?: "Error!")
                    }

                    is Result.Success -> {
                        if (result.data?.success.toBoolean()) {
                            _eventFlow.emit(UiEvents.SnackbarEvent("Otp Sent Successfully"))
                            navigationEvent(Screens.LoginOtpScreen.route)
                        } else {
                            snackBarEvent(result.data?.message ?: "Error!")
                        }

                    }
                }
                setLoadingState(false)
            }
        }
    }

    fun verifyOtp() {
        viewModelScope.launch {
            setLoadingState(true)
            repository.verifyPassword(otpState.value.text).collectLatest { result ->
                when (result) {

                    is Result.Error -> {
                        snackBarEvent(result.message ?: "Error!")
                    }

                    is Result.Success -> {
                        if (result.data?.success.toBoolean()) {
                            _eventFlow.emit(UiEvents.SnackbarEvent("Otp Verified Successfully"))
                            result.data?.token?.let { setTokenState(it) }
                            navigationEvent(Screens.LoginResetScreen.route)
                        } else {
                            snackBarEvent(result.data?.message ?: "Error!")
                        }

                    }
                }
                setLoadingState(false)
            }
        }
    }

    fun changePassword() {
        viewModelScope.launch {
            setLoadingState(true)
            repository.changePassword(
                token = _tokenState.value.text,
                password = passwordState.value.text,
                cPassword = rpasswordState.value.text
            ).collectLatest { result ->
                when (result) {

                    is Result.Error -> {
                        snackBarEvent(result.message ?: "Error!")
                    }

                    is Result.Success -> {
                        if (result.data?.success.toBoolean()) {
                            _eventFlow.emit(UiEvents.SnackbarEvent("Password Changed Successfully"))
                            navigationEvent(Screens.LoginScreenMain.route)
                        } else {
                            snackBarEvent(result.data?.message ?: "Error!")
                        }

                    }
                }
                setLoadingState(false)
            }
        }
    }


}