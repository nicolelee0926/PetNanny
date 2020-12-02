package com.nicole.petnanny.data.source

class DefaultPetNannyRepository(private val remoteDataSource: PetNannyDataSource,
                                private val localDataSource: PetNannyDataSource
) : PetNannyRepository  {

}