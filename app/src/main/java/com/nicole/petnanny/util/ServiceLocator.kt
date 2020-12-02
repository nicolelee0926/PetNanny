package com.nicole.petnanny.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.nicole.petnanny.data.source.DefaultPetNannyRepository
import com.nicole.petnanny.data.source.PetNannyDataSource
import com.nicole.petnanny.data.source.PetNannyRepository
import com.nicole.petnanny.data.source.local.PetNannyLocalDataSource
import com.nicole.petnanny.data.source.remote.PetNannyRemoteDataSource

object ServiceLocator {
    @Volatile
    var repository: PetNannyRepository? = null
        @VisibleForTesting set

    fun provideRepository(context: Context): PetNannyRepository {
        synchronized(this) {
            return repository
                ?: repository
                ?: createPetNannyRepository(context)
        }
    }

    private fun createPetNannyRepository(context: Context): PetNannyRepository {
        return DefaultPetNannyRepository(
            PetNannyRemoteDataSource,
            createLocalDataSource(context)
        )
    }

    private fun createLocalDataSource(context: Context): PetNannyDataSource {
        return PetNannyLocalDataSource(context)
    }
}