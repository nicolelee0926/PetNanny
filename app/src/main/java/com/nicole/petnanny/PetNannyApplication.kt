package com.nicole.petnanny

import android.app.Application
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.util.ServiceLocator
import kotlin.properties.Delegates

class PetNannyApplication: Application() {

    val repository: PetNannyRepository
        get() = ServiceLocator.provideRepository(this)

    companion object {
        var instance: PetNannyApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

//ues refresh
    fun isLiveDataDesign() = true
}