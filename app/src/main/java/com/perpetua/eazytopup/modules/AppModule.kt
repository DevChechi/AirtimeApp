package com.perpetua.eazytopup.modules

import com.perpetua.eazytopup.repositories.AirtimeRepository
import com.perpetua.eazytopup.viewmodels.AirtimeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel {
        AirtimeViewModel(get())
    }
}

val repositoryModule = module {
    single {
        AirtimeRepository()
    }
}
